package com.zhang.java;

/**
 * @Date 2022/4/24 9:42
 * @Author zsy
 * @Description 编辑距离 字节面试题 七牛云面试题 类比Problem161、Problem2452 动态规划类比Problem97、Problem115、Problem132、Problem139、Problem221、Problem392、Problem516、Problem1143、Problem1312、Problem2645
 * 给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数 。
 * 你可以对一个单词进行如下三种操作：
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 * <p>
 * 输入：word1 = "horse", word2 = "ros"
 * 输出：3
 * 解释：
 * horse -> rorse (将 'h' 替换为 'r')
 * rorse -> rose (删除 'r')
 * rose -> ros (删除 'e')
 * <p>
 * 输入：word1 = "intention", word2 = "execution"
 * 输出：5
 * 解释：
 * intention -> inention (删除 't')
 * inention -> enention (将 'i' 替换为 'e')
 * enention -> exention (将 'n' 替换为 'x')
 * exention -> exection (将 'n' 替换为 'c')
 * exection -> execution (插入 'u')
 * <p>
 * 0 <= word1.length, word2.length <= 500
 * word1 和 word2 由小写英文字母组成
 */
public class Problem72 {
    public static void main(String[] args) {
        Problem72 problem72 = new Problem72();
        String word1 = "intention";
        String word2 = "execution";
        System.out.println(problem72.minDistance(word1, word2));
    }

    /**
     * 动态规划
     * dp[i][j]：word1[0]-word1[i-1](word1前i个字母)转换成word2[0]-word2[j-1](word2前j个字母)所使用的最少操作数
     * dp[i][j] = dp[i-1][j-1]                                  (word1[i-1] == word2[j-1])
     * dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1 (word1[i-1] != word2[j-1])
     * 时间复杂度O(mn)，空间复杂度O(mn)
     * <p>
     * word1[0]-word1[i]转换成word2[0]-word2[j]，有以下3种情况：
     * 1、word1[0]-word1[i-1]转换成word2[0]-word2[j-1]，消耗dp[i][j]，
     * 再根据word1[i]和word2[j]是否相等判断是否需要再消耗1步
     * 2、word1[0]-word1[i]先删除word1[i]，消耗1步，变为word1[0]-word1[i-1]，
     * 再word1[0]-word1[i-1]转换成word2[0]-word2[j]，消耗dp[i][j+1]，共需要dp[i][j+1]+1
     * 3、word1[0]-word1[i]转换成word2[0]-word2[j-1]，消耗dp[i+1][j]，再添加word2[j]，
     * 转换成word2[0]-word2[j]，消耗1步，共需要dp[i+1][j]+1
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        if (word1.length() == 0) {
            return word2.length();
        }

        if (word2.length() == 0) {
            return word1.length();
        }

        //word1[0]-word1[i-1]转换成word2[0]-word2[j-1]所使用的最少操作数
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        //dp初始化，word2长度为0的情况
        for (int i = 1; i <= word1.length(); i++) {
            dp[i][0] = i;
        }

        //dp初始化，word1长度为0的情况
        for (int j = 1; j <= word2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= word1.length(); i++) {
            char c1 = word1.charAt(i - 1);

            for (int j = 1; j <= word2.length(); j++) {
                char c2 = word2.charAt(j - 1);

                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    //取3种情况的最小值+1,，即为所需的最少操作数
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }

        return dp[word1.length()][word2.length()];
    }
}
