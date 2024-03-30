package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/3/22 08:36
 * @Author zsy
 * @Description 定长子串中元音的最大数目 元音类比Problem345、Problem824、Problem966、Problem1119、Problem1371、Problem1704、Problem1839、Problem2062、Problem2063、Problem2559、Problem2586 滑动窗口类比
 * 给你字符串 s 和整数 k 。
 * 请返回字符串 s 中长度为 k 的单个子字符串中可能包含的最大元音字母数。
 * 英文中的 元音字母 为（a, e, i, o, u）。
 * <p>
 * 输入：s = "abciiidef", k = 3
 * 输出：3
 * 解释：子字符串 "iii" 包含 3 个元音字母。
 * <p>
 * 输入：s = "aeiou", k = 2
 * 输出：2
 * 解释：任意长度为 2 的子字符串都包含 2 个元音字母。
 * <p>
 * 输入：s = "leetcode", k = 3
 * 输出：2
 * 解释："lee"、"eet" 和 "ode" 都包含 2 个元音字母。
 * <p>
 * 输入：s = "rhythms", k = 4
 * 输出：0
 * 解释：字符串 s 中不含任何元音字母。
 * <p>
 * 输入：s = "tryhard", k = 4
 * 输出：1
 * <p>
 * 1 <= s.length <= 10^5
 * s 由小写英文字母组成
 * 1 <= k <= s.length
 */
public class Problem1456 {
    public static void main(String[] args) {
        Problem1456 problem1456 = new Problem1456();
        String s = "leetcode";
        int k = 3;
        System.out.println(problem1456.maxVowels(s, k));
    }

    /**
     * 滑动窗口
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @param k
     * @return
     */
    public int maxVowels(String s, int k) {
        //s中长度为k的字符串中最多元音字母的个数
        int maxCount = 0;
        //滑动窗口中元音字母的个数
        int curCount = 0;
        int left = 0;
        int right = 0;

        //5个小写元音字符的集合，不需要包含大写元音字母
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
        }};

        while (right < s.length()) {
            char c = s.charAt(right);

            //更新maxCount
            if (vowelSet.contains(c)) {
                curCount++;
                maxCount = Math.max(maxCount, curCount);
            }

            //窗口大小等于k时，左指针右移
            if (right - left + 1 == k) {
                if (vowelSet.contains(s.charAt(left)) && curCount > 0) {
                    curCount--;
                }

                left++;
            }

            //右指针右移
            right++;
        }

        return maxCount;
    }
}
