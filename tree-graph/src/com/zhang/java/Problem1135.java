package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2023/11/2 08:11
 * @Author zsy
 * @Description 最低成本联通所有城市 最小生成树类比Problem1489、Problem1584、Prim 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1254、Problem1319、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem1998
 * 想象一下你是个城市基建规划者，地图上有 n 座城市，它们按以 1 到 n 的次序编号。
 * 给你整数 n 和一个数组 conections，其中 connections[i] = [xi, yi, costi] 表示将城市 xi 和城市 yi 连接所要的costi（连接是双向的）。
 * 返回连接所有城市的最低成本，每对城市之间至少有一条路径。
 * 如果无法连接所有 n 个城市，返回 -1
 * 该 最小成本 应该是所用全部连接成本的总和。
 * <p>
 * 输入：n = 3, conections = [[1,2,5],[1,3,6],[2,3,1]]
 * 输出：6
 * 解释：选出任意 2 条边都可以连接所有城市，我们从中选取成本最小的 2 条。
 * <p>
 * 输入：n = 4, conections = [[1,2,3],[3,4,4]]
 * 输出：-1
 * 解释：即使连通所有的边，也无法连接所有城市。
 * <p>
 * 1 <= n <= 10^4
 * 1 <= connections.length <= 10^4
 * connections[i].length == 3
 * 1 <= xi, yi <= n
 * xi != yi
 * 0 <= costi <= 10^5
 */
public class Problem1135 {
    public static void main(String[] args) {
        Problem1135 problem1135 = new Problem1135();
        int n = 7;
        int[][] connections = {{0, 2, 1}, {0, 3, 2}, {0, 5, 3}, {1, 2, 4},
                {2, 3, 5}, {4, 6, 6}, {1, 4, 7}, {1, 3, 8}, {5, 6, 9}};
        System.out.println(problem1135.minimumCost(n, connections));
        System.out.println(problem1135.minimumCost2(n, connections));
        System.out.println(problem1135.minimumCost3(n, connections));
    }

    /**
     * Kruskal求最小生成树
     * 图中边的权值由小到大排序，由小到大遍历排好序的边，当前边两个节点已经连通，即当前边作为最小生成树的边会成环，
     * 当前边不能作为最小生成树的边，直接进行下次循环；当前边两个节点不连通，则当前边能够作为最小生成树的边，当前边的两个节点相连，
     * 遍历结束，判断所有节点是否连通，即只有一个连通分量，则能得到最小生成树；否则不能得到最小生成树
     * 时间复杂度O(mlogm+m*α(n))=O(mlogm+m)=O(mlogm)，空间复杂度O(n) (m=connections.length，即图中边的个数)
     * (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param n
     * @param connections
     * @return
     */
    public int minimumCost(int n, int[][] connections) {
        //图中边权值由小到大排序
        mergeSort(connections, 0, connections.length - 1, new int[connections.length][2]);

        UnionFind unionFind = new UnionFind(n);
        //图的最小生成树的权值
        int minCost = 0;

        for (int i = 0; i < connections.length; i++) {
            //当前边的节点u
            int u = connections[i][0];
            //当前边的节点v
            int v = connections[i][1];
            //当前边的权值
            int cost = connections[i][2];

            //当前边作为最小生成树的边成环，则当前边不能作为最小生成树的边，直接进行下次循环
            if (unionFind.isConnected(u, v)) {
                continue;
            }

            //当前边作为最小生成树的边，节点u和节点v相连
            unionFind.union(u, v);
            minCost = minCost + cost;

        }

        //图中所有节点连通，即只有一个连通分量，则能得到最小生成树，返回最小生成树的权值；否则不能得到最小生成树，返回-1
        return unionFind.count == 1 ? minCost : -1;
    }

    /**
     * Prim求最小生成树
     * 选择一个节点作为起始节点，加入已访问节点集合，更新未访问节点到已访问节点集合的最短路径长度，
     * 从未访问节点中选择距离已访问节点集合最短路径长度的节点u，节点u作为中间节点更新未访问节点到已访问节点集合的最短路径长度，
     * 节点u和已访问节点集合中某个节点构成的最小生成树边，当前边加入结果集合；
     * 不存在未访问节点中距离已访问节点集合最短路径长度的节点，则无法形成最小生成树
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param n
     * @param connections
     * @return
     */
    public int minimumCost2(int n, int[][] connections) {
        //邻接矩阵
        int[][] edges = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    edges[i][j] = 0;
                } else {
                    edges[i][j] = -1;
                }
            }
        }

        for (int i = 0; i < connections.length; i++) {
            //当前边的节点u
            int u = connections[i][0];
            //当前边的节点v
            int v = connections[i][1];
            //当前边的权值
            int cost = connections[i][2];

            edges[u][v] = cost;
            edges[v][u] = cost;
        }

        //未访问节点到已访问节点集合的最短路径长度数组
        int[] distance = new int[n];
        //未访问节点到已访问节点集合是通过哪个节点得到的最短路径长度数组，
        //通过vertexArr得到当前节点和已访问节点集合某个节点构成的最小生成树边
        int[] vertexArr = new int[n];
        //节点访问数组，visited[u]为true，表示已经得到节点u到已访问节点集合的最短路径长度
        boolean[] visited = new boolean[n];

        //distance数组初始化，初始化为int最大值表示节点i无法到达已访问节点集合
        //vertexArr数组初始化，初始化为-1表示节点i无法到达已访问节点集合
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
            vertexArr[i] = -1;
        }

        //节点0作为起始节点
        visited[0] = true;

        //初始化，节点0加入已访问节点集合，更新其他节点到已访问节点集合的最短路径长度
        for (int i = 0; i < n; i++) {
            if (edges[0][i] != -1) {
                distance[i] = edges[0][i];
                //节点i到已访问节点集合是通过节点0得到的最短路径长度
                vertexArr[i] = 0;
            }
        }

        //图的最小生成树的权值
        int minCost = 0;

        //n个节点的最小生成树只需要遍历n-1次，得到n-1条边
        for (int i = 0; i < n - 1; i++) {
            //初始化distance数组中未访问节点中选择距离已访问节点集合最短路径长度的节点u
            int u = -1;

            //未访问节点中选择距离已访问节点集合最短路径长度的节点u
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || distance[j] < distance[u])) {
                    u = j;
                }
            }

            //不存在未访问节点中距离已访问节点集合最短路径长度的节点，则无法形成最小生成树，返回-1
            if (distance[u] == Integer.MAX_VALUE) {
                return -1;
            }

            //设置节点u已访问，表示节点u和vertexArr[u]构成的最小生成树边
            visited[u] = true;
            //节点u和vertexArr[u]构成的最小生成树边，加入minCost
            minCost = minCost + edges[u][vertexArr[u]];

            //节点u作为中间节点更新未访问节点到已访问节点集合的最短路径长度
            for (int j = 0; j < n; j++) {
                if (!visited[j] && edges[u][j] != -1 && edges[u][j] < distance[j]) {
                    distance[j] = edges[u][j];
                    //节点j到已访问节点集合是通过节点u得到的最短路径长度
                    vertexArr[j] = u;
                }
            }
        }

        return minCost;
    }

    /**
     * 堆优化Prim求最小生成树
     * 选择一个节点作为起始节点，加入已访问节点集合，更新未访问节点到已访问节点集合的最短路径长度，
     * 通过优先队列找未访问节点中距离已访问节点集合最短路径长度的节点u，节点u作为中间节点更新未访问节点到已访问节点集合的最短路径长度，
     * 节点u和已访问节点集合中某个节点构成的最小生成树边，当前边加入结果集合；
     * 不存在未访问节点中距离已访问节点集合最短路径长度的节点，则无法形成最小生成树
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param n
     * @param connections
     * @return
     */
    public int minimumCost3(int n, int[][] connections) {
        //邻接矩阵
        int[][] edges = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    edges[i][j] = 0;
                } else {
                    edges[i][j] = -1;
                }
            }
        }

        for (int i = 0; i < connections.length; i++) {
            //当前边的节点u
            int u = connections[i][0];
            //当前边的节点v
            int v = connections[i][1];
            //当前边的权值
            int cost = connections[i][2];

            edges[u][v] = cost;
            edges[v][u] = cost;
        }

        //未访问节点到已访问节点集合的最短路径长度数组
        int[] distance = new int[n];
        //未访问节点到已访问节点集合是通过哪个节点得到的最短路径长度数组，
        //通过vertexArr得到当前节点和已访问节点集合某个节点构成的最小生成树边
        int[] vertexArr = new int[n];
        //节点访问数组，visited[u]为true，表示已经得到节点u到已访问节点集合的最短路径长度
        boolean[] visited = new boolean[n];

        //distance数组初始化，初始化为int最大值表示节点i无法到达已访问节点集合
        //vertexArr数组初始化，初始化为-1表示节点i无法到达已访问节点集合
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
            vertexArr[i] = -1;
        }

        //节点0作为起始节点
        visited[0] = true;

        //小根堆，arr[0]：当前节点，arr[i]：当前节点到已访问节点集合的路径长度
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] - arr2[1];
            }
        });

        //节点0作为起始节点，和节点0相连的边入堆
        for (int i = 0; i < n; i++) {
            if (edges[0][i] != -1) {
                distance[i] = edges[0][i];
                //节点i到已访问节点集合是通过节点0得到的最短路径长度
                vertexArr[i] = 0;
                priorityQueue.offer(new int[]{i, distance[i]});
            }
        }

        //图的最小生成树的权值
        int minCost = 0;
        //最小生成树边的个数，用于判断是否存在最小生成树
        int count = 0;

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //通过优先队列找未访问节点到已访问节点集合的最短路径长度的节点u
            int u = arr[0];

            if (visited[u]) {
                continue;
            }

            //设置节点u已访问，表示节点u和vertexArr[u]构成的最小生成树边
            visited[u] = true;
            //节点u和vertexArr[u]构成的最小生成树边，加入minCost
            minCost = minCost + edges[u][vertexArr[u]];
            //最小生成树边的个数加1
            count++;

            //节点u作为中间节点更新未访问节点到已访问节点集合的最短路径长度
            for (int i = 0; i < n; i++) {
                if (!visited[i] && edges[u][i] != -1 && edges[u][i] < distance[i]) {
                    distance[i] = edges[u][i];
                    //节点i到已访问节点集合是通过节点u得到的最短路径长度
                    vertexArr[i] = u;
                    //节点i入堆，用于下一次找未访问节点到已访问节点集合的最短路径长度的节点u
                    priorityQueue.offer(new int[]{i, distance[i]});
                }
            }
        }

        //n个节点的最小生成树需要n-1条边，没有n-1条边，则不存在最小生成树
        return count == n - 1 ? minCost : -1;
    }

    private void mergeSort(int[][] arr, int left, int right, int[][] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);

        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
    }

    private void merge(int[][] arr, int left, int mid, int right, int[][] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i][2] < arr[j][2]) {
                tempArr[k] = arr[i];
                i++;
                k++;
            } else {
                tempArr[k] = arr[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = arr[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            arr[k] = tempArr[k];
        }
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
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
