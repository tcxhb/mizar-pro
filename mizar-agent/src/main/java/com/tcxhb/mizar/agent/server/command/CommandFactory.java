package com.tcxhb.mizar.agent.server.command;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
@Component
public class CommandFactory implements ApplicationContextAware, SmartInitializingSingleton {
    private ApplicationContext context;
    public final static Map<String, CommandHandler> handlerMap = new ConcurrentHashMap<String, CommandHandler>();

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beans = this.context.getBeansWithAnnotation(CommandMapping.class);
        if (beans == null) {
            return;
        }
        for (Object bean : beans.values()) {
            CommandMapping command = bean.getClass().getAnnotation(CommandMapping.class);
            handlerMap.put(command.name(), (CommandHandler) bean);
        }
        return;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
