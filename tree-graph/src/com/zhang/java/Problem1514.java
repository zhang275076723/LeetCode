package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Date 2023/11/20 08:27
 * @Author zsy
 * @Description 概率最大的路径 图中最短路径类比Problem399、Problem743、Problem787、Problem882、Problem1334、Problem1368、Problem1462、Problem1786、Problem1928、Problem1976、Problem2093、Dijkstra
 * 给你一个由 n 个节点（下标从 0 开始）组成的无向加权图，该图由一个描述边的列表组成，
 * 其中 edges[i] = [a, b] 表示连接节点 a 和 b 的一条无向边，且该边遍历成功的概率为 succProb[i] 。
 * 指定两个节点分别作为起点 start 和终点 end ，请你找出从起点到终点成功概率最大的路径，并返回其成功概率。
 * 如果不存在从 start 到 end 的路径，请 返回 0 。
 * 只要答案与标准答案的误差不超过 1e-5 ，就会被视作正确答案。
 * <p>
 * 输入：n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.2], start = 0, end = 2
 * 输出：0.25000
 * 解释：从起点到终点有两条路径，其中一条的成功概率为 0.2 ，而另一条为 0.5 * 0.5 = 0.25
 * <p>
 * 输入：n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.3], start = 0, end = 2
 * 输出：0.30000
 * <p>
 * 输入：n = 3, edges = [[0,1]], succProb = [0.5], start = 0, end = 2
 * 输出：0.00000
 * 解释：节点 0 和 节点 2 之间不存在路径
 * <p>
 * 2 <= n <= 10^4
 * 0 <= start, end < n
 * start != end
 * 0 <= a, b < n
 * a != b
 * 0 <= succProb.length == edges.length <= 2*10^4
 * 0 <= succProb[i] <= 1
 * 每两个节点之间最多有一条边
 */
public class Problem1514 {
    public static void main(String[] args) {
        Problem1514 problem1514 = new Problem1514();
        int n = 3;
        int[][] edges = {{0, 1}, {1, 2}, {0, 2}};
        double[] succProb = {0.5, 0.5, 0.2};
        int start = 0;
        int end = 2;
        System.out.println(problem1514.maxProbability(n, edges, succProb, start, end));
        System.out.println(problem1514.maxProbability2(n, edges, succProb, start, end));
    }

    /**
     * Dijkstra求节点start到节点end的最大成功概率
     * 时间复杂度O(n^2+m)，空间复杂度O(m+n) (n为图中节点的个数，m为图中边的个数，m=edges.length)
     *
     * @param n
     * @param edges
     * @param succProb
     * @param start
     * @param end
     * @return
     */
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        //邻接表，无向图，pos.next：当前节点的邻接节点，pos.probability：当前节点到邻接节点的成功概率
        List<List<Pos>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            //节点u到节点v或节点v到节点u的成功概率
            double probability = succProb[i];

            graph.get(u).add(new Pos(v, probability));
            graph.get(v).add(new Pos(u, probability));
        }

        //节点start到其他节点的最大成功概率数组
        double[] probArr = new double[n];
        //节点访问数组，visited[u]为true，表示已经得到节点start到节点u的最大成功概率
        boolean[] visited = new boolean[n];

        //probArr数组初始化，初始化为0表示节点start无法到达节点i
        for (int i = 0; i < n; i++) {
            probArr[i] = 0;
        }

        //初始化，节点start到节点start的最大成功概率为1
        probArr[start] = 1;

        //每次从未访问节点中选择距离节点start最大成功概率的节点u，节点u作为中间节点更新节点start到其他节点的最大成功概率
        for (int i = 0; i < n; i++) {
            //初始化probArr数组中未访问节点中选择距离节点start最大成功概率的节点u
            int u = -1;

            //未访问节点中选择距离节点start最大成功概率的节点u
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || probArr[j] > probArr[u])) {
                    u = j;
                }
            }

            //设置节点u已访问，表示已经得到节点start到节点u的最大成功概率
            visited[u] = true;

            //已经得到节点start到节点end的最大成功概率，直接返回probArr[end]
            if (u == end) {
                return probArr[end];
            }

            //遍历节点u的邻接节点v，节点u作为中间节点更新节点start到其他节点的最大成功概率
            for (Pos pos : graph.get(u)) {
                //节点u的邻接节点v
                int v = pos.next;
                //节点u到邻接节点v的成功概率
                double probability = pos.probability;

                if (!visited[v]) {
                    probArr[v] = Math.max(probArr[v], probArr[u] * probability);
                }
            }
        }

        //遍历结束，没有找到节点0到节点end的最大成功概率，则返回0
        return 0;
    }

    /**
     * 堆优化Dijkstra求节点start到节点end的最大成功概率
     * 时间复杂度O(mlogm+n)，空间复杂度O(m+n) (n为图中节点的个数，m为图中边的个数，m=edges.length)
     *
     * @param n
     * @param edges
     * @param succProb
     * @param start
     * @param end
     * @return
     */
    public double maxProbability2(int n, int[][] edges, double[] succProb, int start, int end) {
        //邻接表，无向图，pos.next：当前节点的邻接节点，pos.probability：当前节点到邻接节点的成功概率
        List<List<Pos>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            //节点u到节点v或节点v到节点u的成功概率
            double probability = succProb[i];

            graph.get(u).add(new Pos(v, probability));
            graph.get(v).add(new Pos(u, probability));
        }

        //节点start到其他节点的最大成功概率数组
        double[] probArr = new double[n];

        //probArr数组初始化，初始化为0表示节点start无法到达节点i
        for (int i = 0; i < n; i++) {
            probArr[i] = 0;
        }

        //初始化，节点start到节点start的最大成功概率为1
        probArr[start] = 1;

        //大根堆，pos.next：当前节点，pos.probability：节点start到当前节点的成功概率
        PriorityQueue<Pos> priorityQueue = new PriorityQueue<>(new Comparator<Pos>() {
            @Override
            public int compare(Pos pos1, Pos pos2) {
                //不能写成return (int) (pos2.probability - pos1.probability);，因为相差很小的double相减有可能会舍去精度得到0，
                //导致不能正确判断两个double的大小关系
                return Double.compare(pos2.probability, pos1.probability);
            }
        });

        //节点start入堆
        priorityQueue.offer(new Pos(start, probArr[start]));

        while (!priorityQueue.isEmpty()) {
            Pos pos = priorityQueue.poll();
            //当前节点u
            int u = pos.next;
            //节点start到节点u的成功概率
            double curProb = pos.probability;

            //curProb小于probArr[u]，则当前节点u不能作为中间节点更新节点start到其他节点的最大成功概率，直接进行下次循环
            if (curProb < probArr[u]) {
                continue;
            }

            //大根堆保证第一次访问到节点end，则得到节点start到节点end的最大成功概率，直接返回curProb
            if (u == end) {
                return curProb;
            }

            //遍历节点u的邻接节点v
            for (Pos pos2 : graph.get(u)) {
                //节点u的邻接节点v
                int v = pos2.next;
                //节点u到邻接节点v的成功概率
                double probability = pos2.probability;

                //节点u作为中间节点更新节点start到其他节点的最大成功概率，更新probArr[v]，节点v入堆
                if (curProb * probability > probArr[v]) {
                    probArr[v] = curProb * probability;
                    priorityQueue.offer(new Pos(v, probArr[v]));
                }
            }

        }

        //遍历结束，没有找到节点0到节点end的最大成功概率，则返回0
        return 0;
    }

    /**
     * 邻接表节点，大根堆节点
     */
    private static class Pos {
        //邻接表节点：当前节点的邻接节点，大根堆节点：当前节点
        private int next;
        //邻接表节点：当前节点到邻接节点的成功概率，大根堆节点：节点start到当前节点的成功概率
        private double probability;

        public Pos(int next, double probability) {
            this.next = next;
            this.probability = probability;
        }
    }
}
