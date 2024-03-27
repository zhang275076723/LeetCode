package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/3/24 08:20
 * @Author zsy
 * @Description 判断字符串的两半是否相似 元音类比Problem345、Problem824、Problem966、Problem1119、Problem1371、Problem1456、Problem1839
 * 给你一个偶数长度的字符串 s 。
 * 将其拆分成长度相同的两半，前一半为 a ，后一半为 b 。
 * 两个字符串 相似 的前提是它们都含有相同数目的元音（'a'，'e'，'i'，'o'，'u'，'A'，'E'，'I'，'O'，'U'）。
 * 注意，s 可能同时含有大写和小写字母。
 * 如果 a 和 b 相似，返回 true ；否则，返回 false 。
 * <p>
 * 输入：s = "book"
 * 输出：true
 * 解释：a = "bo" 且 b = "ok" 。a 中有 1 个元音，b 也有 1 个元音。所以，a 和 b 相似。
 * <p>
 * 输入：s = "textbook"
 * 输出：false
 * 解释：a = "text" 且 b = "book" 。a 中有 1 个元音，b 中有 2 个元音。因此，a 和 b 不相似。
 * 注意，元音 o 在 b 中出现两次，记为 2 个。
 * <p>
 * 2 <= s.length <= 1000
 * s.length 是偶数
 * s 由 大写和小写 字母组成
 */
public class Problem1704 {
    public static void main(String[] args) {
        Problem1704 problem1704 = new Problem1704();
        String s = "textbook";
        System.out.println(problem1704.halvesAreAlike(s));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public boolean halvesAreAlike(String s) {
        //大写和小写共10个元音字符的集合
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
            add('A');
            add('E');
            add('I');
            add('O');
            add('U');
        }};

        //前半部分元音个数
        int count1 = 0;
        //后半部分元音个数
        int count2 = 0;

        for (int i = 0; i < s.length() / 2; i++) {
            char c = s.charAt(i);

            if (vowelSet.contains(c)) {
                count1++;
            }
        }

        for (int i = s.length() / 2; i < s.length(); i++) {
            char c = s.charAt(i);

            if (vowelSet.contains(c)) {
                count2++;
            }
        }

        return count1 == count2;
    }
}
