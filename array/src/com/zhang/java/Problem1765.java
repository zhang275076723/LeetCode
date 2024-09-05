package com.zhang.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2024/12/29 08:22
 * @Author zsy
 * @Description 地图中的最高点 多源bfs类比Problem286、Problem542、Problem994、Problem1162、Problem2812
 * 给你一个大小为 m x n 的整数矩阵 isWater ，它代表了一个由 陆地 和 水域 单元格组成的地图。
 * 如果 isWater[i][j] == 0 ，格子 (i, j) 是一个 陆地 格子。
 * 如果 isWater[i][j] == 1 ，格子 (i, j) 是一个 水域 格子。
 * 你需要按照如下规则给每个单元格安排高度：
 * 每个格子的高度都必须是非负的。
 * 如果一个格子是 水域 ，那么它的高度必须为 0 。
 * 任意相邻的格子高度差 至多 为 1 。当两个格子在正东、南、西、北方向上相互紧挨着，就称它们为相邻的格子。
 * （也就是说它们有一条公共边）
 * 找到一种安排高度的方案，使得矩阵中的最高高度值 最大 。
 * 请你返回一个大小为 m x n 的整数矩阵 height ，其中 height[i][j] 是格子 (i, j) 的高度。
 * 如果有多种解法，请返回 任意一个 。
 * <p>
 * 输入：isWater = [[0,1],[0,0]]
 * 输出：[[1,0],[2,1]]
 * 解释：上图展示了给各个格子安排的高度。
 * 蓝色格子是水域格，绿色格子是陆地格。
 * <p>
 * 输入：isWater = [[0,0,1],[1,0,0],[0,0,0]]
 * 输出：[[1,1,0],[0,1,1],[1,2,2]]
 * 解释：所有安排方案中，最高可行高度为 2 。
 * 任意安排方案中，只要最高高度为 2 且符合上述规则的，都为可行方案。
 * <p>
 * m == isWater.length
 * n == isWater[i].length
 * 1 <= m, n <= 1000
 * isWater[i][j] 要么是 0 ，要么是 1 。
 * 至少有 1 个水域格子。
 */
public class Problem1765 {
    public static void main(String[] args) {
        Problem1765 problem1765 = new Problem1765();
        int[][] isWater = {
                {0, 0, 1},
                {1, 0, 0},
                {0, 0, 0}
        };
        System.out.println(Arrays.deepToString(problem1765.highestPeak(isWater)));
    }

    /**
     * 多源bfs
     * 值为1的节点入队，bfs每次往外扩一层，得到当前节点的最大高度
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param isWater
     * @return
     */
    public int[][] highestPeak(int[][] isWater) {
        int m = isWater.length;
        int n = isWater[0].length;

        Queue<int[]> queue = new LinkedList<>();
        //结果数组，同时也作为访问数组，判断当前节点是否已访问
        int[][] result = new int[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //值为1的节点入队，当前节点的最大高度为0
                if (isWater[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                    result[i][j] = 0;
                } else {
                    //值为0的节点，初始化当前节点的高度为-1，表示没有得到当前节点的最大高度
                    result[i][j] = -1;
                }
            }
        }

        //bfs遍历过程中，当前层中节点的高度
        int height = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();
                int x1 = arr[0];
                int y1 = arr[1];

                for (int j = 0; j < direction.length; j++) {
                    int x2 = x1 + direction[j][0];
                    int y2 = y1 + direction[j][1];

                    //邻接节点越界，或者已经得到邻接节点的最大高度，直接进行下次循环
                    if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n || result[x2][y2] != -1) {
                        continue;
                    }

                    //邻接节点入队，邻接节点的最大高度为height+1
                    queue.offer(new int[]{x2, y2});
                    result[x2][y2] = height + 1;
                }
            }

            //bfs每次往外扩一层
            height++;
        }

        return result;
    }
}
