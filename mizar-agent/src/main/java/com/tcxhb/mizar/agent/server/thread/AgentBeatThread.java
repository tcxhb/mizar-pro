package com.tcxhb.mizar.agent.server.thread;

import com.tcxhb.mizar.agent.constants.AgentConfig;
import com.tcxhb.mizar.agent.constants.Host;
import com.tcxhb.mizar.agent.service.ConfigService;
import com.tcxhb.mizar.agent.service.ServerCenterApi;
import com.tcxhb.mizar.common.Thread.NamedThreadFactory;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
@Slf4j
public class AgentBeatThread implements InitializingBean, DisposableBean {
    @Autowired
    private ServerCenterApi serverCenterApi;
    @Autowired
    private ConfigService configService;
    @Autowired
    private AgentConfig config;
    public static Host host;
    private ScheduledExecutorService agentBeatPool = Executors.newScheduledThreadPool(1,
            new NamedThreadFactory("agent-beat", true));

    /**
     * 心跳
     */
    protected void register(Host host) {
        Integer port = config.getPort();
        String appName = config.getAppName();
        //log.info("agent-beat:" + appName + "," + host.getHost() + ":" + port + "");
        MiscResult<Long> result = serverCenterApi.register(host.getHost(), port, appName);
        //刷新配置
        if (result.isSuccess()) {
            configService.refresh(result.getData());
        }
    }

    @Override
    public void destroy() throws Exception {
        Integer port = config.getPort();
        String appName = config.getAppName();
        log.error("agent-offline:" + appName + "," + host.getHost() + ":" + port + "");
        serverCenterApi.offline(host.getHost(), port, appName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
        agentBeatPool.scheduleAtFixedRate(() -> {
            try {
                //注册
                register(this.host);
            } catch (Exception e) {
                log.error("agent-beat", e);
            }
        }, 10, 60, TimeUnit.SECONDS);
    }

    public void init() {
        InetAddress local = IPUtils.getIpAddress();
        Host host = new Host();
        host.setHost(local.getHostAddress());
        host.setHostName(local.getHostName());
        AgentBeatThread.host = host;
    }
}
