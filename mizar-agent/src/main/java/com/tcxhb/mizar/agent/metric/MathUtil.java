package com.tcxhb.mizar.agent.metric;

public class MathUtil {

    public static long divide(long dividend, long divisor) {
        if (divisor == 0) {
            return 0;
        }
        return dividend / divisor;
    }

}
