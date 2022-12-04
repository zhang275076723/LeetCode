package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/11/26 11:04
 * @Author zsy
 * @Description 生命游戏 类比Problem73、Problem130
 * 根据 百度百科 ， 生命游戏 ，简称为 生命 ，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
 * 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。
 * 每个细胞都具有一个初始状态： 1 即为 活细胞 （live），或 0 即为 死细胞 （dead）。
 * 每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
 * 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
 * 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
 * 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
 * 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
 * 下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。
 * 给你 m x n 网格面板 board 的当前状态，返回下一个状态。
 * <p>
 * 输入：board = [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
 * 输出：[[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
 * <p>
 * 输入：board = [[1,1],[1,0]]
 * 输出：[[1,1],[1,1]]
 * <p>
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 25
 * board[i][j] 为 0 或 1
 */
public class Problem289 {
    public static void main(String[] args) {
        Problem289 problem289 = new Problem289();
        int[][] board = {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}};
//        problem289.gameOfLife(board);
        problem289.gameOfLife2(board);
        System.out.println(Arrays.deepToString(board));
    }

    /**
     * 使用额外数组
     * 规则：
     * 1、活细胞，周围有2-3个活细胞，变为活细胞
     * 2、死细胞，周围有3个活细胞，变为活细胞
     * 3、其他情况均变为死细胞
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param board
     */
    public void gameOfLife(int[][] board) {
        int[][] tempArr = new int[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                tempArr[i][j] = board[i][j];
            }
        }

        //当前位置的8个相邻位置
        int[][] direction = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //当前细胞周围8个细胞的存活细胞数量
                int aliveCount = 0;

                //找周围活细胞数量
                for (int k = 0; k < direction.length; k++) {
                    if (i + direction[k][0] >= 0 && i + direction[k][0] < board.length &&
                            j + direction[k][1] >= 0 && j + direction[k][1] < board[0].length) {
                        aliveCount = aliveCount + tempArr[i + direction[k][0]][j + direction[k][1]];
                    }
                }

                //当前位置是活细胞，周围有2-3个活细胞，变为活细胞，否则变为死细胞
                if (tempArr[i][j] == 1) {
                    if (!(aliveCount == 2 || aliveCount == 3)) {
                        board[i][j] = 0;
                    }
                } else {
                    //当前位置是死细胞，周围有3个活细胞，变为活细胞，否则变为死细胞

                    if (aliveCount == 3) {
                        board[i][j] = 1;
                    }
                }
            }
        }
    }

    /**
     * 原地修改，使用标志位
     * 如果下一个状态细胞由死变活，赋值为2；如果下一个状态细胞由活变死，赋值为3
     * 最后再进行一次遍历，将2和3转化为1和0
     * 规则：
     * 1、活细胞，周围有2-3个活细胞，变为活细胞
     * 2、死细胞，周围有3个活细胞，变为活细胞
     * 3、其他情况均变为死细胞
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param board
     */
    public void gameOfLife2(int[][] board) {
        //当前位置的8个相邻位置
        int[][] direction = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //当前细胞周围8个细胞的存活细胞数量
                int aliveCount = 0;

                //找周围活细胞数量
                for (int k = 0; k < direction.length; k++) {
                    if (i + direction[k][0] >= 0 && i + direction[k][0] < board.length &&
                            j + direction[k][1] >= 0 && j + direction[k][1] < board[0].length) {
                        //周围细胞本来就是活细胞
                        if (board[i + direction[k][0]][j + direction[k][1]] == 1 ||
                                board[i + direction[k][0]][j + direction[k][1]] == 3) {
                            aliveCount++;
                        }
                    }
                }

                //当前位置是活细胞，周围有2-3个活细胞，变为活细胞，否则变为死细胞
                if (board[i][j] == 1) {
                    if (!(aliveCount == 2 || aliveCount == 3)) {
                        board[i][j] = 3;
                    }
                } else if (board[i][j] == 0) {
                    //当前位置是死细胞，周围有3个活细胞，变为活细胞，否则变为死细胞

                    if (aliveCount == 3) {
                        board[i][j] = 2;
                    }
                }
            }
        }

        //将上次发生变化的细胞状态2和3，转为1和0
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 1;
                } else if (board[i][j] == 3) {
                    board[i][j] = 0;
                }
            }
        }
    }
}
