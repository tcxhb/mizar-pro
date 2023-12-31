package com.tcxhb.mizar.dao.dataobject.query;


import com.tcxhb.mizar.common.model.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * author:auto.generator
 * time: 2023-11-05
 */
@Data
public class MachineQuery implements Serializable {
    private Long id;
    private String ip;
    private String appName;
    private PageRequest page;
    private Integer port;
    private Integer status;
    // 更新时间
    private Long lastId;
    /**
     * 更新时间起始
     */
    private Date beatTimeStart;
    /**
     * 更新时间结束
     */
    private Date beatTimeEnd;
    /**
     * 排序字段
     */
    private String ascKey;
}
