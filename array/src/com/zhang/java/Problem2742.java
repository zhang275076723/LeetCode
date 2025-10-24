package com.zhang.java;

/**
 * @Date 2025/10/22 19:19
 * @Author zsy
 * @Description 给墙壁刷油漆 01背包类比Problem416、Problem474、Problem494、Problem2291 动态规划类比
 * 给你两个长度为 n 下标从 0 开始的整数数组 cost 和 time ，分别表示给 n 堵不同的墙刷油漆需要的开销和时间。
 * 你有两名油漆匠：
 * 一位需要 付费 的油漆匠，刷第 i 堵墙需要花费 time[i] 单位的时间，开销为 cost[i] 单位的钱。
 * 一位 免费 的油漆匠，刷 任意 一堵墙的时间为 1 单位，开销为 0 。
 * 但是必须在付费油漆匠 工作 时，免费油漆匠才会工作。
 * 请你返回刷完 n 堵墙最少开销为多少。
 * <p>
 * 输入：cost = [1,2,3,2], time = [1,2,3,2]
 * 输出：3
 * 解释：下标为 0 和 1 的墙由付费油漆匠来刷，需要 3 单位时间。
 * 同时，免费油漆匠刷下标为 2 和 3 的墙，需要 2 单位时间，开销为 0 。
 * 总开销为 1 + 2 = 3 。
 * <p>
 * 输入：cost = [2,3,4,2], time = [1,1,1,1]
 * 输出：4
 * 解释：下标为 0 和 3 的墙由付费油漆匠来刷，需要 2 单位时间。
 * 同时，免费油漆匠刷下标为 1 和 2 的墙，需要 2 单位时间，开销为 0 。
 * 总开销为 2 + 2 = 4 。
 * <p>
 * 1 <= cost.length <= 500
 * cost.length == time.length
 * 1 <= cost[i] <= 10^6
 * 1 <= time[i] <= 500
 */
public class Problem2742 {
    //最大值为int最大值除以2，避免最大值加1在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem2742 problem2742 = new Problem2742();
        int[] cost = {1, 2, 3, 2};
        int[] time = {1, 2, 3, 2};
        System.out.println(problem2742.paintWalls(cost, time));
        System.out.println(problem2742.paintWalls2(cost, time));
        System.out.println(problem2742.paintWalls3(cost, time));
    }

    /**
     * 动态规划 01背包 容量最少为j的最小价值
     * dp[i][j]：前i面墙至少刷j面墙的最小开销
     * dp[i][j] = min(dp[i-1][j], dp[i-1][0] + cost[i-1])             (time[i-1]+1 < j)
     * dp[i][j] = min(dp[i-1][j], dp[i-1][j-time[i-1]-1] + cost[i-1]) (time[i-1]+1 >= j)
     * 第i面墙免费油漆匠刷，则前i-1面墙中存在付费油漆匠刷的墙，即dp[i][j] = dp[i-1][j]
     * 第i面墙付费油漆匠刷，则前i-1面墙中免费油漆匠刷可以刷time[i-1]面墙，即dp[i][j] = dp[i-1][j-time[i-1]-1]+cost[i-1]
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param cost
     * @param time
     * @return
     */
    public int paintWalls(int[] cost, int[] time) {
        int[][] dp = new int[cost.length + 1][cost.length + 1];

        //dp初始化，前0面墙至少刷0面墙的最小开销为0
        dp[0][0] = 0;

        //dp初始化，前0面墙不存在至少刷1-cost.length面墙的最小开销
        for (int j = 1; j <= cost.length; j++) {
            dp[0][j] = INF;
        }

        for (int i = 1; i <= cost.length; i++) {
            for (int j = 0; j <= cost.length; j++) {
                //注意：time[i-1]+1>j时，dp[i][j]还需要考虑到dp[i-1][0]，
                //即前i-1面墙至少刷0面墙的最小开销加上付费油漆匠第i面墙的开销cost[i-1]构成的最小开销
                if (time[i - 1] + 1 > j) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][0] + cost[i - 1]);
                } else {
                    //第i面墙付费油漆匠刷，则前i-1面墙中免费油漆匠刷可以刷time[i-1]面墙
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - time[i - 1] - 1] + cost[i - 1]);
                }
            }
        }

        return dp[cost.length][cost.length];
    }

    /**
     * 动态规划优化，使用滚动数组
     * dp[j]：前i面墙至少刷j面墙的最小开销
     * dp[j] = min(dp[j], dp[0] + cost[i-1])             (time[i-1]+1 < j)
     * dp[j] = min(dp[j], dp[j-time[i-1]-1] + cost[i-1]) (time[i-1]+1 >= j)
     * 第i面墙免费油漆匠刷，则前i-1面墙中存在付费油漆匠刷的墙，即dp[j] = dp[j]
     * 第i面墙付费油漆匠刷，则前i-1面墙中免费油漆匠刷可以刷time[i-1]面墙，即dp[j] = dp[j-time[i-1]-1]+cost[i-1]
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param cost
     * @param time
     * @return
     */
    public int paintWalls2(int[] cost, int[] time) {
        int[] dp = new int[cost.length + 1];

        //dp初始化，前0面墙至少刷0面墙的最小开销为0
        dp[0] = 0;

        //dp初始化，前0面墙不存在至少刷1-cost.length面墙的最小开销
        for (int j = 1; j <= cost.length; j++) {
            dp[j] = INF;
        }

        for (int i = 1; i <= cost.length; i++) {
            //注意：当前dp[j]会使用到前面的dp，所以逆序遍历
            for (int j = cost.length; j >= 0; j--) {
                if (time[i - 1] + 1 > j) {
                    dp[j] = Math.min(dp[j], dp[0] + cost[i - 1]);
                } else {
                    dp[j] = Math.min(dp[j], dp[j - time[i - 1] - 1] + cost[i - 1]);
                }
            }
        }

        return dp[cost.length];
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param cost
     * @param time
     * @return
     */
    public int paintWalls3(int[] cost, int[] time) {
        int[][] dp = new int[cost.length + 1][cost.length + 1];

        for (int i = 0; i <= cost.length; i++) {
            for (int j = 0; j <= cost.length; j++) {
                //初始化为-1，表示当前dp未访问
                dp[i][j] = -1;
            }
        }

        dfs(cost.length, cost.length, cost, time, dp);

        return dp[cost.length][cost.length];
    }

    private int dfs(int i, int j, int[] cost, int[] time, int[][] dp) {
        if (i == 0) {
            //前0面墙至少刷0面墙的最小开销为0
            if (j == 0) {
                dp[i][j] = 0;
                return dp[i][j];
            } else {
                //前0面墙不存在至少刷1-cost.length面墙的最小开销
                dp[i][j] = INF;
                return dp[i][j];
            }
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        if (time[i - 1] + 1 > j) {
            dp[i][j] = Math.min(dfs(i - 1, j, cost, time, dp), dfs(i - 1, 0, cost, time, dp) + cost[i - 1]);
            return dp[i][j];
        }

        dp[i][j] = Math.min(dfs(i - 1, j, cost, time, dp), dfs(i - 1, j - time[i - 1] - 1, cost, time, dp) + cost[i - 1]);
        return dp[i][j];
    }
}
