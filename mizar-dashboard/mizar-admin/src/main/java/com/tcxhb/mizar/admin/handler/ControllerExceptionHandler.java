package com.tcxhb.mizar.admin.handler;

import com.alibaba.fastjson.JSON;
import com.tcxhb.mizar.common.constants.BaseException;
import com.tcxhb.mizar.common.constants.BlockException;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.model.MiscResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/9/21
 */
@Configuration
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {


    @ExceptionHandler(value = BaseException.class)
    public void handleServiceException(Exception exception, HttpServletRequest request,
                                       HttpServletResponse response) {
        log.error("error", exception);
        BaseException e = (BaseException) exception;
        MiscResult result = new MiscResult();
        result.setMsg(e.getMsg());
        result.setCode(e.getCode());
        result.setData(false);
        responseResult(response, result);
    }

    @ExceptionHandler(value = BlockException.class)
    public void handleServiceException(BlockException exception, HttpServletRequest request,
                                       HttpServletResponse response) {
        log.error("BlockException", exception.getMessage());
        BlockException e = (BlockException) exception;
        MiscResult result = MiscResult.err(CommonErrorCode.BLOCK_REQUEST);
        result.setData(e.getMessage());
        responseResult(response, result);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception e, HttpServletRequest request,
                                HttpServletResponse response) {
        MiscResult result = MiscResult.err(CommonErrorCode.BAD_REQUEST, "内部异常");
        log.error("error:" + request.getRequestURI(), e);
        responseResult(response, result);
    }

    private void responseResult(HttpServletResponse response, MiscResult result) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (Exception e) {
            log.error("response error", e);
        }
    }
}
