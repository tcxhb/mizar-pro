package com.tcxhb.mizar.agent.server.netty;

import com.tcxhb.mizar.agent.constants.AgentConfig;
import com.tcxhb.mizar.common.Thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
@Component
@Slf4j
public class AgentServerFactory {
    @Autowired
    private AgentConfig config;
    private AgentServer server;

    @PostConstruct
    public void serverStart() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("start-mizar-agent:" + config.getPort());
                    server = new AgentServer();
                    server.start(config.getPort());
                } catch (Exception e) {
                    log.error("start-mizar-agent:" + config.getServerHost(), e);
                    throw new RuntimeException("客户端服务启动异常");
                }
            }
        };
        NamedThreadFactory factory = new NamedThreadFactory("agent-server", true);
        Thread t = factory.newThread(runnable);
        t.start();
    }
}
