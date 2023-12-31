package com.tcxhb.mizar.admin.convert;

import com.tcxhb.mizar.admin.model.request.add.AppAddReq;
import com.tcxhb.mizar.admin.model.request.query.AppQueryReq;
import com.tcxhb.mizar.dao.dataobject.AppDO;
import com.tcxhb.mizar.dao.dataobject.query.AppQuery;
import com.tcxhb.mizar.admin.model.response.AppVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppConvert {

    public AppVO do2VO(AppDO wjApp);

    public AppQuery toQuery(AppQueryReq req);

    public AppDO req2DO(AppAddReq addReq);

}
