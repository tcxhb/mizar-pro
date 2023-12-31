package com.tcxhb.mizar.core.service.biz.impl;

import com.tcxhb.mizar.dao.dataobject.MachineDO;
import com.tcxhb.mizar.dao.dataobject.query.MachineQuery;
import com.tcxhb.mizar.dao.repository.MachineRepository;
import com.tcxhb.mizar.common.utils.LocalCache;
import com.tcxhb.mizar.core.constants.enums.OnlineStatus;
import com.tcxhb.mizar.core.service.biz.MachineService;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.common.utils.ParamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * author:auto.generator
 * time: 2023-11-05
 */
@Service
@Slf4j
public class MachineServiceImpl implements MachineService {
    @Resource
    private MachineRepository repository;
    private LocalCache<List<MachineDO>> cache = new LocalCache<List<MachineDO>>(30000L);

    @Override
    public boolean beat(MachineDO machineDO) {
        //应用不存在就插入数据
        MachineDO exit = queryByHost(machineDO.getIp(), machineDO.getPort(), machineDO.getAppName());
        if (exit == null) {
            repository.create(machineDO);
            cleanCache(machineDO.getAppName());
            return true;
        }
        //不在线就要清理缓存
        if (!exit.online()) {
            cleanCache(machineDO.getAppName());
        }
        //更新数据
        MachineDO update = new MachineDO();
        update.setId(machineDO.getId());
        update.setStatus(machineDO.getStatus());
        update.setBeatTime(machineDO.getBeatTime());
        return repository.updateById(machineDO);
    }

    @Override
    public boolean deleteById(Long id) {
        ParamUtils.notNull(id, "id不能为空");
        return repository.deleteById(id);
    }

    @Override
    public boolean updateById(MachineDO tcJobMachine) {
        return repository.updateById(tcJobMachine);
    }

    @Override
    public MachineDO queryById(Long id) {
        ParamUtils.notNull(id, "id不能为空");
        return repository.queryById(id);
    }

    @Override
    public PageResponse<MachineDO> page(MachineQuery query) {
        return repository.page(query);
    }

    @Override
    public List<MachineDO> list(MachineQuery query) {
        return repository.list(query);
    }

    @Override
    public boolean updateByHost(MachineDO machineDO) {
        return repository.updateByHost(machineDO);
    }

    @Override
    public MachineDO queryByHost(String host, Integer port, String appName) {
        List<MachineDO> cacheList = listByCache(appName);
        if (CollectionUtils.isEmpty(cacheList)) {
            return null;
        }
        for (MachineDO machineDO : cacheList) {
            if (machineDO.eq(host, port)) {
                return machineDO;
            }
        }
        return null;
    }

    @Override
    public List<MachineDO> getOnlineByCache(String appName) {
        List<MachineDO> list = listByCache(appName);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        //在线设备
        List<MachineDO> result = new ArrayList<>();
        for (MachineDO machineDO : list) {
            if (OnlineStatus.online.eq(machineDO.getStatus())) {
                result.add(machineDO);
            }
        }
        return result;
    }

    @Override
    public void cleanCache(String app) {
        cache.clean(app);
    }

    /**
     * @param appName
     * @return
     */
    public List<MachineDO> listByCache(String appName) {
        List<MachineDO> list = cache.get(appName);
        if (list != null) {
            return list;
        }
        MachineQuery query = new MachineQuery();
        query.setAppName(appName);
        list = repository.list(query);
        cache.put(appName, list);
        return list;
    }
}
