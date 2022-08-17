package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/5/12 9:22
 * @Author zsy
 * @Description 岛屿数量 类比Problem399(并查集) 字节面试题
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围。
 * <p>
 * 输入：grid = [
 * ["1","1","1","1","0"],
 * ["1","1","0","1","0"],
 * ["1","1","0","0","0"],
 * ["0","0","0","0","0"]
 * ]
 * 输出：1
 * <p>
 * 输入：grid = [
 * ["1","1","0","0","0"],
 * ["1","1","0","0","0"],
 * ["0","0","1","0","0"],
 * ["0","0","0","1","1"]
 * ]
 * 输出：3
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * grid[i][j] 的值为 '0' 或 '1'
 */
public class Problem200 {
    public static void main(String[] args) {
        Problem200 problem200 = new Problem200();
        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };
//        System.out.println(problem200.numIslands(grid));
//        System.out.println(problem200.numIslands2(grid));
        System.out.println(problem200.numIslands3(grid));
    }

    /**
     * 深度优先遍历dfs
     * 访问为'1'的位置，并置为'2'，表示已经访问过，直至没有'1'的位置表示遍历结束
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int landNum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //只有当前位置是'1'时，才进行遍历
                if (grid[i][j] == '1') {
                    landNum++;
                    dfs(grid, i, j);
                }
            }
        }
        return landNum;
    }

    /**
     * 广度优先遍历bfs
     * 访问为'1'的位置，并置为'2'，表示已经访问过，将该位置加入队列，直至没有'1'的位置表示遍历结束
     * 时间复杂度O(mn)，空间复杂度O(min(m,n))
     *
     * @param grid
     * @return
     */
    public int numIslands2(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int landNum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //只有当前位置是'1'时，才进行遍历
                if (grid[i][j] == '1') {
                    landNum++;
                    bfs(grid, i, j);
                }
            }
        }
        return landNum;
    }

    /**
     * 并查集
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int numIslands3(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int row = grid.length;
        int column = grid[0].length;
        UnionFind unionFind = new UnionFind(grid);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (grid[i][j] == '1') {
                    //不需要判断当前位置的左边和上边，只需要判断右边和下边
                    if (j + 1 < column && grid[i][j + 1] == '1') {
                        unionFind.union(i * column + j, i * column + j + 1);
                    }
                    if (i + 1 < row && grid[i + 1][j] == '1') {
                        unionFind.union(i * column + j, (i + 1) * column + j);
                    }
                }
            }
        }

        return unionFind.count;
    }

    /**
     * 将遍历过的位置置为'2'，表示已经访问过
     *
     * @param grid
     * @param i
     * @param j
     */
    private void dfs(char[][] grid, int i, int j) {
        if (i < 0
                || i >= grid.length
                || j < 0
                || j >= grid[0].length
                || grid[i][j] != '1') {
            return;
        }

        //将当前位置置为'2'，表示已经访问过
        grid[i][j] = '2';
        dfs(grid, i - 1, j);
        dfs(grid, i + 1, j);
        dfs(grid, i, j - 1);
        dfs(grid, i, j + 1);
    }

    private void bfs(char[][] grid, int i, int j) {
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(i, j));

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            if (pos.i >= 0
                    && pos.i < grid.length
                    && pos.j >= 0
                    && pos.j < grid[0].length
                    && grid[pos.i][pos.j] == '1') {
                //将当前位置置为'2'，表示已经访问过
                grid[pos.i][pos.j] = '2';
                queue.offer(new Pos(pos.i - 1, pos.j));
                queue.offer(new Pos(pos.i + 1, pos.j));
                queue.offer(new Pos(pos.i, pos.j - 1));
                queue.offer(new Pos(pos.i, pos.j + 1));
            }
        }
    }

    /**
     * 广度优先遍历队列中存储的节点
     */
    private static class Pos {
        int i;
        int j;

        public Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    /**
     * 并查集(不相交数据集)类
     */
    private static class UnionFind {
        /**
         * 并查集个数
         */
        int count;

        /**
         * 当前位置节点的秩(高度)
         */
        int[] rank;

        /**
         * 当前位置节点对应的父节点
         */
        int[] parent;

        public UnionFind(char[][] grid) {
            count = 0;
            int row = grid.length;
            int column = grid[0].length;
            rank = new int[row * column];
            parent = new int[row * column];

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (grid[i][j] == '1') {
                        count++;
                        //当前位置节点的父节点是它本身
                        parent[i * column + j] = i * column + j;
                    }
                    //当前位置节点的秩为0
                    rank[i * column + j] = 0;
                }
            }
        }

        /**
         * 按秩合并，具有较小秩的根节点指向具有较大秩的根节点
         *
         * @param i
         * @param j
         */
        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (rank[rootI] > rank[rootJ]) {
                    parent[rootJ] = rootI;
                } else if (rank[rootI] < rank[rootJ]) {
                    parent[rootI] = rootJ;
                } else { //两个根节点秩相等，则其中一个根节点的秩加1
                    parent[rootJ] = rootI;
                    rank[rootI]++;
                }
                //并查集个数减少一个
                count--;
            }
        }

        /**
         * 路径压缩，使每个节点直接指向根节点
         *
         * @param i
         * @return
         */
        public int find(int i) {
            //当前位置节点的父节点不是他本身，则当前节点的父节点指向父节点的父节点
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }
    }
}