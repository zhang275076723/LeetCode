package com.zhang.java;

/**
 * @Date 2023/9/29 08:54
 * @Author zsy
 * @Description 粉刷房子 III 类比Problem256、Problem265 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
 * 在一个小城市里，有 m 个房子排成一排，你需要给每个房子涂上 n 种颜色之一（颜色编号为 1 到 n ）。
 * 有的房子去年夏天已经涂过颜色了，所以这些房子不可以被重新涂色。
 * 我们将连续相同颜色尽可能多的房子称为一个街区。
 * （比方说 houses = [1,2,2,3,3,2,1,1] ，它包含 5 个街区  [{1}, {2,2}, {3,3}, {2}, {1,1}] 。）
 * 给你一个数组 houses ，一个 m * n 的矩阵 cost 和一个整数 target ，其中：
 * houses[i]：是第 i 个房子的颜色，0 表示这个房子还没有被涂色。
 * cost[i][j]：是将第 i 个房子涂成颜色 j+1 的花费。
 * 请你返回房子涂色方案的最小总花费，使得每个房子都被涂色后，恰好组成 target 个街区。
 * 如果没有可用的涂色方案，请返回 -1 。
 * <p>
 * 输入：houses = [0,0,0,0,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
 * 输出：9
 * 解释：房子涂色方案为 [1,2,2,1,1]
 * 此方案包含 target = 3 个街区，分别是 [{1}, {2,2}, {1,1}]。
 * 涂色的总花费为 (1 + 1 + 1 + 1 + 5) = 9。
 * <p>
 * 输入：houses = [0,2,1,2,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
 * 输出：11
 * 解释：有的房子已经被涂色了，在此基础上涂色方案为 [2,2,1,2,2]
 * 此方案包含 target = 3 个街区，分别是 [{2,2}, {1}, {2,2}]。
 * 给第一个和最后一个房子涂色的花费为 (10 + 1) = 11。
 * <p>
 * 输入：houses = [0,0,0,0,0], cost = [[1,10],[10,1],[1,10],[10,1],[1,10]], m = 5, n = 2, target = 5
 * 输出：5
 * <p>
 * 输入：houses = [3,1,2,3], cost = [[1,1,1],[1,1,1],[1,1,1],[1,1,1]], m = 4, n = 3, target = 3
 * 输出：-1
 * 解释：房子已经被涂色并组成了 4 个街区，分别是 [{3},{1},{2},{3}] ，无法形成 target = 3 个街区。
 * <p>
 * m == houses.length == cost.length
 * n == cost[i].length
 * 1 <= m <= 100
 * 1 <= n <= 20
 * 1 <= target <= m
 * 0 <= houses[i] <= n
 * 1 <= cost[i][j] <= 10^4
 */
public class Problem1473 {
    public static void main(String[] args) {
        Problem1473 problem1473 = new Problem1473();
//        int[] houses = {0, 0, 0, 0, 0};
//        int[][] cost = {{1, 10}, {10, 1}, {10, 1}, {1, 10}, {5, 1}};
//        int m = 5;
//        int n = 2;
//        int target = 3;
        int[] houses = {0, 2, 1, 2, 0};
        int[][] cost = {{1, 10}, {10, 1}, {10, 1}, {1, 10}, {5, 1}};
        int m = 5;
        int n = 2;
        int target = 3;
        System.out.println(problem1473.minCost(houses, cost, m, n, target));
    }

    /**
     * 动态规划
     * dp[i][j][k]：前i个房子，最后一个房子粉刷成第j种颜色，并且最后一个房子是第k个街区的最小花费
     * (房子、颜色的下标索引从0开始，街区的下标索引从1开始)
     * 房子的个数：[0,m-1]
     * 粉刷的颜色：[0,n-1]
     * 街区的个数：[1,target]
     * houses[i] == -1：表示当前房子未被粉刷上颜色，即原houses数组中houses[i]0变为-1
     * dp[i][j][k] = dp[i-1][j][k] + cost[i][j]        (houses[i] == -1，并且粉刷之后houses[i] == houses[i-1])
     * dp[i][j][k] = min(dp[i-1][l][k-1] + cost[i][j]) (houses[i] == -1，并且粉刷之后houses[i] != houses[i-1]) (0 <= l < n，并且l != j)
     * dp[i][j][k] = Integer.MAX_VALUE                 (houses[i] != -1，houses[i] != j)
     * dp[i][j][k] = dp[i-1][j][k]                     (houses[i] != -1，houses[i] == houses[i-1] == j)
     * dp[i][j][k] = min(dp[i-1][l][k-1])              (houses[i] != -1，houses[i] != houses[i-1]，houses[i] == j) (0 <= l < n，并且l != j)
     * 时间复杂度O(mn^2*target)，空间复杂度O(mn*target)
     *
     * @param houses
     * @param cost
     * @param m
     * @param n
     * @param target
     * @return
     */
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        //houses[i] == -1：表示当前房子未被粉刷上颜色，即原houses数组中houses[i]0变为-1
        for (int i = 0; i < m; i++) {
            houses[i]--;
        }

        int[][][] dp = new int[m][n][target + 1];
        //设置为一个较大的数表示当前情况不存在，不能设置为int最大值，避免int最大值加上其他int溢出
        int INF = (int) 1e9;

        //dp初始化
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //初始化k=0，因为dp[i][j][k]会使用到dp[i-1][l][k-1]，使k-1为0
                dp[i][j][0] = INF;
            }
        }

        //dp初始化，i=0的情况
        for (int j = 0; j < n; j++) {
            for (int k = 1; k <= target; k++) {
                //前0个房子，最多只能分为1个街区
                if (k == 1) {
                    //第0个房子之前未被粉刷上颜色
                    if (houses[0] == -1) {
                        dp[0][j][k] = cost[0][j];
                    } else {
                        //第0个房子之前被粉刷上颜色，但颜色不等于第j种颜色
                        if (houses[0] != j) {
                            dp[0][j][k] = INF;
                        } else {
                            //第0个房子之前被粉刷上颜色，并且颜色等于第j种颜色
                            dp[0][j][k] = 0;
                        }
                    }
                } else {
                    //前0个房子，最多只能分为1个街区，超过1个街区则无法划分
                    dp[0][j][k] = INF;
                }
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 1; k <= target; k++) {
                    //第i个房子之前未被粉刷上颜色
                    if (houses[i] == -1) {
                        //第i个房子粉刷之后颜色和第i-1个房子颜色相等
                        dp[i][j][k] = dp[i - 1][j][k] + cost[i][j];

                        //第i个房子粉刷之后颜色和第i-1个房子颜色不相等
                        for (int l = 0; l < n; l++) {
                            if (l == j) {
                                continue;
                            }

                            dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][l][k - 1] + cost[i][j]);
                        }
                    } else {
                        //第i个房子之前被粉刷上颜色，但颜色不等于第j种颜色
                        if (houses[i] != j) {
                            dp[i][j][k] = INF;
                        } else {
                            //第i个房子之前被粉刷上颜色，并且颜色等于第j种颜色

                            //第i个房子颜色和第i-1个房子颜色相等
                            dp[i][j][k] = dp[i - 1][j][k];

                            //第i个房子颜色和第i-1个房子不颜色相等
                            for (int l = 0; l < n; l++) {
                                if (l == j) {
                                    continue;
                                }

                                dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][l][k - 1]);
                            }
                        }
                    }
                }
            }
        }

        int result = INF;

        for (int j = 0; j < n; j++) {
            result = Math.min(result, dp[m - 1][j][target]);
        }

        return result == INF ? -1 : result;
    }
}
