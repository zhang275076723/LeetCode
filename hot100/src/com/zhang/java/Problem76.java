package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/4/26 9:27
 * @Author zsy
 * @Description 最小覆盖子串 字节面试题 滑动窗口类比Problem3、Problem30、Problem209、Problem219、Problem220、Problem239、Problem438、Problem485、Problem487、Problem567、Problem1004、Offer48、Offer57_2、Offer59
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。
 * 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
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
     * 滑动窗口，双指针
     * 当left和right形成的窗口不能覆盖t时，right右移；当left和right形成的窗口覆盖t时，left左移
     * 时间复杂度O(C*s.length())，空间复杂度O(C)，C为字符集大小，即|C|=26
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }

        //sMap中存放s中字符和对应的个数
        Map<Character, Integer> sMap = new HashMap<>();
        //tMap中存放t中字符的对应的个数
        Map<Character, Integer> tMap = new HashMap<>();

        for (char c : t.toCharArray()) {
            tMap.put(c, tMap.getOrDefault(c, 0) + 1);
        }

        //滑动窗口左右指针
        int left = 0;
        int right = 0;
        //最小窗口指针
        int minLeft = -1;
        //初始化最小最右指针口最大
        int minRight = s.length();

        while (right < s.length()) {
            char c = s.charAt(right);
            //sMap中添加右指针元素
            sMap.put(c, sMap.getOrDefault(c, 0) + 1);

            //当前窗口的大小大于等于t的大小，并且当前窗口覆盖t时，left指针右移，并更新sMap和最小窗口
            while (right - left + 1 >= t.length() && isCover(sMap, tMap)) {
                //更新最小覆盖子串的窗口大小
                if (right - left < minRight - minLeft) {
                    minLeft = left;
                    minRight = right;
                }

                //sMap中左指针元素个数减1
                sMap.put(s.charAt(left), sMap.get(s.charAt(left)) - 1);
                //left指针右移
                left++;
            }

            //right指针右移
            right++;
        }

        return minLeft == -1 ? "" : s.substring(minLeft, minRight + 1);
    }

    /**
     * sMap中存在的字符能否覆盖tMap中存在的字符
     * 时间复杂度O(C)，空间复杂度O(1) (C为字符集大小，即|C|=26)
     *
     * @param sMap
     * @param tMap
     * @return
     */
    private boolean isCover(Map<Character, Integer> sMap, Map<Character, Integer> tMap) {
        for (Map.Entry<Character, Integer> entry : tMap.entrySet()) {
            //sMap中没有tMap中的key，或者sMap中有tMap中的key，但value小于tMap中的value，则说明当前窗口不能覆盖t
            //Integer和Integer之间比较只能使用equals()，不能使用==，==比较的是地址是否相等
            if (!sMap.containsKey(entry.getKey()) || sMap.get(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }

        return true;
    }
}
