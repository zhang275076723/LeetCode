package com.zhang.java;

/**
 * @Date 2022/3/14 16:48
 * @Author zsy
 * @Description 矩阵中的路径 dfs和bfs类比Problem79、Problem130、Problem200、Problem212、Problem463、Problem490、Problem499、Problem505、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905 同Problem79
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
                if (backtrack(0, i, j, new boolean[board.length][board[0].length], board, word)) {
                    return true;
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
