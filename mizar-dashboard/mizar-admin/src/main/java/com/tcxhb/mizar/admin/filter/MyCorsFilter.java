package com.tcxhb.mizar.admin.filter;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/10/5
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
@WebFilter(filterName = "accessFilter", urlPatterns = "/*")
public class MyCorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-token,x-requested-with,content-type");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("ok");
            return;
        }
        chain.doFilter(req, res);
    }
    @Override
    public void init(FilterConfig filterConfig) {
        log.info("AccessFilter过滤器初始化！");
    }

    public void destroy() {
    }
}
