package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2025/1/14 08:55
 * @Author zsy
 * @Description 获取食物的最短路径 类比Problem490、Problem499、Problem505、Problem2258
 * 你现在很饿，想要尽快找东西吃。
 * 你需要找到最短的路径到达一个食物所在的格子。
 * 给定一个 m x n 的字符矩阵 grid ，包含下列不同类型的格子：
 * '*' 是你的位置。矩阵中有且只有一个 '*' 格子。
 * '#' 是食物。矩阵中可能存在多个食物。
 * 'O' 是空地，你可以穿过这些格子。
 * 'X' 是障碍，你不可以穿过这些格子。
 * 返回你到任意食物的最短路径的长度。如果不存在你到任意食物的路径，返回 -1。
 * <p>
 * 输入： grid = [
 * ["X","X","X","X","X","X"],
 * ["X","*","O","O","O","X"],
 * ["X","O","O","#","O","X"],
 * ["X","X","X","X","X","X"]
 * ]
 * 输出： 3
 * 解释： 要拿到食物，你需要走 3 步。
 * <p>
 * 输入： grid = [
 * ["X","X","X","X","X"],
 * ["X","*","X","O","X"],
 * ["X","O","X","#","X"],
 * ["X","X","X","X","X"]
 * ]
 * 输出： -1
 * 解释： 你不可能拿到食物。
 * <p>
 * 输入: grid = [
 * ["X","X","X","X","X","X","X","X"],
 * ["X","*","O","X","O","#","O","X"],
 * ["X","O","O","X","O","O","X","X"],
 * ["X","O","O","O","O","#","O","X"],
 * ["X","X","X","X","X","X","X","X"]
 * ]
 * 输出: 6
 * 解释: 这里有多个食物。拿到下边的食物仅需走 6 步。
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 200
 * grid[row][col] 是 '*'、 'X'、 'O' 或 '#' 。
 * grid 中有且只有一个 '*' 。
 */
public class Problem1730 {
    /**
     * dfs找到食物的最短路径的长度
     */
    private int minDistance = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Problem1730 problem1730 = new Problem1730();
        char[][] grid = {
                {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                {'X', '*', 'O', 'X', 'O', '#', 'O', 'X'},
                {'X', 'O', 'O', 'X', 'O', 'O', 'X', 'X'},
                {'X', 'O', 'O', 'O', 'O', '#', 'O', 'X'},
                {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}
        };
        System.out.println(problem1730.getFood(grid));
    }

    /**
     * bfs
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int getFood(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            //跳出循环标志位
            boolean flag = false;

            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '*') {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                    flag = true;
                    break;
                }
            }

            //只有一个起始位置，当找到起始位置时，直接跳出循环，不需要继续遍历
            if (flag) {
                break;
            }
        }

        //找到食物的最短路径的长度
        int distance = 0;
        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();
                int x1 = arr[0];
                int y1 = arr[1];

                for (int j = 0; j < direction.length; j++) {
                    int x2 = x1 + direction[j][0];
                    int y2 = y1 + direction[j][1];

                    if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n || grid[x2][y2] == 'X' || visited[x2][y2]) {
                        continue;
                    }

                    //找到了食物，返回到当前食物的最短距离distance+1
                    if (grid[x2][y2] == '#') {
                        return distance + 1;
                    }

                    queue.offer(new int[]{x2, y2});
                    visited[x2][y2] = true;
                }
            }

            //bfs每次往外扩一层
            distance++;
        }

        //遍历结束，则无法找到食物，返回-1
        return -1;
    }
}
