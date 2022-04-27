package com.zhang.java;

/**
 * @Date 2022/4/27 9:17
 * @Author zsy
 * @Description 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。
 * 如果 word 存在于网格中，返回 true ；否则，返回 false 。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
 * 同一个单元格内的字母不允许被重复使用。
 * <p>
 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 * 输出：true
 * <p>
 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
 * 输出：true
 * <p>
 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
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
        char[][] board = new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        String word = "ABCCED";
        System.out.println(problem79.exist(board, word));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(mn*3^l)，空间复杂度O(mn)，使用额外的访问数组visited，栈深度min(mn, l)
     * m = board.length
     * n = board[0].length
     * l = word.length()
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }
        if (board.length * board[0].length < word.length()) {
            return false;
        }

        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //看哪个起始位置，满足要求
                if (backtrack(i, j, visited, board, word, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param i         当前位置行
     * @param j         当前位置列
     * @param visited   访问数组
     * @param board     网格
     * @param word      单词，判断网格中是否有满足要求的单词
     * @param wordIndex 当前单词索引位置
     * @return
     */
    private boolean backtrack(int i, int j, boolean[][] visited, char[][] board,
                              String word, int wordIndex) {
        //不满足要求的情况，直接剪枝
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length ||
                visited[i][j] || board[i][j] != word.charAt(wordIndex)) {
            return false;
        }

        //wordIndex是word的最后一个位置，且相等，返回true
        if (wordIndex == word.length() - 1) {
            return true;
        }

        visited[i][j] = true;
        boolean flag = backtrack(i, j - 1, visited, board, word, wordIndex + 1) ||
                backtrack(i - 1, j, visited, board, word, wordIndex + 1) ||
                backtrack(i, j + 1, visited, board, word, wordIndex + 1) ||
                backtrack(i + 1, j, visited, board, word, wordIndex + 1);
        visited[i][j] = false;
        return flag;
    }
}
