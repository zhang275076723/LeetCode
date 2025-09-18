package com.zhang.java;

/**
 * @Date 2025/9/17 22:24
 * @Author zsy
 * @Description 恢复数组 类比Problem91、Problem639 记忆化搜索类比
 * 某个程序本来应该输出一个整数数组。
 * 但是这个程序忘记输出空格了以致输出了一个数字字符串，我们所知道的信息只有：
 * 数组中所有整数都在 [1, k] 之间，且数组中的数字都没有前导 0 。
 * 给你字符串 s 和整数 k 。
 * 可能会有多种不同的数组恢复结果。
 * 按照上述程序，请你返回所有可能输出字符串 s 的数组方案数。
 * 由于数组方案数可能会很大，请你返回它对 10^9 + 7 取余 后的结果。
 * <p>
 * 输入：s = "1000", k = 10000
 * 输出：1
 * 解释：唯一一种可能的数组方案是 [1000]
 * <p>
 * 输入：s = "1000", k = 10
 * 输出：0
 * 解释：不存在任何数组方案满足所有整数都 >= 1 且 <= 10 同时输出结果为 s 。
 * <p>
 * 输入：s = "1317", k = 2000
 * 输出：8
 * 解释：可行的数组方案为 [1317]，[131,7]，[13,17]，[1,317]，[13,1,7]，[1,31,7]，[1,3,17]，[1,3,1,7]
 * <p>
 * 输入：s = "2020", k = 30
 * 输出：1
 * 解释：唯一可能的数组方案是 [20,20] 。 [2020] 不是可行的数组方案，原因是 2020 > 30 。 [2,020] 也不是可行的数组方案，因为 020 含有前导 0 。
 * <p>
 * 输入：s = "1234567890", k = 90
 * 输出：34
 * <p>
 * 1 <= s.length <= 10^5.
 * s 只包含数字且不包含前导 0 。
 * 1 <= k <= 10^9.
 */
public class Problem1416 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem1416 problem1416 = new Problem1416();
        String s = "2020";
        int k = 30;
//        String s = "5";
//        int k = 4;
        System.out.println(problem1416.numberOfArrays(s, k));
        System.out.println(problem1416.numberOfArrays2(s, k));
    }

    /**
     * 动态规划
     * dp[i]：s[0]-s[i-1]构成的解码方法个数
     * dp[i] = dp[i] + dp[i-j] (1 <= j <= len && s[i-j] != '0' && s[i-j]-s[i-1]构成的数字小于等于k) (len为k的长度)
     * 时间复杂度O(nlogC)=O(n)，空间复杂度O(n)
     *
     * @param s
     * @param k
     * @return
     */
    public int numberOfArrays(String s, int k) {
        //注意：s[0]和k比较的时候，s[0]需要转换为int再比较
        if (s.charAt(0) == '0' || s.charAt(0) - '0' > k) {
            return 0;
        }

        //k的长度
        int len = 0;
        int temp = k;

        while (temp != 0) {
            len++;
            temp = temp / 10;
        }

        long[] dp = new long[s.length() + 1];
        //dp初始化，没有数字或只有一个数字的解码方法个数为1
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= s.length(); i++) {
            //s[i-j]-s[i-1]构成的数字
            long curNum = 0;
            int base = 1;

            //s[i-j]-s[i-1]
            for (int j = 1; j <= len; j++) {
                if (i - j < 0) {
                    break;
                }

                char c = s.charAt(i - j);
                curNum = curNum + (long) (c - '0') * base;

                if (c != '0' && curNum <= k) {
                    dp[i] = (dp[i] + dp[i - j]) % MOD;
                }

                base = base * 10;
            }
        }

        return (int) dp[s.length()];
    }

    /**
     * 回溯+剪枝+记忆化搜索
     * dp[i]：s[i]-s[s.length()-1]构成的解码方法个数 (注意：和动态规划中dp的定义不同)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @param k
     * @return
     */
    public int numberOfArrays2(String s, int k) {
        //注意：s[0]和k比较的时候，s[0]需要转换为int再比较
        if (s.charAt(0) == '0' || s.charAt(0) - '0' > k) {
            return 0;
        }

        int len = 0;
        int temp = k;

        while (temp != 0) {
            len++;
            temp = temp / 10;
        }

        long[] dp = new long[s.length()];

        //dp初始化为-1，表示当前dp未访问
        for (int i = 0; i < s.length(); i++) {
            dp[i] = -1;
        }

        backtrack(0, s, k, len, dp);

        return (int) dp[0];
    }

    private long backtrack(int t, String s, int k, int len, long[] dp) {
        if (t == s.length()) {
            return 1;
        }

        if (dp[t] != -1) {
            return dp[t];
        }

        if (s.charAt(t) == '0') {
            dp[t] = 0;
            return 0;
        }

        long count = 0;
        //s[t]-s[t+i]构成的数字
        long curNum = 0;

        //s[t]-s[t+i]
        for (int i = 0; i < len; i++) {
            if (t + i >= s.length()) {
                break;
            }

            //当前字符
            char c = s.charAt(t + i);
            curNum = curNum * 10 + (c - '0');

            if (curNum <= k) {
                count = (count + backtrack(t + i + 1, s, k, len, dp)) % MOD;
            }
        }

        dp[t] = count;

        return dp[t];
    }
}
