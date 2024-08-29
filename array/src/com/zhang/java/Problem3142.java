package com.zhang.java;

/**
 * @Date 2024/12/11 08:36
 * @Author zsy
 * @Description 判断矩阵是否满足条件 类比Problem3122
 * 给你一个大小为 m x n 的二维矩阵 grid 。
 * 你需要判断每一个格子 grid[i][j] 是否满足：
 * 如果它下面的格子存在，那么它需要等于它下面的格子，也就是 grid[i][j] == grid[i + 1][j] 。
 * 如果它右边的格子存在，那么它需要不等于它右边的格子，也就是 grid[i][j] != grid[i][j + 1] 。
 * 如果 所有 格子都满足以上条件，那么返回 true ，否则返回 false 。
 * <p>
 * 输入：grid = [[1,0,2],[1,0,2]]
 * 输出：true
 * 解释：
 * 网格图中所有格子都符合条件。
 * <p>
 * 输入：grid = [[1,1,1],[0,0,0]]
 * 输出：false
 * 解释：
 * 同一行中的格子值都相等。
 * <p>
 * 输入：grid = [[1],[2],[3]]
 * 输出：false
 * 解释：
 * 同一列中的格子值不相等。
 * <p>
 * 1 <= n, m <= 10
 * 0 <= grid[i][j] <= 9
 */
public class Problem3142 {
    public static void main(String[] args) {
        Problem3142 problem3142 = new Problem3142();
        int[][] grid = {
                {1, 1, 1},
                {0, 0, 0}
        };
        System.out.println(problem3142.satisfiesConditions(grid));
    }

    /**
     * 模拟
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param grid
     * @return
     */
    public boolean satisfiesConditions(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i + 1 < grid.length && grid[i][j] != grid[i + 1][j]) {
                    return false;
                }

                if (j + 1 < grid[0].length && grid[i][j] == grid[i][j + 1]) {
                    return false;
                }
            }
        }

        return true;
    }
}
