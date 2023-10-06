package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2023/10/10 08:49
 * @Author zsy
 * @Description 最小高度树 快手面试题 类比Problem1162 拓扑排序类比Problem207、Problem210、Problem329、IsCircleDependency 图类比
 * 树是一个无向图，其中任何两个顶点只通过一条路径连接。
 * 换句话说，一个任何没有简单环路的连通图都是一棵树。
 * 给你一棵包含 n 个节点的树，标记为 0 到 n - 1 。
 * 给定数字 n 和一个有 n - 1 条无向边的 edges 列表（每一个边都是一对标签），
 * 其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间存在一条无向边。
 * 可选择树中任何一个节点作为根。当选择节点 x 作为根节点时，设结果树的高度为 h 。
 * 在所有可能的树中，具有最小高度的树（即，min(h)）被称为 最小高度树 。
 * 请你找到所有的 最小高度树 并按 任意顺序 返回它们的根节点标签列表。
 * 树的 高度 是指根节点和叶子节点之间最长向下路径上边的数量。
 * <p>
 * 输入：n = 4, edges = [[1,0],[1,2],[1,3]]
 * 输出：[1]
 * 解释：如图所示，当根是标签为 1 的节点时，树的高度是 1 ，这是唯一的最小高度树。
 * <p>
 * 输入：n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]]
 * 输出：[3,4]
 * <p>
 * 1 <= n <= 2 * 10^4
 * edges.length == n - 1
 * 0 <= ai, bi < n
 * ai != bi
 * 所有 (ai, bi) 互不相同
 * 给定的输入 保证 是一棵树，并且 不会有重复的边
 */
public class Problem310 {
    public static void main(String[] args) {
        Problem310 problem310 = new Problem310();
        int n = 6;
        int[][] edges = {{3, 0}, {3, 1}, {3, 2}, {3, 4}, {5, 4}};
        System.out.println(problem310.findMinHeightTrees(n, edges));
        System.out.println(problem310.findMinHeightTrees2(n, edges));
    }

    /**
     * bfs(超时)
     * 对每个节点进行bfs，得到每个节点节点作为根的树的高度，得到图中最小高度对应的根节点
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) {
            return new ArrayList<Integer>() {{
                add(0);
            }};
        }

        //邻接矩阵
        int[][] graph = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    graph[i][j] = 1;
                } else {
                    graph[i][j] = -1;
                }
            }
        }

        for (int i = 0; i < edges.length; i++) {
            graph[edges[i][0]][edges[i][1]] = 1;
            graph[edges[i][1]][edges[i][0]] = 1;
        }

        List<Integer> list = new ArrayList<>();
        //图中最小高度，初始化为int最大值
        int minHeight = Integer.MAX_VALUE;

        //对每个节点进行bfs，得到每个节点节点作为根的树的高度，得到图中最小高度对应的根节点
        for (int i = 0; i < n; i++) {
            int curHeight = bfs(i, n, graph);

            //更新list和minHeight
            if (curHeight < minHeight) {
                list.clear();
                list.add(i);
                minHeight = curHeight;
            } else if (curHeight == minHeight) {
                list.add(i);
            }
        }

        return list;
    }

    /**
     * bfs拓扑排序
     * 核心思想：像剪枝一样一层层把最外层节点删除，即删除最外层度为1的节点，最后剩下的1个或2个节点即为图中最小高度对应的根节点
     * 图中最外层度为1的节点不是图中最小高度对应的根节点，图中度为1的节点入队，队列中节点出队，
     * 删除当前节点相连的边，即邻接节点的度减1，邻接节点度为1的节点入队，
     * 直至剩下1个或2个节点未访问，则剩下的这1个或2个节点为图中最小高度对应的根节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees2(int n, int[][] edges) {
        if (n == 1) {
            return new ArrayList<Integer>() {{
                add(0);
            }};
        }

        //邻接表
        List<List<Integer>> graph = new ArrayList<>(n);
        //无向图不分出度入度，统称为度
        int[] degree = new int[n];

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
            graph.get(edges[i][1]).add(edges[i][0]);
            degree[edges[i][0]]++;
            degree[edges[i][1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        //未访问到的节点的个数
        int count = n;

        //度为1的节点入队
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) {
                queue.offer(i);
            }
        }

        //未访问到的节点的个数不超过2个，则找到了图中最小高度对应的根节点
        while (count > 2) {
            //当前层需要删除的度为1的节点
            int size = queue.size();
            count = count - size;

            //不能一个节点一个节点删除度为1的节点，只能一层一层删除度为1的节点，
            //否则只能得到2个图中最小高度对应的根节点，没有考虑到只有1个图中最小高度对应的根节点的情况
            for (int i = 0; i < size; i++) {
                int u = queue.poll();

                //删除当前节点相连的边，即邻接节点的度减1，邻接节点度为1的节点入队
                for (int v : graph.get(u)) {
                    degree[v]--;

                    if (degree[v] == 1) {
                        queue.offer(v);
                    }
                }
            }
        }

        List<Integer> list = new ArrayList<>();

        //剩下的这1个或2个节点为图中最小高度对应的根节点
        while (!queue.isEmpty()) {
            int u = queue.poll();
            list.add(u);
        }

        return list;
    }

    private int bfs(int u, int n, int[][] graph) {
        int height = 0;
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        queue.offer(u);
        visited[u] = true;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int v = queue.poll();
                visited[u] = true;

                for (int j = 0; j < graph.length; j++) {
                    if (graph[v][j] != -1 && !visited[j]) {
                        queue.offer(j);
                        visited[j] = true;
                    }
                }
            }

            height++;
        }

        //高度不包含根节点，需要减1
        return height - 1;
    }
}
