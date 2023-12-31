package com.tcxhb.mizar.admin.controller.api;

import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.biz.FlowRuleConfig;
import com.tcxhb.mizar.common.utils.ParamUtils;
import com.tcxhb.mizar.core.constants.enums.OnlineStatus;
import com.tcxhb.mizar.core.service.biz.MachineService;
import com.tcxhb.mizar.core.service.biz.AppService;
import com.tcxhb.mizar.core.service.biz.FlowRuleService;
import com.tcxhb.mizar.dao.dataobject.MachineDO;
import com.tcxhb.mizar.dao.dataobject.AppDO;
import com.tcxhb.mizar.dao.dataobject.query.FlowRuleQuery;
import com.tcxhb.mizar.dao.repository.MetricsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/10/31
 * 对外提供的API
 */
@RestController
@RequestMapping("/api/agent")
@Slf4j
public class AgentApiController {
    @Autowired
    private MachineService machineService;
    @Autowired
    private FlowRuleService flowRuleService;
    @Autowired
    private AppService appService;

    @Autowired
    private MetricsRepository metricsRepository;

    @GetMapping(value = "/beat")
    public MiscResult<Long> beat(@RequestParam(value = "host", required = false) String host,
                                 @RequestParam(value = "appName", required = false) String appName,
                                 @RequestParam(value = "session", required = false) String session,
                                 @RequestParam(value = "port", required = false) Integer port
    ) {
        ParamUtils.notEmpty(host, "host is null");
        ParamUtils.notEmpty(appName, "app is null");
        ParamUtils.notNull(port, "port is null");
        //心跳注册
        MachineDO machineDO = new MachineDO();
        machineDO.setIp(host);
        machineDO.setPort(port);
        machineDO.setAppName(appName);
        machineDO.setBeatTime(new Date());
        machineDO.setStatus(OnlineStatus.online.getType());
        AppDO appDO = appService.queryByAppName(appName, true);
        if (appDO == null) {
            return MiscResult.err("1001", "应用不存在");
        }
        //返回
        machineService.beat(machineDO);
        return MiscResult.suc(appDO.getVersion());
    }

    @GetMapping(value = "/offline")
    public MiscResult<Boolean> offline(@RequestParam(value = "host") String host,
                                       @RequestParam(value = "appName") String appName,
                                       @RequestParam(value = "session", required = false) String session,
                                       @RequestParam(value = "port") Integer port
    ) {
        ParamUtils.notEmpty(host, "host is null");
        ParamUtils.notEmpty(appName, "app is null");
        ParamUtils.notNull(port, "port is null");
        log.info("offline,agent:{},{},{}", host, appName, port);
        MachineDO machineDO = new MachineDO();
        machineDO.setIp(host);
        machineDO.setPort(port);
        machineDO.setAppName(appName);
        machineDO.setStatus(OnlineStatus.offline.getType());
        //返回
        Boolean result = machineService.updateByHost(machineDO);
        return MiscResult.suc(result);
    }

    @GetMapping(value = "/getConfig")
    public MiscResult<FlowRuleConfig> getConfig(@RequestParam(value = "appName") String appName) {
        FlowRuleQuery query = new FlowRuleQuery();
        query.setAppName(appName);
        FlowRuleConfig config = flowRuleService.queryAgentRules(appName);
        return MiscResult.suc(config);
    }

    @GetMapping(value = "/test")
    public MiscResult<Boolean> test() {
        //metricsRepository.deleteHistory(7);
        return MiscResult.suc(true);
    }
}

