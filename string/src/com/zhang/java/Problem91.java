package com.zhang.java;

/**
 * @Date 2022/12/9 12:02
 * @Author zsy
 * @Description 解码方法 类比Offer46
 * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。
 * 例如，"11106" 可以映射为：
 * "AAJF" ，将消息分组为 (1 1 10 6)
 * "KJF" ，将消息分组为 (11 10 6)
 * 注意，消息不能分组为 (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
 * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
 * 题目数据保证答案肯定是一个 32 位 的整数。
 * <p>
 * 输入：s = "12"
 * 输出：2
 * 解释：它可以解码为 "AB"（1 2）或者 "L"（12）。
 * <p>
 * 输入：s = "226"
 * 输出：3
 * 解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
 * <p>
 * 输入：s = "06"
 * 输出：0
 * 解释："06" 无法映射到 "F" ，因为存在前导零（"6" 和 "06" 并不等价）。
 * <p>
 * 1 <= s.length <= 100
 * s 只包含数字，并且可能包含前导零。
 */
public class Problem91 {
    public static void main(String[] args) {
        Problem91 problem91 = new Problem91();
        String s = "11106";
        System.out.println(problem91.numDecodings(s));
        System.out.println(problem91.numDecodings2(s));
    }

    /**
     * 动态规划
     * dp[i]：s[0]-s[i-1]构成的解码方法数量
     * dp[i] = dp[i-1] + dp[i-2] (s[i-2] != '0' && s[i-1] != '0' && s[i-2]-s[i-1] <= 26)
     * dp[i] = dp[i-2]           (s[i-2] != '0' && s[i-1] == '0' && s[i-2]-s[i-1] <= 26)
     * dp[i] = dp[i-1]           (s[i-2] == '0' && s[i-1] != '0')
     * dp[i] = 0                 (其他情况)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        //存在前导0，则不存在解码方法
        if (s.charAt(0) == '0') {
            return 0;
        }

        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= s.length(); i++) {
            //当前字符c
            char c = s.charAt(i - 1);
            //当前字符c的前一个字符c2
            char c2 = s.charAt(i - 2);
            //c2和c组成的数字
            int num = (c2 - '0') * 10 + (c - '0');

            if (c != '0' && c2 != '0') {
                if (num <= 26) {
                    dp[i] = dp[i - 1] + dp[i - 2];
                } else {
                    dp[i] = dp[i - 1];
                }
            } else if (c2 != '0') {
                if (num <= 26) {
                    dp[i] = dp[i - 2];
                }
            } else if (c != '0') {
                dp[i] = dp[i - 1];
            }
        }

        return dp[s.length()];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public int numDecodings2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        //存在前导0，则不存在解码方法
        if (s.charAt(0) == '0') {
            return 0;
        }

        int p = 1;
        int q = 1;

        for (int i = 2; i <= s.length(); i++) {
            //当前字符c
            char c = s.charAt(i - 1);
            //当前字符c的前一个字符c2
            char c2 = s.charAt(i - 2);
            //c2和c组成的数字
            int num = (c2 - '0') * 10 + (c - '0');

            if (c != '0' && c2 != '0') {
                if (num <= 26) {
                    int temp = p + q;
                    p = q;
                    q = temp;
                } else {
                    p = q;
                }
            } else if (c2 != '0') {
                if (num <= 26) {
                    int temp = p;
                    p = q;
                    q = temp;
                } else {
                    p = q;
                    q = 0;
                }
            } else if (c != '0') {
                p = q;
            } else {
                p = q;
                q = 0;
            }
        }

        return q;
    }
}
