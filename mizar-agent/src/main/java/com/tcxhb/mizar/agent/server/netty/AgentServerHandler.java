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
package com.tcxhb.mizar.agent.server.netty;

import com.tcxhb.mizar.agent.server.command.CommandFactory;
import com.tcxhb.mizar.agent.server.command.CommandHandler;
import com.tcxhb.mizar.agent.server.command.CommandRequest;
import com.tcxhb.mizar.agent.server.command.CommandRequestUtils;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.utils.JsonUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * from sentinel
 */
@Slf4j
public class AgentServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest httpRequest = (FullHttpRequest) msg;
        Boolean keeplive = HttpUtil.isKeepAlive(httpRequest);
        try {
            CommandRequest request = CommandRequestUtils.parseRequest(httpRequest);
            if (StringUtils.isBlank(request.getCmdPath())) {
                writeResponse(MiscResult.err("101", "Invalid command"), ctx, keeplive);
                return;
            }
            handleRequest(request, ctx, keeplive);
        } catch (Exception ex) {
            writeResponse(MiscResult.err("101", SERVER_ERROR_MESSAGE), ctx, keeplive);
            log.warn("Internal error", ex);
        }
    }

    private void handleRequest(CommandRequest request, ChannelHandlerContext ctx, boolean keepAlive)
            throws Exception {
        String commandName = request.getCmdPath();
        // Find the matching command handler.
        CommandHandler<?> commandHandler = getHandler(commandName);
        Object response = commandHandler.handle(request);
        writeResponse(response, ctx, keepAlive);
    }


    private void writeResponse(Object result, ChannelHandlerContext ctx, boolean keepAlive) {
        String message = JsonUtils.toJson(result);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
//        if (keepAlive) {
//            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//        }
        ctx.write(response);
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    private CommandHandler getHandler(String commandName) {
        if (StringUtils.isBlank(commandName)) {
            return null;
        }
        return CommandFactory.handlerMap.get(commandName);
    }

    private static final String SERVER_ERROR_MESSAGE = "Command server error";
}
