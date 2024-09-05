package com.zhang.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/12/21 08:27
 * @Author zsy
 * @Description 扫雷游戏 阿里机试题 类比Problem361、Problem2101 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem490、Problem499、Problem505、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12
 * 让我们一起来玩扫雷游戏！
 * 给你一个大小为 m x n 二维字符矩阵 board ，表示扫雷游戏的盘面，其中：
 * 'M' 代表一个 未挖出的 地雷，
 * 'E' 代表一个 未挖出的 空方块，
 * 'B' 代表没有相邻（上，下，左，右，和所有4个对角线）地雷的 已挖出的 空白方块，
 * 数字（'1' 到 '8'）表示有多少地雷与这块 已挖出的 方块相邻，
 * 'X' 则表示一个 已挖出的 地雷。
 * 给你一个整数数组 click ，其中 click = [clickr, clickc] 表示在所有 未挖出的 方块（'M' 或者 'E'）中的下一个点击位置（clickr 是行下标，clickc 是列下标）。
 * 根据以下规则，返回相应位置被点击后对应的盘面：
 * 如果一个地雷（'M'）被挖出，游戏就结束了- 把它改为 'X' 。
 * 如果一个 没有相邻地雷 的空方块（'E'）被挖出，修改它为（'B'），并且所有和其相邻的 未挖出 方块都应该被递归地揭露。
 * 如果一个 至少与一个地雷相邻 的空方块（'E'）被挖出，修改它为数字（'1' 到 '8' ），表示相邻地雷的数量。
 * 如果在此次点击中，若无更多方块可被揭露，则返回盘面。
 * <p>
 * 输入：board = [
 * ["E","E","E","E","E"],
 * ["E","E","M","E","E"],
 * ["E","E","E","E","E"],
 * ["E","E","E","E","E"]
 * ],
 * click = [3,0]
 * 输出：[
 * ["B","1","E","1","B"],
 * ["B","1","M","1","B"],
 * ["B","1","1","1","B"],
 * ["B","B","B","B","B"]
 * ]
 * <p>
 * 输入：board = [
 * ["B","1","E","1","B"],
 * ["B","1","M","1","B"],
 * ["B","1","1","1","B"],
 * ["B","B","B","B","B"]
 * ],
 * click = [1,2]
 * 输出：[
 * ["B","1","E","1","B"],
 * ["B","1","X","1","B"],
 * ["B","1","1","1","B"],
 * ["B","B","B","B","B"]
 * ]
 * <p>
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 50
 * board[i][j] 为 'M'、'E'、'B' 或数字 '1' 到 '8' 中的一个
 * click.length == 2
 * 0 <= clickr < m
 * 0 <= clickc < n
 * board[clickr][clickc] 为 'M' 或 'E'
 */
public class Problem529 {
    public static void main(String[] args) {
        Problem529 problem529 = new Problem529();
        char[][] board = {
                {'E', 'E', 'E', 'E', 'E'},
                {'E', 'E', 'M', 'E', 'E'},
                {'E', 'E', 'E', 'E', 'E'},
                {'E', 'E', 'E', 'E', 'E'},
        };
        int[] click = {3, 0};
        System.out.println(Arrays.deepToString(problem529.updateBoard(board, click)));
        System.out.println(Arrays.deepToString(problem529.updateBoard2(board, click)));
    }

    /**
     * dfs
     * 注意：当前节点周围八个位置不存在地雷，则更新当前节点为'B'，才能继续遍历；
     * 当前节点周围八个位置存在地雷，则更新当前节点为地雷的数量，停止遍历
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param board
     * @param click
     * @return
     */
    public char[][] updateBoard(char[][] board, int[] click) {
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }

        dfs(click[0], click[1], board);

        return board;
    }

    /**
     * bfs
     * 注意：当前节点周围八个位置不存在地雷，则更新当前节点为'B'，才能继续遍历；
     * 当前节点周围八个位置存在地雷，则更新当前节点为地雷的数量，停止遍历
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param board
     * @param click
     * @return
     */
    public char[][] updateBoard2(char[][] board, int[] click) {
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }

        Queue<int[]> queue = new LinkedList<>();
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        //注意：必须使用访问数组，避免同一节点重复入队超时
        boolean[][] visited = new boolean[board.length][board[0].length];
        queue.offer(new int[]{click[0], click[1]});
        visited[click[0]][click[1]] = true;

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            int i = arr[0];
            int j = arr[1];
            //节点(i,j)周围八个位置地雷的数量
            int count = 0;

            //计算节点(i,j)周围八个位置地雷的数量
            for (int k = 0; k < direction.length; k++) {
                int x = i + direction[k][0];
                int y = j + direction[k][1];

                //周围节点为'M'才是地雷
                if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != 'M') {
                    continue;
                }

                count++;
            }

            //当前节点(i,j)周围八个位置存在地雷，则更新当前节点(i,j)为地雷的数量
            if (count != 0) {
                board[i][j] = (char) ('0' + count);
            } else {
                //当前节点(i,j)周围八个位置不存在地雷，则标记当前节点(i,j)为'B'，周围八个位置继续bfs
                board[i][j] = 'B';

                //周围八个位置继续bfs
                for (int k = 0; k < direction.length; k++) {
                    int x = i + direction[k][0];
                    int y = j + direction[k][1];

                    //周围节点为'E'才能继续bfs，如果周围节点为'B'的话，则周围节点已经bfs过，不需要再次bfs，
                    //并且周围节点已经访问过，则周围节点不入队，避免同一节点重复入队超时
                    if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != 'E' || visited[x][y]) {
                        continue;
                    }

                    queue.offer(new int[]{x, y});
                    visited[x][y] = true;
                }
            }
        }

        return board;
    }

    private void dfs(int i, int j, char[][] board) {
        //节点(i,j)周围八个位置地雷的数量
        int count = 0;
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        //计算节点(i,j)周围八个位置地雷的数量
        for (int k = 0; k < direction.length; k++) {
            int x = i + direction[k][0];
            int y = j + direction[k][1];

            //周围节点为'M'才是地雷
            if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != 'M') {
                continue;
            }

            count++;
        }

        //当前节点(i,j)周围八个位置存在地雷，则更新当前节点(i,j)为地雷的数量
        if (count != 0) {
            board[i][j] = (char) ('0' + count);
        } else {
            //当前节点(i,j)周围八个位置不存在地雷，则标记当前节点(i,j)为'B'，周围八个位置继续dfs
            board[i][j] = 'B';

            //周围八个位置继续dfs
            for (int k = 0; k < direction.length; k++) {
                int x = i + direction[k][0];
                int y = j + direction[k][1];

                //周围节点为'E'才能继续dfs，如果周围节点为'B'的话，则周围节点已经dfs过，不需要再次dfs
                if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != 'E') {
                    continue;
                }

                dfs(x, y, board);
            }
        }
    }
}
