package com.zhang.java;

/**
 * @Date 2023/3/21 08:50
 * @Author zsy
 * @Description 通配符匹配 类比Problem10、Problem32、Offer19
 * 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
 * '?' 可以匹配任何单个字符。
 * '*' 可以匹配任意字符串（包括空字符串）。
 * 两个字符串完全匹配才算匹配成功。
 * <p>
 * 输入:
 * s = "aa"
 * p = "a"
 * 输出: false
 * 解释: "a" 无法匹配 "aa" 整个字符串。
 * <p>
 * 输入:
 * s = "aa"
 * p = "*"
 * 输出: true
 * 解释: '*' 可以匹配任意字符串。
 * <p>
 * 输入:
 * s = "cb"
 * p = "?a"
 * 输出: false
 * 解释: '?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
 * <p>
 * 输入:
 * s = "adceb"
 * p = "*a*b"
 * 输出: true
 * 解释: 第一个 '*' 可以匹配空字符串, 第二个 '*' 可以匹配字符串 "dce".
 * <p>
 * 输入:
 * s = "acdcb"
 * p = "a*c?b"
 * 输出: false
 * <p>
 * s 可能为空，且只包含从 a-z 的小写字母。
 * p 可能为空，且只包含从 a-z 的小写字母，以及字符 ? 和 *。
 */
public class Problem44 {
    public static void main(String[] args) {
        Problem44 problem44 = new Problem44();
        String s = "acdcb";
        String p = "a*c?b";
        System.out.println(problem44.isMatch(s, p));
    }

    /**
     * 动态规划
     * dp[i][j]：主串s[0]-s[i-1]和模式串p[0]-p[j-1]是否匹配
     * dp[i][j] = dp[i-1][j-1]             (s[i-1] == p[j-1] || p[j-1] == '?')
     * dp[i][j] = dp[i-1][j] || dp[i][j-1] (p[i-1] == '*')
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        //空串和空串匹配
        dp[0][0] = true;

        //边界处理，当主串s为空串和模式串p匹配的情况
        for (int j = 1; j <= p.length(); j++) {
            //p[j-1] == '*'，即空串和p[0]-p[j-1]匹配
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = true;
            } else {
                //p[j-1] != '*'，即之后所有p都不匹配，直接跳出循环
                break;
            }
        }

        for (int i = 1; i <= s.length(); i++) {
            //s当前字符s[i-1]
            char s1 = s.charAt(i - 1);

            for (int j = 1; j <= p.length(); j++) {
                //p当前字符p[j-1]
                char p1 = p.charAt(j - 1);

                if (s1 == p1 || p1 == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p1 == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }
            }
        }

        return dp[s.length()][p.length()];
    }
}
