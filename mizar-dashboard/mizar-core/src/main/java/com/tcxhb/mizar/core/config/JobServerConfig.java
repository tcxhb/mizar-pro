package com.tcxhb.mizar.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/20
 */
@Configuration
@Component
@Data
public class JobServerConfig {
    @Value("${server.port}")
    private Integer port;
    @Value("${spring.application.name}")
    private String serverName;
}
