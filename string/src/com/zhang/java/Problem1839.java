package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/3/23 08:26
 * @Author zsy
 * @Description 所有元音按顺序排布的最长子字符串 元音类比Problem345、Problem824、Problem966、Problem1119、Problem1371、Problem1456、Problem1641、Problem1704、Problem2062、Problem2063、Problem2559、Problem2586、Problem2785 滑动窗口类比
 * 当一个字符串满足如下条件时，我们称它是 美丽的 ：
 * 所有 5 个英文元音字母（'a' ，'e' ，'i' ，'o' ，'u'）都必须 至少 出现一次。
 * 这些元音字母的顺序都必须按照 字典序 升序排布（也就是说所有的 'a' 都在 'e' 前面，所有的 'e' 都在 'i' 前面，以此类推）
 * 比方说，字符串 "aeiou" 和 "aaaaaaeiiiioou" 都是 美丽的 ，但是 "uaeio" ，"aeoiu" 和 "aaaeeeooo" 不是美丽的 。
 * 给你一个只包含英文元音字母的字符串 word ，请你返回 word 中 最长美丽子字符串的长度 。
 * 如果不存在这样的子字符串，请返回 0 。
 * 子字符串 是字符串中一个连续的字符序列。
 * <p>
 * 输入：word = "aeiaaioaaaaeiiiiouuuooaauuaeiu"
 * 输出：13
 * 解释：最长子字符串是 "aaaaeiiiiouuu" ，长度为 13 。
 * <p>
 * 输入：word = "aeeeiiiioooauuuaeiou"
 * 输出：5
 * 解释：最长子字符串是 "aeiou" ，长度为 5 。
 * <p>
 * 输入：word = "a"
 * 输出：0
 * 解释：没有美丽子字符串，所以返回 0 。
 * <p>
 * 1 <= word.length <= 5 * 10^5
 * word 只包含字符 'a'，'e'，'i'，'o' 和 'u' 。
 */
public class Problem1839 {
    public static void main(String[] args) {
        Problem1839 problem1839 = new Problem1839();
        String word = "aeiaaioaaaaeiiiiouuuooaauuaeiu";
        System.out.println(problem1839.longestBeautifulSubstring(word));
    }

    /**
     * 滑动窗口
     * 滑动窗口右边界的元音小于等于当前元音，则当前元音可以加入滑动窗口；滑动窗口右边界的元音大于当前元音，则滑动窗口左边界右移到当前元音
     * 如果滑动窗口中包含5个不同的元音，则滑动窗口中字符串为美丽字符串
     * 注意：word只包含小写英文元音字母
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param word
     * @return
     */
    public int longestBeautifulSubstring(String word) {
        //最长美丽字符串的长度
        int maxLen = 0;
        int left = 0;
        int right = 0;
        //滑动窗口中包含的不同元音集合
        Set<Character> vowelSet = new HashSet<>();

        while (right < word.length()) {
            //当前字符
            char c = word.charAt(right);

            //滑动窗口为空，或者滑动窗口右边界的元音小于等于当前元音，则当前元音可以加入滑动窗口
            if (left == right || word.charAt(right - 1) <= c) {
                vowelSet.add(c);

                //滑动窗口中包含5个不同的元音，则滑动窗口中字符串为美丽字符串
                if (vowelSet.size() == 5) {
                    maxLen = Math.max(maxLen, right - left + 1);
                }
            } else {
                //滑动窗口右边界的元音大于当前元音，则滑动窗口左边界右移到当前元音

                vowelSet.clear();
                vowelSet.add(word.charAt(right));
                left = right;
            }

            //右指针右移
            right++;
        }

        return maxLen;
    }
}
