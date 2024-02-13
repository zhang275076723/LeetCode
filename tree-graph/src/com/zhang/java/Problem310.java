package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2023/10/10 08:49
 * @Author zsy
 * @Description 最小高度树 快手面试题 类比Problem1245 保存父节点类比Problem113、Problem126、Problem272、Problem863、Offer34 拓扑排序类比Problem207、Problem210、Problem329、IsCircleDependency
 * 树是一个无向图，其中任何两个顶点只通过一条路径连接。
 * 换句话说，一个任何没有简单环路的连通图都是一棵树。
 * 给你一棵包含 n 个节点的树，标记为 0 到 n - 1 。
 * 给定数字 n 和一个有 n - 1 条无向边的 edges 列表（每一个边都是一对标签），
 * 其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间存在一条无向边。
 * 可选择树中任何一个节点作为根。当选择节点 x 作为根节点时，设结果树的高度为 h 。
 * 在所有可能的树中，具有最小高度的树（即，min(h)）被称为 最小高度树 。
 * 请你找到所有的 最小高度树 并按 任意顺序 返回它们的根节点标签列表。
 * 树的 高度 是指根节点和叶子节点之间最长向下路径上边的数量。
 * <p>
 * 输入：n = 4, edges = [[1,0],[1,2],[1,3]]
 * 输出：[1]
 * 解释：如图所示，当根是标签为 1 的节点时，树的高度是 1 ，这是唯一的最小高度树。
 * <p>
 * 输入：n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]]
 * 输出：[3,4]
 * <p>
 * 1 <= n <= 2 * 10^4
 * edges.length == n - 1
 * 0 <= ai, bi < n
 * ai != bi
 * 所有 (ai, bi) 互不相同
 * 给定的输入 保证 是一棵树，并且 不会有重复的边
 */
public class Problem310 {
    public static void main(String[] args) {
        Problem310 problem310 = new Problem310();
//        int n = 6;
//        int[][] edges = {{3, 0}, {3, 1}, {3, 2}, {3, 4}, {5, 4}};
        int n = 5;
        int[][] edges = {{0, 1}, {0, 2}, {0, 3}, {3, 4}};
        System.out.println(problem310.findMinHeightTrees(n, edges));
        System.out.println(problem310.findMinHeightTrees2(n, edges));
        System.out.println(problem310.findMinHeightTrees3(n, edges));
        System.out.println(problem310.findMinHeightTrees4(n, edges));
    }

    /**
     * 暴力dfs (超时)
     * 对每个节点dfs，得到每个节点作为根节点的树的高度，得到图中最小高度对应的根节点
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) {
            return new ArrayList<Integer>() {{
                add(0);
            }};
        }

        //邻接矩阵
        int[][] graph = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = -1;
            }
        }

        for (int i = 0; i < edges.length; i++) {
            graph[edges[i][0]][edges[i][1]] = 1;
            graph[edges[i][1]][edges[i][0]] = 1;
        }

        List<Integer> list = new ArrayList<>();
        //图中最小高度，初始化为int最大值
        int minHeight = Integer.MAX_VALUE;

        //对每个节点dfs，得到每个节点作为根节点的树的高度，得到图中最小高度对应的根节点
        for (int i = 0; i < n; i++) {
            int curHeight = dfs(i, n, graph, new boolean[n]);

            if (curHeight < minHeight) {
                list.clear();
                list.add(i);
                minHeight = curHeight;
            } else if (curHeight == minHeight) {
                list.add(i);
            }
        }

        return list;
    }

    /**
     * 暴力bfs (超时)
     * 对每个节点bfs，得到每个节点作为根节点的树的高度，得到图中最小高度对应的根节点
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees2(int n, int[][] edges) {
        if (n == 1) {
            return new ArrayList<Integer>() {{
                add(0);
            }};
        }

        //邻接矩阵
        int[][] graph = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = -1;
            }
        }

        for (int i = 0; i < edges.length; i++) {
            graph[edges[i][0]][edges[i][1]] = 1;
            graph[edges[i][1]][edges[i][0]] = 1;
        }

        List<Integer> list = new ArrayList<>();
        //图中最小高度，初始化为int最大值
        int minHeight = Integer.MAX_VALUE;

        //对每个节点bfs，得到每个节点作为根节点的树的高度，得到图中最小高度对应的根节点
        for (int i = 0; i < n; i++) {
            int curHeight = bfs(i, n, graph);

            if (curHeight < minHeight) {
                list.clear();
                list.add(i);
                minHeight = curHeight;
            } else if (curHeight == minHeight) {
                list.add(i);
            }
        }

        return list;
    }

    /**
     * 两次bfs
     * 任意一个节点bfs，得到距离当前节点最远的节点，再从最远的节点bfs，得到另一端的最远节点，
     * 这两个最远节点之间的距离即为树的最长路径，最长路径的中间节点即为图中最小高度对应的根节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees3(int n, int[][] edges) {
        if (n == 1) {
            return new ArrayList<Integer>() {{
                add(0);
            }};
        }

        //邻接表，使用邻接矩阵，则超时
        List<List<Integer>> graph = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
            graph.get(edges[i][1]).add(edges[i][0]);
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        //任意一个节点入队
        queue.offer(0);
        visited[0] = true;

        //最远节点
        int node1 = -1;
        int node2 = -1;
        //节点的父节点数组，用于求node1到node2的路径
        int[] parent = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }

        //从该节点bfs，得到距离当前节点最远的节点node1
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int u = queue.poll();
                node1 = u;

                for (int v : graph.get(u)) {
                    if (visited[v]) {
                        continue;
                    }

                    queue.offer(v);
                    visited[v] = true;
                }
            }
        }

        visited = new boolean[n];
        //最远节点入队
        queue.offer(node1);
        visited[node1] = true;

        //从最远的节点node1bfs，得到另一端的节点node2
        //注意：需要记录parent[i]，用于求node1到node2的路径
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int u = queue.poll();
                node2 = u;

                for (int v : graph.get(u)) {
                    if (visited[v]) {
                        continue;
                    }

                    queue.offer(v);
                    visited[v] = true;
                    parent[v] = u;
                }
            }
        }

        //树的最长路径中的节点集合，即从node1到node2的路径
        List<Integer> path = new ArrayList<>();
        int node = node2;

        //这两个最远节点之间的距离即为树的最长路径
        while (node != -1) {
            path.add(node);
            node = parent[node];
        }

        List<Integer> list = new ArrayList<>();

        //最长路径的中间节点即为图中最小高度对应的根节点
        if (path.size() % 2 == 0) {
            //偶数个节点，则中间的两个节点为图中最小高度对应的根节点
            list.add(path.get(path.size() / 2 - 1));
            list.add(path.get(path.size() / 2));
        } else {
            //奇数个节点，则中间的一个节点为图中最小高度对应的根节点
            list.add(path.get(path.size() / 2));
        }

        return list;
    }

    /**
     * bfs拓扑排序
     * 删除度为1(无向图不区分入度出度)的节点，即删除叶节点，直至图中剩余1个或2个节点即为图中最小高度对应的根节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees4(int n, int[][] edges) {
        if (n == 1) {
            return new ArrayList<Integer>() {{
                add(0);
            }};
        }

        //邻接表
        List<List<Integer>> graph = new ArrayList<>(n);
        //无向图不分出度入度，统称为度
        int[] degree = new int[n];

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
            graph.get(edges[i][1]).add(edges[i][0]);
            degree[edges[i][0]]++;
            degree[edges[i][1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        //未访问到的节点的个数
        int count = n;

        //度为1的节点入队
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) {
                queue.offer(i);
            }
        }

        //未访问到的节点的个数不超过2个，则找到了图中最小高度对应的根节点
        while (count > 2) {
            //当前层需要删除的度为1的节点
            int size = queue.size();
            count = count - size;

            //不能一个节点一个节点删除度为1的节点，只能一层一层删除度为1的节点，
            //否则只能得到2个图中最小高度对应的根节点，没有考虑到只有1个图中最小高度对应的根节点的情况
            for (int i = 0; i < size; i++) {
                int u = queue.poll();

                //删除当前节点相连的边，即邻接节点的度减1，邻接节点度为1的节点入队
                for (int v : graph.get(u)) {
                    degree[v]--;

                    if (degree[v] == 1) {
                        queue.offer(v);
                    }
                }
            }
        }

        List<Integer> list = new ArrayList<>();

        //剩下的这1个或2个节点为图中最小高度对应的根节点
        while (!queue.isEmpty()) {
            int u = queue.poll();
            list.add(u);
        }

        return list;
    }

    /**
     * 得到节点u到最远节点的距离
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param n
     * @param graph
     * @return
     */
    private int dfs(int u, int n, int[][] graph, boolean[] visited) {
        if (visited[u]) {
            return 0;
        }

        int distance = 0;
        visited[u] = true;

        for (int i = 0; i < n; i++) {
            if (graph[u][i] != -1) {
                distance = Math.max(distance, dfs(i, n, graph, visited) + 1);
            }
        }

        return distance;
    }

    /**
     * 得到节点u到最远节点的距离
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param n
     * @param graph
     * @return
     */
    private int bfs(int u, int n, int[][] graph) {
        int distance = 0;
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        queue.offer(u);
        visited[u] = true;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int v = queue.poll();
                visited[u] = true;

                for (int j = 0; j < n; j++) {
                    if (graph[v][j] != -1 && !visited[j]) {
                        queue.offer(j);
                        visited[j] = true;
                    }
                }
            }

            distance++;
        }

        //节点u到最远节点的距离需要减1
        return distance - 1;
    }
}
