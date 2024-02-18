package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/1 08:54
 * @Author zsy
 * @Description 重新规划路线 图类比
 * n 座城市，从 0 到 n-1 编号，其间共有 n-1 条路线。
 * 因此，要想在两座不同城市之间旅行只有唯一一条路线可供选择（路线网形成一颗树）。
 * 去年，交通运输部决定重新规划路线，以改变交通拥堵的状况。
 * 路线用 connections 表示，其中 connections[i] = [a, b] 表示从城市 a 到 b 的一条有向路线。
 * 今年，城市 0 将会举办一场大型比赛，很多游客都想前往城市 0 。
 * 请你帮助重新规划路线方向，使每个城市都可以访问城市 0 。
 * 返回需要变更方向的最小路线数。
 * 题目数据 保证 每个城市在重新规划路线方向后都能到达城市 0 。
 * <p>
 * 输入：n = 6, connections = [[0,1],[1,3],[2,3],[4,0],[4,5]]
 * 输出：3
 * 解释：更改以红色显示的路线的方向，使每个城市都可以到达城市 0 。
 * <p>
 * 输入：n = 5, connections = [[1,0],[1,2],[3,2],[3,4]]
 * 输出：2
 * 解释：更改以红色显示的路线的方向，使每个城市都可以到达城市 0 。
 * <p>
 * 输入：n = 3, connections = [[1,0],[2,0]]
 * 输出：0
 * <p>
 * 2 <= n <= 5 * 10^4
 * connections.length == n-1
 * connections[i].length == 2
 * 0 <= connections[i][0], connections[i][1] <= n-1
 * connections[i][0] != connections[i][1]
 */
public class Problem1466 {
    public static void main(String[] args) {
        Problem1466 problem1466 = new Problem1466();
        int n = 6;
        int[][] connections = {{0, 1}, {1, 3}, {2, 3}, {4, 0}, {4, 5}};
        System.out.println(problem1466.minReorder(n, connections));
        System.out.println(problem1466.minReorder2(n, connections));
    }

    /**
     * dfs
     * 核心思想：有向图当成无向图dfs，当前节点u相连的有向边存在节点u到节点v的边，则当前有向边需要修改方向
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param connections
     * @return
     */
    public int minReorder(int n, int[][] connections) {
        //存储节点相连的有向边集合，arr[0]：有向边起点，arr[1]：有向边终点
        List<List<int[]>> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            list.add(new ArrayList<>());
        }

        for (int i = 0; i < connections.length; i++) {
            int u = connections[i][0];
            int v = connections[i][1];

            list.get(u).add(new int[]{u, v});
            list.get(v).add(new int[]{u, v});
        }

        //有向图当成无向图，从节点0开始dfs
        return dfs(0, new boolean[n], list);
    }

    /**
     * bfs
     * 核心思想：有向图当成无向图bfs，当前节点u相连的有向边存在节点u到节点v的边，则当前有向边需要修改方向
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param connections
     * @return
     */
    public int minReorder2(int n, int[][] connections) {
        //存储节点相连的有向边集合，arr[0]：有向边起点，arr[1]：有向边终点
        List<List<int[]>> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            list.add(new ArrayList<>());
        }

        for (int i = 0; i < connections.length; i++) {
            int u = connections[i][0];
            int v = connections[i][1];

            list.get(u).add(new int[]{u, v});
            list.get(v).add(new int[]{u, v});
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        queue.offer(0);

        //从节点0开始bfs，图中有向边需要修改方向的最小个数
        int count = 0;

        while (!queue.isEmpty()) {
            //当前节点u
            int u = queue.poll();

            //遍历当前节点u相连的有向边
            for (int[] arr : list.get(u)) {
                //有向边的起始节点
                int start = arr[0];
                //有向边的终止节点
                int end = arr[1];

                //存在start到end的边中节点u为start，则当前有向边需要修改方向
                if (start == u) {
                    if (!visited[end]) {
                        count++;
                        visited[end] = true;
                        queue.offer(end);
                    }
                } else {
                    //存在start到end的边中节点u为end，则当前有向边不需要修改方向
                    if (!visited[start]) {
                        visited[start] = true;
                        queue.offer(start);
                    }
                }
            }
        }

        return count;
    }

    private int dfs(int u, boolean[] visited, List<List<int[]>> list) {
        //从节点u开始dfs，有向边需要修改方向的最小个数
        int count = 0;

        //遍历当前节点u相连的有向边
        for (int[] arr : list.get(u)) {
            //有向边的起始节点
            int start = arr[0];
            //有向边的终止节点
            int end = arr[1];

            //存在start到end的边中节点u为start，则当前有向边需要修改方向
            if (u == start) {
                if (!visited[end]) {
                    visited[end] = true;
                    count = count + 1 + dfs(end, visited, list);
                }
            } else {
                //存在start到end的边中节点u为end，则当前有向边不需要修改方向
                if (!visited[start]) {
                    visited[start] = true;
                    count = count + dfs(start, visited, list);
                }
            }
        }

        return count;
    }
}
