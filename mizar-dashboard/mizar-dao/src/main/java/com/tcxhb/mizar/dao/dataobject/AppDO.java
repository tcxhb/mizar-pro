package com.tcxhb.mizar.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * author:auto.generator
 * time: 2023-11-02
 */

@TableName(value = "mz_app", autoResultMap = true)
@Data
public class AppDO extends BaseDO {
    /**
     * *
     **/
    private String appName;

    /**
     * *
     **/
    private String name;
    /**
     * 密钥
     */
    private String appsecret;
    /**
     * 版本
     */
    private Long version;
}
