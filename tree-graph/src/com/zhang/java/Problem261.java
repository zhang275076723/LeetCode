package com.zhang.java;

/**
 * @Date 2023/9/30 08:16
 * @Author zsy
 * @Description 以图判树 并查集类比Problem130、Problem200、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem952、Problem1254、Problem1319、Problem1627、Problem1905、Problem1998
 * 给定编号从 0 到 n - 1 的 n 个结点。
 * 给定一个整数 n 和一个 edges 列表，其中 edges[i] = [ai, bi] 表示图中节点 ai 和 bi 之间存在一条无向边。
 * 如果这些边能够形成一个合法有效的树结构，则返回 true ，否则返回 false 。
 * <p>
 * 输入: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
 * 输出: true
 * <p>
 * 输入: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]
 * 输出: false
 * <p>
 * 1 <= n <= 2000
 * 0 <= edges.length <= 5000
 * edges[i].length == 2
 * 0 <= ai, bi < n
 * ai != bi
 * 不存在自循环或重复的边
 */
public class Problem261 {
    public static void main(String[] args) {
        Problem261 problem261 = new Problem261();
        int n = 5;
//        int[][] edges = {{0, 1}, {0, 2}, {0, 3}, {1, 4}};
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {1, 3}, {1, 4}};
        System.out.println(problem261.validTree(n, edges));
    }

    /**
     * 并查集
     * 如果edges[i][0]和edges[i][1]已经是同一个连通分量中节点，添加当前边edges[i]，则树产生环变为图；
     * 如果edges[i][0]和edges[i][1]不是同一个连通分量中节点，则连接这两个节点
     * 时间复杂度O(n*α(n))=O(n)，空间复杂度O(n) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param n
     * @param edges
     * @return
     */
    public boolean validTree(int n, int[][] edges) {
        //n个节点的树只能有n-1条边，大于n-1条边或小于n-1条边，则为图
        if (edges.length != n - 1) {
            return false;
        }

        UnionFind unionFind = new UnionFind(n);

        for (int i = 0; i < edges.length; i++) {
            //当前边edges[i]的两个节点
            int u = edges[i][0];
            int v = edges[i][1];

            //u、v两个节点已经是同一个连通分量中节点，添加当前边edges[i]，则树产生环变为图，返回false
            if (unionFind.isConnected(u, v)) {
                return false;
            } else {
                //u、v这两个节点不是同一个连通分量中节点，则连接这两个节点
                unionFind.union(u, v);
            }
        }

        //节点连接完之后，只有1个连通分量则为树，超过1个连通分量则为图
        return unionFind.count == 1;
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        //连通分量的个数
        private int count;
        //节点的父节点数组
        private final int[] parent;
        //节点的权值数组
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
