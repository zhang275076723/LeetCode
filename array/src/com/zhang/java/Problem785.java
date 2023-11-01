package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/4/16 08:37
 * @Author zsy
 * @Description 判断二分图 微软面试题 类比Problem886 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem1998、Problem2685 图类比Problem133、Problem207、Problem210、Problem329、Problem399、Problem863
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
     * 当前节点所有的邻接节点都在一个集合中，并且当前节点和所有的邻接节点都不在一个集合中，才是二分图
     * 标记当前节点的访问方式和前一个节点的访问方式不同，
     * 如果当前节点已被访问，并且和前一个节点的访问方式相同，则一条边上两个节点的访问方式相同，当前边的两个节点在一个集合中，无法二分，不是二分图
     * 时间复杂度O(m+n)，空间复杂度O(n) (m：图中边数，n：图中节点个数)
     *
     * @param graph
     * @return
     */
    public boolean isBipartite(int[][] graph) {
        //节点访问数组
        //visited[i]为0：节点i未访问；visited[i]为1：节点i的访问方式为1；visited[i]为2：节点i的访问方式为2
        //一条边上两个顶点的访问方式不同，则当前边可以二分
        int[] visited = new int[graph.length];

        //一条边上两个节点的访问方式相同，则当前边的两个节点属于同一个集合，无法二分，不是二分图
        for (int i = 0; i < graph.length; i++) {
            //未访问的节点进行dfs
            if (visited[i] == 0) {
                //lastVisited：节点i的上一个节点的访问方式
                //这里lastVisited不仅可以为0，也可以为1或2
                dfs(i, 0, visited, graph);
            }

            //dfs过程中不能二分，不是二分图，返回false
            if (!flag) {
                return false;
            }
        }

        //dfs遍历结束，则说明所有边都可以二分，是二分图，返回true
        return true;
    }

    /**
     * bfs
     * 当前节点所有的邻接节点都在一个集合中，并且当前节点和所有的邻接节点都不在一个集合中，才是二分图
     * 标记当前节点的访问方式和前一个节点的访问方式不同，
     * 如果当前节点已被访问，并且和前一个节点的访问方式相同，则一条边上两个节点的访问方式相同，当前边的两个节点在一个集合中，无法二分，不是二分图
     * 时间复杂度O(m+n)，空间复杂度O(n) (m：图中边数，n：图中节点个数)
     *
     * @param graph
     * @return
     */
    public boolean isBipartite2(int[][] graph) {
        //节点访问数组
        //visited[i]为0：节点i未访问；visited[i]为1：节点i的访问方式为1；visited[i]为2：节点i的访问方式为2
        //一条边上两个顶点的访问方式不同，则当前边可以二分
        int[] visited = new int[graph.length];

        //一条边上两个节点的访问方式相同，则当前边的两个节点属于同一个集合，无法二分，不是二分图
        for (int i = 0; i < graph.length; i++) {
            //未访问的节点进行bfs
            if (visited[i] == 0) {
                //bfs过程中不能二分，不是二分图，返回false
                if (!bfs(i, visited, graph)) {
                    return false;
                }
            }
        }

        //bfs遍历结束，则说明所有边都可以二分，是二分图，返回true
        return true;
    }

    /**
     * 并查集
     * 当前节点所有的邻接节点都在同一个连通分量中，并且当前节点和所有邻接节点都不在同一个连通分量中，才是二分图
     * 时间复杂度O(m+n)，空间复杂度O(n) (m：图中边的个数，n：图中节点的个数)
     *
     * @param graph
     * @return
     */
    public boolean isBipartite3(int[][] graph) {
        UnionFind unionFind = new UnionFind(graph.length);

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                //当前节点i和当前节点的邻接节点graph[i][j]连通，则无法二分，不是二分图，返回false
                if (unionFind.isConnected(i, graph[i][j])) {
                    return false;
                } else {
                    //当前节点i和当前节点的邻接节点graph[i][j]不连通，则节点graph[i][j]和节点i的邻接节点graph[i][0]相连
                    unionFind.union(graph[i][0], graph[i][j]);
                }
            }
        }

        //遍历结束，则说明所有边都可以二分，是二分图，返回true
        return true;
    }

    private void dfs(int i, int lastVisited, int[] visited, int[][] graph) {
        if (!flag) {
            return;
        }

        //当前节点已访问
        if (visited[i] != 0) {
            //当前节点的访问方式和上一个节点的访问方式相同，则当前边的两个节点属于同一个集合，
            //无法二分，不是二分图，flag设置为false
            if (visited[i] == lastVisited) {
                flag = false;
            }
        } else {
            //当前节点未访问，设置当前节点的访问方式不同于上一个节点的访问方式
            if (lastVisited == 1) {
                visited[i] = 2;
            } else {
                visited[i] = 1;
            }

            //dfs遍历当前节点i的邻接节点graph[i][j]
            for (int j = 0; j < graph[i].length; j++) {
                dfs(graph[i][j], visited[i], visited, graph);
            }
        }
    }

    private boolean bfs(int i, int[] visited, int[][] graph) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(i);
        //设置当前节点i的访问方式为1，也可以设置为2
        visited[i] = 1;

        while (!queue.isEmpty()) {
            //当前节点u
            int u = queue.poll();

            //当前节点u的邻接节点v
            for (int j = 0; j < graph[u].length; j++) {
                int v = graph[u][j];

                //邻接节点v已访问，当前节点u的访问方式和邻接节点v的访问方式相同，则当前边的两个节点属于同一个集合，
                //无法二分，不是二分图，返回false
                if (visited[v] != 0) {
                    if (visited[u] == visited[v]) {
                        return false;
                    }
                } else {
                    //邻接节点v未访问，设置邻接节点v的访问方式不同于当前节点u的访问方式
                    if (visited[u] == 1) {
                        visited[v] = 2;
                    } else {
                        visited[v] = 1;
                    }

                    queue.offer(v);
                }
            }
        }

        //bfs遍历结束，则说明所有边都可以二分，是二分图，返回true
        return true;
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
