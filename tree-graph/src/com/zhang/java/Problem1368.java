package com.zhang.java;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2023/11/8 08:02
 * @Author zsy
 * @Description 使网格图至少有一条有效路径的最小代价 bfs类比Problem407、Problem499、Problem505、Problem778、Problem847、Problem1129、Problem1293、Problem1631、Problem2045、Problem2290 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1293、Problem1334、Problem1462、Problem1514、Problem1631、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2203、Problem2290、Problem2473、Problem2662、Dijkstra
 * 给你一个 m x n 的网格图 grid 。 grid 中每个格子都有一个数字，对应着从该格子出发下一步走的方向。
 * grid[i][j] 中的数字可能为以下几种情况：
 * 1 ，下一步往右走，也就是你会从 grid[i][j] 走到 grid[i][j + 1]
 * 2 ，下一步往左走，也就是你会从 grid[i][j] 走到 grid[i][j - 1]
 * 3 ，下一步往下走，也就是你会从 grid[i][j] 走到 grid[i + 1][j]
 * 4 ，下一步往上走，也就是你会从 grid[i][j] 走到 grid[i - 1][j]
 * 注意网格图中可能会有 无效数字 ，因为它们可能指向 grid 以外的区域。
 * 一开始，你会从最左上角的格子 (0,0) 出发。我们定义一条 有效路径 为从格子 (0,0) 出发，
 * 每一步都顺着数字对应方向走，最终在最右下角的格子 (m - 1, n - 1) 结束的路径。
 * 有效路径 不需要是最短路径 。
 * 你可以花费 cost = 1 的代价修改一个格子中的数字，但每个格子中的数字 只能修改一次 。
 * 请你返回让网格图至少有一条有效路径的最小代价。
 * <p>
 * 输入：grid = [
 * [1,1,1,1],
 * [2,2,2,2],
 * [1,1,1,1],
 * [2,2,2,2]
 * ]
 * 输出：3
 * 解释：你将从点 (0, 0) 出发。
 * 到达 (3, 3) 的路径为：
 * (0, 0) --> (0, 1) --> (0, 2) --> (0, 3) 花费代价 cost = 1 使方向向下 -->
 * (1, 3) --> (1, 2) --> (1, 1) --> (1, 0) 花费代价 cost = 1 使方向向下 -->
 * (2, 0) --> (2, 1) --> (2, 2) --> (2, 3) 花费代价 cost = 1 使方向向下 -->
 * (3, 3)
 * 总花费为 cost = 3.
 * <p>
 * 输入：grid = [[1,1,3],[3,2,2],[1,1,4]]
 * 输出：0
 * 解释：不修改任何数字你就可以从 (0, 0) 到达 (2, 2) 。
 * <p>
 * 输入：grid = [[1,2],[4,3]]
 * 输出：1
 * <p>
 * 输入：grid = [[2,2,2],[2,2,2]]
 * 输出：3
 * <p>
 * 输入：grid = [[4]]
 * 输出：0
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 */
public class Problem1368 {
    public static void main(String[] args) {
        Problem1368 problem1368 = new Problem1368();
        int[][] grid = {
                {1, 1, 1, 1},
                {2, 2, 2, 2},
                {1, 1, 1, 1},
                {2, 2, 2, 2}
        };
        System.out.println(problem1368.minCost(grid));
        System.out.println(problem1368.minCost2(grid));
        System.out.println(problem1368.minCost3(grid));
    }

    /**
     * bfs
     * 当前节点到邻接节点的方向和邻接节点grid对应，则当前节点到邻接节点边的权值为0；否则，权值为1
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到其他节点的最小修改代价数组
        int[][] cost = new int[m][n];
        //当前节点的右左下上四个位置，和grid[i][j]中1、2、3、4对应，用于判断当前节点到相邻节点边的权值是0还是1，
        //例如：当前节点grid[i][j]为2，即指向左，则当前节点到左边相邻节点的边的权值为0(grid[i][j]==k)，
        //当前节点到其他相邻节点的边的权值为1(grid[i][j]!=k)
        int[][] direction = {{0, 0}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        //cost数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)的最小修改代价为0
        cost[0][0] = 0;

        //arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点的修改代价，注意：当前修改代价不一定是最小修改代价
        Queue<int[]> queue = new LinkedList<>();

        //节点(0,0)入队
        queue.offer(new int[]{0, 0, cost[0][0]});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点(x1,y1)的修改代价，注意：当前修改代价不一定是最小修改代价
            int curCost = arr[2];

            //curCost大于cost[x1][y1]，则当前节点(x1,y1)不能作为中间节点更新节点(0,0)到其他节点的最小修改代价，直接进行下次循环
            if (curCost > cost[x1][y1]) {
                continue;
            }

            //遍历节点(x1,y1)的邻接节点(x2,y2)
            //注意：i从1开始遍历，因为grid上下左右是1-4
            for (int i = 1; i < direction.length; i++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                //当前节点(x1,y1)到邻接节点(x2,y2)的方向和邻接节点grid对应，则当前节点到邻接节点边的权值为0
                if (grid[x1][y1] == i) {
                    if (cost[x1][y1] < cost[x2][y2]) {
                        cost[x2][y2] = cost[x1][y1];
                        queue.offer(new int[]{x2, y2, cost[x2][y2]});
                    }
                } else {
                    //当前节点(x1,y1)到邻接节点(x2,y2)的方向和邻接节点grid不对应，则当前节点到邻接节点边的权值为1
                    if (cost[x1][y1] + 1 < cost[x2][y2]) {
                        cost[x2][y2] = cost[x1][y1] + 1;
                        queue.offer(new int[]{x2, y2, cost[x2][y2]});
                    }
                }
            }
        }

        return cost[m - 1][n - 1];
    }

    /**
     * Dijkstra求节点(0,0)到节点(m-1,n-1)的最短路径长度
     * 当前节点到邻接节点的方向和邻接节点grid对应，则当前节点到邻接节点边的权值为0；否则，权值为1
     * 注意：不适合权值为负的图
     * 每次从未访问节点中选择距离节点(0,0)最短路径长度的节点(x,y)，节点(x,y)作为中间节点更新节点(0,0)到其他节点的最短路径长度
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int minCost2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到其他节点的最小修改代价数组
        int[][] cost = new int[m][n];
        //节点访问数组，visited[i][j]为true，表示已经得到节点(0,0)到节点(i,j)的最小修改代价
        boolean[][] visited = new boolean[m][n];
        //当前节点的右左下上四个位置，和grid[i][j]中1、2、3、4对应，用于判断当前节点到相邻节点边的权值是0还是1，
        //例如：当前节点grid[i][j]为2，即指向左，则当前节点到左边相邻节点的边的权值为0(grid[i][j]==k)，
        //当前节点到其他相邻节点的边的权值为1(grid[i][j]!=k)
        int[][] direction = {{0, 0}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        //cost数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)的最小修改代价为0
        cost[0][0] = 0;

        for (int i = 0; i < m * n; i++) {
            //初始化cost数组中未访问节点中选择距离节点(0,0)最小修改代价的节点(x1,y1)
            int x1 = -1;
            int y1 = -1;

            //未访问节点中选择距离节点(0,0)最小修改代价的节点(x1,y1)
            for (int j = 0; j < m * n; j++) {
                int x2 = j / n;
                int y2 = j % n;

                if (!visited[x2][y2] && ((x1 == -1 && y1 == -1) || (cost[x2][y2] < cost[x1][y1]))) {
                    x1 = x2;
                    y1 = y2;
                }
            }

            //设置节点(x1,y1)已访问，表示已经得到节点(0,0)到节点(x1,y1)的最小修改代价
            visited[x1][y1] = true;

            //已经得到节点(0,0)到节点(m-1,n-1)的最小修改代价，直接返回cost[m-1][n-1]
            if (x1 == m - 1 && y1 == n - 1) {
                return cost[m - 1][n - 1];
            }

            //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点的最小修改代价
            //注意：j从1开始遍历，因为grid上下左右是1-4
            for (int j = 1; j < direction.length; j++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[j][0];
                int y2 = y1 + direction[j][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                if (!visited[x2][y2]) {
                    if (grid[x1][y1] == j) {
                        if (cost[x1][y1] < cost[x2][y2]) {
                            cost[x2][y2] = cost[x1][y1];
                        }
                    } else {
                        if (cost[x1][y1] + 1 < cost[x2][y2]) {
                            cost[x2][y2] = cost[x1][y1] + 1;
                        }
                    }
                }
            }
        }

        //遍历结束，没有找到节点(0,0)到节点(m-1,n-1)的最小修改代价，则返回-1
        return -1;
    }

    /**
     * 堆优化Dijkstra求节点(0,0)到节点(m-1,n-1)的最短路径长度
     * 当前节点到邻接节点的方向和邻接节点grid对应，则当前节点到邻接节点边的权值为0；否则，权值为1
     * 优先队列每次出队节点(0,0)到其他节点的路径长度中最短路径的节点(x,y)，节点(x,y)作为中间节点更新节点(0,0)到其他节点的最短路径长度
     * 时间复杂度O(mn*log(mn))，空间复杂度O(mn)
     * (堆优化Dijkstra的时间复杂度O(mlogm)，其中m为图中边的个数，本题边的个数O(mn)，所以时间复杂度O(mn*log(mn)))
     *
     * @param grid
     * @return
     */
    public int minCost3(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到其他节点的最小修改代价数组
        int[][] cost = new int[m][n];
        //当前节点的右左下上四个位置，和grid[i][j]中1、2、3、4对应，用于判断当前节点到相邻节点边的权值是0还是1，
        //例如：当前节点grid[i][j]为2，即指向左，则当前节点到左边相邻节点的边的权值为0(grid[i][j]==k)，
        //当前节点到其他相邻节点的边的权值为1(grid[i][j]!=k)
        int[][] direction = {{0, 0}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        //cost数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)的最小修改代价为0
        cost[0][0] = 0;

        //小根堆，arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点u到当前节点的修改代价，注意：当前修改代价不一定是最小修改代价
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });

        //节点(0,0)入堆
        priorityQueue.offer(new int[]{0, 0, cost[0][0]});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点(x1,y1)的修改代价
            int curCost = arr[2];

            //curCost大于cost[x1][y1]，则当前节点(x1,y1)不能作为中间节点更新节点(0,0)到其他节点的最小修改代价，直接进行下次循环
            if (curCost > cost[x1][y1]) {
                continue;
            }

            //小根堆保证第一次访问到节点(m-1,n-1)，则得到节点(0,0)到节点(m-1,n-1)的最小修改代价，直接返回curCost
            if (x1 == m - 1 && y1 == n - 1) {
                return curCost;
            }

            //遍历节点(x1,y1)的邻接节点(x2,y2)
            //注意：i从1开始遍历，因为grid上下左右是1-4
            for (int i = 0; i < direction.length; i++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                //当前节点(x1,y1)到邻接节点(x2,y2)的方向和邻接节点grid对应，则当前节点到邻接节点边的权值为0
                if (grid[x1][y1] == i) {
                    if (curCost < cost[x2][y2]) {
                        cost[x2][y2] = curCost;
                        priorityQueue.offer(new int[]{x2, y2, cost[x2][y2]});
                    }
                } else {
                    //当前节点(x1,y1)到邻接节点(x2,y2)的方向和邻接节点grid不对应，则当前节点到邻接节点边的权值为1
                    if (curCost + 1 < cost[x2][y2]) {
                        cost[x2][y2] = curCost + 1;
                        priorityQueue.offer(new int[]{x2, y2, cost[x2][y2]});
                    }
                }
            }
        }

        //遍历结束，没有找到节点(0,0)到节点(m-1,n-1)的最小修改代价，则返回-1
        return -1;
    }
}
