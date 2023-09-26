package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2023/9/29 08:45
 * @Author zsy
 * @Description 粉刷房子 II 类比Problem256、Problem1473 动态规划类比Problem198、Problem213、Problem256、Problem279、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
 * 假如有一排房子共有 n 幢，每个房子可以被粉刷成 k 种颜色中的一种。
 * 房子粉刷成不同颜色的花费成本也是不同的。
 * 你需要粉刷所有的房子并且使其相邻的两个房子颜色不能相同。
 * 每个房子粉刷成不同颜色的花费以一个 n x k 的矩阵表示。
 * 例如，costs[0][0] 表示第 0 幢房子粉刷成 0 号颜色的成本；costs[1][2] 表示第 1 幢房子粉刷成 2 号颜色的成本，以此类推。
 * 返回 粉刷完所有房子的最低成本 。
 * <p>
 * 输入: costs = [[1,5,3],[2,9,4]]
 * 输出: 5
 * 解释:
 * 将房子 0 刷成 0 号颜色，房子 1 刷成 2 号颜色。花费: 1 + 4 = 5;
 * 或者将 房子 0 刷成 2 号颜色，房子 1 刷成 0 号颜色。花费: 3 + 2 = 5.
 * <p>
 * 输入: costs = [[1,3],[2,4]]
 * 输出: 5
 * <p>
 * costs.length == n
 * costs[i].length == k
 * 1 <= n <= 100
 * 2 <= k <= 20
 * 1 <= costs[i][j] <= 20
 */
public class Problem265 {
    public static void main(String[] args) {
        Problem265 problem265 = new Problem265();
        int[][] costs = {{1, 5, 3}, {2, 9, 4}};
        System.out.println(problem265.minCost(costs));
        System.out.println(problem265.minCost2(costs));
    }

    /**
     * 动态规划
     * dp[i][j]：前i个房子，最后一个房子粉刷成第j种颜色的最小花费(房子、颜色的下标索引从0开始)
     * dp[i][j] = min(dp[i-1][m] + costs[i][j]) (0 <= m < k，并且m != j)
     * 时间复杂度O(nk^2)，空间复杂度O(nk)
     *
     * @param costs
     * @return
     */
    public int minCost(int[][] costs) {
        int k = costs[0].length;
        int[][] dp = new int[costs.length][k];

        //dp初始化
        for (int j = 0; j < k; j++) {
            dp[0][j] = costs[0][j];
        }

        for (int i = 1; i < costs.length; i++) {
            for (int j = 0; j < k; j++) {
                dp[i][j] = Integer.MAX_VALUE;

                for (int m = 0; m < k; m++) {
                    if (m == j) {
                        continue;
                    }

                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][m] + costs[i][j]);
                }
            }
        }

        int result = Integer.MAX_VALUE;

        for (int j = 0; j < k; j++) {
            result = Math.min(result, dp[costs.length - 1][j]);
        }

        return result;
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(nk^2)，空间复杂度O(k)
     *
     * @param costs
     * @return
     */
    public int minCost2(int[][] costs) {
        int k = costs[0].length;
        int[] dp = new int[k];

        //dp初始化
        for (int j = 0; j < k; j++) {
            dp[j] = costs[0][j];
        }

        for (int i = 1; i < costs.length; i++) {
            //保存粉刷到前i-1个房子的dp状态，用于本次dp更新
            int[] temp = Arrays.copyOf(dp, k);

            for (int j = 0; j < k; j++) {
                dp[j] = Integer.MAX_VALUE;

                for (int m = 0; m < k; m++) {
                    if (m == j) {
                        continue;
                    }

                    dp[j] = Math.min(dp[j], temp[m] + costs[i][j]);
                }
            }
        }

        int result = Integer.MAX_VALUE;

        for (int j = 0; j < k; j++) {
            result = Math.min(result, dp[j]);
        }

        return result;
    }
}
