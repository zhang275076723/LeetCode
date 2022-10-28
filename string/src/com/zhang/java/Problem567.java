package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/9/22 10:38
 * @Author zsy
 * @Description 字符串的排列 类比Problem3、Problem76、Problem438
 * 给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。
 * 如果是，返回 true ；否则，返回 false 。
 * 换句话说，s1 的排列之一是 s2 的 子串 。
 * <p>
 * 输入：s1 = "ab" s2 = "eidbaooo"
 * 输出：true
 * 解释：s2 包含 s1 的排列之一 ("ba").
 * <p>
 * 输入：s1= "ab" s2 = "eidboaoo"
 * 输出：false
 * <p>
 * 1 <= s1.length, s2.length <= 10^4
 * s1 和 s2 仅包含小写字母
 */
public class Problem567 {
    public static void main(String[] args) {
        Problem567 problem567 = new Problem567();
        String s1 = "ab";
        String s2 = "eidbaooo";
        System.out.println(problem567.checkInclusion(s1, s2));
    }

    /**
     * 滑动窗口，双指针
     * 时间复杂度O(s2.length()*|C|)，空间复杂度O(|C|) (字符串仅包含小写字母，所以|C|=26)
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        Map<Character, Integer> s1Map = new HashMap<>();
        Map<Character, Integer> s2Map = new HashMap<>();

        for (char c : s1.toCharArray()) {
            s1Map.put(c, s1Map.getOrDefault(c, 0) + 1);
        }

        int left = 0;
        int right = 0;

        while (right < s2.length()) {
            char c = s2.charAt(right);
            s2Map.put(c, s2Map.getOrDefault(c, 0) + 1);

            //判断当前字符串是否是s1的排列
            if (right - left + 1 == s1.length()) {
                if (isCover(s1Map, s2Map)) {
                    return true;
                }

                //左指针所指字符从s2Map中移除，左指针右移
                s2Map.put(s2.charAt(left), s2Map.get(s2.charAt(left)) - 1);
                left++;
            }

            right++;
        }

        return false;
    }

    /**
     * 判断s2Map是否能覆盖s1Map
     *
     * @param s1Map
     * @param s2Map
     * @return
     */
    private boolean isCover(Map<Character, Integer> s1Map, Map<Character, Integer> s2Map) {
        for (Map.Entry<Character, Integer> entry : s1Map.entrySet()) {
            //注意Integer之间比较要使用equals，如果范围在[-128,127]之内能比较正确，其他范围比较失败
            if (!s2Map.containsKey(entry.getKey()) || !s2Map.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }

        return true;
    }
}
