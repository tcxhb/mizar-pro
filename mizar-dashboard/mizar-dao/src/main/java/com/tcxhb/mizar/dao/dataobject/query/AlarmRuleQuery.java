package com.tcxhb.mizar.dao.dataobject.query;


import com.tcxhb.mizar.common.model.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * author:auto.generator
 * time: 2023-12-29
 */
@Data
public class AlarmRuleQuery implements Serializable {

    private Integer status;
    private PageRequest page;
    private String resource;
}
