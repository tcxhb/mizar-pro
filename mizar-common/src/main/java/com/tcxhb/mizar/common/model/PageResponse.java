package com.tcxhb.mizar.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResponse<T> implements Serializable {

    private Long total;
    private List<T> list;
}
