package com.tcxhb.mizar.agent.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
@Component
@Data
@ConfigurationProperties(prefix = "mizar.agent")
public class AgentConfig {
    //默认agent服务端口
    private Integer port = 9008;
    //当前应用名称
    private String appName;
    //服务器地址
    private String serverHost;
}
