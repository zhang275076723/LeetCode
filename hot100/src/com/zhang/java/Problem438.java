package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/8 8:24
 * @Author zsy
 * @Description 找到字符串中所有字母异位词 字母异位词类比Problem49、Problem242、Problem1347 滑动窗口类比Problem3、Problem30、Problem76、Problem209、Problem219、Problem220、Problem239、Problem340、Problem485、Problem487、Problem567、Problem632、Problem643、Problem713、Problem1004、Problem1456、Problem1839、Problem2062、Offer48、Offer57_2、Offer59
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
 * <p>
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 * <p>
 * 输入: s = "abab", p = "ab"
 * 输出: [0,1,2]
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
 * <p>
 * 1 <= s.length, p.length <= 3 * 10^4
 * s 和 p 仅包含小写字母
 */
public class Problem438 {
    public static void main(String[] args) {
        Problem438 problem438 = new Problem438();
        String s = "cbaebabacd";
        String p = "abc";
        System.out.println(problem438.findAnagrams(s, p));
        System.out.println(problem438.findAnagrams2(s, p));
    }

    /**
     * 滑动窗口，双指针 (2个map)
     * map分别存储s滑动窗口和p中字符出现的次数
     * 时间复杂度O(p.length()+s.length()*|C|)，空间复杂度O(|C|) (字符串仅包含小写字母，所以|C|=26)
     *
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams(String s, String p) {
        if (s.length() < p.length()) {
            return new ArrayList<>();
        }

        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> pMap = new HashMap<>();

        for (char c : p.toCharArray()) {
            pMap.put(c, pMap.getOrDefault(c, 0) + 1);
        }

        List<Integer> list = new ArrayList<>();
        int left = 0;
        int right = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            sMap.put(c, sMap.getOrDefault(c, 0) + 1);

            //当前窗口大小和p的长度相同时，才比较是否是p的异位词，并且left指针右移
            if (right - left + 1 == p.length()) {
                if (isEqual(sMap, pMap)) {
                    list.add(left);
                }

                //左指针所指字符从sMap删除
                sMap.put(s.charAt(left), sMap.get(s.charAt(left)) - 1);
                //左指针右移
                left++;
            }

            right++;
        }

        return list;
    }

    /**
     * 滑动窗口，双指针 (1个map)
     * map存储s滑动窗口和p中字符出现的次数之差diff，当diff等于0时，当前滑动窗口表示的字符串，即是p的一个异位词
     * 时间复杂度O(s.length()+p.length())，空间复杂度O(|C|) (字符串仅包含小写字母，所以|C|=26)
     *
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams2(String s, String p) {
        if (s.length() < p.length()) {
            return new ArrayList<>();
        }

        //s滑动窗口和p中字符出现次数之差
        int diff = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (char c : p.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
            diff++;
        }

        List<Integer> list = new ArrayList<>();
        int left = 0;
        int right = 0;

        while (right < s.length()) {
            char c = s.charAt(right);

            //当前字符c不在map中，或当前字符c在map中，但出现次数小于等于0，diff加1
            if (!map.containsKey(c) || map.get(c) <= 0) {
                map.put(c, map.getOrDefault(c, 0) - 1);
                diff++;
            } else {
                //当前字符c在map中，diff减1
                map.put(c, map.get(c) - 1);
                diff--;
            }

            //当前窗口大小和p的长度相同时，才看diff是否为0，是否是p的异位词，并且left指针右移
            if (right - left + 1 == p.length()) {
                //diff等于0，当前滑动窗口即是p的一个异位词
                if (diff == 0) {
                    list.add(left);
                }

                char leftChar = s.charAt(left);

                //滑动窗口左指针所指字符出现次数小于0，diff减1
                if (map.get(leftChar) < 0) {
                    map.put(leftChar, map.get(leftChar) + 1);
                    diff--;
                } else {
                    //滑动窗口左指针所指字符出现次数大于等于0，diff加1
                    map.put(leftChar, map.get(leftChar) + 1);
                    diff++;
                }

                left++;
            }

            right++;
        }

        return list;
    }

    /**
     * sMap中存在的字符及出现次数是否和pMap中存在的字符及出现次数相同
     * 时间复杂度O(C)，空间复杂度O(1) (C为字符集大小，即|C|=26)
     *
     * @param sMap
     * @param pMap
     * @return
     */
    private boolean isEqual(Map<Character, Integer> sMap, Map<Character, Integer> pMap) {
        //只能遍历pMap中的Entry，因为sMap中有可能存在key，但对应value为0的情况
        for (Map.Entry<Character, Integer> entry : pMap.entrySet()) {
            //Integer和Integer之间比较会自动装箱，只能使用equals()，不能使用==，==比较的是地址是否相等
            if (!sMap.containsKey(entry.getKey()) || !sMap.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }

        return true;
    }
}
