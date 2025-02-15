package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2025/4/4 08:48
 * @Author zsy
 * @Description 使字符串中不同字符的数目相等 类比Problem859、Problem1247、Problem1657、Problem1790
 * 给你两个下标从 0 开始的字符串 word1 和 word2 。
 * 一次 移动 由以下两个步骤组成：
 * 选中两个下标 i 和 j ，分别满足 0 <= i < word1.length 和 0 <= j < word2.length ，
 * 交换 word1[i] 和 word2[j] 。
 * 如果可以通过 恰好一次 移动，使 word1 和 word2 中不同字符的数目相等，则返回 true ；否则，返回 false 。
 * <p>
 * 输入：word1 = "ac", word2 = "b"
 * 输出：false
 * 解释：交换任何一组下标都会导致第一个字符串中有 2 个不同的字符，而在第二个字符串中只有 1 个不同字符。
 * <p>
 * 输入：word1 = "abcc", word2 = "aab"
 * 输出：true
 * 解释：交换第一个字符串的下标 2 和第二个字符串的下标 0 。之后得到 word1 = "abac" 和 word2 = "cab" ，各有 3 个不同字符。
 * <p>
 * 输入：word1 = "abcde", word2 = "fghij"
 * 输出：true
 * 解释：无论交换哪一组下标，两个字符串中都会有 5 个不同字符。
 * <p>
 * 1 <= word1.length, word2.length <= 10^5
 * word1 和 word2 仅由小写英文字母组成。
 */
public class Problem2531 {
    public static void main(String[] args) {
        Problem2531 problem2531 = new Problem2531();
        String word1 = "abcc";
        String word2 = "aab";
        System.out.println(problem2531.isItPossible(word1, word2));
    }

    /**
     * 哈希表
     * 统计word1和word2中字符出现的次数，判断word1和word2中字符交换后，word1和word2中不同字符的个数是否相等
     * 时间复杂度O(m+n+|Σ|^2)=O(m+n)，空间复杂度O(|Σ|)=O(1) (m=word1.length，n=word2.length()) (|Σ|=26，只包含小写字母)
     *
     * @param word1
     * @param word2
     * @return
     */
    public boolean isItPossible(String word1, String word2) {
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();

        for (char c : word1.toCharArray()) {
            map1.put(c, map1.getOrDefault(c, 0) + 1);
        }

        for (char c : word2.toCharArray()) {
            map2.put(c, map2.getOrDefault(c, 0) + 1);
        }

        //遍历word1和word2中字符
        for (Map.Entry<Character, Integer> entry1 : map1.entrySet()) {
            //word1中字符
            char c1 = entry1.getKey();
            //word1中字符c1出现的次数
            int count1 = entry1.getValue();

            for (Map.Entry<Character, Integer> entry2 : map2.entrySet()) {
                //word2中字符
                char c2 = entry2.getKey();
                //word2中字符c2出现的次数
                int count2 = entry2.getValue();

                //要交换的c1和c2相等
                if (c1 == c2) {
                    //交换后word1和word2中不同字符的个数相等，返回true
                    if (map1.size() == map2.size()) {
                        return true;
                    }
                } else {
                    //要交换的c1和c2不相等

                    //word1中不同字符的个数
                    int word1Count = map1.size();
                    //word2中不同字符的个数
                    int word2Count = map2.size();

                    //word1中字符c1出现的次数为1，则交换后word1中不同字符的个数减1
                    if (count1 == 1) {
                        word1Count--;
                    }

                    //word2中字符c2出现的次数为1，则交换后word2中不同字符的个数减1
                    if (count2 == 1) {
                        word2Count--;
                    }

                    //word1中不存在字符c2，则交换后word1中不同字符的个数加1
                    if (!map1.containsKey(c2)) {
                        word1Count++;
                    }

                    //word2中不存在字符c1，则交换后word2中不同字符的个数加1
                    if (!map2.containsKey(c1)) {
                        word2Count++;
                    }

                    //交换后word1和word2中不同字符的个数相等，返回true
                    if (word1Count == word2Count) {
                        return true;
                    }
                }
            }
        }

        //遍历结束，则交换后word1和word2中不同字符的个数不相等，返回false
        return false;
    }
}
