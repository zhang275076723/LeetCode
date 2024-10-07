package com.zhang.java;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2023/11/25 08:35
 * @Author zsy
 * @Description 到达角落需要移除障碍物的最小数目 bfs类比Problem407、Problem499、Problem505、Problem778、Problem847、Problem1129、Problem1293、Problem1368、Problem1631、Problem2045 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1293、Problem1334、Problem1368、Problem1462、Problem1514、Problem1631、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2203、Problem2473、Problem2662、Dijkstra
 * 给你一个下标从 0 开始的二维整数数组 grid ，数组大小为 m x n 。
 * 每个单元格都是两个值之一：
 * 0 表示一个 空 单元格，
 * 1 表示一个可以移除的 障碍物 。
 * 你可以向上、下、左、右移动，从一个空单元格移动到另一个空单元格。
 * 现在你需要从左上角 (0, 0) 移动到右下角 (m - 1, n - 1) ，返回需要移除的障碍物的 最小 数目。
 * <p>
 * 输入：grid = [[0,1,1],[1,1,0],[1,1,0]]
 * 输出：2
 * 解释：可以移除位于 (0, 1) 和 (0, 2) 的障碍物来创建从 (0, 0) 到 (2, 2) 的路径。
 * 可以证明我们至少需要移除两个障碍物，所以返回 2 。
 * 注意，可能存在其他方式来移除 2 个障碍物，创建出可行的路径。
 * <p>
 * 输入：grid = [[0,1,0,0,0],[0,1,0,1,0],[0,0,0,1,0]]
 * 输出：0
 * 解释：不移除任何障碍物就能从 (0, 0) 到 (2, 4) ，所以返回 0 。
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 10^5
 * 2 <= m * n <= 10^5
 * grid[i][j] 为 0 或 1
 * grid[0][0] == grid[m - 1][n - 1] == 0
 */
public class Problem2290 {
    public static void main(String[] args) {
        Problem2290 problem2290 = new Problem2290();
        int[][] grid = {
                {0, 1, 1},
                {1, 1, 0},
                {1, 1, 0}
        };
        System.out.println(problem2290.minimumObstacles(grid));
        System.out.println(problem2290.minimumObstacles2(grid));
        System.out.println(problem2290.minimumObstacles3(grid));
    }

    /**
     * bfs
     * 当前节点的邻接节点grid为1，则邻接节点为障碍物，当前节点到邻接节点边的权值为1；否则，当前节点到邻接节点边的权值为0
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int minimumObstacles(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到其他节点需要移除的最小障碍物数量数组
        int[][] remove = new int[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //remove数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                remove[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)需要移除的最小障碍物数量为grid[0][0]
        remove[0][0] = grid[0][0];

        //arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点需要移除的障碍物数量，注意：当前需要移除的障碍物数量不一定是需要移除的最小障碍物数量
        Queue<int[]> queue = new LinkedList<>();

        //节点(0,0)入队
        queue.offer(new int[]{0, 0, remove[0][0]});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点(x1,y1)需要移除的障碍物数量，注意：当前需要移除的障碍物数量不一定是需要移除的最小障碍物数量
            int curRemove = arr[2];

            //curRemove大于remove[x1][y1]，则当前节点(x1,y1)不能作为中间节点更新节点(0,0)到其他节点需要移除的最小障碍物数量，直接进行下次循环
            if (curRemove > remove[x1][y1]) {
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

                //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点需要移除的最小障碍物数量，更新remove[x2][y2]，节点(x2,y2)入队
                if (curRemove + grid[x2][y2] < remove[x2][y2]) {
                    remove[x2][y2] = curRemove + grid[x2][y2];
                    queue.offer(new int[]{x2, y2, remove[x2][y2]});
                }
            }
        }

        return remove[m - 1][n - 1];
    }

    /**
     * Dijkstra求节点(0,0)到节点(m-1,n-1)需要移除的最小障碍物数量
     * 当前节点的邻接节点grid为1，则邻接节点为障碍物，当前节点到邻接节点边的权值为1；否则，当前节点到邻接节点边的权值为0
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int minimumObstacles2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到其他节点需要移除的最小障碍物数量数组
        int[][] remove = new int[m][n];
        //节点访问数组，visited[i][j]为true，表示已经得到节点(0,0)到节点(i,j)需要移除的最小障碍物数量
        boolean[][] visited = new boolean[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //remove数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                remove[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)需要移除的最小障碍物数量为grid[0][0]
        remove[0][0] = grid[0][0];

        for (int i = 0; i < m * n; i++) {
            //初始化remove数组中未访问节点中选择距离节点(0,0)需要移除的最小障碍物数量的节点(x1,y1)
            int x1 = -1;
            int y1 = -1;

            //未访问节点中选择距离节点(0,0)需要移除的最小障碍物数量的节点(x1,y1)
            for (int j = 0; j < m * n; j++) {
                int x2 = j / n;
                int y2 = j % n;

                if (!visited[x2][y2] && ((x1 == -1 && y1 == -1) || (remove[x2][y2] < remove[x1][y1]))) {
                    x1 = x2;
                    y1 = y2;
                }
            }

            //设置节点(x1,y1)已访问，表示已经得到节点(0,0)到节点(x1,y1)需要移除的最小障碍物数量
            visited[x1][y1] = true;

            //已经得到节点(0,0)到节点(m-1,n-1)需要移除的最小障碍物数量，直接返回cost[m-1][n-1]
            if (x1 == m - 1 && y1 == n - 1) {
                return remove[m - 1][n - 1];
            }

            //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点需要移除的最小障碍物数量
            for (int j = 0; j < direction.length; j++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[j][0];
                int y2 = y1 + direction[j][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                if (!visited[x2][y2] && remove[x1][y1] + grid[x2][y2] < remove[x2][y2]) {
                    remove[x2][y2] =remove[x1][y1] + grid[x2][y2];
                }
            }
        }

        //遍历结束，没有找到节点(0,0)到节点(m-1,n-1)需要移除的最小障碍物数量，则返回-1
        return -1;
    }

    /**
     * 堆优化Dijkstra求节点(0,0)到节点(m-1,n-1)需要移除的最小障碍物数量
     * 当前节点的邻接节点grid为1，则邻接节点为障碍物，当前节点到邻接节点边的权值为1；否则，当前节点到邻接节点边的权值为0
     * 时间复杂度O(mn*log(mn))，空间复杂度O(mn)
     * (堆优化Dijkstra的时间复杂度O(mlogm)，其中m为图中边的个数，本题边的个数O(mn)，所以时间复杂度O(mn*log(mn)))
     *
     * @param grid
     * @return
     */
    public int minimumObstacles3(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到其他节点需要移除的最小障碍物数量数组
        int[][] remove = new int[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //remove数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                remove[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)需要移除的最小障碍物数量为grid[0][0]
        remove[0][0] = grid[0][0];

        //小根堆，arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点需要移除的障碍物数量，注意：当前需要移除的障碍物数量不一定是需要移除的最小障碍物数量
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });

        //节点(0,0)入堆
        priorityQueue.offer(new int[]{0, 0, remove[0][0]});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点(x1,y1)需要移除的障碍物数量，注意：当前需要移除的障碍物数量不一定是需要移除的最小障碍物数量
            int curRemove = arr[2];

            //curRemove大于remove[x1][y1]，则当前节点(x1,y1)不能作为中间节点更新节点(0,0)到其他节点需要移除的最小障碍物数量，直接进行下次循环
            if (curRemove > remove[x1][y1]) {
                continue;
            }

            //小根堆保证第一次访问到节点(m-1,n-1)，则得到节点(0,0)到节点(m-1,n-1)需要移除的最小障碍物数量，直接返回curRemove
            if (x1 == m - 1 && y1 == n - 1) {
                return curRemove;
            }

            //遍历节点(x1,y1)的邻接节点(x2,y2)
            for (int i = 0; i < direction.length; i++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点需要移除的最小障碍物数量，更新remove[x2][y2]，节点(x2,y2)入堆
                if (curRemove + grid[x2][y2] < remove[x2][y2]) {
                    remove[x2][y2] = curRemove + grid[x2][y2];
                    priorityQueue.offer(new int[]{x2, y2, remove[x2][y2]});
                }
            }
        }

        //遍历结束，没有找到节点(0,0)到节点(m-1,n-1)需要移除的最小障碍物数量，则返回-1
        return -1;
    }
}
