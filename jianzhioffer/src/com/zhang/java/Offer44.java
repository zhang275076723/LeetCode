package com.zhang.java;

/**
 * @Date 2022/3/27 8:49
 * @Author zsy
 * @Description 数字以0123456789101112131415…的格式序列化到一个字符序列中。
 * 在这个序列中，第5位（从下标0开始计数）是5，第13位是1，第19位是4，等等。
 * 请写一个函数，求任意第n位对应的数字。
 * <p>
 * 输入：n = 3
 * 输出：3
 * <p>
 * 输入：n = 11
 * 输出：0
 */
public class Offer44 {
    public static void main(String[] args) {
        Offer44 offer44 = new Offer44();
        System.out.println(offer44.findNthDigit(11));
    }

    /**
     * 范围         个数        所占的位数
     * 1-9          9           9*1=9
     * 10-99        90          90*2=180
     * 100-999      900         900*3=2700
     * 1000-9999    9000        9000*4=36000
     *
     * @param n
     * @return
     */
    public int findNthDigit(int n) {
        if (n == 0) {
            return 0;
        }

        //当前所占的位数，如果是int类型，有可能溢出
        long count = 0;
        //数字的位数
        int k = 1;
        //k位数字的个数
        int size = 9;

        while (count + (long) size * k < n) {
            count = count + (long) size * k;
            k++;
            size = size * 10;
        }

        //n在k位数字的位置
        int nStartAtK = (int) (n - count);
        //k位数字的起始值
        int kStart = (int) Math.pow(10, k - 1);
        int i = nStartAtK / k;
        int j = nStartAtK % k;

        if (j == 0) {
            String temp = String.valueOf(kStart + i - 1);
            return temp.charAt(k - 1) - '0';
        } else {
            String temp = String.valueOf(kStart + i);
            return temp.charAt(j - 1) - '0';
        }
    }
}
