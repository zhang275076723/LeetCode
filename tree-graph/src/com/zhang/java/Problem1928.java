package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/17 08:14
 * @Author zsy
 * @Description 规定时间内到达终点的最小花费 花旗银行机试题 带限制条件的最短路径类比Problem787、Problem1293、Problem2093 Bellman-Ford类比Problem568、Problem787、Problem1293 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1293、Problem1334、Problem1368、Problem1462、Problem1514、Problem1631、Problem1786、Problem1976、Problem2045、Problem2093、Problem2203、Problem2290、Problem2473、Problem2662、Dijkstra
 * 一个国家有 n 个城市，城市编号为 0 到 n - 1 ，题目保证 所有城市 都由双向道路 连接在一起 。
 * 道路由二维整数数组 edges 表示，其中 edges[i] = [xi, yi, timei] 表示城市 xi 和 yi 之间有一条双向道路，耗费时间为 timei 分钟。
 * 两个城市之间可能会有多条耗费时间不同的道路，但是不会有道路两头连接着同一座城市。
 * 每次经过一个城市时，你需要付通行费。
 * 通行费用一个长度为 n 且下标从 0 开始的整数数组 passingFees 表示，其中 passingFees[j] 是你经过城市 j 需要支付的费用。
 * 一开始，你在城市 0 ，你想要在 maxTime 分钟以内 （包含 maxTime 分钟）到达城市 n - 1 。
 * 旅行的 费用 为你经过的所有城市 通行费之和 （包括 起点和终点城市的通行费）。
 * 给你 maxTime，edges 和 passingFees ，请你返回完成旅行的 最小费用 ，如果无法在 maxTime 分钟以内完成旅行，请你返回 -1 。
 * <p>
 * 输入：maxTime = 30, edges = [[0,1,10],[1,2,10],[2,5,10],[0,3,1],[3,4,10],[4,5,15]], passingFees = [5,1,2,20,20,3]
 * 输出：11
 * 解释：最优路径为 0 -> 1 -> 2 -> 5 ，总共需要耗费 30 分钟，需要支付 11 的通行费。
 * <p>
 * 输入：maxTime = 29, edges = [[0,1,10],[1,2,10],[2,5,10],[0,3,1],[3,4,10],[4,5,15]], passingFees = [5,1,2,20,20,3]
 * 输出：48
 * 解释：最优路径为 0 -> 3 -> 4 -> 5 ，总共需要耗费 26 分钟，需要支付 48 的通行费。
 * 你不能选择路径 0 -> 1 -> 2 -> 5 ，因为这条路径耗费的时间太长。
 * <p>
 * 输入：maxTime = 25, edges = [[0,1,10],[1,2,10],[2,5,10],[0,3,1],[3,4,10],[4,5,15]], passingFees = [5,1,2,20,20,3]
 * 输出：-1
 * 解释：无法在 25 分钟以内从城市 0 到达城市 5 。
 * <p>
 * 1 <= maxTime <= 1000
 * n == passingFees.length
 * 2 <= n <= 1000
 * n - 1 <= edges.length <= 1000
 * 0 <= xi, yi <= n - 1
 * 1 <= timei <= 1000
 * 1 <= passingFees[j] <= 1000
 * 图中两个节点之间可能有多条路径。
 * 图中不含有自环。
 */
public class Problem1928 {
    public static void main(String[] args) {
        Problem1928 problem1928 = new Problem1928();
        int maxTime = 29;
        int[][] edges = {{0, 1, 10}, {1, 2, 10}, {2, 5, 10}, {0, 3, 1}, {3, 4, 10}, {4, 5, 15}};
        int[] passingFees = {5, 1, 2, 20, 20, 3};
        System.out.println(problem1928.minCost(maxTime, edges, passingFees));
        System.out.println(problem1928.minCost2(maxTime, edges, passingFees));
        System.out.println(problem1928.minCost3(maxTime, edges, passingFees));
    }

    /**
     * bfs
     * 时间复杂度O((n*maxTime)^2)，空间复杂度O(m+n+n*maxTime) (m=edges.length，即图中边的个数，n=passingFees.length，即图中节点的个数)
     * (最多经过maxTime分钟，每经过1分钟，最多将n个节点入堆，最多会将n*maxTime个节点入堆)
     *
     * @param maxTime
     * @param edges
     * @param passingFees
     * @return
     */
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        //图中节点个数
        int n = passingFees.length;
        //邻接表，无向图，arr[0]：当前节点的邻接节点，arr[1]：当前节点和邻接节点边的权值，即两节点之间需要的时间
        List<List<int[]>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            //节点u和节点v之间需要的时间
            int weight = edges[i][2];

            graph.get(u).add(new int[]{v, weight});
            graph.get(v).add(new int[]{u, weight});
        }

        //节点0到其他节点经过分钟的最小费用数组
        int[][] cost = new int[n][maxTime + 1];

        //cost初始化，初始化为int最大值表示节点0无法到节点i经过j分钟
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= maxTime; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点0到节点0经过0分钟的最小费用为passingFees[0]
        cost[0][0] = passingFees[0];

        //arr[0]：当前节点，arr[1]：节点0到当前节点经过的分钟数，
        //arr[2]：节点0到当前节点经过arr[1]分钟的费用，注意：当前费用不一定是最小费用
        Queue<int[]> queue = new LinkedList<>();
        //节点0入队
        queue.offer(new int[]{0, 0, cost[0][0]});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //当前节点
            int u = arr[0];
            //节点0到当前节点经过的分钟数
            int curTime = arr[1];
            //节点0到当前节点经过arr[1]分钟的费用，注意：当前费用不一定是最小费用
            int curCost = arr[2];

            //节点0到当前节点经过的分钟数大于最大分钟数，则不合法，直接进行下次循环
            if (curTime > maxTime) {
                continue;
            }

            //节点0到当前节点经过arr[1]分钟的费用大于节点0到当前节点经过arr[1]分钟的最小费用，
            //则当前节点不能作为中间节点更新节点0到其他节点经过分钟的最小费用，直接进行下次循环
            if (curCost > cost[u][curTime]) {
                continue;
            }

            //遍历节点u的邻接节点v
            for (int[] arr2 : graph.get(u)) {
                int v = arr2[0];
                int weight = arr2[1];

                //找到更小的cost[v][curTime+weight]，更新cost[v][curTime+weight]，节点v入队
                if (curTime + weight <= maxTime && curCost + passingFees[v] < cost[v][curTime + weight]) {
                    cost[v][curTime + weight] = curCost + passingFees[v];
                    queue.offer(new int[]{v, curTime + weight, cost[v][curTime + weight]});
                }
            }
        }

        //节点0到节点n-1最多经过maxTime分钟的最小费用
        int result = Integer.MAX_VALUE;

        for (int i = 0; i <= maxTime; i++) {
            result = Math.min(result, cost[n - 1][i]);
        }

        return result == Integer.MAX_VALUE ? -1 : result;
    }

    /**
     * 堆优化Dijkstra求节点0到节点n-1最多经过maxTime分钟的最小费用
     * 时间复杂度O(n*maxTime*log(n*maxTime))，空间复杂度O(m+n+n*maxTime) (m=edges.length，即图中边的个数，n=passingFees.length，即图中节点的个数)
     * (最多经过maxTime分钟，每经过1分钟，最多将n个节点入堆，最多会将n*maxTime个节点入堆)
     * (堆优化Dijkstra的时间复杂度O(mlogm)，其中m为图中边的个数，本题边的个数O(n*maxTime)，所以时间复杂度O(n*maxTime*log(n*maxTime)))
     *
     * @param maxTime
     * @param edges
     * @param passingFees
     * @return
     */
    public int minCost2(int maxTime, int[][] edges, int[] passingFees) {
        //图中节点个数
        int n = passingFees.length;
        //邻接表，无向图，arr[0]：当前节点的邻接节点，arr[1]：当前节点和邻接节点边的权值，即两节点之间需要的时间
        List<List<int[]>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            //节点u和节点v之间需要的时间
            int weight = edges[i][2];

            graph.get(u).add(new int[]{v, weight});
            graph.get(v).add(new int[]{u, weight});
        }

        //节点0到其他节点经过分钟的最小费用数组
        int[][] cost = new int[n][maxTime + 1];

        //cost初始化，初始化为int最大值表示节点0无法到节点i经过j分钟
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= maxTime; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点0到节点0经过0分钟的最小费用为passingFees[0]
        cost[0][0] = passingFees[0];

        //小根堆，arr[0]：当前节点，arr[1]：节点0到当前节点经过的分钟数，
        //arr[2]：节点0到当前节点经过arr[1]分钟的费用，注意：当前费用不一定是最小费用
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });
        //节点0入堆
        priorityQueue.offer(new int[]{0, 0, passingFees[0]});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点
            int u = arr[0];
            //节点0到当前节点经过的分钟数
            int curTime = arr[1];
            //节点0到当前节点经过arr[1]分钟的费用，注意：当前费用不一定是最小费用
            int curCost = arr[2];

            //节点0到当前节点经过的分钟数大于最大分钟数，则不合法，直接进行下次循环
            if (curTime > maxTime) {
                continue;
            }

            //节点0到当前节点经过arr[1]分钟的费用大于节点0到当前节点经过arr[1]分钟的最小费用，
            //则当前节点不能作为中间节点更新节点0到其他节点经过分钟的最小费用，直接进行下次循环
            if (curCost > cost[u][curTime]) {
                continue;
            }

            //小根堆保证第一次访问到节点n-1，则得到节点0到节点n-1最多经过maxTime分钟的最小费用，直接返回curCost
            if (u == n - 1) {
                return curCost;
            }

            //遍历节点u的邻接节点v
            for (int[] arr2 : graph.get(u)) {
                int v = arr2[0];
                int weight = arr2[1];

                //找到更小的cost[v][curTime+weight]，更新cost[v][curTime+weight]，节点v入队
                if (curTime + weight <= maxTime && curCost + passingFees[v] < cost[v][curTime + weight]) {
                    cost[v][curTime + weight] = curCost + passingFees[v];
                    priorityQueue.offer(new int[]{v, curTime + weight, cost[v][curTime + weight]});
                }
            }
        }

        //遍历结束，节点0无法到节点n-1最多经过maxTime分钟，返回-1
        return -1;
    }

    /**
     * 动态规划(Bellman-Ford)
     * dp[i][j]：节点0经过j分钟到节点i的最小费用
     * dp[i][j] = min(dp[k][j-weight]+passingFees[i]) (节点k到节点i边的权值为weight)
     * 时间复杂度O(n*maxTime+m*maxTime)，空间复杂度O(n*maxTime) (m=edges.length，即图中边的个数，n=passingFees.length，即图中节点的个数)
     *
     * @param maxTime
     * @param edges
     * @param passingFees
     * @return
     */
    public int minCost3(int maxTime, int[][] edges, int[] passingFees) {
        //图中节点个数
        int n = passingFees.length;
        //节点0到节点i经过j分钟的最小费用
        int[][] dp = new int[n][maxTime + 1];

        //dp初始化，节点0无法到节点i经过j分钟
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= maxTime; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点0到节点0经过0分钟的最小费用为passingFees[0]
        dp[0][0] = passingFees[0];

        //经过i分钟
        for (int i = 0; i <= maxTime; i++) {
            for (int j = 0; j < edges.length; j++) {
                int u = edges[j][0];
                int v = edges[j][1];
                //节点u和节点v之间需要的时间
                int weight = edges[j][2];

                //节点0经过i-weight分钟能够到节点u，才能更新节点0经过i分钟到节点v的最小费用
                if (i - weight >= 0 && dp[u][i - weight] != Integer.MAX_VALUE) {
                    dp[v][i] = Math.min(dp[v][i], dp[u][i - weight] + passingFees[v]);
                }

                //节点0经过i-weight分钟能够到节点v，才能更新节点0经过i分钟到节点u的最小费用
                if (i - weight >= 0 && dp[v][i - weight] != Integer.MAX_VALUE) {
                    dp[u][i] = Math.min(dp[u][i], dp[v][i - weight] + passingFees[u]);
                }
            }
        }

        //节点0到节点n-1最多经过maxTime分钟的最小费用
        int result = Integer.MAX_VALUE;

        for (int i = 0; i <= maxTime; i++) {
            result = Math.min(result, dp[n - 1][i]);
        }

        return result == Integer.MAX_VALUE ? -1 : result;
    }
}
