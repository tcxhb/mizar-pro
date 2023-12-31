package com.tcxhb.mizar.admin.model.request;

import com.tcxhb.mizar.common.utils.ParamUtils;
import com.tcxhb.mizar.core.utils.PasswordValidator;
import lombok.Data;

import java.io.Serializable;

/**
 * author:auto.generator
 * time: 2023-09-20
 */
@Data
public class PasswordRestReq extends BaseReq implements Serializable {

    private Long id;

    private String password;

    @Override
    public void check() {
        super.check();
        ParamUtils.notNull(id, "password不能为空");
        ParamUtils.notNull(password, "password不能为空");
        //密码校验
        PasswordValidator.ValidResult result = PasswordValidator.validPwd(password);
        ParamUtils.isTrue(result.isValid(), result.getMsg());
    }
}
