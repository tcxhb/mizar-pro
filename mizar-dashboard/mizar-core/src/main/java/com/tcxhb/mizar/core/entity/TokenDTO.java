package com.tcxhb.mizar.core.entity;

import lombok.Data;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/9/21
 */
@Data
public class TokenDTO {
    /**
     * *
     **/
    private String token;

    /**
     * *
     **/
    private Long userid;

    /**
     * *
     **/
    private String username;

    /**
     * *
     **/
    private Integer type;
}
