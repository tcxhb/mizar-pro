package com.tcxhb.mizar.admin.filter;

import com.tcxhb.mizar.admin.handler.PermissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/9/21
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private PermissionHandler permissionHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionHandler).addPathPatterns("/**")
                .excludePathPatterns("/api/**").order(0);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
