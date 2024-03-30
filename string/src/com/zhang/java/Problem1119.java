package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2023/12/27 08:44
 * @Author zsy
 * @Description 删去字符串中的元音 元音类比Problem345、Problem824、Problem966、Problem1371、Problem1456、Problem1704、Problem1839、Problem2062
 * 给你一个字符串 s ，请你删去其中的所有元音字母 'a'，'e'，'i'，'o'，'u'，并返回这个新字符串。
 * <p>
 * 输入：s = "leetcodeisacommunityforcoders"
 * 输出："ltcdscmmntyfrcdrs"
 * <p>
 * 输入：s = "aeiou"
 * 输出：""
 * <p>
 * 1 <= S.length <= 1000
 * s 仅由小写英文字母组成
 */
public class Problem1119 {
    public static void main(String[] args) {
        Problem1119 problem1119 = new Problem1119();
        String s = "leetcodeisacommunityforcoders";
        System.out.println(problem1119.removeVowels(s));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String removeVowels(String s) {
        //5个小写元音字符的集合，不需要包含大写元音字母
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
        }};

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (!vowelSet.contains(c)) {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
