package com.tcxhb.mizar.core.service;

import javax.servlet.ServletResponse;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/4/3
 */
public interface ResponseService {
    public void returnJson(ServletResponse response, Object obj);
}
