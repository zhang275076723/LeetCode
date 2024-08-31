package com.zhang.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2024/12/15 08:08
 * @Author zsy
 * @Description 01 矩阵 类比Problem1162
 * 给定一个由 0 和 1 组成的矩阵 mat ，请输出一个大小相同的矩阵，其中每一个格子是 mat 中对应位置元素到最近的 0 的距离。
 * 两个相邻元素间的距离为 1 。
 * <p>
 * 输入：mat = [[0,0,0],[0,1,0],[0,0,0]]
 * 输出：[[0,0,0],[0,1,0],[0,0,0]]
 * <p>
 * 输入：mat = [[0,0,0],[0,1,0],[1,1,1]]
 * 输出：[[0,0,0],[0,1,0],[1,2,1]]
 * <p>
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 10^4
 * 1 <= m * n <= 10^4
 * mat[i][j] is either 0 or 1.
 * mat 中至少有一个 0
 */
public class Problem542 {
    public static void main(String[] args) {
        Problem542 problem542 = new Problem542();
        int[][] mat = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        System.out.println(Arrays.deepToString(problem542.updateMatrix(mat)));
    }

    /**
     * bfs
     * 从1开始bfs，如果从0开始bfs时间复杂度O((mn)^2)，超时
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param mat
     * @return
     */
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        //bfs当前层中节点1到最近的0的距离
        int distance = 1;
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        int[][] result = new int[m][n];

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();
                int x = arr[0];
                int y = arr[1];

                for (int j = 0; j < direction.length; j++) {
                    int x2 = x + direction[j][0];
                    int y2 = y + direction[j][1];

                    if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n || visited[x2][y2]) {
                        continue;
                    }

                    queue.offer(new int[]{x2, y2});
                    visited[x2][y2] = true;
                    result[x2][y2] = distance;
                }
            }

            distance++;
        }

        return result;
    }
}
