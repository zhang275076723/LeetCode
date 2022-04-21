package com.zhang.java;

/**
 * @Date 2022/4/14 8:30
 * @Author zsy
 * @Description 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
 * '.' 匹配任意单个字符
 * '*' 匹配零个或多个前面的那一个元素
 * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
 * <p>
 * 输入：s = "aa", p = "a"
 * 输出：false
 * 解释："a" 无法匹配 "aa" 整个字符串。
 * <p>
 * 输入：s = "aa", p = "a*"
 * 输出：true
 * 解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。
 * 因此，字符串 "aa" 可被视为 'a' 重复了一次。
 * <p>
 * 输入：s = "ab", p = ".*"
 * 输出：true
 * 解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
 * <p>
 * 1 <= s.length <= 20
 * 1 <= p.length <= 30
 * s 只包含从 a-z 的小写字母。
 * p 只包含从 a-z 的小写字母，以及字符 . 和 *。
 * 保证每次出现字符 * 时，前面都匹配到有效的字符
 */
public class Problem10 {
    public static void main(String[] args) {
        Problem10 problem10 = new Problem10();
//        String s = "mississippi";
//        String p = "mis*is*p*.";
//        String s = "ab";
//        String p = ".*";
        String s = "aaa";
        String p = "ab*a*c*a";
        System.out.println(problem10.isMatch(s, p));
    }

    /**
     * 动态规划，时间复杂度O(mn)，空间复杂度O(mn)
     * dp[i][j]：主串s[0]-s[i-1]和模式串p[0]-p[j-1]是否匹配
     * dp[i][j] = dp[i-1][j-1]               ((s[i-1] == p[j-1]，p[j-1]为普通字符；或者p[j-1] = '.')
     * dp[i][j] = dp[i][j-2]                 (s[i-1] != p[j-2]，p[j-1] == '*'，则说明p[j-2]取0个)
     * dp[i][j] = dp[i][j - 2] || dp[i-1][j] (s[i-1] == p[j-2]，p[j-1] == '*'，则说明p[j-2]取0个或多个，只要有一种情况满足即可)
     *
     * @param s 主串
     * @param p 模式串
     * @return
     */
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        //dp[i][j]：s[0]-s[i-1]和p[0]-p[j-1]是否匹配
        boolean[][] dp = new boolean[m + 1][n + 1];
        //空串和空串匹配
        dp[0][0] = true;

        //边界处理，当主串s为空串和模式串p匹配的情况
        for (int j = 2; j <= n; j = j + 2) {
            //空串和p[0]-p[j-3]无法匹配，说明p后面都无法和空串匹配
            if (!dp[0][j - 2]) {
                break;
            }

            //c*取0个，指针需要往后移动2位
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = true;
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                //(s[i-1] == p[j-1]，p[j-1]为普通字符；或者p[j-1] = '.'
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*' && s.charAt(i - 1) != p.charAt(j - 2) && p.charAt(j - 2) != '.') {
                    //p[j-1] == '*'，s[i-1] != p[j-2]，p[j-2] != '.'，则说明p[j-2]取0个
                    dp[i][j] = dp[i][j - 2];
                } else if (p.charAt(j - 1) == '*' && (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.')) {
                    //dp[j-1] == '*'，(s[i-1] == p[j-2] 或者 p[j-2] == '.')，则说明p[j-2]取0个或多个，只要有一种情况满足就行
                    dp[i][j] = dp[i][j - 2] || dp[i - 1][j];
                }
            }
        }

        return dp[m][n];
    }
}
