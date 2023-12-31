package com.tcxhb.mizar.admin.controller.biz;

import com.tcxhb.mizar.admin.convert.FlowRuleConvert;
import com.tcxhb.mizar.admin.model.request.add.FlowRuleAddReq;
import com.tcxhb.mizar.admin.model.request.query.FlowRuleQueryReq;
import com.tcxhb.mizar.admin.model.response.FlowRuleVO;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.common.request.CommonIdDeleteReq;
import com.tcxhb.mizar.common.request.CommonIdQueryReq;
import com.tcxhb.mizar.common.utils.ConvertUtils;
import com.tcxhb.mizar.core.service.biz.FlowRuleService;
import com.tcxhb.mizar.dao.dataobject.FlowRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.FlowRuleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:auto.generator
 * time: 2023-12-14
 */
@RestController
@RequestMapping("/flowRule")
public class FlowRuleController {
    @Autowired
    private FlowRuleConvert convert;
    @Autowired
    private FlowRuleService flowRuleService;


    @PostMapping(value = "/page", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<PageResponse<FlowRuleVO>> page(@RequestBody FlowRuleQueryReq req) {
        FlowRuleQuery query = convert.toQuery(req);
        PageResponse<FlowRuleDO> response = flowRuleService.page(query);
        PageResponse<FlowRuleVO> result = ConvertUtils.page(response, convert::do2VO);
        return MiscResult.suc(result);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<Boolean> create(@RequestBody FlowRuleAddReq req) {
        req.check();
        FlowRuleDO query = convert.req2DO(req);
        Long id = flowRuleService.create(query);
        return MiscResult.suc(true);
    }

    @PostMapping(value = "/queryById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<FlowRuleVO> queryById(@RequestBody CommonIdQueryReq req) {
        FlowRuleDO relationDO = flowRuleService.queryById(req.getId());
        FlowRuleVO vo = convert.do2VO(relationDO);
        return MiscResult.suc(vo);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<String> update(@RequestBody FlowRuleAddReq req) {
        req.check();
        FlowRuleDO query = convert.req2DO(req);
        Boolean result = flowRuleService.updateById(query);
        if (Boolean.TRUE.equals(result)) {
            return MiscResult.suc("操作成功");
        }
        return MiscResult.err(CommonErrorCode.BAD_REQUEST);
    }

    @PostMapping(value = "/deleteById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<String> deleteById(@RequestBody CommonIdDeleteReq req) {
        Boolean result = flowRuleService.deleteById(req.getId());
        if (Boolean.TRUE.equals(result)) {
            return MiscResult.suc("操作成功");
        }
        return MiscResult.err(CommonErrorCode.BAD_REQUEST);
    }
}
