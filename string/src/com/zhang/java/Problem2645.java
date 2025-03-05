package com.zhang.java;

/**
 * @Date 2025/2/24 08:47
 * @Author zsy
 * @Description 构造有效字符串的最少插入数 动态规划类比Problem72、Problem97、Problem115、Problem132、Problem139、Problem221、Problem392、Problem516、Problem1143、Problem1312
 * 给你一个字符串 word ，你可以向其中任何位置插入 "a"、"b" 或 "c" 任意次，返回使 word 有效 需要插入的最少字母数。
 * 如果字符串可以由 "abc" 串联多次得到，则认为该字符串 有效 。
 * <p>
 * 输入：word = "b"
 * 输出：2
 * 解释：在 "b" 之前插入 "a" ，在 "b" 之后插入 "c" 可以得到有效字符串 "abc" 。
 * <p>
 * 输入：word = "aaa"
 * 输出：6
 * 解释：在每个 "a" 之后依次插入 "b" 和 "c" 可以得到有效字符串 "abcabcabc" 。
 * <p>
 * 输入：word = "abc"
 * 输出：0
 * 解释：word 已经是有效字符串，不需要进行修改。
 * <p>
 * 1 <= word.length <= 50
 * word 仅由字母 "a"、"b" 和 "c" 组成。
 */
public class Problem2645 {
    public static void main(String[] args) {
        Problem2645 problem2645 = new Problem2645();
        //ab(c)a(b)(c)a(b)(c)
        String word = "abaa";
        System.out.println(problem2645.addMinimum(word));
        System.out.println(problem2645.addMinimum2(word));
    }

    /**
     * 动态规划
     * dp[i]：word[0]-word[i]变为有效字符串的最少插入数
     * dp[i] = dp[i-1]-1 (word[i]>word[i-1])
     * dp[i] = dp[i-1]+2 (word[i]<=word[i-1])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param word
     * @return
     */
    public int addMinimum(String word) {
        int[] dp = new int[word.length()];
        //dp初始化，只有一个字符变为有效字符串的最少插入数为2
        dp[0] = 2;

        for (int i = 1; i < word.length(); i++) {
            //word[i]大于word[i-1]，则word[i-1]和word[i]可以构成"abc"，dp[i-1]需要在word[i-1]后面插入word[i]，
            //dp[i]包含有word[i]，即dp[i]=dp[i-1]-1
            if (word.charAt(i) > word.charAt(i - 1)) {
                dp[i] = dp[i - 1] - 1;
            } else {
                //word[i]小于等于word[i-1]，则word[i]需要单独构成"abc"，即在word[i-1]后面插入2个字符，即dp[i]=dp[i-1]+2
                dp[i] = dp[i - 1] + 2;
            }
        }

        return dp[word.length() - 1];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param word
     * @return
     */
    public int addMinimum2(String word) {
        //dp初始化，只有一个字符变为有效字符串的最少插入数为2
        int dp = 2;

        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) > word.charAt(i - 1)) {
                dp = dp - 1;
            } else {
                dp = dp + 2;
            }
        }

        return dp;
    }
}
