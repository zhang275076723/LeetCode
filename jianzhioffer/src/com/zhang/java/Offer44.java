package com.zhang.java;

/**
 * @Date 2022/3/27 8:49
 * @Author zsy
 * @Description 数字序列中某一位的数字
 * 数字以0123456789101112131415…的格式序列化到一个字符序列中。
 * 在这个序列中，第5位（从下标0开始计数）是5，第13位是1，第19位是4，等等。
 * 请写一个函数，求任意第n位对应的数字。
 * <p>
 * 输入：n = 3
 * 输出：3
 * <p>
 * 输入：n = 11
 * 输出：0
 * <p>
 * 0 <= n < 2^31
 */
public class Offer44 {
    public static void main(String[] args) {
        Offer44 offer44 = new Offer44();
        System.out.println(offer44.findNthDigit(123));
    }

    /**
     * 模拟
     * 找到n是属于k位数字的范围，从k位数字的起始位置找到n所属的数字，找到对应下标
     * 例如：n=123
     * 1位数字范围共9个数字，不属于1位数字
     * 2位数字范围共9+180个数字，属于2位数字范围
     * 123-9-1=113
     * 113/2=56
     * 113%2=1
     * 说明，123是2位数字66的第2位，即为6
     * 时间复杂度O(logn)，空间复杂度O(logn)
     * <p>
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
        //k位数字的位数
        int k = 1;
        //k位数字的个数
        int size = 9;

        while (count + (long) size * k < n) {
            count = count + (long) size * k;
            k++;
            size = size * 10;
        }

        //n在k位数字的位置
        int nStartAtK = (int) (n - count - 1);
        //k位数字的起始值
        int kStart = (int) Math.pow(10, k - 1);
        int i = nStartAtK / k;
        int j = nStartAtK % k;

        String value = String.valueOf(kStart + i);
        return value.charAt(j) - '0';
    }
}
