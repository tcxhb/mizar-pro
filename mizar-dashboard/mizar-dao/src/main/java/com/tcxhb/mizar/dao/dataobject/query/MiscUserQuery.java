package com.tcxhb.mizar.dao.dataobject.query;


import com.tcxhb.mizar.common.model.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * author:auto.generator
 * time: 2023-09-20
 */
@Data
public class MiscUserQuery implements Serializable {

    private Long id;

    private PageRequest page;

    private String name;
    private String username;
    private String appName;
}
