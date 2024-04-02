package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/8/3 10:49
 * @Author zsy
 * @Description 至多包含 K 个不同字符的最长子串 滑动窗口类比Problem3、Problem30、Problem76、Problem209、Problem219、Problem220、Problem239、Problem438、Problem485、Problem487、Problem567、Problem632、Problem643、Problem713、Problem1004、Problem1456、Problem1839、Problem2062、Offer48、Offer57_2、Offer59
 * 给你一个字符串 s 和一个整数 k ，请你找出 至多 包含 k 个 不同 字符的最长子串，并返回该子串的长度。
 * <p>
 * 输入：s = "eceba", k = 2
 * 输出：3
 * 解释：满足题目要求的子串是 “ece” ，长度为 3 。
 * <p>
 * 输入：s = "aa", k = 1
 * 输出：2
 * 解释：满足题目要求的子串是 “aa” ，长度为 2 。
 * <p>
 * 1 <= s.length <= 5 * 10^4
 * 0 <= k <= 50
 */
public class Problem340 {
    public static void main(String[] args) {
        Problem340 problem340 = new Problem340();
        String s = "eceba";
        int k = 2;
        System.out.println(problem340.lengthOfLongestSubstringKDistinct(s, k));
    }

    /**
     * 滑动窗口，双指针
     * 时间复杂度O(n)，空间复杂度O(|Σ|) (|Σ|=128，ascii码)
     *
     * @param s
     * @param k
     * @return
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k == 0) {
            return 0;
        }

        //key：当前字符，value：当前字符出现的次数
        Map<Character, Integer> map = new HashMap<>();
        int left = 0;
        int right = 0;
        //包含k个不同字符的最长子串长度
        int maxLen = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            map.put(c, map.getOrDefault(c, 0) + 1);

            while (map.size() > k) {
                char c2 = s.charAt(left);
                map.put(c2, map.get(c2) - 1);
                if (map.get(c2) == 0) {
                    map.remove(c2);
                }
                //左指针右移
                left++;
            }

            //更新包含k个不同字符的最长子串长度
            maxLen = Math.max(maxLen, right - left + 1);
            //右指针右移
            right++;
        }

        return maxLen;
    }
}
