package com.zhang.java;

/**
 * @Date 2023/4/12 09:10
 * @Author zsy
 * @Description 判断子序列 动态规划类比Problem72、Problem97、Problem115、Problem132、Problem139、Problem221、Problem516、Problem1143、Problem1312 子序列和子数组类比Problem53、Problem115、Problem152、Problem209、Problem300、Problem325、Problem491、Problem516、Problem525、Problem560、Problem581、Problem659、Problem673、Problem674、Problem718、Problem862、Problem1143、Offer42、Offer57_2
 * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
 * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。
 * （例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
 * 进阶：
 * 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。
 * 在这种情况下，你会怎样改变代码？
 * <p>
 * 输入：s = "abc", t = "ahbgdc"
 * 输出：true
 * <p>
 * 输入：s = "axc", t = "ahbgdc"
 * 输出：false
 * <p>
 * 0 <= s.length <= 100
 * 0 <= t.length <= 10^4
 * 两个字符串都只由小写字符组成。
 */
public class Problem392 {
    public static void main(String[] args) {
        Problem392 problem392 = new Problem392();
        String s = "abc";
        String t = "ahbgdc";
        System.out.println(problem392.isSubsequence(s, t));
        System.out.println(problem392.isSubsequence2(s, t));
    }

    /**
     * 双指针
     * 遍历字符串t，如果s[i]==s[j]，则i指针右移，最后判断i是否遍历到字符串s末尾，即i是否等于s.length()
     * 时间复杂度O(n)，空间复杂度O(1) (n=t.length())
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) {
            return true;
        }

        if (s.length() > t.length()) {
            return false;
        }

        //字符串s的下标索引
        int index = 0;

        for (int i = 0; i < t.length(); i++) {
            //s[index]和t[i]相等，index后移
            if (s.charAt(index) == t.charAt(i)) {
                index++;
            }

            //index已经遍历完，则s是t的子序列，返回true
            if (index == s.length()) {
                return true;
            }
        }

        //遍历结束，则s不是t的子序列，返回false
        return false;
    }

    /**
     * 动态规划
     * dp[i][j]：从t[i]开始往后第一次出现字符'a'+j的下标索引
     * dp[i][j] = i          (t[i] == 'a'+j)
     * dp[i][j] = dp[i+1][j] (t[i] != 'a'+j)
     * 时间复杂度O(n*|Σ|+m)=O(m+n)，空间复杂度O(n*|Σ|)=O(n) (m=s.length()，n=t.length()，|Σ|=26，只包含小写字母)
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence2(String s, String t) {
        //只包含小写字母，所以数组二维长度为26
        int[][] dp = new int[t.length() + 1][26];

        //dp初始化
        for (int j = 0; j < 26; j++) {
            //-1表示字符串t中不存在字符'a'+j
            dp[t.length()][j] = -1;
        }

        for (int i = t.length() - 1; i >= 0; i--) {
            char c = t.charAt(i);

            for (int j = 0; j < 26; j++) {
                //t[i]等于字符'a'+j，则从t[i]开始往后第一次出现字符'a'+j的下标索引为i
                if (c == 'a' + j) {
                    dp[i][j] = i;
                } else {
                    //t[i]不等于字符'a'+j，则从t[i]开始往后第一次出现字符'a'+j的下标索引为dp[i+1][j]
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }

        //字符串t的下标索引
        int index = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //从t[index]开始往后不存在字符c，则直接返回false
            if (dp[index][c - 'a'] == -1) {
                return false;
            } else {
                //从t[index]开始往后存在字符c，更新index为从t[index]开始往后第一次出现字符c的下标索引dp[index][c-'a']+1
                index = dp[index][c - 'a'] + 1;
            }
        }

        return true;
    }
}
