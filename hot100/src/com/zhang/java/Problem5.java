package com.zhang.java;

/**
 * @Date 2022/4/13 9:32
 * @Author zsy
 * @Description 最长回文子串 类比Problem131、Problem647
 * 给你一个字符串 s，找到 s 中最长的回文子串。
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
        String s = "babad";
        System.out.println(problem5.longestPalindrome(s));
        System.out.println(problem5.longestPalindrome2(s));
        System.out.println(problem5.longestPalindrome3(s));
    }

    /**
     * 暴力
     * 时间复杂度O(n^3)，空间复杂度O(1)
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
     * 双指针，中心扩散
     * 以一个字符作为中心向两边扩散，找最长回文串
     * 以两个字符作为中心向两边扩散，找最长回文串
     * 时间复杂度O(n^2)，空间复杂度O(1)
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

        int left = 0;
        int right = 0;

        for (int i = 0; i < s.length(); i++) {
            //一个字符作为中心向两边扩散
            int[] arr1 = centerExtend(s, i, i);

            //两个字符作为中心向两边扩散
            int[] arr2 = arr1;
            if (i + 1 < s.length() && s.charAt(i) == s.charAt(i + 1)) {
                arr2 = centerExtend(s, i, i + 1);
            }

            int[] curArr;

            if (arr1[1] - arr1[0] > arr2[1] - arr2[0]) {
                curArr = arr1;
            } else {
                curArr = arr2;
            }

            if (curArr[1] - curArr[0] > right - left) {
                left = curArr[0];
                right = curArr[1];
            }
        }

        return s.substring(left, right + 1);
    }

    /**
     * 动态规划
     * dp[i][j]：s[i]到s[j]是否是回文串
     * dp[i][j] = false (s[i] != s[j])
     * dp[i][j] = true (s[i] == s[j] && dp[i+1][j-1] == true)
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
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

        boolean[][] dp = new boolean[s.length()][s.length()];

        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }
        for (int i = 1; i < s.length(); i++) {
            //用于s[i]-s[i+1]的情况
            dp[i][i - 1] = true;
        }

        int left = 0;
        int right = 0;

        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j < s.length() - i; j++) {
                if (s.charAt(j) == s.charAt(j + i) && dp[j + 1][j + i - 1]) {
                    dp[j][j + i] = true;
                    if (i + 1 > right - left + 1) {
                        left = j;
                        right = j + i;
                    }
                }
            }
        }

        return s.substring(left, right + 1);
    }

    /**
     * 中心扩散
     *
     * @param s     原字符串
     * @param left  扩散起始的左指针
     * @param right 扩散起始的右指针
     * @return 当前最长回文串的左右指针数组
     */
    private int[] centerExtend(String s, int left, int right) {
        while (left > 0 && right + 1 < s.length() && s.charAt(left - 1) == s.charAt(right + 1)) {
            left--;
            right++;
        }

        return new int[]{left, right};
    }
}
