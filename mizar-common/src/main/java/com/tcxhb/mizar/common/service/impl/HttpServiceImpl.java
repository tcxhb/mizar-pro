package com.tcxhb.mizar.common.service.impl;

import com.tcxhb.mizar.common.constants.BaseException;
import com.tcxhb.mizar.common.service.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
@Slf4j
public class HttpServiceImpl implements HttpService {
    private HttpClient client = null;

    public HttpServiceImpl(HttpClient client) {
        this.client = client;
    }

    public String get(String url, Map<String, ? extends Object> params,
                      Map<String, String> header) {
        //拼接参数
        url = url + getParams(params);
        HttpGet get = new HttpGet(url);
        addHead(get, header);
        try {
            HttpResponse response = null;
            response = client.execute(get);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new BaseException("1001", "httpGet fail:" + url);
        }
    }

    public void addHead(HttpRequestBase requestBase, Map<String, String> header) {
        if (header == null) {
            return;
        }
        for (String key : header.keySet()) {
            requestBase.setHeader(key, header.get(key));
        }
    }

    @Override
    public String post(String url, Map<String, ? extends Object> params, Map<String, String> header) {
        HttpPost post = new HttpPost(url);
        addHead(post, header);
        try {
            List<NameValuePair> list = new ArrayList<>(params.size());
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }

            post.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
            HttpResponse response = null;
            response = client.execute(post);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            log.error("post-agent-exp:" + url, e);
            throw new BaseException("1001", "post-agent-exp:" + url);
        }
    }

    public static String getParams(Map<String, ? extends Object> params) {
        if (params == null || params.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer("?");
        for (Map.Entry<String, ? extends Object> item : params.entrySet()) {
            Object value = item.getValue();
            if (value != null) {
                sb.append("&");
                sb.append(item.getKey());
                sb.append("=");
                sb.append(value);
            }
        }
        return sb.toString();
    }
}
