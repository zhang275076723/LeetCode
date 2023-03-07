package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/2/9 09:05
 * @Author zsy
 * @Description 岛屿的最大面积 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem733、Problem827、Problem994、Problem1034、Problem1162、Offer12 并查集类比Problem130、Problem200、Problem399、Problem765、Problem827
 * 给你一个大小为 m x n 的二进制矩阵 grid 。
 * 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。
 * 你可以假设 grid 的四个边缘都被 0（代表水）包围着。
 * 岛屿的面积是岛上值为 1 的单元格的数目。
 * 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
 * <p>
 * 输入：grid = [
 * [0,0,1,0,0,0,0,1,0,0,0,0,0],
 * [0,0,0,0,0,0,0,1,1,1,0,0,0],
 * [0,1,1,0,1,0,0,0,0,0,0,0,0],
 * [0,1,0,0,1,1,0,0,1,0,1,0,0],
 * [0,1,0,0,1,1,0,0,1,1,1,0,0],
 * [0,0,0,0,0,0,0,0,0,0,1,0,0],
 * [0,0,0,0,0,0,0,1,1,1,0,0,0],
 * [0,0,0,0,0,0,0,1,1,0,0,0,0]
 * ]
 * 输出：6
 * 解释：答案不应该是 11 ，因为岛屿只能包含水平或垂直这四个方向上的 1 。
 * <p>
 * 输入：grid = [[0,0,0,0,0,0,0,0]]
 * 输出：0
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 50
 * grid[i][j] 为 0 或 1
 */
public class Problem695 {
    public static void main(String[] args) {
        Problem695 problem695 = new Problem695();
        int[][] grid = {
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
        };
        System.out.println(problem695.maxAreaOfIsland(grid));
        System.out.println(problem695.maxAreaOfIsland2(grid));
        System.out.println(problem695.maxAreaOfIsland3(grid));
    }

    /**
     * dfs
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int max = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //对未被访问为1的节点进行dfs
                if (grid[i][j] == 1 && !visited[i][j]) {
                    max = Math.max(max, dfs(i, j, grid, visited));
                }
            }
        }

        return max;
    }

    /**
     * bfs
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int maxAreaOfIsland2(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int max = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //对未被访问为1的节点进行bfs
                if (grid[i][j] == 1 && !visited[i][j]) {
                    max = Math.max(max, bfs(i, j, grid, visited));
                }
            }
        }

        return max;
    }

    /**
     * 并查集
     * 时间复杂度O(mn*α(mn))=O(mn)，空间复杂度O(mn) (find()和union()的时间复杂度为O(α(mn))，可视为常数O(1))
     *
     * @param grid
     * @return
     */
    public int maxAreaOfIsland3(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        UnionFind unionFind = new UnionFind(grid);
        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //当前节点为1时，和当前位置的上下左右1进行合并，成为一个连通分量，得到当前连通分量的面积
                if (grid[i][j] == 1) {
                    for (int k = 0; k < direction.length; k++) {
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        //节点(x,y)不在矩阵范围之内，或节点(x,y)不是1，进行下次循环
                        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != 1) {
                            continue;
                        }

                        //(i,j)和(x,y)合并为一个连通分量
                        unionFind.union(i * grid[0].length + j, x * grid[0].length + y);
                    }
                }
            }
        }

        int max = 0;

        //并查集中连通分量的根节点面积中的最大值，即为岛屿的最大面积
        for (int area : unionFind.area) {
            max = Math.max(max, area);
        }

        return max;
    }

    private int dfs(int i, int j, int[][] grid, boolean[][] visited) {
        //当前节点(i,j)不在矩阵范围之内，或当前节点(i,j)已被访问，或当前节点(i,j)不是1，直接返回0
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j] || grid[i][j] != 1) {
            return 0;
        }

        int area = 1;
        //当前位置已被访问
        visited[i][j] = true;

        //往上下左右找
        area = area + dfs(i - 1, j, grid, visited);
        area = area + dfs(i + 1, j, grid, visited);
        area = area + dfs(i, j - 1, grid, visited);
        area = area + dfs(i, j + 1, grid, visited);

        return area;
    }

    private int bfs(int i, int j, int[][] grid, boolean[][] visited) {
        int area = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            //当前节点(arr[0],arr[1])不在矩阵范围之内，或当前节点(arr[0],arr[1])已被访问，或当前节点(arr[0],arr[1])不是1，进行下次循环
            if (arr[0] < 0 || arr[0] >= grid.length || arr[1] < 0 || arr[1] >= grid[0].length ||
                    visited[arr[0]][arr[1]] || grid[arr[0]][arr[1]] != 1) {
                continue;
            }

            //当前位置已被访问
            visited[arr[0]][arr[1]] = true;
            //面积加1
            area++;

            //当前位置的上下左右加入队列
            queue.offer(new int[]{arr[0] - 1, arr[1]});
            queue.offer(new int[]{arr[0] + 1, arr[1]});
            queue.offer(new int[]{arr[0], arr[1] - 1});
            queue.offer(new int[]{arr[0], arr[1] + 1});
        }

        return area;
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        //并查集中连通分量的个数
        private int count;
        //节点的父节点索引下标数组，二维数组按照由左到右由上到下的顺序，从0开始存储
        private final int[] parent;
        //节点的权值数组(节点的高度)，只有一个节点的权值为1
        private final int[] weight;
        //节点所包含的个数数组，用于求面积
        private final int[] area;

        public UnionFind(int[][] grid) {
            count = 0;
            parent = new int[grid.length * grid[0].length];
            weight = new int[grid.length * grid[0].length];
            area = new int[grid.length * grid[0].length];

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    //当前节点为'1'时，才加入并查集中
                    if (grid[i][j] == 1) {
                        count++;
                        parent[i * grid[0].length + j] = i * grid[0].length + j;
                        weight[i * grid[0].length + j] = 1;
                        area[i * grid[0].length + j] = 1;
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
                    //更新根节点的面积
                    area[rootJ] = area[rootJ] + area[rootI];
                } else if (weight[rootI] > weight[rootJ]) {
                    parent[rootJ] = rootI;
                    //更新根节点的面积
                    area[rootI] = area[rootI] + area[rootJ];
                } else {
                    parent[rootJ] = rootI;
                    weight[rootI]++;
                    //更新根节点的面积
                    area[rootI] = area[rootI] + area[rootJ];
                }

                //i、j两个连通分量合并，并查集中连通分量的个数减1
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