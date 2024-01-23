package com.zhang.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Date 2022/8/8 8:57
 * @Author zsy
 * @Description N 皇后 类比Problem36、Problem37、Problem52、Problem1001 对角线类比Problem52、Problem498、Problem1001、Problem1329、Problem1424、Problem1572、Problem2614、Problem2711、Problem3000 回溯+剪枝类比
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
        System.out.println(problem51.solveNQueens2(4));
    }

    /**
     * 回溯+剪枝
     * 使用数组存储皇后放置的位置，判断当前皇后和之前皇后是否冲突
     * 时间复杂度O(n*n!)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();

        int[] visited = new int[n];

        for (int i = 0; i < n; i++) {
            visited[i] = -1;
        }

        backtrack(0, n, visited, result);

        return result;
    }

    /**
     * 回溯+剪枝
     * 核心思想：左上到右下对角线上的元素的下标索引j-i相等，左下到右上对角线上的元素下标索引i+j相等
     * 使用set存储皇后影响的行(可以省略)、列、左上到右下对角线(第j-i+n-1个对角线)、左下到右上对角线(第i+j个对角线)，判断是否冲突
     * 时间复杂度O(n*n!)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens2(int n) {
        List<List<String>> result = new ArrayList<>();
        //皇后影响的列set
        Set<Integer> colSet = new HashSet<>();
        //皇后影响的左上到右下对角线set
        Set<Integer> diagSet = new HashSet<>();
        //皇后影响的左下到右上对角线set
        Set<Integer> antiDiagSet = new HashSet<>();

        backtrack2(0, n, colSet, diagSet, antiDiagSet, new ArrayList<>(), result);

        return result;
    }

    private void backtrack(int t, int n, int[] visited, List<List<String>> result) {
        if (t == n) {
            //根据visited得到当前n皇后方案
            List<String> list = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();

                //当前皇后之前的'.'
                for (int j = 0; j < visited[i]; j++) {
                    sb.append('.');
                }

                //当前皇后所在位置
                sb.append('Q');

                //当前皇后之后的'.'
                for (int j = visited[i] + 1; j < n; j++) {
                    sb.append('.');
                }

                list.add(sb.toString());
            }

            result.add(list);
            return;
        }

        for (int i = 0; i < n; i++) {
            visited[t] = i;

            //第0行到第t行共t+1个皇后不冲突，才继续往后查找
            if (!isConflict(t, visited)) {
                backtrack(t + 1, n, visited, result);
            }

            visited[t] = -1;
        }
    }

    private void backtrack2(int t, int n, Set<Integer> colSet, Set<Integer> diagSet, Set<Integer> antiDiagSet,
                            List<String> list, List<List<String>> result) {
        if (t == n) {
            result.add(new ArrayList<>(list));
            return;
        }

        //判断(t,i)放置皇后是否冲突
        for (int i = 0; i < n; i++) {
            if (colSet.contains(i) || diagSet.contains(i - t + n - 1) || antiDiagSet.contains(t + i)) {
                continue;
            }

            StringBuilder sb = new StringBuilder();

            //当前皇后之前的'.'
            for (int j = 0; j < i; j++) {
                sb.append('.');
            }

            //当前皇后所在位置
            sb.append('Q');

            //当前皇后之后的'.'
            for (int j = i + 1; j < n; j++) {
                sb.append('.');
            }

            list.add(sb.toString());
            //当前皇后影响的行(可以省略)、列、左上到右下对角线、左下到右上对角线，加入set中
            colSet.add(i);
            diagSet.add(i - t + n - 1);
            antiDiagSet.add(t + i);

            backtrack2(t + 1, n, colSet, diagSet, antiDiagSet, list, result);

            antiDiagSet.remove(t + i);
            diagSet.remove(i - t + n - 1);
            colSet.remove(i);
            list.remove(list.size() - 1);
        }
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
