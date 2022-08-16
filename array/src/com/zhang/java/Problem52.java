package com.zhang.java;

/**
 * @Date 2022/8/8 7:57
 * @Author zsy
 * @Description N皇后 II 类比Problem51
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n × n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给你一个整数 n ，返回 n 皇后问题 不同的解决方案的数量。
 * <p>
 * 输入：n = 4
 * 输出：2
 * 解释：如上图所示，4 皇后问题存在两个不同的解法。
 * <p>
 * 输入：n = 1
 * 输出：1
 * <p>
 * 1 <= n <= 9
 */
public class Problem52 {
    /**
     * n皇后不相互攻击的方案数
     */
    private int count = 0;

    public static void main(String[] args) {
        Problem52 problem52 = new Problem52();
        System.out.println(problem52.totalNQueens(8));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n*n^n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int totalNQueens(int n) {
        if (n == 1) {
            return 1;
        }

        backtrack(0, new int[n]);

        return count;
    }

    /**
     *
     * @param t
     * @param position position[0]=3表示第0个皇后放在第0行第3列
     */
    private void backtrack(int t, int[] position) {
        if (t == position.length) {
            count++;
            return;
        }

        for (int i = 0; i < position.length; i++) {
            position[t] = i;

            //当前放置第0行到第t行共t+1个皇后是否冲突，如果冲突直接剪枝
            if (!isConflict(t, position)) {
                backtrack(t + 1, position);
            }

            position[t] = 0;
        }
    }

    /**
     * 放置的第0行到第n行皇后是否冲突
     *
     * @param position
     * @return
     */
    private boolean isConflict(int n, int[] position) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                //在同一行、同一列、同一斜线上，相互攻击
                if (position[i] == position[j] || Math.abs(position[i] - position[j]) == Math.abs(i - j)) {
                    return true;
                }
            }
        }

        return false;
    }
}
