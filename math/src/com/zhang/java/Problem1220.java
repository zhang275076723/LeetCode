package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/3/31 08:28
 * @Author zsy
 * @Description 统计元音字母序列的数目 矩阵快速幂类比Problem70、Problem509、Problem1137、Problem1641、Offer10、Offer10_2 元音类比Problem345、Problem824、Problem966、Problem1119、Problem1371、Problem1456、Problem1641、Problem1704、Problem1839、Problem2062、Problem2063、Problem2559、Problem2586、Problem2785
 * 给你一个整数 n，请你帮忙统计一下我们可以按下述规则形成多少个长度为 n 的字符串：
 * 字符串中的每个字符都应当是小写元音字母（'a', 'e', 'i', 'o', 'u'）
 * 每个元音 'a' 后面都只能跟着 'e'
 * 每个元音 'e' 后面只能跟着 'a' 或者是 'i'
 * 每个元音 'i' 后面 不能 再跟着另一个 'i'
 * 每个元音 'o' 后面只能跟着 'i' 或者是 'u'
 * 每个元音 'u' 后面只能跟着 'a'
 * 由于答案可能会很大，所以请你返回 模 10^9 + 7 之后的结果。
 * <p>
 * 输入：n = 1
 * 输出：5
 * 解释：所有可能的字符串分别是："a", "e", "i" , "o" 和 "u"。
 * <p>
 * 输入：n = 2
 * 输出：10
 * 解释：所有可能的字符串分别是："ae", "ea", "ei", "ia", "ie", "io", "iu", "oi", "ou" 和 "ua"。
 * <p>
 * 输入：n = 5
 * 输出：68
 * <p>
 * 1 <= n <= 2 * 10^4
 */
public class Problem1220 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem1220 problem1220 = new Problem1220();
        int n = 2;
        System.out.println(problem1220.countVowelPermutation(n));
        System.out.println(problem1220.countVowelPermutation2(n));
        System.out.println(problem1220.countVowelPermutation3(n));
    }

    /**
     * 动态规划
     * dp[i][j]：长度为i，并且以第j个元音结尾满足上述规则的元音字符串个数
     * dp[i][0] = dp[i-1][1] + dp[i-1][2] + dp[i-1][4] (元音'a'的前一个字符只能是元音'e'、'i'、'u')
     * dp[i][1] = dp[i-1][0] + dp[i-1][2]              (元音'e'的前一个字符只能是元音'a'、'i')
     * dp[i][2] = dp[i-1][1] + dp[i-1][3]              (元音'i'的前一个字符只能是元音'e'、'o')
     * dp[i][3] = dp[i-1][2]                           (元音'o'的前一个字符只能是元音'i')
     * dp[i][4] = dp[i-1][2] + dp[i-1][3]              (元音'u'的前一个字符只能是元音'i'、'o')
     * 时间复杂度O(n*|Σ|)=O(n)，空间复杂度O(n*|Σ|)=O(n) (|Σ|=5，只包含5个小写元音)
     *
     * @param n
     * @return
     */
    public int countVowelPermutation(int n) {
        //使用long，避免int溢出
        long[][] dp = new long[n + 1][5];

        //dp初始化，长度为1，并且以第j个元音结尾满足上述规则的元音字符串个数为1
        for (int j = 0; j < 5; j++) {
            dp[1][j] = 1;
        }

        for (int i = 2; i <= n; i++) {
            //元音'a'的前一个字符只能是元音'e'、'i'、'u'
            dp[i][0] = (dp[i - 1][1] + dp[i - 1][2] + dp[i - 1][4]) % MOD;
            //元音'e'的前一个字符只能是元音'a'、'i'
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % MOD;
            //元音'i'的前一个字符只能是元音'e'、'o'
            dp[i][2] = (dp[i - 1][1] + dp[i - 1][3]) % MOD;
            //元音'o'的前一个字符只能是元音'i'
            dp[i][3] = dp[i - 1][2] % MOD;
            //元音'u'的前一个字符只能是元音'i'、'o'
            dp[i][4] = (dp[i - 1][2] + dp[i - 1][3]) % MOD;
        }

        //长度为n，并且满足上述规则的元音字符串个数
        //使用long，避免int溢出
        long count = 0;

        for (int j = 0; j < 5; j++) {
            count = (count + dp[n][j]) % MOD;
        }

        return (int) count;
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n*|Σ|)=O(n)，空间复杂度O(|Σ|)=O(1) (|Σ|=5，只包含5个小写元音)
     *
     * @param n
     * @return
     */
    public int countVowelPermutation2(int n) {
        //使用long，避免int溢出
        long[] dp = new long[5];

        //dp初始化，长度为1，并且以第i个元音结尾满足上述规则的元音字符串个数为1
        for (int i = 0; i < 5; i++) {
            dp[i] = 1;
        }

        for (int i = 2; i <= n; i++) {
            //临时数组，用于更新dp
            //使用long，避免int溢出
            long[] tempArr = new long[5];

            tempArr[0] = (dp[1] + dp[2] + dp[4]) % MOD;
            tempArr[1] = (dp[0] + dp[2]) % MOD;
            tempArr[2] = (dp[1] + dp[3]) % MOD;
            tempArr[3] = dp[2] % MOD;
            tempArr[4] = (dp[2] + dp[3]) % MOD;

            //tempArr重新赋值给dp
            dp = Arrays.copyOfRange(tempArr, 0, tempArr.length);
        }

        //长度为n，并且满足上述规则的元音字符串个数
        //使用long，避免int溢出
        long count = 0;

        for (int i = 0; i < 5; i++) {
            count = (count + dp[i]) % MOD;
        }

        return (int) count;
    }

    /**
     * 动态规划+矩阵快速幂
     * dp[i][j]：长度为i，并且以第j个元音结尾满足上述规则的元音字符串个数
     * dp[i][0] = dp[i-1][1] + dp[i-1][2] + dp[i-1][4] (元音'a'的前一个字符只能是元音'e'、'i'、'u')
     * dp[i][1] = dp[i-1][0] + dp[i-1][2]              (元音'e'的前一个字符只能是元音'a'、'i')
     * dp[i][2] = dp[i-1][1] + dp[i-1][3]              (元音'i'的前一个字符只能是元音'e'、'o')
     * dp[i][3] = dp[i-1][2]                           (元音'o'的前一个字符只能是元音'i')
     * dp[i][4] = dp[i-1][2] + dp[i-1][3]              (元音'u'的前一个字符只能是元音'i'、'o')
     * [dp[n][0]]         [0 1 1 0 1] ^ (n-1)         [dp[1][0]]
     * [dp[n][1]]         [1 0 1 0 0]                 [dp[1][1]]
     * [dp[n][2]]    =    [0 1 0 1 0]        *        [dp[1][2]]
     * [dp[n][3]]         [0 0 1 0 0]                 [dp[1][3]]
     * [dp[n][4]]         [0 0 1 1 0]                 [dp[1][4]]
     * 时间复杂度O(logn*|Σ|^3)=O(logn)，空间复杂度O(|Σ|^2)=O(1) (|Σ|=5，只包含5个小写元音)
     *
     * @param n
     * @return
     */
    public int countVowelPermutation3(int n) {
        //使用long，避免int溢出
        long[][] result = {
                {0, 1, 1, 0, 1},
                {1, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 1, 0}
        };

        result = quickPow(result, n - 1);
        //使用long，避免int溢出
        result = multiply(result, new long[][]{{1}, {1}, {1}, {1}, {1}});

        //长度为n，并且满足上述规则的元音字符串个数
        //使用long，避免int溢出
        long count = 0;

        for (int i = 0; i < 5; i++) {
            count = (count + result[i][0]) % MOD;
        }

        return (int) count;
    }

    /**
     * 二维矩阵快速幂
     * 时间复杂度O(logn*|Σ|^3)=O(logn)，空间复杂度O(1) (|Σ|=5，只包含5个小写元音)
     *
     * @param a
     * @param n
     * @return
     */
    private long[][] quickPow(long[][] a, int n) {
        //使用long，避免int溢出
        long[][] result = new long[a.length][a.length];

        //result初始化，初始化为单位矩阵
        for (int i = 0; i < a.length; i++) {
            result[i][i] = 1;
        }

        while (n != 0) {
            if ((n & 1) == 1) {
                //注意乘积先后顺序
                result = multiply(result, a);
            }

            a = multiply(a, a);
            n = n >>> 1;
        }

        return result;
    }

    /**
     * 二维矩阵a和二维矩阵b相乘
     * 时间复杂度O(a.length*b[0].length*a[0].length)，空间复杂度O(1)
     *
     * @param a
     * @param b
     * @return
     */
    private long[][] multiply(long[][] a, long[][] b) {
        //使用long，避免int溢出
        long[][] result = new long[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] = (result[i][j] + (a[i][k] * b[k][j]) % MOD) % MOD;
                }
            }
        }

        return result;
    }
}
