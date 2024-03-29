package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/3/29 15:59
 * @Author zsy
 * @Description 最长不含重复字符的子字符串 滑动窗口类比Problem3、Problem30、Problem76、Problem209、Problem219、Problem220、Problem239、Problem340、Problem438、Problem485、Problem487、Problem567、Problem632、Problem643、Problem713、Problem1004、Offer57_2、Offer59 同Problem3
 * 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
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
 * <p>
 * s.length <= 40000
 */
public class Offer48 {
    public static void main(String[] args) {
        Offer48 offer48 = new Offer48();
        String s = "abcabcbb";
        System.out.println(offer48.lengthOfLongestSubstring(s));
        System.out.println(offer48.lengthOfLongestSubstring2(s));
        System.out.println(offer48.lengthOfLongestSubstring3(s));
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

        //当前字符在字符串中上次出现的下标索引
        Map<Character, Integer> map = new HashMap<>();
        int[] dp = new int[s.length()];
        int max = 1;

        //初始化，第一个字符放入map中
        map.put(s.charAt(0), 0);
        dp[0] = 1;

        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            //当前字符c上次出现的下标索引
            int index = map.getOrDefault(c, -1);

            //当前字符c在以s[i-1]结尾表示的不重复字符串中出现，则dp[i]=i-index
            if (i - index <= dp[i - 1]) {
                dp[i] = i - index;
            } else {
                //当前字符c在以s[i-1]结尾表示的不重复字符串中没有出现，则dp[i]=dp[i-1]+1
                dp[i] = dp[i - 1] + 1;
            }

            max = Math.max(max, dp[i]);
            //更新当前字符c上次出现的下标索引
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

        //当前字符在字符串中上次出现的下标索引
        Map<Character, Integer> map = new HashMap<>();
        //初始化，第一个字符放入map中
        map.put(s.charAt(0), 0);
        int dp = 1;
        int max = 1;

        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            //当前字符c上次出现的下标索引
            int index = map.getOrDefault(c, -1);

            //当前字符c在以s[i-1]结尾表示的不重复字符串中出现，则dp[i]=i-index
            if (i - index <= dp) {
                dp = i - index;
            } else {
                //当前字符c在以s[i-1]结尾表示的不重复字符串中没有出现，则dp[i]=dp[i-1]+1
                dp++;
            }

            max = Math.max(max, dp);
            //更新当前字符c上次出现的下标索引
            map.put(c, i);
        }

        return max;
    }

    /**
     * 滑动窗口，双指针
     * 时间复杂度O(n)，空间复杂度O(|Σ|) (|Σ|=128，ascii码)
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int max = 1;
        int left = 0;
        int right = 0;
        //当前字符在字符串中上次出现的下标索引
        Map<Character, Integer> map = new HashMap<>();

        while (right < s.length()) {
            char c = s.charAt(right);
            //当前字符c上次出现的下标索引
            int index = map.getOrDefault(c, -1);

            //当前字符c上次出现的下标索引大于等于左指针left时，左指针右移，变为index+1
            if (index >= left) {
                left = index + 1;
            }

            max = Math.max(max, right - left + 1);
            //更新当前字符c上次出现的下标索引
            map.put(c, right);
            //右指针右移
            right++;
        }

        return max;
    }
}
