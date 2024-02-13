package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2023/10/22 08:07
 * @Author zsy
 * @Description 找到最终的安全状态 拓扑排序类比
 * 有一个有 n 个节点的有向图，节点按 0 到 n - 1 编号。
 * 图由一个 索引从 0 开始 的 2D 整数数组 graph表示，
 * graph[i]是与节点 i 相邻的节点的整数数组，这意味着从节点 i 到 graph[i]中的每个节点都有一条边。
 * 如果一个节点没有连出的有向边，则该节点是 终端节点 。
 * 如果从该节点开始的所有可能路径都通向 终端节点 ，则该节点为 安全节点 。
 * 返回一个由图中所有 安全节点 组成的数组作为答案。
 * 答案数组中的元素应当按 升序 排列。
 * <p>
 * 输入：graph = [[1,2],[2,3],[5],[0],[5],[],[]]
 * 输出：[2,4,5,6]
 * 解释：示意图如上。
 * 节点 5 和节点 6 是终端节点，因为它们都没有出边。
 * 从节点 2、4、5 和 6 开始的所有路径都指向节点 5 或 6 。
 * <p>
 * 输入：graph = [[1,2,3,4],[1,2],[3,4],[0,4],[]]
 * 输出：[4]
 * 解释:
 * 只有节点 4 是终端节点，从节点 4 开始的所有路径都通向节点 4 。
 * <p>
 * n == graph.length
 * 1 <= n <= 10^4
 * 0 <= graph[i].length <= n
 * 0 <= graph[i][j] <= n - 1
 * graph[i] 按严格递增顺序排列。
 * 图中可能包含自环。
 * 图中边的数目在范围 [1, 4 * 10^4] 内。
 */
public class Problem802 {
    public static void main(String[] args) {
        Problem802 problem802 = new Problem802();
        int[][] graph = {{1, 2}, {2, 3}, {5}, {0}, {5}, {}, {}};
//        int[][] graph = {{1, 2, 3, 4}, {1, 2}, {3, 4}, {0, 4}, {}};
        System.out.println(problem802.eventualSafeNodes(graph));
        System.out.println(problem802.eventualSafeNodes2(graph));
    }

    /**
     * dfs拓扑排序
     * 核心思想：环中的节点或能到达环中的节点，则不是安全节点；否则，则是安全节点
     * 对未访问的节点dfs，标记未访问的节点为0，正在访问的节点为1，已经访问的节点为2，如果当前节点访问标记为1，
     * 则说明图中存在环，不存在拓扑排序，当前节点访问结束，标记当前节点访问标记为2，
     * dfs结束，访问标记为1的节点，说明节点在环上，或者节点能到达环中的节点，则不是安全节点；访问标记为2的节点，则为安全节点
     * 时间复杂度O(m+n)，空间复杂度O(n)
     * (n=graph.length，即图中节点的个数，m为图中边的数量) (访问数组的空间复杂度O(n)，dfs栈的深度为O(n))
     *
     * @param graph
     * @return
     */
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        //访问数组，0-未访问，1-正在访问，2-已访问
        int[] visited = new int[n];

        for (int i = 0; i < n; i++) {
            //从未访问的节点开始dfs
            if (visited[i] == 0) {
                dfs(i, graph, visited);
            }
        }

        List<Integer> list = new ArrayList<>();

        //dfs结束，visited[i]为1，说明节点i在环上，或者节点i能到达环中的节点，则不是安全节点；visited[i]为2，则是安全节点
        for (int i = 0; i < n; i++) {
            if (visited[i] == 2) {
                list.add(i);
            }
        }

        return list;
    }

    /**
     * bfs逆拓扑排序
     * 核心思想：环中的节点或能到达环中的节点，则不是安全节点；否则，是安全节点
     * 为什么逆拓扑排序，因为图中存在环，拓扑排序入度为0的节点入队，无法遍历到所有出度为0的节点，导致不能得到所有的安全节点，
     * 而逆拓扑排序出度为0的节点入队，则可以遍历到所有出度为0的节点，可以得到所有的安全节点
     * 图中出度为0的节点入队，队列中节点出队，指向当前节点的邻接节点的出度减0，邻接节点出度为0的节点入队，
     * 遍历结束，出度为0的节点，则为安全节点
     * 时间复杂度O(m+n)，空间复杂度O(m+n)
     * (n=graph.length，即图中节点的个数，m为图中边的数量，如果使用邻接表，空间复杂度O(n^2))
     *
     * @param graph
     * @return
     */
    public List<Integer> eventualSafeNodes2(int[][] graph) {
        int n = graph.length;
        //邻接表，存储反图，用于逆拓扑排序
        List<List<Integer>> antiEdges = new ArrayList<>();
        //出度数组
        int[] outDegree = new int[n];

        for (int i = 0; i < n; i++) {
            antiEdges.add(new ArrayList<>());
        }

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                antiEdges.get(graph[i][j]).add(i);
                outDegree[i]++;
            }
        }

        //存放出度为0节点的队列
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (outDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();

            //遍历指向节点u的邻接节点v
            for (int v : antiEdges.get(u)) {
                outDegree[v]--;

                if (outDegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        List<Integer> list = new ArrayList<>();

        //遍历结束，出度为0的节点，则为安全节点
        for (int i = 0; i < n; i++) {
            if (outDegree[i] == 0) {
                list.add(i);
            }
        }

        return list;
    }

    private void dfs(int u, int[][] graph, int[] visited) {
        //当前节点u正在访问，说明有环，或者当前节点u已经访问，直接返回
        //注意：没添加visited[u] == 2，则提交超时
        if (visited[u] == 1 || visited[u] == 2) {
            return;
        }

        //当前节点u正在访问
        visited[u] = 1;

        //遍历节点u的邻接节点v
        for (int i = 0; i < graph[u].length; i++) {
            int v = graph[u][i];

            dfs(v, graph, visited);

            //遍历邻接节点v之后，visited[v]仍为1，则说明节点u在环上，或者节点u能到达环中的节点
            if (visited[v] == 1) {
                return;
            }
        }

        //当前节点u已经访问
        visited[u] = 2;
    }
}
