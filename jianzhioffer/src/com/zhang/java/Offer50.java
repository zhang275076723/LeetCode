package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/3/30 10:41
 * @Author zsy
 * @Description 第一个只出现一次的字符
 * 在字符串 s 中找出第一个只出现一次的字符。
 * 如果没有，返回一个单空格。 s 只包含小写字母。
 * <p>
 * 输入：s = "abaccdeff"
 * 输出：'b'
 * <p>
 * 输入：s = ""
 * 输出：' '
 * <p>
 * 0 <= s 的长度 <= 50000
 */
public class Offer50 {
    public static void main(String[] args) {
        Offer50 offer50 = new Offer50();
        System.out.println(offer50.firstUniqChar("abaccdeff"));
        System.out.println(offer50.firstUniqChar2("abaccdeff"));
        System.out.println(offer50.firstUniqChar3("abaccdeff"));
    }

    /**
     * 计数数组
     * 时间复杂度O(n)，空间复杂度O(Σ)，Σ为字符a-z，共26个字符
     *
     * @param s
     * @return
     */
    public char firstUniqChar(String s) {
        //计数数组
        int[] count = new int[26];

        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < s.length(); i++) {
            if (1 == count[s.charAt(i) - 'a']) {
                return s.charAt(i);
            }
        }

        return ' ';
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(Σ)，Σ为字符a-z，共26个字符
     *
     * @param s
     * @return
     */
    public char firstUniqChar2(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }

        for (int i = 0; i < s.length(); i++) {
            if (1 == map.get(s.charAt(i))) {
                return s.charAt(i);
            }
        }

        return ' ';
    }

    /**
     * 哈希表优化
     * 在查找第一个只出现一次的字符时，不遍历字符串，而遍历哈希表，在字符串很长的情况下可以提升性能
     * 时间复杂度O(n)，空间复杂度O(Σ)，Σ为字符a-z，共26个字符
     *
     * @param s
     * @return
     */
    public char firstUniqChar3(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                //-1表示该字符出现多次
                map.put(s.charAt(i), -1);
            } else {
                //存放该字符第一次出现的下标索引
                map.put(s.charAt(i), i);
            }
        }

        int index = s.length();

        //从后往前遍历，找到第一个出现一次的索引下标
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() != -1 && entry.getValue() < index) {
                index = entry.getValue();
            }
        }

        return index == s.length() ? ' ' : s.charAt(index);
    }
}
