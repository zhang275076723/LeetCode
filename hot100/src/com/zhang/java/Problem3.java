package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/4/12 8:59
 * @Author zsy
 * @Description 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * <p>
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * <p>
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 * <p>
 * 0 <= s.length <= 5 * 10^4
 * s 由英文字母、数字、符号和空格组成
 */
public class Problem3 {
    public static void main(String[] args) {
        Problem3 problem3 = new Problem3();
        String s = "pwwkew";
        System.out.println(problem3.lengthOfLongestSubstring(s));
        System.out.println(problem3.lengthOfLongestSubstring2(s));
        System.out.println(problem3.lengthOfLongestSubstring3(s));
    }

    /**
     * 动态规划，时间复杂度O(n^2)，空间复杂度O(n)
     * dp[i]：以索引下标i结尾的字符串s的最大子串长度
     * j为s[i]之前相同字符的索引下标
     * dp[i] = dp[i-1] + 1 (i-j > dp[i-1])
     * dp[i] = i-j (i-j <= dp[i-1])
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }

        int max = 0;
        int[] dp = new int[s.length()];
        dp[0] = 1;

        for (int i = 1; i < s.length(); i++) {
            //j为s[i]之前相同字符的索引下标
            int j = -1;
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
        if (s == null || s.length() == 0) {
            return 0;
        }

        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        int dp = 0;

        for (int i = 0; i < s.length(); i++) {
            //s[i]之前相同字符的索引下标
            Integer j = map.getOrDefault(s.charAt(i), -1);
            map.put(s.charAt(i), i);

            if (i - j > dp) {
                dp++;
            } else {
                dp = i - j;
            }

            max = Math.max(max, dp);
        }

        return max;
    }

    /**
     * 滑动窗口，时间复杂度O(n)，空间复杂度O(|Σ|)，|Σ|=128，ascii码
     * 使用哈希表，在O(1)时间内找到s[i]之前相同字符的索引下标
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            if (map.containsKey(s.charAt(right))) {
                //左指针右移
                left = Math.max(left, map.get(s.charAt(right)) + 1);
            }
            map.put(s.charAt(right), right);

            max = Math.max(max, right - left + 1);
        }

        return max;
    }
}
