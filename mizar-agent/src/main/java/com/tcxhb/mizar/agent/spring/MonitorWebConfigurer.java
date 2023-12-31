package com.tcxhb.mizar.agent.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/10
 */
@Configuration
public class MonitorWebConfigurer implements WebMvcConfigurer {
    @Autowired
    private MonitorInterceptor monitorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(monitorInterceptor).addPathPatterns("/**")
                .excludePathPatterns("*.js")
                .excludePathPatterns("*.html")
                .excludePathPatterns("*.css")
                .order(1);
    }
}
