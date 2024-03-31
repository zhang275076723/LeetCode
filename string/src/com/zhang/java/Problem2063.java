package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/3/26 08:20
 * @Author zsy
 * @Description 所有子字符串中的元音 类比Problem795 元音类比Problem345、Problem824、Problem966、Problem1119、Problem1371、Problem1456、Problem1641、Problem1704、Problem1839、Problem2062、Problem2559、Problem2586、Problem2785
 * 给你一个字符串 word ，返回 word 的所有子字符串中 元音的总数 ，元音是指 'a'、'e'、'i'、'o' 和 'u' 。
 * 子字符串 是字符串中一个连续（非空）的字符序列。
 * 注意：由于对 word 长度的限制比较宽松，答案可能超过有符号 32 位整数的范围。
 * 计算时需当心。
 * <p>
 * 输入：word = "aba"
 * 输出：6
 * 解释：
 * 所有子字符串是："a"、"ab"、"aba"、"b"、"ba" 和 "a" 。
 * - "b" 中有 0 个元音
 * - "a"、"ab"、"ba" 和 "a" 每个都有 1 个元音
 * - "aba" 中有 2 个元音
 * 因此，元音总数 = 0 + 1 + 1 + 1 + 1 + 2 = 6 。
 * <p>
 * 输入：word = "abc"
 * 输出：3
 * 解释：
 * 所有子字符串是："a"、"ab"、"abc"、"b"、"bc" 和 "c" 。
 * - "a"、"ab" 和 "abc" 每个都有 1 个元音
 * - "b"、"bc" 和 "c" 每个都有 0 个元音
 * 因此，元音总数 = 1 + 1 + 1 + 0 + 0 + 0 = 3 。
 * <p>
 * 输入：word = "ltcd"
 * 输出：0
 * 解释："ltcd" 的子字符串均不含元音。
 * <p>
 * 输入：word = "noosabasboosa"
 * 输出：237
 * 解释：所有子字符串中共有 237 个元音。
 * <p>
 * 1 <= word.length <= 10^5
 * word 由小写英文字母组成
 */
public class Problem2063 {
    public static void main(String[] args) {
        Problem2063 problem2063 = new Problem2063();
        String word = "noosabasboosa";
        System.out.println(problem2063.countVowels(word));
        System.out.println(problem2063.countVowels2(word));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param word
     * @return
     */
    public long countVowels(String word) {
        //word中所有子字符串中元音的总数
        long count = 0;
        //5个小写元音字符的集合，不需要包含大写元音字母
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
        }};

        for (int i = 0; i < word.length(); i++) {
            //word[i]-word[j]中元音的个数
            int curCount = 0;

            for (int j = i; j < word.length(); j++) {
                char c = word.charAt(j);

                if (vowelSet.contains(c)) {
                    curCount++;
                }

                count = count + curCount;
            }
        }

        return count;
    }

    /**
     * 模拟
     * 当前字符为元音字母，则所有包含当前字符的字符串都满足要求，共有(index-leftBound+1)*(rightBound-index+1)个字符串满足要求，
     * index为当前元音字母的下标索引，leftBound和rightBound为word的左右下标索引
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param word
     * @return
     */
    public long countVowels2(String word) {
        //word中所有子字符串中元音的总数
        long count = 0;
        //5个小写元音字符的集合，不需要包含大写元音字母
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
        }};

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            //当前字符c为元音字母，则所有包含当前字符的字符串都满足要求，共有(index-leftBound+1)*(rightBound-index+1)个字符串满足要求
            if (vowelSet.contains(c)) {
                count = count + (long) (i + 1) * (word.length() - i);
            }
        }

        return count;
    }
}
