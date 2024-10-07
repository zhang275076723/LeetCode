package com.zhang.java;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2023/11/26 08:45
 * @Author zsy
 * @Description 网格中的最短路径 美团机试题 带限制条件的单元最短路径类比Problem787、Problem1928、Problem2093 bfs类比Problem407、Problem499、Problem505、Problem778、Problem847、Problem1091、Problem1102、Problem1129、Problem1368、Problem1631、Problem1730、Problem2045、Problem2258、Problem2290、Problem2812、Problem3286 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1334、Problem1368、Problem1462、Problem1514、Problem1631、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2203、Problem2290、Problem2473、Problem2662、Dijkstra 记忆化搜索类比
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
//        int[][] grid = {
//                {0, 0, 0},
//                {1, 1, 0},
//                {0, 0, 0},
//                {0, 1, 1},
//                {0, 0, 0}
//        };
//        int k = 1;
        int[][] grid = {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };
        int k = 5;
        System.out.println(problem1293.shortestPath(grid, k));
        System.out.println(problem1293.shortestPath2(grid, k));
        System.out.println(problem1293.shortestPath3(grid, k));
//        //注意：不能使用dp，方法不正确
//        System.out.println(problem1293.shortestPath4(grid, k));
    }

    /**
     * bfs
     * 一般bfs，如果当前节点(i,j)被访问，则直接跳过当前节点；但当前bfs，如果当前节点(i,j)被访问，
     * 并且当前路径中节点(0,0)到节点(i,j)需要消除的障碍物数量小于节点(0,0)到节点(i,j)需要消除的最少障碍物数量remove[i][j]，
     * 则当前路径可能是最多消除k个节点到达(m-1,n-1)的最短路径，不能跳过当前节点(i,j)，也需要对当前节点(i,j)进行bfs
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

        //arr[0]：节点的横坐标，arr[1]：节点的纵坐标，
        //arr[2]：节点(0,0)到当前节点需要消除的障碍物数量，注意：当前需要消除的障碍物数量不一定是需要消除的最少障碍物数量
        Queue<int[]> queue = new LinkedList<>();

        //节点(0,0)入队
        queue.offer(new int[]{0, 0, grid[0][0]});

        //节点(0,0)最多消除k个障碍物到达节点(m-1,n-1)的最短路径长度
        int distance = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();
                //当前节点(x1,y1)
                int x1 = arr[0];
                int y1 = arr[1];
                //节点(0,0)到当前节点(x1,y1)需要消除的障碍物数量，注意：当前需要消除的障碍物数量不一定是需要消除的最少障碍物数量
                int curRemove = arr[2];

                //节点(0,0)到当前节点(x1,y1)需要消除的障碍物数量curRemove大于k，则不合法，直接进行下次循环
                if (curRemove > k) {
                    continue;
                }

                //访问到节点(m-1,n-1)，则找到节点(0,0)最多消除k个障碍物到达节点(m-1,n-1)的最短路径长度，直接返回distance
                if (x1 == m - 1 && y1 == n - 1) {
                    return distance;
                }

                //遍历节点(x1,y1)的邻接节点(x2,y2)
                for (int j = 0; j < direction.length; j++) {
                    //节点(x1,y1)的邻接节点(x2,y2)
                    int x2 = x1 + direction[j][0];
                    int y2 = y1 + direction[j][1];

                    if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                        continue;
                    }

                    //当前路径中节点(0,0)到节点(x2,y2)需要消除的障碍物数量小于节点(0,0)到节点(x2,y2)需要消除的最少障碍物数量remove[x2][y2]，
                    //则当前路径可能是最多消除k个节点到达(m-1,n-1)的最短路径，不能跳过当前节点(x2,y2)，也需要对当前节点(x2,y2)进行dfs
                    if (curRemove + grid[x2][y2] < remove[x2][y2]) {
                        remove[x2][y2] = curRemove + grid[x2][y2];
                        queue.offer(new int[]{x2, y2, remove[x2][y2]});
                    }
                }
            }

            //bfs每次往外扩1层，最短路径长度加1
            distance++;
        }

        //遍历结束，节点(0,0)最多消除k个障碍物无法到达节点(m-1,n-1)，返回-1
        return -1;
    }

    /**
     * 堆优化Dijkstra求节点(0,0)最多消除k个障碍物到达节点(m-1,n-1)的最短路径长度
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
        priorityQueue.offer(new int[]{0, 0, 0, grid[0][0]});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点(x1,y1)的路径长度
            int curDistance = arr[2];
            //节点(0,0)到当前节点(x1,y1)需要消除的障碍物数量，注意：当前需要消除的障碍物数量不一定是需要消除的最少障碍物数量
            int curRemove = arr[3];

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
     * 递归+记忆化搜索
     * dp[i][j][k]：节点(i,j)最多消除k个障碍物到达节点(m-1,n-1)的最短路径长度
     * 时间复杂度O(mnk)，空间复杂度O(mnk)
     *
     * @param grid
     * @param k
     * @return
     */
    public int shortestPath3(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        int[][][] dp = new int[m][n][k + 1];
        int INF = Integer.MAX_VALUE / 2;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int l = 0; l <= k; l++) {
                    dp[i][j][l] = INF;
                }
            }
        }

        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //访问数组保证访问过的节点不会再次访问，即保证不会产生环，导致dfs无法终止的情况
        //注意：必须使用访问数组，不能使用dp作为访问数组
        int result = dfs(0, 0, m, n, k, INF, grid, dp, new boolean[m][n], direction);

        return result == INF ? -1 : result;
    }

    /**
     * 节点(i,j)最多消除k个障碍物到达节点(m-1,n-1)的最短路径长度
     *
     * @param i
     * @param j
     * @param m
     * @param n
     * @param k
     * @param INF
     * @param grid
     * @param dp
     * @param visited
     * @param direction
     * @return
     */
    private int dfs(int i, int j, int m, int n, int k, int INF, int[][] grid, int[][][] dp,
                    boolean[][] visited, int[][] direction) {
        if (i == m - 1 && j == n - 1) {
            dp[i][j][k] = 0;
            return 0;
        }

        if (dp[i][j][k] != INF) {
            return dp[i][j][k];
        }

        if (visited[i][j]) {
            return INF;
        }

        visited[i][j] = true;

        for (int l = 0; l < direction.length; l++) {
            int x = i + direction[l][0];
            int y = j + direction[l][1];

            if (x < 0 || x >= m || y < 0 || y >= n) {
                continue;
            }

            if (k - grid[x][y] >= 0) {
                dp[i][j][k] = Math.min(dp[i][j][k], dfs(x, y, m, n, k - grid[x][y], INF, grid, dp, visited, direction) + 1);
            }
        }

        visited[i][j] = false;
        return dp[i][j][k];
    }

    /**
     * 动态规划(Bellman-Ford)
     * 注意：不能使用dp，因为状态转移方程需要往上下左右四个方向寻找，不是只往右下两个方向寻找，导致本次状态方程使用到的状态方程未完全得到
     * dp[i][j][k]：节点(0,0)消除k个障碍物到达节点(i,j)的最短路径长度
     * dp[i][j][k] = min(dp[x][y][k-1]+1) (节点(x,y)是节点(i,j)的邻接节点，grid[i][j] == 1)
     * dp[i][j][k] = min(dp[x][y][k]+1)   (节点(x,y)是节点(i,j)的邻接节点，grid[i][j] == 0)
     * 时间复杂度O((mn)^2)，空间复杂度O((mn)^2)
     *
     * @param grid
     * @param k
     * @return
     */
    public int shortestPath4(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到达其他节点最多消除k个障碍物
        int[][][] dp = new int[m][n][k + 1];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        //节点(0,0)到达其他节点的最大路径长度，不能初始化为int最大值，避免相加溢出
        int INF = Integer.MAX_VALUE / 2;

        //dp初始化，节点(0,0)消除l个障碍物无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int l = 0; l <= k; l++) {
                    dp[i][j][l] = INF;
                }
            }
        }

        //dp初始化，节点(0,0)消除grid[0][0]个障碍物到达节点(i,j)的最短路径长度为0
        dp[0][0][grid[0][0]] = 0;

        //消除的障碍物个数l
        for (int l = 0; l <= k; l++) {
            //当前节点(i,j)
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (int p = 0; p < direction.length; p++) {
                        //节点(i,j)的邻接节点(x,y)
                        int x = i + direction[p][0];
                        int y = j + direction[p][1];

                        if (x < 0 || x >= m || y < 0 || y >= n) {
                            continue;
                        }

                        //当前节点(i,j)为0，则从节点(x,y)到节点(i,j)不需要消除障碍物
                        if (grid[i][j] == 0) {
                            dp[i][j][l] = Math.min(dp[i][j][l], dp[x][y][l] + 1);
                        } else if (grid[i][j] == 1 && l - 1 >= 0) {
                            //当前节点(i,j)为1，则从节点(x,y)到节点(i,j)需要消除障碍物
                            dp[i][j][l] = Math.min(dp[i][j][l], dp[x][y][l - 1] + 1);
                        }

                        if (grid[x][y] == 0) {
                            dp[x][y][l] = Math.min(dp[x][y][l], dp[i][j][l] + 1);
                        } else if (grid[x][y] == 1 && l - 1 >= 0) {
                            dp[x][y][l] = Math.min(dp[x][y][l], dp[i][j][l - 1] + 1);
                        }
                    }
                }
            }
        }

        //节点(0,0)最多消除k个障碍物到达节点(m-1,n-1)的最短路径长度
        int result = INF;

        //最短路径长度为dp[m-1][n-1][0]、dp[m-1][n-1][1]、...、dp[m-1][n-1][k]中的最小值
        for (int i = 0; i <= k; i++) {
            result = Math.min(result, dp[m - 1][n - 1][i]);
        }

        //result为INF，则节点(0,0)最多消除k个障碍物无法到达节点(m-1,n-1)，返回-1；否则返回result
        return result == INF ? -1 : result;
    }
}
