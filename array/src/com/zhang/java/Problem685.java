package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2023/9/22 08:57
 * @Author zsy
 * @Description 冗余连接 II 入度出度类比Problem331、Problem1361 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1361、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem1998、Problem2685
 * 在本问题中，有根树指满足以下条件的 有向 图。
 * 该树只有一个根节点，所有其他节点都是该根节点的后继。
 * 该树除了根节点之外的每一个节点都有且只有一个父节点，而根节点没有父节点。
 * 输入一个有向图，该图由一个有着 n 个节点（节点值不重复，从 1 到 n）的树及一条附加的有向边构成。
 * 附加的边包含在 1 到 n 中的两个不同顶点间，这条附加的边不属于树中已存在的边。
 * 结果图是一个以边组成的二维数组 edges 。
 * 每个元素是一对 [ui, vi]，用以表示 有向 图中连接顶点 ui 和顶点 vi 的边，其中 ui 是 vi 的一个父节点。
 * 返回一条能删除的边，使得剩下的图是有 n 个节点的有根树。
 * 若有多个答案，返回最后出现在给定二维数组的答案。
 * <p>
 * 输入：edges = [[1,2],[1,3],[2,3]]
 * 输出：[2,3]
 * <p>
 * 输入：edges = [[1,2],[2,3],[3,4],[4,1],[1,5]]
 * 输出：[4,1]
 * <p>
 * n == edges.length
 * 3 <= n <= 1000
 * edges[i].length == 2
 * 1 <= ui, vi <= n
 */
public class Problem685 {
    public static void main(String[] args) {
        Problem685 problem685 = new Problem685();
        int[][] edges = {{1, 2}, {2, 3}, {3, 4}, {4, 2}};
        System.out.println(Arrays.toString(problem685.findRedundantDirectedConnection(edges)));
    }

    /**
     * 并查集
     * 有根树的根节点入度为0，其他节点入度为1
     * 有根树添加一条有向边形成有向图，有以下3种情况：
     * 1、存在入度为2的节点，且有向图中存在有向环，则删除有向环中指向入度为2的节点的那条边
     * 2、存在入度为2的节点，且有向图中不存在有向环，则删除指向入度为2的节点的两条边中在edges中最后出现的那条边
     * 3、不存在入度为2的节点，有向图中一定存在有向环，则按照Problem684删除有向环中在edges中最后出现的那条边
     * 时间复杂度O(n*α(n))=O(n)，空间复杂度O(n) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param edges
     * @return
     */
    public int[] findRedundantDirectedConnection(int[][] edges) {
        //因为节点是从1开始，所以要多申请一个长度
        int[] inDegree = new int[edges.length + 1];

        for (int i = 0; i < edges.length; i++) {
            inDegree[edges[i][1]]++;
        }

        //存放指向入度为2的节点的两条边
        List<int[]> list = new ArrayList<>();

        //找到指向入度为2的节点的两条边
        for (int i = 0; i < edges.length; i++) {
            if (inDegree[edges[i][1]] == 2) {
                list.add(edges[i]);
            }
        }

        //情况1、情况2，存在入度为2的节点
        if (!list.isEmpty()) {
            return findWithInDegree2(edges, list);
        } else {
            //情况3，不存在入度为2的节点
            return findWithoutInDegree2(edges);
        }
    }

    /**
     * 存在入度为2的节点的有向图中删除冗余边
     * 优先从list中选出指向入度为2的节点的两条边中后面的一条边(保证这两条边都能删除时优先删除后面的边)，删除当前边，
     * 如果有向图不存在有向环，则当前边即为要删除的冗余边；如果有向图存在有向环，则list中的另一条边即为要删除的冗余边
     * 时间复杂度O(n*α(n))=O(n)，空间复杂度O(n) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param edges
     * @param list
     * @return
     */
    private int[] findWithInDegree2(int[][] edges, List<int[]> list) {
        //因为节点是从1开始，所以要多申请一个长度
        UnionFind unionFind = new UnionFind(edges.length + 1);
        //list中存放指向入度为2的节点的两条边
        int[] edge1 = list.get(0);
        int[] edge2 = list.get(1);

        //优先删除靠后的边edge2，判断删除edge2之后，有向图是否存在有向环，
        //不存在环，则edge2即为要删除的冗余边；存在环，则edge1即为要删除的冗余边
        for (int i = 0; i < edges.length; i++) {
            //当前边是要删除的边，则直接进行下次循环
            if (edges[i] == edge2) {
                continue;
            }

            //当前边edges[i]的两个节点
            int u = edges[i][0];
            int v = edges[i][1];

            //有向图存在有向环，则list中的另一条边edge1即为要删除的冗余边
            if (unionFind.isConnected(u, v)) {
                return edge1;
            } else {
                //u、v这两个节点不是同一个连通分量中节点，则连接这两个节点
                unionFind.union(u, v);
            }
        }

        //有向图不存在有向环，则edge2即为要删除的冗余边
        return edge2;
    }

    /**
     * 不存在入度为2的节点的有向图中删除冗余边 (同Problem684)
     * 时间复杂度O(n*α(n))=O(n)，空间复杂度O(n) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param edges
     * @return
     */
    private int[] findWithoutInDegree2(int[][] edges) {
        //因为节点是从1开始，所以要多申请一个长度
        UnionFind unionFind = new UnionFind(edges.length + 1);

        for (int i = 0; i < edges.length; i++) {
            //当前边edges[i]的两个节点
            int u = edges[i][0];
            int v = edges[i][1];

            //u、v这两个节点已经是同一个连通分量中节点，添加当前边edges[i]，则树产生环变为图，当前边edges[i]就是需要删除的冗余边
            if (unionFind.isConnected(u, v)) {
                return edges[i];
            } else {
                //u、v这两个节点不是同一个连通分量中节点，则连接这两个节点
                unionFind.union(u, v);
            }
        }

        return null;
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
