package com.tcxhb.mizar.core.service.biz;

import com.tcxhb.mizar.common.model.biz.MetricNode;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.dao.dataobject.MachineDO;

import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
public interface AgentApiService {
    /**
     * 心跳
     *
     * @return
     */
    public MiscResult<Boolean> beat(String host, Integer port, String cmd);

    /**
     * @param appName
     * @return
     */
    public Boolean refreshConfig(String appName);
}
