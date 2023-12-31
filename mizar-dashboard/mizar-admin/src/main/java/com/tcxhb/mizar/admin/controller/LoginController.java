package com.tcxhb.mizar.admin.controller;

import com.tcxhb.mizar.admin.model.request.LoginReq;
import com.tcxhb.mizar.admin.utils.ContextHolder;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.utils.ParamUtils;
import com.tcxhb.mizar.core.entity.TokenDTO;
import com.tcxhb.mizar.core.entity.UserDTO;
import com.tcxhb.mizar.core.service.LoginService;
import com.tcxhb.mizar.core.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/3/31
 */
@RestController
@RequestMapping("/system/login")
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public MiscResult<TokenDTO> login(@RequestBody LoginReq req) {
        req.check();
        String md5 = Md5Utils.getMD5(req.getPassword());
        req.setPassword(md5);
        return loginService.login(req.getUsername(), req.getPassword());
    }

    @PostMapping(value = "/logout")
    public MiscResult logout() {
        //清除redis的key
        UserDTO dto = ContextHolder.get();
        ParamUtils.notNull(dto, "未登录");
        String sessionId = dto.getToken();
        return loginService.loginOut(sessionId);
    }

    @PostMapping(value = "/getLoginUser")
    public MiscResult<UserDTO> getLoginUser() {
        UserDTO dto = ContextHolder.get();
        if (dto == null) {
            return MiscResult.err(CommonErrorCode.NEED_LOGIN);
        }
        return loginService.getLoginUser(dto.getToken());
    }
}
