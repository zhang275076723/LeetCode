package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2024/11/12 09:02
 * @Author zsy
 * @Description T 秒后青蛙的位置 类比Problem2065 跳跃问题类比Problem45、Problem55、Problem403、Problem975、Problem1306、Problem1340、Problem1345、Problem1654、Problem1696、Problem1871、Problem2297、Problem2498、Problem2770、LCP09
 * 给你一棵由 n 个顶点组成的无向树，顶点编号从 1 到 n。青蛙从 顶点 1 开始起跳。
 * 规则如下：
 * 在一秒内，青蛙从它所在的当前顶点跳到另一个 未访问 过的顶点（如果它们直接相连）。
 * 青蛙无法跳回已经访问过的顶点。
 * 如果青蛙可以跳到多个不同顶点，那么它跳到其中任意一个顶点上的机率都相同。
 * 如果青蛙不能跳到任何未访问过的顶点上，那么它每次跳跃都会停留在原地。
 * 无向树的边用数组 edges 描述，其中 edges[i] = [ai, bi] 意味着存在一条直接连通 ai 和 bi 两个顶点的边。
 * 返回青蛙在 t 秒后位于目标顶点 target 上的概率。
 * 与实际答案相差不超过 10^-5 的结果将被视为正确答案。
 * <p>
 * 输入：n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 2, target = 4
 * 输出：0.16666666666666666
 * 解释：上图显示了青蛙的跳跃路径。
 * 青蛙从顶点 1 起跳，第 1 秒 有 1/3 的概率跳到顶点 2 ，然后第 2 秒 有 1/2 的概率跳到顶点 4，
 * 因此青蛙在 2 秒后位于顶点 4 的概率是 1/3 * 1/2 = 1/6 = 0.16666666666666666 。
 * <p>
 * 输入：n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 1, target = 7
 * 输出：0.3333333333333333
 * 解释：上图显示了青蛙的跳跃路径。
 * 青蛙从顶点 1 起跳，有 1/3 = 0.3333333333333333 的概率能够 1 秒 后跳到顶点 7 。
 * <p>
 * 1 <= n <= 100
 * edges.length == n - 1
 * edges[i].length == 2
 * 1 <= ai, bi <= n
 * 1 <= t <= 50
 * 1 <= target <= n
 */
public class Problem1377 {
    public static void main(String[] args) {
        Problem1377 problem1377 = new Problem1377();
//        int n = 7;
//        int[][] edges = {{1, 2}, {1, 3}, {1, 7}, {2, 4}, {2, 6}, {3, 5}};
//        int t = 20;
//        int target = 4;
        int n = 1;
        int[][] edges = {};
        int t = 1;
        int target = 1;
        System.out.println(problem1377.frogPosition(n, edges, t, target));
        System.out.println(problem1377.frogPosition2(n, edges, t, target));
    }

    /**
     * 图的dfs
     * 当前节点u有a个子节点，则访问节点u的子节点v的概率为1/a，子节点v有b个子节点，则当前节点u访问子节点v的子节点p的概率为1/a*b
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @param t
     * @param target
     * @return
     */
    public double frogPosition(int n, int[][] edges, int t, int target) {
        //邻接表，无向图
        List<List<Integer>> graph = new ArrayList<>();

        //因为节点从1开始，所以邻接表长度要加1
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        //因为节点从1开始，所以访问数组长度要加1
        return dfs(1, 0, target, t, new boolean[n + 1], graph);
    }

    /**
     * 图的bfs
     * 当前节点u有a个子节点，则访问节点u的子节点v的概率为1/a，子节点v有b个子节点，则当前节点u访问子节点v的子节点p的概率为1/a*b
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @param t
     * @param target
     * @return
     */
    public double frogPosition2(int n, int[][] edges, int t, int target) {
        //邻接表，无向图
        List<List<Integer>> graph = new ArrayList<>();

        //因为节点从1开始，所以邻接表长度要加1
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        //arr[0]：当前节点，arr[1]：节点1跳跃到当前节点的时间，arr[2]：节点1跳跃到当前节点的概率
        Queue<double[]> queue = new LinkedList<>();
        //因为节点从1开始，所以访问数组长度要加1
        boolean[] visited = new boolean[n + 1];

        queue.offer(new double[]{1, 0, 1});
        visited[1] = true;

        while (!queue.isEmpty()) {
            double[] arr = queue.poll();
            //当前节点u
            int u = (int) arr[0];
            //节点1跳跃到当前节点u的时间
            int curTime = (int) arr[1];
            //节点1跳跃到当前节点u的概率
            double probability = arr[2];

            //curTime大于t，则无法跳跃到节点target，直接进行下次循环
            if (curTime > t) {
                continue;
            }

            //节点u沿着节点1往下的邻接节点的个数
            int count;

            //节点1往下的邻接节点的个数不需要减1
            if (u == 1) {
                count = graph.get(u).size();
            } else {
                count = graph.get(u).size() - 1;
            }

            //遍历到节点target，并且当前时间等于t，或者当前时间小于t，但是当前节点u往下没有邻接节点，
            //则返回节点1跳跃到节点target的概率
            if (u == target && (curTime == t || count == 0)) {
                return probability;
            }

            //遍历节点u的邻接节点v
            for (int v : graph.get(u)) {
                if (visited[v]) {
                    continue;
                }

                //节点1跳跃到节点u邻接节点v的概率=节点1跳跃到节点u的概率/节点u往下的邻接节点的个数
                queue.offer(new double[]{v, curTime + 1, probability / count});
                visited[v] = true;
            }
        }

        //bfs结束，则无法遍历到节点target，返回遍历到节点target的概率为0
        return 0;
    }

    /**
     * curTime时间节点u出发跳跃到targetTime时间节点target的概率
     *
     * @param u
     * @param curTime
     * @param target
     * @param targetTime
     * @param visited
     * @param graph
     * @return
     */
    private double dfs(int u, int curTime, int target, int targetTime, boolean[] visited, List<List<Integer>> graph) {
        //curTime大于targetTime，则无法跳跃到节点target，返回0
        if (curTime > targetTime) {
            return 0;
        }

        //节点u沿着节点1往下的邻接节点的个数
        int count;

        //节点1往下的邻接节点的个数不需要减1
        if (u == 1) {
            count = graph.get(u).size();
        } else {
            count = graph.get(u).size() - 1;
        }

        //遍历到节点target，并且当前时间等于targetTime，或者当前时间小于targetTime，但是当前节点u往下没有邻接节点，
        //则curTime时间节点u出发跳跃到targetTime时间节点target的概率为1
        if (u == target && (curTime == targetTime || count == 0)) {
            return 1;
        }

        visited[u] = true;

        //curTime时间节点u出发跳跃到targetTime时间节点target的概率
        double probability = 0;

        //遍历节点u的邻接节点v
        for (int v : graph.get(u)) {
            if (visited[v]) {
                continue;
            }

            //节点u跳跃到节点target的概率=邻接节点v跳跃到节点target的概率/节点u往下的邻接节点的个数
            probability = Math.max(probability, dfs(v, curTime + 1, target, targetTime, visited, graph) / count);
        }

        return probability;
    }
}
