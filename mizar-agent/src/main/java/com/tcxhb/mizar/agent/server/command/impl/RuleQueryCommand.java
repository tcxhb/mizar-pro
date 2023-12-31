package com.tcxhb.mizar.agent.server.command.impl;

import com.tcxhb.mizar.agent.server.command.CommandHandler;
import com.tcxhb.mizar.agent.server.command.CommandMapping;
import com.tcxhb.mizar.agent.server.command.CommandRequest;
import com.tcxhb.mizar.agent.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
@Component
@Slf4j
@CommandMapping(name = "/cmd/rule/query", desc = "QPS查询")
public class RuleQueryCommand implements CommandHandler<Map<String, Object>> {
    @Autowired
    private ConfigService configService;

    @Override
    public Map<String, Object> handle(CommandRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("version", configService.currentVersion());
        result.put("rules", configService.queryRule());
        return result;
    }
}
