package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/4 10:27
 * @Author zsy
 * @Description 除法求值 图类比Problem133、Problem207、Problem210、Problem329、Problem785、Problem863 图求最短路径类比Dijkstra 并查集类比Problem130、Problem200、Problem695、Problem765、Problem785、Problem827
 * 给你一个变量对数组 equations 和一个实数值数组 values 作为已知条件，
 * 其中 equations[i] = [Ai, Bi] 和 values[i] 共同表示等式 Ai / Bi = values[i] 。
 * 每个 Ai 或 Bi 是一个表示单个变量的字符串。
 * 另有一些以数组 queries 表示的问题，其中 queries[j] = [Cj, Dj] 表示第 j 个问题，
 * 请你根据已知条件找出 Cj / Dj = ? 的结果作为答案。
 * 返回 所有问题的答案 。如果存在某个无法确定的答案，则用 -1.0 替代这个答案。
 * 如果问题中出现了给定的已知条件中没有出现的字符串，也需要用 -1.0 替代这个答案。
 * 注意：输入总是有效的。你可以假设除法运算中不会出现除数为 0 的情况，且不存在任何矛盾的结果。
 * <p>
 * 输入：equations = [["a","b"],["b","c"]], values = [2.0,3.0],
 * queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
 * 输出：[6.00000,0.50000,-1.00000,1.00000,-1.00000]
 * 解释：
 * 条件：a / b = 2.0, b / c = 3.0
 * 问题：a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
 * 结果：[6.0, 0.5, -1.0, 1.0, -1.0 ]
 * <p>
 * 输入：equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0],
 * queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
 * 输出：[3.75000,0.40000,5.00000,0.20000]
 * <p>
 * 输入：equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a","c"],["x","y"]]
 * 输出：[0.50000,2.00000,-1.00000,-1.00000]
 * <p>
 * 1 <= equations.length <= 20
 * equations[i].length == 2
 * 1 <= Ai.length, Bi.length <= 5
 * values.length == equations.length
 * 0.0 < values[i] <= 20.0
 * 1 <= queries.length <= 20
 * queries[i].length == 2
 * 1 <= Cj.length, Dj.length <= 5
 * Ai, Bi, Cj, Dj 由小写英文字母与数字组成
 */
public class Problem399 {
    public static void main(String[] args) {
        Problem399 problem399 = new Problem399();
        List<List<String>> equations = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        list2.add("b");
        list2.add("c");
        equations.add(list1);
        equations.add(list2);
        double[] values = {2.0, 3.0};
        List<List<String>> queries = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<String> list4 = new ArrayList<>();
        List<String> list5 = new ArrayList<>();
        list1.add("a");
        list1.add("c");
        list2.add("b");
        list2.add("a");
        list3.add("a");
        list3.add("e");
        list4.add("a");
        list4.add("a");
        list5.add("x");
        list5.add("x");
        queries.add(list1);
        queries.add(list2);
        queries.add(list3);
        queries.add(list4);
        queries.add(list5);
        System.out.println(Arrays.toString(problem399.calcEquation(equations, values, queries)));
        System.out.println(Arrays.toString(problem399.calcEquation2(equations, values, queries)));
        System.out.println(Arrays.toString(problem399.calcEquation3(equations, values, queries)));
        System.out.println(Arrays.toString(problem399.calcEquation4(equations, values, queries)));
    }

    /**
     * dfs
     * 建立邻接表形式的有向图，从起始节点dfs，判断是否能遍历到最终节点，再将结果保存
     * 时间复杂度O(ML+Q(M+L))，空间复杂度O(NL+N^2) (M：边的数量，N：节点数量，Q：询问的数量，L：字符串的平均长度)
     *
     * @param equations
     * @param values
     * @param queries
     * @return
     */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        //将字符串和数字一一对应，便于建图
        Map<String, Integer> map = new HashMap<>();
        //有向图中不同元素的个数
        int count = 0;

        for (List<String> list : equations) {
            String str1 = list.get(0);
            String str2 = list.get(1);

            if (!map.containsKey(str1)) {
                map.put(str1, count);
                count++;
            }

            if (!map.containsKey(str2)) {
                map.put(str2, count);
                count++;
            }
        }

        //邻接矩阵表示的图
        double[][] edges = buildGraph(equations, values, map);
        double[] result = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            String str1 = queries.get(i).get(0);
            String str2 = queries.get(i).get(1);

            //map中不存在当前节点，直接赋值-1.0
            if (!map.containsKey(str1) || !map.containsKey(str2)) {
                result[i] = -1.0;
                continue;
            }

            int u = map.get(str1);
            int v = map.get(str2);

            //u到v已经在邻接矩阵中，直接赋值edge[u][v]
            if (edges[u][v] != -1.0) {
                result[i] = edges[u][v];
                continue;
            }

            //更新邻接矩阵，便于下次查询
            edges[u][v] = dfs(u, v, 1.0, edges, new boolean[edges.length]);
            edges[v][u] = 1.0 / edges[u][v];
            result[i] = edges[u][v];
        }

        return result;
    }

    /**
     * bfs
     * 建立邻接表形式的有向图，从起始节点bfs，判断是否能遍历到最终节点，再将结果保存
     * 时间复杂度O(ML+Q(M+L))，空间复杂度O(NL+N^2) (M：边的数量，N：节点数量，Q：询问的数量，L：字符串的平均长度)
     *
     * @param equations
     * @param values
     * @param queries
     * @return
     */
    public double[] calcEquation2(List<List<String>> equations, double[] values, List<List<String>> queries) {
        //将字符串转换成数字存储，便于建立图
        Map<String, Integer> map = new HashMap<>();
        //有向图中不同元素的个数
        int count = 0;

        for (List<String> list : equations) {
            String str1 = list.get(0);
            String str2 = list.get(1);

            if (!map.containsKey(str1)) {
                map.put(str1, count);
                count++;
            }

            if (!map.containsKey(str2)) {
                map.put(str2, count);
                count++;
            }
        }

        //邻接矩阵表示的图
        double[][] edges = buildGraph(equations, values, map);
        double[] result = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            String str1 = queries.get(i).get(0);
            String str2 = queries.get(i).get(1);

            //map中不存在当前节点，直接赋值-1.0
            if (!map.containsKey(str1) || !map.containsKey(str2)) {
                result[i] = -1.0;
                continue;
            }

            int u = map.get(str1);
            int v = map.get(str2);

            //u到v已经在邻接矩阵中，直接赋值edge[u][v]
            if (edges[u][v] != -1.0) {
                result[i] = edges[u][v];
                continue;
            }

            //更新邻接矩阵，便于下次查询
            edges[u][v] = bfs(u, v, edges);
            edges[v][u] = 1.0 / edges[u][v];
            result[i] = edges[u][v];
        }

        return result;
    }

    /**
     * Floyd，计算任意两个节点之间的最短路径
     * 注意：Floyd可以处理带负权值的图，而Dijkstra不能处理带负权值的图
     * 时间复杂度O(ML+N^3+QL)，空间复杂度O(NL+N^2) (M：边的数量，N：节点数量，Q：询问的数量，L：字符串的平均长度)
     *
     * @param equations
     * @param values
     * @param queries
     * @return
     */
    public double[] calcEquation3(List<List<String>> equations, double[] values, List<List<String>> queries) {
        //将字符串转换成数字存储，便于建立图
        Map<String, Integer> map = new HashMap<>();
        //有向图中不同元素的个数
        int count = 0;

        for (List<String> list : equations) {
            String str1 = list.get(0);
            String str2 = list.get(1);

            if (!map.containsKey(str1)) {
                map.put(str1, count);
                count++;
            }

            if (!map.containsKey(str2)) {
                map.put(str2, count);
                count++;
            }
        }

        //邻接矩阵表示的图
        double[][] edges = buildGraph(equations, values, map);
        double[] result = new double[queries.size()];

        //Floyd，计算任意两个节点之间的最近距离
        for (int k = 0; k < edges.length; k++) {
            for (int i = 0; i < edges.length; i++) {
                for (int j = 0; j < edges.length; j++) {
                    //只有在i-k和k-j存在路径时，才计算i-j的路径
                    if (edges[i][k] != -1 && edges[k][j] != -1) {
                        edges[i][j] = edges[i][k] * edges[k][j];
                    }
                }
            }
        }

        for (int i = 0; i < queries.size(); i++) {
            String str1 = queries.get(i).get(0);
            String str2 = queries.get(i).get(1);

            //map中不存在当前节点，直接赋值-1.0
            if (!map.containsKey(str1) || !map.containsKey(str2)) {
                result[i] = -1.0;
                continue;
            }

            int u = map.get(str1);
            int v = map.get(str2);

            //u到v已经计算过，在邻接矩阵中，直接赋值edge[u][v]
            result[i] = edges[u][v];
        }

        return result;
    }

    /**
     * 并查集
     * 时间复杂度O((N+Q)logA)，空间复杂度O(A) (N：equations的长度，Q：queries的长度，A：map的大小)
     * 构建并查集O(NlogA)，每一次执行合并操作的时间复杂度是O(logA)
     * 查询并查集O(QlogA)，每一次查询时执行「路径压缩」的时间复杂度是O(logA)
     *
     * @param equations
     * @param values
     * @param queries
     * @return
     */
    public double[] calcEquation4(List<List<String>> equations, double[] values, List<List<String>> queries) {
        //将字符串转换成数字存储，便于建立图
        Map<String, Integer> map = new HashMap<>();
        //有向图中不同元素的个数
        int count = 0;

        for (List<String> list : equations) {
            String str1 = list.get(0);
            String str2 = list.get(1);

            if (!map.containsKey(str1)) {
                map.put(str1, count);
                count++;
            }

            if (!map.containsKey(str2)) {
                map.put(str2, count);
                count++;
            }
        }

        UnionFind unionFind = new UnionFind(map.size());

        //创建并查集
        for (int i = 0; i < equations.size(); i++) {
            String str1 = equations.get(i).get(0);
            String str2 = equations.get(i).get(1);
            int u = map.get(str1);
            int v = map.get(str2);

            unionFind.union(u, v, values[i]);
        }

        double[] result = new double[queries.size()];

        //查询并查集
        for (int i = 0; i < queries.size(); i++) {
            String str1 = queries.get(i).get(0);
            String str2 = queries.get(i).get(1);

            //map中不存在当前节点，直接赋值-1.0
            if (!map.containsKey(str1) || !map.containsKey(str2)) {
                result[i] = -1.0;
                continue;
            }

            int u = map.get(str1);
            int v = map.get(str2);

            result[i] = unionFind.isConnected(u, v);
        }

        return result;
    }

    /**
     * @param result  结果
     * @param u       起始节点在map中的索引
     * @param v       结束节点在map中的索引
     * @param edges   邻接矩阵
     * @param visited 访问数组
     * @return
     */
    private double dfs(int u, int v, double result, double[][] edges, boolean[] visited) {
        if (u == v) {
            return result;
        }

        visited[u] = true;

        //遍历u未被访问的邻接顶点i
        for (int i = 0; i < edges[0].length; i++) {
            if (edges[u][i] != -1.0 && !visited[i]) {
                double temp = dfs(i, v, result * edges[u][i], edges, visited);

                //结果不为-1.0，则表示当前路径可达，直接返回；结果为-1.0，说明当前路径不可达，要寻找其他路径
                if (temp != -1.0) {
                    return temp;
                }
            }
        }

        visited[u] = false;

        //u到v不可达，返回-1.0
        return -1.0;
    }

    /**
     * bfs
     *
     * @param u     起始节点在map中的索引
     * @param v     结束节点在map中的索引
     * @param edges 邻接矩阵
     * @return
     */
    private double bfs(int u, int v, double[][] edges) {
        Queue<Pos> queue = new LinkedList<>();
        boolean[] visited = new boolean[edges.length];
        queue.offer(new Pos(u, u, 1.0));

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            visited[u] = true;

            //找到结束节点v，直接返回
            if (pos.v == v) {
                return pos.result;
            }

            //遍历pos.v未被访问的邻接顶点i
            for (int i = 0; i < edges[0].length; i++) {
                if (edges[pos.v][i] != -1.0 && !visited[i]) {
                    queue.offer(new Pos(pos.u, i, pos.result * edges[pos.v][i]));
                }
            }
        }

        //u到v不可达，返回-1.0
        return -1.0;
    }

    /**
     * 建立邻接矩阵形式的有向图
     * graph[i][j]；第i个元素ci和第j个元素cj相除，ci/cj表示的值
     *
     * @param equations
     * @param values
     * @param map
     * @return
     */
    private double[][] buildGraph(List<List<String>> equations, double[] values, Map<String, Integer> map) {
        //邻接矩阵表示的有向图
        double[][] edges = new double[map.size()][map.size()];

        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[0].length; j++) {
                //i到i自身为1.0
                if (i == j) {
                    edges[i][j] = 1.0;
                } else {
                    //不可达为-1.0
                    edges[i][j] = -1.0;
                }
            }
        }

        for (int i = 0; i < values.length; i++) {
            String str1 = equations.get(i).get(0);
            String str2 = equations.get(i).get(1);
            int u = map.get(str1);
            int v = map.get(str2);

            edges[u][v] = values[i];
            edges[v][u] = 1.0 / values[i];
        }

        return edges;
    }

    /**
     * bfs中队列存储的节点
     */
    private static class Pos {
        //起始节点在map中的索引
        private int u;
        //结束节点在map中的索引
        private int v;
        //u到v的结果值
        private double result;

        public Pos(int u, int v, double result) {
            this.u = u;
            this.v = v;
            this.result = result;
        }
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        //并查集中连通分量的个数
        private int count;
        //节点的父节点索引下标数组，从0开始存储
        private final int[] parent;
        //节点的权值数组，当前节点除以根节点的值
        private final double[] weight;

        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            weight = new double[n];

            for (int i = 0; i < n; i++) {
                //当前位置节点的父节点是它本身
                parent[i] = i;
                //当前位置节点的权值为1.0，因为当前节点除以根节点的值，即为当前节点除以当前节点的值
                weight[i] = 1.0;
            }
        }

        /**
         * 节点合并
         * 将节点i的根节点指向节点y的根节点，并更新节点i的根节点的权值
         * <              ?
         * <    rootI ---------> rootJ
         * <     /\               /\
         * <    / weight[i]      / weight[j]
         * <   /                /
         * <  /     value      /
         * < i —————————————> j
         *
         * @param i
         * @param j
         * @param value
         */
        public void union(int i, int j, double value) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                //节点i的根节点指向节点j的根节点
                parent[rootI] = rootJ;
                //更新节点i根节点的权值
                weight[rootI] = weight[j] * value / weight[i];
                //i、j两个连通分量合并之后，并查集中连通分量的个数减1
                count--;
            }
        }

        /**
         * 路径压缩，返回当前节点的根节点
         * 使每个节点的父节点直接指向根节点，并更新每个节点的权值
         *
         * @param i
         * @return
         */
        public int find(int i) {
            //当前节点不是根节点，则当前节点的父节点指向父节点的父节点，进行递归，最终当前节点的父节点指向根节点，
            //并更新路径中每个节点的权值，即每个节点除以根节点的值
            if (parent[i] != i) {
                //当前节点之前的父节点
                int originParent = parent[i];
                parent[i] = find(parent[i]);
                //更新当前节点的权值，为当前节点之前的权值乘上当前节点之前父节点的权值
                weight[i] = weight[i] * weight[originParent];
            }

            return parent[i];
        }

        /**
         * 判断节点i和节点j是否连通 (判断之前，需要进行路径压缩)
         * 如果连通，返回i/j；如果不连通，返回-1.0
         *
         * @param i
         * @param j
         * @return
         */
        public double isConnected(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            //i和j连通，返回节点i到根节点的权值weight[i]/节点j到根节点的权值weight[j]，即为i/j
            if (rootI == rootJ) {
                return weight[i] / weight[j];
            } else {
                //i和j不连通，返回-1.0
                return -1.0;
            }
        }
    }
}
