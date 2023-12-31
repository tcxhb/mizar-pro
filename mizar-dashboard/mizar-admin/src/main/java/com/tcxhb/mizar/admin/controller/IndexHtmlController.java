package com.tcxhb.mizar.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/23
 */
@Controller
public class IndexHtmlController {
    @RequestMapping(value = "/index")
    public String screen(HttpServletRequest req) {
        return "/static/index.html";
    }
}
