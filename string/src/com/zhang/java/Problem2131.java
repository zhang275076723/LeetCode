package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/4/7 09:05
 * @Author zsy
 * @Description 连接两字母单词得到的最长回文串 类比Problem266、Problem267、Problem409、Problem2384、Problem3035 回文类比
 * 给你一个字符串数组 words 。
 * words 中每个元素都是一个包含 两个 小写英文字母的单词。
 * 请你从 words 中选择一些元素并按 任意顺序 连接它们，并得到一个 尽可能长的回文串 。
 * 每个元素 至多 只能使用一次。
 * 请你返回你能得到的最长回文串的 长度 。
 * 如果没办法得到任何一个回文串，请你返回 0 。
 * 回文串 指的是从前往后和从后往前读一样的字符串。
 * <p>
 * 输入：words = ["lc","cl","gg"]
 * 输出：6
 * 解释：一个最长的回文串为 "lc" + "gg" + "cl" = "lcggcl" ，长度为 6 。
 * "clgglc" 是另一个可以得到的最长回文串。
 * <p>
 * 输入：words = ["ab","ty","yt","lc","cl","ab"]
 * 输出：8
 * 解释：最长回文串是 "ty" + "lc" + "cl" + "yt" = "tylcclyt" ，长度为 8 。
 * "lcyttycl" 是另一个可以得到的最长回文串。
 * <p>
 * 输入：words = ["cc","ll","xx"]
 * 输出：2
 * 解释：最长回文串是 "cc" ，长度为 2 。
 * "ll" 是另一个可以得到的最长回文串。"xx" 也是。
 * <p>
 * 1 <= words.length <= 10^5
 * words[i].length == 2
 * words[i] 仅包含小写英文字母。
 */
public class Problem2131 {
    public static void main(String[] args) {
        Problem2131 problem2131 = new Problem2131();
        String[] words = {"ab", "ty", "yt", "lc", "cl", "ab"};
        System.out.println(problem2131.longestPalindrome(words));
    }

    /**
     * 哈希表
     * 统计words[i]出现的次数，统计完之后，如果words[i]等于words[i]的逆序，出现次数为偶数的words[i]都可以拼接最长回文串，
     * 出现次数为奇数的words[i]可以拼接奇数减1个words[i]，剩余的1个words[i]放在回文串的中间；
     * 如果words[i]不等于words[i]的逆序，并且words[i]和words[i]的逆序都存在，则取两者出现次数较小值拼接最长回文串
     * 时间复杂度O(n)，空间复杂度O(min(n,|Σ|^2)) (n=words.length，|Σ|=26，只包含小写字母)
     *
     * @param words
     * @return
     */
    public int longestPalindrome(String[] words) {
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < words.length; i++) {
            map.put(words[i], map.getOrDefault(words[i], 0) + 1);
        }

        //最长回文串的长度
        int maxLen = 0;
        //出现次数为奇数的回文words[i]的标志位
        boolean oddFlag = false;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String word = entry.getKey();
            //word出现的次数
            int count = entry.getValue();

            //words[i]等于words[i]的逆序，出现次数为偶数的words[i]都可以拼接最长回文串，
            //出现次数为奇数的words[i]可以拼接奇数减1个words[i]，剩余的words[i]放在回文串的中间；
            //每个单词只有2位，则可以通过word[0]和word[1]进行判断
            if (word.charAt(0) == word.charAt(1)) {
                if (count % 2 == 0) {
                    maxLen = maxLen + 2 * count;
                } else {
                    maxLen = maxLen + 2 * (count - 1);
                    oddFlag = true;
                }
            } else {
                //words[i]不等于words[i]的逆序，并且words[i]和words[i]的逆序都存在，则取两者出现次数较小值拼接最长回文串
                //每个单词只有2位，则可以通过word[0]和word[1]进行判断
                String reverseWord = "" + word.charAt(1) + word.charAt(0);

                if (map.containsKey(reverseWord)) {
                    maxLen = maxLen + 2 * Math.min(count, map.get(reverseWord));
                }
            }
        }

        //存在出现次数为奇数的回文words[i]，剩余的1个words[i]放在回文串的中间
        if (oddFlag) {
            maxLen = maxLen + 2;
        }

        return maxLen;
    }
}
