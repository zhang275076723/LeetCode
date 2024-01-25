package com.zhang.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Date 2023/11/7 08:08
 * @Author zsy
 * @Description 统计完全连通分量的数量 Tarjan类比Problem1192、Problem1489、Problem1568 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1361、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem1998
 * 给你一个整数 n 。现有一个包含 n 个顶点的 无向 图，顶点按从 0 到 n - 1 编号。
 * 给你一个二维整数数组 edges 其中 edges[i] = [ai, bi] 表示顶点 ai 和 bi 之间存在一条 无向 边。
 * 返回图中 完全连通分量 的数量。
 * 如果在子图中任意两个顶点之间都存在路径，并且子图中没有任何一个顶点与子图外部的顶点共享边，则称其为 连通分量 。
 * 如果连通分量中每对节点之间都存在一条边，则称其为 完全连通分量 。
 * <p>
 * 输入：n = 6, edges = [[0,1],[0,2],[1,2],[3,4]]
 * 输出：3
 * 解释：如上图所示，可以看到此图所有分量都是完全连通分量。
 * <p>
 * 输入：n = 6, edges = [[0,1],[0,2],[1,2],[3,4],[3,5]]
 * 输出：1
 * 解释：包含节点 0、1 和 2 的分量是完全连通分量，因为每对节点之间都存在一条边。
 * 包含节点 3 、4 和 5 的分量不是完全连通分量，因为节点 4 和 5 之间不存在边。
 * 因此，在图中完全连接分量的数量是 1 。
 * <p>
 * 1 <= n <= 50
 * 0 <= edges.length <= n * (n - 1) / 2
 * edges[i].length == 2
 * 0 <= ai, bi <= n - 1
 * ai != bi
 * 不存在重复的边
 */
public class Problem2685 {
    /**
     * dfs过程中第一次访问当前节点的dfn和low的初始值
     */
    private int index = 0;

    /**
     * Tarjan求完全连通分量的个数
     */
    private int count = 0;

    public static void main(String[] args) {
        Problem2685 problem2685 = new Problem2685();
        int n = 6;
//        int[][] edges = {{0, 1}, {0, 2}, {1, 2}, {3, 4}};
        int[][] edges = {{0, 1}, {0, 2}, {1, 2}, {3, 4}, {3, 5}};
        System.out.println(problem2685.countCompleteComponents(n, edges));
        System.out.println(problem2685.countCompleteComponents2(n, edges));
    }

    /**
     * 并查集
     * n个节点的连通分量中存在n*(n-1)/2条边，即任意两个节点之间都存在边，则当前连通分量为完全连通分量
     * 时间复杂度O(m*α(n)+n*α(n))=O(m+n)，空间复杂度O(n) (m=edges.length，即m为图中边的个数，n为图中节点的个数)
     * (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param n
     * @param edges
     * @return
     */
    public int countCompleteComponents(int n, int[][] edges) {
        UnionFind unionFind = new UnionFind(n);

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            unionFind.union(u, v);
        }

        //完全连通分量的个数，连通分量中任意两个节点之间都存在边，则当前连通分量为完全连通分量
        int count = 0;
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            //当前节点所在连通分量的根节点
            int rootI = unionFind.find(i);

            if (visited[rootI]) {
                continue;
            }

            visited[rootI] = true;

            //n个节点的连通分量中存在n*(n-1)/2条边，则任意两个节点之间都存在边，当前连通分量为完全连通分量
            if (unionFind.edgeCount[rootI] == unionFind.vertexCount[rootI] * (unionFind.vertexCount[rootI] - 1) / 2) {
                count++;
            }
        }

        return count;
    }

    /**
     * dfs，Tarjan(读作：ta young，https://www.cnblogs.com/nullzx/p/7968110.html)
     * 核心思想：通过Tarjan求图的强连通分量，强连通分量即为有向图的极大连通子图
     * dfn[u]：节点u第一次访问的时间戳，即节点u在dfs的访问顺序
     * low[u]：节点u不通过当前节点u到父节点的边能够访问到的祖先节点v的最小dfn[v]
     * 1、求桥边：当前节点u访问未访问的邻接节点v，更新当前节点u的low[u]=min(low[u],low[v])，
     * 如果low[v]>dfn[u]，则邻接节点v只能通过节点u访问节点u的祖先节点，则节点u和节点v的边是桥边
     * 2、求割点：当前节点u访问未访问的邻接节点v，更新当前节点u的low[u]=min(low[u],low[v])，
     * 如果low[v]>=dfn[u]，并且节点u不是第一个访问到的节点(即不是图的根节点)，则邻接节点v只能通过节点u访问节点u的祖先节点，
     * 则节点u是割点；如果节点u是第一个访问到的节点(即图的根节点)，并且节点u能够访问到2个或2个以上子节点，则节点u是割点
     * 3、求强连通分量：遍历到当前节点u，当前节点u入栈，当前节点u的邻接节点dfs结束，如果dfn[u]==low[u]，
     * 则节点u为当前强连通分量dfs过程中的第一个遍历到的节点，栈中不等于节点u的节点出栈，是同一个强连通分量中的节点
     * <p>
     * 通过Tarjan求图的强连通分量，连通分量中每个节点和其他节点都存在边，即任意两个节点之间都存在边，则当前连通分量为完全连通分量
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m=edges.length，即图中边的个数，n为图中节点的个数)
     *
     * @param n
     * @param edges
     * @return
     */
    public int countCompleteComponents2(int n, int[][] edges) {
        //邻接表，有向图
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        //节点第一次访问的时间戳，即节点在dfs的访问顺序，同时也作为节点访问数组
        int[] dfn = new int[n];
        //节点不通过当前节点到父节点的边能够访问到的祖先节点的最小dfn
        int[] low = new int[n];

        //dfn、low初始化为-1，表示节点未访问
        for (int i = 0; i < n; i++) {
            dfn[i] = -1;
            low[i] = -1;
        }

        for (int i = 0; i < n; i++) {
            if (dfn[i] == -1) {
                dfs(i,  dfn, low, graph, new Stack<>());
            }
        }

        return count;
    }

    /**
     * Tarjan求强连通分离
     * 注意：有向图不需要遍历当前节点时保存父节点parent，避免重复遍历
     *
     * @param u      节点u
     * @param dfn    节点u第一次访问的时间戳，即节点在dfs的访问顺序，同时也作为节点访问数组
     * @param low    节点u不通过当前节点u到父节点的边能够访问到的祖先节点v的最小dfn[v]
     * @param graph  邻接表，有向图
     * @param stack  存储图中节点的栈，用于找强连通分量
     */
    private void dfs(int u,int[] dfn, int[] low, List<List<Integer>> graph, Stack<Integer> stack) {
        dfn[u] = index;
        low[u] = index;
        index++;

        //当前节点入栈
        stack.push(u);

        //遍历节点u的邻接节点v
        for (int v : graph.get(u)) {
            //邻接节点v未访问
            if (dfn[v] == -1) {
                dfs(v, dfn, low, graph, stack);
                //更新节点u不通过父节点parent能够访问到的祖先节点的最小dfn[u]，
                //因为节点v为节点u未访问的邻接节点，所以取low[u]和low[v]中的较小值
                low[u] = Math.min(low[u], low[v]);
            } else {
                //邻接节点v已访问

                //更新节点u不通过父节点parent能够访问到的祖先节点的最小dfn[u]，
                //注意：这里是取low[u]和dfn[v]中的较小值
                low[u] = Math.min(low[u], dfn[v]);
            }
        }

        //节点u为当前强连通分量dfs过程中的第一个遍历到的节点，栈中不等于节点u的节点出栈，是同一个强连通分量中的节点
        if (dfn[u] == low[u]) {
            //存储强连通分量中节点的集合
            List<Integer> list = new ArrayList<>();

            while (stack.peek() != u) {
                list.add(stack.pop());
            }

            list.add(stack.pop());

            //强连通分量中节点的个数
            int n = list.size();
            //当前强连通分量是否是完全连通分量标志位，即任意两个节点之间是否都存在边
            boolean flag = true;

            for (int i = 0; i < n; i++) {
                //当前节点list.get(i)和其他n-1个节点之间不存在n-1条边，则不是完全连通分量
                if (graph.get(list.get(i)).size() != n - 1) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                count++;
            }
        }
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        private int count;
        private final int[] parent;
        private final int[] weight;
        //连通分量中节点的个数数组
        private final int[] vertexCount;
        //连通分量中边的个数数组
        private final int[] edgeCount;

        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            weight = new int[n];
            vertexCount = new int[n];
            edgeCount = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                weight[i] = 1;
                vertexCount[i] = 1;
                edgeCount[i] = 0;
            }
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (weight[rootI] < weight[rootJ]) {
                    parent[rootI] = rootJ;
                    //连通分量中节点的个数
                    vertexCount[rootJ] = vertexCount[rootI] + vertexCount[rootJ];
                    //连通分量中边的个数
                    edgeCount[rootJ] = edgeCount[rootI] + edgeCount[rootJ] + 1;
                } else if (weight[rootI] > weight[rootJ]) {
                    parent[rootJ] = rootI;
                    //连通分量中节点的个数
                    vertexCount[rootI] = vertexCount[rootI] + vertexCount[rootJ];
                    //连通分量中边的个数
                    edgeCount[rootI] = edgeCount[rootI] + edgeCount[rootJ] + 1;
                } else {
                    parent[rootJ] = rootI;
                    weight[rootI]++;
                    //连通分量中节点的个数
                    vertexCount[rootI] = vertexCount[rootI] + vertexCount[rootJ];
                    //连通分量中边的个数
                    edgeCount[rootI] = edgeCount[rootI] + edgeCount[rootJ] + 1;
                }

                count--;
            } else {
                //连通分量中边的个数
                edgeCount[rootI]++;
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
