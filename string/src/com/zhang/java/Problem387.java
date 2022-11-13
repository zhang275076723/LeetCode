package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/11/13 09:50
 * @Author zsy
 * @Description 字符串中的第一个唯一字符 类比Offer50
 * 给定一个字符串 s ，找到 它的第一个不重复的字符，并返回它的索引 。如果不存在，则返回 -1 。
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
     * 时间复杂度O(n)，空间复杂度O(Σ) (|Σ| = 26，s只包含小写字母，共26个字符)
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
            //Integer比较不能使用==，而应该使用equals()
            if (map.get(s.charAt(i)).equals(1)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 哈希表优化，不需要遍历字符串s，而最多只需要遍历26次哈希表中的字符
     * key：当前字符，value：当前字符第一次出现的下标索引，如果出现多次，赋值为-1
     * 时间复杂度O(n)，空间复杂度O(Σ) (|Σ| = 26，s只包含小写字母，共26个字符)
     *
     * @param s
     * @return
     */
    public int firstUniqChar2(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (map.containsKey(c)) {
                map.put(c, -1);
            } else {
                map.put(c, i);
            }
        }

        int index = s.length();

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            //Integer比较不能使用==，而应该使用equals()
            if (!entry.getValue().equals(-1)) {
                index = Math.min(index, entry.getValue());
            }
        }

        return index == s.length() ? -1 : index;
    }
}
