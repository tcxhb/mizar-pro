package com.tcxhb.mizar.core.service;

import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.core.entity.TokenDTO;
import com.tcxhb.mizar.core.entity.UserDTO;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/3/31
 */
public interface LoginService {
    /**
     * 登录获取平台信息
     * 校验用户名密码
     * 把platformId放到redis里面 key是一个32位的字符串
     * 失效时间60秒
     *
     * @return
     */
    MiscResult<TokenDTO> login(String username, String password);

    /**
     * 清除redis的key
     *
     * @param session
     * @return
     */
    MiscResult loginOut(String session);


    /**
     * 从redis里面拿数据
     *
     * @param session
     * @return
     */
    MiscResult<UserDTO> getLoginUser(String session);
}
