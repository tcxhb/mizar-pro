package com.tcxhb.mizar.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/11/5
 */
public class IPUtils {

    public static String getIp() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String hostAddress = localHost.getHostAddress();
        String hostName = localHost.getHostName();
        return hostAddress;
    }

    public static InetAddress getIpAddress() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localHost;
    }
}
