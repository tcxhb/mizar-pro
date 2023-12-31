package com.tcxhb.mizar.admin.controller.biz;

import com.tcxhb.mizar.admin.controller.BaseController;
import com.tcxhb.mizar.admin.convert.AppConvert;
import com.tcxhb.mizar.admin.model.request.add.AppAddReq;
import com.tcxhb.mizar.admin.model.response.AppVO;
import com.tcxhb.mizar.dao.dataobject.AppDO;
import com.tcxhb.mizar.dao.dataobject.query.AppQuery;
import com.tcxhb.mizar.admin.model.request.query.AppQueryReq;
import com.tcxhb.mizar.admin.utils.ContextHolder;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.common.request.CommonIdDeleteReq;
import com.tcxhb.mizar.common.request.CommonIdQueryReq;
import com.tcxhb.mizar.common.utils.ConvertUtils;
import com.tcxhb.mizar.common.utils.ParamUtils;
import com.tcxhb.mizar.core.constants.enums.UserTypeEnum;
import com.tcxhb.mizar.core.entity.UserDTO;
import com.tcxhb.mizar.core.service.biz.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author:auto.generator
 * time: 2023-11-02
 */
@RestController
@RequestMapping("/app")
public class AppController extends BaseController {

    @Autowired
    private AppConvert convert;
    @Autowired
    private AppService appService;


    @PostMapping(value = "/page", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<PageResponse<AppVO>> page(@RequestBody AppQueryReq req) {
        fillAppName(req);
        AppQuery query = convert.toQuery(req);
        PageResponse<AppDO> response = appService.page(query);
        PageResponse<AppVO> result = ConvertUtils.page(response, convert::do2VO);
        return MiscResult.suc(result);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<Boolean> create(@RequestBody AppAddReq req) {
        check();
        req.check();
        AppDO query = convert.req2DO(req);
        Long id = appService.create(query);
        return MiscResult.suc(true);
    }

    @PostMapping(value = "/queryById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<AppVO> queryById(@RequestBody CommonIdQueryReq req) {
        AppDO relationDO = appService.queryById(req.getId());
        AppVO vo = convert.do2VO(relationDO);
        return MiscResult.suc(vo);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<String> update(@RequestBody AppAddReq req) {
        req.check();
        AppDO update = convert.req2DO(req);
        Boolean result = appService.updateById(update);
        if (Boolean.TRUE.equals(result)) {
            return MiscResult.suc("操作成功");
        }
        return MiscResult.err(CommonErrorCode.BAD_REQUEST);
    }

    @PostMapping(value = "/deleteById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<String> deleteById(@RequestBody CommonIdDeleteReq req) {
        check();
        Boolean result = appService.deleteById(req.getId());
        if (Boolean.TRUE.equals(result)) {
            return MiscResult.suc("操作成功");
        }
        return MiscResult.err(CommonErrorCode.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<List<AppVO>> list(@RequestBody AppQueryReq req) {
        fillAppName(req);
        AppQuery query = convert.toQuery(req);
        List<AppDO> list = appService.list(query);
        List<AppVO> result = ConvertUtils.list(list, convert::do2VO);
        return MiscResult.suc(result);
    }

    private void check() {
        //只有管理员或者自己
        UserDTO login = ContextHolder.get();
        if (UserTypeEnum.NORMAL.eq(login.getType())) {
            ParamUtils.isTrue(false, "只有管理可以操作");
        }
    }
}
