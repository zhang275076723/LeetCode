package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/10/28 08:32
 * @Author zsy
 * @Description 找到离给定两个节点最近的节点 图类比
 * 给你一个 n 个节点的 有向图 ，节点编号为 0 到 n - 1 ，每个节点 至多 有一条出边。
 * 有向图用大小为 n 下标从 0 开始的数组 edges 表示，表示节点 i 有一条有向边指向 edges[i] 。
 * 如果节点 i 没有出边，那么 edges[i] == -1 。
 * 同时给你两个节点 node1 和 node2 。
 * 请你返回一个从 node1 和 node2 都能到达节点的编号，使节点 node1 和节点 node2 到这个节点的距离 较大值最小化。
 * 如果有多个答案，请返回 最小 的节点编号。
 * 如果答案不存在，返回 -1 。
 * 注意 edges 可能包含环。
 * <p>
 * 输入：edges = [2,2,3,-1], node1 = 0, node2 = 1
 * 输出：2
 * 解释：从节点 0 到节点 2 的距离为 1 ，从节点 1 到节点 2 的距离为 1 。
 * 两个距离的较大值为 1 。我们无法得到一个比 1 更小的较大值，所以我们返回节点 2 。
 * <p>
 * 输入：edges = [1,2,-1], node1 = 0, node2 = 2
 * 输出：2
 * 解释：节点 0 到节点 2 的距离为 2 ，节点 2 到它自己的距离为 0 。
 * 两个距离的较大值为 2 。我们无法得到一个比 2 更小的较大值，所以我们返回节点 2 。
 * <p>
 * n == edges.length
 * 2 <= n <= 10^5
 * -1 <= edges[i] < n
 * edges[i] != i
 * 0 <= node1, node2 < n
 */
public class Problem2359 {
    public static void main(String[] args) {
        Problem2359 problem2359 = new Problem2359();
        int[] edges = {2, 2, 3, -1};
        int node1 = 0;
        int node2 = 1;
        System.out.println(problem2359.closestMeetingNode(edges, node1, node2));
    }

    /**
     * dfs/bfs
     * 注意：无权图不需要Dijkstra或Floyd求最短路径，只需要dfs/bfs遍历到的节点即得到了到当前节点的距离
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param edges
     * @param node1
     * @param node2
     * @return
     */
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int n = edges.length;
        //节点node1到其他节点的距离
        int[] distance1 = new int[n];
        //节点node2到其他节点的距离
        int[] distance2 = new int[n];

        //初始化，节点node1和节点node2到节点i不可达
        for (int i = 0; i < n; i++) {
            distance1[i] = -1;
            distance2[i] = -1;
        }

        dfs(node1, 0, edges, new boolean[n], distance1);
        dfs(node2, 0, edges, new boolean[n], distance2);
//        bfs(node1, edges, distance1);
//        bfs(node2, edges, distance2);

        //node1和node2都能到达，并且距离node1和node2最近的节点，初始化为-1，即不存在这样的节点
        int result = -1;
        //节点result到node1和node2的距离中的较大值
        int minDistance = Integer.MAX_VALUE;

        //从后往前遍历保证如果存在多个答案，优先返回编号较小的节点
        for (int i = n - 1; i >= 0; i--) {
            //node1或node2无法到达节点i，直接进行下次循环
            if (distance1[i] == -1 || distance2[i] == -1) {
                continue;
            }

            int curDistance = Math.max(distance1[i], distance2[i]);

            //更新result和minDistance
            if (curDistance <= minDistance) {
                result = i;
                minDistance = curDistance;
            }
        }

        return result;
    }

    /**
     * dfs计算无权图中node节点到其他节点的距离
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param step
     * @param edges
     * @param visited
     * @param distance
     */
    private void dfs(int u, int step, int[] edges, boolean[] visited, int[] distance) {
        if (u == -1 || visited[u]) {
            return;
        }

        visited[u] = true;
        distance[u] = step;

        dfs(edges[u], step + 1, edges, visited, distance);
    }

    /**
     * bfs计算无权图中node节点到其他节点的距离
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param edges
     * @param distance
     */
    private void bfs(int u, int[] edges, int[] distance) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[edges.length];
        int step = 0;
        queue.offer(u);

        while (!queue.isEmpty()) {
            int v = queue.poll();

            if (v == -1 || visited[v]) {
                continue;
            }

            visited[v] = true;
            distance[v] = step;
            step++;

            queue.offer(edges[v]);
        }
    }
}
