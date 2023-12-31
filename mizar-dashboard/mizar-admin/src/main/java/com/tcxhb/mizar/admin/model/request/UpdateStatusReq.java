package com.tcxhb.mizar.admin.model.request;

import com.tcxhb.mizar.common.utils.ParamUtils;
import lombok.Data;

import java.io.Serializable;
/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/4
 */
@Data
public class UpdateStatusReq implements Serializable {
    private Long id;
    private Integer status;

    public void check(){
        ParamUtils.notNull(id,"id不能为空");
        ParamUtils.notNull(status,"status不能为空");
    }
}
