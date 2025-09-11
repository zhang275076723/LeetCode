package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/10/30 08:58
 * @Author zsy
 * @Description 颜色交替的最短路径 字节面试题 类比Problem785、Problem886 bfs类比Problem407、Problem499、Problem505、Problem778、Problem847、Problem1293、Problem1368、Problem1631、Problem2045、Problem2290
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
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m为图中边的个数，即m=redEdges.length+blueEdges.length)
     *
     * @param n
     * @param redEdges
     * @param blueEdges
     * @return
     */
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        //注意：不能使用一个邻接表，因为两个节点之间可能既存在红边又存在蓝边
        //存储图中红边的邻接表
        List<List<Integer>> redGraph = new ArrayList<>();
        //存储图中蓝边的邻接表
        List<List<Integer>> blueGraph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            redGraph.add(new ArrayList<>());
            blueGraph.add(new ArrayList<>());
        }

        for (int[] arr : redEdges) {
            int u = arr[0];
            int v = arr[1];
            redGraph.get(u).add(v);
        }

        for (int[] arr : blueEdges) {
            int u = arr[0];
            int v = arr[1];
            blueGraph.get(u).add(v);
        }

        //节点0到其他节点的最短路径长度数组，并且遍历到当前节点的最短路径最后一条边的颜色为红色
        int[] distance1 = new int[n];
        //节点0到其他节点的最短路径长度数组，并且遍历到当前节点的最短路径最后一条边的颜色为蓝色
        int[] distance2 = new int[n];
        //节点访问数组，并且遍历到当前节点的最后一条边的颜色为红色
        boolean[] visited1 = new boolean[n];
        //节点访问数组，并且遍历到当前节点的最后一条边的颜色为蓝色
        boolean[] visited2 = new boolean[n];
        //distance1和distance2初始化
        distance1[0] = 0;
        distance2[0] = 0;

        //distance1和distance2初始化，初始化为int最大值表示无法到达节点i
        for (int i = 1; i < n; i++) {
            distance1[i] = Integer.MAX_VALUE;
            distance2[i] = Integer.MAX_VALUE;
        }

        //arr[0]：当前节点，arr[1]：遍历到当前节点路径最后一条边的颜色，1为红色，2为蓝色，
        //arr[2]：节点0到节点arr[0]的路径，最后一条边颜色为arr[1]的最短路径长度
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 1, 0});
        queue.offer(new int[]{0, 2, 0});
        visited1[0] = true;
        visited2[0] = true;

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //当前节点
            int u = arr[0];
            //遍历到当前节点的最短路径最后一条边的颜色
            int lastColor = arr[1];
            //节点0到节点u的路径，最后一条边颜色为lastColor的最短路径长度
            int curDistance = arr[2];

            //最后一条边为红色
            if (lastColor == 1) {
                //交替出现的下一条边为蓝色
                for (int v : blueGraph.get(u)) {
                    if (visited2[v]) {
                        continue;
                    }

                    if (curDistance + 1 < distance2[v]) {
                        distance2[v] = curDistance + 1;
                        queue.offer(new int[]{v, 2, distance2[v]});
                        visited2[v] = true;
                    }
                }
            } else {
                //最后一条边为蓝色

                //交替出现的下一条边为红色
                for (int v : redGraph.get(u)) {
                    if (visited1[v]) {
                        continue;
                    }

                    if (curDistance + 1 < distance1[v]) {
                        distance1[v] = curDistance + 1;
                        queue.offer(new int[]{v, 1, distance1[v]});
                        visited1[v] = true;
                    }
                }
            }
        }

        int[] result = new int[n];

        for (int i = 1; i < n; i++) {
            //当前节点i的最后一条边的颜色为红色或蓝色中的较小值，即为路径中颜色交替出现的最短路径长度
            int distance = Math.min(distance1[i], distance2[i]);
            //当前节点不可达，则赋值为-1
            result[i] = distance == Integer.MAX_VALUE ? -1 : distance;
        }

        return result;
    }
}
