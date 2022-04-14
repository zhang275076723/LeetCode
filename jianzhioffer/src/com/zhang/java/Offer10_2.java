package com.zhang.java;

/**
 * @Date 2022/3/14 15:12
 * @Author zsy
 * @Description 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
 * 总跳法 = 1次跳上1级台阶和剩余n-1级台阶的跳法 + 1次跳上2级台阶和剩余n-2级台阶的跳法
 * <p>
 * 输入：n = 2
 * 输出：2
 * <p>
 * 输入：n = 7
 * 输出：21
 * <p>
 * 输入：n = 0
 * 输出：1
 */
public class Offer10_2 {
    public static void main(String[] args) {
        Offer10_2 offer10_2 = new Offer10_2();
        System.out.println(offer10_2.numWays(7));
        System.out.println(offer10_2.numWays2(7));
        System.out.println(offer10_2.numWays3(7));
    }

    /**
     * 递归，有大量重复计算，需要优化
     *
     * @param n
     * @return
     */
    public int numWays(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return (numWays(n - 1) + numWays(n - 2)) % 1000000007;
    }

    /**
     * 动态规划，时间复杂度O(n)，空间复杂的O(n）
     *
     * @param n
     * @return
     */
    public int numWays2(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        int[] dp = new int[n + 1];
        final int MOD = 1000000007;
        dp[0] = dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % MOD;
        }
        return dp[n];
    }

    /**
     * 动态规划优化，时间复杂度O(n)，空间复杂的O(1）
     *
     * @param n
     * @return
     */
    public int numWays3(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        int p = 1;
        int q = 1;
        int result = 0;
        final int MOD = 1000000007;
        for (int i = 2; i <= n; i++) {
            result = (p + q) % MOD;
            p = q;
            q = result;
        }
        return result;
    }
}
