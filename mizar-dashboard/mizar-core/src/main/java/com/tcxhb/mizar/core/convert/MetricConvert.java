package com.tcxhb.mizar.core.convert;

import com.tcxhb.mizar.core.entity.MetricEntity;
import com.tcxhb.mizar.dao.dataobject.MetricsDO;
import org.mapstruct.Mapper;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/10
 */
@Mapper(componentModel = "spring")
public interface MetricConvert {

    public static MetricEntity toEntity(MetricsDO metricsDO) {
        MetricEntity entity = new MetricEntity();
        entity.setId(metricsDO.getId());
        entity.setApp(metricsDO.getApp());
        entity.setGmtCreate(metricsDO.getGmtCreate());
        entity.setTimestamp(metricsDO.getTimestamp());
        entity.setResource(metricsDO.getResource());
        entity.setPassQps(metricsDO.getPassQps());
        entity.setBlockQps(metricsDO.getBlockQps());
        entity.setSuccessQps(metricsDO.getSuccessQps());
        entity.setExceptionQps(metricsDO.getExceptionQps());
        entity.setRt(metricsDO.getRt());
        entity.setCount(metricsDO.getCount());
        return entity;
    }
    public static MetricsDO toDO(MetricEntity oldEntity) {
        MetricsDO metricsDO = new MetricsDO();
        metricsDO.setId(oldEntity.getId());
        metricsDO.setGmtCreate(oldEntity.getGmtCreate());
        metricsDO.setApp(oldEntity.getApp());
        metricsDO.setTimestamp(oldEntity.getTimestamp());
        metricsDO.setResource(oldEntity.getResource());
        metricsDO.setPassQps(oldEntity.getPassQps());
        metricsDO.setBlockQps(oldEntity.getBlockQps());
        metricsDO.setSuccessQps(oldEntity.getSuccessQps());
        metricsDO.setExceptionQps(oldEntity.getExceptionQps());
        metricsDO.setRt(oldEntity.getRt());
        metricsDO.setCount(oldEntity.getCount());
        return metricsDO;
    }
}
