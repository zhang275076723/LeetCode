package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/11/28 08:18
 * @Author zsy
 * @Description 迷宫 类比Problem499、Problem505、Problem2258 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem499、Problem505、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12
 * 由空地（用 0 表示）和墙（用 1 表示）组成的迷宫 maze 中有一个球。
 * 球可以途经空地向 上、下、左、右 四个方向滚动，且在遇到墙壁前不会停止滚动。
 * 当球停下时，可以选择向下一个方向滚动。
 * 给你一个大小为 m x n 的迷宫 maze ，以及球的初始位置 start 和目的地 destination ，
 * 其中 start = [startrow, startcol] 且 destination = [destinationrow, destinationcol] 。
 * 请你判断球能否在目的地停下：如果可以，返回 true ；否则，返回 false 。
 * 你可以 假定迷宫的边缘都是墙壁（参考示例）。
 * <p>
 * 输入：maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [4,4]
 * 输出：true
 * 解释：一种可能的路径是 : 左 -> 下 -> 左 -> 下 -> 右 -> 下 -> 右。
 * <p>
 * 输入：maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [3,2]
 * 输出：false
 * 解释：不存在能够使球停在目的地的路径。注意，球可以经过目的地，但无法在那里停驻。
 * <p>
 * 输入：maze = [[0,0,0,0,0],[1,1,0,0,1],[0,0,0,0,0],[0,1,0,0,1],[0,1,0,0,0]], start = [4,3], destination = [0,1]
 * 输出：false
 * <p>
 * m == maze.length
 * n == maze[i].length
 * 1 <= m, n <= 100
 * maze[i][j] is 0 or 1.
 * start.length == 2
 * destination.length == 2
 * 0 <= startrow, destinationrow <= m
 * 0 <= startcol, destinationcol <= n
 * 球和目的地都在空地上，且初始时它们不在同一位置
 * 迷宫 至少包括 2 块空地
 */
public class Problem490 {
    public static void main(String[] args) {
        Problem490 problem490 = new Problem490();
        int[][] maze = {
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {1, 1, 0, 1, 1},
                {0, 0, 0, 0, 0},
        };
        int[] start = {0, 4};
        int[] destination = {4, 4};
//        int[][] maze = {
//                {0, 0, 1, 0, 0},
//                {0, 0, 0, 0, 0},
//                {0, 0, 0, 1, 0},
//                {1, 1, 0, 1, 1},
//                {0, 0, 0, 0, 0},
//        };
//        int[] start = {0, 4};
//        int[] destination = {3, 2};
        System.out.println(problem490.hasPath(maze, start, destination));
        System.out.println(problem490.hasPath2(maze, start, destination));
    }

    /**
     * dfs
     * 不停止滚动表示球向一个方向滚动时，会一直滚动直至遇到墙才停止，不会停留在空白位置
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param maze
     * @param start
     * @param destination
     * @return
     */
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        //节点访问数组
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        dfs(start[0], start[1], maze, destination, visited, direction);

        return visited[destination[0]][destination[1]];
    }

    /**
     * bfs
     * 不停止滚动表示球向一个方向滚动时，会一直滚动直至遇到墙才停止，不会停留在空白位置
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param maze
     * @param start
     * @param destination
     * @return
     */
    public boolean hasPath2(int[][] maze, int[] start, int[] destination) {
        //节点访问数组
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start[0], start[1]});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            int i = arr[0];
            int j = arr[1];

            if (visited[i][j]) {
                continue;
            }

            visited[i][j] = true;

            //球从初始位置开始能够停止在目的地，则返回true
            if (i == destination[0] && j == destination[1]) {
                return true;
            }

            //球找下一个停止滚动的位置
            for (int k = 0; k < direction.length; k++) {
                int x = i;
                int y = j;

                //球向一个方向滚动时，会一直滚动直至遇到墙才停止，不会停留在空白位置
                while (x + direction[k][0] >= 0 && x + direction[k][0] < maze.length &&
                        y + direction[k][1] >= 0 && y + direction[k][1] < maze[0].length &&
                        maze[x + direction[k][0]][y + direction[k][1]] == 0) {
                    x = x + direction[k][0];
                    y = y + direction[k][1];
                }

                queue.offer(new int[]{x, y});
            }
        }

        //bfs结束，球从初始位置都不能够停止在目的地，则返回false
        return false;
    }

    private void dfs(int i, int j, int[][] maze, int[] destination, boolean[][] visited, int[][] direction) {
        if (visited[i][j]) {
            return;
        }

        visited[i][j] = true;

        //球从初始位置开始能够停止在目的地，不需要继续dfs，直接返回
        if (i == destination[0] && j == destination[1]) {
            return;
        }

        //球找下一个停止滚动的位置
        for (int k = 0; k < direction.length; k++) {
            int x = i;
            int y = j;

            //球向一个方向滚动时，会一直滚动直至遇到墙才停止，不会停留在空白位置
            while (x + direction[k][0] >= 0 && x + direction[k][0] < maze.length &&
                    y + direction[k][1] >= 0 && y + direction[k][1] < maze[0].length &&
                    maze[x + direction[k][0]][y + direction[k][1]] == 0) {
                x = x + direction[k][0];
                y = y + direction[k][1];
            }

            dfs(x, y, maze, destination, visited, direction);
        }
    }
}
