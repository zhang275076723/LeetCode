package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/11/13 09:50
 * @Author zsy
 * @Description 字符串中的第一个唯一字符 哈希表类比Problem1、Problem128、Problem166、Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem389、Problem454、Problem532、Problem535、Problem554、Problem763、Problem1640、Offer50 同Offer50
 * 给定一个字符串 s ，找到 它的第一个不重复的字符，并返回它的索引 。
 * 如果不存在，则返回 -1 。
 * <p>
 * 输入: s = "leetcode"
 * 输出: 0
 * <p>
 * 输入: s = "loveleetcode"
 * 输出: 2
 * <p>
 * 输入: s = "aabb"
 * 输出: -1
 * <p>
 * 1 <= s.length <= 10^5
 * s 只包含小写字母
 */
public class Problem387 {
    public static void main(String[] args) {
        Problem387 problem387 = new Problem387();
        String s = "leetcode";
        System.out.println(problem387.firstUniqChar(s));
        System.out.println(problem387.firstUniqChar2(s));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(|Σ|) (|Σ| = 26，s只包含小写字母，共26个字符)
     *
     * @param s
     * @return
     */
    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < s.length(); i++) {
            //Integer之间的比较不能使用==，而应该使用equals()；Integer和int的比较，可以使用==，自动拆箱
            if (map.get(s.charAt(i)) == 1) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 哈希表优化
     * 不需要遍历字符串s，最多只需要遍历26次哈希表，因为s只包含小写字母，在字符串很长的情况下可以提升性能
     * key：当前字符，value：当前字符第一次出现的下标索引，如果出现多次，则不是只出现一次的字符，赋值为-1
     * 时间复杂度O(n)，空间复杂度O(|Σ|) (|Σ| = 26，s只包含小写字母，共26个字符)
     *
     * @param s
     * @return
     */
    public int firstUniqChar2(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //当前字符之前出现过，则不是只出现一次的字符，赋值为-1
            if (map.containsKey(c)) {
                map.put(c, -1);
            } else {
                //当前字符第一次出现，存放当前字符的下标索引
                map.put(c, i);
            }
        }

        int index = s.length();

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            //Integer之间的比较不能使用==，而应该使用equals()；Integer和int的比较，可以使用==，自动拆箱
            if (entry.getValue() != -1) {
                index = Math.min(index, entry.getValue());
            }
        }

        //遍历完map没有找到只出现一次的字符，则返回-1；找到，则返回只出现一次的字符下标索引
        return index == s.length() ? -1 : index;
    }
}
