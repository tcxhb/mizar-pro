package com.tcxhb.mizar.admin.controller;

import com.tcxhb.mizar.admin.model.request.BaseAppReq;
import com.tcxhb.mizar.admin.utils.ContextHolder;
import com.tcxhb.mizar.core.constants.enums.UserTypeEnum;
import com.tcxhb.mizar.core.entity.UserDTO;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/15
 */
public abstract class BaseController {

    public void fillAppName(BaseAppReq req){
       UserDTO userDTO = ContextHolder.get();
       if(userDTO == null){
           return;
       }
       if(UserTypeEnum.NORMAL.eq(userDTO.getType())){
           req.setAppName(userDTO.getAppName());
       }
    }
}
