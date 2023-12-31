package com.tcxhb.mizar.admin.convert;

import com.tcxhb.mizar.admin.model.request.add.FlowRuleAddReq;
import com.tcxhb.mizar.admin.model.request.query.FlowRuleQueryReq;
import com.tcxhb.mizar.admin.model.response.FlowRuleVO;
import com.tcxhb.mizar.dao.dataobject.FlowRuleDO;
import com.tcxhb.mizar.dao.dataobject.query.FlowRuleQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlowRuleConvert {

    public FlowRuleVO do2VO(FlowRuleDO mzFlowRule);

    public FlowRuleQuery toQuery(FlowRuleQueryReq req);

    public FlowRuleDO req2DO(FlowRuleAddReq addReq);

}
