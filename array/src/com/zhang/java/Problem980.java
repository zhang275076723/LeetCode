package com.zhang.java;

/**
 * @Date 2023/8/5 08:41
 * @Author zsy
 * @Description 不同路径 III 类比Problem62、Problem63、Problem64、Problem874、Offer13
 * 在二维网格 grid 上，有 4 种类型的方格：
 * 1 表示起始方格。且只有一个起始方格。
 * 2 表示结束方格，且只有一个结束方格。
 * 0 表示我们可以走过的空方格。
 * -1 表示我们无法跨越的障碍。
 * 返回在四个方向（上、下、左、右）上行走时，从起始方格到结束方格的不同路径的数目。
 * 每一个无障碍方格都要通过一次，但是一条路径中不能重复通过同一个方格。
 * <p>
 * 输入：[
 * [1,0,0,0],
 * [0,0,0,0],
 * [0,0,2,-1]
 * ]
 * 输出：2
 * 解释：我们有以下两条路径：
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
 * 2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)
 * <p>
 * 输入：[
 * [1,0,0,0],
 * [0,0,0,0],
 * [0,0,0,2]
 * ]
 * 输出：4
 * 解释：我们有以下四条路径：
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
 * 2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
 * 3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
 * 4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)
 * <p>
 * 输入：[
 * [0,1],
 * [2,0]
 * ]
 * 输出：0
 * 解释：
 * 没有一条路能完全穿过每一个空的方格一次。
 * 请注意，起始和结束方格可以位于网格中的任意位置。
 * <p>
 * 1 <= grid.length * grid[0].length <= 20
 */
public class Problem980 {
    public static void main(String[] args) {
        Problem980 problem980 = new Problem980();
//        int[][] grid = {
//                {1, 0, 0, 0},
//                {0, 0, 0, 0},
//                {0, 0, 2, -1}
//        };
        int[][] grid = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 2}
        };
        System.out.println(problem980.uniquePathsIII(grid));
    }

    /**
     * dfs
     * 注意：从起始节点到终止节点经过所有的空节点才是一条路径，如果一条路径没有经过某个空节点，则不是一条路径
     * 时间复杂度O(4^(mn))，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int uniquePathsIII(int[][] grid) {
        //空节点的数量
        int count0 = 0;
        //起始下标
        int startIndex0 = -1;
        int startIndex1 = -1;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    count0++;
                } else if (grid[i][j] == 1) {
                    startIndex0 = i;
                    startIndex1 = j;
                }
            }
        }

        return dfs(startIndex0, startIndex1, count0, 0,
                grid, new boolean[grid.length][grid[0].length]);
    }

    private int dfs(int i, int j, int count0, int curCount0, int[][] grid, boolean[][] visited) {
        //当前位置(i,j)不在grid范围内，或(i,j)是障碍物，或(i,j)已被访问，则(i,j)不能达到，返回0
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == -1 || visited[i][j]) {
            return 0;
        }

        //到达结尾位置，判断所有0是否都遍历过
        if (grid[i][j] == 2) {
            //起始位置(startIndex0,startIndex1)也当作一个0，即多算了一个0，需要减去一个0
            if (curCount0 - 1 == count0) {
                return 1;
            } else {
                return 0;
            }
        }

        int count = 0;
        visited[i][j] = true;

        count = count + dfs(i - 1, j, count0, curCount0 + 1, grid, visited);
        count = count + dfs(i + 1, j, count0, curCount0 + 1, grid, visited);
        count = count + dfs(i, j - 1, count0, curCount0 + 1, grid, visited);
        count = count + dfs(i, j + 1, count0, curCount0 + 1, grid, visited);

        visited[i][j] = false;

        return count;
    }
}
