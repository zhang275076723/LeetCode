package com.zhang.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/10/30 08:58
 * @Author zsy
 * @Description 颜色交替的最短路径 字节面试题 bfs类比Problem407、Problem499、Problem505、Problem847、Problem1293、Problem1368、Problem1631、Problem2045、Problem2290
 * 给定一个整数 n，即有向图中的节点数，其中节点标记为 0 到 n - 1。
 * 图中的每条边为红色或者蓝色，并且可能存在自环或平行边。
 * 给定两个数组 redEdges 和 blueEdges，其中：
 * redEdges[i] = [ai, bi] 表示图中存在一条从节点 ai 到节点 bi 的红色有向边，
 * blueEdges[j] = [uj, vj] 表示图中存在一条从节点 uj 到节点 vj 的蓝色有向边。
 * 返回长度为 n 的数组 answer，其中 answer[X] 是从节点 0 到节点 X 的红色边和蓝色边交替出现的最短路径的长度。
 * 如果不存在这样的路径，那么 answer[x] = -1。
 * <p>
 * 输入：n = 3, red_edges = [[0,1],[1,2]], blue_edges = []
 * 输出：[0,1,-1]
 * <p>
 * 输入：n = 3, red_edges = [[0,1]], blue_edges = [[2,1]]
 * 输出：[0,1,-1]
 * <p>
 * 1 <= n <= 100
 * 0 <= redEdges.length, blueEdges.length <= 400
 * redEdges[i].length == blueEdges[j].length == 2
 * 0 <= ai, bi, uj, vj < n
 */
public class Problem1129 {
    public static void main(String[] args) {
        Problem1129 problem1129 = new Problem1129();
        int n = 5;
        int[][] redEdges = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        int[][] blueEdges = {{1, 2}, {2, 3}, {3, 1}};
        System.out.println(Arrays.toString(problem1129.shortestAlternatingPaths(n, redEdges, blueEdges)));
    }

    /**
     * bfs
     * 时间复杂度O(m+n)，空间复杂度O(n^2) (m为图中边的个数，即m=redEdges.length+blueEdges.length，如果使用邻接表，空间复杂度O(m+n))
     *
     * @param n
     * @param redEdges
     * @param blueEdges
     * @return
     */
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        //邻接矩阵
        //注意：不能使用一个邻接矩阵，因为两个节点之间可能既存在红边又存在蓝边，如果使用一个邻接矩阵，前面的颜色会被后面的颜色覆盖
        //edges[i][j]=0：节点i到节点j不存在边
        //edges[i][j]=1：节点i到节点j存在一条红边
        //edges[i][j]=2：节点i到节点j存在一条蓝边
        int[][] edges1 = new int[n][n];
        int[][] edges2 = new int[n][n];

        for (int i = 0; i < redEdges.length; i++) {
            int u = redEdges[i][0];
            int v = redEdges[i][1];
            edges1[u][v] = 1;
        }

        for (int i = 0; i < blueEdges.length; i++) {
            int u = blueEdges[i][0];
            int v = blueEdges[i][1];
            edges2[u][v] = 2;
        }

        //节点0到其他节点的最短路径长度数组，并且遍历到当前节点的最短路径最后一条边的颜色为红色
        int[] distance1 = new int[n];
        //节点0到其他节点的最短路径长度数组，并且遍历到当前节点的最短路径最后一条边的颜色为蓝色
        int[] distance2 = new int[n];

        //distance1和distance2初始化，初始化为int最大值表示无法到达节点i
        for (int i = 0; i < n; i++) {
            distance1[i] = Integer.MAX_VALUE;
            distance2[i] = Integer.MAX_VALUE;
        }

        //arr[0]：当前节点，arr[1]：遍历到当前节点路径最后一条边的颜色，1为红色，2为蓝色
        Queue<int[]> queue = new LinkedList<>();
        //节点访问数组，并且遍历到当前节点的最后一条边的颜色为红色
        boolean[] visited1 = new boolean[n];
        //节点访问数组，并且遍历到当前节点的最后一条边的颜色为蓝色
        boolean[] visited2 = new boolean[n];

        queue.offer(new int[]{0, 1});
        queue.offer(new int[]{0, 2});
        visited1[0] = true;
        visited2[0] = true;

        int distance = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();
                //当前节点
                int u = arr[0];
                //遍历到当前节点的最短路径最后一条边的颜色
                int lastColor = arr[1];

                //最后一条边为红色
                if (lastColor == 1) {
                    distance1[u] = distance;

                    //交替出现的下一条边为蓝色
                    for (int v = 0; v < n; v++) {
                        if (edges2[u][v] == 2 && !visited2[v]) {
                            queue.offer(new int[]{v, 2});
                            visited2[v] = true;
                        }
                    }
                } else {
                    //最后一条边为蓝色

                    distance2[u] = distance;

                    //交替出现的下一条边为红色
                    for (int v = 0; v < n; v++) {
                        if (edges1[u][v] == 1 && !visited1[v]) {
                            queue.offer(new int[]{v, 1});
                            visited1[v] = true;
                        }
                    }
                }
            }

            distance++;
        }

        int[] result = new int[n];

        //遍历到当前节点i的最后一条边的颜色为红色或蓝色中的较小值，即为路径中颜色交替出现的最短路径长度
        for (int i = 0; i < n; i++) {
            result[i] = Math.min(distance1[i], distance2[i]);

            //当前节点不可达，赋值为-1
            if (result[i] == Integer.MAX_VALUE) {
                result[i] = -1;
            }
        }

        return result;
    }
}
