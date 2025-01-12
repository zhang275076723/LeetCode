package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/10/4 09:03
 * @Author zsy
 * @Description 设计可以求最短路径的图类 图中最短路径类比1293
 * 给你一个有 n 个节点的 有向带权 图，节点编号为 0 到 n - 1 。
 * 图中的初始边用数组 edges 表示，其中 edges[i] = [fromi, toi, edgeCosti] 表示从 fromi 到 toi 有一条代价为 edgeCosti 的边。
 * 请你实现一个 Graph 类：
 * Graph(int n, int[][] edges) 初始化图有 n 个节点，并输入初始边。
 * addEdge(int[] edge) 向边集中添加一条边，其中 edge = [from, to, edgeCost] 。
 * 数据保证添加这条边之前对应的两个节点之间没有有向边。
 * int shortestPath(int node1, int node2) 返回从节点 node1 到 node2 的路径 最小 代价。
 * 如果路径不存在，返回 -1 。一条路径的代价是路径中所有边代价之和。
 * <p>
 * 输入：
 * ["Graph", "shortestPath", "shortestPath", "addEdge", "shortestPath"]
 * [[4, [[0, 2, 5], [0, 1, 2], [1, 2, 1], [3, 0, 3]]], [3, 2], [0, 3], [[1, 3, 4]], [0, 3]]
 * 输出：
 * [null, 6, -1, null, 6]
 * 解释：
 * Graph g = new Graph(4, [[0, 2, 5], [0, 1, 2], [1, 2, 1], [3, 0, 3]]);
 * g.shortestPath(3, 2); // 返回 6 。从 3 到 2 的最短路径如第一幅图所示：3 -> 0 -> 1 -> 2 ，总代价为 3 + 2 + 1 = 6 。
 * g.shortestPath(0, 3); // 返回 -1 。没有从 0 到 3 的路径。
 * g.addEdge([1, 3, 4]); // 添加一条节点 1 到节点 3 的边，得到第二幅图。
 * g.shortestPath(0, 3); // 返回 6 。从 0 到 3 的最短路径为 0 -> 1 -> 3 ，总代价为 2 + 4 = 6 。
 * <p>
 * 1 <= n <= 100
 * 0 <= edges.length <= n * (n - 1)
 * edges[i].length == edge.length == 3
 * 0 <= fromi, toi, from, to, node1, node2 <= n - 1
 * 1 <= edgeCosti, edgeCost <= 10^6
 * 图中任何时候都不会有重边和自环。
 * 调用 addEdge 至多 100 次。
 * 调用 shortestPath 至多 100 次。
 */
public class Problem2642 {
    public static void main(String[] args) {
//        int n = 4;
//        int[][] edges = {{0, 2, 5}, {0, 1, 2}, {1, 2, 1}, {3, 0, 3}};
////        Graph g = new Graph(n, edges);
//        Graph2 g = new Graph2(n, edges);
//        Graph3 g = new Graph3(n, edges);
//        // 返回 6 。从 3 到 2 的最短路径如第一幅图所示：3 -> 0 -> 1 -> 2 ，总代价为 3 + 2 + 1 = 6 。
//        System.out.println(g.shortestPath(3, 2));
//        // 返回 -1 。没有从 0 到 3 的路径。
//        System.out.println(g.shortestPath(0, 3));
//        // 添加一条节点 1 到节点 3 的边，得到第二幅图。
//        g.addEdge(new int[]{1, 3, 4});
//        // 返回 6 。从 0 到 3 的最短路径为 0 -> 1 -> 3 ，总代价为 2 + 4 = 6 。
//        System.out.println(g.shortestPath(0, 3));

        int n = 6;
        int[][] edges = {{0, 4, 617630}, {5, 3, 501040}, {3, 4, 654340}, {5, 1, 277928}, {4, 3, 519665}};
        Graph g = new Graph(n, edges);
//        Graph2 g = new Graph2(n, edges);
//        Graph3 g = new Graph3(n, edges);
        System.out.println(g.shortestPath(3, 4));
        System.out.println(g.shortestPath(1, 1));
        System.out.println(g.shortestPath(1, 1));
        g.addEdge(new int[]{4, 2, 36803});
        g.addEdge(new int[]{5, 2, 156440});
        System.out.println(g.shortestPath(2, 2));
        System.out.println(g.shortestPath(3, 3));
        g.addEdge(new int[]{4, 1, 138227});
        g.addEdge(new int[]{4, 5, 1455});
        System.out.println(g.shortestPath(5, 3));
        System.out.println(g.shortestPath(2, 3));
    }

    /**
     * Dijkstra求单元最短路径
     */
    static class Graph {
        //邻接矩阵，有向图
        private final int[][] graph;

        /**
         * 时间复杂度O(n^2)，空间复杂度O(1)
         *
         * @param n
         * @param edges
         */
        public Graph(int n, int[][] edges) {
            graph = new int[n][n];

            //邻接矩阵初始化
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        graph[i][j] = 0;
                    } else {
                        graph[i][j] = -1;
                    }
                }
            }

            for (int i = 0; i < edges.length; i++) {
                graph[edges[i][0]][edges[i][1]] = edges[i][2];
            }
        }

        public void addEdge(int[] edge) {
            graph[edge[0]][edge[1]] = edge[2];
        }

        /**
         * Dijkstra求节点node1到节点node2的最短路径长度
         * 时间复杂度O(n^2)，空间复杂度O(n)
         *
         * @param node1
         * @param node2
         * @return
         */
        public int shortestPath(int node1, int node2) {
            //图中节点的个数
            int n = graph.length;
            //节点node1到其他节点的最短路径长度数组
            int[] distance = new int[n];
            //节点访问数组，visited[node2]为true，表示已经得到节点node1到节点node2的最短路径长度
            boolean[] visited = new boolean[n];
            //图中节点之间的最大距离，表示节点之间不连通，不能初始化为int最大值，避免相加溢出
            int INF = Integer.MAX_VALUE / 2;

            //distance数组初始化，初始化为int最大值表示节点node1无法到达节点i
            for (int i = 0; i < n; i++) {
                distance[i] = INF;
            }

            //初始化，节点node1到节点node2的最短路径长度为0
            distance[node1] = 0;

            //每次从未访问节点中选择距离节点node1最短路径长度的节点u，节点u作为中间节点更新节点node1到其他节点的最短路径长度
            for (int i = 0; i < n; i++) {
                int u = -1;

                //未访问节点中选择距离节点node1最短路径长度的节点u
                for (int j = 0; j < n; j++) {
                    if (!visited[j] && (u == -1 || distance[j] < distance[u])) {
                        u = j;
                    }
                }

                //设置节点u已访问，表示已经得到节点node1到节点u的最短路径长度
                visited[u] = true;

                //已经得到节点node1到节点node2的最短路径长度，则直接返回
                if (u == node2) {
                    //如果不存在node1到node2的路径，则返回-1
                    return distance[node2] == INF ? -1 : distance[node2];
                }

                //节点u作为中间节点更新节点node1到其他节点的最短路径长度
                for (int j = 0; j < n; j++) {
                    if (!visited[j] && graph[u][j] != -1) {
                        distance[j] = Math.min(distance[j], distance[u] + graph[u][j]);
                    }
                }
            }

            //如果不存在node1到node2的路径，则返回-1
            return distance[node2] == INF ? -1 : distance[node2];
        }
    }

    /**
     * 堆优化Dijkstra求单元最短路径
     */
    static class Graph2 {
        //邻接矩阵，有向图
        private final int[][] graph;

        /**
         * 时间复杂度O(n^2)，空间复杂度O(1)
         *
         * @param n
         * @param edges
         */
        public Graph2(int n, int[][] edges) {
            graph = new int[n][n];

            //邻接矩阵初始化
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        graph[i][j] = 0;
                    } else {
                        graph[i][j] = -1;
                    }
                }
            }

            for (int i = 0; i < edges.length; i++) {
                graph[edges[i][0]][edges[i][1]] = edges[i][2];
            }
        }

        public void addEdge(int[] edge) {
            graph[edge[0]][edge[1]] = edge[2];
        }

        /**
         * 堆优化Dijkstra求节点node1到节点node2的最短路径长度
         * 时间复杂度O(mlogm)，空间复杂度O(n) (n为图中节点的个数，m为图中边的个数)
         *
         * @param node1
         * @param node2
         * @return
         */
        public int shortestPath(int node1, int node2) {
            //图中节点的个数
            int n = graph.length;
            //节点node1到其他节点的最短路径长度数组
            int[] distance = new int[n];

            //distance数组初始化，初始化为int最大值表示节点node1无法到达节点i
            for (int i = 0; i < n; i++) {
                distance[i] = Integer.MAX_VALUE;
            }

            //初始化，节点node1到节点node2的最短路径长度为0
            distance[node1] = 0;

            //小根堆，arr[0]：当前节点，arr[1]：节点node1到当前节点的路径长度，注意：当前路径长度不一定是最短路径长度
            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] arr1, int[] arr2) {
                    return arr1[1] - arr2[1];
                }
            });

            //起始节点node1入堆
            priorityQueue.offer(new int[]{node1, distance[node1]});

            while (!priorityQueue.isEmpty()) {
                int[] arr = priorityQueue.poll();
                //当前节点u
                int u = arr[0];
                //节点node1到节点u的路径长度，注意：当前路径长度不一定是最短路径长度
                int curDistance = arr[1];

                //curDistance大于distance[u]，则当前节点u不能作为中间节点更新节点node1到其他节点的最短路径长度，直接进行下次循环
                if (curDistance > distance[u]) {
                    continue;
                }

                //已经得到节点node1到节点node2的最短路径长度，则直接返回
                if (u == node2) {
                    //如果不存在node1到node2的路径，则返回-1
                    return distance[node2] == Integer.MAX_VALUE ? -1 : distance[node2];
                }

                //节点u作为中间节点更新节点node1到其他节点的最短路径长度
                for (int i = 0; i < n; i++) {
                    if (graph[u][i] != -1 && curDistance + graph[u][i] < distance[i]) {
                        distance[i] = curDistance + graph[u][i];
                        priorityQueue.offer(new int[]{i, distance[i]});
                    }
                }
            }

            //如果不存在node1到node2的路径，则返回-1
            return distance[node2] == Integer.MAX_VALUE ? -1 : distance[node2];
        }
    }

    /**
     * Floyd，计算任意两个节点之间的最短路径
     */
    static class Graph3 {
        //节点u到节点v的最短路径数组
        private final int[][] distance;

        /**
         * 时间复杂度O(n^3)，空间复杂度O(1)
         *
         * @param n
         * @param edges
         */
        public Graph3(int n, int[][] edges) {
            distance = new int[n][n];

            //distance初始化
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        distance[i][j] = 0;
                    } else {
                        //注意：只能初始化不存在边的两个节点的距离为int最大值，不能初始化为-1，因为floyd可以求带负权值的边
                        distance[i][j] = Integer.MAX_VALUE;
                    }
                }
            }

            for (int i = 0; i < edges.length; i++) {
                distance[edges[i][0]][edges[i][1]] = edges[i][2];
            }

            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        //节点i到节点k和节点k到节点j都存在路径时，才计算节点i到节点j的最短路径长度
                        if (distance[i][k] != Integer.MAX_VALUE && distance[k][j] != Integer.MAX_VALUE) {
                            distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                        }
                    }
                }
            }
        }

        /**
         * 时间复杂度O(n^2)，空间复杂度O(1)
         *
         * @param edge
         */
        public void addEdge(int[] edge) {
            //未添加edge[0]到edge[1]的边时，从edge[0]到edge[1]的最短路径长度小于等于edge[0]到edge[1]的边的权值，
            //则添加edge[0]到edge[1]的边无效，直接返回
            if (distance[edge[0]][edge[1]] <= edge[2]) {
                return;
            }

            //图中节点的个数
            int n = distance.length;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    //通过edge[0]到edge[1]的边，更新distance[i][j]
                    if (distance[i][edge[0]] != Integer.MAX_VALUE && distance[edge[1]][j] != Integer.MAX_VALUE) {
                        distance[i][j] = Math.min(distance[i][j], distance[i][edge[0]] + edge[2] + distance[edge[1]][j]);
                    }
                }
            }
        }

        public int shortestPath(int node1, int node2) {
            //如果不存在node1到node2的路径，则返回-1
            return distance[node1][node2] == Integer.MAX_VALUE ? -1 : distance[node1][node2];
        }
    }
}
