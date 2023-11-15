package com.zhang.java;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2023/11/8 08:02
 * @Author zsy
 * @Description 使网格图至少有一条有效路径的最小代价 bfs类比Problem847、Problem1129、Problem2045 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1334、Problem1462、Problem1514、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2662、Dijkstra
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
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到其他节点的最短路径长度数组
        int[][] distance = new int[m][n];
        //当前节点的右左下上四个位置，和grid[i][j]中1、2、3、4相对应，用于判断当前节点到相邻节点边的权值是0还是1，
        //例如：当前节点grid[i][j]为2，即指向左，通过direction[1]到相邻左边节点的边的权值为0(grid[i][j]==k+1)，
        //到其他相邻节点的边的权值为1(grid[i][j]!=k+1)
        int[][] direction = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        //distance数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)的最短路径长度为0
        distance[0][0] = 0;

        //arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点的路径长度，注意：当前路径长度不一定是最短路径长度
        Queue<int[]> queue = new LinkedList<>();

        //节点(0,0)入队
        queue.offer(new int[]{0, 0, 0});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点的路径长度，注意：当前路径长度不一定是最短路径长度
            int curDistance = arr[2];

            //curDistance大于distance[x1][y1]，则当前节点(x1,y1)不能作为中间节点更新节点(0,0)到其他节点的最短路径长度，直接进行下次循环
            if (curDistance > distance[x1][y1]) {
                continue;
            }

            //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点的最短路径长度
            for (int i = 0; i < direction.length; i++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                //节点(x1,y1)作为中间节点，节点(0,0)到节点(x2,y2)的路径长度
                //grid[x1][y1]所指方向等于节点(x1,y1)到节点(x2,y2)的方向，则节点(x1,y1)到节点(x2,y2)边的权值为0；
                //grid[x1][y1]所指方向不等于节点(x1,y1)到节点(x2,y2)的方向，则节点(x1,y1)到节点(x2,y2)边的权值为1
                int cost = curDistance + ((grid[x1][y1] == i + 1) ? 0 : 1);

                //节点(x1,y1)作为中间节点，节点(0,0)到节点(x2,y2)的路径长度cost小于之前节点(0,0)到节点(x2,y2)的路径长度distance[x2][y2]，
                //更新节点(0,0)到节点(x2,y2)的最短路径长度，并将节点(x2,y2)入队
                if (cost < distance[x2][y2]) {
                    distance[x2][y2] = cost;
                    queue.offer(new int[]{x2, y2, distance[x2][y2]});
                }
            }
        }

        return distance[m - 1][n - 1];
    }

    /**
     * Dijkstra求(0,0)到(m-1,n-1)的最短路径长度
     * 注意：不适合权值为负的图
     * 每次从未访问节点中选择距离节点(0,0)最短路径长度的节点(x,y)，节点(x,y)作为中间节点更新节点(0,0)到其他节点的最短路径长度
     * 如果存在当前节点指向邻接节点的边，则当前节点到邻接节点边的权值为0；否则，权值为1
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int minCost2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到其他节点的最短路径长度数组
        int[][] distance = new int[m][n];
        //节点访问数组，visited[i][j]为true，表示已经得到节点(0,0)到节点(i,j)的最短路径长度
        boolean[][] visited = new boolean[m][n];
        //当前节点的右左下上四个位置，和grid[i][j]中1、2、3、4相对应，用于判断当前节点到相邻节点边的权值是0还是1，
        //例如：当前节点grid[i][j]为2，即指向左，通过direction[1]到相邻左边节点的边的权值为0(grid[i][j]==k+1)，
        //到其他相邻节点的边的权值为1(grid[i][j]!=k+1)
        int[][] direction = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        //distance数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)的最短路径长度为0
        distance[0][0] = 0;

        //每次从未访问节点中选择距离节点(0,0)最短路径长度的节点(x1,y1)，节点(x1,y1)作为中间节点更新节点(0,0)到其他节点的最短路径长度
        for (int i = 0; i < m * n; i++) {
            //初始化distance数组中未访问节点中选择距离节点(0,0)最短路径长度的节点(x1,y1)
            int x1 = -1;
            int y1 = -1;

            //未访问节点中选择距离节点(0,0)最短路径长度的节点(x1,y1)
            for (int j = 0; j < m * n; j++) {
                int x2 = j / n;
                int y2 = j % n;

                if (!visited[x2][y2] && ((x1 == -1 && y1 == -1) || (distance[x2][y2] < distance[x1][y1]))) {
                    x1 = x2;
                    y1 = y2;
                }
            }

            //设置节点(x1,y1)已访问，表示已经得到节点(0,0)到节点(x1,y1)的最短路径长度
            visited[x1][y1] = true;

            //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点的最短路径长度
            for (int k = 0; k < direction.length; k++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[k][0];
                int y2 = y1 + direction[k][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                if (!visited[x2][y2]) {
                    //grid[x1][y1]所指方向等于节点(x1,y1)到节点(x2,y2)的方向，则节点(x1,y1)到节点(x2,y2)边的权值为0；
                    //grid[x1][y1]所指方向不等于节点(x1,y1)到节点(x2,y2)的方向，则节点(x1,y1)到节点(x2,y2)边的权值为1
                    distance[x2][y2] = Math.min(distance[x2][y2], distance[x1][y1] + ((grid[x1][y1] == k + 1) ? 0 : 1));
                }
            }
        }

        return distance[m - 1][n - 1];
    }

    /**
     * 堆优化Dijkstra求(0,0)到(m-1,n-1)的最短路径长度
     * 优先队列每次出队节点(0,0)到其他节点的路径长度中最短路径的节点(x,y)，节点(x,y)作为中间节点更新节点(0,0)到其他节点的最短路径长度
     * 如果存在当前节点指向邻接节点的边，则当前节点到邻接节点边的权值为0；否则，权值为1
     * 时间复杂度O(mn*log(mn))，空间复杂度O(mn)
     * (堆优化Dijkstra的时间复杂度O(mlogm)，其中m为图中边的个数，本题边的个数O(mn)，所以时间复杂度O(mn*log(mn)))
     *
     * @param grid
     * @return
     */
    public int minCost3(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        //节点(0,0)到其他节点的最短路径长度数组
        int[][] distance = new int[m][n];
        //当前节点的右左下上四个位置，和grid[i][j]中1、2、3、4相对应，用于判断当前节点到相邻节点边的权值是0还是1，
        //例如：当前节点grid[i][j]为2，即指向左，通过direction[1]到相邻左边节点的边的权值为0(grid[i][j]==k+1)，
        //到其他相邻节点的边的权值为1(grid[i][j]!=k+1)
        int[][] direction = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        //distance数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)的最短路径长度为0
        distance[0][0] = 0;

        //小根堆，arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点u到当前节点的路径长度
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });
        priorityQueue.offer(new int[]{0, 0, 0});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到节点(x1,y1)的路径长度
            int curDistance = arr[2];

            //curDistance大于distance[x1][y1]，则当前节点(x1,y1)不能作为中间节点更新节点(0,0)到其他节点的最短路径长度，直接进行下次循环
            if (curDistance > distance[x1][y1]) {
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

                //节点(x1,y1)作为中间节点，节点(0,0)到节点(x2,y2)的路径长度
                //grid[x1][y1]所指方向等于节点(x1,y1)到节点(x2,y2)的方向，则节点(x1,y1)到节点(x2,y2)边的权值为0；
                //grid[x1][y1]所指方向不等于节点(x1,y1)到节点(x2,y2)的方向，则节点(x1,y1)到节点(x2,y2)边的权值为1
                int cost = curDistance + ((grid[x1][y1] == i + 1) ? 0 : 1);

                //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点的最短路径长度，更新distance[x2][y2]，节点(x2,y2)入堆
                if (cost < distance[x2][y2]) {
                    distance[x2][y2] = cost;
                    priorityQueue.offer(new int[]{x2, y2, distance[x2][y2]});
                }
            }
        }

        return distance[m - 1][n - 1];
    }
}
