package com.zhang.java;

/**
 * @Date 2024/5/12 08:28
 * @Author zsy
 * @Description 十-二进制数的最少数目 类比Problem483、Problem2396
 * 如果一个十进制数字不含任何前导零，且每一位上的数字不是 0 就是 1 ，那么该数字就是一个 十-二进制数 。
 * 例如，101 和 1100 都是 十-二进制数，而 112 和 3001 不是。
 * 给你一个表示十进制整数的字符串 n ，返回和为 n 的 十-二进制数 的最少数目。
 * <p>
 * 输入：n = "32"
 * 输出：3
 * 解释：10 + 11 + 11 = 32
 * <p>
 * 输入：n = "82734"
 * 输出：8
 * <p>
 * 输入：n = "27346209830709182346"
 * 输出：9
 * <p>
 * 1 <= n.length <= 10^5
 * n 仅由数字组成
 * n 不含任何前导零并总是表示正整数
 */
public class Problem1689 {
    public static void main(String[] args) {
        Problem1689 problem1689 = new Problem1689();
        String n = "27346209830709182346";
        System.out.println(problem1689.minPartitions(n));
    }

    /**
     * 模拟
     * n的每一位只能由当前位为0或1的十-二进制数相加构成，所以n每一位中的最大值即为和为n的十-二进制数的最少数目
     * 时间复杂度O(n.length())，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int minPartitions(String n) {
        //n每一位中的最大值
        int max = 1;

        for (char c : n.toCharArray()) {
            max = Math.max(max, c - '0');

            //n每一位中的最大值最大只能为9
            if (max == 9) {
                return 9;
            }
        }

        return max;
    }
}
