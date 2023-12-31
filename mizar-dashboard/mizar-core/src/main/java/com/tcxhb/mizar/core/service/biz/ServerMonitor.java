package com.tcxhb.mizar.core.service.biz;

import com.tcxhb.mizar.core.entity.ServerNode;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/20
 */
public interface ServerMonitor {



    /**
     * 节点下线
     * @return
     */
    public Boolean offline();

    /**
     * 获取当前服务器节点信息
     *
     * @return
     */
    public ServerNode currentNode();
}
