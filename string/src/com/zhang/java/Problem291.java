package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/12/4 08:25
 * @Author zsy
 * @Description 单词规律 II 哈希表类比Problem1、Problem187、Problem205、Problem242、Problem290、Problem383、Problem387、Problem389、Problem454、Problem554、Problem763、Problem1640、Offer50 回溯+剪枝类比
 * 给你一种规律 pattern 和一个字符串 s，请你判断 s 是否和 pattern 的规律相匹配。
 * 如果存在单个字符到 非空 字符串的 双射映射 ，那么字符串 s 匹配 pattern ，
 * 即：如果 pattern 中的每个字符都被它映射到的字符串替换，那么最终的字符串则为 s 。
 * 双射 意味着映射双方一一对应，不会存在两个字符映射到同一个字符串，也不会存在一个字符分别映射到两个不同的字符串。
 * <p>
 * 输入：pattern = "abab", s = "redblueredblue"
 * 输出：true
 * 解释：一种可能的映射如下：
 * 'a' -> "red"
 * 'b' -> "blue"
 * <p>
 * 输入：pattern = "aaaa", s = "asdasdasdasd"
 * 输出：true
 * 解释：一种可能的映射如下：
 * 'a' -> "asd"
 * <p>
 * 输入：pattern = "aabb", s = "xyzabcxzyabc"
 * 输出：false
 * <p>
 * 1 <= pattern.length, s.length <= 20
 * pattern 和 s 由小写英文字母组成
 */
public class Problem291 {
    public static void main(String[] args) {
        Problem291 problem291 = new Problem291();
        String pattern = "abab";
        String s = "redblueredblue";
        System.out.println(problem291.wordPatternMatch(pattern, s));
    }

    /**
     * 回溯+剪枝+哈希表
     * pattern中字符和s中单词的映射是双向一一对应才遵循相同的规律
     * 时间复杂度O(min(m,n)^n)，空间复杂度O(min(m,n)) (m=pattern.length()，n=s.length())
     *
     * @param pattern
     * @param s
     * @return
     */
    public boolean wordPatternMatch(String pattern, String s) {
        //pattern中字符到s中单词的映射map
        Map<Character, String> map1 = new HashMap<>();
        //s中单词到pattern中字符的映射map
        Map<String, Character> map2 = new HashMap<>();

        return backtrack(0, 0, pattern, s, map1, map2);
    }

    private boolean backtrack(int t1, int t2, String pattern, String s,
                              Map<Character, String> map1, Map<String, Character> map2) {
        if (t1 == pattern.length() && t2 == s.length()) {
            return true;
        }

        if (t1 == pattern.length() || t2 == s.length()) {
            return false;
        }

        char c = pattern.charAt(t1);

        for (int i = t2; i < s.length(); i++) {
            String word = s.substring(t2, i + 1);

            //不满足pattern中每个字符对应s中唯一单词，则进行下次循环，找s中下一个单词
            if (map1.containsKey(c) && !map1.get(c).equals(word)) {
                continue;
            }

            //不满足s中每个单词对应pattern中唯一字符，则进行下次循环，找s中下一个单词
            if (map2.containsKey(word) && map2.get(word) != c) {
                continue;
            }

            map1.put(c, word);
            map2.put(word, c);

            if (backtrack(t1 + 1, i + 1, pattern, s, map1, map2)) {
                return true;
            }

            map1.remove(c);
            map2.remove(word);
        }

        //遍历结束，没有找到pattern中字符和s中单词的映射是双向一一对应，返回false
        return false;
    }
}
