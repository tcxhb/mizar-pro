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
import com.tcxhb.mizar.common.utils.DateUtils;
import com.tcxhb.mizar.common.utils.ParamUtils;
import com.tcxhb.mizar.core.entity.MetricEntity;
import com.tcxhb.mizar.core.service.biz.MetricDBService;
import com.tcxhb.mizar.dao.dataobject.query.MetricsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author leyou
 */
@Controller
@RequestMapping(value = "/metric", produces = MediaType.APPLICATION_JSON_VALUE)
public class MetricHistoryController {
    @Autowired
    private MetricDBService metricDBService;

    @ResponseBody
    @RequestMapping("/queryResourceHistory.json")
    public MiscResult<?> queryByAppAndResource(@RequestBody MetricQueryReq req) {
        ParamUtils.notEmpty(req.getAppName(), "appName is null");
        ParamUtils.notEmpty(req.getResource(), "resource is null");
        ParamUtils.notNull(req.getStartTime(), "startTime is null");
        Date start = DateUtils.getStartOfDay(new Date(req.getStartTime()));
        Date end = DateUtils.getEndOfDay(new Date(req.getStartTime()));
        req.setStartTime(start.getTime());
        req.setEndTime(end.getTime());
        MetricsQuery query = query(req);
        List<MetricEntity> entities = metricDBService.queryByAppAndResourceBetween(query);
        List<MetricVo> vos = MetricVo.fromMetricEntities(entities, req.getResource());
        return MiscResult.suc(sortMetricVoAndDistinct(vos));
    }

    private MetricsQuery query(MetricQueryReq req) {
        MetricsQuery query = new MetricsQuery();
        query.setApp(req.getAppName());
        query.setResource(req.getResource());
        query.setStartTime(req.getStartTime());
        query.setEndTime(req.getEndTime());
        return query;
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
