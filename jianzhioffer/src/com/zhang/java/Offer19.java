package com.zhang.java;

/**
 * @Date 2022/3/23 11:37
 * @Author zsy
 * @Description 正则表达式匹配 同Problem10
 * 请实现一个函数用来匹配包含'.'和'*'的正则表达式。
 * 模式中的字符'.'表示任意一个字符，而'*'表示它前面的一个字符可以出现任意次（含0次）。
 * 在本题中，匹配是指字符串的所有字符匹配整个模式。
 * 例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但与"aa.a"和"ab*a"均不匹配。
 * <p>
 * 输入:
 * s = "aa"
 * p = "a"
 * 输出: false
 * 解释: "a" 无法匹配 "aa" 整个字符串。
 * <p>
 * 输入:
 * s = "aa"
 * p = "a*"
 * 输出: true
 * 解释: 因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。
 * 因此，字符串 "aa" 可被视为 'a' 重复了一次。
 * <p>
 * 输入:
 * s = "ab"
 * p = ".*"
 * 输出: true
 * 解释: ".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
 * <p>
 * 输入:
 * s = "aab"
 * p = "c*a*b"
 * 输出: true
 * 解释: 因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
 * <p>
 * 输入:
 * s = "mississippi"
 * p = "mis*is*p*."
 * 输出: false
 * <p>
 * 1 <= s.length <= 20
 * s 仅含英文字母（大写和小写），数字（0-9），加号 '+' ，减号 '-' ，空格 ' ' 或者点 '.' 。
 */
public class Offer19 {
    public static void main(String[] args) {
        Offer19 offer19 = new Offer19();
        String s = "mississippi";
        String p = "mis*is*p*.";
        System.out.println(offer19.isMatch(s, p));
    }

    /**
     * 动态规划
     * dp[i][j]：主串s[0]-s[i-1]和模式串p[0]-p[j-1]是否匹配
     * dp[i][j] = dp[i-1][j-1]               (s[i-1] == p[j-1]，p[j-1]为普通字符；或者p[j-1] = '.')
     * dp[i][j] = dp[i][j-2]                 (s[i-1] != p[j-2]，p[j-1] == '*'，则说明p[j-2]取0个)
     * dp[i][j] = dp[i][j - 2] || dp[i-1][j] (s[i-1] == p[j-2]，p[j-1] == '*'，则说明p[j-2]取0个或多个，只要有一种情况满足即可)
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param s 主串，可能为空，且只包含从 a-z 的小写字母。
     * @param p 模式串，可能为空，且只包含从 a-z 的小写字母以及字符 . 和 *，无连续的 '*'。
     * @return
     */
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        //方便空串的处理
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
                    dp[i][j] = dp[i][j - 2] || dp[i-1][j];
                }
            }
        }

        return dp[m][n];
    }
}
