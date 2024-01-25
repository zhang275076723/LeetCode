package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/18 08:08
 * @Author zsy
 * @Description 岛屿数量 II 并查集类比Problem130、Problem200、Problem261、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1361、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem1998、Problem2685
 * 给你一个大小为 m x n 的二进制网格 grid 。
 * 网格表示一个地图，其中，0 表示水，1 表示陆地。
 * 最初，grid 中的所有单元格都是水单元格（即，所有单元格都是 0）。
 * 可以通过执行 addLand 操作，将某个位置的水转换成陆地。
 * 给你一个数组 positions ，其中 positions[i] = [ri, ci] 是要执行第 i 次操作的位置 (ri, ci) 。
 * 返回一个整数数组 answer ，其中 answer[i] 是将单元格 (ri, ci) 转换为陆地后，地图中岛屿的数量。
 * 岛屿 的定义是被「水」包围的「陆地」，通过水平方向或者垂直方向上相邻的陆地连接而成。
 * 你可以假设地图网格的四边均被无边无际的「水」所包围。
 * <p>
 * 输入：m = 3, n = 3, positions = [[0,0],[0,1],[1,2],[2,1]]
 * 输出：[1,1,2,3]
 * 解释：
 * 起初，二维网格 grid 被全部注入「水」。（0 代表「水」，1 代表「陆地」）
 * 操作 #1：addLand(0, 0) 将 grid[0][0] 的水变为陆地。此时存在 1 个岛屿。
 * 操作 #2：addLand(0, 1) 将 grid[0][1] 的水变为陆地。此时存在 1 个岛屿。
 * 操作 #3：addLand(1, 2) 将 grid[1][2] 的水变为陆地。此时存在 2 个岛屿。
 * 操作 #4：addLand(2, 1) 将 grid[2][1] 的水变为陆地。此时存在 3 个岛屿。
 * [0,0,0]       [1,0,0]       [1,1,0]       [1,1,0]       [1,1,0]
 * [0,0,0]  ==>  [0,0,0]  ==>  [0,0,0]  ==>  [0,0,1]  ==>  [0,0,1]
 * [0,0,0]       [0,0,0]       [0,0,0]       [0,0,0]       [0,1,0]
 * <p>
 * 输入：m = 1, n = 1, positions = [[0,0]]
 * 输出：[1]
 * <p>
 * 1 <= m, n, positions.length <= 10^4
 * 1 <= m * n <= 10^4
 * positions[i].length == 2
 * 0 <= ri < m
 * 0 <= ci < n
 */
public class Problem305 {
    public static void main(String[] args) {
        Problem305 problem305 = new Problem305();
        int m = 3;
        int n = 3;
        int[][] positions = {{0, 0}, {0, 1}, {1, 2}, {2, 1}};
        System.out.println(problem305.numIslands(m, n, positions));
    }

    /**
     * 并查集
     * 时间复杂度O(k*α(mn))，空间复杂度O(mn) (k = positions.length) (find()和union()的时间复杂度为O(α(mn))，可视为常数O(1))
     *
     * @param m
     * @param n
     * @param positions
     * @return
     */
    public List<Integer> numIslands(int m, int n, int[][] positions) {
        List<Integer> list = new ArrayList<>();
        UnionFind unionFind = new UnionFind(m, n);
        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        //访问数组，当前节点是否被置为1
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < positions.length; i++) {
            int x = positions[i][0];
            int y = positions[i][1];

            //当前节点已经被置为1，并查集数量不变，直接添加，进行下次循环
            if (visited[x][y]) {
                list.add(unionFind.count);
                continue;
            }

            //当前节点被置为1
            visited[x][y] = true;
            //当前节点作为一个新的连通分量
            unionFind.parent[x * n + y] = x * n + y;
            unionFind.weight[x * n + y] = 1;
            unionFind.count++;

            //当前节点(x,y)与相邻节点合并
            for (int j = 0; j < direction.length; j++) {
                int x2 = x + direction[j][0];
                int y2 = y + direction[j][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n || !visited[x2][y2]) {
                    continue;
                }

                unionFind.union(x * n + y, x2 * n + y2);
            }

            list.add(unionFind.count);
        }

        return list;
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

        public UnionFind(int m, int n) {
            count = 0;
            parent = new int[m * n];
            weight = new int[m * n];
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
