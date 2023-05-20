package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/8/8 7:57
 * @Author zsy
 * @Description N皇后 II 类比Problem36、Problem37、Problem51、Problem1001
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
    public static void main(String[] args) {
        Problem52 problem52 = new Problem52();
        System.out.println(problem52.totalNQueens(8));
        System.out.println(problem52.totalNQueens2(8));
    }

    /**
     * 回溯+剪枝
     * 使用数组存储皇后放置的位置，判断是否冲突
     * 时间复杂度O(n*n!))，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int totalNQueens(int n) {
        if (n == 1) {
            return 1;
        }

        return backtrack(0, n, new int[n]);
    }

    /**
     * 回溯+剪枝
     * 使用set存储皇后影响的行(可以省略)、列、左上右下对角线(第j-i+n-1个对角线)、左下右上对角线(第i+j个对角线)，判断是否冲突
     * 时间复杂度O(n!)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int totalNQueens2(int n) {
        if (n == 1) {
            return 1;
        }

        //皇后影响的列set
        Set<Integer> columnSet = new HashSet<>();
        //皇后影响的左上右下对角线set
        Set<Integer> diagSet = new HashSet<>();
        //皇后影响的左下右上对角线set
        Set<Integer> antiDiagSet = new HashSet<>();

        return backtrack2(0, n, columnSet, diagSet, antiDiagSet);
    }

    /**
     * @param t
     * @param n
     * @param position position[0]=3，表示第0个皇后放在第0行第3列
     * @return
     */
    private int backtrack(int t, int n, int[] position) {
        if (t == n) {
            return 1;
        }

        int count = 0;

        for (int i = 0; i < n; i++) {
            position[t] = i;

            //第0行到第t行共t+1个皇后不冲突，才继续往后查找
            if (!isConflict(t, position)) {
                count = count + backtrack(t + 1, n, position);
            }
        }

        return count;
    }

    private int backtrack2(int t, int n, Set<Integer> columnSet, Set<Integer> diagSet, Set<Integer> antiDiagSet) {
        if (t == n) {
            return 1;
        }

        int count = 0;

        for (int i = 0; i < n; i++) {
            //判断(t,i)放置皇后是否冲突
            if (columnSet.contains(i) || diagSet.contains(i - t + n - 1) || antiDiagSet.contains(t + i)) {
                continue;
            }

            //当前皇后影响的行(可以省略)、列、左上右下对角线、左下右上对角线，加入set中
            columnSet.add(i);
            diagSet.add(i - t + n - 1);
            antiDiagSet.add(t + i);

            count = count + backtrack2(t + 1, n, columnSet, diagSet, antiDiagSet);

            antiDiagSet.remove(t + i);
            diagSet.remove(i - t + n - 1);
            columnSet.remove(i);
        }

        return count;
    }

    /**
     * 第t个皇后和之前0到t-1个皇后之间是否冲突
     *
     * @param t
     * @param position
     * @return
     */
    private boolean isConflict(int t, int[] position) {
        for (int i = 0; i < t; i++) {
            //两个皇后在同一行或同一列或同一斜线上，则会相互攻击，返回true
            if (position[i] == position[t] || Math.abs(i - t) == Math.abs(position[i] - position[t])) {
                return true;
            }
        }

        //都不冲突，返回false
        return false;
    }
}
