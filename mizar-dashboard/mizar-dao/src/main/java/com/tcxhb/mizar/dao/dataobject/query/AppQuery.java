package com.tcxhb.mizar.dao.dataobject.query;


import com.tcxhb.mizar.common.model.PageRequest;
import lombok.Data;

import java.io.Serializable;
/**
* author:auto.generator
* time: 2023-11-02
*/
@Data
public class AppQuery implements Serializable {

    private Long id;
    private String appName;
    private PageRequest page;
}
