package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2025/4/7 08:44
 * @Author zsy
 * @Description 制造字母异位词的最小步骤数 字母异位词类比Problem49、Problem242、Problem438
 * 给你两个长度相等的字符串 s 和 t。
 * 每一个步骤中，你可以选择将 t 中的 任一字符 替换为 另一个字符。
 * 返回使 t 成为 s 的字母异位词的最小步骤数。
 * 字母异位词 指字母相同，但排列不同（也可能相同）的字符串。
 * <p>
 * 输出：s = "bab", t = "aba"
 * 输出：1
 * 提示：用 'b' 替换 t 中的第一个 'a'，t = "bba" 是 s 的一个字母异位词。
 * <p>
 * 输出：s = "leetcode", t = "practice"
 * 输出：5
 * 提示：用合适的字符替换 t 中的 'p', 'r', 'a', 'i' 和 'c'，使 t 变成 s 的字母异位词。
 * <p>
 * 输出：s = "anagram", t = "mangaar"
 * 输出：0
 * 提示："anagram" 和 "mangaar" 本身就是一组字母异位词。
 * <p>
 * 输出：s = "xxyyzz", t = "xxyyzz"
 * 输出：0
 * <p>
 * 输出：s = "friend", t = "family"
 * 输出：4
 * <p>
 * 1 <= s.length <= 50000
 * s.length == t.length
 * s 和 t 只包含小写英文字母
 */
public class Problem1347 {
    public static void main(String[] args) {
        Problem1347 problem1347 = new Problem1347();
        String s = "anagram";
        String t = "mangaar";
        System.out.println(problem1347.minSteps(s, t));
        System.out.println(problem1347.minSteps2(s, t));
    }

    /**
     * 哈希表 (2个map)
     * map分别存储和t中字符出现的次数
     * 遍历tMap中字符，如果当前字符在t中出现的次数大于在s中出现的次数，则需要当前字符出现的次数差值次替换
     * 时间复杂度O(n)，空间复杂度O(|Σ|)=O(1) (|Σ|=26，只包含小写字母)
     *
     * @param s
     * @param t
     * @return
     */
    public int minSteps(String s, String t) {
        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> tMap = new HashMap<>();

        for (char c : s.toCharArray()) {
            sMap.put(c, sMap.getOrDefault(c, 0) + 1);
        }

        for (char c : t.toCharArray()) {
            tMap.put(c, tMap.getOrDefault(c, 0) + 1);
        }

        int count = 0;

        for (Map.Entry<Character, Integer> entry : tMap.entrySet()) {
            char c = entry.getKey();

            //当前当前字符在t中出现的次数大于在s中出现的次数，则需要当前字符出现的次数差值次替换
            if (entry.getValue() > sMap.getOrDefault(c, 0)) {
                count = count + entry.getValue() - sMap.getOrDefault(c, 0);
            }
        }

        return count;
    }

    /**
     * 哈希表 (1个map)
     * map存储t中字符出现的次数减去s中字符出现的次数
     * 遍历map中字符，如果当前字符出现的次数差值大于0，则需要当前字符出现的次数差值次替换
     * 时间复杂度O(n)，空间复杂度O(|Σ|)=O(1) (|Σ|=26，只包含小写字母)
     *
     * @param s
     * @param t
     * @return
     */
    public int minSteps2(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();

        //s中字符出现的次数减1，t中字符出现的次数加1
        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);

            map.put(c1, map.getOrDefault(c1, 0) - 1);
            map.put(c2, map.getOrDefault(c2, 0) + 1);
        }

        int count = 0;

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            //当前字符出现的次数差值大于0，则当前字符在s中出现的次数小于在t中出现的次数，则需要当前字符出现的次数差值次替换
            //当前字符出现的次数差值等于0，则当前字符在s和t中出现的次数相等，s中当前字符不需要替换
            //当前字符出现的次数差值小于0，则当前字符在s中出现的次数大于在t中出现的次数，或者当前字符在t中不存在，s中当前字符不需要替换
            if (entry.getValue() > 0) {
                count = count + entry.getValue();
            }
        }

        return count;
    }
}
