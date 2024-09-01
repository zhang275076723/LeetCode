package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/8/8 7:57
 * @Author zsy
 * @Description N 皇后 II 类比Problem36、Problem37、Problem51、Problem1001 对角线类比Problem51、Problem498、Problem1001、Problem1329、Problem1424、Problem1572、Problem2319、Problem2614、Problem2711、Problem3000 回溯+剪枝类比
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
     * 使用数组存储皇后放置的位置，判断当前皇后和之前皇后是否冲突
     * 时间复杂度O(n*n!)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int totalNQueens(int n) {
        if (n == 1) {
            return 1;
        }

        //皇后的位置数组，visited[0]=3，表示第0个皇后放在第0行第3列
        int[] visited = new int[n];

        for (int i = 0; i < n; i++) {
            visited[i] = -1;
        }

        return backtrack(0, n, visited);
    }

    /**
     * 回溯+剪枝
     * 核心思想：左上到右下对角线上的元素的下标索引j-i相等，左下到右上对角线上的元素的下标索引i+j相等
     * 使用set存储皇后影响的行(可以省略)、列、左上到右下对角线(第j-i+n-1个对角线)、左下到右上对角线(第i+j个对角线)，判断是否冲突
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
        Set<Integer> colSet = new HashSet<>();
        //皇后影响的左上到右下对角线set
        Set<Integer> diagSet = new HashSet<>();
        //皇后影响的左下到右上对角线set
        Set<Integer> antiDiagSet = new HashSet<>();

        return backtrack2(0, n, colSet, diagSet, antiDiagSet);
    }

    private int backtrack(int t, int n, int[] visited) {
        if (t == n) {
            return 1;
        }

        int count = 0;

        for (int i = 0; i < n; i++) {
            visited[t] = i;

            //第t个皇后和之前0到t-1个皇后之间不冲突，继续往后查找
            if (!isConflict(t, visited)) {
                count = count + backtrack(t + 1, n, visited);
            }

            visited[t] = -1;
        }

        return count;
    }

    private int backtrack2(int t, int n, Set<Integer> colSet, Set<Integer> diagSet, Set<Integer> antiDiagSet) {
        if (t == n) {
            return 1;
        }

        int count = 0;

        for (int i = 0; i < n; i++) {
            //判断(t,i)放置皇后是否冲突
            if (colSet.contains(i) || diagSet.contains(i - t + n - 1) || antiDiagSet.contains(t + i)) {
                continue;
            }

            //当前皇后影响的行(可以省略)、列、左上到右下对角线、左下到右上对角线，加入set中
            colSet.add(i);
            diagSet.add(i - t + n - 1);
            antiDiagSet.add(t + i);

            count = count + backtrack2(t + 1, n, colSet, diagSet, antiDiagSet);

            antiDiagSet.remove(t + i);
            diagSet.remove(i - t + n - 1);
            colSet.remove(i);
        }

        return count;
    }

    /**
     * 第t个皇后和之前0到t-1个皇后之间是否冲突
     * 因为使用一维数组，不需要考虑行是否冲突，只考虑列和对角线是否冲突
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param t
     * @param visited
     * @return
     */
    private boolean isConflict(int t, int[] visited) {
        for (int i = 0; i < t; i++) {
            //两个皇后在同一行或同一列或同一对角线上，则冲突，返回true
            if (visited[i] == visited[t] || Math.abs(i - t) == Math.abs(visited[i] - visited[t])) {
                return true;
            }
        }

        //都不冲突，返回false
        return false;
    }
}
