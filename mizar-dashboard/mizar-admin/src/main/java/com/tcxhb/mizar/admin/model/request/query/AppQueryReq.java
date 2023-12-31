package com.tcxhb.mizar.admin.model.request.query;


import com.tcxhb.mizar.admin.model.request.BaseAppReq;
import com.tcxhb.mizar.common.model.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppQueryReq extends BaseAppReq implements Serializable {

    private Long id;
    private String appName;
}
