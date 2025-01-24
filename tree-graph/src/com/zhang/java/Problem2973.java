package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/3/13 08:17
 * @Author zsy
 * @Description 树中每个节点放置的金币数目 类比Problem628、Problem747、Problem1509 dfs类比Problem124、Problem250、Problem298、Problem337、Problem543、Problem687、Problem968、Problem979、Problem1245、Problem1372、Problem1373、Problem2246、Problem2378、Problem2925
 * 给你一棵 n 个节点的 无向 树，节点编号为 0 到 n - 1 ，树的根节点在节点 0 处。
 * 同时给你一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间有一条边。
 * 给你一个长度为 n 下标从 0 开始的整数数组 cost ，其中 cost[i] 是第 i 个节点的 开销 。
 * 你需要在树中每个节点都放置金币，在节点 i 处的金币数目计算方法如下：
 * 如果节点 i 对应的子树中的节点数目小于 3 ，那么放 1 个金币。
 * 否则，计算节点 i 对应的子树内 3 个不同节点的开销乘积的 最大值 ，并在节点 i 处放置对应数目的金币。
 * 如果最大乘积是 负数 ，那么放置 0 个金币。
 * 请你返回一个长度为 n 的数组 coin ，coin[i]是节点 i 处的金币数目。
 * <p>
 * 输入：edges = [[0,1],[0,2],[0,3],[0,4],[0,5]], cost = [1,2,3,4,5,6]
 * 输出：[120,1,1,1,1,1]
 * 解释：在节点 0 处放置 6 * 5 * 4 = 120 个金币。所有其他节点都是叶子节点，子树中只有 1 个节点，所以其他每个节点都放 1 个金币。
 * <p>
 * 输入：edges = [[0,1],[0,2],[1,3],[1,4],[1,5],[2,6],[2,7],[2,8]], cost = [1,4,2,3,5,7,8,-4,2]
 * 输出：[280,140,32,1,1,1,1,1,1]
 * 解释：每个节点放置的金币数分别为：
 * - 节点 0 处放置 8 * 7 * 5 = 280 个金币。
 * - 节点 1 处放置 7 * 5 * 4 = 140 个金币。
 * - 节点 2 处放置 8 * 2 * 2 = 32 个金币。
 * - 其他节点都是叶子节点，子树内节点数目为 1 ，所以其他每个节点都放 1 个金币。
 * <p>
 * 输入：edges = [[0,1],[0,2]], cost = [1,2,-2]
 * 输出：[0,1,1]
 * 解释：节点 1 和 2 都是叶子节点，子树内节点数目为 1 ，各放置 1 个金币。节点 0 处唯一的开销乘积是 2 * 1 * -2 = -4 。
 * 所以在节点 0 处放置 0 个金币。
 * <p>
 * 2 <= n <= 2 * 10^4
 * edges.length == n - 1
 * edges[i].length == 2
 * 0 <= ai, bi < n
 * cost.length == n
 * 1 <= |cost[i]| <= 10^4
 * edges 一定是一棵合法的树。
 */
public class Problem2973 {
    public static void main(String[] args) {
        Problem2973 problem2973 = new Problem2973();
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {1, 5}, {2, 6}, {2, 7}, {2, 8}};
        int[] cost = {1, 4, 2, 3, 5, 7, 8, -4, 2};
        System.out.println(Arrays.toString(problem2973.placedCoins(edges, cost)));
    }

    /**
     * dfs+优先队列，大小根堆
     * 计算当前节点子节点作为根节点的树的TreeNodeInfo，当前节点作为根节点的树中最大的三个节点开销乘积与最大的节点开销和最小的2个节点开销乘积中的较大值即为当前节点的金币数，
     * 返回当前节点作为根节点的树的TreeNodeInfo，用于计算当前节点父节点作为根节点的树的TreeNodeInfo
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param edges
     * @param cost
     * @return
     */
    public long[] placedCoins(int[][] edges, int[] cost) {
        //节点的个数
        int n = cost.length;
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

        long[] result = new long[n];

        dfs(0, -1, graph, cost, result);

        return result;
    }

    private TreeNodeInfo dfs(int u, int parent, List<List<Integer>> graph, int[] cost, long[] result) {
        //节点u为根节点的树中节点的个数
        int count = 1;
        //小根堆，存储节点u为根节点的树中最大的3个节点开销
        PriorityQueue<Integer> maxPriorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });
        //大根堆，存储节点u为根节点的树中最小的2个节点开销
        PriorityQueue<Integer> minPriorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });
        //当前节点u的开销入小根堆
        maxPriorityQueue.offer(cost[u]);
        //当前节点u的开销入大根堆
        minPriorityQueue.offer(cost[u]);

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            //子节点v为根节点的树的TreeNodeInfo
            TreeNodeInfo vTreeNodeInfo = dfs(v, u, graph, cost, result);
            count = count + vTreeNodeInfo.count;

            //更新maxPriorityQueue
            for (int max : vTreeNodeInfo.maxArr) {
                maxPriorityQueue.offer(max);

                if (maxPriorityQueue.size() > 3) {
                    maxPriorityQueue.poll();
                }
            }

            //更新minPriorityQueue
            for (int min : vTreeNodeInfo.minArr) {
                minPriorityQueue.offer(min);

                if (minPriorityQueue.size() > 2) {
                    minPriorityQueue.poll();
                }
            }
        }

        //节点u为根节点的树中最大的3个节点开销
        int[] maxArr = new int[maxPriorityQueue.size()];
        //节点u为根节点的树中最小的2个节点开销
        int[] minArr = new int[minPriorityQueue.size()];

        //最大开销放在第一个位置
        for (int i = maxArr.length - 1; i >= 0; i--) {
            maxArr[i] = maxPriorityQueue.poll();
        }

        //最小开销放在第一个位置
        for (int i = minArr.length - 1; i >= 0; i--) {
            minArr[i] = minPriorityQueue.poll();
        }

        //节点u为根节点的树中节点的个数小于3，则放1个金币
        if (count < 3) {
            result[u] = 1;
        } else {
            //如果最大开销为负数，则放0个金币
            //使用long，避免int相乘溢出
            result[u] = Math.max(0, Math.max((long) maxArr[0] * maxArr[1] * maxArr[2], (long) maxArr[0] * minArr[0] * minArr[1]));
        }

        return new TreeNodeInfo(count, maxArr, minArr);
    }

    /**
     * dfs当前节点为根节点的树中返回的内容
     */
    private static class TreeNodeInfo {
        //当前节点为根节点的树中节点的个数
        private int count;
        //当前节点为根节点的树中最大的3个节点的开销数组
        //最大开销放在第一个位置
        private int[] maxArr;
        //当前节点为根节点的树中最小的2个节点的开销数组
        //最小开销放在第一个位置
        private int[] minArr;

        public TreeNodeInfo(int count, int[] maxArr, int[] minArr) {
            this.count = count;
            this.maxArr = maxArr;
            this.minArr = minArr;
        }
    }
}
