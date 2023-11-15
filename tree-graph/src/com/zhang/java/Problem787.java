package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Date 2023/11/16 08:36
 * @Author zsy
 * @Description K 站中转内最便宜的航班 带限制条件的单元最短路径类比Problem1928、Problem2093 图中最短路径类比Problem399、Problem743、Problem882、Problem1334、Problem1368、Problem1462、Problem1514、Problem1631、Problem1786、Problem1928、Problem1976、Problem2045、Problem2093、Problem2203、Problem2662、Dijkstra
 * 有 n 个城市通过一些航班连接。给你一个数组 flights ，其中 flights[i] = [fromi, toi, pricei] ，
 * 表示该航班都从城市 fromi 开始，以价格 pricei 抵达 toi。
 * 现在给定所有的城市和航班，以及出发城市 src 和目的地 dst，你的任务是找到出一条最多经过 k 站中转的路线，
 * 使得从 src 到 dst 的 价格最便宜 ，并返回该价格。
 * 如果不存在这样的路线，则输出 -1。
 * <p>
 * 输入:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 1
 * 输出: 200
 * 解释:
 * 城市航班图如下
 * 从城市 0 到城市 2 在 1 站中转以内的最便宜价格是 200，如图中红色所示。
 * <p>
 * 输入:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 0
 * 输出: 500
 * 解释:
 * 城市航班图如下
 * 从城市 0 到城市 2 在 0 站中转以内的最便宜价格是 500，如图中蓝色所示。
 * <p>
 * 1 <= n <= 100
 * 0 <= flights.length <= (n * (n - 1) / 2)
 * flights[i].length == 3
 * 0 <= fromi, toi < n
 * fromi != toi
 * 1 <= pricei <= 10^4
 * 航班没有重复，且不存在自环
 * 0 <= src, dst, k < n
 * src != dst
 */
public class Problem787 {
    public static void main(String[] args) {
        Problem787 problem787 = new Problem787();
//        int n = 3;
//        int[][] flight = {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};
//        int src = 0;
//        int dst = 2;
//        int k = 2;
        int n = 5;
        int[][] flight = {{0, 1, 5}, {1, 2, 5}, {0, 3, 2}, {3, 1, 2}, {1, 4, 1}, {4, 2, 1}};
        int src = 0;
        int dst = 2;
        int k = 2;
        System.out.println(problem787.findCheapestPrice(n, flight, src, dst, k));
        System.out.println(problem787.findCheapestPrice2(n, flight, src, dst, k));
    }

    /**
     * 堆优化Dijkstra求节点src最多经过k+1个节点(经过k站中转)到达节点dst的最少费用
     * 难点：使用额外的step数组，记录节点src到其他节点的最少费用经过的节点个数，遍历到当前节点u，如果不能更新邻接节点v的distance[v]，
     * 但节点src到节点u经过的节点个数加1小于节点src到节点v的最少费用经过的节点个数，即curStep+1<step[v]，
     * 节点src经过节点u到达节点v，最后到达节点dst可能是一条更优的路径，不能遗漏这条路径
     * 时间复杂度O(nklognk)，空间复杂度O(m+n+nk) (m=flights.length，即图中边的个数，n为图中节点的个数)
     * (最多经过k+1个节点，每经过1个节点，对应n个节点，最多会将nk个节点加入堆中)
     * (堆优化Dijkstra的时间复杂度O(mlogm)，其中m为图中边的个数，本题边的个数O(nk)，所以时间复杂度O(nklognk))
     *
     * @param n
     * @param flights
     * @param src
     * @param dst
     * @param k
     * @return
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        //邻接表，有向图，arr[0]：当前节点的邻接节点，arr[1]：当前节点到邻接节点边的权值
        List<List<int[]>> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < flights.length; i++) {
            int u = flights[i][0];
            int v = flights[i][1];
            int weight = flights[i][2];

            edges.get(u).add(new int[]{v, weight});
        }

        //节点src到其他节点的最少费用数组
        int[] distance = new int[n];
        //节点src到其他节点的最少费用经过的节点个数数组
        //遍历到当前节点u，如果不能更新邻接节点v的distance[v]，
        //但节点src到节点u经过的节点个数加1小于节点src到节点v的最少费用经过的节点个数，即curStep+1<step[v]，
        //节点src经过节点u到达节点v，最后到达节点dst可能是一条更优的路径，不能遗漏这条路径
        int[] step = new int[n];

        //distance和step初始化，初始化为int最大值表示节点src无法到达节点i
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
            step[i] = Integer.MAX_VALUE;
        }

        //初始化，节点src到节点src的最少费用为0
        distance[src] = 0;
        //初始化，节点src到节点src的最少费用经过的节点个数为0
        step[src] = 0;

        //小根堆，arr[0]：当前节点，arr[1]：节点src到节点arr[0]的费用，注意：当前费用不一定是最少费用，
        //arr[2]：节点src到节点arr[0]经过的节点个数(即经过的中转次数为arr[2]-1)
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] - arr2[1];
            }
        });

        //起始节点src入堆
        priorityQueue.offer(new int[]{src, 0, 0});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点u
            int u = arr[0];
            //节点src到节点u的费用，注意：当前费用不一定是最少费用
            int curDistance = arr[1];
            //节点src到节点u经过的节点个数(即经过的中转次数为curStep-1)
            int curStep = arr[2];

            //节点src到节点u经过的节点个数curStep大于k+1(即经过的中转次数大于k)，则不合法，直接进行下次循环
            if (curStep > k + 1) {
                continue;
            }

            //小根堆保证第一次访问到节点dst，则得到节点src最多经过k+1个节点到达节点dst的最少费用，直接返回curDistance
            //注意：如果使用变量保存curDistance取最小值，在小根堆遍历结束时再返回，会超时
            if (u == dst) {
                return curDistance;
            }

            //遍历节点u的邻接节点，节点u作为中间节点更新节点src到其他节点的最少费用
            for (int[] arr2 : edges.get(u)) {
                //节点u的邻接节点
                int v = arr2[0];
                //节点u到节点v边的权值，即节点u到节点v的费用
                int weight = arr2[1];

                //找到更小的distance[v]，更新distance[v]和step[v]，节点v入堆
                if (curDistance + weight < distance[v]) {
                    distance[v] = curDistance + weight;
                    step[v] = curStep + 1;
                    priorityQueue.offer(new int[]{v, distance[v], step[v]});
                } else if (curStep + 1 < step[v]) {
                    //curDistance+weight不能更新distance[v]，
                    //但节点src到节点u经过的节点个数加1小于节点src到节点v的最少费用经过的节点个数，即curStep+1<step[v]，
                    //节点src经过节点u到达节点v，最后到达节点dst可能是一条更优的路径，不能遗漏这条路径，节点v入堆
                    priorityQueue.offer(new int[]{v, curDistance + weight, curStep + 1});
                }
            }
        }

        //遍历结束，没有找到节点src到节点dst最多经过k+1个节点(经过k站中转)的最少费用，则返回-1
        return -1;
    }

    /**
     * 动态规划(Bellman-Ford)
     * dp[i][j]：节点src经过j个节点(经过j-1站中转)到达节点i的最少费用
     * dp[i][j] = min(dp[k][j-1]+weight) (存在节点k到节点i的边，并且边的权值为weight)
     * 时间复杂度O(nk+mk)，空间复杂度O(nk) (m=flights.length，即图中边的个数，n为图中节点的个数)
     *
     * @param n
     * @param flights
     * @param src
     * @param dst
     * @param k
     * @return
     */
    public int findCheapestPrice2(int n, int[][] flights, int src, int dst, int k) {
        //经过k+1个节点(经过k站中转)，所以需要申请长度为k+2
        int[][] dp = new int[n][k + 2];

        //dp初始化，节点src经过j个节点(经过j-1站中转)无法到达节点i
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k + 1; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点src经过0个节点到达节点src的最少费用为0
        dp[src][0] = 0;

        //经过的节点个数i
        for (int i = 1; i <= k + 1; i++) {
            //当前节点u、v
            for (int j = 0; j < flights.length; j++) {
                int u = flights[j][0];
                int v = flights[j][1];
                int weight = flights[j][2];

                //节点src经过i-1个节点(经过i-2站中转)能够到达节点u，才能更新节点src经过i个节点(经过i-1站中转)到达节点v的最少费用
                if (dp[u][i - 1] != Integer.MAX_VALUE) {
                    dp[v][i] = Math.min(dp[v][i], dp[u][i - 1] + weight);
                }
            }
        }

        //节点src最多经过k+1个节点(经过k站中转)到达节点dst的最少费用
        int result = Integer.MAX_VALUE;

        //最少费用为dp[dst][1]、dp[dst][2]、...、dp[dst][k]、dp[dst][k+1]中的最小值
        for (int i = 1; i <= k + 1; i++) {
            result = Math.min(result, dp[dst][i]);
        }

        //result为int最大值，则节点src最多经过k+1个节点(经过k站中转)无法到达节点dst，返回-1；否则返回result
        return result == Integer.MAX_VALUE ? -1 : result;
    }
}
