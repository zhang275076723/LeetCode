package com.zhang.java;

/**
 * @Date 2022/3/17 17:37
 * @Author zsy
 * @Description 二进制中1的个数 类比Problem461
 * 编写一个函数，输入是一个无符号整数（以二进制串的形式），
 * 返回其二进制表达式中数字位数为 '1' 的个数（也被称为 汉明重量).）。
 * <p>
 * 输入：n = 11 (控制台输入 00000000000000000000000000001011)
 * 输出：3
 * 解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。
 * <p>
 * 输入：n = 11 (控制台输入 00000000000000000000000000001011)
 * 输出：3
 * 解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。
 * <p>
 * 输入：n = 4294967293 (控制台输入 11111111111111111111111111111101，部分语言中 n = -3）
 * 输出：31
 * 解释：输入的二进制串 11111111111111111111111111111101 中，共有 31 位为 '1'。
 * 在 Java 中，编译器使用 二进制补码 记法来表示有符号整数。因此，在 示例 3 中，输入表示有符号整数 -3
 * <p>
 * 输入必须是长度为 32 的 二进制串 。
 */
public class Offer15 {
    public static void main(String[] args) {
        Offer15 offer15 = new Offer15();
        System.out.println(offer15.hammingWeight(-3));
        System.out.println(offer15.hammingWeight2(-3));
    }

    /**
     * n & 2^i，如果结果不为0，说明n的第i位为1
     * 时间复杂度O(k)，空间复杂的O(1) (k为int类型长度，即32)
     *
     * @param n
     * @return
     */
    public int hammingWeight(int n) {
        int result = 0;

        while (n != 0) {
            result = result + (n & 1);
            //因为是补码存放，所以需要无符号右移
            n = n >>> 1;
        }

        return result;
    }

    /**
     * n & (n−1)，将n表示的二进制位中的最低位的 1 变为 0
     * 时间复杂度O(k)，空间复杂的O(1) (k为n的二进制中1的个数)
     *
     * @param n
     * @return
     */
    public int hammingWeight2(int n) {
        int result = 0;

        while (n != 0) {
            //将n的最低位1变为0
            n = n & (n - 1);
            result++;
        }

        return result;
    }
}
