package com.tcxhb.mizar.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageParam implements Serializable {
    private Integer pageSize = 20;
    private Integer pageNum;

    public PageParam(){
        this.pageNum = 1;
    }

    public PageParam(int pageNum,int pageSize){
        this.pageNum = 1;
    }
}
