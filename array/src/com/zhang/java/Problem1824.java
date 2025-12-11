package com.zhang.java;

/**
 * @Date 2024/11/15 09:04
 * @Author zsy
 * @Description 最少侧跳次数 动态规划类比 跳跃问题类比
 * 给你一个长度为 n 的 3 跑道道路 ，它总共包含 n + 1 个 点 ，编号为 0 到 n 。
 * 一只青蛙从 0 号点第二条跑道 出发 ，它想要跳到点 n 处。
 * 然而道路上可能有一些障碍。
 * 给你一个长度为 n + 1 的数组 obstacles ，其中 obstacles[i] （取值范围从 0 到 3）表示在点 i 处的 obstacles[i] 跑道上有一个障碍。
 * 如果 obstacles[i] == 0 ，那么点 i 处没有障碍。
 * 任何一个点的三条跑道中 最多有一个 障碍。
 * 比方说，如果 obstacles[2] == 1 ，那么说明在点 2 处跑道 1 有障碍。
 * 这只青蛙从点 i 跳到点 i + 1 且跑道不变的前提是点 i + 1 的同一跑道上没有障碍。
 * 为了躲避障碍，这只青蛙也可以在 同一个 点处 侧跳 到 另外一条 跑道（这两条跑道可以不相邻），但前提是跳过去的跑道该点处没有障碍。
 * 比方说，这只青蛙可以从点 3 处的跑道 3 跳到点 3 处的跑道 1 。
 * 这只青蛙从点 0 处跑道 2 出发，并想到达点 n 处的 任一跑道 ，请你返回 最少侧跳次数 。
 * 注意：点 0 处和点 n 处的任一跑道都不会有障碍。
 * <p>
 * 输入：obstacles = [0,1,2,3,0]
 * 输出：2
 * 解释：最优方案如上图箭头所示。总共有 2 次侧跳（红色箭头）。
 * 注意，这只青蛙只有当侧跳时才可以跳过障碍（如上图点 2 处所示）。
 * <         x
 * < start         x
 * <                     x
 * <   0     1     2     3     4
 * <p>
 * 输入：obstacles = [0,1,1,3,3,0]
 * 输出：0
 * 解释：跑道 2 没有任何障碍，所以不需要任何侧跳。
 * <p>
 * 输入：obstacles = [0,2,1,0,3,0]
 * 输出：2
 * 解释：最优方案如上图所示。总共有 2 次侧跳。
 * <p>
 * obstacles.length == n + 1
 * 1 <= n <= 5 * 10^5
 * 0 <= obstacles[i] <= 3
 * obstacles[0] == obstacles[n] == 0
 */
public class Problem1824 {
    //最大值为int最大值除以2，避免相加在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem1824 problem1824 = new Problem1824();
        int[] obstacles = {0, 1, 2, 3, 0};
        System.out.println(problem1824.minSideJumps(obstacles));
        System.out.println(problem1824.minSideJumps2(obstacles));
        System.out.println(problem1824.minSideJumps3(obstacles));
    }

    /**
     * 动态规划
     * dp[i][j]：从节点(0,1)跳到节点(n,j)的最少侧跳次数
     * dp[i][j] = INF                      (obstacles[i] == j+1)
     * dp[i][j] = min(dp[i-1][j],dp[i][k]+1) (obstacles[i] != j+1，k != j) (注意：要先赋值dp[i][j]为dp[i-1][j])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param obstacles
     * @return
     */
    public int minSideJumps(int[] obstacles) {
        int n = obstacles.length - 1;
        int[][] dp = new int[n + 1][3];

        //dp初始化，青蛙初始在节点(0,1)
        dp[0][0] = 1;
        dp[0][1] = 0;
        dp[0][2] = 1;

        for (int i = 1; i < obstacles.length; i++) {
            //从节点(i-1,j)跳到节点(i,j)
            for (int j = 0; j < 3; j++) {
                //当前节点(i,j)为障碍物，无法到达
                if (obstacles[i] == j + 1) {
                    dp[i][j] = INF;
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }

            //从节点(i,k)跳到节点(i,j) (k!=j)
            for (int j = 0; j < 3; j++) {
                if (obstacles[i] == j + 1) {
                    continue;
                }

                for (int k = 0; k < 3; k++) {
                    if (k != j) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + 1);
                    }
                }
            }
        }

        //节点(n,0)、(n,1)、(n,2)中的最小值，即为最少侧跳次数
        return Math.min(dp[n][0], Math.min(dp[n][1], dp[n][2]));
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param obstacles
     * @return
     */
    public int minSideJumps2(int[] obstacles) {
        int n = obstacles.length - 1;
        int[] dp = new int[3];

        //dp初始化，青蛙初始在节点(0,1)
        dp[0] = 1;
        dp[1] = 0;
        dp[2] = 1;

        for (int i = 1; i < obstacles.length; i++) {
            //从节点(i-1,j)跳到节点(i,j)
            for (int j = 0; j < 3; j++) {
                //当前节点(i,j)为障碍物，无法到达
                if (obstacles[i] == j + 1) {
                    dp[j] = INF;
                }
            }

            //从节点(i,k)跳到节点(i,j) (k!=j)
            for (int j = 0; j < 3; j++) {
                if (obstacles[i] == j + 1) {
                    continue;
                }

                for (int k = 0; k < 3; k++) {
                    dp[j] = Math.min(dp[j], dp[k] + 1);
                }
            }
        }

        //节点(n,0)、(n,1)、(n,2)中的最小值，即为最少侧跳次数
        return Math.min(dp[0], Math.min(dp[1], dp[2]));
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param obstacles
     * @return
     */
    public int minSideJumps3(int[] obstacles) {
        int n = obstacles.length - 1;
        int[][] dp = new int[n + 1][3];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 3; j++) {
                //-1表示节点(i,j)未访问
                dp[i][j] = -1;
            }
        }

        dfs(n, 0, obstacles, dp);
        dfs(n, 1, obstacles, dp);
        dfs(n, 2, obstacles, dp);

        //节点(n,0)、(n,1)、(n,2)中的最小值，即为最少侧跳次数
        return Math.min(dp[n][0], Math.min(dp[n][1], dp[n][2]));
    }

    private int dfs(int i, int j, int[] obstacles, int[][] dp) {
        if (i == 0) {
            //青蛙初始在节点(0,1)，即到节点(0,1)最少侧跳次数为0
            if (j == 1) {
                dp[i][j] = 0;
            } else {
                //青蛙初始在节点(0,1)，即到节点(0,0)、(0,2)最少侧跳次数为1
                dp[i][j] = 1;
            }

            return dp[i][j];
        }

        //当前dp已访问，直接返回当前dp
        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        //当前节点(i,j)为障碍物，无法到达
        if (obstacles[i] == j + 1) {
            dp[i][j] = INF;
            return dp[i][j];
        }

        //从节点(i-1,j)跳到节点(i,j)
        dp[i][j] = dfs(i - 1, j, obstacles, dp);

        //从节点(i,k)跳到节点(i,j) (k!=j)
        for (int k = 0; k < 3; k++) {
            if (k != j) {
                dp[i][j] = Math.min(dp[i][j], dfs(i, k, obstacles, dp) + 1);
            }
        }

        return dp[i][j];
    }
}
