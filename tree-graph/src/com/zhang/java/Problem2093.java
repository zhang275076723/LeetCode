package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/18 08:48
 * @Author zsy
 * @Description 前往目标城市的最小费用 带限制条件的最短路径类比Problem787、Problem1293、Problem1928 bfs类比 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1293、Problem1334、Problem1368、Problem1462、Problem1514、Problem1631、Problem1786、Problem1928、Problem1976、Problem2045、Problem2203、Problem2290、Problem2473、Problem2662、Dijkstra
 * 一组公路连接 n 个城市，城市编号为从 0 到 n - 1 。
 * 输入包含一个二维数组 highways ，其中 highways[i] = [city1i, city2i, tolli] 表示有一条连接城市 city1i 和 city2i 的双向公路，
 * 允许汽车缴纳值为 tolli 的费用从  city1i 前往 city2i 或 从  city2i 前往 city1i 。
 * 另给你一个整数 discounts 表示你最多可以使用折扣的次数。
 * 你可以使用一次折扣使通过第 ith 条公路的费用降低至 tolli / 2（向下取整）。
 * 最多只可使用 discounts 次折扣， 且 每条公路最多只可使用一次折扣 。
 * 返回从城市 0 前往城市 n - 1 的 最小费用 。
 * 如果不存在从城市 0 前往城市 n - 1 的路径，返回 -1 。
 * <p>
 * 输入：n = 5, highways = [[0,1,4],[2,1,3],[1,4,11],[3,2,3],[3,4,2]], discounts = 1
 * 输出：9
 * 解释：
 * 从 0 前往 1 ，需要费用为 4 。
 * 从 1 前往 4 并使用一次折扣，需要费用为 11 / 2 = 5 。
 * 从 0 前往 4 最小费用为 4 + 5 = 9 。
 * <p>
 * 输入：n = 4, highways = [[1,3,17],[1,2,7],[3,2,5],[0,1,6],[3,0,20]], discounts = 20
 * 输出：8
 * 解释：
 * 从 0 前往 1 并使用一次折扣，需要费用为 6 / 2 = 3 。
 * 从 1 前往 2 并使用一次折扣，需要费用为 7 / 2 = 3 。
 * 从 2 前往 3 并使用一次折扣，需要费用为 5 / 2 = 2 。
 * 从 0 前往 3 最小费用为 3 + 3 + 2 = 8 。
 * <p>
 * 输入：n = 4, highways = [[0,1,3],[2,3,2]], discounts = 0
 * 输出：-1
 * 解释：
 * 不存在从 0 前往 3 的路径，所以返回 -1 。
 * <p>
 * 2 <= n <= 1000
 * 1 <= highways.length <= 1000
 * highways[i].length == 3
 * 0 <= city1i, city2i <= n - 1
 * city1i != city2i
 * 0 <= tolli <= 10^5
 * 0 <= discounts <= 500
 * 任意两个城市之间最多只有一条公路相连
 */
public class Problem2093 {
    public static void main(String[] args) {
        Problem2093 problem2093 = new Problem2093();
//        int n = 5;
//        int[][] highways = {{0, 1, 4}, {2, 1, 3}, {1, 4, 11}, {3, 2, 3}, {3, 4, 2}};
//        int discounts = 1;
        int n = 4;
        int[][] highways = {{1, 3, 17}, {1, 2, 7}, {3, 2, 5}, {0, 1, 6}, {3, 0, 20}};
        int discounts = 20;
        System.out.println(problem2093.minimumCost(n, highways, discounts));
        System.out.println(problem2093.minimumCost2(n, highways, discounts));
//        //注意：不能使用Bellman-Ford，因为状态转移方程中dp[i][j]中的j次折扣会使用到dp[u][j]，如果只使用到dp[u][j-1]，则可以使用Bellman-Ford
//        System.out.println(problem2093.minimumCost3(n, highways, discounts));
    }

    /**
     * bfs
     * 时间复杂度O((n*discounts)^2)，空间复杂度O(m+n+n*discounts) (m=highways.length，即图中边的个数，n为图中节点的个数)
     *
     * @param n
     * @param highways
     * @param discounts
     * @return
     */
    public int minimumCost(int n, int[][] highways, int discounts) {
        //邻接表，无向图，arr[0]：当前节点的邻接节点，arr[1]：当前节点和邻接节点边的权值
        List<List<int[]>> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < highways.length; i++) {
            int u = highways[i][0];
            int v = highways[i][1];
            int weight = highways[i][2];

            edges.get(u).add(new int[]{v, weight});
            edges.get(v).add(new int[]{u, weight});
        }

        //节点0到其他节点使用折扣的最小费用数组
        int[][] cost = new int[n][discounts + 1];

        //cost初始化，初始化为int最大值表示节点0无法到节点i使用j次折扣
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= discounts; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点0到节点0使用0次折扣的最小费用为0
        cost[0][0] = 0;

        //arr[0]：当前节点，arr[1]：节点0到当前节点使用的折扣次数，
        //arr[2]：节点0到当前节点使用arr[1]次折扣的费用，注意：当前费用不一定是最少费用
        Queue<int[]> queue = new LinkedList<>();
        //节点0入队
        queue.offer(new int[]{0, 0, cost[0][0]});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //当前节点
            int u = arr[0];
            //节点0到当前节点使用的折扣次数
            int curDiscount = arr[1];
            //节点0到当前节点使用arr[1]次折扣的费用，注意：当前费用不一定是最少费用
            int curCost = arr[2];

            //节点0到当前节点使用的折扣次数大于最大折扣次数，则不合法，直接进行下次循环
            if (curDiscount > discounts) {
                continue;
            }

            //节点0到当前节点使用arr[1]次折扣的费用大于节点0到当前节点使用arr[1]次折扣的最小费用，
            //则当前节点不能作为中间节点更新节点0到其他节点使用折扣的最小费用，直接进行下次循环
            if (curCost > cost[u][curDiscount]) {
                continue;
            }

            //遍历节点u的邻接节点v
            for (int i = 0; i < edges.get(u).size(); i++) {
                int v = edges.get(u).get(i)[0];
                int weight = edges.get(u).get(i)[1];

                //不使用折扣找到更小的cost[v][curDiscount]，更新cost[v][curDiscount]，节点v入队
                if (curCost + weight < cost[v][curDiscount]) {
                    cost[v][curDiscount] = curCost + weight;
                    queue.offer(new int[]{v, curDiscount, cost[v][curDiscount]});
                }

                //使用折扣找到更小的cost[v][curDiscount+1]，更新cost[v][curDiscount+1]，节点v入队
                if (curDiscount + 1 <= discounts && curCost + weight / 2 < cost[v][curDiscount + 1]) {
                    cost[v][curDiscount + 1] = curCost + weight / 2;
                    queue.offer(new int[]{v, curDiscount + 1, cost[v][curDiscount + 1]});
                }
            }
        }

        //节点0到节点n-1最多使用discounts次折扣的最小费用
        int result = Integer.MAX_VALUE;

        for (int i = 0; i <= discounts; i++) {
            result = Math.min(result, cost[n - 1][i]);
        }

        //result为int最大值，则节点0无法到节点n-1最多使用discounts次折扣，返回-1；否则返回result
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    /**
     * 堆优化Dijkstra求节点0到节点n-1最多使用discounts次折扣的最小费用
     * 时间复杂度O(n*discounts*log(n*discounts))，空间复杂度O(m+n) (m=highways.length，即图中边的个数，n为图中节点的个数)
     * (最多使用discounts次折扣，每使用1次折扣，最多将n个节点入堆，最多会将n*discounts个节点入堆)
     * (堆优化Dijkstra的时间复杂度O(mlogm)，其中m为图中边的个数，本题边的个数O(n*discounts)，所以时间复杂度O(n*discounts*log(n*discounts)))
     *
     * @param n
     * @param highways
     * @param discounts
     * @return
     */
    public int minimumCost2(int n, int[][] highways, int discounts) {
        //邻接表，无向图，arr[0]：当前节点的邻接节点，arr[1]：当前节点和邻接节点边的权值
        List<List<int[]>> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < highways.length; i++) {
            int u = highways[i][0];
            int v = highways[i][1];
            int weight = highways[i][2];

            edges.get(u).add(new int[]{v, weight});
            edges.get(v).add(new int[]{u, weight});
        }

        //节点0到其他节点使用折扣的最小费用数组
        int[][] cost = new int[n][discounts + 1];

        //cost初始化，初始化为int最大值表示节点0无法到节点i使用j次折扣
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= discounts; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，节点0到节点0使用0次折扣的最小费用为0
        cost[0][0] = 0;

        //小根堆，arr[0]：当前节点，arr[1]：节点0到当前节点使用的折扣次数，
        //arr[2]：节点0到当前节点使用arr[1]次折扣的费用，注意：当前费用不一定是最少费用
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[2] - arr2[2];
            }
        });
        //节点0入堆
        priorityQueue.offer(new int[]{0, 0, cost[0][0]});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点
            int u = arr[0];
            //节点0到当前节点使用的折扣次数
            int curDiscount = arr[1];
            //节点0到当前节点使用arr[1]次折扣的费用，注意：当前费用不一定是最少费用
            int curCost = arr[2];

            //节点0到当前节点使用的折扣次数大于最大折扣次数，则不合法，直接进行下次循环
            if (curDiscount > discounts) {
                continue;
            }

            //节点0到当前节点使用arr[1]次折扣的费用大于节点0到当前节点使用arr[1]次折扣的最小费用，
            //则当前节点不能作为中间节点更新节点0到其他节点使用折扣的最小费用，直接进行下次循环
            if (curCost > cost[u][curDiscount]) {
                continue;
            }

            //小根堆保证第一次访问到节点n-1，则得到节点0到节点n-1最多使用discounts折扣的最小费用，直接返回curCost
            if (u == n - 1) {
                return curCost;
            }

            //遍历节点u的邻接节点v
            for (int i = 0; i < edges.get(u).size(); i++) {
                int v = edges.get(u).get(i)[0];
                int weight = edges.get(u).get(i)[1];

                //不使用折扣找到更小的cost[v][curDiscount]，更新cost[v][curDiscount]，节点v入堆
                if (curCost + weight < cost[v][curDiscount]) {
                    cost[v][curDiscount] = curCost + weight;
                    priorityQueue.offer(new int[]{v, curDiscount, cost[v][curDiscount]});
                }

                //使用折扣找到更小的cost[v][curDiscount+1]，更新cost[v][curDiscount+1]，节点v入堆
                if (curDiscount + 1 <= discounts && curCost + weight / 2 < cost[v][curDiscount + 1]) {
                    cost[v][curDiscount + 1] = curCost + weight / 2;
                    priorityQueue.offer(new int[]{v, curDiscount + 1, cost[v][curDiscount + 1]});
                }
            }
        }

        //遍历结束，没有找到节点0到节点n-1最多使用discounts次折扣的最小费用，则返回-1
        return -1;
    }

    /**
     * 动态规划(Bellman-Ford)
     * 注意：不能使用Bellman-Ford，因为状态转移方程中dp[i][j]中的j次折扣会使用到dp[u][j]，如果只使用到dp[u][j-1]，则可以使用Bellman-Ford
     * dp[i][j]：节点0到节点i使用j次折扣的最小费用
     * dp[i][j] = min(dp[k][j]+weight,dp[k][j-1]+weight/2) (节点i和节点k边的权值为weight)
     * 时间复杂度O(n*discounts+m*discounts)，空间复杂度O(n*discounts) (m=highways.length，即图中边的个数，n为图中节点的个数)
     *
     * @param n
     * @param highways
     * @param discounts
     * @return
     */
    public int minimumCost3(int n, int[][] highways, int discounts) {
        //节点0到节点i使用j次折扣的最小费用
        int[][] dp = new int[n][discounts + 1];

        //dp初始化，节点0无法到节点i使用j次折扣
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= discounts; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        //dp初始化，节点0到节点0使用0次折扣的最小费用为0
        dp[0][0] = 0;

        //使用的折扣次数i
        for (int i = 0; i <= discounts; i++) {
            for (int j = 0; j < highways.length; j++) {
                int u = highways[j][0];
                int v = highways[j][1];
                int weight = highways[j][2];

                //节点0使用i次折扣能够到节点u，才能更新节点0使用i次折扣到节点v的最小费用
                if (dp[u][i] != Integer.MAX_VALUE) {
                    dp[v][i] = Math.min(dp[v][i], dp[u][i] + weight);
                }

                //节点0使用i-1次折扣能够到节点u，才能更新节点0使用i次折扣到节点v的最小费用
                if (i - 1 >= 0 && dp[u][i - 1] != Integer.MAX_VALUE) {
                    dp[v][i] = Math.min(dp[v][i], dp[u][i - 1] + weight / 2);
                }

                //节点0使用i次折扣能够到节点v，才能更新节点0使用i次折扣到节点u的最小费用
                if (dp[v][i] != Integer.MAX_VALUE) {
                    dp[u][i] = Math.min(dp[u][i], dp[v][i] + weight);
                }

                //节点0使用i-1次折扣能够到节点v，才能更新节点0使用i次折扣到节点u的最小费用
                if (i - 1 >= 0 && dp[v][i - 1] != Integer.MAX_VALUE) {
                    dp[u][i] = Math.min(dp[u][i], dp[v][i - 1] + weight / 2);
                }
            }
        }

        //节点0到节点n-1最多使用discounts次折扣的最小费用
        int result = Integer.MAX_VALUE;

        for (int i = 0; i <= discounts; i++) {
            result = Math.min(result, dp[n - 1][i]);
        }

        //result为int最大值，则节点0无法到节点n-1最多使用discounts次折扣，返回-1；否则返回result
        return result == Integer.MAX_VALUE ? -1 : result;
    }
}
