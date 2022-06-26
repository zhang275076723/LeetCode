package com.zhang.java;

/**
 * @Date 2021/12/6 21:56
 * @Author zsy
 * @Description 最长公共子序列
 */
public class Problem1143 {
    public static void main(String[] args) {
        Problem1143 p = new Problem1143();

        String x = "ABCDE";
        String y = "ACBDE";
        //position[i][j]：表示LCS选择x1...xi-1和y1...yj-1、x1...xi-1和y1...yj、x1...xi和y1...yj-1
        String[][] position = new String[x.length() + 1][y.length() + 1];
        System.out.println("LCS长度为：" + p.longestCommonSubsequence(x, y, position));
        System.out.print("LCS为：");
        p.printLcs(x, position, x.length(), y.length());
    }

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
