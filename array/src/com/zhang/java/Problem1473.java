package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2023/9/29 08:54
 * @Author zsy
 * @Description 粉刷房子 III 类比Problem256、Problem265、Problem276 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
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
    //最大值为int最大值除以2，避免相加在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem1473 problem1473 = new Problem1473();
//        int[] houses = {0, 0, 0, 0, 0};
//        int[][] cost = {{1, 10}, {10, 1}, {10, 1}, {1, 10}, {5, 1}};
//        int m = 5;
//        int n = 2;
//        int target = 3;
//        int[] houses = {0, 2, 1, 2, 0};
//        int[][] cost = {{1, 10}, {10, 1}, {10, 1}, {1, 10}, {5, 1}};
//        int m = 5;
//        int n = 2;
//        int target = 3;
//        int[] houses = {1};
//        int[][] cost = {{2}};
//        int m = 1;
//        int n = 1;
//        int target = 1;
        int[] houses = {0, 0, 0, 3};
        int[][] cost = {{2, 2, 5}, {1, 5, 5}, {5, 1, 2}, {5, 2, 5}};
        int m = 4;
        int n = 3;
        int target = 3;
        System.out.println(problem1473.minCost(houses, cost, m, n, target));
        System.out.println(problem1473.minCost2(houses, cost, m, n, target));
        System.out.println(problem1473.minCost3(houses, cost, m, n, target));
    }

    /**
     * 动态规划
     * dp[i][j][k]：前i个房子最后一个房子粉刷成第j种颜色，第i个房子是第k个街区的最小花费
     * dp[i][j][k] = min(dp[i-1][j][k]+cost[i-1][j-1], dp[i-1][l][k-1]+cost[i-1][j-1]) (houses[i-1] == 0) (1 <= l <= n && l != j)
     * dp[i][j][k] = INF                                                               (houses[i-1] != 0 && houses[i-1] != j)
     * dp[i][j][k] = min(dp[i-1][j][k], dp[i-1][l][k-1])                               (houses[i-1] != 0 && houses[i-1] == j) (1 <= l <= n && l != j)
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
        int[][][] dp = new int[m + 1][n + 1][target + 1];

        //dp初始化，初始化为int最大值INF
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= target; k++) {
                    dp[i][j][k] = INF;
                }
            }
        }

        //dp初始化，前0个房子最后一个房子粉刷成第j种颜色，第0个房子是第0个街区的最小花费为0
        for (int j = 0; j <= n; j++) {
            dp[0][j][0] = 0;
        }

        //dp初始化，第1个房子未被涂色，前1个房子最后一个房子粉刷成第j种颜色，第1个房子是第1个街区的最小花费为cost[0][j-1]
        if (houses[0] == 0) {
            for (int j = 1; j <= n; j++) {
                dp[1][j][1] = cost[0][j - 1];
            }
        } else {
            //dp初始化，第1个房子已经被涂色，前1个房子最后一个房子粉刷成第j种颜色，第1个房子是第1个街区的最小花费为0
            dp[1][houses[0]][1] = 0;
        }

        //第i个房子
        //注意：第1个房子已经初始化，从i=2开始遍历
        for (int i = 2; i <= m; i++) {
            //粉刷成第j种颜色
            for (int j = 1; j <= n; j++) {
                //第i个房子是第k个街区
                for (int k = 1; k <= target; k++) {
                    //第i个房子未被涂色
                    if (houses[i - 1] == 0) {
                        //第i个房子粉刷成第j种颜色，和第i-1个房子的颜色相等，第i个房子的街区和第i-1个房子的街区相等
                        dp[i][j][k] = dp[i - 1][j][k] + cost[i - 1][j - 1];

                        //第i个房子粉刷成第j种颜色，和第i-1个房子的颜色第l种颜色不相等，第i个房子的街区和第i-1个房子的街区不相等
                        for (int l = 1; l <= n; l++) {
                            if (l == j) {
                                continue;
                            }

                            dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][l][k - 1] + cost[i - 1][j - 1]);
                        }
                    } else {
                        //第i个房子已经被涂色

                        //第i个房子已经被涂色的颜色和第i个房子要粉刷的第j种颜色不相等，则当前情况不存在
                        if (houses[i - 1] != j) {
                            dp[i][j][k] = INF;
                        } else {
                            //第i个房子的颜色和第i个房子要粉刷的第j种颜色相等

                            //第i个房子的颜色和第i-1个房子的颜色相等，第i个房子的街区和第i-1个房子的街区相等
                            dp[i][j][k] = dp[i - 1][j][k];

                            //第i个房子的颜色和第i-1个房子的颜色第l种颜色不相等，第i个房子的街区和第i-1个房子的街区不相等
                            for (int l = 1; l <= n; l++) {
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

        for (int j = 1; j <= n; j++) {
            result = Math.min(result, dp[m][j][target]);
        }

        return result == INF ? -1 : result;
    }

    /**
     * 动态规划优化，使用滚动数组
     * dp[j][k]：前i个房子最后一个房子粉刷成第j种颜色，第i个房子是第k个街区的最小花费
     * dp[i][j][k] = min(dp[i-1][j][k]+cost[i-1][j-1], dp[i-1][l][k-1]+cost[i-1][j-1]) (houses[i-1] == 0) (1 <= l <= n && l != j)
     * dp[i][j][k] = INF                                                               (houses[i-1] != 0 && houses[i-1] != j)
     * dp[i][j][k] = min(dp[i-1][j][k], dp[i-1][l][k-1])                               (houses[i-1] != 0 && houses[i-1] == j) (1 <= l <= n && l != j)
     * 时间复杂度O(mn^2*target)，空间复杂度O(n*target)
     *
     * @param houses
     * @param cost
     * @param m
     * @param n
     * @param target
     * @return
     */
    public int minCost2(int[] houses, int[][] cost, int m, int n, int target) {
        int[][] dp = new int[n + 1][target + 1];

        //dp初始化，初始化为int最大值INF
        for (int j = 0; j <= n; j++) {
            for (int k = 0; k <= target; k++) {
                dp[j][k] = INF;
            }
        }

        //注意：和三维dp不同，因为初始化了i=1的情况，所以i=0，dp[0][j][0]不需要初始化
//        for (int j = 0; j <= n; j++) {
//            dp[j][0] = 0;
//        }

        //dp初始化，第1个房子未被涂色，前1个房子最后一个房子粉刷成第j种颜色，第1个房子是第1个街区的最小花费为cost[0][j-1]
        if (houses[0] == 0) {
            for (int j = 1; j <= n; j++) {
                dp[j][1] = cost[0][j - 1];
            }
        } else {
            //dp初始化，第1个房子已经被涂色，前1个房子最后一个房子粉刷成第j种颜色，第1个房子是第1个街区的最小花费为0
            dp[houses[0]][1] = 0;
        }

        //第i个房子
        //注意：第1个房子已经初始化，从i=2开始遍历
        for (int i = 2; i <= m; i++) {
            //保存上次dp，用于更新本次dp
            int[][] temp = new int[n + 1][target + 1];

            //注意：二维数组通过Arrays.copyOf必须逐行复制，不能整体复制，避免浅拷贝
            for (int j = 0; j <= n; j++) {
                temp[j] = Arrays.copyOf(dp[j], target + 1);
            }

            //粉刷成第j种颜色
            for (int j = 1; j <= n; j++) {
                //第i个房子是第k个街区
                for (int k = 1; k <= target; k++) {
                    //第i个房子未被涂色
                    if (houses[i - 1] == 0) {
                        //第i个房子粉刷成第j种颜色，和第i-1个房子的颜色相等，第i个房子的街区和第i-1个房子的街区相等
                        dp[j][k] = temp[j][k] + cost[i - 1][j - 1];

                        //第i个房子粉刷成第j种颜色，和第i-1个房子的颜色第l种颜色不相等，第i个房子的街区和第i-1个房子的街区不相等
                        for (int l = 1; l <= n; l++) {
                            if (l == j) {
                                continue;
                            }

                            dp[j][k] = Math.min(dp[j][k], temp[l][k - 1] + cost[i - 1][j - 1]);
                        }
                    } else {
                        //第i个房子已经被涂色

                        //第i个房子已经被涂色的颜色和第i个房子要粉刷的第j种颜色不相等，则当前情况不存在
                        if (houses[i - 1] != j) {
                            dp[j][k] = INF;
                        } else {
                            //第i个房子的颜色和第i个房子要粉刷的第j种颜色相等

                            //第i个房子的颜色和第i-1个房子的颜色相等，第i个房子的街区和第i-1个房子的街区相等
                            dp[j][k] = temp[j][k];

                            //第i个房子的颜色和第i-1个房子的颜色第l种颜色不相等，第i个房子的街区和第i-1个房子的街区不相等
                            for (int l = 1; l <= n; l++) {
                                if (l == j) {
                                    continue;
                                }

                                dp[j][k] = Math.min(dp[j][k], temp[l][k - 1]);
                            }
                        }
                    }
                }
            }
        }

        int result = INF;

        for (int j = 1; j <= n; j++) {
            result = Math.min(result, dp[j][target]);
        }

        return result == INF ? -1 : result;
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(mn^2*target)，空间复杂度O(mn*target)
     *
     * @param houses
     * @param cost
     * @param m
     * @param n
     * @param target
     * @return
     */
    public int minCost3(int[] houses, int[][] cost, int m, int n, int target) {
        int[][][] dp = new int[m + 1][n + 1][target + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= target; k++) {
                    //初始化为-1，表示当前dp未访问
                    dp[i][j][k] = -1;
                }
            }
        }

        int result = INF;

        for (int j = 1; j <= n; j++) {
            result = Math.min(result, dfs(m, j, target, n, houses, cost, dp));
        }

        return result == INF ? -1 : result;
    }

    private int dfs(int i, int j, int k, int n, int[] houses, int[][] cost, int[][][] dp) {
        //不存在前i个房子最后一个房子粉刷成第j种颜色，第i个房子是第负数个街区的最小花费
        if (k < 0) {
            return INF;
        }

        if (i == 0) {
            //前0个房子最后一个房子粉刷成第j种颜色，第0个房子是第0个街区的最小花费为0
            if (k == 0) {
                dp[i][j][k] = 0;
                return dp[i][j][k];
            } else {
                //不存在前0个房子最后一个房子粉刷成第j种颜色，第0个房子是第1-target个街区的最小花费
                dp[i][j][k] = INF;
                return dp[i][j][k];
            }
        }

        if (i == 1) {
            //不存在前1个房子最后一个房子粉刷成第j种颜色，第1个房子是第0个或第2-target个街区的最小花费
            if (k != 1) {
                dp[i][j][k] = INF;
                return dp[i][j][k];
            }

            if (houses[i - 1] == 0) {
                //不存在前1个房子最后一个房子粉刷成第0种颜色，第1个房子是第1个街区的最小花费
                if (j == 0) {
                    dp[i][j][k] = INF;
                    return dp[i][j][k];
                } else {
                    //前1个房子最后一个房子粉刷成第1-n种颜色，第1个房子是第1个街区的最小花费为cost[0][j-1]
                    dp[i][j][k] = cost[0][j - 1];
                    return dp[i][j][k];
                }
            } else {
                //前1个房子最后一个房子已经被涂色的颜色houses[i-1]和第j种颜色相等，第1个房子是第1个街区的最小花费为0
                if (houses[i - 1] == j) {
                    dp[i][j][k] = 0;
                    return dp[i][j][k];
                } else {
                    //不存在前1个房子最后一个房子已经被涂色的颜色houses[i-1]和第j种颜色不相等，第1个房子是第1个街区的最小花费
                    dp[i][j][k] = INF;
                    return dp[i][j][k];
                }
            }
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][j][k] != -1) {
            return dp[i][j][k];
        }

        //第i个房子未被涂色
        if (houses[i - 1] == 0) {
            //第i个房子粉刷成第j种颜色，和第i-1个房子的颜色相等，第i个房子的街区和第i-1个房子的街区相等
            dp[i][j][k] = dfs(i - 1, j, k, n, houses, cost, dp) + cost[i - 1][j - 1];

            //第i个房子粉刷成第j种颜色，和第i-1个房子的颜色第l种颜色不相等，第i个房子的街区和第i-1个房子的街区不相等
            for (int l = 1; l <= n; l++) {
                if (l == j) {
                    continue;
                }

                dp[i][j][k] = Math.min(dp[i][j][k], dfs(i - 1, l, k - 1, n, houses, cost, dp) + cost[i - 1][j - 1]);
            }
        } else {
            //第i个房子已经被涂色

            //第i个房子已经被涂色的颜色和第i个房子要粉刷的第j种颜色不相等，则当前情况不存在
            if (houses[i - 1] != j) {
                dp[i][j][k] = INF;
            } else {
                //第i个房子的颜色和第i-1个房子的颜色相等，第i个房子的街区和第i-1个房子的街区相等
                dp[i][j][k] = dfs(i - 1, j, k, n, houses, cost, dp);

                //第i个房子的颜色和第i-1个房子的颜色第l种颜色不相等，第i个房子的街区和第i-1个房子的街区不相等
                for (int l = 1; l <= n; l++) {
                    if (l == j) {
                        continue;
                    }

                    dp[i][j][k] = Math.min(dp[i][j][k], dfs(i - 1, l, k - 1, n, houses, cost, dp));
                }
            }
        }

        return dp[i][j][k];
    }
}
