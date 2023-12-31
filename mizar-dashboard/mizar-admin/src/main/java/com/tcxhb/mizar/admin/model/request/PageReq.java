package com.tcxhb.mizar.admin.model.request;

import com.tcxhb.mizar.common.model.PageRequest;
import lombok.Data;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/3/31
 */
@Data
public class PageReq extends BaseReq{
    private PageRequest page = new PageRequest();
}
