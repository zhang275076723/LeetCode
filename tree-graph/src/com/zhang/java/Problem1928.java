package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Date 2023/11/17 08:14
 * @Author zsy
 * @Description 规定时间内到达终点的最小花费 花旗银行笔试题 带限制条件的单元最短路径类比Problem787、Problem2093 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1334、Problem1368、Problem1462、Problem1514、Problem1786、Problem1976、Problem2093、Dijkstra
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
    }

    /**
     * 堆优化Dijkstra求节点0最多经过maxTime分钟到达节点n-1的最少费用
     * 注意：本题边的权值为需要的时间，节点的权值为需要的费用；无向图Dijkstra保存当前节点的父节点，避免重复遍历
     * 时间复杂度O(n*maxTime*log(n*maxTime))，空间复杂度O(m+n+n*maxTime) (m=edges.length，即图中边的个数，n=passingFees.length，即图中节点的个数)
     * (最多经过maxTime分钟，每经过1分钟，最多将n个节点入堆，最多会将n*maxTime个节点入堆)
     * (堆优化Dijkstra的时间复杂度O(mlogm)，其中m为图中边的个数，本题边的个数O(n*maxTime)，所以时间复杂度O(n*maxTime*log(n*maxTime)))
     *
     * @param maxTime
     * @param edges
     * @param passingFees
     * @return
     */
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        //图中节点个数
        int n = passingFees.length;
        //邻接表，无向图，arr[0]：当前节点的邻接节点，arr[1]：当前节点和邻接节点边的权值，即两节点之间的分钟数
        List<List<int[]>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            //节点u和节点v边的权值，即两节点之间的分钟数
            int weight = edges[i][2];

            graph.get(u).add(new int[]{v, weight});
            graph.get(v).add(new int[]{u, weight});
        }

        //节点0到其他节点需要的最少时间数组
        //注意：本题边的权值为当前节点到邻接节点需要的时间，而不是当前节点到邻接节点需要的费用，注意和787题区别
        int[] time = new int[n];

        //time初始化，初始化为int最大值表示节点0无法到达节点i
        for (int i = 0; i < n; i++) {
            time[i] = Integer.MAX_VALUE;
        }

        //初始化，节点0到节点0需要的最少时间为0
        time[0] = 0;

        //小根堆，arr[0]：当前节点，arr[1]：节点0经过arr[2]分钟到达节点arr[0]的费用，注意：当前费用不一定是最少费用，
        //arr[2]：节点0到节点arr[0]需要的时间，arr[3]：节点arr[0]的父节点，避免重复遍历
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] - arr2[1];
            }
        });

        //起始节点0入堆
        priorityQueue.offer(new int[]{0, passingFees[0], 0, -1});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点u
            int u = arr[0];
            //节点0经过curTime分钟到达节点u的费用，注意：当前费用不一定是最少费用
            int curDistance = arr[1];
            //节点0到达节点u需要的时间
            int curTime = arr[2];
            //节点u的父节点，即无向图保存父节点，避免重复遍历
            int parent = arr[3];

            //节点0到达节点u需要的时间大于maxTime，则不合法，直接进行下次循环
            if (curTime > maxTime) {
                continue;
            }

            //小根堆保证第一次访问到节点n-1，则得到节点0最多经过maxTime分钟到达节点n-1的最少费用，直接返回curDistance
            //注意：如果使用变量保存curDistance取最小值，在小根堆遍历结束时再返回，会超时
            if (u == n - 1) {
                return curDistance;
            }

            //遍历节点u的邻接节点，节点u作为中间节点更新节点0到其他节点的最少时间
            for (int[] arr2 : graph.get(u)) {
                //节点u的邻接节点
                int v = arr2[0];
                //节点u和节点v边的权值，即节点u到节点v需要的时间
                int weight = arr2[1];

                //当前节点u的邻接节点v为节点u的父节点，则节点u到节点v的路径已经遍历过，避免重复遍历，直接进行下次循环
                if (v == parent) {
                    continue;
                }

                //找到更小的time[v]，更新time[v]，节点v入堆
                if (curTime + weight < time[v]) {
                    time[v] = curTime + weight;
                    priorityQueue.offer(new int[]{v, curDistance + passingFees[v], time[v], u});
                }
            }
        }

        //遍历结束，没有找到节点0最多经过maxTime分钟到达节点n-1的最少费用，则返回-1
        return -1;
    }

    /**
     * 动态规划(Bellman-Ford)
     * dp[i][j]：节点0经过j分钟到达节点i的最少费用
     * dp[i][j] = min(dp[k][j-weight]+passingFees[i]) (存在节点k到节点i的边，并且边的权值为weight)
     * 时间复杂度O(n*maxTime+m*maxTime)，空间复杂度O(n*maxTime) (m=edges.length，即图中边的个数，n=passingFees.length，即图中节点的个数)
     *
     * @param maxTime
     * @param edges
     * @param passingFees
     * @return
     */
    public int minCost2(int maxTime, int[][] edges, int[] passingFees) {
        //图中节点个数
        int n = passingFees.length;
        int[][] dp = new int[n][maxTime + 1];

        //dp初始化，节点0经过j分钟无法到达节点i
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= maxTime; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点0经过0分钟到达节点0的最少费用为passingFees[0]
        dp[0][0] = passingFees[0];

        //经过的分钟i
        for (int i = 1; i <= maxTime; i++) {
            //当前节点u、v
            for (int j = 0; j < edges.length; j++) {
                int u = edges[j][0];
                int v = edges[j][1];
                //节点u和节点v边的权值，即两节点之间的分钟数
                int weight = edges[j][2];

                //节点0经过i-weight分钟能够到达节点u，才能更新节点0经过i分钟到达节点v的最少费用
                if (i - weight >= 0 && dp[u][i - weight] != Integer.MAX_VALUE) {
                    dp[v][i] = Math.min(dp[v][i], dp[u][i - weight] + passingFees[v]);
                }

                //节点0经过i-weight分钟能够到达节点v，才能更新节点0经过i分钟到达节点u的最少费用
                if (i - weight >= 0 && dp[v][i - weight] != Integer.MAX_VALUE) {
                    dp[u][i] = Math.min(dp[u][i], dp[v][i - weight] + passingFees[u]);
                }
            }
        }

        //节点0最多经过maxTime分钟到达节点n-1的最少费用
        int result = Integer.MAX_VALUE;

        //最少费用为dp[n-1][1]、dp[n-1][2]、...、dp[n-1][maxTime-1]、dp[n-1][maxTime]中的最小值
        for (int i = 1; i <= maxTime; i++) {
            result = Math.min(result, dp[n - 1][i]);
        }

        //result为int最大值，则节点0最多经过maxTime分钟无法到达节点n-1，返回-1；否则返回result
        return result == Integer.MAX_VALUE ? -1 : result;
    }
}
