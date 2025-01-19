package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2024/1/30 08:35
 * @Author zsy
 * @Description 树的直径 类比Problem543、Problem2246 类比Problem310、Problem2603 dfs类比Problem124、Problem298、Problem337、Problem543、Problem687、Problem968、Problem979、Problem1372、Problem1373、Problem2246、Problem2378 拓扑排序类比
 * 给你这棵「无向树」，请你测算并返回它的「直径」：这棵树上最长简单路径的 边数。
 * 我们用一个由所有「边」组成的数组 edges 来表示一棵无向树，其中 edges[i] = [u, v] 表示节点 u 和 v 之间的双向边。
 * 树上的节点都已经用 {0, 1, ..., edges.length} 中的数做了标记，每个节点上的标记都是独一无二的。
 * <p>
 * 输入：edges = [[0,1],[0,2]]
 * 输出：2
 * 解释：
 * 这棵树上最长的路径是 1 - 0 - 2，边数为 2。
 * <p>
 * 输入：edges = [[0,1],[1,2],[2,3],[1,4],[4,5]]
 * 输出：4
 * 解释：
 * 这棵树上最长的路径是 3 - 2 - 1 - 4 - 5，边数为 4。
 * <p>
 * 0 <= edges.length < 10^4
 * edges[i][0] != edges[i][1]
 * 0 <= edges[i][j] <= edges.length
 * edges 会形成一棵无向树
 */
public class Problem1245 {
    /**
     * treeDiameter2中dfs树的直径
     */
    private int diameter = 0;

    /**
     * treeDiameter2中dfs树的直径中一端的节点
     */
    private int diameterNode = -1;

    /**
     * treeDiameter3中dfs树的直径
     */
    private int diameter2 = 0;

    public static void main(String[] args) {
        Problem1245 problem1245 = new Problem1245();
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {1, 4}, {4, 5}};
        System.out.println(problem1245.treeDiameter(edges));
        System.out.println(problem1245.treeDiameter2(edges));
        System.out.println(problem1245.treeDiameter3(edges));
        System.out.println(problem1245.treeDiameter4(edges));
    }

    /**
     * 暴力dfs
     * 从每一个节点dfs，求当前节点能够到达的最远节点，更新最长路径，最终得到树的直径
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param edges
     * @return
     */
    public int treeDiameter(int[][] edges) {
        //节点的个数
        int n = edges.length + 1;
        //邻接表，无向图
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

        //树的直径
        int diameter = 0;

        for (int i = 0; i < n; i++) {
            //注意：dfs返回的是节点i到其他节点的路径中最多能访问到的节点个数，而树的直径是路径中边的个数，所以要减1
            diameter = Math.max(diameter, dfs(i, graph, new boolean[n]) - 1);
        }

        return diameter;
    }

    /**
     * 两次dfs
     * 任意一个节点dfs，得到距离当前节点最远的节点，最远节点即为树的直径中一端的节点，
     * 再从树的直径中一端的节点dfs，得到树的直径中另一端的节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param edges
     * @return
     */
    public int treeDiameter2(int[][] edges) {
        //节点的个数
        int n = edges.length + 1;
        //邻接表，无向图
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

        //任意一个节点dfs，得到距离当前节点最远的节点，最远节点即为树的直径中一端的节点
        dfs(0, 0, graph, new boolean[n]);
        //从树的直径中一端的节点dfs，得到树的直径中另一端的节点
        dfs(diameterNode, 0, graph, new boolean[n]);

        return diameter;
    }

    /**
     * dfs
     * 计算当前节点子节点作为路径起点的最大单侧路径长度，更新树的直径，
     * 返回当前节点对父节点的最大单侧路径长度，用于计算以当前节点父节点作为根节点的树的直径
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param edges
     * @return
     */
    public int treeDiameter3(int[][] edges) {
        //节点的个数
        int n = edges.length + 1;
        //邻接表，无向图
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

        //从任意节点开始dfs都可以，这里就从0开始dfs
        dfs2(0, graph, new boolean[n]);

        return diameter2;
    }

    /**
     * bfs拓扑排序
     * 删除度为1(无向图不区分入度出度)的节点，即删除叶节点，直至图中剩余1个或2个节点，
     * 最后图中剩余1个节点，则bfs删除节点的层数*2即为树的直径；最后图中剩余2个节点，则bfs删除节点的层数*2+1即为树的直径
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param edges
     * @return
     */
    public int treeDiameter4(int[][] edges) {
        //节点的个数
        int n = edges.length + 1;
        //邻接表，无向图
        List<List<Integer>> graph = new ArrayList<>();
        //bfs拓扑排序统计入度，所以无向图当做有向图
        int[] degree = new int[n];

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            graph.get(u).add(v);
            graph.get(v).add(u);
            degree[u]++;
            degree[v]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        //bfs拓扑排序未访问到的节点的个数
        int count = n;
        //bfs的层数
        int level = 0;

        //度为1的节点入队
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) {
                queue.offer(i);
            }
        }

        //未访问到的节点的个数不超过2个，则找到了树的直径
        while (!queue.isEmpty() && count > 2) {
            //bfs当前层遍历的节点个数
            int size = queue.size();

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

            //删除bfs当前层遍历的节点个数
            count = count - size;
            level++;
        }

        //最后图中剩余1个节点，则bfs删除节点的层数*2即为树的直径；最后图中剩余2个节点，则bfs删除节点的层数*2+1即为树的直径
        return count == 1 ? level * 2 : level * 2 + 1;
    }

    /**
     * treeDiameter中节点u到其他节点的路径中最多能访问到的节点个数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param graph
     * @param visited
     * @return
     */
    private int dfs(int u, List<List<Integer>> graph, boolean[] visited) {
        if (visited[u]) {
            return 0;
        }

        visited[u] = true;
        int count = 0;

        for (int v : graph.get(u)) {
            count = Math.max(count, dfs(v, graph, visited) + 1);
        }

        return count;
    }

    /**
     * treeDiameter2中节点u最远能访问到的节点，同时diameterNode记录树的直径中一端的节点，diameter更新树的直径
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param count   从起始节点到当前节点u路径边的个数
     * @param graph
     * @param visited
     * @return
     */
    private void dfs(int u, int count, List<List<Integer>> graph, boolean[] visited) {
        if (visited[u]) {
            return;
        }

        visited[u] = true;
        count++;

        //更新树的直径中一端的节点，访问个数减1，即为当前路径的直径
        if (count - 1 > diameter) {
            diameter = count - 1;
            diameterNode = u;
        }

        for (int v : graph.get(u)) {
            dfs(v, count, graph, visited);
        }
    }

    private int dfs2(int u, List<List<Integer>> graph, boolean[] visited) {
        if (visited[u]) {
            return 0;
        }

        visited[u] = true;

        //节点v对父节点u的最大单侧路径长度
        int maxVLen = 0;

        for (int v : graph.get(u)) {
            //节点v对父节点u的单侧路径长度
            int vLen = dfs2(v, graph, visited);
            //更新树的直径
            diameter2 = Math.max(diameter2, maxVLen + vLen);
            //更新maxVLen
            maxVLen = Math.max(maxVLen, vLen);
        }

        //返回当前节点u对父节点的最大单侧路径长度，用于计算以当前节点父节点作为根节点的树的直径
        return maxVLen + 1;
    }
}
