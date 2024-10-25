package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2025/1/17 08:37
 * @Author zsy
 * @Description 最大化一张图中的路径价值 类比Problem1377
 * 给你一张 无向 图，图中有 n 个节点，节点编号从 0 到 n - 1 （都包括）。
 * 同时给你一个下标从 0 开始的整数数组 values ，其中 values[i] 是第 i 个节点的 价值 。
 * 同时给你一个下标从 0 开始的二维整数数组 edges ，其中 edges[j] = [uj, vj, timej] 表示节点 uj 和 vj 之间有一条需要 timej 秒才能通过的无向边。
 * 最后，给你一个整数 maxTime 。
 * 合法路径 指的是图中任意一条从节点 0 开始，最终回到节点 0 ，且花费的总时间 不超过 maxTime 秒的一条路径。
 * 你可以访问一个节点任意次。一条合法路径的 价值 定义为路径中 不同节点 的价值 之和 （每个节点的价值 至多 算入价值总和中一次）。
 * 请你返回一条合法路径的 最大 价值。
 * 注意：每个节点 至多 有 四条 边与之相连。
 * <p>
 * 输入：values = [0,32,10,43], edges = [[0,1,10],[1,2,15],[0,3,10]], maxTime = 49
 * 输出：75
 * 解释：
 * 一条可能的路径为：0 -> 1 -> 0 -> 3 -> 0 。总花费时间为 10 + 10 + 10 + 10 = 40 <= 49 。
 * 访问过的节点为 0 ，1 和 3 ，最大路径价值为 0 + 32 + 43 = 75 。
 * <p>
 * 输入：values = [5,10,15,20], edges = [[0,1,10],[1,2,10],[0,3,10]], maxTime = 30
 * 输出：25
 * 解释：
 * 一条可能的路径为：0 -> 3 -> 0 。总花费时间为 10 + 10 = 20 <= 30 。
 * 访问过的节点为 0 和 3 ，最大路径价值为 5 + 20 = 25 。
 * <p>
 * 输入：values = [1,2,3,4], edges = [[0,1,10],[1,2,11],[2,3,12],[1,3,13]], maxTime = 50
 * 输出：7
 * 解释：
 * 一条可能的路径为：0 -> 1 -> 3 -> 1 -> 0 。总花费时间为 10 + 13 + 13 + 10 = 46 <= 50 。
 * 访问过的节点为 0 ，1 和 3 ，最大路径价值为 1 + 2 + 4 = 7 。
 * <p>
 * 输入：values = [0,1,2], edges = [[1,2,10]], maxTime = 10
 * 输出：0
 * 解释：
 * 唯一一条路径为 0 。总花费时间为 0 。
 * 唯一访问过的节点为 0 ，最大路径价值为 0 。
 * <p>
 * n == values.length
 * 1 <= n <= 1000
 * 0 <= values[i] <= 10^8
 * 0 <= edges.length <= 2000
 * edges[j].length == 3
 * 0 <= uj < vj <= n - 1
 * 10 <= timej, maxTime <= 100
 * [uj, vj] 所有节点对 互不相同 。
 * 每个节点 至多有四条 边。
 * 图可能不连通。
 */
public class Problem2065 {
    /**
     * 从节点0出发再重新返回节点0的路径的最大价值
     */
    private int maxValue = 0;

    public static void main(String[] args) {
        Problem2065 problem2065 = new Problem2065();
        int[] values = {0, 32, 10, 43};
        int[][] edges = {{0, 1, 10}, {1, 2, 15}, {0, 3, 10}};
        int maxTime = 49;
//        int[] values = {1, 2, 3, 4};
//        int[][] edges = {{0, 1, 10}, {1, 2, 11}, {2, 3, 12}, {1, 3, 13}};
//        int maxTime = 50;
        System.out.println(problem2065.maximalPathQuality(values, edges, maxTime));
    }

    /**
     * 图的dfs
     * 时间复杂度O(m+n+4^k)，空间复杂度O(m+n+k) (n=values.length，即图中节点的个数，m=edges.length，即图中边的个数)
     * (k=maxTime/min(timej)=10，即dfs的最大深度) (每个节点最多有4条边，即dfs每次至多有4个选择，k层深度的dfs时间复杂度O(4^k))
     *
     * @param values
     * @param edges
     * @param maxTime
     * @return
     */
    public int maximalPathQuality(int[] values, int[][] edges, int maxTime) {
        int n = values.length;

        //邻接表，无向图
        //当前图为稀疏图，所以适合使用邻接表
        List<List<int[]>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int weight = edges[i][2];

            graph.get(u).add(new int[]{v, weight});
            graph.get(v).add(new int[]{u, weight});
        }

        boolean[] visited = new boolean[n];
        visited[0] = true;

        dfs(0, values[0], 0, maxTime, visited, values, graph);

        return maxValue;
    }

    private void dfs(int u, int curValue, int curTime, int maxTime, boolean[] visited, int[] values, List<List<int[]>> graph) {
        if (curTime > maxTime) {
            return;
        }

        //从节点0出发再重新返回节点0，则当前路径是一条合法路径，更新合法路径的最大价值
        if (u == 0) {
            maxValue = Math.max(maxValue, curValue);
        }

        for (int[] arr : graph.get(u)) {
            int v = arr[0];
            int weight = arr[1];

            if (!visited[v]) {
                visited[v] = true;
                dfs(v, curValue + values[v], curTime + weight, maxTime, visited, values, graph);
                visited[v] = false;
            } else {
                dfs(v, curValue, curTime + weight, maxTime, visited, values, graph);
            }
        }
    }
}
