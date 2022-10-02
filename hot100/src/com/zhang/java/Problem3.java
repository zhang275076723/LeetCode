package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/4/12 8:59
 * @Author zsy
 * @Description 无重复字符的最长子串 类比Problem76、Problem438、Problem567 同Offer48
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
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
     * 动态规划
     * dp[i]：以s[i]结尾的最长不含重复字符的子串长度
     * j为s[i]之前相同字符的索引下标
     * dp[i] = dp[i-1] + 1 (i-j > dp[i-1]，说明s[j]不在以s[i-1]结尾的最长子串中)
     * dp[i] = i-j         (i-j <= dp[i-1]，说明s[j]在以s[i-1]结尾的最长子串中)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        //当前字符在字符串中最后一次出现的索引下标
        Map<Character, Integer> map = new HashMap<>();
        int[] dp = new int[s.length()];
        int max = 1;

        map.put(s.charAt(0), 0);
        dp[0] = 1;

        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            int j = map.getOrDefault(c, -1);

            if (i - j > dp[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = i - j;
            }

            max = Math.max(max, dp[i]);
            map.put(c, i);
        }

        return max;
    }

    /**
     * 动态规划优化，使用滚动数组
     * 使用哈希表，在O(1)时间内找到s[i]之前相同字符的索引下标
     * 时间复杂度O(n)，空间复杂度O(|Σ|)，|Σ|=128，ascii码
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        //当前字符在字符串中最后一次出现的索引下标
        Map<Character, Integer> map = new HashMap<>();
        map.put(s.charAt(0), 0);
        int dp = 1;
        int max = 1;

        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            int j = map.getOrDefault(c, -1);

            if (i - j > dp) {
                dp++;
            } else {
                dp = i - j;
            }

            max = Math.max(max, dp);
            map.put(c, i);
        }

        return max;
    }

    /**
     * 滑动窗口，双指针
     * 时间复杂度O(n)，空间复杂度O(|Σ|)，|Σ|=128，ascii码
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int max = 0;
        int left = 0;
        int right = 0;
        //当前字符在字符串中最后一次出现的索引下标
        Map<Character, Integer> map = new HashMap<>();

        while (right < s.length()) {
            char c = s.charAt(right);

            //当前字符c在当前子串中出现，左指针右移
            if (map.containsKey(c) && map.get(c) >= left) {
                left = map.get(c) + 1;
            }

            //更新当前字符c在map中最后一次出现的索引
            map.put(c, right);
            max = Math.max(max, right - left + 1);
            right++;
        }

        return max;
    }
}
