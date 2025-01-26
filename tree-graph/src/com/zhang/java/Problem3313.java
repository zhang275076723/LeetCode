package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2025/3/16 08:00
 * @Author zsy
 * @Description 查找树中最后标记的节点 类比Problem1245
 * 有一棵有 n 个节点，编号从 0 到 n - 1 的 无向 树。
 * 给定一个长度为 n - 1 的整数数组 edges，其中 edges[i] = [ui, vi] 表示树中的 ui 和 vi 之间有一条边。
 * 一开始，所有 节点都 未标记。之后的每一秒，你需要标记所有 至少 有一个已标记节点相邻的未标记节点。
 * 返回一个数组 nodes，表示在时刻 t = 0 标记了节点 i，那么树中最后标记的节点是 nodes[i]。
 * 如果对于任意节点 i 有多个 nodes[i]，你可以选择 任意 一个作为答案。
 * <p>
 * 输入：edges = [[0,1],[0,2]]
 * 输出：[2,2,1]
 * 解释：
 * 对于 i = 0，节点以如下序列标记：[0] -> [0,1,2]。1 和 2 都可以是答案。
 * 对于 i = 1，节点以如下序列标记：[1] -> [0,1] -> [0,1,2]。节点 2 最后被标记。
 * 对于 i = 2，节点以如下序列标记：[2] -> [0,2] -> [0,1,2]。节点 1 最后被标记。
 * <p>
 * 输入：edges = [[0,1]]
 * 输出：[1,0]
 * <p>
 * 解释：
 * 对于 i = 0，节点以如下序列被标记：[0] -> [0,1]。
 * 对于 i = 1，节点以如下序列被标记：[1] -> [0,1]。
 * <p>
 * 输入：edges = [[0,1],[0,2],[2,3],[2,4]]
 * 输出：[3,3,1,1,1]
 * 解释：
 * 对于 i = 0，节点以如下序列被标记：[0] -> [0,1,2] -> [0,1,2,3,4]。
 * 对于 i = 1，节点以如下序列被标记：[1] -> [0,1] -> [0,1,2] -> [0,1,2,3,4]。
 * 对于 i = 2，节点以如下序列被标记：[2] -> [0,2,3,4] -> [0,1,2,3,4]。
 * 对于 i = 3，节点以如下序列被标记：[3] -> [2,3] -> [0,2,3,4] -> [0,1,2,3,4]。
 * 对于 i = 4，节点以如下序列被标记：[4] -> [2,4] -> [0,2,3,4] -> [0,1,2,3,4]。
 * <p>
 * 2 <= n <= 10^5
 * edges.length == n - 1
 * edges[i].length == 2
 * 0 <= edges[i][0], edges[i][1] <= n - 1
 * 输入保证 edges 形成一棵合法的树。
 */
public class Problem3313 {
    /**
     * 树的直径，即树中最长路径边的个数
     */
    private int diameter = 0;

    /**
     * 树的直径的一个端点
     */
    private int diameterNode = -1;

    public static void main(String[] args) {
        Problem3313 problem3313 = new Problem3313();
//        int[][] edges = {{0, 1}, {0, 2}};
//        int[][] edges = {{0, 1}};
        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {2, 4}};
        System.out.println(Arrays.toString(problem3313.lastMarkedNodes(edges)));
    }

    /**
     * dfs
     * 同1245题，先求出树的直径的两个端点，从任意节点出发，最后标记的节点为距离当前节点最远的树的直径的端点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param edges
     * @return
     */
    public int[] lastMarkedNodes(int[][] edges) {
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

        //树的直径的端点
        int diameterNode1;
        //树的直径的端点
        int diameterNode2;

        //从任意节点开始dfs，得到树的直径的一个端点diameterNode1
        dfs(0, -1, 0, graph);
        diameterNode1 = diameterNode;

        //树的直径重新赋值为0，来求树的直径的另一个端点
        diameter = 0;

        //从树的直径的端点dfs，得到树的直径的另外一个端点diameterNode2
        dfs(diameterNode1, -1, 0, graph);
        diameterNode2 = diameterNode;

        //diameterNode1到其他节点的距离
        int[] distance1 = new int[n];
        //diameterNode2到其他节点的距离
        int[] distance2 = new int[n];

        dfs(diameterNode1, -1, 0, graph, distance1);
        dfs(diameterNode2, -1, 0, graph, distance2);

        int[] result = new int[n];

        //从任意节点出发，最后标记的节点为距离当前节点最远的树的直径的端点
        for (int i = 0; i < n; i++) {
            if (distance1[i] > distance2[i]) {
                result[i] = diameterNode1;
            } else {
                result[i] = diameterNode2;
            }
        }

        return result;
    }

    /**
     * 从任意节点dfs，得到树的直径的一个端点diameterNode；从树的直径的端点dfs，得到树的直径diameter
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param parent
     * @param count
     * @param graph
     */
    private void dfs(int u, int parent, int count, List<List<Integer>> graph) {
        count++;

        //更新树的的直径，更新树的直径的一个端点
        if (count - 1 > diameter) {
            diameter = count - 1;
            diameterNode = u;
        }

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            dfs(v, u, count, graph);
        }
    }

    private void dfs(int u, int parent, int count, List<List<Integer>> graph, int[] distance) {
        count++;
        //diameterNode1或diameterNode2到节点u的距离为经过的节点个数减1
        distance[u] = count - 1;

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            dfs(v, u, count, graph, distance);
        }
    }
}
