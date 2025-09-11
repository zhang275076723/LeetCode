package com.zhang.java;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2023/12/18 08:58
 * @Author zsy
 * @Description 接雨水 II 京东机试题 字节面试题 类比Problem42、Problem218、Problem699 bfs类比Problem490、Problem499、Problem505、Problem778、Problem847、Problem1129、Problem1293、Problem1368、Problem1631、Problem2045、Problem2290 优先队列类比Problem253、Problem630
 * 给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。
 * <p>
 * 输入: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[3,2,1,3,2,4]]
 * 输出: 4
 * 解释: 下雨后，雨水将会被上图蓝色的方块中。总的接雨水量为1+2+1=4。
 * <p>
 * 输入: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
 * 输出: 10
 * <p>
 * m == heightMap.length
 * n == heightMap[i].length
 * 1 <= m, n <= 200
 * 0 <= heightMap[i][j] <= 2 * 10^4
 */
public class Problem407 {
    public static void main(String[] args) {
        Problem407 problem407 = new Problem407();
//        int[][] heightMap = {
//                {1, 4, 3, 1, 3, 2},
//                {3, 2, 1, 3, 2, 4},
//                {2, 3, 3, 2, 3, 1}
//        };
        int[][] heightMap = {
                {12, 13, 4, 5},
                {13, 4, 3, 12},
                {13, 8, 10, 12},
                {12, 13, 12, 12},
                {13, 13, 13, 13}
        };
        System.out.println(problem407.trapRainWater(heightMap));
        System.out.println(problem407.trapRainWater2(heightMap));
    }

    /**
     * bfs
     * water[i][j]：节点(i,j)接雨水之后的高度
     * water[x2][y2] = max(heightMap[x2][y2],water[x1][y1]) (节点(x2,y2)为当前节点(x1,y1)的邻接节点，并且water[x2][y2]>water[x1][y1])
     * water[i][j]-heightMap[i][j]即为当前节点(i,j)能够接的雨水
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param heightMap
     * @return
     */
    public int trapRainWater(int[][] heightMap) {
        int m = heightMap.length;
        int n = heightMap[0].length;

        //接雨水之后的高度数组，water[i][j]-heightMap[i][j]即为当前节点(i,j)能够接的雨水
        int[][] water = new int[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //非边缘water[i][j]初始化，初始化为int最大值表示当前节点还没有接到雨水
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                water[i][j] = Integer.MAX_VALUE;
            }
        }

        Queue<int[]> queue = new LinkedList<>();

        //边缘节点初始化，边缘节点接雨水高度为当前节点高度，并且边缘节点入列
        for (int i = 0; i < m; i++) {
            water[i][0] = heightMap[i][0];
            water[i][n - 1] = heightMap[i][n - 1];
            queue.offer(new int[]{i, 0});
            queue.offer(new int[]{i, n - 1});
        }

        //边缘节点初始化，边缘节点接雨水高度为当前节点高度，并且边缘节点入列
        for (int j = 1; j < n - 1; j++) {
            water[0][j] = heightMap[0][j];
            water[m - 1][j] = heightMap[m - 1][j];
            queue.offer(new int[]{0, j});
            queue.offer(new int[]{m - 1, j});
        }

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            int x1 = arr[0];
            int y1 = arr[1];

            for (int i = 0; i < direction.length; i++) {
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                //当前节点(x1,y1)接雨水之后的高度water[x1][y1]小于邻接节点(x2,y2)接雨水之后的高度water[x2][y2]，
                //则更新邻接节点(x2,y2)接雨水之后的高度，邻接节点(x2,y2)入队
                if (water[x1][y1] < water[x2][y2]) {
                    //water[x2][y2]始终不能小于heightMap[x2][y2]，即邻接节点(x2,y2)最差情况接不到雨水
                    water[x2][y2] = Math.max(heightMap[x2][y2], water[x1][y1]);
                    queue.offer(new int[]{x2, y2});
                }
            }
        }

        int result = 0;

        //边缘不会接到雨水，water[i][j]-heightMap[i][j]即为当前节点(i,j)能够接的雨水
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                result = result + water[i][j] - heightMap[i][j];
            }
        }

        return result;
    }

    /**
     * 优先队列，小根堆
     * 边缘节点不会接到雨水，所有边缘节点入堆，最小高度节点(x1,y1)出堆，
     * 如果当前节点(x1,y1)接雨水之后的高度waterHeight大于当前节点邻接节点(x2,y2)的高度heightMap[x2][y2]，
     * 则邻接节点能接到的雨水，能够接的雨水为waterHeight-heightMap[x2][y2]，
     * 邻接节点入堆，邻接节点接雨水之后的高度为max(heightMap[x2][y2],waterHeight)
     * 时间复杂度O(mnlog(mn))，空间复杂度O(mn)
     *
     * @param heightMap
     * @return
     */
    public int trapRainWater2(int[][] heightMap) {
        int m = heightMap.length;
        int n = heightMap[0].length;

        //小根堆，arr[0]：当前节点横坐标，arr[1]：当前节点纵坐标，arr[2]：当前节点接雨水之后的高度
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });
        boolean[][] visited = new boolean[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //边缘节点入堆
        for (int i = 0; i < m; i++) {
            priorityQueue.offer(new int[]{i, 0, heightMap[i][0]});
            priorityQueue.offer(new int[]{i, n - 1, heightMap[i][n - 1]});
            visited[i][0] = true;
            visited[i][n - 1] = true;
        }

        //边缘节点入堆
        for (int j = 1; j < n - 1; j++) {
            priorityQueue.offer(new int[]{0, j, heightMap[0][j]});
            priorityQueue.offer(new int[]{m - 1, j, heightMap[m - 1][j]});
            visited[0][j] = true;
            visited[m - 1][j] = true;
        }

        int result = 0;

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            int x1 = arr[0];
            int y1 = arr[1];
            //当前节点接雨水之后的高度
            int waterHeight = arr[2];

            for (int i = 0; i < direction.length; i++) {
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n || visited[x2][y2]) {
                    continue;
                }

                //当前节点(x1,y1)接雨水之后的高度waterHeight大于当前节点邻接节点(x2,y2)的高度heightMap[x2][y2]，
                //则邻接节点能接到的雨水，能够接的雨水为waterHeight-heightMap[x2][y2]
                if (waterHeight > heightMap[x2][y2]) {
                    result = result + waterHeight - heightMap[x2][y2];
                }

                //邻接节点入堆
                priorityQueue.offer(new int[]{x2, y2, Math.max(heightMap[x2][y2], waterHeight)});
                visited[x2][y2] = true;
            }
        }

        return result;
    }
}
