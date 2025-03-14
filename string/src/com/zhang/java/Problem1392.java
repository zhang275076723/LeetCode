package com.zhang.java;

/**
 * @Date 2025/4/18 08:02
 * @Author zsy
 * @Description 最长快乐前缀 kmp类比Problem28、Problem214、Problem459、Problem471、Problem686、Problem796、Problem1408、Problem3029、Problem3031 字符串哈希类比Problem187、Problem1044、Problem1316、Problem1698、Problem3029、Problem3031、Problem3042、Problem3045、Problem3076
 * 「快乐前缀」 是在原字符串中既是 非空 前缀也是后缀（不包括原字符串自身）的字符串。
 * 给你一个字符串 s，请你返回它的 最长快乐前缀。如果不存在满足题意的前缀，则返回一个空字符串 "" 。
 * <p>
 * 输入：s = "level"
 * 输出："l"
 * 解释：不包括 s 自己，一共有 4 个前缀（"l", "le", "lev", "leve"）和 4 个后缀（"l", "el", "vel", "evel"）。
 * 最长的既是前缀也是后缀的字符串是 "l" 。
 * <p>
 * 输入：s = "level"
 * 输出："abab"
 * 解释："abab" 是最长的既是前缀也是后缀的字符串。题目允许前后缀在原字符串中重叠。
 * <p>
 * 1 <= s.length <= 10^5
 * s 只含有小写英文字母
 */
public class Problem1392 {
    public static void main(String[] args) {
        Problem1392 problem1392 = new Problem1392();
        String s = "ababab";
        System.out.println(problem1392.longestPrefix(s));
        System.out.println(problem1392.longestPrefix2(s));
    }

    /**
     * kmp
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String longestPrefix(String s) {
        int[] next = new int[s.length()];
        int j = 0;

        //注意：i从1开始遍历，因为s[0]-s[0]不存在公共前缀和后缀
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = next[j - 1];
            }

            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }

            next[i] = j;
        }

        //如果不存在最长公共前缀和后缀，则返回""；否则返回最长公共前缀和后缀
        return next[s.length() - 1] == 0 ? "" : s.substring(0, next[s.length() - 1]);
    }

    /**
     * 字符串哈希
     * hash[i]：s[0]-s[i-1]的哈希值
     * prime[i]：p^i的值
     * hash[j+1]-hash[i]*prime[j-i+1]：s[i]-s[j]的哈希值
     * 核心思想：将字符串看成P进制数，再对MOD取余，作为当前字符串的哈希值，只要两个字符串哈希值相等，则认为两个字符串相等
     * 一般取P为较大的质数，P=131或P=13331或P=131313，此时产生的哈希冲突低；
     * 一般取MOD=2^63(long类型最大值+1)，在计算时不处理溢出问题，产生溢出相当于自动对MOD取余；
     * 如果产生哈希冲突，则使用双哈希来减少冲突
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String longestPrefix2(String s) {
        //大质数，p进制
        int p = 131;
        long[] hash = new long[s.length() + 1];
        long[] prime = new long[s.length() + 1];

        //p^0初始化
        prime[0] = 1;

        for (int i = 1; i <= s.length(); i++) {
            char c = s.charAt(i - 1);
            //注意：不需要进行取模运算，产生溢出相当于自动对MOD取模
            hash[i] = hash[i - 1] * p + c;
            prime[i] = prime[i - 1] * p;
        }

        //判断前缀s[0]-s[i]和后缀s[s.length()-1-i]-s[s.length()-1]是否相等
        //注意：i从s.length()-2开始，因为原字符串s不能作为公共前缀和后缀
        for (int i = s.length() - 2; i >= 0; i--) {
            //前缀s[0]-s[i]的哈希值
            long preHash = hash[i + 1] - hash[0] * prime[i + 1];
            //后缀s[s.length()-1-i]-s[s.length()-1]的哈希值
            long sufHash = hash[s.length()] - hash[s.length() - 1 - i] * prime[i + 1];

            if (preHash == sufHash) {
                return s.substring(0, i + 1);
            }
        }

        //遍历结束，则不存在最长公共前缀和后缀，返回""
        return "";
    }
}
