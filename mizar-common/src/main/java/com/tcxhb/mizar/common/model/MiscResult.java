package com.tcxhb.mizar.common.model;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/9/9
 */

import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.constants.ServiceError;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class MiscResult<T> {
    private String code;
    private String msg;
    private T data;
    private boolean success;


    public static <T> MiscResult<T> err(ServiceError serviceError, T data) {
        MiscResult<T> tResult = new MiscResult<>();
        tResult.setCode(serviceError.getCode());
        tResult.setMsg(serviceError.getMsg());
        tResult.setData(data);
        return tResult;
    }

    public static <T> MiscResult<T> err(String code,String error) {
        MiscResult<T> tResult = new MiscResult<>();
        tResult.setCode(code);
        tResult.setMsg(error);
        return tResult;
    }


    public static <T> MiscResult<T> err(ServiceError serviceError) {
        return err(serviceError.getCode(),serviceError.getMsg());
    }

    public static <T> MiscResult<T> err(ServiceError serviceError,String error) {
        return err(serviceError.getCode(),error);
    }


    public static <T> MiscResult<T> suc() {
        return suc(null);
    }

    public static <T> MiscResult<T> suc(T data) {
        MiscResult<T> tResult = new MiscResult<>();
        tResult.setCode(CommonErrorCode.SUCCESS.getCode());
        tResult.setMsg(CommonErrorCode.SUCCESS.getMsg());
        tResult.setSuccess(true);
        tResult.setData(data);
        return tResult;
    }
}
