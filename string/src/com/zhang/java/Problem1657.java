package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2025/4/2 08:07
 * @Author zsy
 * @Description 确定两个字符串是否接近 类比Problem859、Problem1247、Problem1790、Problem2531
 * 如果可以使用以下操作从一个字符串得到另一个字符串，则认为两个字符串 接近 ：
 * 操作 1：交换任意两个 现有 字符。
 * 例如，abcde -> aecdb
 * 操作 2：将一个 现有 字符的每次出现转换为另一个 现有 字符，并对另一个字符执行相同的操作。
 * 例如，aacabb -> bbcbaa（所有 a 转化为 b ，而所有的 b 转换为 a ）
 * 你可以根据需要对任意一个字符串多次使用这两种操作。
 * 给你两个字符串，word1 和 word2 。如果 word1 和 word2 接近 ，就返回 true ；否则，返回 false 。
 * <p>
 * 输入：word1 = "abc", word2 = "bca"
 * 输出：true
 * 解释：2 次操作从 word1 获得 word2 。
 * 执行操作 1："abc" -> "acb"
 * 执行操作 1："acb" -> "bca"
 * <p>
 * 输入：word1 = "a", word2 = "aa"
 * 输出：false
 * 解释：不管执行多少次操作，都无法从 word1 得到 word2 ，反之亦然。
 * <p>
 * 输入：word1 = "cabbba", word2 = "abbccc"
 * 输出：true
 * 解释：3 次操作从 word1 获得 word2 。
 * 执行操作 1："cabbba" -> "caabbb"
 * 执行操作 2："caabbb" -> "baaccc"
 * 执行操作 2："baaccc" -> "abbccc"
 * <p>
 * 1 <= word1.length, word2.length <= 10^5
 * word1 和 word2 仅包含小写英文字母
 */
public class Problem1657 {
    public static void main(String[] args) {
        Problem1657 problem1657 = new Problem1657();
        String word1 = "cabbba";
        String word2 = "abbccc";
        System.out.println(problem1657.closeStrings(word1, word2));
    }

    /**
     * 模拟
     * word1和word2接近：word1和word2长度相等，字符种类相同，字符种类出现的次数排序后相同
     * 时间复杂度O(m+n+|Σ|log|Σ|)=O(m+n)，空间复杂度O(|Σ|)=O(1) (|Σ|=26，只包含小写字母)
     *
     * @param word1
     * @param word2
     * @return
     */
    public boolean closeStrings(String word1, String word2) {
        //word1和word2长度不相等，则不接近，返回false
        if (word1.length() != word2.length()) {
            return false;
        }

        //word1中字符出现的次数数组
        int[] count1 = new int[26];
        //word2中字符出现的次数数组
        int[] count2 = new int[26];

        for (char c : word1.toCharArray()) {
            count1[c - 'a']++;
        }

        for (char c : word2.toCharArray()) {
            count2[c - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            //字符种类不相同，则不接近，返回false
            if ((count1[i] != 0 && count2[i] == 0) || (count1[i] == 0 && count2[i] != 0)) {
                return false;
            }
        }

        Arrays.sort(count1);
        Arrays.sort(count2);

        for (int i = 0; i < 26; i++) {
            //字符种类出现的次数排序后不相同，则不接近，返回false
            if (count1[i] != count2[i]) {
                return false;
            }
        }

        //遍历结束，则接近，返回true
        return true;
    }
}
