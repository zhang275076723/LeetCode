package com.zhang.java;

/**
 * @Date 2022/3/14 16:48
 * @Author zsy
 * @Description 矩阵中的路径 类比problem200、Problem212 同Problem79
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
    /**
     * word是否存在于board中
     */
    private boolean isExist = false;

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
     * 时间复杂度O(mn*4^l)，空间复杂度O(mn) (m = board.length, n = board[0].length, l = word.length())
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        if (board.length * board[0].length < word.length()) {
            return false;
        }

        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(i, j, 0, board, word, visited);

                //已经找到，则直接返回true
                if (isExist) {
                    return true;
                }
            }
        }

        return false;
    }

    private void dfs(int i, int j, int t, char[][] board, String word, boolean[][] visited) {
        //不满足要求，直接剪枝，返回false
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length ||
                visited[i][j] || word.charAt(t) != board[i][j]) {
            return;
        }

        //已经遍历到最后一个字母，返回true
        if (t == word.length() - 1) {
            isExist = true;
            return;
        }

        //已经找到，直接返回，相当于剪枝
        if (isExist) {
            return;
        }

        visited[i][j] = true;

        //从当前位置往上下左右遍历
        dfs(i - 1, j, t + 1, board, word, visited);
        dfs(i + 1, j, t + 1, board, word, visited);
        dfs(i, j - 1, t + 1, board, word, visited);
        dfs(i, j + 1, t + 1, board, word, visited);

        visited[i][j] = false;
    }
}
