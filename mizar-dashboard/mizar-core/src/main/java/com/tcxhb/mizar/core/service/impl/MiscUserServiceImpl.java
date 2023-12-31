package com.tcxhb.mizar.core.service.impl;

import com.tcxhb.mizar.dao.dataobject.MiscUserDO;
import com.tcxhb.mizar.dao.dataobject.query.MiscUserQuery;
import com.tcxhb.mizar.dao.repository.MiscUserRepository;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.PageResponse;
import com.tcxhb.mizar.common.utils.ParamUtils;
import com.tcxhb.mizar.core.constants.enums.UserTypeEnum;
import com.tcxhb.mizar.core.service.MiscUserService;
import com.tcxhb.mizar.core.utils.Md5Utils;
import com.tcxhb.mizar.core.utils.PasswordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * author:auto.generator
 * time: 2023-09-20
 */
@Service
public class MiscUserServiceImpl implements MiscUserService {
    @Resource
    private MiscUserRepository repository;

    @Override
    public Long create(MiscUserDO userDO) {
        userDO.setType(UserTypeEnum.NORMAL.getType());
        MiscUserDO miscUserDO = queryByUsername(userDO.getUsername());
        ParamUtils.mustNull(miscUserDO, userDO.getUsername() + "用户已经存在");
        //生成md5
        String md5 = Md5Utils.getMD5(userDO.getPassword());
        String pwd = PasswordUtils.generatePassword(userDO.getUsername(), md5);
        userDO.setPassword(pwd);
        return repository.create(userDO);
    }

    @Override
    public boolean deleteById(Long id) {
        ParamUtils.notNull(id, "id不能为空");
        return repository.deleteById(id);
    }

    @Override
    public boolean updateById(MiscUserDO update) {
        MiscUserDO userDO = queryById(update.getId());
        ParamUtils.notNull(userDO, "用户不存在");
        //如果需要修改密码
        if (StringUtils.isNotBlank(update.getPassword())) {
            String md5 = Md5Utils.getMD5(update.getPassword());
            String pwd = PasswordUtils.generatePassword(userDO.getUsername(), md5);
            update.setPassword(pwd);
        }
        return repository.updateById(update);
    }

    @Override
    public MiscUserDO queryById(Long id) {
        ParamUtils.notNull(id, "id不能为空");
        return repository.queryById(id);
    }

    @Override
    public MiscUserDO queryByUsername(String usrname) {
        return repository.queryByUsername(usrname);
    }

    @Override
    public PageResponse<MiscUserDO> page(MiscUserQuery query) {
        return repository.page(query);
    }

    @Override
    public List<MiscUserDO> list(MiscUserQuery query) {
        return repository.list(query);
    }

    @Override
    public MiscResult<MiscUserDO> checkPassword(String userName, String password) {
        MiscUserDO userDO = queryByUsername(userName);
        if (userDO == null) {
            return MiscResult.err(CommonErrorCode.COMMON_ERROR, "用户不存在");
        }
        String encPassword = PasswordUtils.generatePassword(userName, password);
        if (encPassword.equals(userDO.getPassword())) {
            return MiscResult.suc(userDO);
        }
        return MiscResult.err(CommonErrorCode.COMMON_ERROR, "密码错误");
    }
}
