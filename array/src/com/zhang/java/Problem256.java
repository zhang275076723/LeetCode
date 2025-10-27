package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2023/9/29 08:30
 * @Author zsy
 * @Description 粉刷房子 类比Problem265、Problem276、Problem1473 动态规划类比Problem198、Problem213、Problem265、Problem279、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
 * 假如有一排房子，共 n 个，每个房子可以被粉刷成红色、蓝色或者绿色这三种颜色中的一种，
 * 你需要粉刷所有的房子并且使其相邻的两个房子颜色不能相同。
 * 当然，因为市场上不同颜色油漆的价格不同，所以房子粉刷成不同颜色的花费成本也是不同的。
 * 每个房子粉刷成不同颜色的花费是以一个 n x 3 的正整数矩阵 costs 来表示的。
 * 例如，costs[0][0] 表示第 0 号房子粉刷成红色的成本花费；costs[1][2] 表示第 1 号房子粉刷成绿色的花费，以此类推。
 * 请计算出粉刷完所有房子最少的花费成本。
 * <p>
 * 输入: costs = [[17,2,17],[16,16,5],[14,3,19]]
 * 输出: 10
 * 解释: 将 0 号房子粉刷成蓝色，1 号房子粉刷成绿色，2 号房子粉刷成蓝色。
 * 最少花费: 2 + 5 + 3 = 10。
 * <p>
 * 输入: costs = [[7,6,2]]
 * 输出: 2
 * <p>
 * costs.length == n
 * costs[i].length == 3
 * 1 <= n <= 100
 * 1 <= costs[i][j] <= 20
 */
public class Problem256 {
    public static void main(String[] args) {
        Problem256 problem256 = new Problem256();
        int[][] costs = {{17, 2, 17}, {16, 16, 5}, {14, 3, 19}};
        System.out.println(problem256.minCost(costs));
        System.out.println(problem256.minCost2(costs));
    }

    /**
     * 动态规划
     * dp[i][j]：前i个房子最后一个房子粉刷成第j种颜色的最小花费
     * dp[i][0] = min(dp[i-1][1], dp[i-1][2]) + costs[i-1][0]
     * dp[i][1] = min(dp[i-1][0], dp[i-1][2]) + costs[i-1][1]
     * dp[i][2] = min(dp[i-1][0], dp[i-1][1]) + costs[i-1][2]
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param costs
     * @return
     */
    public int minCost(int[][] costs) {
        int[][] dp = new int[costs.length + 1][3];

        //dp初始化，前0个房子最后一个房子粉刷成第j种颜色的最小花费为0
        dp[0][0] = 0;
        dp[0][1] = 0;
        dp[0][2] = 0;

        for (int i = 1; i <= costs.length; i++) {
            dp[i][0] = Math.min(dp[i - 1][1], dp[i - 1][2]) + costs[i - 1][0];
            dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][2]) + costs[i - 1][1];
            dp[i][2] = Math.min(dp[i - 1][0], dp[i - 1][1]) + costs[i - 1][2];
        }

        int result = Integer.MAX_VALUE;

        for (int j = 0; j < 3; j++) {
            result = Math.min(result, dp[costs.length][j]);
        }

        return result;
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param costs
     * @return
     */
    public int minCost2(int[][] costs) {
        int[] dp = new int[3];

        //dp初始化，前0个房子最后一个房子粉刷成第j种颜色的最小花费为0
        dp[0] = 0;
        dp[1] = 0;
        dp[2] = 0;

        for (int i = 1; i <= costs.length; i++) {
            //保存上次粉刷到前i-1个房子的dp状态，用于本次dp更新
            int[] temp = Arrays.copyOf(dp, 3);
            dp[0] = Math.min(temp[1], temp[2]) + costs[i - 1][0];
            dp[1] = Math.min(temp[0], temp[2]) + costs[i - 1][1];
            dp[2] = Math.min(temp[0], temp[1]) + costs[i - 1][2];
        }

        int result = Integer.MAX_VALUE;

        for (int j = 0; j < 3; j++) {
            result = Math.min(result, dp[j]);
        }

        return result;
    }
}
