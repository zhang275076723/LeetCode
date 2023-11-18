package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/27 08:11
 * @Author zsy
 * @Description 购买苹果的最低成本 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1293、Problem1334、Problem1368、Problem1462、Problem1514、Problem1631、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2203、Problem2290、Problem2662、Dijkstra
 * 给你一个正整数  n，表示从 1 到 n 的 n 个城市。
 * 还给你一个 二维 数组 roads，其中 roads[i] = [ai, bi, costi] 表示在城市 ai 和 bi 之间有一条双向道路，其旅行成本等于 costi。
 * 你可以在 任何 城市买到苹果，但是有些城市买苹果的费用不同。
 * 给定数组 appleCost ，其中 appleCost[i] 是从城市 i 购买一个苹果的成本。
 * 你从某个城市开始，穿越各种道路，最终从 任何一个 城市买 一个 苹果。
 * 在你买了那个苹果之后，你必须回到你 开始的 城市，但现在所有道路的成本将 乘以 一个给定的因子 k。
 * 给定整数 k，返回一个大小为 n 的数组 answer，其中 answer[i] 是从城市 i 开始购买一个苹果的 最小 总成本。
 * <p>
 * 输入: n = 4, roads = [[1,2,4],[2,3,2],[2,4,5],[3,4,1],[1,3,4]], appleCost = [56,42,102,301], k = 2
 * 输出: [54,42,48,51]
 * 解释: 每个起始城市的最低费用如下:
 * - 从城市 1 开始:你走路径 1 -> 2，在城市 2 买一个苹果，最后走路径 2 -> 1。总成本是 4 + 42 + 4 * 2 = 54。
 * - 从城市 2 开始:你直接在城市 2 买一个苹果。总费用是 42。
 * - 从城市 3 开始:你走路径 3 -> 2，在城市 2 买一个苹果，最后走路径 2 -> 3。总成本是 2 + 42 + 2 * 2 = 48。
 * - 从城市 4 开始:你走路径 4 -> 3 -> 2，然后你在城市 2 购买，最后走路径 2 -> 3 -> 4。总成本是 1 + 2 + 42 + 1 * 2 + 2 * 2 = 51。
 * <p>
 * 输入: n = 3, roads = [[1,2,5],[2,3,1],[3,1,2]], appleCost = [2,3,1], k = 3
 * 输出: [2,3,1]
 * 解释: 在起始城市买苹果总是最优的。
 * <p>
 * 2 <= n <= 1000
 * 1 <= roads.length <= 1000
 * 1 <= ai, bi <= n
 * ai != bi
 * 1 <= costi <= 10^5
 * appleCost.length == n
 * 1 <= appleCost[i] <= 10^5
 * 1 <= k <= 100
 * 没有重复的边。
 */
public class Problem2473 {
    public static void main(String[] args) {
        Problem2473 problem2473 = new Problem2473();
        int n = 4;
        int[][] roads = {{1, 2, 4}, {2, 3, 2}, {2, 4, 5}, {3, 4, 1}, {1, 3, 4}};
        int[] appleCost = {56, 42, 102, 301};
        int k = 2;
        System.out.println(Arrays.toString(problem2473.minCost(n, roads, appleCost, k)));
    }

    /**
     * 堆优化Dijkstra求当前节点到其他节点的最小旅行成本(不包含购买苹果的成本)的过程中，得到当前节点到其他节点购买苹果的最小总成本
     * 节点u到节点v购买苹果的最小总成本=节点u到节点v的最小旅行成本*(k+1)+appleCost[v] (乘以k+1是路径往返的旅行成本)
     * 时间复杂度O(n*mlogm)，空间复杂度O(n) (m=roads.length，即m为图中边的个数，n为图中节点的个数)
     *
     * @param n
     * @param roads
     * @param appleCost
     * @param k
     * @return
     */
    public long[] minCost(int n, int[][] roads, int[] appleCost, int k) {
        List<List<int[]>> edges = new ArrayList<>();

        //注意节点是从1开始，不需要多加1，是因为每个节点都减1，使节点从0开始
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < roads.length; i++) {
            int u = roads[i][0] - 1;
            int v = roads[i][1] - 1;
            int weight = roads[i][2];

            edges.get(u).add(new int[]{v, weight});
            edges.get(v).add(new int[]{u, weight});
        }

        long[] result = new long[n];

        for (int i = 0; i < n; i++) {
            result[i] = dijkstra(i, edges, appleCost, k);
        }

        return result;
    }

    /**
     * 堆优化Dijkstra求节点u到其他节点的最小旅行成本(不包含购买苹果的成本)的过程中，得到节点u到其他节点购买苹果的最小总成本
     * 节点u到节点v购买苹果的最小总成本=节点u到节点v的最小旅行成本*(k+1)+appleCost[v] (乘以k+1是路径往返的旅行成本)
     * 时间复杂度O(mlogm)，空间复杂度O(n) (n为图中节点的个数，m为图中边的个数)
     *
     * @param u
     * @param edges
     * @param appleCost
     * @param k
     * @return
     */
    private long dijkstra(int u, List<List<int[]>> edges, int[] appleCost, int k) {
        //图中节点的个数
        int n = edges.size();
        //节点u到其他节点的最小旅行成本数组
        int[] cost = new int[n];

        //distance数组初始化，初始化为int最大值表示节点u无法到达节点i
        for (int i = 0; i < n; i++) {
            cost[i] = Integer.MAX_VALUE;
        }

        //初始化，节点u到节点u的最小旅行成本为0
        cost[u] = 0;

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] - arr2[1];
            }
        });

        priorityQueue.offer(new int[]{u, 0});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            int v = arr[0];
            int curCost = arr[1];

            if (curCost > cost[v]) {
                continue;
            }

            for (int[] arr2 : edges.get(v)) {
                int w = arr2[0];
                int weight = arr2[1];

                if (curCost + weight < cost[w]) {
                    cost[w] = curCost + weight;
                    priorityQueue.offer(new int[]{w, cost[w]});
                }
            }
        }

        //节点u到其他节点购买苹果的最小总成本
        long result = Long.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            //节点u到节点i购买苹果的最小总成本=节点u到节点i的最小旅行成本*(k+1)+appleCost[i] (乘以k+1是路径往返的旅行成本)
            result = Math.min(result, (long) cost[i] * (k + 1) + appleCost[i]);
        }

        return result;
    }
}
