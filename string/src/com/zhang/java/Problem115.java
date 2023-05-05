package com.zhang.java;

/**
 * @Date 2023/5/5 09:31
 * @Author zsy
 * @Description 不同的子序列 子序列和子数组类比Problem53、Problem300、Problem392、Problem673、Problem674、Problem718、Problem1143 动态规划类比Problem72、Problem97、Problem221、Problem516、Problem1143
 * 给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 出现的个数。
 * 题目数据保证答案符合 32 位带符号整数范围。
 * <p>
 * 输入：s = "rabbbit", t = "rabbit"
 * 输出：3
 * 解释：
 * 如下所示, 有 3 种可以从 s 中得到 "rabbit" 的方案。
 * (rabb)b(it)
 * (rab)b(bit)
 * (ra)b(bbit)
 * <p>
 * 输入：s = "babgbag", t = "bag"
 * 输出：5
 * 解释：
 * 如下所示, 有 5 种可以从 s 中得到 "bag" 的方案。
 * (ba)b(g)bag
 * (ba)bgba(g)
 * (b)abgb(ag)
 * ba(b)gb(ag)
 * babg(bag)
 * <p>
 * 1 <= s.length, t.length <= 1000
 * s 和 t 由英文字母组成
 */
public class Problem115 {
    public static void main(String[] args) {
        Problem115 problem115 = new Problem115();
        String s = "rabbbit";
        String t = "rabbit";
        System.out.println(problem115.numDistinct(s, t));
    }

    /**
     * 动态规划
     * dp[i][j]：s[i]-s[s.length()-1]的子序列中t[j]-t[t.length()-1]出现的次数
     * dp[i][j] = dp[i+1][j+1] + dp[i+1][j] (s[i] == t[j])
     * dp[i][j] = dp[i+1][j]                (s[i] != t[j])
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param s
     * @param t
     * @return
     */
    public int numDistinct(String s, String t) {
        if (s.length() < t.length()) {
            return 0;
        }

        int[][] dp = new int[s.length() + 1][t.length() + 1];
        //dp初始化，""的子序列中""出现1次
        dp[s.length()][t.length()] = 1;

        //dp初始化，s[i]-s[s.length()-1]的子序列中""出现1次
        for (int i = 0; i < s.length(); i++) {
            dp[i][t.length()] = 1;
        }

        //dp初始化，""的子序列中t[j]-t[t.length()-1]出现0次
        for (int j = 0; j < t.length(); j++) {
            dp[s.length()][j] = 0;
        }

        for (int i = s.length() - 1; i >= 0; i--) {
            char c1 = s.charAt(i);
            for (int j = t.length() - 1; j >= 0; j--) {
                char c2 = t.charAt(j);
                if (c1 == c2) {
                    dp[i][j] = dp[i + 1][j + 1] + dp[i + 1][j];
                } else {
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }

        return dp[0][0];
    }
}
