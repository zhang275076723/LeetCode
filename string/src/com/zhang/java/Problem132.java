package com.zhang.java;

/**
 * @Date 2022/11/11 09:46
 * @Author zsy
 * @Description 分割回文串 II 腾讯机试题 动态规划类比Problem72、Problem97、Problem115、Problem139、Problem221、Problem392、Problem516、Problem1143、Problem1312 回文类比Problem5、Problem9、Problem125、Problem131、Problem214、Problem234、Problem266、Problem267、Problem409、Problem516、Problem647、Problem680、Problem1312
 * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文。
 * 返回符合要求的 最少分割次数 。
 * <p>
 * 输入：s = "aab"
 * 输出：1
 * 解释：只需一次分割就可将 s 分割成 ["aa","b"] 这样两个回文子串。
 * <p>
 * 输入：s = "a"
 * 输出：0
 * <p>
 * 输入：s = "ab"
 * 输出：1
 * <p>
 * 1 <= s.length <= 2000
 * s 仅由小写英文字母组成
 */
public class Problem132 {
    public static void main(String[] args) {
        Problem132 problem132 = new Problem132();
        String s = "aab";
        System.out.println(problem132.minCut(s));
    }

    /**
     * 动态规划
     * dp1[i][j]：s[i]-s[j]是否是回文串
     * dp1[i][j] = dp1[i+1][j-1] && (s[i] == s[j])
     * dp2[i]：s[0]-s[i]分割成回文子串的最少分割次数
     * dp2[i] = min(dp2[j]+1) (0 <= j < i，s[j+1]-s[i]是回文串)
     * dp2[i] = 0 (s[0]-s[j]是回文串)
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param s
     * @return
     */
    public int minCut(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }

        boolean[][] dp1 = new boolean[s.length()][s.length()];

        for (int i = 0; i < s.length(); i++) {
            //只有一个字符也是回文串
            dp1[i][i] = true;
        }

        for (int i = 1; i < s.length(); i++) {
            //用于s[i]-s[i+1]，即两个字符的情况
            dp1[i][i - 1] = true;
        }

        //当前字符串长度
        for (int i = 2; i <= s.length(); i++) {
            //当前字符串起始字符索引
            for (int j = 0; j <= s.length() - i; j++) {
                if (s.charAt(j) == s.charAt(j + i - 1) && dp1[j + 1][j + i - 2]) {
                    dp1[j][j + i - 1] = true;
                }
            }
        }

        int[] dp2 = new int[s.length()];
        //dp初始化
        dp2[0] = 0;

        for (int i = 1; i < s.length(); i++) {
            //s[0]-s[i]是回文串，则分割s[0]-s[i]为回文子串的最少分割次数为0
            if (dp1[0][i]) {
                dp2[i] = 0;
                continue;
            }

            //初始化分割s[0]-s[i]为回文串的最少分割次数为i
            dp2[i] = i;

            for (int j = 0; j < i; j++) {
                //s[j+1]-s[i]是回文串，则dp2[i]为dp2[i]和分割s[0]-s[j]为回文子串的最少分割次数dp[j]+1两者中的较小值
                if (dp1[j + 1][i]) {
                    dp2[i] = Math.min(dp2[i], dp2[j] + 1);
                }
            }
        }

        return dp2[s.length() - 1];
    }
}
