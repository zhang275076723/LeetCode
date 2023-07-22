package com.zhang.java;

/**
 * @Date 2022/11/27 17:19
 * @Author zsy
 * @Description 整数拆分 动态规划类比Problem279、Problem322、Problem377、Problem416、Problem494、Problem518、Problem983、Offer14、Offer14_2、CircleBackToOrigin、Knapsack
 * 给定一个正整数 n ，将其拆分为 k 个 正整数 的和（ k >= 2 ），并使这些整数的乘积最大化。
 * 返回 你可以获得的最大乘积 。
 * <p>
 * 输入: n = 2
 * 输出: 1
 * 解释: 2 = 1 + 1, 1 × 1 = 1。
 * <p>
 * 输入: n = 10
 * 输出: 36
 * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36。
 * <p>
 * 2 <= n <= 58
 */
public class Problem343 {
    public static void main(String[] args) {
        Problem343 problem343 = new Problem343();
        System.out.println(problem343.integerBreak(10));
        System.out.println(problem343.integerBreak2(10));
    }

    /**
     * 动态规划
     * dp[i]：数字i拆分得到的最大乘积 (数字i至少拆为2个数字)
     * dp[i] = max(dp[i-j]*j, (i-j)*j) (1 < j < i)
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                //j * dp[i - j]：绳子剪为长度为j和之和为(i-j)的最大乘积的字段，至少为3段，
                //j * (i - j)：绳子剪为长度为j和(i-j)的2段
                dp[i] = Math.max(dp[i], Math.max(j * dp[i - j], j * (i - j)));
            }
        }

        return dp[n];
    }

    /**
     * 贪心，尽可能将n分为3的和
     * 最优：3
     * 次优：2
     * 最差：1
     * 时间复杂度O(1)，空间复杂的O(1)
     *
     * @param n
     * @return
     */
    public int integerBreak2(int n) {
        if (n == 2) {
            return 1;
        }

        if (n == 3) {
            return 2;
        }

        //尽可能拆分为3的和

        //商
        int a = n / 3;
        //余数
        int b = n % 3;

        //余数为0
        if (b == 0) {
            return quickPow(3, a);
        } else if (b == 1) {
            //余数为1，需要考虑最后一段和倒数第二段
            return quickPow(3, a - 1) * 2 * 2;
        } else {
            //余数为2
            return quickPow(3, a) * 2;
        }
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
