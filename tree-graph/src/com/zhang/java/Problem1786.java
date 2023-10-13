package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/10/25 08:14
 * @Author zsy
 * @Description 从第一个节点出发到最后一个节点的受限路径数 图中最短路径类比Problem399、Problem1462、Problem1976、Dijkstra 拓扑排序类比 图类比
 * 现有一个加权无向连通图。给你一个正整数 n ，表示图中有 n 个节点，并按从 1 到 n 给节点编号；
 * 另给你一个数组 edges ，其中每个 edges[i] = [ui, vi, weighti] 表示存在一条位于节点 ui 和 vi 之间的边，这条边的权重为 weighti 。
 * 从节点 start 出发到节点 end 的路径是一个形如 [z0, z1, z2, ..., zk] 的节点序列，
 * 满足 z0 = start 、zk = end 且在所有符合 0 <= i <= k-1 的节点 zi 和 zi+1 之间存在一条边。
 * 路径的距离定义为这条路径上所有边的权重总和。
 * 用 distanceToLastNode(x) 表示节点 n 和 x 之间路径的最短距离。
 * 受限路径 为满足 distanceToLastNode(zi) > distanceToLastNode(zi+1) 的一条路径，其中 0 <= i <= k-1 。
 * 返回从节点 1 出发到节点 n 的 受限路径数 。
 * 由于数字可能很大，请返回对 10^9 + 7 取余 的结果。
 * <p>
 * 输入：n = 5, edges = [[1,2,3],[1,3,3],[2,3,1],[1,4,2],[5,2,2],[3,5,1],[5,4,10]]
 * 输出：3
 * 解释：每个圆包含黑色的节点编号和蓝色的 distanceToLastNode 值。三条受限路径分别是：
 * 1) 1 --> 2 --> 5
 * 2) 1 --> 2 --> 3 --> 5
 * 3) 1 --> 3 --> 5
 * <p>
 * 输入：n = 7, edges = [[1,3,1],[4,1,2],[7,3,4],[2,5,3],[5,6,1],[6,7,2],[7,5,3],[2,6,4]]
 * 输出：1
 * 解释：每个圆包含黑色的节点编号和蓝色的 distanceToLastNode 值。唯一一条受限路径是：1 --> 3 --> 7 。
 * <p>
 * 1 <= n <= 2 * 10^4
 * n - 1 <= edges.length <= 4 * 10^4
 * edges[i].length == 3
 * 1 <= ui, vi <= n
 * ui != vi
 * 1 <= weighti <= 10^5
 * 任意两个节点之间至多存在一条边
 * 任意两个节点之间至少存在一条路径
 */
public class Problem1786 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem1786 problem1786 = new Problem1786();
//        int n = 5;
//        int[][] edges = {{1, 2, 3}, {1, 3, 3}, {2, 3, 1}, {1, 4, 2}, {5, 2, 2}, {3, 5, 1}, {5, 4, 10}};
        int n = 4;
        int[][] edges = {{1, 2, 2}, {1, 3, 3}, {2, 4, 8}, {3, 4, 2}};
        System.out.println(problem1786.countRestrictedPaths(n, edges));
    }

    /**
     * Dijkstra+bfs拓扑排序+动态规划 (朴素Dijkstra超时，要使用堆优化Dijkstra；使用邻接矩阵空间溢出，要使用邻接表)
     * 受限路径：节点u到节点v的一条路径中每个节点到节点v的最短路径长度递减，则是受限路径
     * 核心思想：Dijkstra求节点n到其他节点的最短路径长度，通过节点n到其他节点的最短路径长度，将无向图转换为有向图，
     * 有向图才能拓扑排序，在bfs拓扑排序过程中，通过动态规划求节点1到节点n受限路径的个数
     * dp[i]：节点1到节点i受限路径的个数
     * dp[i] = sum(dp[j]) (图为有向图，存在节点j到节点i的边)
     * Dijkstra求节点n到其他节点的最短路径长度，通过节点n到其他节点的最短路径长度，将无向图转换为有向图，
     * 节点u到节点n的最短路径长度 > 节点v到节点n的最短路径长度，则有向图中存在节点u到节点v的边，不存在节点v到节点u的边；
     * 节点u到节点n的最短路径长度 < 节点v到节点n的最短路径长度，则有向图中存在节点v到节点u的边，不存在节点u到节点v的边；
     * 节点u到节点n的最短路径长度 == 节点v和节点n边的权值，则有向图中不存在节点u到节点v的边和节点v到节点u的边，
     * 图中入度为0的节点入队，队列中节点出队，存在节点u到节点v的边，则dp[v]=dp[v]+dp[u]
     * 时间复杂度O(nlogn+m)，空间复杂度O(m+n) (m=edges.length，即无向图中边的数量)
     * (Dijkstra的时间复杂度O(nlogn)，拓扑排序的时间复杂度O(m+n)，无向图转换为有向图的时间复杂度O(m)，邻接表的空间复杂度O(m+n))
     *
     * @param n
     * @param edges
     * @return
     */
    public int countRestrictedPaths(int n, int[][] edges) {
        //邻接表，先作为无向图，之后通过Dijkstra转换为有向图
        //key：当前节点，value：当前节点的邻接节点和权值映射
        Map<Integer, Map<Integer, Integer>> graph1 = new HashMap<>();

        //注意：节点不是从0而是从1开始，需要多申请一个长度
        for (int i = 1; i <= n; i++) {
            graph1.put(i, new HashMap<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int weight = edges[i][2];
            graph1.get(u).put(v, weight);
            graph1.get(v).put(u, weight);
        }

        //节点n到其他节点的最短路径长度数组
        int[] distance = dijkstra(n, graph1);
        //入度数组，节点不是从0而是从1开始，需要多申请一个长度
        int[] inDegree = new int[n + 1];

        //邻接表，通过Dijkstra转换为的有向图
        List<List<Integer>> graph2 = new ArrayList<>();

        //注意：节点不是从0而是从1开始，需要多申请一个长度
        for (int i = 0; i <= n; i++) {
            graph2.add(new ArrayList<>());
        }

        //通过Dijkstra无向图转换为有向图，有向图才能拓扑排序
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            //节点u到节点n的最短路径长度 > 节点v到节点n的最短路径长度，则有向图中存在节点u到节点v的边，不存在节点v到节点u的边
            if (distance[u] > distance[v]) {
                graph2.get(u).add(v);
                inDegree[v]++;
            } else if (distance[u] < distance[v]) {
                //节点u到节点n的最短路径长度 < 节点v到节点n的最短路径长度，则有向图中存在节点v到节点u的边，不存在节点u到节点v的边
                graph2.get(v).add(u);
                inDegree[u]++;
            }
        }

        //存放入度为0节点的队列
        Queue<Integer> queue = new LinkedList<>();

        //注意：节点不是从0而是从1开始
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        //节点1到节点i受限路径的个数
        //注意：节点不是从0而是从1开始，需要多申请一个长度
        int[] dp = new int[n + 1];
        //dp初始化
        dp[1] = 1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            //节点u的邻接节点v
            for (int v : graph2.get(u)) {
                inDegree[v]--;

                if (inDegree[v] == 0) {
                    queue.offer(v);
                }

                //更新节点1到节点v受限路径的个数
                dp[v] = (dp[v] + dp[u]) % MOD;
            }
        }

        return dp[n];
    }

    /**
     * dijkstra求节点u到其他节点的最短路径长度
     * 优化：通过优先队列找节点u到未访问节点中最短路径长度的节点
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param u
     * @param edges
     * @return
     */
    private int[] dijkstra(int u, Map<Integer, Map<Integer, Integer>> edges) {
        //节点u到其他节点的最短路径长度数组
        //注意：节点不是从0而是从1开始，需要多申请一个长度
        int[] distance = new int[edges.size() + 1];
        //节点访问数组，visited[v]为true，表示已经得到节点u到节点v的最短路径长度
        //注意：节点不是从0而是从1开始，需要多申请一个长度
        boolean[] visited = new boolean[edges.size() + 1];

        //distance数组初始化，初始化为int最大值表示节点u无法到达节点i
        //注意：节点不是从0而是从1开始
        for (int i = 1; i <= edges.size(); i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        //节点u到节点u的最短路径长度为0
        distance[u] = 0;

        //小根堆，arr[0]：当前节点，arr[i]：节点u到当前节点的路径长度
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] - arr2[1];
            }
        });
        //起始节点u入堆
        priorityQueue.offer(new int[]{u, 0});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //节点u到小根堆中节点的最短路径长度节点v
            int v = arr[0];

            if (visited[v]) {
                continue;
            }

            visited[v] = true;

            //节点u通过节点v作为中间节点更新节点u能够达到其他节点的最短路径长度
            //节点v的邻接节点entry.getKey()和权值entry.getValue()
            for (Map.Entry<Integer, Integer> entry : edges.get(v).entrySet()) {
                if (!visited[entry.getKey()]) {
                    distance[entry.getKey()] = Math.min(distance[entry.getKey()], distance[v] + entry.getValue());
                    priorityQueue.offer(new int[]{entry.getKey(), distance[entry.getKey()]});
                }
            }
        }

        return distance;
    }
}
