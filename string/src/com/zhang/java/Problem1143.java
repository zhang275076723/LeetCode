package com.zhang.java;

/**
 * @Date 2022/6/29 11:56
 * @Author zsy
 * @Description 最长公共子序列 Google面试题 子序列和子数组类比Problem300、Problem718 动态规划类比Problem72、Problem97、Problem221、Problem516
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。
 * 如果不存在 公共子序列 ，返回 0 。
 * 一个字符串的 子序列 是指这样一个新的字符串：
 * 它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 * <p>
 * 输入：text1 = "abcde", text2 = "ace"
 * 输出：3
 * 解释：最长公共子序列是 "ace" ，它的长度为 3 。
 * <p>
 * 输入：text1 = "abc", text2 = "abc"
 * 输出：3
 * 解释：最长公共子序列是 "abc" ，它的长度为 3 。
 * <p>
 * 输入：text1 = "abc", text2 = "def"
 * 输出：0
 * 解释：两个字符串没有公共子序列，返回 0 。
 * <p>
 * 1 <= text1.length, text2.length <= 1000
 * text1 和 text2 仅由小写英文字符组成。
 */
public class Problem1143 {
    public static void main(String[] args) {
        Problem1143 problem1143 = new Problem1143();
        String text1 = "abcde";
        String text2 = "ace";
        System.out.println(problem1143.longestCommonSubsequence(text1, text2));

        //position[i][j]：表示LCS选择x1...xi-1和y1...yj-1、x1...xi-1和y1...yj、x1...xi和y1...yj-1
        String[][] position = new String[text1.length() + 1][text2.length() + 1];
        System.out.println("LCS长度为：" + problem1143.longestCommonSubsequence(text1, text2, position));
        System.out.print("LCS为：");
        problem1143.printLcs(text1, position, text1.length(), text2.length());
    }

    /**
     * 动态规划
     * dp[i][j]：text1[0]-text1[i-1]和text2[0]-text2[j-1]的最长公共子序列的长度
     * dp[i][j] = dp[i-1][j-1] + 1            (text1[i-1] == text2[j-1])
     * dp[i][j] = max(dp[i-1][j], dp[i][j-1]) (text1[i-1] != text2[j-1])
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text1.length() == 0 || text2 == null || text2.length() == 0) {
            return 0;
        }

        int[][] dp = new int[text1.length() + 1][text2.length() + 1];

        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[text1.length()][text2.length()];
    }

    /**
     * 带有最长公共子序列的输出，position记录了当前最长公共子序列所选择的位置
     *
     * @param text1
     * @param text2
     * @param position
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2, String[][] position) {
        //dp[i][j]：表示x1...xi和y1...yj的最长公共子序列LCS的长度
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];

        for (int i = 0; i <= text1.length(); i++) {
            dp[i][0] = 0;
        }

        for (int i = 0; i <= text2.length(); i++) {
            dp[0][i] = 0;
        }

        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    position[i][j] = "top-left";
                } else if (dp[i - 1][j] >= dp[i][j - 1]) {
                    dp[i][j] = dp[i - 1][j];
                    position[i][j] = "top";
                } else {
                    dp[i][j] = dp[i][j - 1];
                    position[i][j] = "left";
                }
            }
        }

        return dp[text1.length()][text2.length()];
    }

    public void printLcs(String x, String[][] position, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }

        if ("top-left".equals(position[i][j])) {
            printLcs(x, position, i - 1, j - 1);
            System.out.print(x.charAt(i - 1));
        } else if ("top".equals(position[i][j])) {
            printLcs(x, position, i - 1, j);
        } else {
            printLcs(x, position, i, j - 1);
        }
    }
}
