package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/8/8 8:57
 * @Author zsy
 * @Description N 皇后 类比problem52
 * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * <p>
 * 输入：n = 4
 * 输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 * 解释：如上图所示，4 皇后问题存在两个不同的解法。
 * <p>
 * 输入：n = 1
 * 输出：[["Q"]]
 * <p>
 * 1 <= n <= 9
 */
public class Problem51 {
    public static void main(String[] args) {
        Problem51 problem51 = new Problem51();
        System.out.println(problem51.solveNQueens(4));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n*n^n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();

        backtrack(0, n, new ArrayList<>(), result);

        return result;
    }

    private void backtrack(int t, int n, List<String> list, List<List<String>> result) {
        if (t == n) {
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < i; j++) {
                sb.append('.');
            }

            //当前皇后所在位置
            sb.append('Q');

            for (int j = i + 1; j < n; j++) {
                sb.append('.');
            }

            list.add(sb.toString());

            if (!isConflict(list)) {
                backtrack(t + 1, n, list, result);
            }

            list.remove(list.size() - 1);
        }
    }

    /**
     * list中存放的皇后是否冲突
     *
     * @param list
     * @return
     */
    private boolean isConflict(List<String> list) {
        int[] position = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);

            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == 'Q') {
                    position[i] = j;
                    break;
                }
            }
        }

        for (int i = 0; i < position.length - 1; i++) {
            for (int j = i + 1; j < position.length; j++) {
                //在同一行、同一列、同一斜线上，相互攻击
                if (position[i] == position[j] || Math.abs(position[i] - position[j]) == Math.abs(i - j)) {
                    return true;
                }
            }
        }

        return false;
    }
}
