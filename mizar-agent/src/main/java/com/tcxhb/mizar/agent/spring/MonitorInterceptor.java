package com.tcxhb.mizar.agent.spring;

import com.tcxhb.mizar.agent.statistic.Context;
import com.tcxhb.mizar.agent.statistic.workflow.MonitorHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/2
 */
@Component
@Slf4j
public class MonitorInterceptor implements HandlerInterceptor {
    public static final String contextAttributeName = "mizar-context-attr";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String resourceName = getResourceName(request);
        if (StringUtils.isBlank(resourceName)) {
            return true;
        }
        //会抛出限流错误
        Context context = MonitorHolder.entry(resourceName);
        request.setAttribute(contextAttributeName, context);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        Context context = (Context) request.getAttribute(contextAttributeName);
        if (context == null) {
            return;
        }
        context.setError(ex);
        MonitorHolder.exit(context);
    }

    /**
     * 获取资源名称
     *
     * @param request
     * @return
     */
    protected String getResourceName(HttpServletRequest request) {
        Object resourceNameObject = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        if (resourceNameObject == null || !(resourceNameObject instanceof String)) {
            return null;
        }
        return (String) resourceNameObject;
    }
}
