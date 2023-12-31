package com.tcxhb.mizar.core.schedule.worker;

import com.tcxhb.mizar.common.utils.LocalCache;
import com.tcxhb.mizar.core.constants.enums.OnlineStatus;
import com.tcxhb.mizar.core.schedule.ServerExecutorPool;
import com.tcxhb.mizar.core.service.biz.MachineService;
import com.tcxhb.mizar.dao.dataobject.MachineDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 处理机器状态的woker
 * @Auther: tcxhb
 * @Date: 2023/12/17
 */
@Component
public class MachineStatusWorker {
    @Autowired
    private MachineService machineService;
    @Autowired
    private ServerExecutorPool executorPool;
    private LocalCache<Integer> failCount = new LocalCache<Integer>(20000L);

    // 连续3次失败 就下线
    public void work(MachineDO machineDO) {
        String key = machineDO.httpAddress();
        Integer count = failCount.get(key);
        count = count == null ? 0 : count;
        if (count < 2) {
            failCount.put(key, ++count);
            return;
        }
        //在线改成离线
        updateStatus(machineDO);
    }

    private void updateStatus(MachineDO machineDO) {
        executorPool.submit(() -> {
            //更新状态
            MachineDO updateDO = new MachineDO();
            updateDO.setId(machineDO.getId());
            updateDO.setStatus(OnlineStatus.offline.getType());
            machineService.updateById(updateDO);
            machineService.cleanCache(machineDO.getAppName());
        });
    }
}
