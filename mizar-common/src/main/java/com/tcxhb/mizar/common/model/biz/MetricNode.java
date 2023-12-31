package com.tcxhb.mizar.common.model.biz;

import lombok.Data;

/**
 * tcxhb
 */
@Data
public class MetricNode {
    //资源
    private String resource;
    private long timestamp;
    private long exceptionQps;
    private long successQps;
    private long blockQps;
    private long passQps;
    //取的是平均RT
    private long rt;
    private long avgRt;

    @Override
    public String toString() {
        return timestamp + "|" + successQps + "|" + exceptionQps + "|" + rt + "|" + avgRt + "\n";
    }

    public String toThinString() {
        StringBuilder sb = new StringBuilder();
        sb.append(timestamp).append("|");
        String legalName = resource.replaceAll("\\|", "_");
        sb.append(legalName).append("|");
        sb.append(passQps).append("|");
        sb.append(blockQps).append("|");
        sb.append(successQps).append("|");
        sb.append(exceptionQps).append("|");
        sb.append(avgRt).append("|");
        return sb.toString();
    }

    public static MetricNode fromThinString(String line) {
        MetricNode node = new MetricNode();
        String[] strs = line.split("\\|");
        node.setTimestamp(Long.parseLong(strs[0]));
        node.setResource(strs[1]);
        node.setPassQps(Long.parseLong(strs[2]));
        node.setBlockQps(Long.parseLong(strs[3]));
        node.setSuccessQps(Long.parseLong(strs[4]));
        node.setExceptionQps(Long.parseLong(strs[5]));
        node.setRt(Long.parseLong(strs[6]));
        return node;
    }
}
