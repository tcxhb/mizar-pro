package com.tcxhb.mizar.core.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/13
 */
@Configuration
public class HttpClientConfig {

    private IOReactorConfig ioConfig = IOReactorConfig.custom()
            .setConnectTimeout(3000)
            .setSoTimeout(3000)
            .setIoThreadCount(Runtime.getRuntime().availableProcessors() * 2)
            .build();

    @ConditionalOnMissingBean
    @Bean
    public CloseableHttpAsyncClient asyncClient() {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom()
                .setRedirectStrategy(new DefaultRedirectStrategy() {
                    @Override
                    protected boolean isRedirectable(final String method) {
                        return false;
                    }
                }).setMaxConnTotal(4000)
                .setMaxConnPerRoute(1000)
                .setDefaultIOReactorConfig(ioConfig)
                .build();
        httpclient.start();
        return httpclient;
    }
    @Bean
    @ConditionalOnMissingBean
    public HttpClient httpClient(){
        HttpClient client = HttpClients.createDefault();
        return client;
    }
}
