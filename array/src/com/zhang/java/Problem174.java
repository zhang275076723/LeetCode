package com.zhang.java;

/**
 * @Date 2025/2/10 08:50
 * @Author zsy
 * @Description 地下城游戏 大疆机试题 类比Problem62、Problem63、Problem64、Problem874、Problem980、Offer13
 * 恶魔们抓住了公主并将她关在了地下城 dungeon 的 右下角 。
 * 地下城是由 m x n 个房间组成的二维网格。
 * 我们英勇的骑士最初被安置在 左上角 的房间里，他必须穿过地下城并通过对抗恶魔来拯救公主。
 * 骑士的初始健康点数为一个正整数。如果他的健康点数在某一时刻降至 0 或以下，他会立即死亡。
 * 有些房间由恶魔守卫，因此骑士在进入这些房间时会失去健康点数（若房间里的值为负整数，则表示骑士将损失健康点数）；
 * 其他房间要么是空的（房间里的值为 0），要么包含增加骑士健康点数的魔法球（若房间里的值为正整数，则表示骑士将增加健康点数）。
 * 为了尽快解救公主，骑士决定每次只 向右 或 向下 移动一步。
 * 返回确保骑士能够拯救到公主所需的最低初始健康点数。
 * 注意：任何房间都可能对骑士的健康点数造成威胁，也可能增加骑士的健康点数，包括骑士进入的左上角房间以及公主被监禁的右下角房间。
 * <p>
 * 输入：dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]
 * 输出：7
 * 解释：如果骑士遵循最佳路径：右 -> 右 -> 下 -> 下 ，则骑士的初始健康点数至少为 7 。
 * <p>
 * 输入：dungeon = [[0]]
 * 输出：1
 * <p>
 * m == dungeon.length
 * n == dungeon[i].length
 * 1 <= m, n <= 200
 * -1000 <= dungeon[i][j] <= 1000
 */
public class Problem174 {
    public static void main(String[] args) {
        Problem174 problem174 = new Problem174();
        int[][] dungeon = {
                {-2, -3, 3},
                {-5, -10, 1},
                {10, 30, -5}
        };
        System.out.println(problem174.calculateMinimumHP(dungeon));
    }

    /**
     * 动态规划
     * dp[i][j]：从房间(i,j)出发，能够拯救到公主所需的最低初始健康点数
     * dp[i][j] = max(min(dp[i+1][j],dp[i][j+1])-dungeon[i][j],1)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=dungeon.length，n=dungeon[0].length)
     *
     * @param dungeon
     * @return
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;

        int[][] dp = new int[m][n];
        //dp初始化
        dp[m - 1][n - 1] = Math.max(1 - dungeon[m - 1][n - 1], 1);

        //n-1列dp初始化
        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = Math.max(dp[i + 1][n - 1] - dungeon[i][n - 1], 1);
        }

        //m-1行dp初始化
        for (int j = n - 2; j >= 0; j--) {
            dp[m - 1][j] = Math.max(dp[m - 1][j + 1] - dungeon[m - 1][j], 1);
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = Math.max(Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j], 1);
            }
        }

        return dp[0][0];
    }
}
