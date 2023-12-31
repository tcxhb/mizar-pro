/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tcxhb.mizar.agent.server.command;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Util class for HTTP command center.
 *
 * @author Eric Zhao
 */
@Slf4j
public final class CommandRequestUtils {

    public static CommandRequest parseRequest(FullHttpRequest request) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        CommandRequest serverRequest = new CommandRequest();
        Map<String, List<String>> paramMap = queryStringDecoder.parameters();
        // Parse request parameters.
        if (!paramMap.isEmpty()) {
            for (Map.Entry<String, List<String>> p : paramMap.entrySet()) {
                if (!p.getValue().isEmpty()) {
                    serverRequest.addParam(p.getKey(), p.getValue().get(0));
                }
            }
        }
        // Deal with post method, parameter in post has more privilege compared to that in querystring
        if (request.method().equals(HttpMethod.POST)) {
            // support multi-part and form-urlencoded
            HttpPostRequestDecoder postRequestDecoder = null;
            try {
                postRequestDecoder = new HttpPostRequestDecoder(request);
                for (InterfaceHttpData data : postRequestDecoder.getBodyHttpDatas()) {
                    data.retain(); // must retain each attr before destroy
                    if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                        if (data instanceof HttpData) {
                            HttpData httpData = (HttpData) data;
                            try {
                                String name = httpData.getName();
                                String value = httpData.getString();
                                serverRequest.addParam(name, value);
                            } catch (IOException e) {
                                log.error("parase-request-ext",e);
                            }
                        }
                    }
                }
            } finally {
                if (postRequestDecoder != null) {
                    postRequestDecoder.destroy();
                }
            }
        }
        // Parse command name.
        serverRequest.setCmdPath(queryStringDecoder.rawPath());
        // Parse body.
        if (request.content().readableBytes() <= 0) {
            serverRequest.setBody(null);
        } else {
            byte[] body = new byte[request.content().readableBytes()];
            request.content().getBytes(0, body);
            serverRequest.setBody(body);
        }
        return serverRequest;
    }

}
