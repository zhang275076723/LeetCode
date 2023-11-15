package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Date 2023/11/19 08:37
 * @Author zsy
 * @Description 细分图中的可到达节点 图中最短路径类比Problem399、Problem743、Problem787、Problem1334、Problem1368、Problem1462、Problem1514、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2662、Dijkstra
 * 给你一个无向图（原始图），图中有 n 个节点，编号从 0 到 n - 1 。
 * 你决定将图中的每条边 细分 为一条节点链，每条边之间的新节点数各不相同。
 * 图用由边组成的二维数组 edges 表示，其中 edges[i] = [ui, vi, cnti] 表示原始图中节点 ui 和 vi 之间存在一条边，cnti 是将边 细分 后的新节点总数。
 * 注意，cnti == 0 表示边不可细分。
 * 要 细分 边 [ui, vi] ，需要将其替换为 (cnti + 1) 条新边，和 cnti 个新节点。
 * 新节点为 x1, x2, ..., xcnti ，新边为 [ui, x1], [x1, x2], [x2, x3], ..., [xcnti-1, xcnti], [xcnti, vi] 。
 * 现在得到一个 新的细分图 ，请你计算从节点 0 出发，可以到达多少个节点？
 * 如果节点间距离是 maxMoves 或更少，则视为 可以到达 。
 * 给你原始图和 maxMoves ，返回 新的细分图中从节点 0 出发 可到达的节点数 。
 * <p>
 * 输入：edges = [[0,1,10],[0,2,1],[1,2,2]], maxMoves = 6, n = 3
 * 输出：13
 * 解释：边的细分情况如上图所示。
 * 可以到达的节点已经用黄色标注出来。
 * <p>
 * 输入：edges = [[0,1,4],[1,2,6],[0,2,8],[1,3,1]], maxMoves = 10, n = 4
 * 输出：23
 * <p>
 * 输入：edges = [[1,2,4],[1,4,5],[1,3,1],[2,3,4],[3,4,5]], maxMoves = 17, n = 5
 * 输出：1
 * 解释：节点 0 与图的其余部分没有连通，所以只有节点 0 可以到达。
 * <p>
 * 0 <= edges.length <= min(n * (n - 1) / 2, 10^4)
 * edges[i].length == 3
 * 0 <= ui < vi < n
 * 图中 不存在平行边
 * 0 <= cnti <= 10^4
 * 0 <= maxMoves <= 10^9
 * 1 <= n <= 3000
 */
public class Problem882 {
    public static void main(String[] args) {
        Problem882 problem882 = new Problem882();
//        int[][] edges = {{0, 1, 10}, {0, 2, 1}, {1, 2, 2}};
//        int maxMoves = 6;
//        int n = 3;
        int[][] edges = {{1, 2, 4}, {1, 4, 5}, {1, 3, 1}, {2, 3, 4}, {3, 4, 5}};
        int maxMoves = 17;
        int n = 5;
        System.out.println(problem882.reachableNodes(edges, maxMoves, n));
        System.out.println(problem882.reachableNodes2(edges, maxMoves, n));
    }

    /**
     * Dijkstra求细分前节点0到其他节点的最短路径长度
     * 图中节点分为细分前的节点和细分后的节点，细分前的节点判断完是否可达之后，再判断两个细分前的节点之间的细分后的节点是否可达
     * 细分前两节点边edges[i]的权值为edges[i][2]+1，Dijkstra求细分前节点0到其他节点的最短路径长度，
     * 细分前节点0到当前节点u的最短路径长度distance[u]小于等于maxMoves，则细分前节点u可以到达；
     * 细分前节点u和节点v，在细分后节点u和节点v之间需要添加edges[i][2]个节点，
     * 节点0到节点u往节点v走，最多还能经过fromU2V=max(0,maxMoves-distance[u])个节点，
     * 节点0到节点v往节点u走，最多还能经过fromV2U=max(0,maxMoves-distance[v])个节点，
     * 则细分后节点u和节点v之间能够到达min(fromU2V+fromV2U,edges[i][2])个节点
     * 时间复杂度O(n^2+m)，空间复杂度O(m+n) (n为图中节点的个数，m为图中边的个数，m=edges.length)
     *
     * @param edges
     * @param maxMoves
     * @param n
     * @return
     */
    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        //邻接表，无向图，arr[0]：当前节点的邻接节点，arr[1]：当前节点和邻接节点边的权值，即当前节点到邻接节点经过的节点个数
        List<List<int[]>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            //细分后节点u和节点v之间需要添加的节点个数
            int count = edges[i][2];

            //节点u和节点v边的权值为count+1
            graph.get(u).add(new int[]{v, count + 1});
            graph.get(v).add(new int[]{u, count + 1});
        }

        //细分前节点0到其他节点的最短路径长度数组
        int[] distance = new int[n];
        //细分前节点访问数组，visited[u]为true，表示已经得到细分前节点0到节点u的最短路径长度
        boolean[] visited = new boolean[n];
        //图中节点的最大距离，不能初始化为int最大值，避免相加溢出
        int INF = Integer.MAX_VALUE / 2;

        //distance数组初始化，初始化为INF表示细分前节点0无法到达节点i
        for (int i = 0; i < n; i++) {
            distance[i] = INF;
        }

        //初始化，细分前节点0到节点0的最短路径长度为0
        distance[0] = 0;

        //每次从未访问节点中选择距离节点0最短路径长度的节点u，节点u作为中间节点更新节点0到其他节点的最短路径长度
        for (int i = 0; i < n; i++) {
            //初始化distance数组中未访问节点中选择距离节点0最短路径长度的节点u
            int u = -1;

            //未访问节点中选择距离节点0最短路径长度的节点u
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || distance[j] < distance[u])) {
                    u = j;
                }
            }

            //设置节点u已访问，表示已经得到节点0到节点u的最短路径长度
            visited[u] = true;

            //遍历节点u的邻接节点v，节点u作为中间节点更新节点0到其他节点的最短路径长度
            for (int[] arr : graph.get(u)) {
                //节点u的邻接节点v
                int v = arr[0];
                //节点u和邻接节点v边的权值，即节点u到邻接节点v经过的节点个数
                int weight = arr[1];

                if (!visited[v]) {
                    distance[v] = Math.min(distance[v], distance[u] + weight);
                }
            }
        }

        //新的细分图中从节点0出发可到达的节点数
        int result = 0;

        for (int i = 0; i < n; i++) {
            //细分前节点0到当前节点i的最短路径长度distance[i]小于等于maxMoves，则细分前节点i可以到达
            if (distance[i] <= maxMoves) {
                result++;
            }
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            //细分后节点u和节点v之间需要添加的节点个数
            int count = edges[i][2];

            //节点0到节点u往节点v走，最多还能经过的节点个数为max(0,maxMoves-distance[u])
            int fromU2V = Math.max(0, maxMoves - distance[u]);
            //节点0到节点v往节点u走，最多还能经过的节点个数为max(0,maxMoves-distance[v])
            int fromV2U = Math.max(0, maxMoves - distance[v]);
            //细分后节点u和节点v之间能够到达min(fromU2V+fromV2U,edges[i][2])个节点
            result = result + Math.min(fromU2V + fromV2U, count);
        }

        return result;
    }

    /**
     * 堆优化Dijkstra求细分前节点0到其他节点的最短路径长度
     * 图中节点分为细分前的节点和细分后的节点，细分前的节点判断完是否可达之后，再判断两个细分前的节点之间的细分后的节点是否可达
     * 细分前两节点边edges[i]的权值为edges[i][2]+1，Dijkstra求细分前节点0到其他节点的最短路径长度，
     * 细分前节点0到当前节点u的最短路径长度distance[u]小于等于maxMoves，则细分前节点u可以到达；
     * 细分前节点u和节点v，在细分后节点u和节点v之间需要添加edges[i][2]个节点，
     * 节点0到节点u往节点v走，最多还能经过fromU2V=max(0,maxMoves-distance[u])个节点，
     * 节点0到节点v往节点u走，最多还能经过fromV2U=max(0,maxMoves-distance[v])个节点，
     * 则细分后节点u和节点v之间能够到达min(fromU2V+fromV2U,edges[i][2])个节点
     * 时间复杂度O(mlogm+n)，空间复杂度O(m+n) (n为图中节点的个数，m为图中边的个数，m=edges.length)
     *
     * @param edges
     * @param maxMoves
     * @param n
     * @return
     */
    public int reachableNodes2(int[][] edges, int maxMoves, int n) {
        //邻接表，无向图，arr[0]：当前节点的邻接节点，arr[1]：当前节点和邻接节点边的权值，即当前节点到邻接节点经过的节点个数
        List<List<int[]>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            //细分后节点u和节点v之间需要添加的节点个数
            int count = edges[i][2];

            //节点u和节点v边的权值为count+1
            graph.get(u).add(new int[]{v, count + 1});
            graph.get(v).add(new int[]{u, count + 1});
        }

        //细分前节点0到其他节点的最短路径长度数组
        int[] distance = new int[n];

        //distance数组初始化，初始化为int最大值表示细分前节点0无法到达节点i
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        //初始化，细分前节点0到节点0的最短路径长度为0
        distance[0] = 0;

        //小根堆，arr[0]：当前节点，arr[1]：节点0到当前节点的路径长度
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] - arr2[1];
            }
        });

        //节点0入堆
        priorityQueue.offer(new int[]{0, 0});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点u
            int u = arr[0];
            //节点0到节点u的路径长度
            int curDistance = arr[1];

            //curDistance大于distance[v]，则当前节点u不能作为中间节点更新节点0到其他节点的最短路径长度，直接进行下次循环
            if (curDistance > distance[u]) {
                continue;
            }

            //遍历节点u的邻接节点v
            for (int[] arr2 : graph.get(u)) {
                //节点u的邻接节点v
                int v = arr2[0];
                //节点u和邻接节点v边的权值，即节点u到邻接节点v经过的节点个数
                int weight = arr2[1];

                //节点u作为中间节点更新节点0到其他节点的最短路径长度，更新distance[v]，节点v入堆
                if (curDistance + weight < distance[v]) {
                    distance[v] = curDistance + weight;
                    priorityQueue.offer(new int[]{v, distance[v]});
                }
            }
        }

        //新的细分图中从节点0出发可到达的节点数
        int result = 0;

        for (int i = 0; i < n; i++) {
            //细分前节点0到当前节点i的最短路径长度distance[i]小于等于maxMoves，则细分前节点i可以到达
            if (distance[i] <= maxMoves) {
                result++;
            }
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            //细分后节点u和节点v之间需要添加的节点个数
            int count = edges[i][2];

            //节点0到节点u往节点v走，最多还能经过的节点个数为max(0,maxMoves-distance[u])
            int fromU2V = Math.max(0, maxMoves - distance[u]);
            //节点0到节点v往节点u走，最多还能经过的节点个数为max(0,maxMoves-distance[v])
            int fromV2U = Math.max(0, maxMoves - distance[v]);
            //细分后节点u和节点v之间能够到达min(fromU2V+fromV2U,edges[i][2])个节点
            result = result + Math.min(fromU2V + fromV2U, count);
        }

        return result;
    }
}
