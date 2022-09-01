package com.zhang.java;

/**
 * @Date 2022/6/17 9:06
 * @Author zsy
 * @Description 回文子串 类比Problem5、Problem9、Problem131、Problem234
 * 给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
 * 回文字符串 是正着读和倒过来读一样的字符串。
 * 子字符串 是字符串中的由连续字符组成的一个序列。
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
 * <p>
 * 输入：s = "abc"
 * 输出：3
 * 解释：三个回文子串: "a", "b", "c"
 * <p>
 * 输入：s = "aaa"
 * 输出：6
 * 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
 * <p>
 * 1 <= s.length <= 1000
 * s 由小写英文字母组成
 */
public class Problem647 {
    public static void main(String[] args) {
        Problem647 problem647 = new Problem647();
        String s = "aaa";
        System.out.println(problem647.countSubstrings(s));
        System.out.println(problem647.countSubstrings2(s));
        System.out.println(problem647.countSubstrings3(s));
    }

    /**
     * 暴力
     * 时间复杂度O(n^3)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        if (s.length() == 1) {
            return 1;
        }

        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                //判断从left到right索引的字符串是否是回文串
                int left = i;
                int right = j;
                boolean isPalindrome = true;

                while (left <= right) {
                    if (s.charAt(left) != s.charAt(right)) {
                        isPalindrome = false;
                        break;
                    }

                    left++;
                    right--;
                }

                if (isPalindrome) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 动态规划
     * dp[i][j]：s[i]到s[j]是否是回文串
     * dp[i][j] = false (s[i] != s[j])
     * dp[i][j] = true  (s[i] == s[j] && dp[i+1][j-1] == true)
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param s
     * @return
     */
    public int countSubstrings2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        if (s.length() == 1) {
            return 1;
        }

        int count = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];

        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
            count++;
        }

        for (int i = 1; i < s.length(); i++) {
            //用于s[i]-s[i+1]的情况
            dp[i][i - 1] = true;
        }

        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j < s.length() - i; j++) {
                if (s.charAt(j) == s.charAt(j + i) && dp[j + 1][j + i - 1]) {
                    dp[j][j + i] = true;
                    count++;
                }
            }
        }

        return count;
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
    public int countSubstrings3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        if (s.length() == 1) {
            return 1;
        }

        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            //一个字符作为中心向两边扩散
            count = count + centerExtend(s, i, i);

            //两个字符作为中心向两边扩散
            if (i + 1 < s.length() && s.charAt(i) == s.charAt(i + 1)) {
                count = count + centerExtend(s, i, i + 1);
            }
        }

        return count;
    }

    /**
     * 中心扩散
     *
     * @param s
     * @param left
     * @param right
     * @return
     */
    private int centerExtend(String s, int left, int right) {
        int count = 0;

        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++;
            left--;
            right++;
        }

        return count;
    }
}
