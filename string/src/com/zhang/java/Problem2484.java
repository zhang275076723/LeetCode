package com.zhang.java;

/**
 * @Date 2024/4/14 08:25
 * @Author zsy
 * @Description 统计回文子序列数目 类比Problem1177、Problem1371、Problem1457、Problem1542、Problem1915、Problem1930、Problem2791 前缀和类比 状态压缩类比 回文类比
 * 给你数字字符串 s ，请你返回 s 中长度为 5 的 回文子序列 数目。
 * 由于答案可能很大，请你将答案对 10^9 + 7 取余 后返回。
 * 提示：
 * 如果一个字符串从前往后和从后往前读相同，那么它是 回文字符串 。
 * 子序列是一个字符串中删除若干个字符后，不改变字符顺序，剩余字符构成的字符串。
 * <p>
 * 输入：s = "103301"
 * 输出：2
 * 解释：
 * 总共有 6 长度为 5 的子序列："10330" ，"10331" ，"10301" ，"10301" ，"13301" ，"03301" 。
 * 它们中有两个（都是 "10301"）是回文的。
 * <p>
 * 输入：s = "0000000"
 * 输出：21
 * 解释：所有 21 个长度为 5 的子序列都是 "00000" ，都是回文的。
 * <p>
 * 输入：s = "9999900000"
 * 输出：2
 * 解释：仅有的两个回文子序列是 "99999" 和 "00000" 。
 * <p>
 * 1 <= s.length <= 10^4
 * s 只包含数字字符。
 */
public class Problem2484 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem2484 problem2484 = new Problem2484();
        String s = "0000000";
        System.out.println(problem2484.countPalindromes(s));
    }

    /**
     * 前缀和
     * preSum1[i][j]：s[0]-s[i-1]中数字j出现的次数
     * preSum2[i][j][k]：s[0]-s[i-1]中子序列数字jk出现的次数
     * sufSum1[i][j]：s[i+1]-s[s.length()-1]中数字j出现的次数
     * sufSum2[i][j][k]：s[i+1]-s[s.length()-1]中子序列数字jk出现的次数
     * 时间复杂度O(n*|Σ|^2)，空间复杂度O(n*|Σ|^2) (|Σ|=10，只包含数字字符)
     *
     * @param s
     * @return
     */
    public int countPalindromes(String s) {
        if (s.length() < 5) {
            return 0;
        }

        //s只包含数字0-9
        int[][] preSum1 = new int[s.length() + 1][10];
        int[][][] preSum2 = new int[s.length() + 1][10][10];
        //后缀数组长度不需要加1
        int[][] sufSum1 = new int[s.length()][10];
        int[][][] sufSum2 = new int[s.length()][10][10];

        for (int i = 0; i < s.length(); i++) {
            int num1 = s.charAt(i) - '0';

            //求s[0]-s[i]中数字num2出现的次数preSum1[i+1][num2]
            for (int num2 = 0; num2 <= 9; num2++) {
                if (num1 == num2) {
                    preSum1[i + 1][num2] = preSum1[i][num2] + 1;
                } else {
                    preSum1[i + 1][num2] = preSum1[i][num2];
                }
            }

            //求s[0]-s[i]中子序列数字num2num3出现的次数preSum2[i+1][num2][num3]
            for (int num2 = 0; num2 <= 9; num2++) {
                for (int num3 = 0; num3 <= 9; num3++) {
                    if (num1 == num3) {
                        //s[0]-s[i-1]中子序列数字num2num3出现的次数preSum2[i][num2][num3]，
                        //加上s[0]-s[i-1]中数字num2出现的次数preSum1[i][num2]
                        preSum2[i + 1][num2][num3] = preSum2[i][num2][num3] + preSum1[i][num2];
                    } else {
                        preSum2[i + 1][num2][num3] = preSum2[i][num2][num3];
                    }
                }
            }
        }

        //注意：sufSum对应的是i>0，而不是i>=0
        for (int i = s.length() - 1; i > 0; i--) {
            int num1 = s.charAt(i) - '0';

            //求s[i]-s[s.length()-1]中数字num2出现的次数sufSum1[i-1][num2]
            for (int num2 = 0; num2 <= 9; num2++) {
                if (num1 == num2) {
                    sufSum1[i - 1][num2] = sufSum1[i][num2] + 1;
                } else {
                    sufSum1[i - 1][num2] = sufSum1[i][num2];
                }
            }

            //求s[i]-s[s.length()-1]中子序列数字num2num3出现的次数sufSum2[i+1][num2][num3]
            for (int num2 = 0; num2 <= 9; num2++) {
                for (int num3 = 0; num3 <= 9; num3++) {
                    //注意：sufSum对应的是num1和num2比较，而preSum对应的是num1和num3比较
                    if (num1 == num2) {
                        //s[i+1]-s[s.length()-1]中子序列数字num2num3出现的次数sufSum2[i][num2][num3]，
                        //加上s[i+1]-s[s.length()-1]中数字num3出现的次数sufSum1[i-1][num3]
                        sufSum2[i - 1][num2][num3] = sufSum2[i][num2][num3] + sufSum1[i][num3];
                    } else {
                        sufSum2[i - 1][num2][num3] = sufSum2[i][num2][num3];
                    }
                }
            }
        }

        //使用long，避免int相乘溢出
        long count = 0;

        //s中长度为5的回文不需要从0开始，也不需要从n-1结束
        //求以s[i]作为中心数字，子序列数字num1num2s[i]num2num1出现的次数
        for (int i = 2; i < s.length() - 2; i++) {
            for (int num1 = 0; num1 <= 9; num1++) {
                for (int num2 = 0; num2 <= 9; num2++) {
                    count = (count + (long) preSum2[i][num1][num2] * sufSum2[i][num2][num1]) % MOD;
                }
            }
        }

        return (int) count;
    }
}
