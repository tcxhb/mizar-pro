package com.tcxhb.mizar.dao.dataobject.query;

import com.tcxhb.mizar.common.model.PageRequest;
import lombok.Data;

import java.io.Serializable;
/**
* author:auto.generator
* time: 2023-12-14
*/
@Data
public class FlowRuleQuery implements Serializable {

    private Long id;

    private PageRequest page;

    private String resourceCode;

    private String appName;

    private String flowAction;
}
