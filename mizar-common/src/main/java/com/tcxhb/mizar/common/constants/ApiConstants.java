package com.tcxhb.mizar.common.constants;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/13
 */
public interface ApiConstants {

    public final String api_beat = "/api/agent/beat";
    public final String api_offline = "/api/agent/offline";
    public final String api_config = "/api/agent/getConfig";
    //服务器的心跳
    public final String api_server_beat = "/api/server/beat";

    public final String cmd_query_qps = "/cmd/metric/qps";
    public final String cmd_refresh_config = "/cmd/refresh/rules";
}
