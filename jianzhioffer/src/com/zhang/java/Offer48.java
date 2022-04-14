package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/3/29 15:59
 * @Author zsy
 * @Description 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
 * <p>
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * <p>
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * <p>
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是"wke"，所以其长度为 3。
 * 请注意，你的答案必须是 子串 的长度，"pwke"是一个子序列，不是子串。
 */
public class Offer48 {
    public static void main(String[] args) {
        Offer48 offer48 = new Offer48();
        String s = " ";
        System.out.println(offer48.lengthOfLongestSubstring(s));
        System.out.println(offer48.lengthOfLongestSubstring2(s));
    }

    /**
     * 动态规划，时间复杂度O(n^2)，空间复杂度O(n)
     * dp[i]：以s[i]结尾的最长子串长度
     * j为s[i]之前相同字符的索引下标
     * dp[i] = dp[i-1] + 1 (i-j > dp[i-1]，说明s[j]不在以i-1结尾的最长子串中)
     * dp[i] = i-j (i-j <= dp[i-1]，说明s[j]在以i-1结尾的最长子串中)
     * s[j]为s[i]之前相同的字符
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0 || s.length() == 1) {
            return s.length();
        }

        int max = 0;
        int[] dp = new int[s.length()];
        dp[0] = 1;
        //j为s[i]之前相同字符的索引下标
        int j;

        for (int i = 1; i < s.length(); i++) {
            //j初值
            j = -1;
            for (int k = i - 1; k >= 0; k--) {
                if (s.charAt(k) == s.charAt(i)) {
                    j = k;
                    break;
                }
            }

            if (i - j > dp[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = i - j;
            }
            max = Math.max(max, dp[i]);
        }

        return max;
    }

    /**
     * 动态规划优化，时间复杂度O(n)，空间复杂度O(|Σ|)，|Σ|=128，ascii码
     * 使用哈希表，在O(1)时间内找到s[i]之前相同字符的索引下标
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring2(String s) {
        //使用哈希表，在O(1)时间内找到s[i]之前相同字符的索引下标
        Map<Character, Integer> map = new HashMap<>();
        int dp = 0;
        int max = 0;

        for (int i = 0; i < s.length(); i++) {
            //j为s[i]之前相同字符的索引下标
            int j = map.getOrDefault(s.charAt(i), -1);
            if (i - j > dp) {
                dp++;
            } else {
                dp = i - j;
            }
            max = Math.max(max, dp);
            map.put(s.charAt(i), i);
        }

        return max;
    }
}
