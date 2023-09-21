package com.zhang.java;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @Date 2023/9/21 08:28
 * @Author zsy
 * @Description 统计封闭岛屿的数目 dfs和bfs类比 并查集类比
 * 二维矩阵 grid 由 0 （土地）和 1 （水）组成。
 * 岛是由最大的4个方向连通的 0 组成的群，封闭岛是一个 完全 由1包围（左、上、右、下）的岛。
 * 请返回 封闭岛屿 的数目。
 * <p>
 * 输入：grid = [
 * [1,1,1,1,1,1,1,0],
 * [1,0,0,0,0,1,1,0],
 * [1,0,1,0,1,1,1,0],
 * [1,0,0,0,0,1,0,1],
 * [1,1,1,1,1,1,1,0]
 * ]
 * 输出：2
 * 解释：
 * 灰色区域的岛屿是封闭岛屿，因为这座岛屿完全被水域包围（即被 1 区域包围）。
 * <p>
 * 输入：grid = [
 * [0,0,1,0,0],
 * [0,1,0,1,0],
 * [0,1,1,1,0]
 * ]
 * 输出：1
 * <p>
 * 输入：grid = [
 * [1,1,1,1,1,1,1],
 * [1,0,0,0,0,0,1],
 * [1,0,1,1,1,0,1],
 * [1,0,1,0,1,0,1],
 * [1,0,1,1,1,0,1],
 * [1,0,0,0,0,0,1],
 * [1,1,1,1,1,1,1]
 * ]
 * 输出：2
 * <p>
 * 1 <= grid.length, grid[0].length <= 100
 * 0 <= grid[i][j] <=1
 */
public class Problem1254 {
    public static void main(String[] args) {
        Problem1254 problem1254 = new Problem1254();
        int[][] grid = {
                {1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 1, 1, 0},
                {1, 0, 1, 0, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 0}
        };
        System.out.println(problem1254.closedIsland(grid));
        System.out.println(problem1254.closedIsland2(grid));
        System.out.println(problem1254.closedIsland3(grid));
    }

    /**
     * dfs
     * 对边界为0的节点进行dfs，之后对除边界外为0的节点dfs，找封闭岛屿
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int closedIsland(int[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        //边界为0的节点进行dfs
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][0] == 0) {
                dfs(i, 0, grid, visited);
            }

            if (grid[i][grid[0].length - 1] == 0) {
                dfs(i, grid[0].length - 1, grid, visited);
            }
        }

        //边界为0的节点进行dfs
        for (int j = 1; j < grid[0].length - 1; j++) {
            if (grid[0][j] == 0) {
                dfs(0, j, grid, visited);
            }

            if (grid[grid.length - 1][j] == 0) {
                dfs(grid.length - 1, j, grid, visited);
            }
        }

        int count = 0;

        //对除边界外为0的节点dfs，找封闭岛屿
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if (grid[i][j] == 0 && !visited[i][j]) {
                    dfs(i, j, grid, visited);
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * bfs
     * 对边界为0的节点进行bfs，之后对除边界外为0的节点bfs，找封闭岛屿
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int closedIsland2(int[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        //边界为0的节点进行bfs
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][0] == 0) {
                bfs(i, 0, grid, visited);
            }

            if (grid[i][grid[0].length - 1] == 0) {
                bfs(i, grid[0].length - 1, grid, visited);
            }
        }

        //边界为0的节点进行bfs
        for (int j = 1; j < grid[0].length - 1; j++) {
            if (grid[0][j] == 0) {
                bfs(0, j, grid, visited);
            }

            if (grid[grid.length - 1][j] == 0) {
                bfs(grid.length - 1, j, grid, visited);
            }
        }

        int count = 0;

        //对除边界外为0的节点bfs，找封闭岛屿
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if (grid[i][j] == 0 && !visited[i][j]) {
                    bfs(i, j, grid, visited);
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 并查集
     * 边界为0的节点和dummyIndex相连，遍历除边界外为0的节点，和相邻为0的节点相连，遍历除边界外为0且不和dummyIndex相连的节点，
     * 将当前节点所属连通分量根节点加入set集合，set集合中元素的个数即为封闭岛屿的数量
     * 时间复杂度O(mn*α(mn))=O(mn)，空间复杂度O(mn) (find()和union()的时间复杂度为O(α(mn))，可视为常数O(1))
     *
     * @param grid
     * @return
     */
    public int closedIsland3(int[][] grid) {
        UnionFind unionFind = new UnionFind(grid);

        //边界为0的节点和dummyIndex相连，dummyIndex作为根节点
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][0] == 0) {
                unionFind.unionDummy(i * grid[0].length);
            }

            if (grid[i][grid[0].length - 1] == 0) {
                unionFind.unionDummy(i * grid[0].length + grid[0].length - 1);
            }
        }

        //边界为0的节点和dummyIndex相连，dummyIndex作为根节点
        for (int j = 1; j < grid[0].length - 1; j++) {
            if (grid[0][j] == 0) {
                unionFind.unionDummy(j);
            }

            if (grid[grid.length - 1][j] == 0) {
                unionFind.unionDummy((grid.length - 1) * grid[0].length + j);
            }
        }

        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        //遍历除边界外为'O'的节点，和相邻为'O'节点相连
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if (grid[i][j] == 0) {
                    for (int k = 0; k < direction.length; k++) {
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        if (grid[x][y] == 0) {
                            unionFind.union(i * grid[0].length + j, x * grid[0].length + y);
                        }
                    }
                }
            }
        }

        //存放连通分量根节点的set集合
        Set<Integer> set = new HashSet<>();

        //遍历除边界外为0且不和dummyIndex相连的节点
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if (grid[i][j] == 0 && !unionFind.isConnected(i * grid[0].length + j, unionFind.dummyIndex)) {
                    //当前节点所属连通分量根节点
                    int rootIndex = unionFind.find(i * grid[0].length + j);
                    set.add(rootIndex);
                }
            }
        }

        return set.size();
    }

    private void dfs(int i, int j, int[][] grid, boolean[][] visited) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 0 || visited[i][j]) {
            return;
        }

        visited[i][j] = true;

        dfs(i - 1, j, grid, visited);
        dfs(i + 1, j, grid, visited);
        dfs(i, j - 1, grid, visited);
        dfs(i, j + 1, grid, visited);
    }

    private void bfs(int i, int j, int[][] grid, boolean[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            if (arr[0] < 0 || arr[0] >= grid.length || arr[1] < 0 || arr[1] >= grid[0].length ||
                    grid[arr[0]][arr[1]] != 0 || visited[arr[0]][arr[1]]) {
                continue;
            }

            visited[arr[0]][arr[1]] = true;

            queue.offer(new int[]{arr[0] - 1, arr[1]});
            queue.offer(new int[]{arr[0] + 1, arr[1]});
            queue.offer(new int[]{arr[0], arr[1] - 1});
            queue.offer(new int[]{arr[0], arr[1] + 1});
        }
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        //连通分量的个数
        private int count;
        //虚拟节点，grid中所有边界为0的节点的连通分量根节点都指向虚拟节点
        private final int dummyIndex;
        //节点的父节点数组
        private final int[] parent;
        //节点的权值数组
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
                    if (grid[i][j] == 0) {
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
            parent[rootI] = dummyIndex;
            weight[dummyIndex] = Math.max(weight[dummyIndex], weight[rootI]);
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
