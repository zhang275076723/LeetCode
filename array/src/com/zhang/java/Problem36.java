package com.zhang.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Date 2023/5/20 08:24
 * @Author zsy
 * @Description 有效的数独 类比Problem37、Problem51、Problem52、Problem1001
 * 请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
 * <p>
 * 注意：
 * 一个有效的数独（部分已被填充）不一定是可解的。
 * 只需要根据以上规则，验证已经填入的数字是否有效即可。
 * 空白格用 '.' 表示。
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
 * 输出：true
 * <p>
 * 输入：board = [
 * ["8","3",".",".","7",".",".",".","."],
 * ["6",".",".","1","9","5",".",".","."],
 * [".","9","8",".",".",".",".","6","."],
 * ["8",".",".",".","6",".",".",".","3"],
 * ["4",".",".","8",".","3",".",".","1"],
 * ["7",".",".",".","2",".",".",".","6"],
 * [".","6",".",".",".",".","2","8","."],
 * [".",".",".","4","1","9",".",".","5"],
 * [".",".",".",".","8",".",".","7","9"]
 * ]
 * 输出：false
 * 解释：除了第一行的第一个数字从 5 改为 8 以外，空格内其他数字均与 示例1 相同。
 * 但由于位于左上角的 3x3 宫内有两个 8 存在, 因此这个数独是无效的。
 * <p>
 * board.length == 9
 * board[i].length == 9
 * board[i][j] 是一位数字（1-9）或者 '.'
 */
public class Problem36 {
    public static void main(String[] args) {
        Problem36 problem36 = new Problem36();
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
        System.out.println(problem36.isValidSudoku(board));
    }

    /**
     * 模拟
     * 记录每行、每列、每个3x3格子中包含的字符，如果存在相同字符，则不是有效的数独
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=board.length, m=board[0].length)
     *
     * @param board
     * @return
     */
    public boolean isValidSudoku(char[][] board) {
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
                    continue;
                }

                //当前行或当前列或当前3x3格子内已经包含字符c，则不是有效的数独，直接返回false
                if (rowList.get(i).contains(c) || colList.get(j).contains(c) ||
                        blockList.get(i / 3 * 3 + j / 3).contains(c)) {
                    return false;
                }

                //当前字符c加入行、列、3x3格子set中
                rowList.get(i).add(c);
                colList.get(j).add(c);
                blockList.get(i / 3 * 3 + j / 3).add(c);
            }
        }

        //遍历结束，都没有发现不满足要求的字符，则是有效的数独，返回true
        return true;
    }
}
