package com.zhang.java;

/**
 * @Date 2025/10/27 17:45
 * @Author zsy
 * @Description 栅栏涂色 类比Problem256、Problem265、Problem1473 动态规划类比
 * 有 k 种颜色的涂料和一个包含 n 个栅栏柱的栅栏，请你按下述规则为栅栏设计涂色方案：
 * 每个栅栏柱可以用其中 一种 颜色进行上色。
 * 相邻的栅栏柱 最多连续两个 颜色相同。
 * 给你两个整数 k 和 n ，返回所有有效的涂色 方案数 。
 * <p>
 * 输入：n = 3, k = 2
 * 输出：6
 * 解释：所有的可能涂色方案如上图所示。注意，全涂红或者全涂绿的方案属于无效方案，因为相邻的栅栏柱 最多连续两个 颜色相同。
 * <p>
 * 输入：n = 1, k = 1
 * 输出：1
 * <p>
 * 输入：n = 7, k = 2
 * 输出：42
 * <p>
 * 1 <= n <= 50
 * 1 <= k <= 10^5
 * 题目数据保证：对于输入的 n 和 k ，其答案在范围 [0, 2^31 - 1] 内
 */
public class Problem276 {
    public static void main(String[] args) {
        Problem276 problem276 = new Problem276();
        int n = 7;
        int k = 2;
        System.out.println(problem276.numWays(n, k));
        System.out.println(problem276.numWays2(n, k));
        System.out.println(problem276.numWays3(n, k));
    }

    /**
     * 动态规划
     * dp1[i]：前i个栅栏最后两个栅栏颜色不同的方案数
     * dp2[i]：前i个栅栏最后两个栅栏颜色相同的方案数
     * dp1[i] = dp1[i-1]*(k-1) + dp2[i-1]*(k-1)
     * dp2[i] = dp1[i-1]
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param k
     * @return
     */
    public int numWays(int n, int k) {
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];

        dp1[0] = k;
        dp2[0] = 0;

        for (int i = 1; i < n; i++) {
            dp1[i] = dp1[i - 1] * (k - 1) + dp2[i - 1] * (k - 1);
            dp2[i] = dp1[i - 1];
        }

        return dp1[n - 1] + dp2[n - 1];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @param k
     * @return
     */
    public int numWays2(int n, int k) {
        //前i个栅栏最后两个栅栏颜色不同的方案数
        int p = k;
        //前i个栅栏最后两个栅栏颜色相同的方案数
        int q = 0;

        for (int i = 1; i < n; i++) {
            int temp = p;
            p = p * (k - 1) + q * (k - 1);
            q = temp;
        }

        return p + q;
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param k
     * @return
     */
    public int numWays3(int n, int k) {
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];

        for (int i = 0; i < n; i++) {
            dp1[i] = -1;
            dp2[i] = -1;
        }

        dfs(n - 1, k, dp1, dp2);

        return dp1[n - 1] + dp2[n - 1];
    }

    private void dfs(int i, int k, int[] dp1, int[] dp2) {
        if (i == 0) {
            dp1[0] = k;
            dp2[0] = 0;
            return;
        }

        if (dp1[i] != -1) {
            return;
        }

        dfs(i - 1, k, dp1, dp2);

        dp1[i] = dp1[i - 1] * (k - 1) + dp2[i - 1] * (k - 1);
        dp2[i] = dp1[i - 1];
    }
}
