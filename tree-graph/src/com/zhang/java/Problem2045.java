package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/22 08:45
 * @Author zsy
 * @Description 到达目的地的第二短时间 bfs类比Problem847、Problem1129、Problem1368 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1334、Problem1368、Problem1462、Problem1514、Problem1786、Problem1928、Problem1976、Problem2093、Problem2203、Problem2662、Dijkstra
 * 城市用一个 双向连通 图表示，图中有 n 个节点，从 1 到 n 编号（包含 1 和 n）。
 * 图中的边用一个二维整数数组 edges 表示，其中每个 edges[i] = [ui, vi] 表示一条节点 ui 和节点 vi 之间的双向连通边。
 * 每组节点对由 最多一条 边连通，顶点不存在连接到自身的边。穿过任意一条边的时间是 time 分钟。
 * 每个节点都有一个交通信号灯，每 change 分钟改变一次，从绿色变成红色，再由红色变成绿色，循环往复。
 * 所有信号灯都 同时 改变。你可以在 任何时候 进入某个节点，但是 只能 在节点 信号灯是绿色时 才能离开。
 * 如果信号灯是  绿色 ，你 不能 在节点等待，必须离开。
 * 第二小的值 是 严格大于 最小值的所有值中最小的值。
 * 例如，[2, 3, 4] 中第二小的值是 3 ，而 [2, 2, 4] 中第二小的值是 4 。
 * 给你 n、edges、time 和 change ，返回从节点 1 到节点 n 需要的 第二短时间 。
 * 注意：
 * 你可以 任意次 穿过任意顶点，包括 1 和 n 。
 * 你可以假设在 启程时 ，所有信号灯刚刚变成 绿色 。
 * <p>
 * 输入：n = 5, edges = [[1,2],[1,3],[1,4],[3,4],[4,5]], time = 3, change = 5
 * 输出：13
 * 解释：
 * 上面的左图展现了给出的城市交通图。
 * 右图中的蓝色路径是最短时间路径。
 * 花费的时间是：
 * - 从节点 1 开始，总花费时间=0
 * - 1 -> 4：3 分钟，总花费时间=3
 * - 4 -> 5：3 分钟，总花费时间=6
 * 因此需要的最小时间是 6 分钟。
 * 右图中的红色路径是第二短时间路径。
 * - 从节点 1 开始，总花费时间=0
 * - 1 -> 3：3 分钟，总花费时间=3
 * - 3 -> 4：3 分钟，总花费时间=6
 * - 在节点 4 等待 4 分钟，总花费时间=10
 * - 4 -> 5：3 分钟，总花费时间=13
 * 因此第二短时间是 13 分钟。
 * <p>
 * 输入：n = 2, edges = [[1,2]], time = 3, change = 2
 * 输出：11
 * 解释：
 * 最短时间路径是 1 -> 2 ，总花费时间 = 3 分钟
 * 第二短时间路径是 1 -> 2 -> 1 -> 2 ，总花费时间 = 11 分钟
 * <p>
 * 2 <= n <= 10^4
 * n - 1 <= edges.length <= min(2 * 10^4, n * (n - 1) / 2)
 * edges[i].length == 2
 * 1 <= ui, vi <= n
 * ui != vi
 * 不含重复边
 * 每个节点都可以从其他节点直接或者间接到达
 * 1 <= time, change <= 10^3
 */
public class Problem2045 {
    public static void main(String[] args) {
        Problem2045 problem2045 = new Problem2045();
        int n = 5;
        int[][] edges = {{1, 2}, {1, 3}, {1, 4}, {3, 4}, {4, 5}};
        int time = 3;
        int change = 5;
        System.out.println(problem2045.secondMinimum(n, edges, time, change));
        System.out.println(problem2045.secondMinimum2(n, edges, time, change));
    }

    /**
     * bfs
     * 将图中边的权值作为1，求节点1到节点n的第二短路径长度，根据第二短路径长度得到第二短时间
     * 到达当前节点u的时间为time1，如果time1/change为偶数，则到达节点u时为绿灯，不需要等待，到达邻接节点v的时间为time1+time；
     * 如果time1/change为奇数，则到达节点u时为红灯，需要等待change-time1%change，到达邻接节点v的时间为time1+time+change-time1%change
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m=edges.length，即图中边的个数，n为图中节点的个数)
     *
     * @param n
     * @param edges
     * @param time
     * @param change
     * @return
     */
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        //邻接表，无向图
        List<List<Integer>> graph = new ArrayList<>();

        //注意节点是从1开始，所以需要多加1
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        //节点1到其他节点的最短路径数组
        int[] distance1 = new int[n + 1];
        //节点1到其他节点的第二短路径数组
        int[] distance2 = new int[n + 1];
        //图中节点的最大距离，不能初始化为int最大值，避免相加溢出
        int INF = Integer.MAX_VALUE / 2;

        //distance数组初始化，初始化为INF表示节点1无法到达节点i
        for (int i = 1; i <= n; i++) {
            distance1[i] = INF;
            distance2[i] = INF;
        }

        //初始化，节点1到节点1的最短路径长度为0
        distance1[1] = 0;

        //arr[0]：当前节点u，arr[1]：节点1到当前节点u的路径长度，注意：当前路径长度不一定是最短路径长度
        Queue<int[]> queue = new LinkedList<>();

        //节点1入队
        queue.offer(new int[]{1, 0});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //当前节点
            int u = arr[0];
            //节点1到当前节点u的路径长度，注意：当前路径长度不一定是最短路径长度
            int curDistance = arr[1];

            //curDistance大于distance2[u]，则当前节点u不能作为中间节点更新节点1到其他节点的最短路径长度和第二短路径长度，直接进行下次循环
            if (curDistance > distance2[u]) {
                continue;
            }

            //遍历节点u的邻接节点v
            for (int v : graph.get(u)) {
                //节点1到当前节点u，再到邻接节点v的路径长度小于节点1到节点v的最短路径长度，更新distance1[v]、distance2[v]，节点v入队
                if (curDistance + 1 < distance1[v]) {
                    distance2[v] = distance1[v];
                    distance1[v] = curDistance + 1;
                    queue.offer(new int[]{v, distance1[v]});
                    queue.offer(new int[]{v, distance2[v]});
                } else if (curDistance + 1 > distance1[v] && curDistance + 1 < distance2[v]) {
                    //节点1到当前节点u，再到邻接节点v的路径长度大于节点1到节点v的最短路径长度，但小于节点1到节点v的第二短路径长度，
                    //更新distance2[v]，节点v入队
                    distance2[v] = curDistance + 1;
                    queue.offer(new int[]{v, distance2[v]});
                }
            }
        }

        //节点1到节点n的第二短时间
        int secondMinTime = 0;

        //根据第二短路径长度得到第二短时间
        for (int i = 0; i < distance2[n]; i++) {
            //节点到下一个节点需要等待的时间
            int waitTime = 0;

            //到达当前节点为红灯
            if (secondMinTime / change % 2 != 0) {
                waitTime = change - secondMinTime % change;
            }

            secondMinTime = secondMinTime + time + waitTime;
        }

        return secondMinTime;
    }

    /**
     * 堆优化Dijkstra求节点1到节点n的第二短时间
     * 难点：两个节点之间边的权值不固定，需要根据到达当前节点的时间和change来判断是否需要加上等待时间
     * 到达当前节点u的时间为time1，如果time1/change为偶数，则到达节点u时为绿灯，不需要等待，到达邻接节点v的时间为time1+time；
     * 如果time1/change为奇数，则到达节点u时为红灯，需要等待change-time1%change，到达邻接节点v的时间为time1+time+change-time1%change
     * 时间复杂度O(mlogm)，空间复杂度O(m+n) (m=edges.length，即图中边的个数，n为图中节点的个数)
     *
     * @param n
     * @param edges
     * @param time
     * @param change
     * @return
     */
    public int secondMinimum2(int n, int[][] edges, int time, int change) {
        //邻接表，无向图
        List<List<Integer>> graph = new ArrayList<>();

        //注意节点是从1开始，所以需要多加1
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        //节点1到其他节点的最短时间数组
        int[] time1 = new int[n + 1];
        //节点1到其他节点的第二短时间数组
        int[] time2 = new int[n + 1];
        //图中节点到其他节点的最大时间，不能初始化为int最大值，避免相加溢出
        int INF = Integer.MAX_VALUE / 2;

        //time数组初始化，初始化为INF表示节点1无法到达节点i
        for (int i = 1; i <= n; i++) {
            time1[i] = INF;
            time2[i] = INF;
        }

        //初始化，节点1到节点1的最短时间为0
        time1[1] = 0;

        //小根堆，arr[0]：当前节点u，arr[1]：节点1到当前节点u的时间，注意：当前时间不一定是最短时间
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] - arr2[1];
            }
        });

        //节点1入队
        priorityQueue.offer(new int[]{1, 0});

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            //当前节点u
            int u = arr[0];
            //节点1到当前节点u的时间，注意：当前时间不一定是最短时间
            int curTime = arr[1];

            //curTime大于time2[u]，则当前节点u不能作为中间节点更新节点1到其他节点的最短时间和第二短时间，直接进行下次循环
            if (curTime > time2[u]) {
                continue;
            }

            //遍历节点u的邻接节点v
            for (int v : graph.get(u)) {
                //节点u到邻接节点v需要等待的时间
                int waitTime = 0;

                //到达当前节点u为红灯
                if (curTime / change % 2 != 0) {
                    waitTime = change - curTime % change;
                }

                //节点1到当前节点u，再到邻接节点v的时间小于节点1到节点v的最短时间，更新time1[v]、time2[v]，节点v入队
                if (curTime + time + waitTime < time1[v]) {
                    time2[v] = time1[v];
                    time1[v] = curTime + time + waitTime;
                    priorityQueue.offer(new int[]{v, time1[v]});
                    priorityQueue.offer(new int[]{v, time2[v]});
                } else if (curTime + time + waitTime > time1[v] && curTime + time + waitTime < time2[v]) {
                    //节点1到当前节点u，再到邻接节点v的时间大于节点1到节点v的最短时间，但小于节点1到节点v的第二短时间，
                    //更新time2[v]，节点v入队
                    time2[v] = curTime + time + waitTime;
                    priorityQueue.offer(new int[]{v, time2[v]});
                }
            }
        }

        return time2[n];
    }
}
