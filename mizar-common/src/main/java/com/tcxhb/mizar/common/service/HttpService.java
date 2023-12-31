package com.tcxhb.mizar.common.service;

import java.util.Map;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
public interface HttpService {

    public String get(String url, Map<String, ? extends Object> params, Map<String, String> header);

    public String post(String url,  Map<String, ? extends Object> params, Map<String, String> header);
}
