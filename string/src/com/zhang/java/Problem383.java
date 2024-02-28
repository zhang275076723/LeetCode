package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/7/8 08:40
 * @Author zsy
 * @Description 赎金信 哈希表类比Problem1、Problem128、Problem166、Problem187、Problem205、Problem242、Problem290、Problem291、Problem387、Problem389、Problem454、Problem532、Problem535、Problem554、Problem763、Problem1640、Offer50
 * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
 * 如果可以，返回 true ；否则返回 false 。
 * magazine 中的每个字符只能在 ransomNote 中使用一次。
 * <p>
 * 输入：ransomNote = "a", magazine = "b"
 * 输出：false
 * <p>
 * 输入：ransomNote = "aa", magazine = "ab"
 * 输出：false
 * <p>
 * 输入：ransomNote = "aa", magazine = "aab"
 * 输出：true
 * <p>
 * 1 <= ransomNote.length, magazine.length <= 10^5
 * ransomNote 和 magazine 由小写英文字母组成
 */
public class Problem383 {
    public static void main(String[] args) {
        Problem383 problem383 = new Problem383();
        String ransomNote = "aa";
        String magazine = "aab";
        System.out.println(problem383.canConstruct(ransomNote, magazine));
    }

    /**
     * 哈希表
     * 时间复杂度O(m+n)，空间复杂度O(|Σ|) (|Σ| = 26，s只包含小写字母，共26个字符)
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();

        for (char c : magazine.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (char c : ransomNote.toCharArray()) {
            if (!map.containsKey(c) || map.get(c) == 0) {
                return false;
            } else {
                map.put(c, map.get(c) - 1);
            }
        }

        return true;
    }
}
