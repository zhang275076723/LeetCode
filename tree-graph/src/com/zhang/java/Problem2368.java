package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/11/17 08:28
 * @Author zsy
 * @Description 受限条件下可到达节点的数目 类比Problem2277
 * 现有一棵由 n 个节点组成的无向树，节点编号从 0 到 n - 1 ，共有 n - 1 条边。
 * 给你一个二维整数数组 edges ，长度为 n - 1 ，其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间存在一条边。
 * 另给你一个整数数组 restricted 表示 受限 节点。
 * 在不访问受限节点的前提下，返回你可以从节点 0 到达的 最多 节点数目。
 * 注意，节点 0 不 会标记为受限节点。
 * <p>
 * 输入：n = 7, edges = [[0,1],[1,2],[3,1],[4,0],[0,5],[5,6]], restricted = [4,5]
 * 输出：4
 * 解释：上图所示正是这棵树。
 * 在不访问受限节点的前提下，只有节点 [0,1,2,3] 可以从节点 0 到达。
 * <p>
 * 输入：n = 7, edges = [[0,1],[0,2],[0,5],[0,4],[3,2],[6,5]], restricted = [4,2,1]
 * 输出：3
 * 解释：上图所示正是这棵树。
 * 在不访问受限节点的前提下，只有节点 [0,5,6] 可以从节点 0 到达。
 * <p>
 * 2 <= n <= 10^5
 * edges.length == n - 1
 * edges[i].length == 2
 * 0 <= ai, bi < n
 * ai != bi
 * edges 表示一棵有效的树
 * 1 <= restricted.length < n
 * 1 <= restricted[i] < n
 * restricted 中的所有值 互不相同
 */
public class Problem2368 {
    public static void main(String[] args) {
        Problem2368 problem2368 = new Problem2368();
        int n = 7;
        int[][] edges = {{0, 1}, {1, 2}, {3, 1}, {4, 0}, {0, 5}, {5, 6}};
        int[] restricted = {4, 5};
        System.out.println(problem2368.reachableNodes(n, edges, restricted));
        System.out.println(problem2368.reachableNodes2(n, edges, restricted));
    }

    /**
     * 图的dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @param restricted
     * @return
     */
    public int reachableNodes(int n, int[][] edges, int[] restricted) {
        //邻接表
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        //受限节点集合
        Set<Integer> restrictedSet = new HashSet<>();

        for (int num : restricted) {
            restrictedSet.add(num);
        }

        return dfs(0, graph, restrictedSet, new boolean[n]);
    }

    /**
     * 图的bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @param restricted
     * @return
     */
    public int reachableNodes2(int n, int[][] edges, int[] restricted) {
        //邻接表
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        //受限节点集合
        Set<Integer> restrictedSet = new HashSet<>();

        for (int num : restricted) {
            restrictedSet.add(num);
        }

        int count = 0;
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        queue.offer(0);

        while (!queue.isEmpty()) {
            int u = queue.poll();

            if (visited[u] || restrictedSet.contains(u)) {
                continue;
            }

            count++;
            visited[u] = true;

            for (int v : graph.get(u)) {
                queue.offer(v);
            }
        }

        return count;
    }

    private int dfs(int u, List<List<Integer>> graph, Set<Integer> restrictedSet, boolean[] visited) {
        if (visited[u] || restrictedSet.contains(u)) {
            return 0;
        }

        int count = 1;
        visited[u] = true;

        for (int v : graph.get(u)) {
            count = count + dfs(v, graph, restrictedSet, visited);
        }

        return count;
    }
}
