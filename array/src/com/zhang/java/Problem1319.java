package com.zhang.java;

/**
 * @Date 2023/10/31 08:13
 * @Author zsy
 * @Description 连通网络的操作次数 并查集类比Problem130、Problem200、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem1998、Problem2685
 * 用以太网线缆将 n 台计算机连接成一个网络，计算机的编号从 0 到 n-1。
 * 线缆用 connections 表示，其中 connections[i] = [a, b] 连接了计算机 a 和 b。
 * 网络中的任何一台计算机都可以通过网络直接或者间接访问同一个网络中其他任意一台计算机。
 * 给你这个计算机网络的初始布线 connections，你可以拔开任意两台直连计算机之间的线缆，并用它连接一对未直连的计算机。
 * 请你计算并返回使所有计算机都连通所需的最少操作次数。
 * 如果不可能，则返回 -1 。
 * <p>
 * 输入：n = 4, connections = [[0,1],[0,2],[1,2]]
 * 输出：1
 * 解释：拔下计算机 1 和 2 之间的线缆，并将它插到计算机 1 和 3 上。
 * <p>
 * 输入：n = 6, connections = [[0,1],[0,2],[0,3],[1,2],[1,3]]
 * 输出：2
 * <p>
 * 输入：n = 6, connections = [[0,1],[0,2],[0,3],[1,2]]
 * 输出：-1
 * 解释：线缆数量不足。
 * <p>
 * 输入：n = 5, connections = [[0,1],[0,2],[3,4],[2,3]]
 * 输出：0
 * <p>
 * 1 <= n <= 10^5
 * 1 <= connections.length <= min(n*(n-1)/2, 10^5)
 * connections[i].length == 2
 * 0 <= connections[i][0], connections[i][1] < n
 * connections[i][0] != connections[i][1]
 * 没有重复的连接。
 * 两台计算机不会通过多条线缆连接。
 */
public class Problem1319 {
    public static void main(String[] args) {
        Problem1319 problem1319 = new Problem1319();
        int n = 6;
        int[][] connections = {{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}};
        System.out.println(problem1319.makeConnected(n, connections));
    }

    /**
     * 并查集
     * n个节点最少n-1条边才能连通，如果图中连通分量的个数超过1个，则移动一条边，可以连通两个连通分量，使得连通分量的个数减少1个，
     * 则只需要移动连通分量的个数减1条边，即可以使图中节点都连通
     * 时间复杂度O(m*α(n))=O(m)，空间复杂度O(n) (m=connections.length，即图中边的个数)
     * (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param n
     * @param connections
     * @return
     */
    public int makeConnected(int n, int[][] connections) {
        //n个节点最少n-1条边才能连通，少于n-1条边，则不能连通，返回-1
        if (connections.length < n - 1) {
            return -1;
        }

        UnionFind unionFind = new UnionFind(n);

        for (int i = 0; i < connections.length; i++) {
            int u = connections[i][0];
            int v = connections[i][1];
            unionFind.union(u, v);
        }

        //移动一条边，可以连通两个连通分量，使得连通分量的个数减少1个，则只需要移动连通分量的个数减1条边，即可以使图中节点都连通
        return unionFind.count - 1;
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        private int count;
        private final int[] parent;
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
