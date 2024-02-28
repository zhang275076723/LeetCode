package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/12/3 08:21
 * @Author zsy
 * @Description 单词规律 哈希表类比Problem1、Problem128、Problem166、Problem187、Problem205、Problem242、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem535、Problem554、Problem763、Problem1640、Offer50
 * 给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。
 * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 s 中的每个非空单词之间存在着双向连接的对应规律。
 * <p>
 * 输入: pattern = "abba", s = "dog cat cat dog"
 * 输出: true
 * <p>
 * 输入:pattern = "abba", s = "dog cat cat fish"
 * 输出: false
 * <p>
 * 输入: pattern = "aaaa", s = "dog cat cat dog"
 * 输出: false
 * <p>
 * 1 <= pattern.length <= 300
 * pattern 只包含小写英文字母
 * 1 <= s.length <= 3000
 * s 只包含小写英文字母和 ' '
 * s 不包含 任何前导或尾随对空格
 * s 中每个单词都被 单个空格 分隔
 */
public class Problem290 {
    public static void main(String[] args) {
        Problem290 problem290 = new Problem290();
//        String pattern = "abba";
//        String s = "dog cat cat dog";
        String pattern = "abba";
        String s = "dog dog dog dog";
        System.out.println(problem290.wordPattern(pattern, s));
    }

    /**
     * 哈希表
     * pattern中字符和s中单词的映射是双向一一对应才遵循相同的规律
     * 时间复杂度O(m+n)，空间复杂度O(m+n)
     *
     * @param pattern
     * @param s
     * @return
     */
    public boolean wordPattern(String pattern, String s) {
        //s中单词数组
        String[] wordArr = s.split(" ");

        if (pattern.length() != wordArr.length) {
            return false;
        }

        //pattern中字符到s中单词的映射map
        Map<Character, String> map1 = new HashMap<>();
        //s中单词到pattern中字符的映射map
        //注意：必须使用2个map保证幂等性，即保证pattern中字符和s中单词存在双向一一对应关系
        Map<String, Character> map2 = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String word = wordArr[i];

            //不满足pattern中每个字符对应s中唯一单词，则返回false
            if (map1.containsKey(c) && !map1.get(c).equals(word)) {
                return false;
            }

            //不满足s中每个单词对应pattern中唯一字符，则返回false
            if (map2.containsKey(word) && map2.get(word) != c) {
                return false;
            }

            //建立map1中c到word和map2中word到c的映射关系
            map1.put(c, word);
            map2.put(word, c);
        }

        //遍历结束，则映射都是一一对应，返回true
        return true;
    }
}
