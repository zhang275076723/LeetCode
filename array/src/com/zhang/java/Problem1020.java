package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2024/12/28 08:03
 * @Author zsy
 * @Description 飞地的数量 类比Problem130
 * 给你一个大小为 m x n 的二进制矩阵 grid ，其中 0 表示一个海洋单元格、1 表示一个陆地单元格。
 * 一次 移动 是指从一个陆地单元格走到另一个相邻（上、下、左、右）的陆地单元格或跨过 grid 的边界。
 * 返回网格中 无法 在任意次数的移动中离开网格边界的陆地单元格的数量。
 * <p>
 * 输入：grid = [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
 * 输出：3
 * 解释：有三个 1 被 0 包围。一个 1 没有被包围，因为它在边界上。
 * <p>
 * 输入：grid = [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
 * 输出：0
 * 解释：所有 1 都在边界上或可以到达边界。
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 500
 * grid[i][j] 的值为 0 或 1
 */
public class Problem1020 {
    public static void main(String[] args) {
        Problem1020 problem1020 = new Problem1020();
//        int[][] grid = {
//                {0, 0, 0, 0},
//                {1, 0, 1, 0},
//                {0, 1, 1, 0},
//                {0, 0, 0, 0}
//        };
        int[][] grid = {
                {0, 1, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 0}
        };
        System.out.println(problem1020.numEnclaves(grid));
        System.out.println(problem1020.numEnclaves2(grid));
        System.out.println(problem1020.numEnclaves3(grid));
    }

    /**
     * dfs
     * 边界为1的节点进行dfs，grid中非边界并且未被dfs访问到的为1的节点，即为无法在任意次数移动离开的节点
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int numEnclaves(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        //边界为1的节点进行dfs
        for (int i = 0; i < m; i++) {
            if (grid[i][0] == 1) {
                dfs(i, 0, grid, visited);
            }

            if (grid[i][n - 1] == 1) {
                dfs(i, n - 1, grid, visited);
            }
        }

        //边界为1的节点进行dfs
        for (int j = 1; j < n - 1; j++) {
            if (grid[0][j] == 1) {
                dfs(0, j, grid, visited);
            }

            if (grid[m - 1][j] == 1) {
                dfs(m - 1, j, grid, visited);
            }
        }

        int count = 0;

        //grid中非边界并且未被dfs访问到的为1的节点，即为无法在任意次数移动离开的节点
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * bfs
     * 边界为1的节点进行bfs，grid中非边界并且未被bfs访问到的为1的节点，即为无法在任意次数移动离开的节点
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int numEnclaves2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //边界为1的节点进行dfs
        for (int i = 0; i < m; i++) {
            if (grid[i][0] == 1) {
                queue.offer(new int[]{i, 0});
                visited[i][0] = true;
            }

            if (grid[i][n - 1] == 1) {
                queue.offer(new int[]{i, n - 1});
                visited[i][n - 1] = true;
            }
        }

        //边界为1的节点进行dfs
        for (int j = 1; j < n - 1; j++) {
            if (grid[0][j] == 1) {
                queue.offer(new int[]{0, j});
                visited[0][j] = true;
            }

            if (grid[m - 1][j] == 1) {
                queue.offer(new int[]{m - 1, j});
                visited[m - 1][j] = true;
            }
        }

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            int x1 = arr[0];
            int y1 = arr[1];

            for (int i = 0; i < direction.length; i++) {
                int x2 = x1 + direction[i][0];
                int y2 = y1 + direction[i][1];

                if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n || visited[x2][y2] || grid[x2][y2] != 1) {
                    continue;
                }

                queue.offer(new int[]{x2, y2});
                visited[x2][y2] = true;
            }
        }

        int count = 0;

        //grid中非边界并且未被bfs访问到的为1的节点，即为无法在任意次数移动离开的节点
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 并查集
     * 边界为1的节点和dummyIndex相连，遍历grid中非边界为1的节点，和相邻为1的节点相连，
     * 再次遍历非边界并且不和dummyIndex相连的为1的节点，即为无法在任意次数移动离开的节点
     * 时间复杂度O(mn*α(mn))=O(mn)，空间复杂度O(mn) (find()和union()的时间复杂度为O(α(mn))，可视为常数O(1))
     *
     * @param grid
     * @return
     */
    public int numEnclaves3(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        UnionFind unionFind = new UnionFind(grid);

        //边界为1的节点和dummyIndex相连
        for (int i = 0; i < m; i++) {
            if (grid[i][0] == 1) {
                unionFind.unionDummy(i * n);
            }

            if (grid[i][n - 1] == 1) {
                unionFind.unionDummy(i * n + n - 1);
            }
        }

        //边界为1的节点和dummyIndex相连
        for (int j = 1; j < n - 1; j++) {
            if (grid[0][j] == 1) {
                unionFind.unionDummy(j);
            }

            if (grid[m - 1][j] == 1) {
                unionFind.unionDummy((m - 1) * n + j);
            }
        }

        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //遍历grid中非边界为1的节点，和相邻为1的节点相连
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] == 1) {
                    for (int k = 0; k < direction.length; k++) {
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        if (grid[x][y] == 1) {
                            unionFind.union(i * n + j, x * n + y);
                        }
                    }
                }
            }
        }

        int count = 0;

        //再次遍历非边界并且不和dummyIndex相连的为1的节点，即为无法在任意次数移动离开的节点
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] == 1 && !unionFind.isConnected(unionFind.dummyIndex, i * n + j)) {
                    count++;
                }
            }
        }

        return count;
    }

    private void dfs(int i, int j, int[][] grid, boolean[][] visited) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j] || grid[i][j] != 1) {
            return;
        }

        visited[i][j] = true;

        dfs(i - 1, j, grid, visited);
        dfs(i + 1, j, grid, visited);
        dfs(i, j - 1, grid, visited);
        dfs(i, j + 1, grid, visited);
    }

    /**
     * 并查集
     */
    private static class UnionFind {
        private int count;
        //虚拟节点，grid中所有边界为1的节点的连通分量根节点都指向虚拟节点
        private final int dummyIndex;
        private final int[] parent;
        private final int[] weight;

        public UnionFind(int[][] grid) {
            count = 0;
            //多申请一个长度，末尾节点作为为虚拟节点
            parent = new int[grid.length * grid[0].length + 1];
            weight = new int[grid.length * grid[0].length + 1];
            dummyIndex = grid.length * grid[0].length;
            parent[dummyIndex] = dummyIndex;
            weight[dummyIndex] = 1;

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) {
                        parent[i * grid[0].length + j] = i * grid[0].length + j;
                        weight[i * grid[0].length + j] = 1;
                        count++;
                    }
                }
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

        public void unionDummy(int i) {
            int rootI = find(i);

            if (rootI != dummyIndex) {
                parent[rootI] = dummyIndex;
                weight[dummyIndex] = Math.max(weight[dummyIndex], weight[rootI]);
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
