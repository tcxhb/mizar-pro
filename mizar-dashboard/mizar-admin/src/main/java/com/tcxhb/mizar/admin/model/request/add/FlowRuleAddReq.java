package com.tcxhb.mizar.admin.model.request.add;

import com.tcxhb.mizar.common.utils.ParamUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * author:auto.generator
 * time: 2023-12-14
 */
@Data
public class FlowRuleAddReq implements Serializable {

    private Integer id;


    private String appName;

    private String resourceCode;


    private String flowType;


    private String flowAction;

    /**
     * 限流值
     */
    private Long flowValue;


    private String feature;

    public void check() {
        ParamUtils.notEmpty(flowAction, "limitAction不能为空");
        ParamUtils.notEmpty(flowType, "flowType不能为空");
        ParamUtils.notEmpty(resourceCode, "资源名称不能为空");
        ParamUtils.notNull(flowValue, "阈值不能为空");
        this.setResourceCode(resourceCode.trim());
    }

}
