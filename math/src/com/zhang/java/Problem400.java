package com.zhang.java;

/**
 * @Date 2022/11/6 08:49
 * @Author zsy
 * @Description 第 N 位数字 类比Problem43、Problem233 同Offer44
 * 给你一个整数 n ，请你在无限的整数序列 [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...] 中找出并返回第 n 位上的数字。
 * <p>
 * 输入：n = 3
 * 输出：3
 * <p>
 * 输入：n = 11
 * 输出：0
 * 解释：第 11 位数字在序列 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... 里是 0 ，它是 10 的一部分。
 * <p>
 * 1 <= n <= 2^31 - 1
 */
public class Problem400 {
    public static void main(String[] args) {
        Problem400 problem400 = new Problem400();
        System.out.println(problem400.findNthDigit(123));
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
        //当前数字的长度
        int length = 1;
        //长度为length数字的个数，使用long避免溢出
        long count = 9;
        //长度小于length数字的总个数，使用long避免溢出
        long sum = 0;

        while (sum + length * count < n) {
            sum = sum + length * count;
            length++;
            count = count * 10;
        }

        //n在长度为length数字的索引下标，索引从0开始
        int index = (int) (n - sum - 1);
        //长度为length的起始数字
        int startNum = (int) Math.pow(10, length - 1);
        //在长度为length的数字的第几个位置
        int i = index / length;
        //在i个位置的偏移量
        int j = index % length;

        return String.valueOf(startNum + i).charAt(j) - '0';
    }
}
