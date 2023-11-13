package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/10/21 08:00
 * @Author zsy
 * @Description 到达目的地的方案数 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1334、Problem1368、Problem1462、Problem1514、Problem1786、Problem1928、Dijkstra 拓扑排序类比 图类比
 * 你在一个城市里，城市由 n 个路口组成，路口编号为 0 到 n - 1 ，某些路口之间有 双向 道路。
 * 输入保证你可以从任意路口出发到达其他任意路口，且任意两个路口之间最多有一条路。
 * 给你一个整数 n 和二维整数数组 roads ，其中 roads[i] = [ui, vi, timei] 表示在路口 ui 和 vi 之间有一条需要花费 timei 时间才能通过的道路。
 * 你想知道花费 最少时间 从路口 0 出发到达路口 n - 1 的方案数。
 * 请返回花费 最少时间 到达目的地的 路径数目 。
 * 由于答案可能很大，将结果对 10^9 + 7 取余 后返回。
 * <p>
 * 输入：n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],[6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
 * 输出：4
 * 解释：从路口 0 出发到路口 6 花费的最少时间是 7 分钟。
 * 四条花费 7 分钟的路径分别为：
 * - 0 ➝ 6
 * - 0 ➝ 4 ➝ 6
 * - 0 ➝ 1 ➝ 2 ➝ 5 ➝ 6
 * - 0 ➝ 1 ➝ 3 ➝ 5 ➝ 6
 * <p>
 * 输入：n = 2, roads = [[1,0,10]]
 * 输出：1
 * 解释：只有一条从路口 0 到路口 1 的路，花费 10 分钟。
 * <p>
 * 1 <= n <= 200
 * n - 1 <= roads.length <= n * (n - 1) / 2
 * roads[i].length == 3
 * 0 <= ui, vi <= n - 1
 * 1 <= timei <= 10^9
 * ui != vi
 * 任意两个路口之间至多有一条路。
 * 从任意路口出发，你能够到达其他任意路口。
 */
public class Problem1976 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem1976 problem1976 = new Problem1976();
        int n = 7;
        int[][] roads = {{0, 6, 7}, {0, 1, 2}, {1, 2, 3}, {1, 3, 3}, {6, 3, 3},
                {3, 5, 1}, {6, 5, 1}, {2, 5, 1}, {0, 4, 5}, {4, 6, 2}};
        System.out.println(problem1976.countPaths(n, roads));
    }

    /**
     * Dijkstra+bfs拓扑排序+动态规划
     * 核心思想：Dijkstra求节点0到其他节点的最短路径长度，通过节点0到其他节点的最短路径长度，将无向图转换为有向图，
     * 有向图才能拓扑排序，在bfs拓扑排序过程中，通过动态规划求两个节点之间最短路径的个数
     * dp[i]：节点0到节点i最短路径的个数
     * dp[i] = dp[i] + dp[j] (图为有向图，存在节点j到节点i的边)
     * Dijkstra求节点0到其他节点的最短路径长度，通过节点0到其他节点的最短路径长度，将无向图转换为有向图，
     * 节点0到节点u的最短路径长度+节点u和节点v边的权值==节点0到节点v的最短路径长度，则有向图中存在节点u到节点v的边，不存在节点v到节点u的边
     * 图中入度为0的节点入队，队列中节点出队，存在节点u到节点v的边，则dp[v]=dp[v]+dp[u]
     * 时间复杂度O(n^2+m)，空间复杂度O(n^2) (m=roads.length，即无向图中边的数量)
     * (Dijkstra的时间复杂度O(n^2)，拓扑排序的时间复杂度O(m+n)，无向图转换为有向图的时间复杂度O(m)，邻接矩阵的空间复杂度O(n^2))
     *
     * @param n
     * @param roads
     * @return
     */
    public int countPaths(int n, int[][] roads) {
        //邻接矩阵，先作为无向图，之后通过Dijkstra转换为有向图
        int[][] edges = new int[n][n];

        //邻接矩阵初始化，节点i到节点j不存在边，则为-1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    edges[i][j] = -1;
                }
            }
        }

        for (int i = 0; i < roads.length; i++) {
            int u = roads[i][0];
            int v = roads[i][1];
            int weight = roads[i][2];
            edges[u][v] = weight;
            edges[v][u] = weight;
        }

        //节点0到其他节点的最短路径长度数组
        long[] distance = dijkstra(0, edges);
        //入度数组
        int[] inDegree = new int[n];

        //通过Dijkstra无向图转换为有向图，有向图才能拓扑排序
        for (int i = 0; i < roads.length; i++) {
            int u = roads[i][0];
            int v = roads[i][1];
            int weight = roads[i][2];

            //节点0到节点u的最短路径长度+节点u和节点v边的权值==节点0到节点v的最短路径长度，则有向图中存在节点u到节点v的边，不存在节点v到节点u的边
            if (distance[u] + weight == distance[v]) {
                edges[u][v] = weight;
                edges[v][u] = -1;
                inDegree[v]++;
            } else if (distance[v] + weight == distance[u]) {
                //点0到节点v的最短路径长度+节点u和节点v边的权值==节点0到节点u的最短路径长度，则有向图中存在节点v到节点u的边，不存在节点u到节点v的边
                edges[v][u] = weight;
                edges[u][v] = -1;
                inDegree[u]++;
            } else {
                //有向图不存在节点u到节点v的边，或者节点v到节点u的边，边赋值为-1
                edges[u][v] = -1;
                edges[v][u] = -1;
            }
        }

        //存放入度为0节点的队列
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        //节点0到节点i最短路径的个数
        int[] dp = new int[n];
        //dp初始化
        dp[0] = 1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < n; v++) {
                //图中边大于0表示存在节点u到节点v的边
                if (edges[u][v] > 0) {
                    inDegree[v]--;

                    if (inDegree[v] == 0) {
                        queue.offer(v);
                    }

                    //更新节点0到节点v最短路径的个数
                    dp[v] = (dp[v] + dp[u]) % MOD;
                }
            }
        }

        return dp[n - 1];
    }

    /**
     * Dijkstra求节点u到其他节点的最短路径长度
     * 注意：不适合权值为负的图
     * 每次从未访问节点中选择距离节点u最短路径长度的节点v，节点v作为中间节点更新节点u到其他节点的最短路径长度
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param u
     * @param edges
     * @return
     */
    private long[] dijkstra(int u, int[][] edges) {
        //图中节点的个数
        int n = edges.length;
        //节点u到其他节点的最短路径长度数组，使用long避免相加权值在int范围内溢出
        long[] distance = new long[n];
        //节点访问数组，visited[v]为true，表示已经得到节点u到节点v的最短路径长度
        boolean[] visited = new boolean[n];

        //distance数组初始化，初始化为int最大值表示节点u无法到达节点i
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
        }

        //初始化，节点u到节点u的最短路径长度为0
        distance[u] = 0;

        //每次从未访问节点中选择距离节点u最短路径长度的节点v，节点v作为中间节点更新节点u到其他节点的最短路径长度
        for (int i = 0; i < n; i++) {
            //初始化distance数组中未访问节点中选择距离节点u最短路径长度的节点v
            int v = -1;

            //未访问节点中选择距离节点u最短路径长度的节点v
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (v == -1 || distance[j] < distance[v])) {
                    v = j;
                }
            }

            //设置节点v已访问，表示已经得到节点u到节点v的最短路径长度
            visited[v] = true;

            //节点v作为中间节点更新节点u到其他节点的最短路径长度
            for (int j = 0; j < n; j++) {
                if (!visited[j] && edges[v][j] != -1) {
                    distance[j] = Math.min(distance[j], distance[v] + edges[v][j]);
                }
            }
        }

        return distance;
    }
}
