package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2025/5/1 08:00
 * @Author zsy
 * @Description 最长重复子串 类比Problem718 同Problem1044 动态规划类比Problem516 字符串哈希类比Problem187、Problem1044、Problem1316、Problem1392、Problem1698、Problem3029、Problem3031、Problem3042、Problem3045、Problem3076 子序列和子数组类比
 * 给定字符串 s，找出最长重复子串的长度。如果不存在重复子串就返回 0。
 * <p>
 * 输入："abcd"
 * 输出：0
 * 解释：没有重复子串。
 * <p>
 * 输入："abbaba"
 * 输出：2
 * 解释：最长的重复子串为 "ab" 和 "ba"，每个出现 2 次。
 * <p>
 * 输入："aabcaabdaab"
 * 输出：3
 * 解释：最长的重复子串为 "aab"，出现 3 次。
 * <p>
 * 1 <= s.length <= 2000
 * 字符串 s 仅包含从 'a' 到 'z' 的小写英文字母。
 */
public class Problem1062 {
    public static void main(String[] args) {
        Problem1062 problem1062 = new Problem1062();
        String s = "aabcaabdaab";
        System.out.println(problem1062.longestRepeatingSubstring(s));
        System.out.println(problem1062.longestRepeatingSubstring2(s));
    }

    /**
     * 动态规划
     * 核心思想：s的最长重复子串等价于s和s的最长公共子数组
     * dp[i][j]：以s[i-1]结尾的数组和以s[j-1]结尾的数组，两个数组的最长公共子数组长度
     * dp[i][j] = dp[i-1][j-1] + 1 (i != j && s[i-1] == s[j-1])
     * dp[i][j] = 0                (s[i-1] != s[j-1])
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int longestRepeatingSubstring(String s) {
        int maxLen = 0;
        int[][] dp = new int[s.length() + 1][s.length() + 1];

        for (int i = 1; i <= s.length(); i++) {
            char c1 = s.charAt(i - 1);

            for (int j = 1; j <= s.length(); j++) {
                char c2 = s.charAt(j - 1);

                //注意：相同子串不是重复子串，即i不能等于j
                if (i != j && c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }

                maxLen = Math.max(maxLen, dp[i][j]);
            }
        }

        return maxLen;
    }

    /**
     * 字符串哈希+二分查找
     * hash[i]：s[0]-s[i-1]的哈希值
     * prime[i]：p^i的值
     * hash[j+1]-hash[i]*prime[j-i+1]：s[i]-s[j]的哈希值
     * 核心思想：将字符串看成P进制数，再对MOD取余，作为当前字符串的哈希值，只要两个字符串哈希值相等，则认为两个字符串相等
     * 一般取P为较大的质数，P=131或P=13331或P=131313，此时产生的哈希冲突低；
     * 一般取MOD=2^63(long类型最大值+1)，在计算时不处理溢出问题，产生溢出相当于自动对MOD取余；
     * 如果产生哈希冲突，则使用双哈希来减少冲突
     * 对[left,right]进行二分查找，left为0，right为s.length()，判断s中是否存在长度为mid的重复子串，
     * 如果s中存在长度为mid的重复子串，则s中最长的重复子串长度在mid或在mid右边，left=mid；
     * 如果s中不存在长度为mid的重复子串，则s中最长的重复子串长度在mid左边，right=mid-1
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int longestRepeatingSubstring2(String s) {
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

        int left = 0;
        int right = s.length();
        int mid;

        while (left < right) {
            //mid往右偏移，因为转移条件是right=mid-1，避免无法跳出循环
            mid = left + ((right - left) >> 1) + 1;

            //s中是否存在长度为mid的重复子串标志位
            boolean flag = false;
            //存储s中长度为mid的字符串的哈希值
            Set<Long> set = new HashSet<>();

            for (int i = 0; i <= s.length() - mid; i++) {
                //s[i]-s[i+mid-1]的哈希值
                long h = hash[i + mid] - hash[i] * prime[mid];

                if (set.contains(h)) {
                    flag = true;
                    break;
                }

                set.add(h);
            }

            //s中存在长度为mid的重复子串，则left=mid
            if (flag) {
                left = mid;
            } else {
                //s中不存在长度为mid的重复子串，则right=mid-1
                right = mid - 1;
            }
        }

        return left;
    }
}
