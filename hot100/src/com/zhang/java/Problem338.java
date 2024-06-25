package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/6/1 9:41
 * @Author zsy
 * @Description 比特位计数 类比Problem397、Problem1342、Problem1404、Problem2139 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem322、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
 * 给你一个整数 n ，对于 0 <= i <= n 中的每个 i ，计算其二进制表示中 1 的个数 ，
 * 返回一个长度为 n + 1 的数组 ans 作为答案。
 * <p>
 * 输入：n = 2
 * 输出：[0,1,1]
 * 解释：
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 * <p>
 * 输入：n = 5
 * 输出：[0,1,1,2,1,2]
 * 解释：
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 * 3 --> 11
 * 4 --> 100
 * 5 --> 101
 * <p>
 * 0 <= n <= 10^5
 */
public class Problem338 {
    public static void main(String[] args) {
        Problem338 problem338 = new Problem338();
        System.out.println(Arrays.toString(problem338.countBits(5)));
    }

    /**
     * 动态规划
     * dp[i]：i的二进制表示中1的个数
     * dp[i] = dp[i/2] + 1 (i%2 == 1) (奇数i中1的个数=i/2中1的个数+1，相当于i/2左移一位，并将最后一位设置为1)
     * dp[i] = dp[i/2]     (i%2 == 0) (偶数i中1的个数=i/2中1的个数，相当于i/2左移一位)
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int[] countBits(int n) {
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                dp[i] = dp[i / 2] + 1;
            } else {
                dp[i] = dp[i / 2];
            }
        }

        return dp;
    }
}
