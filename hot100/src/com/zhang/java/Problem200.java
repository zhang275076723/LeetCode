package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/5/12 9:22
 * @Author zsy
 * @Description 岛屿数量 字节面试题 阿里面试题 dfs和bfs类比Problem79、Problem130、Problem212、Problem463、Problem490、Problem499、Problem505、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12 并查集类比Problem130、Problem261、Problem305、Problem323、Problem399、Problem547、Problem684、Problem685、Problem695、Problem765、Problem785、Problem827、Problem886、Problem952、Problem1135、Problem1254、Problem1319、Problem1489、Problem1568、Problem1584、Problem1627、Problem1905、Problem1998、Problem2685
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
        System.out.println(problem200.numIslands(grid));
        System.out.println(problem200.numIslands2(grid));
        System.out.println(problem200.numIslands3(grid));
    }

    /**
     * dfs
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int count = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //对未被访问为'1'的节点进行dfs
                if (grid[i][j] == '1' && !visited[i][j]) {
                    dfs(i, j, grid, visited);
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * bfs
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int numIslands2(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int count = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //对未被访问为'1'的节点进行dfs
                if (grid[i][j] == '1' && !visited[i][j]) {
                    bfs(i, j, grid, visited);
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 并查集
     * 时间复杂度O(mn*α(mn))=O(mn)，空间复杂度O(mn) (find()和union()的时间复杂度为O(α(mn))，可视为常数O(1))
     *
     * @param grid
     * @return
     */
    public int numIslands3(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        UnionFind unionFind = new UnionFind(grid);
        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //当前节点为'1'时，和当前位置的上下左右'1'进行合并，成为一个连通分量
                if (grid[i][j] == '1') {
                    for (int k = 0; k < direction.length; k++) {
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != '1') {
                            continue;
                        }

                        //(i,j)和(x,y)合并为一个连通分量
                        unionFind.union(i * grid[0].length + j, x * grid[0].length + y);
                    }
                }
            }
        }

        return unionFind.count;
    }

    private void dfs(int i, int j, char[][] grid, boolean[][] visited) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j] || grid[i][j] != '1') {
            return;
        }

        //当前位置已被访问
        visited[i][j] = true;

        //往上下左右找
        dfs(i - 1, j, grid, visited);
        dfs(i + 1, j, grid, visited);
        dfs(i, j - 1, grid, visited);
        dfs(i, j + 1, grid, visited);
    }

    private void bfs(int i, int j, char[][] grid, boolean[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            if (arr[0] < 0 || arr[0] >= grid.length || arr[1] < 0 || arr[1] >= grid[0].length ||
                    visited[arr[0]][arr[1]] || grid[arr[0]][arr[1]] != '1') {
                continue;
            }

            //当前位置已被访问
            visited[arr[0]][arr[1]] = true;

            //当前位置的上下左右加入队列
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
        //并查集中连通分量的个数
        private int count;
        //节点的父节点索引下标数组，二维数组按照由左到右由上到下的顺序，从0开始存储
        private final int[] parent;
        //节点的权值数组(节点的高度)，只有一个节点的权值为1
        private final int[] weight;

        public UnionFind(char[][] grid) {
            count = 0;
            parent = new int[grid.length * grid[0].length];
            weight = new int[grid.length * grid[0].length];

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    //当前节点为'1'时，才加入并查集中
                    if (grid[i][j] == '1') {
                        //当前节点的父节点是它本身
                        parent[grid[0].length * i + j] = grid[0].length * i + j;
                        //只有一个节点的权值为1
                        weight[grid[0].length * i + j] = 1;
                        //并查集中连通分量的个数加1
                        count++;
                    }
                }
            }
        }

        /**
         * 按秩合并
         * 具有较小秩的根节点指向具有较大秩的根节点，将两个集合合并为一个集合 (合并之前，需要进行路径压缩)
         *
         * @param i
         * @param j
         */
        public void union(int i, int j) {
            //找到包含i、j的连通分量的根节点
            int rootI = find(i);
            int rootJ = find(j);

            //i、j是两个集合，则进行合并，较小秩的根节点指向具有较大秩的根节点
            if (rootI != rootJ) {
                if (weight[rootI] < weight[rootJ]) {
                    parent[rootI] = rootJ;
                } else if (weight[rootI] > weight[rootJ]) {
                    parent[rootJ] = rootI;
                } else {
                    //两个根节点秩相等，则其中任意一个根节点指向另一个根节点，被指向的根节点秩加1
                    parent[rootJ] = rootI;
                    weight[rootI]++;
                }

                //i、j两个连通分量合并，并查集中连通分量的个数减1
                count--;
            }
        }

        /**
         * 路径压缩，返回当前节点的根节点
         * 使每个节点的父节点直接指向根节点，并不改变节点的秩
         *
         * @param i
         * @return
         */
        public int find(int i) {
            //当前节点不是根节点，则当前节点的父节点指向父节点的父节点，进行递归，最终当前节点的父节点指向根节点
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }

            return parent[i];
        }

        /**
         * 判断节点i和节点j是否连通，即在一个集合中 (判断之前，需要进行路径压缩)
         *
         * @param i
         * @param j
         * @return
         */
        public boolean isConnected(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            return rootI == rootJ;
        }
    }
}