package com.tcxhb.mizar.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * author:auto.generator
 * time: 2023-09-21
 */

@TableName(value = "mz_token", autoResultMap = true)
@Data
public class MiscTokenDO extends BaseDO{
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
    private String type;

    /**
     * *
     **/
    private Long expireTime;
}
