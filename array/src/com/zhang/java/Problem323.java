package com.zhang.java;

/**
 * @Date 2023/9/30 08:40
 * @Author zsy
 * @Description 无向图中连通分量的数目 并查集类比Problem130、Problem200、Problem261、Problem305、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1489、Problem1584、Problem1627、Problem1905、Problem1998
 * 你有一个包含 n 个节点的图。
 * 给定一个整数 n 和一个数组 edges ，其中 edges[i] = [ai, bi] 表示图中 ai 和 bi 之间有一条边。
 * 返回 图中已连接分量的数目 。
 * <p>
 * 输入: n = 5, edges = [[0, 1], [1, 2], [3, 4]]
 * 输出: 2
 * <p>
 * 输入: n = 5, edges = [[0,1], [1,2], [2,3], [3,4]]
 * 输出:  1
 * <p>
 * 1 <= n <= 2000
 * 1 <= edges.length <= 5000
 * edges[i].length == 2
 * 0 <= ai <= bi < n
 * ai != bi
 * edges 中不会出现重复的边
 */
public class Problem323 {
    public static void main(String[] args) {
        Problem323 problem323 = new Problem323();
        int n = 5;
        int[][] edges = {{0, 1}, {1, 2}, {3, 4}};
        System.out.println(problem323.countComponents(n, edges));
    }

    /**
     * 并查集
     * 时间复杂度O(n*α(n))=O(n)，空间复杂度O(n) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param n
     * @param edges
     * @return
     */
    public int countComponents(int n, int[][] edges) {
        UnionFind unionFind = new UnionFind(n);

        for (int i = 0; i < edges.length; i++) {
            //当前边edges[i]的两个节点
            int u = edges[i][0];
            int v = edges[i][1];

            unionFind.union(u, v);
        }

        //返回并查集中连通分量的个数
        return unionFind.count;
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
