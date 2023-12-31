package com.tcxhb.mizar.admin.model.request;


import lombok.Data;

@Data
public class UserQueryReq extends BaseAppReq {
    private Long id;
    private String name;
    private String username;
}
