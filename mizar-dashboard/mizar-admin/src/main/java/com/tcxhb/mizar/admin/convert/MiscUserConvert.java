package com.tcxhb.mizar.admin.convert;

import com.tcxhb.mizar.dao.dataobject.MiscUserDO;
import com.tcxhb.mizar.dao.dataobject.query.MiscUserQuery;
import com.tcxhb.mizar.admin.model.request.UserAddReq;
import com.tcxhb.mizar.core.entity.UserDTO;
import com.tcxhb.mizar.admin.model.request.UserQueryReq;
import com.tcxhb.mizar.admin.model.response.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MiscUserConvert {

    public UserVO do2VO(MiscUserDO miscUser);

    public MiscUserQuery toQuery(UserQueryReq req);

    public MiscUserDO req2DO(UserAddReq addReq);
    @Mappings({
            @Mapping(target = "userId", source = "id"),
    })
    public UserDTO do2Dto(MiscUserDO userDO);
}
