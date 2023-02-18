package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2023/2/12 18:22
 * @Author zsy
 * @Description Dijkstra求单元最短路径 图求最短路径类比Problem399
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
        int[] path = dijkstra.getClosestPath(edges, u);
        System.out.println(Arrays.toString(path));
    }

    /**
     * Dijkstra用于求单元最短路径，不适合权值为负的图
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param edges 带权图
     * @param u     起始节点u
     * @return 节点u到其他节点的最短路径数组
     */
    public int[] getClosestPath(int[][] edges, int u) {
        //节点u到其他节点的最短路径数组
        int[] path = new int[edges.length];
        //节点访问数组
        boolean[] visited = new boolean[edges.length];

        //节点u直接能够到达的节点加入path，不能到达的节点赋值int最大值
        for (int i = 0; i < edges.length; i++) {
            if (edges[u][i] != -1) {
                path[i] = edges[u][i];
            } else {
                path[i] = Integer.MAX_VALUE;
            }
        }

        //u节点已经访问
        visited[u] = true;

        //每次选择path数组中未访问的最短路径节点v，通过节点v更新节点u能够达到的其他节点最短路径
        for (int i = 0; i < edges.length - 1; i++) {
            //path数组中未访问的最短路径节点v
            int v = -1;
            int minPath = Integer.MAX_VALUE;

            //找path数组中未访问的最短路径节点v
            for (int j = 0; j < path.length; j++) {
                if (!visited[j] && path[j] < minPath) {
                    v = j;
                    minPath = path[j];
                }
            }

            //v节点已经访问
            visited[v] = true;

            //通过节点v更新节点u能够达到的其他节点最短路径
            for (int j = 0; j < edges.length; j++) {
                if (!visited[j] && edges[v][j] != -1) {
                    path[j] = Math.min(path[j], path[v] + edges[v][j]);
                }
            }
        }

        return path;
    }
}
