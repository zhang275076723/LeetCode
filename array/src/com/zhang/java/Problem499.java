package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/11/30 08:35
 * @Author zsy
 * @Description 迷宫 III 类比Problem490、Problem505、Problem1730、Problem2258 bfs类比Problem407、Problem505、Problem778、Problem847、Problem1129、Problem1293、Problem1368、Problem1631、Problem2045、Problem2290 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem490、Problem505、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12
 * 由空地和墙组成的迷宫中有一个球。
 * 球可以向上（u）下（d）左（l）右（r）四个方向滚动，但在遇到墙壁前不会停止滚动。
 * 当球停下时，可以选择下一个方向。
 * 迷宫中还有一个洞，当球运动经过洞时，就会掉进洞里。
 * 给定球的起始位置，目的地和迷宫，找出让球以最短距离掉进洞里的路径。
 * 距离的定义是球从起始位置（不包括）到目的地（包括）经过的空地个数。
 * 通过'u', 'd', 'l' 和 'r'输出球的移动方向。
 * 由于可能有多条最短路径， 请输出字典序最小的路径。
 * 如果球无法进入洞，输出"impossible"。
 * 迷宫由一个0和1的二维数组表示。
 * 1表示墙壁，0表示空地。
 * 你可以假定迷宫的边缘都是墙壁。
 * 起始位置和目的地的坐标通过行号和列号给出。
 * <p>
 * 输入 1: 迷宫由以下二维数组表示
 * 0 0 0 0 0
 * 1 1 0 0 1
 * 0 0 0 0 0
 * 0 1 0 0 1
 * 0 1 0 0 0
 * 输入 2: 球的初始位置 (rowBall, colBall) = (4, 3)
 * 输入 3: 洞的位置 (rowHole, colHole) = (0, 1)
 * 输出: "lul"
 * 解析: 有两条让球进洞的最短路径。
 * 第一条路径是 左 -> 上 -> 左, 记为 "lul".
 * 第二条路径是 上 -> 左, 记为 'ul'.
 * 两条路径都具有最短距离6, 但'l' < 'u'，故第一条路径字典序更小。因此输出"lul"。
 * <p>
 * 输入 1: 迷宫由以下二维数组表示
 * 0 0 0 0 0
 * 1 1 0 0 1
 * 0 0 0 0 0
 * 0 1 0 0 1
 * 0 1 0 0 0
 * 输入 2: 球的初始位置 (rowBall, colBall) = (4, 3)
 * 输入 3: 洞的位置 (rowHole, colHole) = (3, 0)
 * 输出: "impossible"
 * 示例: 球无法到达洞。
 * <p>
 * 迷宫中只有一个球和一个目的地。
 * 球和洞都在空地上，且初始时它们不在同一位置。
 * 给定的迷宫不包括边界 (如图中的红色矩形), 但你可以假设迷宫的边缘都是墙壁。
 * 迷宫至少包括2块空地，行数和列数均不超过30。
 */
public class Problem499 {
    public static void main(String[] args) {
        Problem499 problem499 = new Problem499();
        int[][] maze = {
                {0, 0, 0, 0, 0},
                {1, 1, 0, 0, 1},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1},
                {0, 1, 0, 0, 0}
        };
        int[] ball = {4, 3};
        int[] hole = {0, 1};
//        int[][] maze = {
//                {0, 0, 0, 0, 0},
//                {1, 1, 0, 0, 1},
//                {0, 0, 0, 0, 0},
//                {0, 1, 0, 0, 1},
//                {0, 1, 0, 0, 0}
//        };
//        int[] ball = {4, 3};
//        int[] hole = {3, 0};
        System.out.println(problem499.findShortestWay(maze, ball, hole));
        System.out.println(problem499.findShortestWay2(maze, ball, hole));
    }

    /**
     * dfs
     * 不停止滚动表示球向一个方向滚动时，会一直滚动直至遇到墙或到达hole才停止，不会停留在空白位置
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param maze
     * @param ball
     * @param hole
     * @return
     */
    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        //球从起始位置(ball[0],ball[1])开始滚动，停止到节点(i,j)的最短距离
        int[][] distance = new int[maze.length][maze[0].length];
        //球从起始位置(ball[0],ball[1])开始滚动，停止到节点(i,j)的最短距离的路径
        String[][] path = new String[maze.length][maze[0].length];
        //当前节点的上下左右四个位置，同时还记录了移动方向
        int[][] direction = {{1, 0, 'd'}, {-1, 0, 'u'}, {0, 1, 'r'}, {0, -1, 'l'}};

        //distance初始化，初始化为int最大值表示球从起始位置(ball[0],ball[1])开始滚动，无法停止到节点(i,j)
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，球从起始位置(ball[0],ball[1])开始滚动，停止到节点(ball[0],ball[1])的最短距离为0
        distance[ball[0]][ball[1]] = 0;
        //初始化，球从起始位置(ball[0],ball[1])开始滚动，停止到节点(ball[0],ball[1])的最短距离的路径为""
        path[ball[0]][ball[1]] = "";

        dfs(ball[0], ball[1], maze, hole, distance, path, direction);

        //dfs结束，球从起始位置(ball[0],ball[1])开始滚动，无法到达(hole[0],hole[1])，则返回"impossible"；
        //否则返回path[hole[0]][hole[1]]
        return distance[hole[0]][hole[1]] == Integer.MAX_VALUE ? "impossible" : path[hole[0]][hole[1]];
    }

    /**
     * bfs
     * 不停止滚动表示球向一个方向滚动时，会一直滚动直至遇到墙或到达hole才停止，不会停留在空白位置
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param maze
     * @param ball
     * @param hole
     * @return
     */
    public String findShortestWay2(int[][] maze, int[] ball, int[] hole) {
        //球从起始位置(ball[0],ball[1])开始滚动，停止到节点(i,j)的最短距离
        int[][] distance = new int[maze.length][maze[0].length];
        //球从起始位置(ball[0],ball[1])开始滚动，停止到节点(i,j)的最短距离的路径
        String[][] path = new String[maze.length][maze[0].length];
        //当前节点的上下左右四个位置，同时还记录了移动方向
        int[][] direction = {{1, 0, 'd'}, {-1, 0, 'u'}, {0, 1, 'r'}, {0, -1, 'l'}};

        //distance初始化，初始化为int最大值表示球从起始位置(ball[0],ball[1])开始滚动，无法停止到节点(i,j)
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化，球从起始位置(ball[0],ball[1])开始滚动，停止到节点(ball[0],ball[1])的最短距离为0
        distance[ball[0]][ball[1]] = 0;
        //初始化，球从起始位置(ball[0],ball[1])开始滚动，停止到节点(ball[0],ball[1])的最短距离的路径为""
        path[ball[0]][ball[1]] = "";

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{ball[0], ball[1]});

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

                //球向一个方向滚动时，会一直滚动直至遇到墙或到达hole才停止，不会停留在空白位置
                while (x + direction[k][0] >= 0 && x + direction[k][0] < maze.length &&
                        y + direction[k][1] >= 0 && y + direction[k][1] < maze[0].length &&
                        maze[x + direction[k][0]][y + direction[k][1]] == 0 &&
                        !(x == hole[0] && y == hole[1])) {
                    x = x + direction[k][0];
                    y = y + direction[k][1];
                    curDistance++;
                }

                //找到更小的distance[x][y]，或路径长度相等的情况下，当前路径的字典序更小，
                //更新distance[x][y]和path[x][y]，节点(x,y)加入队列
                //注意：direction[k][2]为int类型，必须转换为char类型拼接，否则拼接的是一个int整形数字
                if ((distance[i][j] + curDistance < distance[x][y]) ||
                        (distance[i][j] + curDistance == distance[x][y] &&
                                (path[i][j] + (char) direction[k][2]).compareTo(path[x][y]) < 0)) {
                    distance[x][y] = distance[i][j] + curDistance;
                    path[x][y] = path[i][j] + (char) direction[k][2];
                    queue.offer(new int[]{x, y});
                }
            }
        }

        //bfs结束，球从起始位置(ball[0],ball[1])开始滚动，无法到达(hole[0],hole[1])，则返回"impossible"；
        //否则返回path[hole[0]][hole[1]]
        return distance[hole[0]][hole[1]] == Integer.MAX_VALUE ? "impossible" : path[hole[0]][hole[1]];
    }

    private void dfs(int i, int j, int[][] maze, int[] hole, int[][] distance, String[][] path, int[][] direction) {
        //球找下一个停止滚动的位置
        for (int k = 0; k < direction.length; k++) {
            int x = i;
            int y = j;
            //球从节点(i,j)滚动到节点(x,y)的路径长度
            int curDistance = 0;

            //球向一个方向滚动时，会一直滚动直至遇到墙或到达hole才停止，不会停留在空白位置
            while (x + direction[k][0] >= 0 && x + direction[k][0] < maze.length &&
                    y + direction[k][1] >= 0 && y + direction[k][1] < maze[0].length &&
                    maze[x + direction[k][0]][y + direction[k][1]] == 0 &&
                    !(x == hole[0] && y == hole[1])) {
                x = x + direction[k][0];
                y = y + direction[k][1];
                curDistance++;
            }

            //找到更小的distance[x][y]，或路径长度相等的情况下，当前路径的字典序更小，
            //更新distance[x][y]和path[x][y]，节点(x,y)加入队列
            //注意：direction[k][2]为int类型，必须转换为char类型拼接，否则拼接的是一个int整形数字
            if ((distance[i][j] + curDistance < distance[x][y]) ||
                    (distance[i][j] + curDistance == distance[x][y] &&
                            (path[i][j] + (char) direction[k][2]).compareTo(path[x][y]) < 0)) {
                distance[x][y] = distance[i][j] + curDistance;
                path[x][y] = path[i][j] + (char) direction[k][2];
                dfs(x, y, maze, hole, distance, path, direction);
            }
        }
    }
}
