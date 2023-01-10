package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/11/29 15:53
 * @Author zsy
 * @Description 被围绕的区域 类比Problem73、Problem289
 * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，
 * 找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
 * <p>
 * 输入：board = [
 * ["X","X","X","X"],
 * ["X","O","O","X"],
 * ["X","X","O","X"],
 * ["X","O","X","X"]
 * ]
 * 输出：[
 * ["X","X","X","X"],
 * ["X","X","X","X"],
 * ["X","X","X","X"],
 * ["X","O","X","X"]
 * ]
 * 解释：被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。
 * 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。
 * 如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
 * <p>
 * 输入：board = [["X"]]
 * 输出：[["X"]]
 * <p>
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 200
 * board[i][j] 为 'X' 或 'O'
 */
public class Problem130 {
    public static void main(String[] args) {
        Problem130 problem130 = new Problem130();
        char[][] board = {
                {'X', 'X', 'X', 'X'},
                {'X', 'O', 'O', 'X'},
                {'X', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'X'}
        };
//        char[][] board = {
//                {'X', 'O', 'O', 'X', 'X', 'X', 'O', 'X', 'O', 'O'},
//                {'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
//                {'X', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'X', 'X'},
//                {'X', 'O', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'O'},
//                {'O', 'X', 'X', 'X', 'O', 'X', 'O', 'X', 'O', 'X'},
//                {'X', 'X', 'O', 'X', 'X', 'O', 'O', 'X', 'X', 'X'},
//                {'O', 'X', 'X', 'O', 'O', 'X', 'O', 'X', 'X', 'O'},
//                {'O', 'X', 'X', 'X', 'X', 'X', 'O', 'X', 'X', 'X'},
//                {'X', 'O', 'O', 'X', 'X', 'O', 'X', 'X', 'O', 'O'},
//                {'X', 'X', 'X', 'O', 'O', 'X', 'O', 'X', 'X', 'O'}
//        };
//        problem130.solve(board);
        problem130.solve2(board);
    }

    /**
     * dfs
     * 从边界'O'dfs，将相连的'O'置为'Y'，表示当前的'O'是外部未被'X'围绕的区域，其余剩下的'O'都是需要置为'X'，
     * 遍历内部的元素'O'，将剩余'O'置为'X'，此时的'O'是被'X'围绕的区域，再将'Y'重新置为'O'
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param board
     */
    public void solve(char[][] board) {
        //边界'O'dfs，将相连的'O'置为'Y'
        for (int i = 0; i < board.length; i++) {
            dfs(i, 0, board);
            dfs(i, board[0].length - 1, board);
        }

        //边界'O'dfs，将相连的'O'置为'Y'
        for (int j = 1; j < board[0].length - 1; j++) {
            dfs(0, j, board);
            dfs(board.length - 1, j, board);
        }

        //将剩余未被置为'Y'的'O'置为'X'，此时的'O'是被'X'围绕的区域，将被置为'Y'的'O'重新置为'O'
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'Y') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    /**
     * bfs
     * 边界'O'加入队列，bfs边界'O'，将相连的'O'置为'Y'，表示当前的'O'是外部未被'X'围绕的区域，其余剩下的'O'都是需要置为'X'，
     * 将剩余'O'置为'X'，此时的'O'是被'X'围绕的区域，再将'Y'重新置为'O'
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param board
     */
    public void solve2(char[][] board) {
        Queue<int[]> queue = new LinkedList<>();

        //边界'O'加入队列
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == 'O') {
                queue.offer(new int[]{i, 0});
            }

            if (board[i][board[0].length - 1] == 'O') {
                queue.offer(new int[]{i, board[0].length - 1});
            }
        }

        //边界'O'加入队列
        for (int j = 1; j < board[0].length - 1; j++) {
            if (board[0][j] == 'O') {
                queue.offer(new int[]{0, j});
            }

            if (board[board.length - 1][j] == 'O') {
                queue.offer(new int[]{board.length - 1, j});
            }
        }

        //bfs边界'O'，将相连的'O'置为'Y'，表示当前的'O'是外部未被'X'围绕的区域
        while (!queue.isEmpty()) {
            int[] arr = queue.poll();

            if (arr[0] < 0 || arr[0] >= board.length || arr[1] < 0 || arr[1] >= board[0].length ||
                    board[arr[0]][arr[1]] != 'O') {
                continue;
            }

            board[arr[0]][arr[1]] = 'Y';

            queue.offer(new int[]{arr[0] - 1, arr[1]});
            queue.offer(new int[]{arr[0] + 1, arr[1]});
            queue.offer(new int[]{arr[0], arr[1] - 1});
            queue.offer(new int[]{arr[0], arr[1] + 1});
        }

        //将剩余未被置为'Y'的'O'置为'X'，此时的'O'是被'X'围绕的区域，将被置为'Y'的'O'重新置为'O'
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'Y') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void dfs(int i, int j, char[][] board) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != 'O') {
            return;
        }

        board[i][j] = 'Y';

        //往上下左右找
        dfs(i - 1, j, board);
        dfs(i + 1, j, board);
        dfs(i, j - 1, board);
        dfs(i, j + 1, board);
    }
}
