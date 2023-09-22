package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/7/23 08:28
 * @Author zsy
 * @Description 省份数量 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1905、Offer12 并查集类比Problem130、Problem200、Problem305、Problem399、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem952、Problem1254、Problem1627、Problem1905、Problem1998
 * 有 n 个城市，其中一些彼此相连，另一些没有相连。
 * 如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
 * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
 * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，
 * 而 isConnected[i][j] = 0 表示二者不直接相连。
 * 返回矩阵中 省份 的数量。
 * <p>
 * 输入：isConnected = [[1,1,0],[1,1,0],[0,0,1]]
 * 输出：2
 * <p>
 * 输入：isConnected = [[1,0,0],[0,1,0],[0,0,1]]
 * 输出：3
 * <p>
 * 1 <= n <= 200
 * n == isConnected.length
 * n == isConnected[i].length
 * isConnected[i][j] 为 1 或 0
 * isConnected[i][i] == 1
 * isConnected[i][j] == isConnected[j][i]
 */
public class Problem547 {
    public static void main(String[] args) {
        Problem547 problem547 = new Problem547();
        int[][] isConnected = {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}};
        System.out.println(problem547.findCircleNum(isConnected));
        System.out.println(problem547.findCircleNum2(isConnected));
        System.out.println(problem547.findCircleNum3(isConnected));
    }

    /**
     * dfs
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param isConnected
     * @return
     */
    public int findCircleNum(int[][] isConnected) {
        //连通分量的个数
        int count = 0;
        //访问数组
        boolean[] visited = new boolean[isConnected.length];

        //对未被访问的节点i进行dfs
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited[i]) {
                dfs(i, isConnected, visited);
                count++;
            }
        }

        return count;
    }

    /**
     * bfs
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param isConnected
     * @return
     */
    public int findCircleNum2(int[][] isConnected) {
        //连通分量的个数
        int count = 0;
        //访问数组
        boolean[] visited = new boolean[isConnected.length];

        //对未被访问的节点i进行bfs
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited[i]) {
                bfs(i, isConnected, visited);
                count++;
            }
        }

        return count;
    }

    /**
     * 并查集
     * 时间复杂度O(n^2*α(mn))=O(n^2)，空间复杂度O(n) (find()和union()的时间复杂度为O(α(n))，可视为常数O(1))
     *
     * @param isConnected
     * @return
     */
    public int findCircleNum3(int[][] isConnected) {
        int n = isConnected.length;
        UnionFind unionFind = new UnionFind(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //节点i和节点j相连，是一个连通分量
                if (isConnected[i][j] != 0) {
                    unionFind.union(i, j);
                }
            }
        }

        //返回并查集中连通分量的个数
        return unionFind.count;
    }

    private void dfs(int i, int[][] isConnected, boolean[] visited) {
        if (i < 0 || i >= isConnected.length || visited[i]) {
            return;
        }

        //节点i标记为已访问
        visited[i] = true;

        //遍历节点i未被访问的邻接顶点j，节点j进行dfs
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] != 0 && !visited[j]) {
                dfs(j, isConnected, visited);
            }
        }
    }

    private void bfs(int i, int[][] isConnected, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(i);

        while (!queue.isEmpty()) {
            int index = queue.poll();
            visited[index] = true;

            //遍历节点index未被访问的邻接顶点j，节点j加入队列
            for (int j = 0; j < isConnected.length; j++) {
                if (isConnected[index][j] != 0 && !visited[j]) {
                    queue.offer(j);
                }
            }
        }
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
