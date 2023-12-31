package com.tcxhb.mizar.dao.dataobject.query;

import com.tcxhb.mizar.common.model.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * author:auto.generator
 * time: 2023-12-20
 */
@Data
public class MetricsQuery implements Serializable {
    private Long id;
    private String app;
    private String resource;
    private Long startTime;
    private Long endTime;
    private PageRequest page;
}
