package com.zhang.java;

/**
 * @Date 2023/7/24 08:11
 * @Author zsy
 * @Description 让字符串成为回文串的最少插入次数 动态规划类比Problem72、Problem97、Problem115、Problem132、Problem139、Problem221、Problem392、Problem516、Problem1143 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem647、Problem680、Problem866、Problem1177、Problem1147、Problem1332
 * 给你一个字符串 s ，每一次操作你都可以在字符串的任意位置插入任意字符。
 * 请你返回让 s 成为回文串的 最少操作次数 。
 * 「回文串」是正读和反读都相同的字符串。
 * <p>
 * 输入：s = "zzazz"
 * 输出：0
 * 解释：字符串 "zzazz" 已经是回文串了，所以不需要做任何插入操作。
 * <p>
 * 输入：s = "mbadm"
 * 输出：2
 * 解释：字符串可变为 "mbdadbm" 或者 "mdbabdm" 。
 * <p>
 * 输入：s = "leetcode"
 * 输出：5
 * 解释：插入 5 个字符后字符串变为 "leetcodocteel" 。
 * <p>
 * 1 <= s.length <= 500
 * s 中所有字符都是小写字母。
 */
public class Problem1312 {
    public static void main(String[] args) {
        Problem1312 problem1312 = new Problem1312();
        String s = "leetcode";
        System.out.println(problem1312.minInsertions(s));
    }

    /**
     * 动态规划
     * dp[i][j]：s[i]-s[j]成为回文串最少插入字符的次数
     * dp[i][j] = dp[i+1][j-1]                 (s[i] == s[j])
     * dp[i][j] = min(dp[i+1][j],dp[i][j-1])+1 (s[i] != s[j])
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param s
     * @return
     */
    public int minInsertions(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }

        int[][] dp = new int[s.length()][s.length()];

        //当前字符串的长度为i
        for (int i = 2; i <= s.length(); i++) {
            //当前字符串的起始下标索引为j
            for (int j = 0; j <= s.length() - i; j++) {
                //表示的字符串s[j]-s[j+i-1]
                if (s.charAt(j) == s.charAt(j + i - 1)) {
                    dp[j][j + i - 1] = dp[j + 1][j + i - 2];
                } else {
                    dp[j][j + i - 1] = Math.min(dp[j][j + i - 2], dp[j + 1][j + i - 1]) + 1;
                }
            }
        }

        return dp[0][s.length() - 1];
    }
}
