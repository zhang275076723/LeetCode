package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/3/30 10:41
 * @Author zsy
 * @Description 第一个只出现一次的字符 哈希表类比Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem554、Problem763、Problem1640 同Problem387
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
        String s = "abaccdeff";
        System.out.println(offer50.firstUniqChar(s));
        System.out.println(offer50.firstUniqChar2(s));
        System.out.println(offer50.firstUniqChar3(s));
    }

    /**
     * 计数数组
     * 时间复杂度O(n)，空间复杂度O(|Σ|) (|Σ| = 26，s只包含小写字母，共26个字符)
     *
     * @param s
     * @return
     */
    public char firstUniqChar(String s) {
        //计数数组
        int[] arr = new int[26];

        for (char c : s.toCharArray()) {
            arr[c - 'a']++;
        }

        for (char c : s.toCharArray()) {
            if (arr[c - 'a'] == 1) {
                return c;
            }
        }

        //返回空格表示没有找到
        return ' ';
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(|Σ|) (|Σ| = 26，s只包含小写字母，共26个字符)
     *
     * @param s
     * @return
     */
    public char firstUniqChar2(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (char c : s.toCharArray()) {
            //Integer之间的比较不能使用==，而应该使用equals()；Integer和int的比较，可以使用==，自动拆箱
            if (map.get(c) == 1) {
                return c;
            }
        }

        //返回空格表示没有找到
        return ' ';
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
    public char firstUniqChar3(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            //当前字符之前出现过，则不是只出现一次的字符，赋值为-1
            if (map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), -1);
            } else {
                //当前字符第一次出现，存放当前字符的下标索引
                map.put(s.charAt(i), i);
            }
        }

        int index = s.length();

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            //Integer之间的比较不能使用==，而应该使用equals()；Integer和int的比较，可以使用==，自动拆箱
            if (entry.getValue() != -1) {
                index = Math.min(index, entry.getValue());
            }
        }

        //遍历完map没有找到只出现一次的字符，则返回' '；找到，则返回只出现一次的字符
        return index == s.length() ? ' ' : s.charAt(index);
    }
}
