package com.tcxhb.mizar.common.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {
    private boolean success;
    private String message;
    private String code;

    private T data;

    public static <T> Response<T> success(T data){
        Response response = new Response();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }
}
