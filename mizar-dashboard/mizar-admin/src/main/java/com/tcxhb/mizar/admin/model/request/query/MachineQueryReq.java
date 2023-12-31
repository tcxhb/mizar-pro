package com.tcxhb.mizar.admin.model.request.query;

import com.tcxhb.mizar.admin.model.request.BaseAppReq;
import lombok.Data;

import java.io.Serializable;

@Data
public class MachineQueryReq extends BaseAppReq implements Serializable {

    private Long id;
    private Integer status;
    private String ip;
}
