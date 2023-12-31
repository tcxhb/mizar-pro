package com.tcxhb.mizar.admin.model.request;

import com.tcxhb.mizar.core.constants.enums.UserTypeEnum;
import com.tcxhb.mizar.common.utils.ParamUtils;
import com.tcxhb.mizar.core.utils.PasswordValidator;
import lombok.Data;

import java.io.Serializable;

/**
 * author:auto.generator
 * time: 2023-09-20
 */
@Data
public class UserAddReq extends BaseReq implements Serializable {

    private Long id;
    private String username;
    private String phone;
    private String name;
    private Integer type;
    private String appName;
    private String password;
    private String email;

    @Override
    public void check() {
        super.check();
        ParamUtils.notNull(username, "username不能为空");
        ParamUtils.notNull(name, "name不能为空");
        ParamUtils.isTrue(username.length() >= 5, "用户名长度大于5");
        //密码校验
        if (id != null) {
            ParamUtils.notNull(password, "password不能为空");
            PasswordValidator.ValidResult result = PasswordValidator.validPwd(password);
            ParamUtils.isTrue(result.isValid(), result.getMsg());
        }
        //超级管理员
        if (UserTypeEnum.NORMAL.getType().equals(type)) {
            ParamUtils.notEmpty(appName, "appName必填");
        }
    }
}
