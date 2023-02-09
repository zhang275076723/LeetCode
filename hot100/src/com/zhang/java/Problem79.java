package com.zhang.java;

/**
 * @Date 2022/4/27 9:17
 * @Author zsy
 * @Description 单词搜索 dfs类比Problem200、Problem212、Problem695、Problem827 同Offer12
 * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。
 * 如果 word 存在于网格中，返回 true ；否则，返回 false 。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
 * 同一个单元格内的字母不允许被重复使用。
 * <p>
 * 输入：board = [
 * ["A","B","C","E"],
 * ["S","F","C","S"],
 * ["A","D","E","E"]
 * ], word = "ABCCED"
 * 输出：true
 * <p>
 * 输入：board = [
 * ["A","B","C","E"],
 * ["S","F","C","S"],
 * ["A","D","E","E"]
 * ], word = "SEE"
 * 输出：true
 * <p>
 * 输入：board = [
 * ["A","B","C","E"],
 * ["S","F","C","S"],
 * ["A","D","E","E"]
 * ], word = "ABCB"
 * 输出：false
 * <p>
 * m == board.length
 * n = board[i].length
 * 1 <= m, n <= 6
 * 1 <= word.length <= 15
 * board 和 word 仅由大小写英文字母组成
 */
public class Problem79 {
    public static void main(String[] args) {
        Problem79 problem79 = new Problem79();
        char[][] board = new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String word = "ABCCED";
        System.out.println(problem79.exist(board, word));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(mn*(4^l))，空间复杂度O(mn) (m = board.length, n = board[0].length, l = word.length())
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        if (board.length * board[0].length < word.length()) {
            return false;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //第一个字符相等才开始查找
                if (board[i][j] == word.charAt(0)) {
                    boolean flag = backtrack(0, i, j, new boolean[board.length][board[0].length], board, word);
                    if (flag) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean backtrack(int t, int i, int j, boolean[][] visited, char[][] board, String word) {
        //遍历到末尾，返回true
        if (t == word.length()) {
            return true;
        }

        //当前位置越界，或当前位置已经被访问，或当前位置字符和word中第t个字符不同时，直接剪枝，返回false
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length ||
                visited[i][j] || word.charAt(t) != board[i][j]) {
            return false;
        }

        visited[i][j] = true;

        //往上下左右找
        boolean flag = backtrack(t + 1, i - 1, j, visited, board, word) ||
                backtrack(t + 1, i + 1, j, visited, board, word) ||
                backtrack(t + 1, i, j - 1, visited, board, word) ||
                backtrack(t + 1, i, j + 1, visited, board, word);

        visited[i][j] = false;

        return flag;
    }
}
