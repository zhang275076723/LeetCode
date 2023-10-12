package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/10/15 08:29
 * @Author zsy
 * @Description 图中的最长环 拓扑排序类比 图类比
 * 给你一个 n 个节点的 有向图 ，节点编号为 0 到 n - 1 ，其中每个节点 至多 有一条出边。
 * 图用一个大小为 n 下标从 0 开始的数组 edges 表示，节点 i 到节点 edges[i] 之间有一条有向边。
 * 如果节点 i 没有出边，那么 edges[i] == -1 。
 * 请你返回图中的 最长 环，如果没有任何环，请返回 -1 。
 * 一个环指的是起点和终点是 同一个 节点的路径。
 * <p>
 * 输入：edges = [3,3,4,2,3]
 * 输出去：3
 * 解释：图中的最长环是：2 -> 4 -> 3 -> 2 。
 * 这个环的长度为 3 ，所以返回 3 。
 * <p>
 * 输入：edges = [2,-1,3,1]
 * 输出：-1
 * 解释：图中没有任何环。
 * <p>
 * n == edges.length
 * 2 <= n <= 10^5
 * -1 <= edges[i] < n
 * edges[i] != i
 */
public class Problem2360 {
    public static void main(String[] args) {
        Problem2360 problem2360 = new Problem2360();
        int[] edges = {3, 3, 4, 2, 3};
        System.out.println(problem2360.longestCycle(edges));
    }

    /**
     * bfs拓扑排序+dfs/bfs计算环中节点的个数
     * bfs拓扑排序删除不是环的节点，dfs或bfs得到每个环中节点的个数取最大值
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param edges
     * @return
     */
    public int longestCycle(int[] edges) {
        int n = edges.length;
        //入度数组
        int[] inDegree = new int[n];

        for (int i = 0; i < n; i++) {
            if (edges[i] != -1) {
                inDegree[edges[i]]++;
            }
        }

        //存放入度为0节点的队列
        Queue<Integer> queue = new LinkedList<>();
        //节点访问数组，bfs拓扑排序之后用于判断哪些节点是环中节点
        boolean[] visited = new boolean[n];
        //bfs能够遍历到的入度为0节点个数
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            visited[u] = true;
            count++;
            //u的邻接顶点v，即存在u到v的边
            int v = edges[u];

            //节点u存在邻接节点v，v入度减1，v入度为0则入队
            if (v != -1) {
                inDegree[v]--;

                if (inDegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        //bfs遍历到了所有的节点，则不存在环，返回-1
        if (count == n) {
            return -1;
        }

        //图中最大环中节点的个数
        int max = 0;

        for (int i = 0; i < n; i++) {
            //为访问的节点即为环中的节点
            if (!visited[i]) {
//                max = Math.max(max, dfs(i, edges, visited));
                max = Math.max(max, bfs(i, edges, visited));
            }
        }

        return max;
    }

    /**
     * dfs计算环中节点的个数
     *
     * @param i
     * @param edges
     * @param visited
     * @return
     */
    private int dfs(int i, int[] edges, boolean[] visited) {
        //当前节点已经访问过，则已经找到了环中节点的个数，返回0
        if (visited[i]) {
            return 0;
        }

        int count = 1;
        visited[i] = true;

        //当前节点i存在邻接顶点，则继续沿着邻接顶点找环中节点的个数
        if (edges[i] != -1) {
            count = count + dfs(edges[i], edges, visited);
        }

        return count;
    }

    /**
     * bfs计算环中节点的个数
     *
     * @param i
     * @param edges
     * @param visited
     * @return
     */
    private int bfs(int i, int[] edges, boolean[] visited) {
        int count = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(i);

        while (!queue.isEmpty()) {
            int j = queue.poll();

            if (visited[j]) {
                continue;
            }

            visited[j] = true;
            count++;

            if (edges[j] != -1) {
                queue.offer(edges[j]);
            }
        }

        return count;
    }
}
