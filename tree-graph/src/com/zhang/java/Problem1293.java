package com.zhang.java;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2023/11/26 08:45
 * @Author zsy
 * @Description 网格中的最短路径 美团机试题 带限制条件的单元最短路径类比Problem787、Problem1928、Problem2093 Bellman-Ford类比Problem568、Problem787、Problem1928、Problem2093 bfs类比Problem499、Problem505、Problem847、Problem1129、Problem1368、Problem1631、Problem2045、Problem2290 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1334、Problem1368、Problem1462、Problem1514、Problem1631、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2203、Problem2290、Problem2473、Problem2662、Dijkstra
 * 给你一个 m * n 的网格，其中每个单元格不是 0（空）就是 1（障碍物）。
 * 每一步，您都可以在空白单元格中上、下、左、右移动。
 * 如果您 最多 可以消除 k 个障碍物，请找出从左上角 (0, 0) 到右下角 (m-1, n-1) 的最短路径，并返回通过该路径所需的步数。
 * 如果找不到这样的路径，则返回 -1 。
 * <p>
 * 输入：grid = [[0,0,0],[1,1,0],[0,0,0],[0,1,1],[0,0,0]], k = 1
 * 输出：6
 * 解释：
 * 不消除任何障碍的最短路径是 10。
 * 消除位置 (3,2) 处的障碍后，最短路径是 6 。该路径是 (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2).
 * <p>
 * 输入：grid = [[0,1,1],[1,1,1],[1,0,0]], k = 1
 * 输出：-1
 * 解释：我们至少需要消除两个障碍才能找到这样的路径。
 * <p>
 * grid.length == m
 * grid[0].length == n
 * 1 <= m, n <= 40
 * 1 <= k <= m*n
 * grid[i][j] 是 0 或 1
 * grid[0][0] == grid[m-1][n-1] == 0
 */
public class Problem1293 {
    public static void main(String[] args) {
        Problem1293 problem1293 = new Problem1293();
        int[][] grid = {
                {0, 0, 0},
                {1, 1, 0},
                {0, 0, 0},
                {0, 1, 1},
                {0, 0, 0}
        };
        int k = 1;
        System.out.println(problem1293.shortestPath(grid, k));
        System.out.println(problem1293.shortestPath2(grid, k));
        System.out.println(problem1293.shortestPath3(grid, k));
    }

    /**
     * bfs
     * 注意：图中边的权值为消除的障碍物数量，而不是经过的路径长度
     * 注意：无向图Dijkstra保存当前节点的父节点，避免重复遍历
     * 当前节点的邻接节点grid为1，则邻接节点为障碍物，当前节点到邻接节点边的权值为1；否则，权值为0
     * 时间复杂度O(mnk)，空间复杂度O(mnk) (每个节点对应k个节点，共有mn个节点，最多会将mnk个节点加入队列中)
     *
     * @param grid
     * @param k
     * @return
     */
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到其他节点需要消除的最少障碍物数量数组
        int[][] remove = new int[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //remove初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                remove[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)需要消除的最少障碍物数量为grid[0][0]
        remove[0][0] = grid[0][0];

        //arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点的路径长度，
        //arr[3]：节点(0,0)到当前节点需要消除的障碍物数量，注意：当前需要消除的障碍物数量不一定是需要消除的最少障碍物数量，
        //arr[4]：父节点的横坐标，arr[5]：父节点的纵坐标
        Queue<int[]> queue = new LinkedList<>();

        //节点(0,0)入队
        queue.offer(new int[]{0, 0, 0, remove[0][0], -1, -1});

        //节点(0,0)最多消除k个障碍物到达节点(m-1,n-1)的最短路径长度
        int result = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点(x1,y1)的路径长度
            int curDistance = arr[2];
            //节点(0,0)到当前节点(x1,y1)需要消除的障碍物数量，注意：当前需要消除的障碍物数量不一定是需要消除的最少障碍物数量
            int curRemove = arr[3];
            //父节点(parentX,parentY)
            int parentX = arr[4];
            int parentY = arr[5];

            //节点(0,0)到当前节点(x1,y1)需要消除的障碍物数量curRemove大于k，则不合法，直接进行下次循环
            if (curRemove > k) {
                continue;
            }

            //访问到节点(m-1,n-1)，则更新result，进行下次循环
            if (x1 == m - 1 && y1 == n - 1) {
                result = Math.min(result, curDistance);
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

                //当前节点(x1,y1)的邻接节点(x2,y2)为节点(x1,y1)的父节点，则节点(x1,y1)到节点(x2,y2)的路径已经遍历过，避免重复遍历，直接进行下次循环
                if (x2 == parentX && y2 == parentY) {
                    continue;
                }

                //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点需要消除的最少障碍物数量，更新remove[x2][y2]，节点(x2,y2)入队
                if (curRemove + grid[x2][y2] < remove[x2][y2]) {
                    remove[x2][y2] = curRemove + grid[x2][y2];
                    queue.offer(new int[]{x2, y2, curDistance + 1, remove[x2][y2], x1, y1});
                }

            }
        }

        //遍历结束，result为int最大值，则节点(0,0)最多消除k个障碍物无法到达节点(m-1,n-1)，返回-1；否则返回result
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    /**
     * 堆优化Dijkstra求节点(0,0)最多消除k个障碍物到达节点(m-1,n-1)的最短路径长度
     * 注意：图中边的权值为消除的障碍物数量，而不是经过的路径长度
     * 注意：无向图Dijkstra保存当前节点的父节点，避免重复遍历
     * 当前节点的邻接节点grid为1，则邻接节点为障碍物，当前节点到邻接节点边的权值为1；否则，权值为0
     * 时间复杂度O(mnk*log(mnk))，空间复杂度O(mnk) (每个节点对应k个节点，共有mn个节点，最多会将mnk个节点加入堆中)
     * (堆优化Dijkstra的时间复杂度O(mlogm)，其中m为图中边的个数，本题边的个数O(mnk)，所以时间复杂度O(mnk*log(mnk)))
     *
     * @param grid
     * @param k
     * @return
     */
    public int shortestPath2(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到其他节点需要消除的最少障碍物数量数组
        int[][] remove = new int[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //remove初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                remove[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)需要消除的最少障碍物数量为grid[0][0]
        remove[0][0] = grid[0][0];

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });

        //小根堆，arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点的路径长度，
        //arr[3]：节点(0,0)到当前节点需要消除的障碍物数量，注意：当前需要消除的障碍物数量不一定是需要消除的最少障碍物数量，
        //arr[4]：父节点的横坐标，arr[5]：父节点的纵坐标
        priorityQueue.offer(new int[]{0, 0, 0, grid[0][0], -1, -1});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点(x1,y1)的路径长度
            int curDistance = arr[2];
            //节点(0,0)到当前节点(x1,y1)需要消除的障碍物数量，注意：当前需要消除的障碍物数量不一定是需要消除的最少障碍物数量
            int curRemove = arr[3];
            //父节点(parentX,parentY)
            int parentX = arr[4];
            int parentY = arr[5];

            //节点(0,0)到当前节点(x1,y1)需要消除的障碍物数量curRemove大于k，则不合法，直接进行下次循环
            if (curRemove > k) {
                continue;
            }

            //小根堆保证第一次访问到节点(m-1,n-1)，则得到节点(0,0)最多消除k个障碍物到达节点(m-1,n-1)的最短路径长度，直接返回curDistance
            if (x1 == m - 1 && y1 == n - 1) {
                return curDistance;
            }

            //遍历节点(x1,y1)的邻接节点(x2,y2)
            for (int i = 0; i < direction.length; i++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                //当前节点(x1,y1)的邻接节点(x2,y2)为节点(x1,y1)的父节点，则节点(x1,y1)到节点(x2,y2)的路径已经遍历过，避免重复遍历，直接进行下次循环
                if (x2 == parentX && y2 == parentY) {
                    continue;
                }

                //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点需要消除的最少障碍物数量，更新remove[x2][y2]，节点(x2,y2)入堆
                if (curRemove + grid[x2][y2] < remove[x2][y2]) {
                    remove[x2][y2] = curRemove + grid[x2][y2];
                    priorityQueue.offer(new int[]{x2, y2, curDistance + 1, remove[x2][y2], x1, y1});
                }
            }
        }

        //遍历结束，节点(0,0)最多消除k个障碍物无法到达节点(m-1,n-1)，返回-1
        return -1;
    }

    /**
     * 动态规划(Bellman-Ford)
     * 注意：图中边的权值为消除的障碍物数量，而不是经过的路径长度，
     * 所以dp[i][j][k]不能定义为节点(0,0)消除k个障碍物到达节点(i,j)的最短路径长度，
     * dp[i][j][k]只能定义为节点(0,0)经过的路径长度为k到达节点(i,j)最少消除的障碍物数量
     * dp[i][j][k]：节点(0,0)经过的路径长度为k到达节点(i,j)最少消除的障碍物数量
     * dp[i][j][k] = min(dp[x][y][k-1]+grid[i][j]) (节点(x,y)是节点(i,j)的邻接节点)
     * 时间复杂度O((mn)^2)，空间复杂度O((mn)^2)
     *
     * @param grid
     * @param k
     * @return
     */
    public int shortestPath3(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        //从节点(0,0)出发经过的最大路径长度为m*n-1
        int[][][] dp = new int[m][n][m * n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //dp初始化，节点(0,0)经过的路径长度为l无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int l = 0; l < m * n; l++) {
                    dp[i][j][l] = Integer.MAX_VALUE;
                }
            }
        }

        //初始化，节点(0,0)经过的路径长度为0到达节点(i,j)最少消除的障碍物数量为grid[0][0]
        dp[0][0][0] = grid[0][0];

        //经过的路径长度i
        for (int i = 0; i < m * n; i++) {
            //当前节点(x1,y1)
            for (int j = 0; j < m * n; j++) {
                int x1 = j / n;
                int y1 = j % n;

                //节点(x1,y1)的邻接节点(x2,y2)
                for (int l = 0; l < direction.length; l++) {
                    int x2 = x1 + direction[l][0];
                    int y2 = y1 + direction[l][1];

                    if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                        continue;
                    }

                    //节点(0,0)经过的路径长度为i-1能够到达节点(x2,y2)，才能更新节点(0,0)经过的路径长度为i到达节点(x1,y1)最少消除的障碍物数量
                    if (i - 1 >= 0 && dp[x2][y2][i - 1] != Integer.MAX_VALUE) {
                        dp[x1][y1][i] = Math.min(dp[x1][y1][i], dp[x2][y2][i - 1] + grid[x1][y1]);
                    }

                    //节点(0,0)经过的路径长度为i-1能够到达节点(x1,y1)，才能更新节点(0,0)经过的路径长度为i到达节点(x2,y2)最少消除的障碍物数量
                    if (i - 1 >= 0 && dp[x1][y1][i - 1] != Integer.MAX_VALUE) {
                        dp[x2][y2][i] = Math.min(dp[x2][y2][i], dp[x1][y1][i - 1] + grid[x2][y2]);
                    }
                }
            }

            //节点(0,0)经过的路径长度为i到达节点(m-1,n-1)最少消除的障碍物数量小于等于k，则直接返回当前路径长度i
            if (dp[m - 1][n - 1][i] <= k) {
                return i;
            }
        }

        //遍历结束，节点(0,0)最多消除k个障碍物无法到达节点(m-1,n-1)，返回-1
        return -1;
    }
}
