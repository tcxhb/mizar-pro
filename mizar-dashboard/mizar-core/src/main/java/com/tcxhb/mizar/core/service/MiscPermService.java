package com.tcxhb.mizar.core.service;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/3/31
 */
public interface MiscPermService {

    /**
     *
     * @return
     */
    public Set<String> getPermByRole(Integer roleId);
}
