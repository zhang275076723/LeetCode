package com.zhang.java;

/**
 * @Date 2025/9/14 12:20
 * @Author zsy
 * @Description 解码方法 II 类比Problem91 记忆化搜索类比
 * 一条包含字母 A-Z 的消息通过以下的方式进行了 编码 ：
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * 要 解码 一条已编码的消息，所有的数字都必须分组，然后按原来的编码方案反向映射回字母（可能存在多种方式）。
 * 例如，"11106" 可以映射为：
 * "AAJF" 对应分组 (1 1 10 6)
 * "KJF" 对应分组 (11 10 6)
 * 注意，像 (1 11 06) 这样的分组是无效的，因为 "06" 不可以映射为 'F' ，因为 "6" 与 "06" 不同。
 * 除了 上面描述的数字字母映射方案，编码消息中可能包含 '*' 字符，可以表示从 '1' 到 '9' 的任一数字（不包括 '0'）。
 * 例如，编码字符串 "1*" 可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条消息。
 * 对 "1*" 进行解码，相当于解码该字符串可以表示的任何编码消息。
 * 给你一个字符串 s ，由数字和 '*' 字符组成，返回 解码 该字符串的方法 数目 。
 * 由于答案数目可能非常大，返回 10^9 + 7 的 模 。
 * <p>
 * 输入：s = "*"
 * 输出：9
 * 解释：这一条编码消息可以表示 "1"、"2"、"3"、"4"、"5"、"6"、"7"、"8" 或 "9" 中的任意一条。
 * 可以分别解码成字符串 "A"、"B"、"C"、"D"、"E"、"F"、"G"、"H" 和 "I" 。
 * 因此，"*" 总共有 9 种解码方法。
 * <p>
 * 输入：s = "1*"
 * 输出：18
 * 解释：这一条编码消息可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条。
 * 每种消息都可以由 2 种方法解码（例如，"11" 可以解码成 "AA" 或 "K"）。
 * 因此，"1*" 共有 9 * 2 = 18 种解码方法。
 * <p>
 * 输入：s = "2*"
 * 输出：15
 * 解释：这一条编码消息可以表示 "21"、"22"、"23"、"24"、"25"、"26"、"27"、"28" 或 "29" 中的任意一条。
 * "21"、"22"、"23"、"24"、"25" 和 "26" 由 2 种解码方法，但 "27"、"28" 和 "29" 仅有 1 种解码方法。
 * 因此，"2*" 共有 (6 * 2) + (3 * 1) = 12 + 3 = 15 种解码方法。
 * <p>
 * 1 <= s.length <= 10^5
 * s[i] 是 0 - 9 中的一位数字或字符 '*'
 */
public class Problem639 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem639 problem639 = new Problem639();
        String s = "2*";
        System.out.println(problem639.numDecodings(s));
        System.out.println(problem639.numDecodings2(s));
        System.out.println(problem639.numDecodings3(s));
    }

    /**
     * 动态规划
     * dp[i]：s[0]-s[i-1]构成的解码方法个数
     * dp[i] = 9*dp[i-1] + 15*dp[i-2] (s[i-2] == '*' && s[i-1] == '*')
     * dp[i] = 9*dp[i-1]              (s[i-2] != '*' && s[i-1] == '*' && s[i-2] == '0')
     * dp[i] = 9*dp[i-1] + 9*dp[i-2]  (s[i-2] != '*' && s[i-1] == '*' && s[i-2] == '1')
     * dp[i] = 9*dp[i-1] + 6*dp[i-2]  (s[i-2] != '*' && s[i-1] == '*' && s[i-2] == '2')
     * dp[i] = 9*dp[i-1]              (s[i-2] != '*' && s[i-1] == '*' && s[i-2] >= '3')
     * dp[i] = 2*dp[i-2]              (s[i-2] == '*' && s[i-1] != '*' && s[i-1] == '0')
     * dp[i] = dp[i-1] + 2*dp[i-2]    (s[i-2] == '*' && s[i-1] != '*' && s[i-1] <= '6')
     * dp[i] = dp[i-1] + dp[i-2]      (s[i-2] == '*' && s[i-1] != '*' && s[i-1] >= '7')
     * dp[i] = 0                      (s[i-2] != '*' && s[i-1] != '*' && s[i-2] == '0' && s[i-1] == '0')
     * dp[i] = dp[i-2]                (s[i-2] != '*' && s[i-1] != '*' && s[i-2] != '0' && s[i-1] == '0' && s[i-2]-s[i-1]构成的数字小于等于26)
     * dp[i] = 0                      (s[i-2] != '*' && s[i-1] != '*' && s[i-2] != '0' && s[i-1] == '0' && s[i-2]-s[i-1]构成的数字大于26)
     * dp[i] = dp[i-1]                (s[i-2] != '*' && s[i-1] != '*' && s[i-2] == '0' && s[i-1] != '0')
     * dp[i] = dp[i-1] + dp[i-2]      (s[i-2] != '*' && s[i-1] != '*' && s[i-2] != '0' && s[i-1] != '0' && s[i-2]-s[i-1]构成的数字小于等于26)
     * dp[i] = dp[i-1]                (s[i-2] != '*' && s[i-1] != '*' && s[i-2] != '0' && s[i-1] != '0' && s[i-2]-s[i-1]构成的数字大于26)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int numDecodings(String s) {
        //存在前导0，则不存在解码方法，直接返回0
        if (s.charAt(0) == '0') {
            return 0;
        }

        long[] dp = new long[s.length() + 1];
        //dp初始化，没有数字或只有一个数字的解码方法个数为1，只有一个字符'*'的的解码方法个数为9
        dp[0] = 1;
        dp[1] = s.charAt(0) == '*' ? 9 : 1;

        for (int i = 2; i <= s.length(); i++) {
            //当前字符c
            char c = s.charAt(i - 1);
            //当前字符c的前一个字符
            char c2 = s.charAt(i - 2);

            //c和c2都为'*'
            if (c == '*' && c2 == '*') {
                //选1个字符，1-9，共9种
                //选2个字符，11-19，21-26，共15种
                dp[i] = (9 * dp[i - 1] + 15 * dp[i - 2]) % MOD;
            } else if (c == '*') {
                //c2不为'*'，c为'*'

                if (c2 == '0') {
                    //选1个字符，1-9，共9种
                    dp[i] = (9 * dp[i - 1]) % MOD;
                } else if (c2 == '1') {
                    //选1个字符，1-9，共9种
                    //选2个字符，11-19，共9种
                    dp[i] = (9 * dp[i - 1] + 9 * dp[i - 2]) % MOD;
                } else if (c2 == '2') {
                    //选1个字符，1-9，共9种
                    //选2个字符，21-26，共6种
                    dp[i] = (9 * dp[i - 1] + 6 * dp[i - 2]) % MOD;
                } else {
                    //选1个字符，1-9，共9种
                    dp[i] = (9 * dp[i - 1]) % MOD;
                }
            } else if (c2 == '*') {
                //c不为'*'，c2为'*'

                if (c == '0') {
                    //选2个字符，10、20，共2种
                    dp[i] = (2 * dp[i - 2]) % MOD;
                } else if (c <= '6') {
                    //选1个字符，c，共1种
                    //选2个字符，1c、2c，共2种
                    dp[i] = (dp[i - 1] + 2 * dp[i - 2]) % MOD;
                } else {
                    //选1个字符，c，共1种
                    //选2个字符，1c，共1种
                    dp[i] = (dp[i - 1] + dp[i - 2]) % MOD;
                }
            } else {
                //c和c2都不为'*'

                //c2和c组成的数字
                int num = (c2 - '0') * 10 + (c - '0');

                //c和c2都为0
                if (c == '0' && c2 == '0') {
                    dp[i] = 0;
                } else if (c == '0') {
                    //c2不为0，c为0
                    if (num <= 26) {
                        dp[i] = dp[i - 2];
                    } else {
                        dp[i] = 0;
                    }
                } else if (c2 == '0') {
                    //c不为0，c2为0
                    dp[i] = dp[i - 1];
                } else {
                    //c和c2都不为0
                    if (num <= 26) {
                        dp[i] = (dp[i - 1] + dp[i - 2]) % MOD;
                    } else {
                        dp[i] = dp[i - 1];
                    }
                }
            }
        }

        return (int) dp[s.length()];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public int numDecodings2(String s) {
        if (s.charAt(0) == '0') {
            return 0;
        }

        //dp初始化，没有数字或只有一个数字的解码方法个数为1，只有一个字符'*'的的解码方法个数为9
        long p = 1;
        long q = s.charAt(0) == '*' ? 9 : 1;

        for (int i = 2; i <= s.length(); i++) {
            //当前字符c
            char c = s.charAt(i - 1);
            //当前字符c的前一个字符
            char c2 = s.charAt(i - 2);

            //c和c2都为'*'
            if (c == '*' && c2 == '*') {
                //选1个字符，1-9，共9种
                //选2个字符，11-19，21-26，共15种
                long temp = (15 * p + 9 * q) % MOD;
                p = q;
                q = temp;
            } else if (c == '*') {
                //c2不为'*'，c为'*'

                if (c2 == '0') {
                    long temp = (9 * q) % MOD;
                    p = q;
                    q = temp;
                } else if (c2 == '1') {
                    long temp = (9 * q + 9 * p) % MOD;
                    p = q;
                    q = temp;
                } else if (c2 == '2') {
                    long temp = (9 * q + 6 * p) % MOD;
                    p = q;
                    q = temp;
                } else {
                    long temp = (9 * q) % MOD;
                    p = q;
                    q = temp;
                }
            } else if (c2 == '*') {
                //c不为'*'，c2为'*'

                if (c == '0') {
                    long temp = q;
                    q = (2 * p) % MOD;
                    p = temp;
                } else if (c <= '6') {
                    long temp = (q + 2 * p) % MOD;
                    p = q;
                    q = temp;
                } else {
                    long temp = (q + p) % MOD;
                    p = q;
                    q = temp;
                }
            } else {
                //c和c2都不为'*'

                //c2和c组成的数字
                int num = (c2 - '0') * 10 + (c - '0');

                //c和c2都为0
                if (c == '0' && c2 == '0') {
                    p = q;
                    q = 0;
                } else if (c == '0') {
                    //c2不为0，c为0
                    if (num <= 26) {
                        long temp = q;
                        q = p;
                        p = temp;
                    } else {
                        p = q;
                        q = 0;
                    }
                } else if (c2 == '0') {
                    //c不为0，c2为0
                    p = q;
                } else {
                    //c和c2都不为0
                    if (num <= 26) {
                        long temp = (q + p) % MOD;
                        p = q;
                        q = temp;
                    } else {
                        p = q;
                    }
                }
            }
        }

        return (int) q;
    }

    /**
     * 回溯+剪枝+记忆化搜索
     * dp[i]：s[i]-s[s.length()-1]构成的解码方法个数 (注意：和动态规划中dp的定义不同)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int numDecodings3(String s) {
        if (s.charAt(0) == '0') {
            return 0;
        }

        long[] dp = new long[s.length()];

        //dp初始化为-1，表示当前dp未访问
        for (int i = 0; i < s.length(); i++) {
            dp[i] = -1;
        }

        backtrack(0, s, dp);

        return (int) dp[0];
    }

    private long backtrack(int t, String s, long[] dp) {
        if (t == s.length()) {
            return 1;
        }

        if (dp[t] != -1) {
            return dp[t];
        }

        //当前字符
        char c = s.charAt(t);

        if (c == '0') {
            dp[t] = 0;
            return 0;
        }

        long count = 0;

        if (c == '*') {
            //往后找一个字符
            //选1个字符，1-9，共9种
            count = (count + 9 * backtrack(t + 1, s, dp)) % MOD;

            //往后找两个字符
            if (t + 1 < s.length()) {
                //当前字符c的后一个字符
                char c2 = s.charAt(t + 1);

                if (c2 == '*') {
                    //选2个字符，11-19，21-26，共15种
                    count = (count + 15 * backtrack(t + 2, s, dp)) % MOD;
                } else if (c2 <= '6') {
                    //选2个字符，1c2、2c2，共2种
                    count = (count + 2 * backtrack(t + 2, s, dp)) % MOD;
                } else {
                    //选2个字符，1c2，共1种
                    count = (count + backtrack(t + 2, s, dp)) % MOD;
                }
            }
        } else if (c == '1') {
            //往后找一个字符
            //选1个字符，c，共1种
            count = (count + backtrack(t + 1, s, dp)) % MOD;

            //往后找两个字符
            if (t + 1 < s.length()) {
                //当前字符c的后一个字符
                char c2 = s.charAt(t + 1);

                if (c2 == '*') {
                    //选2个字符，11-19，共9种
                    count = (count + 9 * backtrack(t + 2, s, dp)) % MOD;
                } else {
                    //选2个字符，1c2，共1种
                    count = (count + backtrack(t + 2, s, dp)) % MOD;
                }
            }
        } else if (c == '2') {
            //往后找一个字符
            //选1个字符，c，共1种
            count = (count + backtrack(t + 1, s, dp)) % MOD;

            //往后找两个字符
            if (t + 1 < s.length()) {
                //当前字符c的后一个字符
                char c2 = s.charAt(t + 1);

                if (c2 == '*') {
                    //选2个字符，21-26，共6种
                    count = (count + 6 * backtrack(t + 2, s, dp)) % MOD;
                } else if (c2 <= '6') {
                    //选2个字符，1c2，共1种
                    count = (count + backtrack(t + 2, s, dp)) % MOD;
                }
            }
        } else {
            //往后找一个字符
            //选1个字符，c，共1种
            count = (count + backtrack(t + 1, s, dp)) % MOD;
        }

        dp[t] = count;

        return dp[t];
    }
}
