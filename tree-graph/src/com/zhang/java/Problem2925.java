package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2025/3/12 08:47
 * @Author zsy
 * @Description 在树上执行操作以后得到的最大分数 dfs类比Problem124、Problem250、Problem298、Problem337、Problem543、Problem687、Problem968、Problem979、Problem1245、Problem1372、Problem1373、Problem2246、Problem2378、Problem2973
 * 有一棵 n 个节点的无向树，节点编号为 0 到 n - 1 ，根节点编号为 0 。
 * 给你一个长度为 n - 1 的二维整数数组 edges 表示这棵树，其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 有一条边。
 * 同时给你一个长度为 n 下标从 0 开始的整数数组 values ，其中 values[i] 表示第 i 个节点的值。
 * 一开始你的分数为 0 ，每次操作中，你将执行：
 * 选择节点 i 。
 * 将 values[i] 加入你的分数。
 * 将 values[i] 变为 0 。
 * 如果从根节点出发，到任意叶子节点经过的路径上的节点值之和都不等于 0 ，那么我们称这棵树是 健康的 。
 * 你可以对这棵树执行任意次操作，但要求执行完所有操作以后树是 健康的 ，请你返回你可以获得的 最大分数 。
 * <p>
 * 输入：edges = [[0,1],[0,2],[0,3],[2,4],[4,5]], values = [5,2,5,2,1,1]
 * 输出：11
 * 解释：我们可以选择节点 1 ，2 ，3 ，4 和 5 。根节点的值是非 0 的。所以从根出发到任意叶子节点路径上节点值之和都不为 0 。所以树是健康的。你的得分之和为 values[1] + values[2] + values[3] + values[4] + values[5] = 11 。
 * 11 是你对树执行任意次操作以后可以获得的最大得分之和。
 * <p>
 * 输入：edges = [[0,1],[0,2],[1,3],[1,4],[2,5],[2,6]], values = [20,10,9,7,4,3,5]
 * 输出：40
 * 解释：我们选择节点 0 ，2 ，3 和 4 。
 * - 从 0 到 4 的节点值之和为 10 。
 * - 从 0 到 3 的节点值之和为 10 。
 * - 从 0 到 5 的节点值之和为 3 。
 * - 从 0 到 6 的节点值之和为 5 。
 * 所以树是健康的。你的得分之和为 values[0] + values[2] + values[3] + values[4] = 40 。
 * 40 是你对树执行任意次操作以后可以获得的最大得分之和。
 * <p>
 * 2 <= n <= 2 * 10^4
 * edges.length == n - 1
 * edges[i].length == 2
 * 0 <= ai, bi < n
 * values.length == n
 * 1 <= values[i] <= 10^9
 * 输入保证 edges 构成一棵合法的树。
 */
public class Problem2925 {
    public static void main(String[] args) {
        Problem2925 problem2925 = new Problem2925();
//        int[][] edges = {{0, 1}, {0, 2}, {0, 3}, {2, 4}, {4, 5}};
//        int[] values = {5, 2, 5, 2, 1, 1};
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {2, 6}};
        int[] values = {20, 10, 9, 7, 4, 3, 5};
        System.out.println(problem2925.maximumScoreAfterOperations(edges, values));
    }

    /**
     * dfs
     * arr[0]：当前节点作为根节点的树的节点值之和
     * arr[1]：当前节点作为根节点的树的最大分数
     * 计算当前节点子节点作为根节点的树的节点值之和和树的最大分数数组，arr[1]即为当前节点作为根节点的树的最大分数，
     * 返回当前节点作为根节点的树的节点值之和和树的最大分数数组，用于计算当前节点父节点作为根节点的树的节点值之和和树的最大分数数组
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param edges
     * @param values
     * @return
     */
    public long maximumScoreAfterOperations(int[][] edges, int[] values) {
        //节点的个数
        int n = values.length;
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

        long[] arr = dfs(0, -1, graph, values);

        return arr[1];
    }

    /**
     * 返回当前节点作为根节点，树的节点值之和和树的最大分数数组
     * arr[0]：当前节点作为根节点的树的节点值之和
     * arr[1]：当前节点作为根节点的树的最大分数
     *
     * @param u
     * @param parent
     * @param graph
     * @param values
     * @return
     */
    private long[] dfs(int u, int parent, List<List<Integer>> graph, int[] values) {
        long[] arr = new long[2];
        arr[0] = values[u];
        //节点u的所有子节点v为根节点的树的最大分数之和
        long vSum = 0;
        //当前节点u是否有子节点标志位
        boolean flag = false;

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            flag = true;
            //当前节点u子节点v作为根节点的树的节点值之和和树的最大分数数组
            long[] vArr = dfs(v, u, graph, values);

            arr[0] = arr[0] + vArr[0];
            vSum = vSum + vArr[1];
        }

        //当前节点u没有子节点，则节点u为根节点的树的最大分数为0
        if (!flag) {
            return new long[]{values[u], 0};
        }

        //节点u为根节点的树的最大分数=max(不选当前节点u的最大分数，选当前节点u的最大分数)
        //不选当前节点u的最大分数=节点u的所有子节点v为根节点的树的节点值之和
        //选当前节点u的最大分数=节点u的所有子节点v为根节点的树的最大分数之和+values[u]
        arr[1] = Math.max(arr[0] - values[u], vSum + values[u]);

        return arr;
    }
}
