package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/26 08:45
 * @Author zsy
 * @Description 网格中的最短路径 华为机试题 美团机试题 带限制条件的最短路径类比Problem787、Problem1928、Problem2093 Bellman-Ford类比Problem568、Problem787、Problem1928 bfs类比1263Problem407、Problem490、Problem499、Problem505、Problem778、Problem787、Problem847、Problem1091、Problem1102、Problem1129、Problem1368、Problem1631、Problem1730、Problem1928、Problem2045、Problem2093、Problem2258、Problem2290、Problem2503、Problem2577、Problem2812、Problem3286 图中最短路径类比Problem399、Problem743、Problem778、Problem787、Problem882、Problem1334、Problem1368、Problem1462、Problem1514、Problem1631、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2203、Problem2290、Problem2473、Problem2577、Problem2642、Problem2662、Problem2812、Problem3286、Dijkstra
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
     * 时间复杂度O((mnk)^2)，空间复杂度O(mnk) (每个节点对应k个节点，共有mn个节点，最多会将mnk个节点加入队列中)
     *
     * @param grid
     * @param k
     * @return
     */
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到节点(m-1,n-1)的最短路径长度不小于等于m+n-2，当最大可消除障碍物个数大于m+n-3，直接返回最短路径长度m+n-2
        if (k >= m + n - 3) {
            return m + n - 2;
        }

        //节点(0,0)到其他节点消除障碍物的最短路径长度数组
        int[][][] distance = new int[m][n][k + 1];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //distance初始化，初始化为int最大值表示节点(0,0)无法到节点(i,j)消除k个障碍物
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int l = 0; l <= k; l++) {
                    distance[i][j][l] = Integer.MAX_VALUE;
                }
            }
        }

        //初始化，节点(0,0)到节点(0,0)消除grid[0][0]个障碍物的最短路径长度为0
        distance[0][0][grid[0][0]] = 0;

        //arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点消除的障碍物个数，
        //arr[3]：节点(0,0)到当前节点消除arr[2]个障碍物的路径长度，注意：当前路径长度不一定是最短路径长度
        Queue<int[]> queue = new LinkedList<>();
        //节点(0,0)入队
        queue.offer(new int[]{0, 0, grid[0][0], distance[0][0][grid[0][0]]});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点消除的障碍物个数
            int curRemove = arr[2];
            //节点(0,0)到当前节点消除arr[2]个障碍物的路径长度，注意：当前路径长度不一定是最短路径长度
            int curDistance = arr[3];

            //节点(0,0)到当前节点消除的障碍物个数大于最大可消除障碍物个数，则不合法，直接进行下次循环
            if (curRemove > k) {
                continue;
            }

            //节点(0,0)到当前节点消除arr[2]个障碍物的路径长度大于节点(0,0)到当前节点消除arr[2]个障碍物的最短路径长度，
            //则当前节点不能作为中间节点更新节点(0,0)到其他节点消除障碍物的最短路径长度，直接进行下次循环
            if (curDistance > distance[x1][y1][curRemove]) {
                continue;
            }

            //遍历节点(x1,y1)的邻接节点(x2,y2)
            for (int j = 0; j < direction.length; j++) {
                int x2 = x1 + direction[j][0];
                int y2 = y1 + direction[j][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                //找到更小的distance[x2][y2][curRemove+grid[x2][y2]]，更新distance[x2][y2][curRemove+grid[x2][y2]]，节点(x2,y2)入队
                if (curRemove + grid[x2][y2] <= k && curDistance + 1 < distance[x2][y2][curRemove + grid[x2][y2]]) {
                    distance[x2][y2][curRemove + grid[x2][y2]] = curDistance + 1;
                    queue.offer(new int[]{x2, y2, curRemove + grid[x2][y2], distance[x2][y2][curRemove + grid[x2][y2]]});
                }
            }
        }

        //节点(0,0)到节点(m-1,n-1)最多消除k个障碍物的最短路径长度
        int result = Integer.MAX_VALUE;

        for (int i = 0; i <= k; i++) {
            result = Math.min(result, distance[m - 1][n - 1][i]);
        }

        return result == Integer.MAX_VALUE ? -1 : result;
    }

    /**
     * 堆优化Dijkstra求节点(0,0)到节点(m-1,n-1)最多消除k个障碍物的最短路径长度
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

        //节点(0,0)到节点(m-1,n-1)的最短路径长度不小于等于m+n-2，当最大可消除障碍物个数大于m+n-3，直接返回最短路径长度m+n-2
        if (k >= m + n - 3) {
            return m + n - 2;
        }

        //节点(0,0)到其他节点消除障碍物的最短路径长度数组
        int[][][] distance = new int[m][n][k + 1];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //distance初始化，初始化为int最大值表示节点(0,0)无法到节点(i,j)消除k个障碍物
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int l = 0; l <= k; l++) {
                    distance[i][j][l] = Integer.MAX_VALUE;
                }
            }
        }

        //初始化，节点(0,0)到节点(0,0)消除grid[0][0]个障碍物的最短路径长度为0
        distance[0][0][grid[0][0]] = 0;

        //小根堆，arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点消除的障碍物个数，
        //arr[3]：节点(0,0)到当前节点消除arr[2]个障碍物的路径长度，注意：当前路径长度不一定是最短路径长度
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[3] - arr2[3];
            }
        });
        //节点(0,0)入堆
        priorityQueue.offer(new int[]{0, 0, grid[0][0], distance[0][0][grid[0][0]]});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点消除的障碍物个数
            int curRemove = arr[2];
            //节点(0,0)到当前节点消除arr[2]个障碍物的路径长度，注意：当前路径长度不一定是最短路径长度
            int curDistance = arr[3];

            //节点(0,0)到当前节点消除的障碍物个数大于最大可消除障碍物个数，则不合法，直接进行下次循环
            if (curRemove > k) {
                continue;
            }

            //小根堆保证第一次访问到节点(m-1,n-1)，则得到节点(0,0)到节点(m-1,n-1)最多消除k个障碍物的最短路径长度，直接返回curDistance
            if (x1 == m - 1 && y1 == n - 1) {
                return curDistance;
            }

            //遍历节点(x1,y1)的邻接节点(x2,y2)
            for (int i = 0; i < direction.length; i++) {
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                //找到更小的distance[x2][y2][curRemove+grid[x2][y2]]，更新distance[x2][y2][curRemove+grid[x2][y2]]，节点(x2,y2)入堆
                if (curRemove + grid[x2][y2] <= k && curDistance + 1 < distance[x2][y2][curRemove + grid[x2][y2]]) {
                    distance[x2][y2][curRemove + grid[x2][y2]] = curDistance + 1;
                    priorityQueue.offer(new int[]{x2, y2, curRemove + grid[x2][y2], distance[x2][y2][curRemove + grid[x2][y2]]});
                }
            }
        }

        //遍历结束，节点(0,0)无法到节点(m-1,n-1)最多消除k个障碍物，返回-1
        return -1;
    }

    /**
     * 动态规划(Bellman-Ford)
     * dp[i][j][k]：节点(0,0)到节点(i,j)的路径长度为k的最小消除的障碍物个数
     * dp[i][j][k] = dp[x][y][k-1] + grid[x][y] (节点(x,y)为当前节点(i,j)的邻接节点)
     * 注意：dp[i][j][k]不能定义为：节点(0,0)到节点(i,j)消除k个障碍物的最短路径长度，
     * 因为当前节点(i,j)的邻接节点(x,y)grid[x][y]为0时，状态转移方程中dp[i][j][k]会使用到dp[x][y][k]，
     * 如果只使用到dp[x][y][k-1]，则dp可以这样定义
     * 时间复杂度O((mn)^2)，空间复杂度O((mn)^2)
     *
     * @param grid
     * @param k
     * @return
     */
    public int shortestPath3(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到节点(m-1,n-1)的最短路径长度不小于等于m+n-2，当最大可消除障碍物个数大于m+n-3，直接返回最短路径长度m+n-2
        if (k >= m + n - 3) {
            return m + n - 2;
        }

        //节点(0,0)到节点(i,j)的路径长度为k的最小消除的障碍物个数
        int[][][] dp = new int[m][n][m * n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //dp初始化，节点(0,0)到节点(i,j)的路径长度为l的最小消除的障碍物个数为int最大值
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //注意：节点(0,0)到节点(i,j)的最大路径长度小于m*n
                for (int l = 0; l < m * n; l++) {
                    dp[i][j][l] = Integer.MAX_VALUE;
                }
            }
        }

        //dp初始化，节点(0,0)到节点(0,0)的路径长度为0的最小消除的障碍物个数为grid[0][0]
        dp[0][0][0] = grid[0][0];

        //节点(0,0)到当前节点(i,j)的路径长度l
        //注意：节点(0,0)到节点(i,j)的最大路径长度小于m*n
        for (int l = 0; l < m * n; l++) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (int p = 0; p < direction.length; p++) {
                        //节点(i,j)的邻接节点(x,y)
                        int x = i + direction[p][0];
                        int y = j + direction[p][1];

                        if (x < 0 || x >= m || y < 0 || y >= n) {
                            continue;
                        }

                        //节点(0,0)到节点(x,y)的路径长度为l-1的最小消除的障碍物个数存在，才能更新节点(0,0)到节点(i,j)的路径长度为l的最小消除的障碍物个数
                        if (l - 1 >= 0 && dp[x][y][l - 1] != Integer.MAX_VALUE) {
                            dp[i][j][l] = Math.min(dp[i][j][l], dp[x][y][l - 1] + grid[i][j]);
                        }

                        //节点(0,0)到节点(i,j)的路径长度为l-1的最小消除的障碍物个数存在，才能更新节点(0,0)到节点(x,y)的路径长度为l的最小消除的障碍物个数
                        if (l - 1 >= 0 && dp[i][j][l - 1] != Integer.MAX_VALUE) {
                            dp[x][y][l] = Math.min(dp[x][y][l], dp[i][j][l - 1] + grid[x][y]);
                        }
                    }
                }
            }

            //节点(0,0)到节点(m-1,n-1)的路径长度为l的最小消除的障碍物个数小于等于k，
            //则节点(0,0)到节点(m-1,n-1)最多消除k个障碍物的最短路径长度为l，直接返回l
            if (dp[m - 1][n - 1][l] <= k) {
                return l;
            }
        }

        //遍历结束，节点(0,0)无法到节点(m-1,n-1)最多消除k个障碍物，返回-1
        return -1;
    }
}
