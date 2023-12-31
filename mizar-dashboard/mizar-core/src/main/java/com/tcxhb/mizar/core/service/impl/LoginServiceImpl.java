package com.tcxhb.mizar.core.service.impl;

import com.tcxhb.mizar.dao.dataobject.MiscUserDO;
import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.utils.ResultUtils;
import com.tcxhb.mizar.common.utils.UUidUtils;
import com.tcxhb.mizar.core.convert.MiscTokenConverter;
import com.tcxhb.mizar.core.convert.MiscUserConverter;
import com.tcxhb.mizar.core.entity.TokenDTO;
import com.tcxhb.mizar.core.entity.UserDTO;
import com.tcxhb.mizar.core.service.LoginService;
import com.tcxhb.mizar.core.service.MiscUserService;
import com.tcxhb.mizar.core.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/3/31
 */
@Component
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private MiscUserService miscUserService;
    @Autowired
    private MiscTokenConverter tokenConvert;

    @Autowired
    private MiscUserConverter convert;

    @Override
    public MiscResult<TokenDTO> login(String username, String password) {
        MiscResult<MiscUserDO> result = miscUserService.checkPassword(username, password);
        if (!result.isSuccess()) {
            return ResultUtils.copy(result, null);
        }
        //用户对象
        String uuid = UUidUtils.getUid();
        //放入缓存
        TokenDTO tokenDTO = tokenConvert.user2Token(result.getData());
        tokenDTO.setToken(uuid);
        tokenService.save(tokenDTO);
        return MiscResult.suc(tokenDTO);
    }

    @Override
    public MiscResult loginOut(String session) {
        tokenService.deleteByToken(session);
        return MiscResult.suc(true);
    }

    @Override
    public MiscResult<UserDTO> getLoginUser(String session) {
        MiscResult<TokenDTO> result = tokenService.queryByToken(session);
        if (!result.isSuccess()) {
            return ResultUtils.copy(result, null);
        }
        MiscUserDO userDO = miscUserService.queryByUsername(result.getData().getUsername());
        UserDTO dto = convert.do2Dto(userDO);
        dto.setToken(session);
        return MiscResult.suc(dto);
    }
}
