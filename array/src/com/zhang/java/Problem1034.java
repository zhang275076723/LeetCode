package com.zhang.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/2/11 08:30
 * @Author zsy
 * @Description 边界着色 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem490、Problem499、Problem505、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1162、Problem1254、Problem1568、Problem1905、Offer12
 * 给你一个大小为 m x n 的整数矩阵 grid ，表示一个网格。
 * 另给你三个整数 row、col 和 color 。
 * 网格中的每个值表示该位置处的网格块的颜色。
 * 两个网格块属于同一 连通分量 需满足下述全部条件：
 * 两个网格块颜色相同
 * 在上、下、左、右任意一个方向上相邻
 * 连通分量的边界 是指连通分量中满足下述条件之一的所有网格块：
 * 在上、下、左、右任意一个方向上与不属于同一连通分量的网格块相邻
 * 在网格的边界上（第一行/列或最后一行/列）
 * 请你使用指定颜色 color 为所有包含网格块 grid[row][col] 的 连通分量的边界 进行着色，并返回最终的网格 grid 。
 * <p>
 * 输入：grid = [[1,1],[1,2]], row = 0, col = 0, color = 3
 * 输出：[[3,3],[3,2]]
 * <p>
 * 输入：grid = [[1,2,2],[2,3,2]], row = 0, col = 1, color = 3
 * 输出：[[1,3,3],[2,3,3]]
 * <p>
 * 输入：grid = [[1,1,1],[1,1,1],[1,1,1]], row = 1, col = 1, color = 2
 * 输出：[[2,2,2],[2,1,2],[2,2,2]]
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 50
 * 1 <= grid[i][j], color <= 1000
 * 0 <= row < m
 * 0 <= col < n
 */
public class Problem1034 {
    public static void main(String[] args) {
        Problem1034 problem1034 = new Problem1034();
        int[][] grid = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
        int row = 1;
        int col = 1;
        int color = 2;
//        System.out.println(Arrays.deepToString(problem1034.colorBorder(grid, row, col, color)));
        System.out.println(Arrays.deepToString(problem1034.colorBorder2(grid, row, col, color)));
    }

    /**
     * dfs
     * 从节点(row,col)dfs遍历，将包含节点(row,col)的连通分量的边界修改为newColor
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @param row
     * @param col
     * @param newColor
     * @return
     */
    public int[][] colorBorder(int[][] grid, int row, int col, int newColor) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return grid;
        }

        dfs(row, col, grid[row][col], newColor, grid, new boolean[grid.length][grid[0].length]);

        return grid;
    }

    /**
     * bfs
     * 从节点(row,col)bfs遍历，将包含节点(row,col)的连通分量的边界修改为newColor
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @param row
     * @param col
     * @param newColor
     * @return
     */
    public int[][] colorBorder2(int[][] grid, int row, int col, int newColor) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return grid;
        }

        int originColor = grid[row][col];
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{row, col});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            //当前节点(arr[0],arr[1])不在矩阵范围之内，或者当前节点(arr[0],arr[1])已被访问，
            //或者当前节点(arr[0],arr[1])不为originColor，直接进行下次循环
            if (arr[0] < 0 || arr[0] >= grid.length || arr[1] < 0 || arr[1] >= grid[0].length ||
                    visited[arr[0]][arr[1]] || grid[arr[0]][arr[1]] != originColor) {
                continue;
            }

            //如果当前节点(arr[0],arr[1])是连通分量的边界节点，则修改当前节点的值为newColor
            for (int k = 0; k < direction.length; k++) {
                int x = arr[0] + direction[k][0];
                int y = arr[1] + direction[k][1];

                //当前节点(arr[0],arr[1])的邻接顶点(x,y)不在矩阵范围之内，或者邻接顶点(x,y)未被访问，且不为originColor，
                //则当前节点(arr[0],arr[1])是连通分量的边界节点
                if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length ||
                        (!visited[x][y] && grid[x][y] != originColor)) {
                    //修改当前节点的值
                    grid[arr[0]][arr[1]] = newColor;
                    break;
                }
            }

            //当前节点已被访问
            visited[arr[0]][arr[1]] = true;

            //当前节点的上下左右加入队列
            queue.offer(new int[]{arr[0] - 1, arr[1]});
            queue.offer(new int[]{arr[0] + 1, arr[1]});
            queue.offer(new int[]{arr[0], arr[1] - 1});
            queue.offer(new int[]{arr[0], arr[1] + 1});
        }

        return grid;
    }

    private void dfs(int i, int j, int originColor, int newColor, int[][] grid, boolean[][] visited) {
        //当前节点(i,j)不在矩阵范围之内，或者当前节点(i,j)已被访问，或者当前节点(i,j)不为originColor，直接返回
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j] || grid[i][j] != originColor) {
            return;
        }

        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        //如果当前节点(i,j)是连通分量的边界节点，则修改当前节点的值为newColor
        for (int k = 0; k < direction.length; k++) {
            int x = i + direction[k][0];
            int y = j + direction[k][1];

            //当前节点(i,j)的邻接顶点(x,y)不在矩阵范围之内，或者邻接顶点(x,y)未被访问，且不为originColor，
            //则当前节点(i,j)是连通分量的边界节点
            if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length ||
                    (!visited[x][y] && grid[x][y] != originColor)) {
                //修改当前节点的值
                grid[i][j] = newColor;
                break;
            }
        }

        //当前节点已被访问
        visited[i][j] = true;

        //往上下左右找
        dfs(i - 1, j, originColor, newColor, grid, visited);
        dfs(i + 1, j, originColor, newColor, grid, visited);
        dfs(i, j - 1, originColor, newColor, grid, visited);
        dfs(i, j + 1, originColor, newColor, grid, visited);
    }
}
