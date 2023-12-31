/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tcxhb.mizar.admin.controller.biz;

import com.tcxhb.mizar.admin.model.request.query.MetricQueryReq;
import com.tcxhb.mizar.admin.model.response.MetricVo;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.core.entity.MetricEntity;
import com.tcxhb.mizar.core.service.biz.MetricService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author leyou
 */
@Controller
@RequestMapping(value = "/metric", produces = MediaType.APPLICATION_JSON_VALUE)
public class MetricController {

    private static Logger logger = LoggerFactory.getLogger(MetricController.class);

    @Autowired
    private MetricService metricStore;

    @ResponseBody
    @PostMapping("/queryResourceMetric")
    public MiscResult<Map<String, Object>> queryTopResourceMetric(@RequestBody MetricQueryReq req) {
        req.check();
        List<String> resources = metricStore.listResourcesOfApp(req.getAppName());
        logger.debug("queryTopResourceMetric(), resources.size()={}", resources.size());
        if (resources == null || resources.isEmpty()) {
            return MiscResult.suc(null);
        }
        if (!req.getDesc()) {
            Collections.reverse(resources);
        }
        if (StringUtils.isNotEmpty(req.getResource())) {
            List<String> searched = new ArrayList<>();
            for (String resource : resources) {
                if (resource.contains(req.getResource())) {
                    searched.add(resource);
                }
            }
            resources = searched;
        }
        //翻页
        int pageSize = req.getPage().getPageSize();
        int pageIndex = req.getPage().getPageNum();
        int totalPage = (resources.size() + pageSize - 1) / pageSize;
        List<String> topResource = new ArrayList<>();
        if (pageIndex <= totalPage) {
            topResource = resources.subList((pageIndex - 1) * pageSize,
                    Math.min(pageIndex * pageSize, resources.size()));
        }
        final Map<String, Iterable<MetricVo>> map = new ConcurrentHashMap<>();
        logger.debug("topResource={}", topResource);
        long time = System.currentTimeMillis();
        for (final String resource : topResource) {
            List<MetricEntity> entities = metricStore.queryByAppAndResourceBetween(
                    req.getAppName(), resource, req.getStartTime(), req.getEndTime());
            logger.debug("resource={}, entities.size()={}", resource, entities == null ? "null" : entities.size());
            List<MetricVo> vos = MetricVo.fromMetricEntities(entities, resource);
            Iterable<MetricVo> vosSorted = sortMetricVoAndDistinct(vos);
            map.put(resource, vosSorted);
        }
        logger.debug("queryTopResourceMetric() total query time={} ms", System.currentTimeMillis() - time);
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("totalCount", Long.parseLong(resources.size() + ""));
        Map<String, Iterable<MetricVo>> map2 = new LinkedHashMap<>();
        // order matters.
        for (String identity : topResource) {
            map2.put(identity, map.get(identity));
        }
        resultMap.put("metric", map2);
        return MiscResult.suc(resultMap);
    }

    @ResponseBody
    @RequestMapping("/queryByAppAndResource.json")
    public MiscResult<?> queryByAppAndResource(@RequestBody MetricQueryReq req) {
        List<MetricEntity> entities = metricStore.queryByAppAndResourceBetween(
                req.getAppName(), req.getResource(), req.getStartTime(), req.getEndTime());
        List<MetricVo> vos = MetricVo.fromMetricEntities(entities, req.getResource());
        return MiscResult.suc(sortMetricVoAndDistinct(vos));
    }

    private Iterable<MetricVo> sortMetricVoAndDistinct(List<MetricVo> vos) {
        if (vos == null) {
            return null;
        }
        Map<Long, MetricVo> map = new TreeMap<>();
        for (MetricVo vo : vos) {
            MetricVo oldVo = map.get(vo.getTimestamp());
            if (oldVo == null || vo.getGmtCreate().getTime() > oldVo.getGmtCreate().getTime()) {
                map.put(vo.getTimestamp(), vo);
            }
        }
        return map.values();
    }
}
