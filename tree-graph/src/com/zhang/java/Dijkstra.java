package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2023/2/12 18:22
 * @Author zsy
 * @Description Dijkstra求单元最短路径 图中最短路径类比Problem399、Problem1462、Problem1976
 */
public class Dijkstra {
    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra();
        int[][] edges = {
                {0, 12, -1, -1, -1, 16, 14},
                {12, 0, 10, -1, -1, 7, -1},
                {-1, 10, 0, 3, 5, 6, -1},
                {-1, -1, 3, 0, 4, -1, -1},
                {-1, -1, 5, 4, 0, 2, 8},
                {16, 7, 6, -1, 2, 0, 9},
                {14, -1, -1, -1, 8, 9, 0}
        };
        int u = 3;
        int[] distance = dijkstra.getClosestPath(u, edges);
        System.out.println(Arrays.toString(distance));
    }

    /**
     * Dijkstra求单元最短路径，不适合权值为负的图
     * 求节点u到其他节点的最短路径长度
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param u     起始节点u
     * @param edges 带权图
     * @return
     */
    public int[] getClosestPath(int u, int[][] edges) {
        //节点u到其他节点的最短路径长度数组
        int[] distance = new int[edges.length];
        //节点访问数组，visited[v]为true，表示已经得到节点u到节点v的最短路径长度
        boolean[] visited = new boolean[edges.length];

        //distance数组初始化，初始化为int最大值表示节点u无法到达节点i
        for (int i = 0; i < edges.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        //节点u到节点u的最短路径长度为0
        distance[u] = 0;

        //每次从还未得到节点u到其他节点的最短路径长度节点中选择最短路径长度节点v，
        //节点u通过节点v作为中间节点更新节点u能够达到其他节点的最短路径长度
        for (int i = 0; i < edges.length; i++) {
            //初始化distance数组中本次选择的最短路径长度节点v
            int v = -1;

            //从还未得到节点u到其他节点的最短路径节点中选择最短路径节点v
            for (int j = 0; j < edges.length; j++) {
                if (!visited[j] && (v == -1 || distance[j] < distance[v])) {
                    v = j;
                }
            }

            //节点v已访问，表示已经得到节点u到节点v的最短路径长度
            visited[v] = true;

            //节点u通过节点v作为中间节点更新节点u能够达到其他节点的最短路径长度
            for (int j = 0; j < edges.length; j++) {
                if (!visited[j] && edges[v][j] != -1) {
                    distance[j] = Math.min(distance[j], distance[v] + edges[v][j]);
                }
            }
        }

        return distance;
    }
}
