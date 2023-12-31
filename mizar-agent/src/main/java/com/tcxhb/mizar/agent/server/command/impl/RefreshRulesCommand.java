package com.tcxhb.mizar.agent.server.command.impl;

import com.tcxhb.mizar.agent.server.command.CommandHandler;
import com.tcxhb.mizar.agent.server.command.CommandMapping;
import com.tcxhb.mizar.agent.server.command.CommandRequest;
import com.tcxhb.mizar.agent.service.ConfigService;
import com.tcxhb.mizar.common.model.MiscResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
@Component
@Slf4j
@CommandMapping(name = "/cmd/refresh/rules", desc = "更新配置")
public class RefreshRulesCommand implements CommandHandler<MiscResult<Boolean>> {
    @Autowired
    private ConfigService configService;

    @Override
    public MiscResult<Boolean> handle(CommandRequest request) {
        log.info("cmd-refresh-rules");
        configService.refresh(-1L);
        return MiscResult.suc(Boolean.TRUE);
    }
}
