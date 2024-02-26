package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/12/2 08:43
 * @Author zsy
 * @Description 同构字符串 哈希表类比Problem1、Problem187、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem535、Problem554、Problem763、Problem1640、Offer50
 * 给定两个字符串 s 和 t ，判断它们是否是同构的。
 * 如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。
 * 每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。
 * 不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。
 * <p>
 * 输入：s = "egg", t = "add"
 * 输出：true
 * <p>
 * 输入：s = "foo", t = "bar"
 * 输出：false
 * <p>
 * 输入：s = "paper", t = "title"
 * 输出：true
 * <p>
 * 1 <= s.length <= 5 * 10^4
 * t.length == s.length
 * s 和 t 由任意有效的 ASCII 字符组成
 */
public class Problem205 {
    public static void main(String[] args) {
        Problem205 problem205 = new Problem205();
        String s = "bbbaaac";
        String t = "aaabbba";
        System.out.println(problem205.isIsomorphic(s, t));
    }

    /**
     * 哈希表
     * s中字符和t中字符的映射是双向一一对应才是同构
     * 时间复杂度O(n)，空间复杂度O(|Σ|) (|Σ|=128，ascii码)
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        //s中字符到t中字符的映射map
        Map<Character, Character> map1 = new HashMap<>();
        //t中字符到s中字符的映射map
        //注意：必须使用2个map保证幂等性，即保证s中字符和t中字符存在双向一一对应关系，
        //如果只使用1个map，假设s中字符'a'映射到t中字符'b'，同时也有s中字符'c'映射到t中字符'b'，不满足不同字符不能映射到同一个字符
        Map<Character, Character> map2 = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);

            //不满足相同字符只能映射到同一个字符上，则不是同构，返回false
            //即不能出现s中'a'映射到t中'b'，同时s中'a'映射到t中'c'的情况
            if (map1.containsKey(c1) && map1.get(c1) != c2) {
                return false;
            }

            //不满足不同字符不能映射到同一个字符上，则不是同构，返回false
            //即不能出现s中'b'映射到t中'a'，同时s中'c'映射到t中'a'的情况
            if (map2.containsKey(c2) && map2.get(c2) != c1) {
                return false;
            }

            //建立map1中c1到c2和map2中c2到c1的映射关系
            map1.put(c1, c2);
            map2.put(c2, c1);
        }

        //遍历结束，则映射都是一一对应，返回true
        return true;
    }
}
