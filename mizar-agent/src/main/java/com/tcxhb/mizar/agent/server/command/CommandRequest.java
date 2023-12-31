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

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Command request representation of command center.
 *
 * @author Eric Zhao
 */
public class CommandRequest {
    private String cmdPath;
    private final Map<String, String> metadata = new HashMap<String, String>();
    private final Map<String, String> parameters = new HashMap<String, String>();
    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public CommandRequest setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParam(String key) {
        return parameters.get(key);
    }

    public String getParam(String key, String defaultValue) {
        String value = parameters.get(key);
        return StringUtils.isBlank(value) ? defaultValue : value;
    }

    public CommandRequest addParam(String key, String value) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("Parameter key cannot be empty");
        }
        parameters.put(key, value);
        return this;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public CommandRequest addMetadata(String key, String value) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("Metadata key cannot be empty");
        }
        metadata.put(key, value);
        return this;
    }

    public String getCmdPath() {
        return this.cmdPath;
    }
    public void setCmdPath(String cmdPath){
        this.cmdPath = cmdPath;
    }
}
