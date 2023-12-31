package com.tcxhb.mizar.core.service.biz.impl;

import com.tcxhb.mizar.common.utils.LocalCache;
import com.tcxhb.mizar.core.schedule.ServerExecutorPool;
import com.tcxhb.mizar.core.service.biz.AgentApiService;
import com.tcxhb.mizar.dao.dataobject.AppDO;
import com.tcxhb.mizar.dao.dataobject.query.AppQuery;
import com.tcxhb.mizar.dao.repository.AppRepository;
import com.tcxhb.mizar.core.service.biz.AppService;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.common.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * author:auto.generator
 * time: 2023-11-02
 */
@Service
public class AppServiceImpl implements AppService {
    @Resource
    private AppRepository repository;
    private LocalCache<List<String>> allApp = new LocalCache<>();
    private LocalCache<AppDO> cache = new LocalCache<AppDO>(1000L * 60);

    @Autowired
    private ServerExecutorPool executorPool;
    @Autowired
    private AgentApiService agentApiService;

    @Override
    public Long create(AppDO appDO) {
        String uuid = UUID.randomUUID().toString();
        String secret = uuid.replace("-", "");
        appDO.setAppsecret(secret);
        appDO.setVersion(0L);
        return repository.create(appDO);
    }

    @Override
    public boolean deleteById(Long id) {
        ParamUtils.notNull(id, "id不能为空");
        return repository.deleteById(id);
    }

    @Override
    public boolean updateById(AppDO wjApp) {
        return repository.updateById(wjApp);
    }

    @Override
    public AppDO queryById(Long id) {
        ParamUtils.notNull(id, "id不能为空");
        return repository.queryById(id);
    }

    @Override
    public PageResponse<AppDO> page(AppQuery query) {
        return repository.page(query);
    }

    @Override
    public List<AppDO> list(AppQuery query) {
        return repository.list(query);
    }

    @Override
    public AppDO queryByAppName(String appName, Boolean useCache) {
        if (Boolean.TRUE.equals(useCache)) {
            return cache.get(appName, repository::queryByAppName);
        }
        //不使用缓存
        AppDO appDO = repository.queryByAppName(appName);
        cache.put(appName, appDO);
        return appDO;
    }

    @Override
    public boolean updateVersion(String appName, Long version) {
        AppDO appDO = new AppDO();
        appDO.setAppName(appName);
        appDO.setVersion(version);
        cleanCache(appName);
        Boolean result = repository.updateByAppName(appDO);
        //更新客户端
        executorPool.submit(() -> {
            agentApiService.refreshConfig(appName);
        });
        return result;
    }


    @Override
    public List<String> allApp() {
        List<String> all = allApp.get("al");
        if (all != null) {
            return all;
        }
        List<AppDO> appDOS = repository.list(new AppQuery());
        if (CollectionUtils.isEmpty(appDOS)) {
            return new ArrayList<>();
        }
        List<String> result = appDOS.stream().map(m -> m.getAppName()).collect(Collectors.toList());
        allApp.put("al", result);
        return result;
    }

    @Override
    public void cleanCache(String app) {
        cache.clean(app);
    }
}
