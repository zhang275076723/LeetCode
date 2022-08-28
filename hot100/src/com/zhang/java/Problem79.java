package com.zhang.java;

/**
 * @Date 2022/4/27 9:17
 * @Author zsy
 * @Description 单词搜索 类比Problem200、Problem212 同Offer12
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
    /**
     * word是否存在于board中
     */
    private boolean isExist = false;

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
