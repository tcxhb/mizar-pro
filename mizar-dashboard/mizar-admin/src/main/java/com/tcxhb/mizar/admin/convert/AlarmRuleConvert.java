package com.tcxhb.mizar.admin.convert;

import com.tcxhb.mizar.admin.model.request.add.AlarmRuleAddReq;
import com.tcxhb.mizar.admin.model.request.query.AlarmRuleQueryReq;
import com.tcxhb.mizar.admin.model.response.AlarmRuleVO;
import com.tcxhb.mizar.dao.dataobject.AlarmRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.AlarmRuleQuery;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface AlarmRuleConvert {

    public AlarmRuleVO do2VO(AlarmRuleDO mzAlarmRule);

    public AlarmRuleQuery toQuery(AlarmRuleQueryReq req);

    public AlarmRuleDO req2DO(AlarmRuleAddReq addReq);

}
