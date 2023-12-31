package com.tcxhb.mizar.admin.model.request.query;

import com.tcxhb.mizar.admin.model.request.BaseAppReq;
import com.tcxhb.mizar.common.utils.ParamUtils;
import lombok.Data;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/10
 */
@Data
public class MetricQueryReq extends BaseAppReq {
    private Boolean desc = true;
    private Long startTime;
    private Long endTime;


    private String resource;
    public void check() {
        ParamUtils.notEmpty(getAppName(),"appName is null");
        if (endTime == null) {
            endTime = System.currentTimeMillis();
        }
        if (startTime == null) {
            startTime = endTime - 1000 * 60 * 5;
        }
    }
}
