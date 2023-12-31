package com.tcxhb.mizar.dao.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.tcxhb.mizar.dao.dataobject.BaseDO;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * 通用参数填充实现类
 * <p>
 * 如果没有显式的对通用参数进行赋值，这里会对通用参数进行填充、赋值
 *
 * @author hexiaowu
 */
@Component
public class DefaultFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO) {
            Date now = new Date();
            BaseDO baseDO = (BaseDO) metaObject.getOriginalObject();
            baseDO.setUpdateTime(now);
            baseDO.setCreateTime(now);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) metaObject.getOriginalObject();
            baseDO.setUpdateTime(new Date());
        }
    }
}
