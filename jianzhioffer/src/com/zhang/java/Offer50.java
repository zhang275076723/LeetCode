package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/3/30 10:41
 * @Author zsy
 * @Description 第一个只出现一次的字符 类比Problem387
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
     * 时间复杂度O(n)，空间复杂度O(Σ) (|Σ| = 26，s只包含小写字母，共26个字符)
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
     * 时间复杂度O(n)，空间复杂度O(Σ) (|Σ| = 26，s只包含小写字母，共26个字符)
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
            //Integer比较不能使用==，而应该使用equals()
            if (map.get(c).equals(1)) {
                return c;
            }
        }

        //返回空格表示没有找到
        return ' ';
    }

    /**
     * 哈希表优化
     * 在查找第一个只出现一次的字符时，不遍历字符串s，而最多只需要遍历26次哈希表，因为s只包含小写字母，在字符串很长的情况下可以提升性能
     * 时间复杂度O(n)，空间复杂度O(Σ) (|Σ| = 26，s只包含小写字母，共26个字符)
     *
     * @param s
     * @return
     */
    public char firstUniqChar3(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                //-1表示该字符出现多次，即不可能为只出现一次的字符
                map.put(s.charAt(i), -1);
            } else {
                //存放该字符第一次出现的下标索引
                map.put(s.charAt(i), i);
            }
        }

        int index = s.length();

        //遍历map而不是遍历字符串s，找最小的索引，即为第一个只出现一次的字符，在字符串s较长的情况下，遍历map最多只需要遍历26次
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            //Integer比较不能使用==，而应该使用equals()
            if (!entry.getValue().equals(-1)) {
                index = Math.min(index, entry.getValue());
            }
        }

        //返回空格表示没有找到
        return index == s.length() ? ' ' : s.charAt(index);
    }
}
