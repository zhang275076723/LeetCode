package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/3/28 08:55
 * @Author zsy
 * @Description 统计范围内的元音字符串数 元音类比Problem345、Problem824、Problem966、Problem1119、Problem1371、Problem1456、Problem1704、Problem1839、Problem2062、Problem2063、Problem2559、Problem2785
 * 给你一个下标从 0 开始的字符串数组 words 和两个整数：left 和 right 。
 * 如果字符串以元音字母开头并以元音字母结尾，那么该字符串就是一个 元音字符串 ，其中元音字母是 'a'、'e'、'i'、'o'、'u' 。
 * 返回 words[i] 是元音字符串的数目，其中 i 在闭区间 [left, right] 内。
 * <p>
 * 输入：words = ["are","amy","u"], left = 0, right = 2
 * 输出：2
 * 解释：
 * - "are" 是一个元音字符串，因为它以 'a' 开头并以 'e' 结尾。
 * - "amy" 不是元音字符串，因为它没有以元音字母结尾。
 * - "u" 是一个元音字符串，因为它以 'u' 开头并以 'u' 结尾。
 * 在上述范围中的元音字符串数目为 2 。
 * <p>
 * 输入：words = ["hey","aeo","mu","ooo","artro"], left = 1, right = 4
 * 输出：3
 * 解释：
 * - "aeo" 是一个元音字符串，因为它以 'a' 开头并以 'o' 结尾。
 * - "mu" 不是元音字符串，因为它没有以元音字母开头。
 * - "ooo" 是一个元音字符串，因为它以 'o' 开头并以 'o' 结尾。
 * - "artro" 是一个元音字符串，因为它以 'a' 开头并以 'o' 结尾。
 * 在上述范围中的元音字符串数目为 3 。
 * <p>
 * 1 <= words.length <= 1000
 * 1 <= words[i].length <= 10
 * words[i] 仅由小写英文字母组成
 * 0 <= left <= right < words.length
 */
public class Problem2586 {
    public static void main(String[] args) {
        Problem2586 problem2586 = new Problem2586();
        String[] words = {"hey", "aeo", "mu", "ooo", "artro"};
        int left = 1;
        int right = 4;
        System.out.println(problem2586.vowelStrings(words, left, right));
    }

    /**
     * 模拟
     * 时间复杂度O(right-left)，空间复杂度O(1)
     *
     * @param words
     * @param left
     * @param right
     * @return
     */
    public int vowelStrings(String[] words, int left, int right) {
        int count = 0;
        //5个小写元音字符的集合，不需要包含大写元音字母
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
        }};

        for (int i = left; i <= right; i++) {
            //words[i]的开头字符
            char c1 = words[i].charAt(0);
            //words[i]的结尾字符
            char c2 = words[i].charAt(words[i].length() - 1);

            if (vowelSet.contains(c1) && vowelSet.contains(c2)) {
                count++;
            }
        }

        return count;
    }
}
