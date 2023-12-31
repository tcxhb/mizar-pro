package com.tcxhb.mizar.admin.filter;

import com.tcxhb.mizar.admin.utils.ContextHolder;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.core.entity.UserDTO;
import com.tcxhb.mizar.core.service.LoginService;
import com.tcxhb.mizar.core.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/3/31
 */
@Component
@WebFilter(filterName = "myFilter", urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {
    @Autowired
    private LoginService loginService;
    @Autowired
    private ResponseService responseService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        ContextHolder.clean();
        //不需要登录的
        if (skipFilter(req)) {
            chain.doFilter(req, res);
            return;
        }
        //登录成功
        if (checkLogin(req, res)) {
            chain.doFilter(req, res);
            return;
        }
    }

    private boolean checkLogin(HttpServletRequest req, HttpServletResponse res) {
        if (req.getMethod().equals("option")) {
            return true;
        }
        //登录逻辑
        String sessionId = req.getHeader("X-Token");
        MiscResult<UserDTO> result = loginService.getLoginUser(sessionId);
        if (result.getData() == null) {
            log.error("check login," + req.getRequestURI() + "," + sessionId + "," + result.getMsg());
            responseService.returnJson(res, result);
            return false;
        }
        //用户登录后的权限
        ContextHolder.put(result.getData());
        return true;
    }

    public boolean skipFilter(HttpServletRequest request) {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String path = request.getServletPath();
        for (String skip : ALLOWED_PATHS) {
            if (path.contains(skip)) {
                return true;
            }
        }
        return false;
    }

    private static final List<String> ALLOWED_PATHS = Arrays.asList(
            "/api/",
            "/static",
            "/index",
            "/system/login/login",
            "favicon.ico",
            ".html",
            ".js",
            ".css",
            "swagger",
            "api-docs"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
