package com.tcxhb.mizar.admin.convert;

import com.tcxhb.mizar.admin.model.request.query.MachineQueryReq;
import com.tcxhb.mizar.admin.model.response.MachineVO;
import com.tcxhb.mizar.dao.dataobject.MachineDO;
import com.tcxhb.mizar.dao.dataobject.query.MachineQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MachineConvert {

    public MachineVO do2VO(MachineDO tcJobMachine);

    public MachineQuery toQuery(MachineQueryReq req);
}
