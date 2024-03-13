package com.zhang.java;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2023/12/18 08:58
 * @Author zsy
 * @Description 接雨水 II 京东笔试题 类比Problem42、Problem218、Problem699 bfs类比Problem499、Problem505、Problem847、Problem1129、Problem1293、Problem1368、Problem1631、Problem2045、Problem2290 优先队列类比Problem253、Problem630
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
     * water[i][j] = max(heightMap[i][j],water[i-1][j],water[i+1][j],water[i][j-1],water[i][j+1])
     * water[i][j]-heightMap[i][j]即为当前位置能够接的雨水
     * 当前节点(i,j)所接雨水之后的高度water[i][j]小于邻接节点(x,y)所接雨水之后的高度water[x][y]，
     * 则修改邻接节点(x,y)所接雨水之后的高度water[x][y]=max(heightMap[x][y],water[i][j])
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param heightMap
     * @return
     */
    public int trapRainWater(int[][] heightMap) {
        int m = heightMap.length;
        int n = heightMap[0].length;
        //heightMap中的最大高度
        int maxHeight = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxHeight = Math.max(maxHeight, heightMap[i][j]);
            }
        }

        //接雨水之后的高度数组，water[i][j]-heightMap[i][j]即为当前位置能够接的雨水
        //water[i][j]始终不小于heightMap[i][j]，即当前位置能够接的雨水不小于当前位置的高度
        int[][] water = new int[m][n];
        Queue<int[]> queue = new LinkedList<>();

        //water边缘初始化，并且边缘节点加入队列
        for (int i = 0; i < m; i++) {
            water[i][0] = heightMap[i][0];
            water[i][n - 1] = heightMap[i][n - 1];
            queue.offer(new int[]{i, 0});
            queue.offer(new int[]{i, n - 1});
        }

        //water边缘初始化，并且边缘节点加入队列
        for (int j = 1; j < n - 1; j++) {
            water[0][j] = heightMap[0][j];
            water[m - 1][j] = heightMap[m - 1][j];
            queue.offer(new int[]{0, j});
            queue.offer(new int[]{m - 1, j});
        }

        //water内部元素初始化，初始化为maxHeight，通过bfs一步步缩小
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                water[i][j] = maxHeight;
            }
        }

        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            int i = arr[0];
            int j = arr[1];

            for (int k = 0; k < direction.length; k++) {
                int x = i + direction[k][0];
                int y = j + direction[k][1];

                if (x < 0 || x >= m || y < 0 || y >= n) {
                    continue;
                }

                //当前节点(i,j)所接雨水之后的高度water[i][j]小于邻接节点(x,y)所接雨水之后的高度water[x][y]，
                //则修改邻接节点(x,y)所接雨水之后的高度water[x][y]=max(heightMap[x][y],water[i][j])，邻接节点(x,y)入队
                if (water[i][j] < water[x][y]) {
                    //water[x][y]始终不能小于heightMap[x][y]，即邻接节点(x,y)接不到水
                    water[x][y] = Math.max(heightMap[x][y], water[i][j]);
                    queue.offer(new int[]{x, y});
                }
            }
        }

        int result = 0;

        //边缘不会接到雨水，water[i][j]-heightMap[i][j]即为当前位置能够接的雨水
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                result = result + water[i][j] - heightMap[i][j];
            }
        }

        return result;
    }

    /**
     * 优先队列，小根堆
     * 边缘不会接到雨水，所有边缘节点先入堆，最小高度节点(i,j)出堆，
     * 如果当前节点(i,j)所接雨水之后的高度water(注意：不是节点的高度)大于当前节点邻接节点(x,y)的高度heightMap[x][y]，
     * 则邻接节点能接到的雨水，能接到的雨水为当前节点所接雨水之后的高度water减去当前节点邻接节点的高度heightMap[x][y]，
     * 并且邻接节点所接雨水之后的高度入堆(注意：不是节点的高度)，邻接节点所接雨水之后的高度为max(heightMap[x][y],water)
     * 时间复杂度O(mnlog(mn))，空间复杂度O(mn)
     *
     * @param heightMap
     * @return
     */
    public int trapRainWater2(int[][] heightMap) {
        int m = heightMap.length;
        int n = heightMap[0].length;

        //小根堆
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });
        boolean[][] visited = new boolean[m][n];

        //边缘节点加入小根堆
        for (int i = 0; i < m; i++) {
            priorityQueue.offer(new int[]{i, 0, heightMap[i][0]});
            priorityQueue.offer(new int[]{i, n - 1, heightMap[i][n - 1]});
            visited[i][0] = true;
            visited[i][n - 1] = true;
        }

        //边缘节点加入小根堆
        for (int j = 1; j < n - 1; j++) {
            priorityQueue.offer(new int[]{0, j, heightMap[0][j]});
            priorityQueue.offer(new int[]{m - 1, j, heightMap[m - 1][j]});
            visited[0][j] = true;
            visited[m - 1][j] = true;
        }

        int result = 0;
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            int i = arr[0];
            int j = arr[1];
            //当前节点接雨水之后的高度
            int water = arr[2];

            for (int k = 0; k < direction.length; k++) {
                int x = i + direction[k][0];
                int y = j + direction[k][1];

                if (x < 0 || x >= m || y < 0 || y >= n || visited[x][y]) {
                    continue;
                }

                //当前节点(i,j)所接雨水之后的高度water(注意：不是节点的高度)大于当前节点邻接节点(x,y)的高度heightMap[x][y]，
                //则邻接节点能接到的雨水，能接到的雨水为当前节点所接雨水之后的高度water减去当前节点邻接节点的高度heightMap[x][y]
                if (water > heightMap[x][y]) {
                    result = result + water - heightMap[x][y];
                }

                //邻接节点所接雨水之后的高度入堆(注意：不是节点的高度)
                priorityQueue.offer(new int[]{x, y, Math.max(heightMap[x][y], water)});
                visited[x][y] = true;
            }
        }

        return result;
    }
}
