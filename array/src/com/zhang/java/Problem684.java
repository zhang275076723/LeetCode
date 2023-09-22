package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2023/9/22 08:32
 * @Author zsy
 * @Description 冗余连接 并查集类比P
 * 树可以看成是一个连通且 无环 的 无向 图。
 * 给定往一棵 n 个节点 (节点值 1～n) 的树中添加一条边后的图。
 * 添加的边的两个顶点包含在 1 到 n 中间，且这条附加的边不属于树中已存在的边。
 * 图的信息记录于长度为 n 的二维数组 edges ，edges[i] = [ai, bi] 表示图中在 ai 和 bi 之间存在一条边。
 * 请找出一条可以删去的边，删除后可使得剩余部分是一个有着 n 个节点的树。
 * 如果有多个答案，则返回数组 edges 中最后出现的那个。
 * <p>
 * 输入: edges = [[1,2], [1,3], [2,3]]
 * 输出: [2,3]
 * <p>
 * 输入: edges = [[1,2], [2,3], [3,4], [1,4], [1,5]]
 * 输出: [1,4]
 * <p>
 * n == edges.length
 * 3 <= n <= 1000
 * edges[i].length == 2
 * 1 <= ai < bi <= edges.length
 * ai != bi
 * edges 中无重复元素
 * 给定的图是连通的
 */
public class Problem684 {
    public static void main(String[] args) {
        Problem684 problem684 = new Problem684();
//        int[][] edges = {{1, 2}, {1, 3}, {2, 3}};
        int[][] edges = {{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}};
        System.out.println(Arrays.toString(problem684.findRedundantConnection(edges)));
    }

    /**
     * 并查集
     * 树只有n-1条边，再添加1条边就变为图，顺序遍历有n条边的edges，如果edges[i][0]和edges[i][1]已经是同一个连通分量中节点，
     * 添加当前边edges[i]，则树产生环变为图，当前边edges[i]就是需要删除的边；
     * 如果edges[i][0]和edges[i][1]不是同一个连通分量中节点，则连接这两个节点
     * 时间复杂度O(n*α(n))=O(n)，空间复杂度O(n) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param edges
     * @return
     */
    public int[] findRedundantConnection(int[][] edges) {
        //因为节点是从1开始，所以要多申请一个长度
        UnionFind unionFind = new UnionFind(edges.length + 1);

        for (int i = 0; i < edges.length; i++) {
            //当前边edges[i]的两个节点
            int u = edges[i][0];
            int v = edges[i][1];

            //当前这两个节点已经是同一个连通分量中节点，添加当前边edges[i]，则树产生环变为图，当前边edges[i]就是需要删除的冗余边
            if (unionFind.isConnected(u, v)) {
                return edges[i];
            } else {
                //当前这两个节点不是同一个连通分量中节点，则连接这两个节点
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
