package com.tcxhb.mizar.admin.controller.biz;

import com.tcxhb.mizar.admin.controller.BaseController;
import com.tcxhb.mizar.admin.convert.MachineConvert;
import com.tcxhb.mizar.admin.model.request.query.MachineQueryReq;
import com.tcxhb.mizar.admin.model.response.MachineVO;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.common.request.CommonIdDeleteReq;
import com.tcxhb.mizar.common.request.CommonIdQueryReq;
import com.tcxhb.mizar.common.utils.ConvertUtils;
import com.tcxhb.mizar.core.service.biz.MachineService;
import com.tcxhb.mizar.dao.dataobject.MachineDO;
import com.tcxhb.mizar.dao.dataobject.query.MachineQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:auto.generator
 * time: 2023-11-05
 */
@RestController
@RequestMapping("/machine")
public class MachineController extends BaseController {

    @Autowired
    private MachineConvert convert;
    @Autowired
    private MachineService machineService;


    @PostMapping(value = "/page", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<PageResponse<MachineVO>> page(@RequestBody MachineQueryReq req) {
        fillAppName(req);
        MachineQuery query = convert.toQuery(req);
        PageResponse<MachineDO> response = machineService.page(query);
        PageResponse<MachineVO> result = ConvertUtils.page(response, convert::do2VO);
        return MiscResult.suc(result);
    }


    @PostMapping(value = "/queryById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<MachineVO> queryById(@RequestBody CommonIdQueryReq req) {
        MachineDO relationDO = machineService.queryById(req.getId());
        MachineVO vo = convert.do2VO(relationDO);
        return MiscResult.suc(vo);
    }

    @PostMapping(value = "/deleteById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<String> deleteById(@RequestBody CommonIdDeleteReq req) {
        Boolean result = machineService.deleteById(req.getId());
        if (Boolean.TRUE.equals(result)) {
            return MiscResult.suc("操作成功");
        }
        return MiscResult.err(CommonErrorCode.BAD_REQUEST);
    }
}
