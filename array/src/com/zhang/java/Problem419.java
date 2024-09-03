package com.zhang.java;

/**
 * @Date 2024/12/23 08:31
 * @Author zsy
 * @Description 棋盘上的战舰 类比Problem463
 * 给你一个大小为 m x n 的矩阵 board 表示棋盘，其中，每个单元格可以是一艘战舰 'X' 或者是一个空位 '.' ，
 * 返回在棋盘 board 上放置的 舰队 的数量。
 * 舰队 只能水平或者垂直放置在 board 上。
 * 换句话说，舰队只能按 1 x k（1 行，k 列）或 k x 1（k 行，1 列）的形状放置，其中 k 可以是任意大小。
 * 两个舰队之间至少有一个水平或垂直的空格分隔 （即没有相邻的舰队）。
 * 进阶：你可以实现一次扫描算法，并只使用 O(1) 额外空间，并且不修改 board 的值来解决这个问题吗？
 * <p>
 * 输入：board = [["X",".",".","X"],[".",".",".","X"],[".",".",".","X"]]
 * 输出：2
 * <p>
 * 输入：board = [["."]]
 * 输出：0
 * <p>
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 200
 * board[i][j] 是 '.' 或 'X'
 */
public class Problem419 {
    public static void main(String[] args) {
        Problem419 problem419 = new Problem419();
        char[][] board = {
                {'X', '.', '.', 'X'},
                {'.', '.', '.', 'X'},
                {'.', '.', '.', 'X'}
        };
        System.out.println(problem419.countBattleships(board));
        //对board原数组进行了修改，所以board需要重新赋值
        System.out.println(problem419.countBattleships2(board));
        board = new char[][]{
                {'X', '.', '.', 'X'},
                {'.', '.', '.', 'X'},
                {'.', '.', '.', 'X'}
        };
        System.out.println(problem419.countBattleships3(board));
    }

    /**
     * 模拟
     * 一次遍历，使用额外空间，不修改board
     * 时间复杂度O(mn*(m+n))，空间复杂度O(mn)
     *
     * @param board
     * @return
     */
    public int countBattleships(char[][] board) {
        int count = 0;
        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'X' && !visited[i][j]) {
                    count++;
                    visited[i][j] = true;

                    //board[i][j]垂直方向的'X'置为已访问
                    for (int k = i + 1; k < board.length; k++) {
                        if (board[k][j] == 'X') {
                            visited[k][j] = true;
                        } else {
                            break;
                        }
                    }

                    //board[i][j]水直方向的'X'置为已访问
                    for (int k = j + 1; k < board[0].length; k++) {
                        if (board[i][k] == 'X') {
                            visited[i][k] = true;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return count;
    }

    /**
     * 模拟
     * 一次遍历，不使用额外空间，原数组作为访问数组，修改了board
     * 时间复杂度O(mn*(m+n))，空间复杂度O(1)
     *
     * @param board
     * @return
     */
    public int countBattleships2(char[][] board) {
        int count = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'X') {
                    count++;
                    //置为'#"，表示已访问
                    board[i][j] = '#';

                    //board[i][j]垂直方向的'X'置为'#'
                    for (int k = i + 1; k < board.length; k++) {
                        if (board[k][j] == 'X') {
                            board[k][j] = '#';
                        } else {
                            break;
                        }
                    }

                    //board[i][j]水平方向的'X'置为'#'
                    for (int k = j + 1; k < board[0].length; k++) {
                        if (board[i][k] == 'X') {
                            board[i][k] = '#';
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return count;
    }

    /**
     * 模拟
     * 一次遍历，不使用额外空间，不修改board
     * 核心思想：只考虑水平或垂直舰队的第一个'X'
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param board
     * @return
     */
    public int countBattleships3(char[][] board) {
        int count = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'X') {
                    //当前'X'不是水平或垂直舰队的第一个'X'，则当前舰队已经考虑，直接进行下次循环
                    if (i - 1 >= 0 && board[i - 1][j] == 'X') {
                        continue;
                    }

                    //当前'X'不是水平或垂直舰队的第一个'X'，则当前舰队已经考虑，直接进行下次循环
                    if (j - 1 >= 0 && board[i][j - 1] == 'X') {
                        continue;
                    }

                    //当前'X'是水平或垂直舰队的第一个'X'，则统计当前舰队
                    count++;
                }
            }
        }

        return count;
    }
}
