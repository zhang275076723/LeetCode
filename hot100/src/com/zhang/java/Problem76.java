package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/4/26 9:27
 * @Author zsy
 * @Description 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。
 * 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 * <p>
 * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
 * <p>
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 * <p>
 * 输入：s = "a", t = "a"
 * 输出："a"
 * <p>
 * 输入: s = "a", t = "aa"
 * 输出: ""
 * 解释: t 中两个字符 'a' 均应包含在 s 的子串中，
 * 因此没有符合条件的子字符串，返回空字符串。
 * <p>
 * 1 <= s.length, t.length <= 10^5
 * s 和 t 由英文字母组成
 */
public class Problem76 {
    public static void main(String[] args) {
        Problem76 problem76 = new Problem76();
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println(problem76.minWindow(s, t));
    }

    /**
     * 滑动窗口，两个指针left和right分别指向满足要求的数组索引
     * 当left和right形成的窗口不能覆盖t时，right右移；当left和right形成的窗口覆盖t时，left左移
     * 时间复杂度O(C*s.length())，空间复杂度O(C)，C为字符集大小
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }

        //tMap中存放t中字符和对应的个数
        Map<Character, Integer> tMap = new HashMap<>();
        //sMap中存放s中字符和对应的个数
        Map<Character, Integer> sMap = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            tMap.put(t.charAt(i), tMap.getOrDefault(t.charAt(i), 0) + 1);
        }

        //滑动窗口指针
        int left = 0;
        int right = -1;
        //最小窗口指针
        int minLeft = 0;
        int minRight = Integer.MAX_VALUE;
        //窗口大小
        int size = 0;
        while (right < s.length()) {
            //右指针右移
            right++;

            //如果tMap中是否包含右指针，则在sMap中添加
            if (right < s.length() && tMap.containsKey(s.charAt(right))) {
                sMap.put(s.charAt(right), sMap.getOrDefault(s.charAt(right), 0) + 1);
            }

            //判断当前窗口是否覆盖t，左指针右移
            while (isCover(sMap, tMap) && left <= right) {
                if (right - left < minRight - minLeft) {
                    minLeft = left;
                    minRight = right;
                    size = minRight - minLeft + 1;
                }
                //如果sMap中包含左指针，则在sMap中减1
                if (sMap.containsKey(s.charAt(left))) {
                    sMap.put(s.charAt(left), sMap.get(s.charAt(left)) - 1);
                }
                //左指针右移
                left++;
            }
        }

        return size == 0 ? "" : s.substring(minLeft, minRight + 1);
    }

    private boolean isCover(Map<Character, Integer> sMap, Map<Character, Integer> tMap) {
        for (Map.Entry<Character, Integer> entry : tMap.entrySet()) {
            //sMap中没有tMap中的key，或者sMap中有tMap中的key，但value小于tMap中的value
            if (!sMap.containsKey(entry.getKey()) || sMap.get(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
}
