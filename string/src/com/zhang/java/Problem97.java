package com.zhang.java;

/**
 * @Date 2022/11/9 11:55
 * @Author zsy
 * @Description 交错字符串 动态规划类比Problem72、Problem115、Problem221、Problem516、Problem1143 回溯+剪枝类比Problem17、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem90、Problem216、Problem377、Problem491、Problem679、Offer17、Offer38
 * 给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
 * 两个字符串 s 和 t 交错 的定义与过程如下，其中每个字符串都会被分割成若干 非空 子字符串：
 * s = s1 + s2 + ... + sn
 * t = t1 + t2 + ... + tm
 * |n - m| <= 1
 * 交错 是 s1 + t1 + s2 + t2 + s3 + t3 + ... 或者 t1 + s1 + t2 + s2 + t3 + s3 + ...
 * 注意：a + b 意味着字符串 a 和 b 连接。
 * <p>
 * 输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
 * 输出：true
 * <p>
 * 输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
 * 输出：false
 * <p>
 * 输入：s1 = "", s2 = "", s3 = ""
 * 输出：true
 * <p>
 * 0 <= s1.length, s2.length <= 100
 * 0 <= s3.length <= 200
 * s1、s2、和 s3 都由小写英文字母组成
 */
public class Problem97 {
    public static void main(String[] args) {
        Problem97 problem97 = new Problem97();
        String s1 = "aabcc";
        String s2 = "dbbca";
        String s3 = "aadbbcbcac";
        System.out.println(problem97.isInterleave(s1, s2, s3));
        System.out.println(problem97.isInterleave2(s1, s2, s3));
        System.out.println(problem97.isInterleave3(s1, s2, s3));
    }

    /**
     * 动态规划
     * dp[i][j]：s1[0]-s1[i-1]和s2[0]-s2[j-1]能否交错形成s3[0]-s3[i+j-1]
     * dp[i][j] = (s1[i-1] == s3[i+j-1] && dp[i-1][j]) || (s2[j-1] == s3[i+j-1] && dp[i][j-1])
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=s1.length()，n=s2.length())
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }

        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
        //初始化，空串s1和空串s2构成空串s3
        dp[0][0] = true;

        for (int i = 1; i <= s1.length(); i++) {
            if (s1.charAt(i - 1) == s3.charAt(i - 1)) {
                dp[i][0] = true;
            } else {
                break;
            }
        }

        for (int j = 1; j <= s2.length(); j++) {
            if (s2.charAt(j - 1) == s3.charAt(j - 1)) {
                dp[0][j] = true;
            } else {
                break;
            }
        }

        for (int i = 1; i <= s1.length(); i++) {
            char c1 = s1.charAt(i - 1);
            for (int j = 1; j <= s2.length(); j++) {
                char c2 = s2.charAt(j - 1);
                char c3 = s3.charAt(i + j - 1);
                //s1[i-1]==s3[i+j-1]，并且s1[0]-s1[i-2]和s2[0]-s2[j-1]能够交错形成s3[0]-s3[i+j-2]；
                //或者s2[j-1]==s3[i+j-1]，并且s1[0]-s1[i-1]和s2[0]-s2[j-2]能够交错形成s3[0]-s3[i+j-2]
                dp[i][j] = (c1 == c3 && dp[i - 1][j]) || (c2 == c3 && dp[i][j - 1]);
            }
        }

        return dp[s1.length()][s2.length()];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(mn)，空间复杂度O(n) (m=s1.length()，n=s2.length())
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public boolean isInterleave2(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }

        boolean[] dp = new boolean[s2.length() + 1];
        //初始化，空串s1和空串s2构成空串s3
        dp[0] = true;

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i > 0) {
                    //判断情况s1[i-1]==s3[i+j-1]，并且s1[0]-s1[i-2]和s2[0]-s2[j-1]是否能够交错形成s3[0]-s3[i+j-2]
                    dp[j] = (s1.charAt(i - 1) == s3.charAt(i + j - 1)) && dp[j];
                }

                if (j > 0) {
                    //判断情况s2[j-1]==s3[i+j-1]，并且s1[0]-s1[i-1]和s2[0]-s2[j-2]是否能够交错形成s3[0]-s3[i+j-2]
                    //"||"保证两种情况有一种满足要求即可
                    dp[j] = dp[j] || (s2.charAt(j - 1) == s3.charAt(i + j - 1) && dp[j - 1]);
                }
            }
        }

        return dp[s2.length()];
    }

    /**
     * 回溯+剪枝
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public boolean isInterleave3(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }

        //从s1开始找，或从s2开始找，只要有一个能找到，则返回true
        //flag为1，表示从s1开始查找，flag为2，表示从s2开始查找
        return backtrack(0, 0, 1, s1, s2, s3) || backtrack(0, 0, 2, s1, s2, s3);
    }

    private boolean backtrack(int t1, int t2, int flag, String s1, String s2, String s3) {
        if (t1 == s1.length() && t2 == s2.length()) {
            return true;
        }

        //从s1开始查找
        if (flag == 1) {
            //s1[t1]和s3[t1+t2]相等，则可以继续从s2开始查找
            while (t1 < s1.length() && s1.charAt(t1) == s3.charAt(t1 + t2)) {
                t1++;
                if (backtrack(t1, t2, 2, s1, s2, s3)) {
                    return true;
                }
            }

            //当前s1没有和s3匹配的字符，直接返回false
            return false;
        } else {
            //从s2开始查找

            //s2[t2]和s3[t1+t2]相等，则可以继续从s1开始查找
            while (t2 < s2.length() && s2.charAt(t2) == s3.charAt(t1 + t2)) {
                t2++;
                if (backtrack(t1, t2, 1, s1, s2, s3)) {
                    return true;
                }
            }

            //当前s2没有和s3匹配的字符，直接返回false
            return false;
        }
    }
}
