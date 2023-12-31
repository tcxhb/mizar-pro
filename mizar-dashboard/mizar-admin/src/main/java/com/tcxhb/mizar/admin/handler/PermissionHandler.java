package com.tcxhb.mizar.admin.handler;

import com.tcxhb.mizar.core.constants.Permission;
import com.tcxhb.mizar.core.constants.enums.UserTypeEnum;
import com.tcxhb.mizar.core.entity.UserDTO;
import com.tcxhb.mizar.core.service.MiscPermService;
import com.tcxhb.mizar.admin.utils.ContextHolder;
import com.tcxhb.mizar.common.constants.BaseException;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.utils.ParamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/9/21
 */
@Slf4j
@Component
public class PermissionHandler implements HandlerInterceptor {
    @Autowired
    private MiscPermService miscPermService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        //
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //权限校验
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Permission permission = handlerMethod.getMethod().getAnnotation(Permission.class);
        if (permission == null) {
            return true;
        }
        log.info(">>> PermissionHandler preHandle -- ");
        //超级管理员
        UserDTO userDTO = ContextHolder.get();
        ParamUtils.notNull(userDTO, "无登录信息,权限校验失败");
        if (UserTypeEnum.SUPPER.getType().equals(userDTO.getType())) {
            return true;
        }
        //判断权限
        Set<String> permCodes = miscPermService.getPermByRole(userDTO.getType());
        //不包含数字
        if (!permCodes.contains(permission.code())) {
            throw new BaseException(CommonErrorCode.FORBIDDEN.getCode(), "权限不足" + permission.code());
        }
        return true;
    }
}
