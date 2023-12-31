package com.tcxhb.mizar.agent.constants;

import com.tcxhb.mizar.common.constants.ServiceError;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/15
 */
public enum AgentErrorCode implements ServiceError {
    AGENT_HTTP_TO_SERVER_FAIL("AGENT_HTTP_TO_SERVER_FAIL", "请求中心服务器异常"),
    SUCCESS("200", "成功");

    private String msg;
    private String code;

    AgentErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
