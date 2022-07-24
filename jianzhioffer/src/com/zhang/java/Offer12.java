package com.zhang.java;

/**
 * @Date 2022/3/14 16:48
 * @Author zsy
 * @Description 矩阵中的路径 同Problem79
 * 给定一个m x n二维字符网格board和一个字符串单词word。
 * 如果word存在于网格中，返回true；否则，返回false。
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
 * ["a","b"],
 * ["c","d"]
 * ], word = "abcd"
 * 输出：false
 * <p>
 * 1 <= board.length <= 200
 * 1 <= board[i].length <= 200
 * board 和 word 仅由大小写英文字母组成
 */
public class Offer12 {
    public static void main(String[] args) {
        Offer12 offer12 = new Offer12();
        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String word = "ABCCED";
        System.out.println(offer12.exist(board, word));
    }

    /**
     * 回溯+剪枝
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }

        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (backtrack(board, word, visited, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param board     网格
     * @param word      单词
     * @param visited   网格访问数组
     * @param i         网格行
     * @param j         网格列
     * @param wordIndex 单词开始索引
     * @return
     */
    public boolean backtrack(char[][] board, String word, boolean[][] visited, int i, int j, int wordIndex) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length ||
                visited[i][j] || word.charAt(wordIndex) != board[i][j]) {
            return false;
        }

        if (wordIndex == word.length() - 1) {
            return true;
        }

        visited[i][j] = true;

        //从当前顶点，上下左右查找
        boolean flag = backtrack(board, word, visited, i - 1, j, wordIndex + 1) ||
                backtrack(board, word, visited, i + 1, j, wordIndex + 1) ||
                backtrack(board, word, visited, i, j - 1, wordIndex + 1) ||
                backtrack(board, word, visited, i, j + 1, wordIndex + 1);

        visited[i][j] = false;

        return flag;
    }
}
