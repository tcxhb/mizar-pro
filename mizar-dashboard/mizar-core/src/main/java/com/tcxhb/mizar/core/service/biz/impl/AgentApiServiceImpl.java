package com.tcxhb.mizar.core.service.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.constants.ApiConstants;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.biz.MetricNode;
import com.tcxhb.mizar.common.service.HttpService;
import com.tcxhb.mizar.common.service.impl.HttpServiceImpl;
import com.tcxhb.mizar.common.utils.JsonUtils;
import com.tcxhb.mizar.core.service.biz.AgentApiService;
import com.tcxhb.mizar.core.service.biz.AppService;
import com.tcxhb.mizar.core.service.biz.MachineService;
import com.tcxhb.mizar.dao.dataobject.MachineDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
@Component
@Slf4j
public class AgentApiServiceImpl implements AgentApiService {
    @Autowired
    private HttpClient client;
    private HttpService httpService;
    @Autowired
    private MachineService machineService;

    private HttpService getHttp() {
        if (httpService == null) {
            httpService = new HttpServiceImpl(client);
        }
        return httpService;
    }


    @Override
    public MiscResult<Boolean> beat(String host, Integer port, String cmd) {
        try {
            String url = getAgent(host, port, cmd);
            String str = getHttp().get(url, null, null);
            return JsonUtils.toBean(str, MiscResult.class, Boolean.class);
        } catch (Exception e) {
            log.error("agent-beat-exp:" + host, e);
            return MiscResult.err(CommonErrorCode.HTTP_TO_AGENT_FAIL);
        }
    }

    @Override
    public Boolean refreshConfig(String appName) {
        List<MachineDO> list = machineService.getOnlineByCache(appName);
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        for (MachineDO machineDO : list) {
            try {
                String url = getAgent(machineDO.getIp(), machineDO.getPort(), ApiConstants.cmd_refresh_config);
                String str = getHttp().get(url, null, null);
                MiscResult<Boolean> result = JsonUtils.toBean(str, MiscResult.class, Boolean.class);
            } catch (Exception e) {
                log.error("refreshConfig-exp:" + machineDO.httpAddress(), e);
            }
        }
        return true;
    }

    /**
     * @param host
     * @param port
     * @param cmd
     * @return
     */
    private String getAgent(String host, Integer port, String cmd) {
        StringBuilder builder = new StringBuilder("http://");
        builder.append(host).append(":").append(port).append("/");
        builder.append(cmd);
        return builder.toString();
    }

}
