package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/11/17 09:07
 * @Author zsy
 * @Description 找不同 哈希表类比Problem187、Problem242、Problem383、Problem387、Problem554、Problem763、Problem1640、Offer50 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt
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

        //遍历t，对t中每个字符判断是否存在于map中，如果不存在，即找到，如果存在，当前字符次数减1
        for (char c : t.toCharArray()) {
            //map中没有字符c，或者map中字符c的个数为0，说明当前字符c就是t中被添加的字符，即不同的字符
            if (!map.containsKey(c) || map.get(c) == 0) {
                return c;
            }

            //当前字符c的次数减1
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
            result = result ^ (int) c;
        }

        for (char c : t.toCharArray()) {
            result = result ^ (int) c;
        }

        //s和t中只存在一个字符只出现一次，其他字符都出现偶数次，异或之后得到只出现一次的字符
        return (char) result;
    }
}
