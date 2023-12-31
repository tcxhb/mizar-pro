package com.tcxhb.mizar.core.entity;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/1/7
 */
public class AppearCounter {
    private int arr[];
    private int appearLimit;
    private int unit = 1;

    public AppearCounter(int timeInterval, int appearLimit) {
        arr = new int[timeInterval];
        this.appearLimit = appearLimit;
    }

    public AppearCounter(int timeInterval, int appearLimit, int unit) {
        arr = new int[timeInterval];
        this.appearLimit = appearLimit;
        this.unit = unit;
    }

    /**
     * @param timeStamp
     * @return true表示触发告警
     */
    public boolean addAndCheck(long timeStamp) {
        int second = (int) (timeStamp / 1000) / unit;
        int index = second % arr.length;
        arr[index] = second;
        //阈值校验
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            //
            if (arr[i] + arr.length >= second) {
                count++;
            }
            if (count >= appearLimit) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回时间区间内最小的一个值
     *
     * @return
     */
    public int minTimeWithInv(long timeStamp) {
        int second = (int) (timeStamp / 1000);
        int min = 2147483647;
        for (int i = 0; i < arr.length; i++) {
            //30秒内最大的值
            if (arr[i] == 0) {
                continue;
            } else if (arr[i] + arr.length > second) {
                min = arr[i] < min ? arr[i] : min;
            }
        }
        return min;
    }

    public static void main(String args[]) {
        AppearCounter counter = new AppearCounter(10, 5);
        for (int i = 1; ; i++) {
            int value = (100 + i) * 1000;
            Boolean result = counter.addAndCheck(value);
            System.out.println(value + "告警" + result + "\n");
        }
    }
}
