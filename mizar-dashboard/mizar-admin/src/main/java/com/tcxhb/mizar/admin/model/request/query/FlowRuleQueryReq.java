package com.tcxhb.mizar.admin.model.request.query;

import com.tcxhb.mizar.common.model.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class FlowRuleQueryReq implements Serializable {

    private Long id;
    private PageRequest page;
    private String resourceCode;
    private String appName;
}
