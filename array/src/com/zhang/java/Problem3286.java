package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/1/15 08:15
 * @Author zsy
 * @Description 穿越网格图的安全路径 bfs类比 图中最短路径类比
 * 给你一个 m x n 的二进制矩形 grid 和一个整数 health 表示你的健康值。
 * 你开始于矩形的左上角 (0, 0) ，你的目标是矩形的右下角 (m - 1, n - 1) 。
 * 你可以在矩形中往上下左右相邻格子移动，但前提是你的健康值始终是 正数 。
 * 对于格子 (i, j) ，如果 grid[i][j] = 1 ，那么这个格子视为 不安全 的，会使你的健康值减少 1 。
 * 如果你可以到达最终的格子，请你返回 true ，否则返回 false 。
 * 注意 ，当你在最终格子的时候，你的健康值也必须为 正数 。
 * <p>
 * 输入：grid = [[0,1,0,0,0],[0,1,0,1,0],[0,0,0,1,0]], health = 1
 * 输出：true
 * 解释：
 * 沿着下图中灰色格子走，可以安全到达最终的格子。
 * <p>
 * 输入：grid = [[0,1,1,0,0,0],[1,0,1,0,0,0],[0,1,1,1,0,1],[0,0,1,0,1,0]], health = 3
 * 输出：false
 * 解释：
 * 健康值最少为 4 才能安全到达最后的格子。
 * <p>
 * 输入：grid = [[1,1,1],[1,0,1],[1,1,1]], health = 5
 * 输出：true
 * 解释：
 * 沿着下图中灰色格子走，可以安全到达最终的格子。
 * 任何不经过格子 (1, 1) 的路径都是不安全的，因为你的健康值到达最终格子时，都会小于等于 0 。
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 50
 * 2 <= m * n
 * 1 <= health <= m + n
 * grid[i][j] 要么是 0 ，要么是 1 。
 */
public class Problem3286 {
    public static void main(String[] args) {
        Problem3286 problem3286 = new Problem3286();
        List<List<Integer>> grid = new ArrayList<List<Integer>>() {{
            add(new ArrayList<Integer>() {{
                add(0);
                add(1);
                add(0);
                add(0);
                add(0);
            }});
            add(new ArrayList<Integer>() {{
                add(0);
                add(1);
                add(0);
                add(1);
                add(0);
            }});
            add(new ArrayList<Integer>() {{
                add(0);
                add(0);
                add(0);
                add(1);
                add(0);
            }});
        }};
        int health = 1;
        System.out.println(problem3286.findSafeWalk(grid, health));
        System.out.println(problem3286.findSafeWalk2(grid, health));
        System.out.println(problem3286.findSafeWalk3(grid, health));
    }

    /**
     * bfs
     * 当前节点到邻接节点边的权值为邻接节点的grid
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param grid
     * @param health
     * @return
     */
    public boolean findSafeWalk(List<List<Integer>> grid, int health) {
        int m = grid.size();
        int n = grid.get(0).size();

        //节点(0,0)到其他节点需要的最小健康值数组
        int[][] cost = new int[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //cost数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)需要的最小健康值为grid[0][0]
        cost[0][0] = grid.get(0).get(0);

        //arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点需要的健康值，注意：当前需要的健康值不一定是需要的最小健康值
        Queue<int[]> queue = new LinkedList<>();

        //节点(0,0)入队
        queue.offer(new int[]{0, 0, cost[0][0]});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //当前节点(x1,y1)
            int x1 = arr[0];
            int y1 = arr[1];
            //节点(0,0)到当前节点(x1,y1)需要的健康值，注意：当前需要的健康值不一定是需要的最小健康值
            int curCost = arr[2];

            //curCost大于等于health，则从节点(0,0)不能到达当前节点(x1,y1)，直接进行下次循环
            if (curCost >= health) {
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

                //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点需要的最小健康值，更新cost[x2][y2]，节点(x2,y2)入队
                if (curCost + grid.get(x2).get(y2) < cost[x2][y2]) {
                    cost[x2][y2] = curCost + grid.get(x2).get(y2);
                    queue.offer(new int[]{x2, y2, cost[x2][y2]});
                }
            }
        }

        //节点(0,0)到节点(m-1,n-1)需要的最小健康值小于health，则返回true；否则，返回false
        return cost[m - 1][n - 1] < health;
    }

    /**
     * Dijkstra求节点(0,0)到节点(m-1,n-1)需要的最小健康值
     * 当前节点到邻接节点边的权值为邻接节点的grid
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param grid
     * @param health
     * @return
     */
    public boolean findSafeWalk2(List<List<Integer>> grid, int health) {
        int m = grid.size();
        int n = grid.get(0).size();

        //节点(0,0)到其他节点需要的最小健康值数组
        int[][] cost = new int[m][n];
        //节点访问数组，visited[i][j]为true，表示已经得到节点(0,0)到节点(i,j)需要的最小健康值
        boolean[][] visited = new boolean[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //cost数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)需要的最小健康值为grid[0][0]
        cost[0][0] = grid.get(0).get(0);

        for (int i = 0; i < m * n; i++) {
            //初始化cost数组中未访问节点中选择距离节点(0,0)需要的最小健康值的节点(x1,y1)
            int x1 = -1;
            int y1 = -1;

            //未访问节点中选择距离节点(0,0)需要的最小健康值的节点(x1,y1)
            for (int j = 0; j < m * n; j++) {
                int x2 = j / n;
                int y2 = j % n;

                if (!visited[x2][y2] && ((x1 == -1 && y1 == -1) || cost[x2][y2] < cost[x1][y1])) {
                    x1 = x2;
                    y1 = y2;
                }
            }

            //设置节点(x1,y1)已访问，表示已经得到节点(0,0)到节点(x1,y1)需要的最小健康值
            visited[x1][y1] = true;

            //已经得到节点(0,0)到节点(m-1,n-1)需要的最小健康值，节点(0,0)到节点(m-1,n-1)需要的最小健康值小于health，则返回true；否则，返回false
            if (x1 == m - 1 && y1 == n - 1) {
                return cost[m - 1][n - 1] < health;
            }

            //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点需要的最小健康值
            for (int j = 0; j < direction.length; j++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[j][0];
                int y2 = y1 + direction[j][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                if (!visited[x2][y2] && cost[x1][y1] + grid.get(x2).get(y2) < cost[x2][y2]) {
                    cost[x2][y2] = cost[x1][y1] + grid.get(x2).get(y2);
                }
            }
        }

        //遍历结束，则节点(0,0)到节点(m-1,n-1)需要的最小健康值大于等于health，返回true
        return false;
    }

    /**
     * 堆优化Dijkstra求节点(0,0)到节点(m-1,n-1)需要的最小健康值
     * 当前节点到邻接节点边的权值为邻接节点的grid
     * 时间复杂度O(mn*log(mn))，空间复杂度O(mn)
     *
     * @param grid
     * @param health
     * @return
     */
    public boolean findSafeWalk3(List<List<Integer>> grid, int health) {
        int m = grid.size();
        int n = grid.get(0).size();

        //节点(0,0)到其他节点需要的最小健康值数组
        int[][] cost = new int[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //cost数组初始化，初始化为int最大值表示节点(0,0)无法到达节点(i,j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点(0,0)到节点(0,0)需要的最小健康值为grid[0][0]
        cost[0][0] = grid.get(0).get(0);

        //小根堆，arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点(0,0)到当前节点需要的健康值，注意：当前需要的健康值不一定是需要的最小健康值
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
            //节点(0,0)到当前节点(x1,y1)需要的健康值，注意：当前需要的健康值不一定是需要的最小健康值
            int curCost = arr[2];

            //curCost大于cost[x1][y1]，则当前节点(x1,y1)不能作为中间节点更新节点(0,0)到其他节点需要的最小健康值，直接进行下次循环
            if (curCost > cost[x1][y1]) {
                continue;
            }

            //已经得到节点(0,0)到节点(m-1,n-1)需要的最小健康值，节点(0,0)到节点(m-1,n-1)需要的最小健康值小于health，则返回true；否则，返回false
            if (x1 == m - 1 && y1 == n - 1) {
                return curCost < health;
            }

            //遍历节点(x1,y1)的邻接节点(x2,y2)
            for (int i = 0; i < direction.length; i++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
                    continue;
                }

                //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点需要的最小健康值，更新cost[x2][y2]，节点(x2,y2)入堆
                if (curCost + grid.get(x2).get(y2) < cost[x2][y2]) {
                    cost[x2][y2] = curCost + grid.get(x2).get(y2);
                    priorityQueue.offer(new int[]{x2, y2, cost[x2][y2]});
                }
            }
        }

        //遍历结束，则节点(0,0)到节点(m-1,n-1)需要的最小健康值大于等于health，返回true
        return false;
    }
}
