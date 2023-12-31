package com.tcxhb.mizar.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * liuyulong
 * 2022/6/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageRequest {

    private Integer pageSize = 20;
    private Integer pageNum = 1;

}
