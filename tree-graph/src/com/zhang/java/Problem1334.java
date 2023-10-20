package com.zhang.java;


/**
 * @Date 2023/10/28 08:12
 * @Author zsy
 * @Description 阈值距离内邻居最少的城市 图中最短路径类比Problem399、Problem1462、Problem1786、Problem1976、Dijkstra 图类比
 * 有 n 个城市，按从 0 到 n-1 编号。
 * 给你一个边数组 edges，其中 edges[i] = [fromi, toi, weighti] 代表 fromi 和 toi 两个城市之间的双向加权边，
 * 距离阈值是一个整数 distanceThreshold。
 * 返回能通过某些路径到达其他城市数目最少、且路径距离 最大 为 distanceThreshold 的城市。
 * 如果有多个这样的城市，则返回编号最大的城市。
 * 注意，连接城市 i 和 j 的路径的距离等于沿该路径的所有边的权重之和。
 * <p>
 * 输入：n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
 * 输出：3
 * 解释：城市分布图如上。
 * 每个城市阈值距离 distanceThreshold = 4 内的邻居城市分别是：
 * 城市 0 -> [城市 1, 城市 2]
 * 城市 1 -> [城市 0, 城市 2, 城市 3]
 * 城市 2 -> [城市 0, 城市 1, 城市 3]
 * 城市 3 -> [城市 1, 城市 2]
 * 城市 0 和 3 在阈值距离 4 以内都有 2 个邻居城市，但是我们必须返回城市 3，因为它的编号最大。
 * <p>
 * 输入：n = 5, edges = [[0,1,2],[0,4,8],[1,2,3],[1,4,2],[2,3,1],[3,4,1]], distanceThreshold = 2
 * 输出：0
 * 解释：城市分布图如上。
 * 每个城市阈值距离 distanceThreshold = 2 内的邻居城市分别是：
 * 城市 0 -> [城市 1]
 * 城市 1 -> [城市 0, 城市 4]
 * 城市 2 -> [城市 3, 城市 4]
 * 城市 3 -> [城市 2, 城市 4]
 * 城市 4 -> [城市 1, 城市 2, 城市 3]
 * 城市 0 在阈值距离 2 以内只有 1 个邻居城市。
 * <p>
 * 2 <= n <= 100
 * 1 <= edges.length <= n * (n - 1) / 2
 * edges[i].length == 3
 * 0 <= fromi < toi < n
 * 1 <= weighti, distanceThreshold <= 10^4
 * 所有 (fromi, toi) 都是不同的。
 */
public class Problem1334 {
    public static void main(String[] args) {
        Problem1334 problem1334 = new Problem1334();
        int n = 4;
        int[][] edges = {{0, 1, 3}, {1, 2, 1}, {1, 3, 4}, {2, 3, 1}};
        int distanceThreshold = 4;
        System.out.println(problem1334.findTheCity(n, edges, distanceThreshold));
    }

    /**
     * Floyd，计算任意两个节点之间的最短路径
     * 注意：Floyd可以处理带负权值的图，而Dijkstra不能处理带负权值的图
     * 时间复杂度O(n^3)，空间复杂度O(n^2)
     *
     * @param n
     * @param edges
     * @param distanceThreshold
     * @return
     */
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        //Floyd数组，distance[i][j]：节点i到节点j的最短路径长度，int最大值即不可达
        int[][] distance = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distance[i][j] = 0;
                } else {
                    //注意：只能初始化不能到达的两个节点的距离为int最大值，不能初始化为-1，因为floyd是通过min求最短路径的
                    distance[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int weight = edges[i][2];
            distance[u][v] = weight;
            distance[v][u] = weight;
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    //节点i到节点k和节点k到节点j存在路径时，才计算节点i到节点j的最短路径长度
                    if (distance[i][k] != Integer.MAX_VALUE && distance[k][j] != Integer.MAX_VALUE) {
                        distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                    }
                }
            }
        }

        //到达其他节点的个数最少，并且路径长度不超过distanceThreshold的节点
        int result = -1;
        //节点result到达其他节点的路径长度不超过distanceThreshold的个数
        int minCount = Integer.MAX_VALUE;

        //从前往后遍历保证如果存在多个答案，优先返回编号较大的节点
        for (int i = 0; i < n; i++) {
            //节点i到达其他节点的路径长度不超过distanceThreshold的个数
            int count = 0;

            for (int j = 0; j < n; j++) {
                if (i != j && distance[i][j] <= distanceThreshold) {
                    count++;
                }
            }

            //更新result和minCount
            if (count <= minCount) {
                result = i;
                minCount = count;
            }
        }

        return result;
    }
}
