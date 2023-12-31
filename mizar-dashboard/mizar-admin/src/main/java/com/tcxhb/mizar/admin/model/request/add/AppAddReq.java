package com.tcxhb.mizar.admin.model.request.add;

import com.tcxhb.mizar.common.utils.ParamUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * author:auto.generator
 * time: 2023-11-02
 */
@Data
public class AppAddReq implements Serializable {
    private Long id;
    private String appName;

    private String name;

    public void check() {
        ParamUtils.notNull("appname", "appName不能为空");
        ParamUtils.notNull("name", "应用名称不能为空");
    }
}
