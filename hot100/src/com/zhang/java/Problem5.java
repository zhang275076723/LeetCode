package com.zhang.java;

/**
 * @Date 2022/4/13 9:32
 * @Author zsy
 * @Description 给你一个字符串 s，找到 s 中最长的回文子串。
 * <p>
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * <p>
 * 输入：s = "cbbd"
 * 输出："bb"
 * <p>
 * 1 <= s.length <= 1000
 * s 仅由数字和英文字母组成
 */
public class Problem5 {
    public static void main(String[] args) {
        Problem5 problem5 = new Problem5();
        String s = "cbbd";
        System.out.println(problem5.longestPalindrome(s));
        System.out.println(problem5.longestPalindrome2(s));
        System.out.println(problem5.longestPalindrome3(s));
    }

    /**
     * 暴力，时间复杂度O(n^3)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        if (s.length() == 1) {
            return s;
        }

        int start = 0;
        int maxLen = 1;
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j <= i; j++) {
                //判断以left起始right结尾的字符串是否是回文串
                int left = j;
                int right = i;
                boolean isPalindrome = true;
                while (left <= right) {
                    if (s.charAt(left) != s.charAt(right)) {
                        isPalindrome = false;
                        break;
                    }
                    left++;
                    right--;
                }
                if (isPalindrome && i - j + 1 > maxLen) {
                    start = j;
                    maxLen = i - j + 1;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }

    /**
     * 双指针，中心扩散，时间复杂度O(n^2)，空间复杂度O(1)
     * 先往左寻找与当期位置相同的字符，直到遇到不相等为止。
     * 再往右寻找与当期位置相同的字符，直到遇到不相等为止。
     * 最后左右双向扩散，直到左和右不相等
     *
     * @param s
     * @return
     */
    public String longestPalindrome2(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        if (s.length() == 1) {
            return s;
        }

        int start = 0;
        int maxLen = 1;
        int left;
        int right;
        for (int i = 0; i < s.length(); i++) {
            left = i;
            right = i;

            //往左找到第一个与s[i]不相等的字符
            while (left - 1 >= 0 && s.charAt(left - 1) == s.charAt(i)) {
                left--;
            }
            //往右找到第一个与s[i]不相等的字符
            while (right + 1 < s.length() && s.charAt(right + 1) == s.charAt(i)) {
                right++;
            }
            //两边比较是否相等
            while (left - 1 >= 0 && right + 1 < s.length() && s.charAt(left - 1) == s.charAt(right + 1)) {
                left--;
                right++;
            }

            if (right - left + 1 > maxLen) {
                maxLen = right - left + 1;
                start = left;
            }
        }

        return s.substring(start, start + maxLen);
    }

    /**
     * 动态规划，时间复杂度O(n^2)，空间复杂度O(n)
     * dp[i][j]：s[i]到s[j]是否是回文串
     * dp[i][j] = false (s[i] != s[j])
     * dp[i][j] = true (s[i] == s[j] && dp[i+1][j-1] == true)
     *
     * @param s
     * @return
     */
    public String longestPalindrome3(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        if (s.length() == 1) {
            return s;
        }

        int start = 0;
        int maxLen = 1;
        boolean[][] dp = new boolean[s.length()][s.length()];

        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }
        for (int i = 1; i < s.length(); i++) {
            dp[i][i - 1] = true;
        }
        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j < s.length() - i; j++) {
                if (s.charAt(j) == s.charAt(j + i) && dp[j + 1][j + i - 1]) {
                    dp[j][j + i] = true;
                    if (i + 1 > maxLen) {
                        maxLen = i + 1;
                        start = j;
                    }
                }
            }
        }

        return s.substring(start, start + maxLen);
    }
}
