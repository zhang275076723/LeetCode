package com.zhang.java;

/**
 * @Date 2022/3/30 9:41
 * @Author zsy
 * @Description 丑数 三指针类比Problem75、Problem264、Problem1201 各种数类比Problem204、Problem263、Problem264、Problem1201 同Problem264
 * 我们把只包含质因子 2、3 和 5 的数称作丑数（Ugly Number）。
 * 求按从小到大的顺序的第 n 个丑数。
 * <p>
 * 输入: n = 10
 * 输出: 12
 * 解释: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 是前 10 个丑数。
 * <p>
 * 1 是丑数。
 * n 不超过1690。
 */
public class Offer49 {
    public static void main(String[] args) {
        Offer49 offer49 = new Offer49();
        System.out.println(offer49.nthUglyNumber(10));
    }

    /**
     * 动态规划，三指针
     * 核心思想：一个丑数乘上2、3、5也是丑数
     * dp[i]：第i+1个丑数
     * dp[m] = min(dp[i]*2,dp[j]*3,dp[k]*5) (i,j,k < m)
     * 例如：1, 2, 3, 4, 5, 6, 8, 9, 10, 12是前10个丑数，
     * i=6、j=4、k=2，dp[i]*2=16、dp[j]*3=15、dp[k]*5=15，取三者中的最小值15，即为第11个丑数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        if (n <= 0) {
            return -1;
        }

        if (n == 1) {
            return 1;
        }

        int[] dp = new int[n];
        //dp初始化
        dp[0] = 1;

        //i、j、k分别指向dp数组的下标索引，每次取dp[i]*2，dp[j]*3，dp[k]*5，三者中最小值作为当前丑数
        int i = 0;
        int j = 0;
        int k = 0;

        for (int m = 1; m < n; m++) {
            //dp[i]*2，dp[j]*3，dp[k]*5，三者中最小值作为当前丑数
            dp[m] = Math.min(dp[i] * 2, Math.min(dp[j] * 3, dp[k] * 5));

            //i指针后移
            if (dp[m] == dp[i] * 2) {
                i++;
            }

            //j指针后移
            if (dp[m] == dp[j] * 3) {
                j++;
            }

            //k指针后移
            if (dp[m] == dp[k] * 5) {
                k++;
            }
        }

        return dp[n - 1];
    }
}
