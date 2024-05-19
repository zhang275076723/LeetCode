package com.zhang.java;

/**
 * @Date 2024/5/2 08:49
 * @Author zsy
 * @Description 不同的子序列 II 类比Problem115、Problem730、Problem1682、Problem1987 动态规划类比 子序列和子数组类比
 * 给定一个字符串 s，计算 s 的 不同非空子序列 的个数。
 * 因为结果可能很大，所以返回答案需要对 10^9 + 7 取余 。
 * 字符串的 子序列 是经由原字符串删除一些（也可能不删除）字符但不改变剩余字符相对位置的一个新字符串。
 * 例如，"ace" 是 "abcde" 的一个子序列，但 "aec" 不是。
 * <p>
 * 输入：s = "abc"
 * 输出：7
 * 解释：7 个不同的子序列分别是 "a", "b", "c", "ab", "ac", "bc", 以及 "abc"。
 * <p>
 * 输入：s = "aba"
 * 输出：6
 * 解释：6 个不同的子序列分别是 "a", "b", "ab", "ba", "aa" 以及 "aba"。
 * <p>
 * 输入：s = "aaa"
 * 输出：3
 * 解释：3 个不同的子序列分别是 "a", "aa" 以及 "aaa"。
 * <p>
 * 1 <= s.length <= 2000
 * s 仅由小写英文字母组成
 */
public class Problem940 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem940 problem940 = new Problem940();
        String s = "aba";
        System.out.println(problem940.distinctSubseqII(s));
    }

    /**
     * 动态规划
     * dp[i][j]：s[0]-s[i-1]中以'a'+j结尾的不同非空子序列的个数
     * dp[i][j] = dp[i-1][j]          (s[i-1] != 'a'+j)
     * dp[i][j] = sum(dp[i-1][k]) + 1 (s[i-1] == 'a'+j，0 <= k < 26) (加1表示的只包含s[i-1]的子序列)
     * 时间复杂度O(n*|Σ|^2)=O(n)，空间复杂度O(n*|Σ|)=O(n) (|Σ|=26，只包含小写字母)
     *
     * @param s
     * @return
     */
    public int distinctSubseqII(String s) {
        //只包含小写字母，所以数组二维长度为26
        int[][] dp = new int[s.length() + 1][26];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            for (int j = 0; j < 26; j++) {
                if (c != 'a' + j) {
                    dp[i + 1][j] = dp[i][j];
                } else {
                    //初始化为1表示的只包含s[i]的子序列
                    dp[i + 1][j] = 1;

                    for (int k = 0; k < 26; k++) {
                        dp[i + 1][j] = (dp[i + 1][j] + dp[i][k]) % MOD;
                    }
                }
            }
        }

        int count = 0;

        for (int i = 0; i < 26; i++) {
            count = (count + dp[s.length()][i]) % MOD;
        }

        return count;
    }
}
