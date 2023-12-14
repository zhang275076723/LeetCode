package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/2/10 08:04
 * @Author zsy
 * @Description 岛屿的周长 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem490、Problem499、Problem505、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12
 * 给定一个 row x col 的二维网格地图 grid ，其中：grid[i][j] = 1 表示陆地， grid[i][j] = 0 表示水域。
 * 网格中的格子 水平和垂直 方向相连（对角线方向不相连）。
 * 整个网格被水完全包围，但其中恰好有一个岛屿（或者说，一个或多个表示陆地的格子相连组成的岛屿）。
 * 岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。
 * 格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。
 * 计算这个岛屿的周长。
 * <p>
 * 输入：grid = [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]]
 * 输出：16
 * 解释：它的周长是上面图片中的 16 个黄色的边
 * <p>
 * 输入：grid = [[1]]
 * 输出：4
 * <p>
 * 输入：grid = [[1,0]]
 * 输出：4
 * <p>
 * row == grid.length
 * col == grid[i].length
 * 1 <= row, col <= 100
 * grid[i][j] 为 0 或 1
 */
public class Problem463 {
    public static void main(String[] args) {
        Problem463 problem463 = new Problem463();
        int[][] grid = {
                {0, 1, 0, 0},
                {1, 1, 1, 0},
                {0, 1, 0, 0},
                {1, 1, 0, 0}
        };
        System.out.println(problem463.islandPerimeter(grid));
        System.out.println(problem463.islandPerimeter2(grid));
        System.out.println(problem463.islandPerimeter3(grid));
    }

    /**
     * 模拟
     * 为1的节点能够贡献的边长包括：
     * 1、当前节点的相邻节点不在矩阵范围之内
     * 2、当前节点的相邻节点为0
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param grid
     * @return
     */
    public int islandPerimeter(int[][] grid) {
        //岛屿周长
        int perimeter = 0;
        //当前节点的上下左右四个位置
        int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //当前为1的节点对相邻节点找贡献的边长
                if (grid[i][j] == 1) {
                    for (int k = 0; k < direction.length; k++) {
                        //当前节点(i,j)的相邻节点(x,y)
                        int x = i + direction[k][0];
                        int y = j + direction[k][1];

                        //相邻节点(x,y)不在矩阵范围之内，或相邻节点(x,y)为0，边长加1
                        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == 0) {
                            perimeter++;
                        }
                    }
                }
            }
        }

        return perimeter;
    }

    /**
     * dfs
     * 为1的节点能够贡献的边长包括：
     * 1、当前节点的相邻节点不在矩阵范围之内
     * 2、当前节点的相邻节点为0
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int islandPerimeter2(int[][] grid) {
        //岛屿周长
        int perimeter = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //对未被访问为1的节点进行dfs，找贡献的边长
                if (grid[i][j] == 1 && !visited[i][j]) {
                    perimeter = perimeter + dfs(i, j, grid, visited);
                }
            }
        }

        return perimeter;
    }

    /**
     * bfs
     * 为1的节点能够贡献的边长包括：
     * 1、当前节点的相邻节点不在矩阵范围之内
     * 2、当前节点的相邻节点为0
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param grid
     * @return
     */
    public int islandPerimeter3(int[][] grid) {
        //岛屿周长
        int perimeter = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //对未被访问为1的节点进行bfs，找贡献的边长
                if (grid[i][j] == 1 && !visited[i][j]) {
                    perimeter = perimeter + bfs(i, j, grid, visited);
                }
            }
        }

        return perimeter;
    }

    private int dfs(int i, int j, int[][] grid, boolean[][] visited) {
        //当前节点(i,j)不在矩阵范围之内，或当前节点(i,j)为0，则说明当前节点(i,j)对前一个相邻节点贡献的边长为1
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0) {
            return 1;
        }

        //当前节点(i,j)已经被访问，则说明当前节点(i,j)对前一个相邻节点贡献的边长为0
        if (visited[i][j]) {
            return 0;
        }

        int count = 0;
        //当前位置已被访问
        visited[i][j] = true;

        //往上下左右找
        count = count + dfs(i - 1, j, grid, visited);
        count = count + dfs(i + 1, j, grid, visited);
        count = count + dfs(i, j - 1, grid, visited);
        count = count + dfs(i, j + 1, grid, visited);

        return count;
    }

    private int bfs(int i, int j, int[][] grid, boolean[][] visited) {
        int count = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            //当前节点(arr[0],arr[1])不在矩阵范围之内，或当前节点(arr[0],arr[1])为0，
            //则说明当前节点(arr[0],arr[1])对前一个相邻节点贡献的边长为1
            if (arr[0] < 0 || arr[0] >= grid.length || arr[1] < 0 || arr[1] >= grid[0].length ||
                    grid[arr[0]][arr[1]] == 0) {
                count++;
                continue;
            }

            //当前节点(arr[0],arr[1])已经被访问，则说明当前节点(arr[0],arr[1])对前一个相邻节点贡献的边长为0，直接进行下次循环
            if (visited[arr[0]][arr[1]]) {
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

        return count;
    }
}
