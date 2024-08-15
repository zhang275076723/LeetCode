package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/11/29 08:19
 * @Author zsy
 * @Description 迷宫 II 类比Problem490、Problem499、Problem2258 bfs类比Problem407、Problem499、Problem847、Problem1129、Problem1293、Problem1368、Problem1631、Problem2045、Problem2290 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem490、Problem499、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12
 * 迷宫中有一个球，它有空地 (表示为 0) 和墙 (表示为 1)。
 * 球可以向上、向下、向左或向右滚过空地，但直到撞上墙之前它都不会停止滚动。
 * 当球停止时，它才可以选择下一个滚动方向。
 * 给定 m × n 的迷宫(maze)，球的起始位置 (start = [startrow, startcol]) 和
 * 目的地 (destination = [destinationrow, destinationcol])，返回球在目的地 (destination) 停止的最短距离。
 * 如果球不能在目的地 (destination) 停止，返回 -1。
 * 距离是指球从起始位置 ( 不包括 ) 到终点 ( 包括 ) 所经过的空地数。
 * 你可以假设迷宫的边界都是墙 ( 见例子 )。
 * <p>
 * 输入: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [4,4]
 * 输出: 12
 * 解析: 一条最短路径 : left -> down -> left -> down -> right -> down -> right。
 * 总距离为 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12。
 * <p>
 * 输入: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [3,2]
 * 输出: -1
 * 解析: 球不可能在目的地停下来。注意，你可以经过目的地，但不能在那里停下来。
 * <p>
 * 输入: maze = [[0,0,0,0,0],[1,1,0,0,1],[0,0,0,0,0],[0,1,0,0,1],[0,1,0,0,0]], start = [4,3], destination = [0,1]
 * 输出: -1
 * <p>
 * m == maze.length
 * n == maze[i].length
 * 1 <= m, n <= 100
 * maze[i][j] 是 0 或 1.
 * start.length == 2
 * destination.length == 2
 * 0 <= startrow, destinationrow < m
 * 0 <= startcol, destinationcol < n
 * 球和目的地都存在于一个空地中，它们最初不会处于相同的位置。
 * 迷宫至少包含两个空地。
 */
public class Problem505 {
    public static void main(String[] args) {
        Problem505 problem505 = new Problem505();
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
        System.out.println(problem505.shortestDistance(maze, start, destination));
        System.out.println(problem505.shortestDistance2(maze, start, destination));
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
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        //球从起始位置(start[0],start[1])开始滚动，停止到节点(i,j)的最短距离
        int[][] distance = new int[maze.length][maze[0].length];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //distance初始化，初始化为int最大值表示球从起始位置(start[0],start[1])开始滚动，无法停止到节点(i,j)
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，球从起始位置(start[0],start[1])开始滚动，停止到节点(start[0],start[1])的最短距离为0
        distance[start[0]][start[1]] = 0;

        dfs(start[0], start[1], maze, distance, direction);

        //dfs结束，球从起始位置(start[0],start[1])开始滚动，无法停止到目的地(destination[0],destination[1])，则返回-1；
        //否则返回distance[destination[0]][destination[1]]
        return distance[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : distance[destination[0]][destination[1]];
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
    public int shortestDistance2(int[][] maze, int[] start, int[] destination) {
        //球从起始位置(start[0],start[1])开始滚动，停止到节点(i,j)的最短距离
        int[][] distance = new int[maze.length][maze[0].length];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //distance初始化，初始化为int最大值表示球从起始位置(start[0],start[1])开始滚动，无法停止到节点(i,j)
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，球从起始位置(start[0],start[1])开始滚动，停止到节点(start[0],start[1])的最短距离为0
        distance[start[0]][start[1]] = 0;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start[0], start[1]});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            int i = arr[0];
            int j = arr[1];

            //球找下一个停止滚动的位置
            for (int k = 0; k < direction.length; k++) {
                int x = i;
                int y = j;
                //球从节点(i,j)滚动到节点(x,y)的路径长度
                int curDistance = 0;

                //球向一个方向滚动时，会一直滚动直至遇到墙才停止，不会停留在空白位置
                while (x + direction[k][0] >= 0 && x + direction[k][0] < maze.length &&
                        y + direction[k][1] >= 0 && y + direction[k][1] < maze.length &&
                        maze[x + direction[k][0]][y + direction[k][1]] == 0) {
                    x = x + direction[k][0];
                    y = y + direction[k][1];
                    curDistance++;
                }

                //找到更小的distance[x][y]，更新distance[x][y]，节点(x,y)加入队列
                if (distance[i][j] + curDistance < distance[x][y]) {
                    distance[x][y] = distance[i][j] + curDistance;
                    queue.offer(new int[]{x, y});
                }
            }
        }

        //bfs结束，球从起始位置(start[0],start[1])开始滚动，无法停止到目的地(destination[0],destination[1])，则返回-1；
        //否则返回distance[destination[0]][destination[1]]
        return distance[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : distance[destination[0]][destination[1]];
    }

    private void dfs(int i, int j, int[][] maze, int[][] distance, int[][] direction) {
        //球找下一个停止滚动的位置
        for (int k = 0; k < direction.length; k++) {
            int x = i;
            int y = j;
            //球从节点(i,j)滚动到节点(x,y)的路径长度
            int curDistance = 0;

            //球向一个方向滚动时，会一直滚动直至遇到墙才停止，不会停留在空白位置
            while (x + direction[k][0] >= 0 && x + direction[k][0] < maze.length &&
                    y + direction[k][1] >= 0 && y + direction[k][1] < maze[0].length &&
                    maze[x + direction[k][0]][y + direction[k][1]] == 0) {
                x = x + direction[k][0];
                y = y + direction[k][1];
                curDistance++;
            }

            //找到更小的distance[x][y]，更新distance[x][y]，节点(x,y)进行dfs
            if (distance[i][j] + curDistance < distance[x][y]) {
                distance[x][y] = distance[i][j] + curDistance;
                dfs(x, y, maze, distance, direction);
            }
        }
    }
}