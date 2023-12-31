package com.tcxhb.mizar.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.tcxhb.mizar.core.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/4/3
 */
@Slf4j
@Component
public class ResponseServiceImpl implements ResponseService {
    @Override
    public void returnJson(ServletResponse res, Object object) {
        HttpServletResponse response = (HttpServletResponse) res;
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-token,x-requested-with,content-type");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(object));
        } catch (Exception e) {
            log.error("response error", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
