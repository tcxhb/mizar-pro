package com.tcxhb.mizar.agent.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tcxhb.mizar.agent.constants.AgentConfig;
import com.tcxhb.mizar.agent.constants.AgentErrorCode;
import com.tcxhb.mizar.agent.service.ServerCenterApi;
import com.tcxhb.mizar.common.constants.ApiConstants;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.biz.FlowRuleConfig;
import com.tcxhb.mizar.common.service.HttpService;
import com.tcxhb.mizar.common.service.impl.HttpServiceImpl;
import com.tcxhb.mizar.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 服务器的请求服务
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
@Service
@Slf4j
public class ServerCenterApiImpl implements ServerCenterApi {
    @Autowired
    private AgentConfig agentConfig;
    public HttpService httpService;

    public ServerCenterApiImpl() {
        HttpClient client = HttpClients.createDefault();
        httpService = new HttpServiceImpl(client);
    }

    @Override
    public MiscResult<Long> register(String ip, Integer port, String appName) {
        try {
            JSONObject params = new JSONObject();
            params.put("host", ip);
            params.put("port", port);
            params.put("appName", appName);
            String url = getUrl(ApiConstants.api_beat);
            String str = httpService.get(url, params, null);
            return JsonUtils.toMiscBean(str, Long.class);
        } catch (Exception e) {
            log.error("agent-register exception:" + appName + ",ip:" + ip + "," + port);
            throw new RuntimeException(AgentErrorCode.AGENT_HTTP_TO_SERVER_FAIL.getCode(), e);
        }
    }

    @Override
    public MiscResult<Boolean> offline(String ip, Integer port, String appName) {
        try {
            JSONObject params = new JSONObject();
            params.put("host", ip);
            params.put("port", port);
            params.put("appName", appName);
            String url = getUrl(ApiConstants.api_offline);
            String str = httpService.get(url, params, null);
            return JsonUtils.toMiscBean(str, Boolean.class);
        } catch (Exception e) {
            log.error("agent-offline exception:" + appName + ",ip:" + ip + "," + port);
            return MiscResult.err(AgentErrorCode.AGENT_HTTP_TO_SERVER_FAIL);
        }
    }


    @Override
    public MiscResult<FlowRuleConfig> queryFlowRules(String appname) {
        try {
            JSONObject params = new JSONObject();
            params.put("appName", appname);
            String url = getUrl(ApiConstants.api_config);
            String str = httpService.get(url, params, null);
            return JsonUtils.toMiscBean(str, FlowRuleConfig.class);
        } catch (Exception e) {
            log.error("flowRules-http-exp:" + appname);
            return MiscResult.err(AgentErrorCode.AGENT_HTTP_TO_SERVER_FAIL);
        }
    }

    private String getUrl(String path) {
        return agentConfig.getServerHost() + path;
    }
}
