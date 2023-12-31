package com.tcxhb.mizar.admin.controller.biz;

import com.tcxhb.mizar.admin.convert.AlarmRuleConvert;
import com.tcxhb.mizar.admin.model.request.add.AlarmRuleAddReq;
import com.tcxhb.mizar.admin.model.request.query.AlarmRuleQueryReq;
import com.tcxhb.mizar.admin.model.response.AlarmRuleVO;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.common.request.CommonIdDeleteReq;
import com.tcxhb.mizar.common.utils.ConvertUtils;
import com.tcxhb.mizar.core.service.alarm.AlarmRuleService;
import com.tcxhb.mizar.dao.dataobject.AlarmRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.AlarmRuleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:auto.generator
 * time: 2023-12-29
 */
@RestController
@RequestMapping("/alarmRule")
public class AlarmRuleController {

    @Autowired
    private AlarmRuleConvert convert;
    @Autowired
    private AlarmRuleService alarmRuleService;


    @PostMapping(value = "/page", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<PageResponse<AlarmRuleVO>> page(@RequestBody AlarmRuleQueryReq req) {
        AlarmRuleQuery query = convert.toQuery(req);
        PageResponse<AlarmRuleDO> response = alarmRuleService.page(query);
        PageResponse<AlarmRuleVO> result = ConvertUtils.page(response, convert::do2VO);
        return MiscResult.suc(result);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<Boolean> create(@RequestBody AlarmRuleAddReq req) {
        AlarmRuleDO query = convert.req2DO(req);
        Long id = alarmRuleService.create(query);
        return MiscResult.suc(true);
    }


    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<String> update(@RequestBody AlarmRuleAddReq req) {
        AlarmRuleDO query = convert.req2DO(req);
        Boolean result = alarmRuleService.updateById(query);
        if (Boolean.TRUE.equals(result)) {
            return MiscResult.suc("操作成功");
        }
        return MiscResult.err(CommonErrorCode.BAD_REQUEST);
    }

    @PostMapping(value = "/deleteById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<String> deleteById(@RequestBody CommonIdDeleteReq req) {
        Boolean result = alarmRuleService.deleteById(req.getId());
        if (Boolean.TRUE.equals(result)) {
            return MiscResult.suc("操作成功");
        }
        return MiscResult.err(CommonErrorCode.BAD_REQUEST);
    }
}
