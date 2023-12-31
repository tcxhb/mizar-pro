package com.tcxhb.mizar.admin.controller;

import com.tcxhb.mizar.core.utils.PasswordValidator;
import com.tcxhb.mizar.dao.dataobject.query.MiscUserQuery;
import com.tcxhb.mizar.admin.model.request.UserAddReq;
import com.tcxhb.mizar.admin.model.response.UserVO;
import com.tcxhb.mizar.admin.utils.ContextHolder;
import com.tcxhb.mizar.core.constants.enums.UserTypeEnum;
import com.tcxhb.mizar.admin.convert.MiscUserConvert;
import com.tcxhb.mizar.dao.dataobject.MiscUserDO;
import com.tcxhb.mizar.admin.model.request.UserQueryReq;
import com.tcxhb.mizar.core.entity.UserDTO;
import com.tcxhb.mizar.core.service.MiscUserService;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.common.request.CommonIdDeleteReq;
import com.tcxhb.mizar.common.request.CommonIdQueryReq;
import com.tcxhb.mizar.common.utils.ConvertUtils;
import com.tcxhb.mizar.common.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:auto.generator
 * time: 2023-09-20
 */
@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private MiscUserConvert convert;
    @Autowired
    private MiscUserService miscUserService;

    @PostMapping(value = "/page", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<PageResponse<UserVO>> page(@RequestBody UserQueryReq req) {
        fillAppName(req);
        MiscUserQuery query = convert.toQuery(req);
        //当前登录的APPID
        PageResponse<MiscUserDO> response = miscUserService.page(query);
        PageResponse<UserVO> result = ConvertUtils.page(response, convert::do2VO);
        //填充角色名称
        return MiscResult.suc(result);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<Boolean> create(@RequestBody UserAddReq req) {
        req.check();
        MiscUserDO userDO = convert.req2DO(req);
        userDO.setType(UserTypeEnum.NORMAL.getType());
        Long id = miscUserService.create(userDO);
        return MiscResult.suc(true);
    }


    @PostMapping(value = "/queryById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<UserVO> queryById(@RequestBody CommonIdQueryReq req) {
        MiscUserDO relationDO = miscUserService.queryById(req.getId());
        UserVO vo = convert.do2VO(relationDO);
        return MiscResult.suc(vo);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<String> update(@RequestBody UserAddReq req) {
        permissionCheck(req.getId());
        MiscUserDO userDO = convert.req2DO(req);
        userDO.setPassword(null);
        Boolean result = miscUserService.updateById(userDO);
        if (Boolean.TRUE.equals(result)) {
            return MiscResult.suc("操作成功");
        }
        return MiscResult.err(CommonErrorCode.BAD_REQUEST);
    }

    @PostMapping(value = "/updatePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<Boolean> updatePassword(@RequestBody UserAddReq req) {
        //参数校验
        ParamUtils.notNull(req.getPassword(), "password不能为空");
        PasswordValidator.ValidResult check = PasswordValidator.validPwd(req.getPassword());
        ParamUtils.isTrue(check.isValid(), check.getMsg());
        //构造
        MiscUserDO userDO = new MiscUserDO();
        userDO.setId(req.getId());
        userDO.setPassword(req.getPassword());
        Boolean result = miscUserService.updateById(userDO);
        if (Boolean.TRUE.equals(result)) {
            return MiscResult.suc(Boolean.TRUE);
        }
        return MiscResult.err(CommonErrorCode.BAD_REQUEST);
    }

    @PostMapping(value = "/deleteById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MiscResult<String> deleteById(@RequestBody CommonIdDeleteReq req) {
        permissionCheck(req.getId());
        Boolean result = miscUserService.deleteById(req.getId());
        if (Boolean.TRUE.equals(result)) {
            return MiscResult.suc("操作成功");
        }
        return MiscResult.err(CommonErrorCode.BAD_REQUEST);
    }

    private void permissionCheck(Long userId) {
        //只有管理员或者自己
        ParamUtils.notNull(userId, "id不能为空");
        UserDTO login = ContextHolder.get();
        if (UserTypeEnum.NORMAL.eq(login.getType())) {
            ParamUtils.isTrue(userId.equals(login.getUserId()), "只有管理才能修改用户信息");
        }
    }
}
