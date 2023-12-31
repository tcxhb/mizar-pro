package com.tcxhb.mizar.agent.server.command.impl;

import com.tcxhb.mizar.common.model.biz.MetricNode;
import com.tcxhb.mizar.agent.server.command.CommandHandler;
import com.tcxhb.mizar.agent.server.command.CommandMapping;
import com.tcxhb.mizar.agent.server.command.CommandRequest;
import com.tcxhb.mizar.agent.statistic.workflow.MonitorHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
@Component
@Slf4j
@CommandMapping(name = "/cmd/metric/qps", desc = "QPS查询")
public class MetricQpsCommand implements CommandHandler<String> {

    @Override
    public String handle(CommandRequest request) {
        String start = request.getParam("startTime");
        String end = request.getParam("endTime");
        //String s = DateUtils.format(new Date(Long.parseLong(start)), DateUtils.format2);
        //String e = DateUtils.format(new Date(Long.parseLong(end)), DateUtils.format2);
        //log.info("metric qps:{},{}", s, e);
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            return "error_param_null";
        }
        //获取QPS
        StringBuilder sb = getQpsMetric(start, end);
        if (sb.length() <= 0) {
            return "error_qps_null";
        }
        return sb.toString();
    }

    /**
     * QPS监控
     *
     * @param start
     * @param end
     * @return
     */
    private StringBuilder getQpsMetric(String start, String end) {
        List<MetricNode> list = MonitorHolder.queryMetric(Long.parseLong(start), Long.parseLong(end));
        StringBuilder sb = new StringBuilder();
        for (MetricNode node : list) {
            sb.append(node.toThinString()).append("\n");
        }
        return sb;
    }

}
