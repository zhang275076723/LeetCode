package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/4/16 08:37
 * @Author zsy
 * @Description 判断二分图 微软面试题 图类比Problem133、Problem207、Problem210、Problem329、Problem399、Problem863 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem827、Problem952、Problem1254、Problem1627、Problem1905、Problem1998
 * 存在一个 无向图 ，图中有 n 个节点。
 * 其中每个节点都有一个介于 0 到 n - 1 之间的唯一编号。
 * 给你一个二维数组 graph ，其中 graph[u] 是一个节点数组，由节点 u 的邻接节点组成。
 * 形式上，对于 graph[u] 中的每个 v ，都存在一条位于节点 u 和节点 v 之间的无向边。
 * 该无向图同时具有以下属性：
 * 不存在自环（graph[u] 不包含 u）。
 * 不存在平行边（graph[u] 不包含重复值）。
 * 如果 v 在 graph[u] 内，那么 u 也应该在 graph[v] 内（该图是无向图）
 * 这个图可能不是连通图，也就是说两个节点 u 和 v 之间可能不存在一条连通彼此的路径。
 * 二分图 定义：如果能将一个图的节点集合分割成两个独立的子集 A 和 B ，
 * 并使图中的每一条边的两个节点一个来自 A 集合，一个来自 B 集合，就将这个图称为 二分图 。
 * 如果图是二分图，返回 true ；否则，返回 false 。
 * <p>
 * 输入：graph = [[1,2,3],[0,2],[0,1,3],[0,2]]
 * 输出：false
 * 解释：不能将节点分割成两个独立的子集，以使每条边都连通一个子集中的一个节点与另一个子集中的一个节点。
 * <p>
 * 输入：graph = [[1,3],[0,2],[1,3],[0,2]]
 * 输出：true
 * 解释：可以将节点分成两组: {0, 2} 和 {1, 3} 。
 * <p>
 * graph.length == n
 * 1 <= n <= 100
 * 0 <= graph[u].length < n
 * 0 <= graph[u][i] <= n - 1
 * graph[u] 不会包含 u
 * graph[u] 的所有值 互不相同
 * 如果 graph[u] 包含 v，那么 graph[v] 也会包含 u
 */
public class Problem785 {
    /**
     * dfs中图是否是二分图
     */
    private boolean flag = true;

    public static void main(String[] args) {
        Problem785 problem785 = new Problem785();
        int[][] graph = {{1, 2, 3}, {0, 2}, {0, 1, 3}, {0, 2}};
//        int[][] graph = {{1, 3}, {0, 2}, {1, 3}, {0, 2}};
        System.out.println(problem785.isBipartite(graph));
        System.out.println(problem785.isBipartite2(graph));
        System.out.println(problem785.isBipartite3(graph));
    }

    /**
     * dfs
     * 当前节点的所有邻接节点都在一个集合中，且和当前节点不在一个集合中，才是二分图
     * 标记当前节点的访问方式和前一个节点的访问方式不同，
     * 如果一条边上两个节点的访问方式相同，则当前边的两个节点在一个集合中，无法二分，不是二分图
     * 时间复杂度O(m+n)，空间复杂度O(n) (m：图中边数，n：图中节点个数)
     *
     * @param graph
     * @return
     */
    public boolean isBipartite(int[][] graph) {
        //节点访问数组，0-未访问，1-访问方式1，2-访问方式2，使用1和2保证一条边的两个顶点属于不同的集合中
        int[] visited = new int[graph.length];

        //如果一条边上两个节点的访问方式相同，则当前边的两个节点无法二分，不是二分图
        for (int i = 0; i < graph.length; i++) {
            //dfs未访问的节点
            if (visited[i] == 0) {
                dfs(i, 0, visited, graph);
            }
            if (!flag) {
                return false;
            }
        }

        return true;
    }

    /**
     * bfs
     * 当前节点的所有邻接节点都在一个集合中，且和当前节点不在一个集合中，才是二分图
     * 标记当前节点的访问方式和前一个节点的访问方式不同，
     * 如果一条边上两个节点的访问方式相同，则当前边的两个节点在一个集合中，无法二分，不是二分图
     * 时间复杂度O(m+n)，空间复杂度O(n) (m：图中边数，n：图中节点个数)
     *
     * @param graph
     * @return
     */
    public boolean isBipartite2(int[][] graph) {
        //节点访问数组，0-未访问，1-访问方式1，2-访问方式2，使用1和2保证一条边的两个顶点属于不同的集合中
        int[] visited = new int[graph.length];
        Queue<Integer> queue = new LinkedList<>();

        //如果一条边上两个节点的访问方式相同，则当前边的两个节点无法二分，不是二分图
        for (int i = 0; i < graph.length; i++) {
            //当前节点已被访问，直接进行下次循环
            if (visited[i] != 0) {
                continue;
            }

            //当前未访问的节点入队
            queue.offer(i);
            //设置当前节点i的访问方式为1
            visited[i] = 1;

            while (!queue.isEmpty()) {
                //当期节点u
                int u = queue.poll();

                for (int j = 0; j < graph[u].length; j++) {
                    //邻接节点graph[u][j]未访问，则赋值邻接节点的访问方式不同于当前节点的访问方式
                    if (visited[graph[u][j]] == 0) {
                        if (visited[u] == 1) {
                            queue.offer(graph[u][j]);
                            visited[graph[u][j]] = 2;
                        } else {
                            queue.offer(graph[u][j]);
                            visited[graph[u][j]] = 1;
                        }
                    } else {
                        //邻接节点graph[u][j]已访问，并且当前节点的访问方式和邻接节点的访问方式相同，则当前边的两个节点无法二分，
                        //不是二分图，直接返回false
                        if (visited[u] == visited[graph[u][j]]) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * 并查集
     * 当前节点的所有邻接节点都在一个集合中，且和当前节点不在一个集合中，才是二分图
     * 时间复杂度O(m+n)，空间复杂度O(n) (m：图中边数，n：图中节点个数)
     *
     * @param graph
     * @return
     */
    public boolean isBipartite3(int[][] graph) {
        UnionFind unionFind = new UnionFind(graph.length);

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                //当前节点i和当前节点的邻接节点graph[i][j]连通，是一个连通分量，则当前边无法二分，不是二分图，直接返回false
                if (unionFind.isConnected(i, graph[i][j])) {
                    return false;
                } else {
                    //当前节点i的邻接节点graph[i][j]相连，是一个连通分量，能和当前节点二分
                    unionFind.union(graph[i][0], graph[i][j]);
                }
            }
        }

        //遍历完也不存在一条边上两个节点连通，则说明每个边都可以二分，是二分图，返回true
        return true;
    }

    private void dfs(int i, int lastVisited, int[] visited, int[][] graph) {
        if (!flag) {
            return;
        }

        //当前节点未访问，则赋值当前节点的访问方式不同于上一个节点的访问方式
        if (visited[i] == 0) {
            if (lastVisited == 1) {
                visited[i] = 2;
            } else {
                visited[i] = 1;
            }
        } else {
            //当前节点已访问，并且当前节点的访问方式和上一个节点的访问方式相同，则当前边的两个节点无法二分，
            //不是二分图，flag设置为false，直接返回
            if (visited[i] == lastVisited) {
                flag = false;
                return;
            } else {
                //当前节点，并且当前节点的访问方式和上一个节点的访问方式不同，则当前边的两个节点可以二分，直接返回
                return;
            }
        }

        //dfs遍历当前节点i的邻接节点graph[i][j]
        for (int j = 0; j < graph[i].length; j++) {
            dfs(graph[i][j], visited[i], visited, graph);
        }
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        //并查集中连通分量的个数
        private int count;
        //节点的父节点索引下标数组
        private final int[] parent;
        //节点的权值数组(节点的高度)，只有一个节点的权值为1
        private final int[] weight;

        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            weight = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                weight[i] = 1;
            }
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (weight[rootI] < weight[rootJ]) {
                    parent[rootI] = rootJ;
                } else if (weight[rootI] > weight[rootJ]) {
                    parent[rootJ] = rootI;
                } else {
                    parent[rootJ] = rootI;
                    weight[rootI]++;
                }

                //两个连通分量合并，并查集中连通分量的个数减1
                count--;
            }
        }

        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }

            return parent[i];
        }

        public boolean isConnected(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            return rootI == rootJ;
        }
    }
}
