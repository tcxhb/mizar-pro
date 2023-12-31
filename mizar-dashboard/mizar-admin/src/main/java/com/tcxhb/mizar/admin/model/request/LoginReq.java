package com.tcxhb.mizar.admin.model.request;

import com.tcxhb.mizar.common.utils.ParamUtils;
import lombok.Data;
/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/3/31
 */
@Data
public class LoginReq {
    private String username;
    private String password;
    /**
     * 没有用的
     */
    private String token;

    public void check(){
        ParamUtils.notEmpty(username,"username不能为空");
        ParamUtils.notEmpty(username,"password不能为空");
    }
}
