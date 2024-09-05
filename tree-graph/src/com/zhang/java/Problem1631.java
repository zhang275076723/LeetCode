package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/24 08:08
 * @Author zsy
 * @Description 最小体力消耗路径 类比Problem778、Problem1102、Problem2812 bfs类比Problem407、Problem499、Problem505、Problem778、Problem847、Problem1129、Problem1293、Problem1368、Problem2045、Problem2290 二分查找类比778 最小生成树类比Problem778、Problem1135、Problem1168、Problem1489、Problem1584、Prim 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1293、Problem1334、Problem1368、Problem1462、Problem1514、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2203、Problem2290、Problem2473、Problem2662、Dijkstra
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
        System.out.println(problem1631.minimumEffortPath4(heights));
        System.out.println(problem1631.minimumEffortPath5(heights));
    }

    /**
     * bfs
     * 当前节点和邻接节点边的权值为两者的高度差
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param heights
     * @return
     */
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        //节点(0,0)到其他节点最小消耗的体力值数组，即节点(0,0)到当前节点路径中相邻节点差的最大值的最小值
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
     * 当前节点和邻接节点边的权值为两者的高度差
     * 时间复杂度O((mn)^2)，空间复杂度O(mn)
     *
     * @param heights
     * @return
     */
    public int minimumEffortPath2(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        //节点(0,0)到其他节点最小消耗的体力值数组，即节点(0,0)到当前节点路径中相邻节点差的最大值的最小值
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

            //已经得到节点(0,0)到节点(m-1,n-1)最小消耗的体力值，直接跳出循环
            if (x1 == m - 1 && y1 == n - 1) {
                break;
            }

            //节点(x1,y1)作为中间节点更新节点(0,0)到其他节点最小消耗的体力值
            for (int j = 0; j < direction.length; j++) {
                //节点(x1,y1)的邻接节点(x2,y2)
                int x2 = x1 + direction[j][0];
                int y2 = y1 + direction[j][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n || visited[x2][y2]) {
                    continue;
                }

                //节点(x1,y1)到邻接节点(x2,y2)消耗的体力值
                int curCost = Math.abs(heights[x1][y1] - heights[x2][y2]);

                if (Math.max(cost[x1][y1], curCost) < cost[x2][y2]) {
                    cost[x2][y2] = Math.max(cost[x1][y1], curCost);
                }
            }
        }

        return cost[m - 1][n - 1];
    }

    /**
     * 堆优化Dijkstra求节点(0,0)到节点(m-1,n-1)最小消耗的体力值
     * 当前节点和邻接节点边的权值为两者的高度差
     * 时间复杂度O(mn*log(mn))，空间复杂度O(mn)
     * (堆优化Dijkstra的时间复杂度O(mlogm)，其中m为图中边的个数，本题边的个数O(mn)，所以时间复杂度O(mn*log(mn)))
     *
     * @param heights
     * @return
     */
    public int minimumEffortPath3(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        //节点(0,0)到其他节点最小消耗的体力值数组，即节点(0,0)到当前节点路径中相邻节点差的最大值的最小值
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

            //已经得到节点(0,0)到节点(m-1,n-1)最小消耗的体力值，直接跳出循环
            if (x1 == m - 1 && y1 == n - 1) {
                break;
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

        return cost[m - 1][n - 1];
    }

    /**
     * Kruskal求最小生成树
     * 当前节点和邻接节点边的权值为两者的高度差
     * 核心思想：节点(0,0)到节点(m-1,n-1)的最小生成树路径中相邻节点差的绝对值的最大值，即为最小体力消耗值
     * 图中边的权值由小到大排序，由小到大遍历排好序的边，当前边两个节点已经连通，即当前边作为最小生成树的边会成环，
     * 当前边不能作为最小生成树的边，直接进行下次循环；当前边两个节点不连通，则当前边能够作为最小生成树的边，当前边的两个节点相连，
     * 遍历结束，判断所有节点是否连通，即只有一个连通分量，则能得到最小生成树；否则不能得到最小生成树
     * 时间复杂度O(mnlog(mn)+mn*α(mn))=O(mnlog(mn))，空间复杂度O(mn) (find()和union()的时间复杂度为O(α(mn))，可视为常数O(1))
     *
     * @param heights
     * @return
     */
    public int minimumEffortPath4(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        //arr[0]：当前边的节点u，arr[1]：当前边的节点v，arr[1]：当前边的长度，即节点u和节点v差的绝对值
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //只考虑当前节点(i,j)和右边节点(i,j+1)的边
                if (j + 1 < n) {
                    list.add(new int[]{i * n + j, i * n + j + 1, Math.abs(heights[i][j] - heights[i][j + 1])});
                }

                //只考虑当前节点(i,j)和下边节点(i+1,j)的边
                if (i + 1 < m) {
                    list.add(new int[]{i * n + j, (i + 1) * n + j, Math.abs(heights[i][j] - heights[i + 1][j])});
                }
            }
        }

        //图中边的权值由小到大排序
        list.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });

        UnionFind unionFind = new UnionFind(m * n);
        //节点(0,0)到节点(m-1,n-1)的最小生成树路径中相邻节点差的绝对值的最大值，即为最小体力消耗值
        int result = 0;

        for (int i = 0; i < list.size(); i++) {
            int[] arr = list.get(i);
            int u = arr[0];
            int v = arr[1];
            int weight = arr[2];

            //节点(0,0)和节点(m-1,n-1)已经连通，则已经找到节点(0,0)到节点(m-1,n-1)的最小体力消耗值
            if (unionFind.isConnected(0, m * n - 1)) {
                break;
            }

            //当前边作为最小生成树的边成环，则当前边不能作为最小生成树的边，直接进行下次循环
            if (unionFind.isConnected(u, v)) {
                continue;
            }

            unionFind.union(u, v);
            result = Math.max(result, weight);
        }

        return result;
    }

    /**
     * 二分查找+bfs
     * 当前节点和邻接节点边的权值为两者的高度差
     * 二分查找变形，使...最大值尽可能小，就要想到二分查找
     * 对[left,right]进行二分查找，left为0，right为heights最大值-heights最小值，判断节点(0,0)到节点(m-1,n-1)的路径中相邻节点差的绝对值的最大值(体力消耗值)是否大于mid，
     * 如果体力消耗值大于mid，则节点(0,0)到节点(m-1,n-1)的最小体力消耗值在mid右边，left=mid+1；
     * 如果体力消耗值小于等于mid，则节点(0,0)到节点(m-1,n-1)的最小体力消耗值在mid或mid左边，right=mid
     * 时间复杂度O(mn*log(max(heights[i])-min(heights[i])))=O(mn)，空间复杂度O(mn)
     *
     * @param heights
     * @return
     */
    public int minimumEffortPath5(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        int max = heights[0][0];
        int min = heights[0][0];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                max = Math.max(max, heights[i][j]);
                min = Math.min(min, heights[i][j]);
            }
        }

        int left = 0;
        int right = max - min;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //arr[0]：节点的横坐标，arr[1]：节点的纵坐标
            Queue<int[]> queue = new LinkedList<>();
            boolean[][] visited = new boolean[m][n];
            int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            queue.offer(new int[]{0, 0});
            visited[0][0] = true;

            while (!queue.isEmpty()) {
                int[] arr = queue.poll();
                int x1 = arr[0];
                int y1 = arr[1];

                //已经访问到节点(m-1,n-1)，则跳出循环
                if (visited[m - 1][n - 1]) {
                    break;
                }

                //遍历节点(x1,y1)的邻接节点(x2,y2)
                for (int i = 0; i < direction.length; i++) {
                    //节点(x1,y1)的邻接节点(x2,y2)
                    int x2 = x1 + direction[i][0];
                    int y2 = y1 + direction[i][1];

                    //邻接节点(x2,y2)越界，或者邻接节点(x2,y2)已访问，则不合法，直接进行下次循环
                    if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n || visited[x2][y2]) {
                        continue;
                    }

                    //节点(x1,y1)和节点(x2,y2)差的绝对值小于等于mid，则当前路径消耗的体力值小于等于mid，节点(x2,y2)加入队列
                    if (Math.abs(heights[x1][y1] - heights[x2][y2]) <= mid) {
                        queue.offer(new int[]{x2, y2});
                        visited[x2][y2] = true;
                    }
                }
            }

            //节点(0,0)到节点(m-1,n-1)的路径中相邻节点差的绝对值的最大值(体力消耗值)小于等于mid，
            //则最小体力消耗值在mid或mid左边，right=mid
            if (visited[m - 1][n - 1]) {
                right = mid;
            } else {
                //节点(0,0)到节点(m-1,n-1)的路径中相邻节点差的绝对值的最大值(体力消耗值)大于mid，
                //则最小体力消耗值在mid右边，left=mid+1
                left = mid + 1;
            }
        }

        return left;
    }

    /**
     * 并查集
     */
    private static class UnionFind {
        private int count;
        private final int[] parent;
        private final int[] weight;

        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            weight = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                weight[i] = 1;
            }
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (weight[rootI] < weight[rootJ]) {
                    parent[rootI] = rootJ;
                } else if (weight[rootI] > weight[rootJ]) {
                    parent[rootJ] = rootI;
                } else {
                    parent[rootJ] = rootI;
                    weight[rootI]++;
                }

                count--;
            }
        }

        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }

            return parent[i];
        }

        public boolean isConnected(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            return rootI == rootJ;
        }
    }
}
