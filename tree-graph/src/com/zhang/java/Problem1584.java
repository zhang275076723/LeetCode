package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Date 2023/11/5 08:10
 * @Author zsy
 * @Description 连接所有点的最小费用 最小生成树类比Problem1135、Problem1489、Prim 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1489、Problem1568、Problem1627、Problem1905、Problem1998、Problem2685
 * 给你一个points 数组，表示 2D 平面上的一些点，其中 points[i] = [xi, yi] 。
 * 连接点 [xi, yi] 和点 [xj, yj] 的费用为它们之间的 曼哈顿距离 ：|xi - xj| + |yi - yj| ，其中 |val| 表示 val 的绝对值。
 * 请你返回将所有点连接的最小总费用。
 * 只有任意两点之间 有且仅有 一条简单路径时，才认为所有点都已连接。
 * <p>
 * 输入：points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
 * 输出：20
 * 我们可以按照上图所示连接所有点得到最小总费用，总费用为 20 。
 * 注意到任意两个点之间只有唯一一条路径互相到达。
 * <p>
 * 输入：points = [[3,12],[-2,5],[-4,1]]
 * 输出：18
 * <p>
 * 输入：points = [[0,0],[1,1],[1,0],[-1,1]]
 * 输出：4
 * <p>
 * 输入：points = [[0,0],[1,1],[1,0],[-1,1]]
 * 输出：4
 * <p>
 * 输入：points = [[0,0]]
 * 输出：0
 * <p>
 * 1 <= points.length <= 1000
 * -10^6 <= xi, yi <= 10^6
 * 所有点 (xi, yi) 两两不同。
 */
public class Problem1584 {
    public static void main(String[] args) {
        Problem1584 problem1584 = new Problem1584();
        int[][] points = {{0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}};
        System.out.println(problem1584.minCostConnectPoints(points));
        System.out.println(problem1584.minCostConnectPoints2(points));
        System.out.println(problem1584.minCostConnectPoints3(points));
    }

    /**
     * Kruskal求最小生成树
     * 图中边的权值由小到大排序，由小到大遍历排好序的边，当前边两个节点已经连通，即当前边作为最小生成树的边会成环，
     * 当前边不能作为最小生成树的边，直接进行下次循环；当前边两个节点不连通，则当前边能够作为最小生成树的边，当前边的两个节点相连，
     * 遍历结束，判断所有节点是否连通，即只有一个连通分量，则能得到最小生成树；否则不能得到最小生成树
     * 时间复杂度O(mlogm+m*α(n))=O(mlogm+m)=O(mlogm)=O(n^2log(n^2))=O(n^2logn)，空间复杂度O(m+n)=O(n^2)
     * (n=points.length，即图中节点的个数) (m=n^2，即图中边的个数，完全图中存在n*(n-1)/2条边)
     * (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param points
     * @return
     */
    public int minCostConnectPoints(int[][] points) {
        //图中节点的个数
        int n = points.length;

        //n个节点完全图的边集合，arr[0]：当前边的节点u，arr[1]：当前边的节点v，arr[2]：当前边的曼哈顿距离
        List<int[]> list = new ArrayList<>();

        //得到完全图的边
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                list.add(new int[]{i, j, Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1])});
            }
        }

        //图中边的曼哈顿距离由小到大排序
        list.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });

        UnionFind unionFind = new UnionFind(n);
        //图的最小生成树的权值
        int minWeight = 0;

        for (int i = 0; i < list.size(); i++) {
            int[] arr = list.get(i);

            //当前边的节点u
            int u = arr[0];
            //当前边的节点v
            int v = arr[1];
            //当前边的权值
            int weight = arr[2];

            //当前边作为最小生成树的边成环，则当前边不能作为最小生成树的边，直接进行下次循环
            if (unionFind.isConnected(u, v)) {
                continue;
            }

            //当前边作为最小生成树的边，节点u和节点v相连
            unionFind.union(u, v);
            minWeight = minWeight + weight;
        }

        //图中所有节点连通，即只有一个连通分量，则能得到最小生成树，返回最小生成树的权值；否则不能得到最小生成树，返回-1
        //本图为完全图，肯定存在最小生成树，可以直接返回minWeight
        return unionFind.count == 1 ? minWeight : -1;
    }

    /**
     * Prim求最小生成树
     * 选择一个节点作为起始节点，加入已访问节点集合，更新未访问节点到已访问节点集合的最短路径长度，
     * 从未访问节点中选择距离已访问节点集合最短路径长度的节点u，节点u作为中间节点更新未访问节点到已访问节点集合的最短路径长度，
     * 节点u和已访问节点集合中某个节点构成的最小生成树边，当前边加入结果集合；
     * 不存在未访问节点中距离已访问节点集合最短路径长度的节点，则无法形成最小生成树
     * 时间复杂度O(n^2)，空间复杂度O(n^2) (n=points.length，即图中节点的个数)
     *
     * @param points
     * @return
     */
    public int minCostConnectPoints2(int[][] points) {
        //图中节点的个数
        int n = points.length;

        //邻接矩阵
        int[][] edges = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    edges[i][j] = 0;
                } else {
                    //存储节点i到节点j的曼哈顿距离
                    edges[i][j] = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                }
            }
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
        int minWeight = 0;

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
            //本图为完全图，则肯定存在最小生成树
            if (distance[u] == Integer.MAX_VALUE) {
                return -1;
            }

            //设置节点u已访问，表示节点u和vertexArr[u]构成的最小生成树边
            visited[u] = true;
            //节点u和vertexArr[u]构成的最小生成树边，加入minWeight
            minWeight = minWeight + edges[u][vertexArr[u]];

            //节点u作为中间节点更新未访问节点到已访问节点集合的最短路径长度
            for (int j = 0; j < n; j++) {
                if (!visited[j] && edges[u][j] != -1 && edges[u][j] < distance[j]) {
                    distance[j] = edges[u][j];
                    //节点j到已访问节点集合是通过节点u得到的最短路径长度
                    vertexArr[j] = u;
                }
            }
        }

        return minWeight;
    }

    /**
     * 堆优化Prim求最小生成树
     * 选择一个节点作为起始节点，加入已访问节点集合，更新未访问节点到已访问节点集合的最短路径长度，
     * 通过优先队列找未访问节点中距离已访问节点集合最短路径长度的节点u，节点u作为中间节点更新未访问节点到已访问节点集合的最短路径长度，
     * 节点u和已访问节点集合中某个节点构成的最小生成树边，当前边加入结果集合；
     * 不存在未访问节点中距离已访问节点集合最短路径长度的节点，则无法形成最小生成树
     * 时间复杂度O(nlogn)，空间复杂度O(n^2) (n=points.length，即图中节点的个数)
     *
     * @param points
     * @return
     */
    public int minCostConnectPoints3(int[][] points) {
        //图中节点的个数
        int n = points.length;

        //邻接矩阵
        int[][] edges = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    edges[i][j] = 0;
                } else {
                    //存储节点i到节点j的曼哈顿距离
                    edges[i][j] = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                }
            }
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
        int minWeight = 0;
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
            //节点u和vertexArr[u]构成的最小生成树边，加入minWeight
            minWeight = minWeight + edges[u][vertexArr[u]];
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
        //本图为完全图，肯定存在最小生成树，可以直接返回minWeight
        return count == n - 1 ? minWeight : -1;
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
