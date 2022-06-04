package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/4 10:27
 * @Author zsy
 * @Description 除法求值 类比Problem200(并查集)
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

        //邻接表形式的有向图
        double[][] graph = buildGraph(equations, values, map);
        double[] result = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            String str1 = queries.get(i).get(0);
            String str2 = queries.get(i).get(1);

            if (!map.containsKey(str1) || !map.containsKey(str2)) {
                result[i] = -1.0;
                continue;
            }

            int start = map.get(str1);
            int end = map.get(str2);
            result[i] = dfs(graph, start, end, new boolean[count], 1.0);

            //更新有向图，便于下次查询
            if (result[i] != -1.0) {
                graph[start][end] = result[i];
                graph[end][start] = 1.0 / result[i];
            }
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

        //邻接表形式的有向图
        double[][] graph = buildGraph(equations, values, map);
        double[] result = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            String str1 = queries.get(i).get(0);
            String str2 = queries.get(i).get(1);

            if (!map.containsKey(str1) || !map.containsKey(str2)) {
                result[i] = -1.0;
                continue;
            }

            int start = map.get(str1);
            int end = map.get(str2);
            result[i] = bfs(graph, start, end, new boolean[count]);

            //更新有向图，便于下次查询
            if (result[i] != -1.0) {
                graph[start][end] = result[i];
                graph[end][start] = 1.0 / result[i];
            }
        }

        return result;
    }

    /**
     * Floyd，计算任意两个节点之间的距离
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

        //邻接表形式的有向图
        double[][] graph = buildGraph(equations, values, map);
        double[] result = new double[queries.size()];

        //Floyd，计算任意两个节点之间的距离
        for (int k = 0; k < graph.length; k++) {
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph.length; j++) {
                    if (graph[i][k] > 0 && graph[k][j] > 0) {
                        graph[i][j] = graph[i][k] * graph[k][j];
                    }
                }
            }
        }

        for (int i = 0; i < queries.size(); i++) {
            String str1 = queries.get(i).get(0);
            String str2 = queries.get(i).get(1);

            if (!map.containsKey(str1) || !map.containsKey(str2)) {
                result[i] = -1.0;
                continue;
            }

            int start = map.get(str1);
            int end = map.get(str2);
            result[i] = graph[start][end];
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
        double[] result = new double[queries.size()];

        //创建并查集
        for (int i = 0; i < values.length; i++) {
            String str1 = equations.get(i).get(0);
            String str2 = equations.get(i).get(1);

            unionFind.union(map.get(str1), map.get(str2), values[i]);
        }

        //查询并查集
        for (int i = 0; i < queries.size(); i++) {
            String str1 = queries.get(i).get(0);
            String str2 = queries.get(i).get(1);

            if (!map.containsKey(str1) || !map.containsKey(str2)) {
                result[i] = -1.0;
                continue;
            }

            result[i] = unionFind.isConnected(map.get(str1), map.get(str2));
        }

        return result;
    }

    /**
     * @param graph   邻接表
     * @param start   起始节点在map中的索引
     * @param end     最终节点在map中的索引
     * @param visited 访问数组
     * @param result  结果
     * @return
     */
    private double dfs(double[][] graph, int start, int end, boolean[] visited, double result) {
        if (start == end) {
            return result;
        }

        visited[start] = true;

        for (int i = 0; i < graph.length; i++) {
            //找第i个元素未被访问的邻接顶点
            if (graph[start][i] != -1.0 && !visited[i]) {
                double temp = dfs(graph, i, end, visited, result * graph[start][i]);
                if (temp != -1.0) {
                    return temp;
                }
            }
        }

        return -1.0;
    }

    /**
     * bfs
     *
     * @param graph   邻接表
     * @param start   起始节点在map中的索引
     * @param end     最终节点在map中的索引
     * @param visited 访问数组
     * @return
     */
    private double bfs(double[][] graph, int start, int end, boolean[] visited) {
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(start, start, 1.0));
        visited[start] = true;

        while (!queue.isEmpty()) {
            Pair pair = queue.poll();

            if (pair.end == end) {
                return pair.result;
            }

            //找当前节点未被访问的临界顶点
            for (int i = 0; i < graph.length; i++) {
                if (graph[pair.end][i] != -1.0 && !visited[i]) {
                    queue.offer(new Pair(start, i, pair.result * graph[pair.end][i]));
                    visited[i] = true;
                }
            }
        }

        return -1.0;
    }

    /**
     * 建立邻接表形式的有向图
     * graph[i][j]；第i个元素ci和第j个元素cj相除，ci/cj表示的值
     *
     * @param equations
     * @param values
     * @param map
     * @return
     */
    private double[][] buildGraph(List<List<String>> equations, double[] values, Map<String, Integer> map) {
        int count = map.size();

        //临界矩阵表示图
        double[][] graph = new double[count][count];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                graph[i][j] = -1.0;
            }
        }

        for (int i = 0; i < count; i++) {
            graph[i][i] = 1.0;
        }

        for (int index = 0; index < values.length; index++) {
            String str1 = equations.get(index).get(0);
            String str2 = equations.get(index).get(1);

            int i = map.get(str1);
            int j = map.get(str2);
            graph[i][j] = values[index];
            graph[j][i] = 1.0 / values[index];
        }

        return graph;
    }

    /**
     * bfs中队列存储的节点
     */
    private static class Pair {
        /**
         * 起始节点在map中的索引
         */
        int start;

        /**
         * 最终节点在map中的索引
         */
        int end;

        /**
         * start到end的结果值
         */
        double result;

        Pair(int start, int end, double result) {
            this.start = start;
            this.end = end;
            this.result = result;
        }
    }

    /**
     * 并查集(不相交数据集)类
     */
    private static class UnionFind {
        /**
         * 当前位置节点对应的父节点
         */
        int[] parent;

        /**
         * 当前位置节点指向父节点的权值
         */
        private double[] weight;

        public UnionFind (int n) {
            parent = new int[n];
            weight = new double[n];

            for (int i = 0; i < n; i++) {
                //当前位置节点的父节点是它本身
                parent[i] = i;
                //当前位置节点的权值为1.0
                weight[i] = 1.0;
            }
        }

        /**
         * 路径压缩，使每个节点直接指向根节点，并更新每个节点的权值
         *
         * @param i
         * @return
         */
        public int find(int i) {
            //当前位置节点的父节点不是他本身，则当前节点的父节点指向父节点的父节点
            if (parent[i] != i) {
                int originParent = parent[i];
                parent[i] = find(parent[i]);
                weight[i] = weight[i] * weight[originParent];
            }
            return parent[i];
        }

        /**
         * 合并，将x的根节点指向y的根节点，并更新x的根节点的权值
         *              ?
         *       x ------------>  y
         *     /\               /\
         *    / weight[x]      / weight[y]
         *   /                /
         *  /     value      /
         * a —————————————> c
         *
         * @param x
         * @param y
         * @param value
         */
        public void union(int x, int y, double value) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                //x的根节点指向y的根节点
                parent[rootX] = rootY;
                //更新x根节点的权值
                weight[rootX] = weight[y] * value / weight[x];
            }
        }

        /**
         * 获取节点x到节点y的权值，x/y
         *
         * @param x
         * @param y
         * @return
         */
        public double isConnected(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            //x和y相交，范围x/y
            if (rootX == rootY) {
                return weight[x] / weight[y];
            }

            //x和y不相交，返回-1.0
            return -1.0;
        }
    }
}
