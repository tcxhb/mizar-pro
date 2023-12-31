package com.tcxhb.mizar.core.convert;

import com.tcxhb.mizar.dao.dataobject.MiscUserDO;
import com.tcxhb.mizar.core.entity.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MiscUserConverter {

    @Mappings({
            @Mapping(target = "userId", source = "id"),
    })
    public UserDTO do2Dto(MiscUserDO userDO);
}
