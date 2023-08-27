package com.zhang.java;

/**
 * @Date 2022/6/10 9:46
 * @Author zsy
 * @Description 汉明距离 位运算类比Problem29、Problem190、Problem191、Problem201、Problem271、Problem371、Problem405、Problem477、Problem1290、Offer15、Offer64、Offer65、IpToInt
 * 两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
 * 给你两个整数 x 和 y，计算并返回它们之间的汉明距离。
 * <p>
 * 输入：x = 1, y = 4
 * 输出：2
 * 解释：
 * 1   (0 0 0 1)
 * 4   (0 1 0 0)
 * <p>
 * 输入：x = 3, y = 1
 * 输出：1
 * <p>
 * 0 <= x, y <= 2^31 - 1
 */
public class Problem461 {
    public static void main(String[] args) {
        Problem461 problem461 = new Problem461();
        System.out.println(problem461.hammingDistance(1, 4));
        System.out.println(problem461.hammingDistance2(1, 4));
    }

    /**
     * 模拟
     * x^y：得到x、y表示的二进制数中当前位不同则置为1，相同则置为0表示的数
     * (n>>i)&1，如果结果为0，说明n表示的二进制中由右往左的第i位为1
     * 时间复杂度O(logC)，空间复杂度O(1) (C为元素取值范围，logC=log2^31=31)
     *
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance(int x, int y) {
        int count = 0;
        int z = x ^ y;

        while (z != 0) {
            //+的优先级高于&，所以需要添加括号
            count = count + (z & 1);
            //计算n二进制表示中1的个数，必须使用无符号右移
            z = z >>> 1;
        }

        return count;
    }

    /**
     * 模拟
     * x^y：得到x、y表示的二进制数中当前位不同则置为1，相同则置为0表示的数
     * n&(n-1)：得到n表示的二进制数中最低位的1置为0表示的数
     * n&(-n)：得到n表示的二进制数中最低位1表示的数
     * n^1：将n表示的二进制中最低位翻转，原先为1，置为0，原先为0，置为1
     * 时间复杂度O(logC)，空间复杂度O(1) (C为元素取值范围，logC=log2^31=31)
     *
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance2(int x, int y) {
        int count = 0;
        int z = x ^ y;

        while (z != 0) {
            //将z表示的二进制中的最低位的1置为0
            z = z & (z - 1);
            count++;
        }

        return count;
    }
}
