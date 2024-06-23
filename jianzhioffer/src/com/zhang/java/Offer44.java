package com.zhang.java;

/**
 * @Date 2022/3/27 8:49
 * @Author zsy
 * @Description 数字序列中某一位的数字 类比Problem60、Problem172、Problem233、Problem400、Offer43、Interview_17_06 同Problem400
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
     * 计算长度为1、2、3...数字的个数，找到n所在长度为几的数字中，确定所在位置
     * 时间复杂度O(logn)，空间复杂度O(logn)
     * <p>
     * 例如：n=123
     * 1位数字范围共9个数字，不属于1位数字
     * 2位数字范围共9+180个数字，属于2位数字范围
     * 123-9-1=113
     * 113/2=56
     * 113%2=1
     * 说明，123是2位数字10+56=66的下标索引为1的数字，即为6
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

        //当前数字的长度
        int numLength = 1;
        //长度为numLength的数字个数，使用long，避免int溢出
        long numCount = 9;
        //长度小于numLength的所有数字所占的位数，使用long，避免int溢出
        long count = 0;

        //找到n所在的长度为numLength的起始数字10^(numLength-1)
        while (count + numLength * numCount < n) {
            count = count + numLength * numCount;
            numLength++;
            numCount = numCount * 10;
        }

        //n所在的长度为numLength的起始数字
        int startNum = quickPow(10, numLength - 1);
        //从startNum开始的下标索引
        int index = (int) (n - count - 1);
        //第几个数字startNum+i
        int i = index / numLength;
        //数字startNum+i的第j个数字即为最后结果
        int j = index % numLength;

        return String.valueOf(startNum + i).charAt(j) - '0';
    }

    private int quickPow(int a, int n) {
        int result = 1;

        while (n != 0) {
            if ((n & 1) == 1) {
                result = result * a;
            }

            a = a * a;
            n = n >>> 1;
        }

        return result;
    }
}
