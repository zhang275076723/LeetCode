package com.zhang.java;

/**
 * @Date 2022/8/27 11:37
 * @Author zsy
 * @Description 丑数 II 类比Problem204、Problem263 同Offer49
 * 给你一个整数 n ，请你找出并返回第 n 个 丑数 。
 * 丑数 就是只包含质因数 2、3 和/或 5 的正整数。
 * <p>
 * 输入：n = 10
 * 输出：12
 * 解释：[1, 2, 3, 4, 5, 6, 8, 9, 10, 12] 是由前 10 个丑数组成的序列。
 * <p>
 * 输入：n = 1
 * 输出：1
 * 解释：1 通常被视为丑数。
 * <p>
 * 1 <= n <= 1690
 */
public class Problem264 {
    public static void main(String[] args) {
        Problem264 problem264 = new Problem264();
        System.out.println(problem264.nthUglyNumber(11));
    }

    /**
     * 动态规划，三指针
     * 要点：一个丑数乘上2、3、5也是丑数
     * dp[i]：第i+1个丑数
     * dp[m] = min(dp[i]*2,dp[j]*3,dp[k]*5) (i,j,k < m)
     * 当前丑数数组乘上2、3、5也是丑数，所以根据前面的丑数可以得到当前的丑数
     * 三个指针分别指向当前丑数数组的下标索引，分别乘上2、3、5，取最小值，即为当前丑数
     * 1, 2, 3, 4, 5, 6, 8, 9, 10, 12是前10个丑数
     * i=6、j=4、k=2，分为乘上2、3、5，取最小值15，即为第11个丑数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        if (n <= 0) {
            return -1;
        }

        int[] dp = new int[n];
        dp[0] = 1;

        //i、j、k分别指向当前丑数数组的下标索引，即乘以2、3、5的丑数下标索引
        int i = 0;
        int j = 0;
        int k = 0;

        for (int m = 1; m < n; m++) {
            //三个指针对应元素乘上相应的2、3、5，得到的值中最小值即为当前丑数
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
