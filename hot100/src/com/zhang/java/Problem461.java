package com.zhang.java;

/**
 * @Date 2022/6/10 9:46
 * @Author zsy
 * @Description 汉明距离 类比Problem190、Problem191、Problem338、Offer15
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
     * x^y，使用&1计算结果二进制中1的个数
     * 时间复杂度O(logC)，空间复杂度O(1) (C为元素取值范围，logC=log2^31=31)
     *
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance(int x, int y) {
        int result = 0;
        int z = x ^ y;

        while (z != 0) {
            //+的优先级高于&，所以需要添加括号
            result = result + (z & 1);

            //如果存在负数，则需要使用无符号右移
            z = z >> 1;
        }

        return result;
    }

    /**
     * x&(x-1)，将x表示的二进制中最低位的 1 置 0
     * 时间复杂度O(logC)，空间复杂度O(1) (C为元素取值范围，logC=log2^31=31)
     *
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance2(int x, int y) {
        int result = 0;
        int z = x ^ y;

        while (z != 0) {
            z = z & (z - 1);

            result++;
        }

        return result;
    }
}
