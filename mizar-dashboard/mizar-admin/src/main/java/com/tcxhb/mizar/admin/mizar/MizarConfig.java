package com.tcxhb.mizar.admin.mizar;

import com.tcxhb.mizar.agent.server.thread.AgentBeatThread;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/22
 */
@ComponentScan(basePackages = {
        "com.tcxhb.mizar.agent.*"
})
@Component
public class MizarConfig extends AgentBeatThread {
}
