package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/11/17 09:07
 * @Author zsy
 * @Description 找不同 类比Problem136、Problem137、Problem260、Offer56、Offer56_2、Offer65
 * 给定两个字符串 s 和 t ，它们只包含小写字母。
 * 字符串 t 由字符串 s 随机重排，然后在随机位置添加一个字母。
 * 请找出在 t 中被添加的字母。
 * <p>
 * 输入：s = "abcd", t = "abcde"
 * 输出："e"
 * 解释：'e' 是那个被添加的字母。
 * <p>
 * 输入：s = "", t = "y"
 * 输出："y"
 * <p>
 * 0 <= s.length <= 1000
 * t.length == s.length + 1
 * s 和 t 只包含小写字母
 */
public class Problem389 {
    public static void main(String[] args) {
        Problem389 problem389 = new Problem389();
        String s = "abcd";
        String t = "abcde";
        System.out.println(problem389.findTheDifference(s, t));
        System.out.println(problem389.findTheDifference2(s, t));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(|Σ|) (|Σ| = 26，s只包含小写字母，共26个字符)
     *
     * @param s
     * @param t
     * @return
     */
    public char findTheDifference(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (char c : t.toCharArray()) {
            //map中没有字符c，或者map中字符c的个数为0，说明当前字符c就是t中被添加的字符
            if (!map.containsKey(c) || map.get(c).equals(0)) {
                return c;
            }

            //当前字符c的个数减1
            map.put(c, map.get(c) - 1);
        }

        return 0;
    }

    /**
     * 位运算
     * 不使用额外的空间，就要想到位运算
     * 将s和t中字符对应的ASCII码进行异或运算，得到的结果即为t中被添加的字符
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @param t
     * @return
     */
    public char findTheDifference2(String s, String t) {
        int result = 0;

        for (char c : s.toCharArray()) {
            result = result ^ c;
        }

        for (char c : t.toCharArray()) {
            result = result ^ c;
        }

        return (char) result;
    }
}
