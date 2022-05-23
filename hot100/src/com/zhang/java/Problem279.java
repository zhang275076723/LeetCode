package com.zhang.java;

/**
 * @Date 2022/5/23 11:27
 * @Author zsy
 * @Description 完全平方数
 * 给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
 * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。
 * 例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
 * <p>
 * 输入：n = 12
 * 输出：3
 * 解释：12 = 4 + 4 + 4
 * <p>
 * 输入：n = 13
 * 输出：2
 * 解释：13 = 4 + 9
 * <p>
 * 1 <= n <= 10^4
 */
public class Problem279 {
    public static void main(String[] args) {
        Problem279 problem279 = new Problem279();
        System.out.println(problem279.numSquares(13));
        System.out.println(problem279.numSquares2(13));
    }

    /**
     * 动态规划
     * dp[i]：最少需要完全平方数的数量
     * dp[i] = dp[i-j^2] + 1 (1 <= j <= i^1/2)
     * 时间复杂度O(n*n^1/2)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int numSquares(int n) {
        if (n <= 0) {
            return -1;
        }

        //dp[0]=0，用于初始化
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }

        return dp[n];
    }

    /**
     * 四平方和定理：任意一个正整数都可以表示为至多四个正整数的平方和
     * 当结果为1时，说明n为完全平方数
     * 当结果为2时，则有n=a^2+b^2，遍历1<=a<=n^1/2，判断n-a^2是否是是完全平方数
     * 当结果为3时，即结果不为1、2、4时，结果为3
     * 当结果为4时，n=4^k*(8m+7) 时，能拆分为4个正整数平方和
     * 时间复杂度O(n^1/2)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int numSquares2(int n) {
        if (n <= 0) {
            return -1;
        }

        //n是否是完全平方数
        int x = (int) Math.sqrt(n);
        if (x * x == n) {
            return 1;
        }

        //n是否能表示成n=4^k*(8m+7)
        x = n;
        while (x % 4 == 0) {
            x = x / 4;
        }
        if (x % 8 == 7) {
            return 4;
        }

        //n是否能表示为n=a^2+b^2
        for (int i = 1; i * i < n; i++) {
            int j = n - i * i;
            x = (int) Math.sqrt(j);
            if (x * x == j) {
                return 2;
            }
        }

        //不满足n能拆分为1、2、4个正整数平方和时，返回3
        return 3;
    }
}
