package com.zhang.java;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2023/11/24 08:08
 * @Author zsy
 * @Description 最小体力消耗路径 bfs类比Problem847、Problem1129、Problem1368、Problem2045 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1334、Problem1368、Problem1462、Problem1514、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2203、Problem2662、Dijkstra
 * 你准备参加一场远足活动。给你一个二维 rows x columns 的地图 heights ，其中 heights[row][col] 表示格子 (row, col) 的高度。
 * 一开始你在最左上角的格子 (0, 0) ，且你希望去最右下角的格子 (rows-1, columns-1) （注意下标从 0 开始编号）。
 * 你每次可以往 上，下，左，右 四个方向之一移动，你想要找到耗费 体力 最小的一条路径。
 * 一条路径耗费的 体力值 是路径上相邻格子之间 高度差绝对值 的 最大值 决定的。
 * 请你返回从左上角走到右下角的最小 体力消耗值 。
 * <p>
 * 输入：heights = [[1,2,2],[3,8,2],[5,3,5]]
 * 输出：2
 * 解释：路径 [1,3,5,3,5] 连续格子的差值绝对值最大为 2 。
 * 这条路径比路径 [1,2,2,2,5] 更优，因为另一条路径差值最大值为 3 。
 * <p>
 * 输入：heights = [[1,2,3],[3,8,4],[5,3,5]]
 * 输出：1
 * 解释：路径 [1,2,3,4,5] 的相邻格子差值绝对值最大为 1 ，比路径 [1,3,5,3,5] 更优。
 * <p>
 * 输入：heights = [
 * [1,2,1,1,1],
 * [1,2,1,2,1],
 * [1,2,1,2,1],
 * [1,2,1,2,1],
 * [1,1,1,2,1]
 * ]
 * 输出：0
 * 解释：上图所示路径不需要消耗任何体力。
 * <p>
 * rows == heights.length
 * columns == heights[i].length
 * 1 <= rows, columns <= 100
 * 1 <= heights[i][j] <= 10^6
 */
public class Problem1631 {
    public static void main(String[] args) {
        Problem1631 problem1631 = new Problem1631();
        int[][] heights = {
                {1, 2, 2},
                {3, 8, 2},
                {5, 3, 5}
        };
        System.out.println(problem1631.minimumEffortPath(heights));
        System.out.println(problem1631.minimumEffortPath2(heights));
        System.out.println(problem1631.minimumEffortPath3(heights));
    }

    /**
     * bfs
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param heights
     * @return
     */
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        //节点(0,0)到其他节点最小消耗的体力值数组
        int[][] cost = new int[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //cost数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)最小消耗的体力值为0
        cost[0][0] = 0;

        //arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点消耗的体力值，注意：当前消耗的体力值不一定是最小消耗的体力值
        Queue<int[]> queue = new LinkedList<>();

        //节点(0,0)入队
        queue.offer(new int[]{0, 0, 0});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点(x1,y1)消耗的体力值，注意：当前消耗的体力值不一定是最小消耗的体力值
            int curCost = arr[2];

            //curCost大于cost[x1][y1]，则当前节点(x1,y1)不能作为中间节点更新节点(0,0)到其他节点最小消耗的体力值，直接进行下次循环
            if (curCost > cost[x1][y1]) {
                continue;
            }

            //遍历节点(x1,y1)的邻接节点(x2,y2)
            for (int i = 0; i < direction.length; i++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                //节点(x1,y1)到邻接节点(x2,y2)消耗的体力值
                int curCost2 = Math.abs(heights[x1][y1] - heights[x2][y2]);

                //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点最小消耗的体力值，更新cost[x2][y2]，节点(x2,y2)入队
                if (Math.max(curCost, curCost2) < cost[x2][y2]) {
                    cost[x2][y2] = Math.max(curCost, curCost2);
                    queue.offer(new int[]{x2, y2, cost[x2][y2]});
                }
            }
        }

        return cost[m - 1][n - 1];
    }

    /**
     * Dijkstra求节点(0,0)到节点(m-1,n-1)最小消耗的体力值
     * 当前节点到邻接节点边的权值为两者的高度差
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param heights
     * @return
     */
    public int minimumEffortPath2(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        //节点(0,0)到其他节点最小消耗的体力值数组
        int[][] cost = new int[m][n];
        //节点访问数组，visited[i][j]为true，表示已经得到节点(0,0)到节点(i,j)最小消耗的体力值
        boolean[][] visited = new boolean[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //cost数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)最小消耗的体力值为0
        cost[0][0] = 0;

        for (int i = 0; i < m * n; i++) {
            //初始化cost数组中未访问节点中选择距离节点(0,0)最小消耗的体力值的节点(x1,y1)
            int x1 = -1;
            int y1 = -1;

            //未访问节点中选择距离节点(0,0)最小消耗的体力值的节点(x1,y1)
            for (int j = 0; j < m * n; j++) {
                int x2 = j / n;
                int y2 = j % n;

                if (!visited[x2][y2] && ((x1 == -1 && y1 == -1) || (cost[x2][y2] < cost[x1][y1]))) {
                    x1 = x2;
                    y1 = y2;

                }
            }

            //设置节点(x1,y1)已访问，表示已经得到节点(0,0)到节点(x1,y1)最小消耗的体力值
            visited[x1][y1] = true;

            //已经得到节点(0,0)到节点(m-1,n-1)最小消耗的体力值，直接返回cost[m-1][n-1]
            if (x1 == m - 1 && y1 == n - 1) {
                return cost[x1][y1];
            }

            //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点最小消耗的体力值
            for (int j = 0; j < direction.length; j++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[j][0];
                int y2 = y1 + direction[j][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                if (!visited[x2][y2]) {
                    //节点(x1,y1)到邻接节点(x2,y2)消耗的体力值
                    int curCost = Math.abs(heights[x1][y1] - heights[x2][y2]);
                    cost[x2][y2] = Math.min(cost[x2][y2], Math.max(cost[x1][y1], curCost));
                }
            }
        }

        //遍历结束，没有找到节点(0,0)到节点(m-1,n-1)最小消耗的体力值，则返回-1
        return -1;
    }

    /**
     * 堆优化Dijkstra求节点(0,0)到节点(m-1,n-1)最小消耗的体力值
     * 当前节点到邻接节点边的权值为两者的高度差
     * 时间复杂度O(mn*log(mn))，空间复杂度O(mn)
     * (堆优化Dijkstra的时间复杂度O(mlogm)，其中m为图中边的个数，本题边的个数O(mn)，所以时间复杂度O(mn*log(mn)))
     *
     * @param heights
     * @return
     */
    public int minimumEffortPath3(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        //节点(0,0)到其他节点最小消耗的体力值数组
        int[][] cost = new int[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //cost数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)最小消耗的体力值为0
        cost[0][0] = 0;

        //小根堆，arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点消耗的体力值，注意：当前消耗的体力值不一定是最小消耗的体力值
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });

        //节点(0,0)入堆
        priorityQueue.offer(new int[]{0, 0, 0});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点(x1,y1)消耗的体力值，注意：当前消耗的体力值不一定是最小消耗的体力值
            int curCost = arr[2];

            //curCost大于cost[x1][y1]，则当前节点(x1,y1)不能作为中间节点更新节点(0,0)到其他节点最小消耗的体力值，直接进行下次循环
            if (curCost > cost[x1][y1]) {
                continue;
            }

            //小根堆保证第一次访问到节点(m-1,n-1)，则得到节点(0,0)到节点(m-1,n-1)最小消耗的体力值，直接返回curCost
            if (x1 == m - 1 && y1 == n - 1) {
                return curCost;
            }

            //遍历节点(x1,y1)的邻接节点(x2,y2)
            for (int i = 0; i < direction.length; i++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                //节点(x1,y1)到邻接节点(x2,y2)消耗的体力值
                int curCost2 = Math.abs(heights[x1][y1] - heights[x2][y2]);

                //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点最小消耗的体力值，更新cost[x2][y2]，节点(x2,y2)入堆
                if (Math.max(curCost, curCost2) < cost[x2][y2]) {
                    cost[x2][y2] = Math.max(curCost, curCost2);
                    priorityQueue.offer(new int[]{x2, y2, cost[x2][y2]});
                }
            }
        }

        //遍历结束，没有找到节点(0,0)到节点(m-1,n-1)最小消耗的体力值，则返回-1
        return -1;
    }
}
