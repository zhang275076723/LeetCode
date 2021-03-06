package com.zhang.java;

/**
 * @Date 2022/7/27 9:10
 * @Author zsy
 * @Description 位1的个数 同Offer15 类比Problem7、Problem190、Problem338、Problem461
 * 编写一个函数，输入是一个无符号整数（以二进制串的形式），
 * 返回其二进制表达式中数字位数为 '1' 的个数（也被称为汉明重量）。
 * 请注意，在某些语言（如 Java）中，没有无符号整数类型。
 * 在这种情况下，输入和输出都将被指定为有符号整数类型，并且不应影响您的实现，
 * 因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
 * 在 Java 中，编译器使用二进制补码记法来表示有符号整数。
 * 因此，在上面的 示例 3 中，输入表示有符号整数 -3。
 * <p>
 * 输入：00000000000000000000000000001011
 * 输出：3
 * 解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。
 * <p>
 * 输入：00000000000000000000000010000000
 * 输出：1
 * 解释：输入的二进制串 00000000000000000000000010000000 中，共有一位为 '1'。
 * <p>
 * 输入：11111111111111111111111111111101
 * 输出：31
 * 解释：输入的二进制串 11111111111111111111111111111101 中，共有 31 位为 '1'。
 * <p>
 * 输入必须是长度为 32 的 二进制串 。
 */
public class Problem191 {
    public static void main(String[] args) {
        Problem191 problem191 = new Problem191();
        System.out.println(problem191.hammingWeight(-3));
        System.out.println(problem191.hammingWeight2(-3));
        System.out.println(problem191.hammingWeight3(-3));
    }

    /**
     * 模拟
     * 时间复杂度O(|C|)，空间复杂度O(1) (|C|=32，因为int二进制为32位)
     *
     * @param n
     * @return
     */
    public int hammingWeight(int n) {
        int count = 0;

        while (n != 0) {
            count = count + (n & 1);

            //有可能n为负数，所以要使用无符号右移
            n = n >>> 1;
        }

        return count;
    }

    /**
     * 模拟
     * n & (n-1)：将二进制表示n的最后一位1置0
     * 时间复杂度O(|C|)，空间复杂度O(1) (|C|=32，因为int二进制为32位)
     *
     * @param n
     * @return
     */
    public int hammingWeight2(int n) {
        int count = 0;

        while (n != 0) {
            //将二进制表示n的最后一位1置0
            n = n & (n - 1);

            count++;
        }

        return count;
    }

    /**
     * 分治法，bitCount()源码
     * 相邻每1位统计1的个数，存放在这2位中
     * 相邻每2位统计1的个数，存放在这4位中
     * 相邻每4位统计1的个数，存放在这8位中
     * 相邻每8位统计1的个数，存放在这16位中
     * 相邻每16位统计1的个数，存放在这32位中
     * 返回这32位表示的数，即为1的个数
     * 时间复杂度O(log|C|)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int hammingWeight3(int n) {
        //相邻每1位统计1的个数，存放在这2位中
        n = ((n & 0xaaaaaaaa) >>> 1) + (n & 0x55555555);

        //相邻每2位统计1的个数，存放在这4位中
        n = ((n & 0xcccccccc) >>> 2) + (n & 0x33333333);

        //相邻每4位统计1的个数，存放在这8位中
        n = ((n & 0xf0f0f0f0) >>> 4) + (n & 0x0f0f0f0f);

        //相邻每8位统计1的个数，存放在这16位中
        n = ((n & 0xff00ff00) >>> 8) + (n & 0x00ff00ff);

        //相邻每16位统计1的个数，存放在这32位中
        n = ((n & 0xffff0000) >>> 16) + (n & 0x0000ffff);

        return n;
    }
}
