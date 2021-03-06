package com.zhang.java;

/**
 * @Date 2022/4/30 9:26
 * @Author zsy
 * @Description 不同的二叉搜索树 类比Problem98
 * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？
 * 返回满足题意的二叉搜索树的种数。
 * <p>
 * 输入：n = 3
 * 输出：5
 * <p>
 * 输入：n = 1
 * 输出：1
 * <p>
 * 1 <= n <= 19
 */
public class Problem96 {
    public static void main(String[] args) {
        Problem96 problem96 = new Problem96();
        System.out.println(problem96.numTrees(4));
        System.out.println(problem96.numTrees2(4));
    }

    /**
     * 动态规划，时间复杂度O(n^2)，空间复杂度O(n)
     * dp[n]：n个节点二叉搜索树的个数
     * 当1为根节点时，左子树有0个节点，右子树有n-1个节点，此时二叉搜索树的个数为dp[0]*dp[n-1]
     * 当2为根节点时，左子树有1个节点，右子树有n-2个节点，此时二叉搜索树的个数为dp[1]*dp[n-2]
     * ...
     * 当n为根节点时，左子树有n-1个节点，右子树有0个节点，此时二叉搜索树的个数为dp[n-1]*dp[0]
     * dp[n] = Σ(i=0到n-1)dp[i]*dp[n-1-i]
     *
     * @param n
     * @return
     */
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= i - 1; j++) {
                dp[i] = dp[i] + dp[j] * dp[i - 1 - j];
            }
        }

        return dp[n];
    }

    /**
     * Catalan数，时间复杂度O(n)，空间复杂度O(1)
     * Cn+1 = 2(2n+1)/(n+2) * Cn
     * Cn = (2n,n)/(n+1) (2n,n)是2n中取n个的组合数
     *
     * @param n
     * @return
     */
    public int numTrees2(int n) {
        //使用long，避免乘法时溢出
        long result = 1;

        for (int i = 0; i < n; i++) {
            //除以i+1，则能保证整除；如果除以n-i，则不能保证整除
            result = result * (2L * n - i) / (i + 1);
        }

        result = result / (n + 1);

        return (int) result;
    }
}
