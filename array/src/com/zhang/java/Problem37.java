package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/5/20 08:57
 * @Author zsy
 * @Description 解数独 类比Problem36、Problem51、Problem52、Problem1001 回溯+剪枝类比
 * 编写一个程序，通过填充空格来解决数独问题。
 * 数独的解法需 遵循如下规则：
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
 * 数独部分空格内已填入了数字，空白格用 '.' 表示。
 * <p>
 * 输入：board = [
 * ["5","3",".",".","7",".",".",".","."],
 * ["6",".",".","1","9","5",".",".","."],
 * [".","9","8",".",".",".",".","6","."],
 * ["8",".",".",".","6",".",".",".","3"],
 * ["4",".",".","8",".","3",".",".","1"],
 * ["7",".",".",".","2",".",".",".","6"],
 * [".","6",".",".",".",".","2","8","."],
 * [".",".",".","4","1","9",".",".","5"],
 * [".",".",".",".","8",".",".","7","9"]
 * ]
 * 输出：[
 * ["5","3","4","6","7","8","9","1","2"],
 * ["6","7","2","1","9","5","3","4","8"],
 * ["1","9","8","3","4","2","5","6","7"],
 * ["8","5","9","7","6","1","4","2","3"],
 * ["4","2","6","8","5","3","7","9","1"],
 * ["7","1","3","9","2","4","8","5","6"],
 * ["9","6","1","5","3","7","2","8","4"],
 * ["2","8","7","4","1","9","6","3","5"],
 * ["3","4","5","2","8","6","1","7","9"]
 * ]
 * 解释：输入的数独如上图所示，唯一有效的解决方案如下所示：
 * <p>
 * board.length == 9
 * board[i].length == 9
 * board[i][j] 是一位数字或者 '.'
 * 题目数据 保证 输入数独仅有一个解
 */
public class Problem37 {
    public static void main(String[] args) {
        Problem37 problem37 = new Problem37();
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        problem37.solveSudoku(board);
        System.out.println(Arrays.deepToString(board));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(9^(9*9))，空间复杂度O(9*9)
     *
     * @param board
     */
    public void solveSudoku(char[][] board) {
        //行中包含的字符set
        List<Set<Character>> rowList = new ArrayList<>();
        //列中包含的字符set
        List<Set<Character>> colList = new ArrayList<>();
        //3x3格子中包含的字符set
        List<Set<Character>> blockList = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            rowList.add(new HashSet<>());
        }

        for (int j = 0; j < board[0].length; j++) {
            colList.add(new HashSet<>());
        }

        for (int i = 0; i < board.length / 3; i++) {
            for (int j = 0; j < board[0].length / 3; j++) {
                blockList.add(new HashSet<>());
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char c = board[i][j];
                if (c != '.') {
                    rowList.get(i).add(c);
                    colList.get(j).add(c);
                    blockList.get(i / 3 * 3 + j / 3).add(c);
                }
            }
        }

        //从左上角(0,0)开始遍历
        backtrack(0, 0, board, rowList, colList, blockList);
    }

    /**
     * 按照从左往右，从上往下回溯遍历
     *
     * @param i
     * @param j
     * @param board
     * @param rowList
     * @param colList
     * @param blockList
     * @return
     */
    private boolean backtrack(int i, int j, char[][] board,
                              List<Set<Character>> rowList,
                              List<Set<Character>> colList,
                              List<Set<Character>> blockList) {
        //已经遍历完当前行，判断是否还有下一行
        if (j == board[0].length) {
            //还有下一行，继续往下一行遍历
            if (i < board.length - 1) {
                return backtrack(i + 1, 0, board, rowList, colList, blockList);
            } else {
                //已经遍历完最后一行，则直接返回true
                return true;
            }
        }

        //当前位置已经有数字，继续往下一个位置遍历
        if (board[i][j] != '.') {
            return backtrack(i, j + 1, board, rowList, colList, blockList);
        } else {
            for (char num = '1'; num <= '9'; num++) {
                //当前行或当前列或已经包含num，则不满足数独，直接进行下次循环
                if (rowList.get(i).contains(num) || colList.get(j).contains(num) ||
                        blockList.get(i / 3 * 3 + j / 3).contains(num)) {
                    continue;
                }

                board[i][j] = num;
                rowList.get(i).add(num);
                colList.get(j).add(num);
                blockList.get(i / 3 * 3 + j / 3).add(num);

                if (backtrack(i, j + 1, board, rowList, colList, blockList)) {
                    return true;
                }

                blockList.get(i / 3 * 3 + j / 3).remove(num);
                colList.get(j).remove(num);
                rowList.get(i).remove(num);
                board[i][j] = '.';
            }

            //当前位置遍历了'1'-'9'，都不满足数独，则返回false
            return false;
        }
    }
}
