package com.tcxhb.mizar.core.convert;

import com.tcxhb.mizar.dao.dataobject.MiscTokenDO;
import com.tcxhb.mizar.dao.dataobject.MiscUserDO;
import com.tcxhb.mizar.core.entity.TokenDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MiscTokenConverter {

    public MiscTokenDO dto2DO(TokenDTO miscToken);


    public TokenDTO do2DTO(MiscTokenDO req);

    public default TokenDTO user2Token(MiscUserDO userDO) {
        TokenDTO dto = new TokenDTO();
        dto.setUserid(userDO.getId());
        dto.setUsername(userDO.getUsername());
        dto.setType(userDO.getType());
        return dto;
    }
}
