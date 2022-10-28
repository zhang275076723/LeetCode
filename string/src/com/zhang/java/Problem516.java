package com.zhang.java;

/**
 * @Date 2022/9/17 11:43
 * @Author zsy
 * @Description 最长回文子序列 类比Problem5、Problem9、Problem131、Problem214、Problem234、Problem647 (注意和最长回文子串的区别)
 * 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
 * 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
 * <p>
 * 输入：s = "bbbab"
 * 输出：4
 * 解释：一个可能的最长回文子序列为 "bbbb" 。
 * <p>
 * 输入：s = "cbbd"
 * 输出：2
 * 解释：一个可能的最长回文子序列为 "bb" 。
 * <p>
 * 1 <= s.length <= 1000
 * s 仅由小写英文字母组成
 */
public class Problem516 {
    public static void main(String[] args) {
        Problem516 problem516 = new Problem516();
        String s = "cbbd";
        System.out.println(problem516.longestPalindromeSubseq(s));
    }

    /**
     * 动态规划
     * dp[i][j]：s[i]-s[j]最长回文子序列长度
     * dp[i][j] = dp[i+1][j-1] + 2                (s[i] == s[j])
     * dp[i][j] = Math.max(dp[i][j-1],dp[i+1][j]) (s[i] != s[j])
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param s
     * @return
     */
    public int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        if (s.length() == 1) {
            return 1;
        }

        int[][] dp = new int[s.length()][s.length()];

        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = 1;
        }

        //当前字符串长度
        for (int i = 2; i <= s.length(); i++) {
            //当前字符串起始字符索引
            for (int j = 0; j <= s.length() - i; j++) {
                if (s.charAt(j) == s.charAt(j + i - 1)) {
                    dp[j][j + i - 1] = dp[j + 1][j + i - 2] + 2;
                } else {
                    dp[j][j + i - 1] = Math.max(dp[j][j + i - 2], dp[j + 1][j + i - 1]);
                }
            }
        }

        return dp[0][s.length() - 1];
    }
}