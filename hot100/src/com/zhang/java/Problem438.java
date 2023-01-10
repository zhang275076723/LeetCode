package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/8 8:24
 * @Author zsy
 * @Description 找到字符串中所有字母异位词 类比Problem49、Problem242 滑动窗口类比Problem3、Problem76、Problem209、Problem567、Offer48、Offer57_2、Offer59
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
     * 滑动窗口，双指针
     * 时间复杂度O(s.length*|C|)，空间复杂度O(|C|) (字符串仅包含小写字母，所以|C|=26)
     *
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams(String s, String p) {
        if (p.length() > s.length()) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        //存放s中每个字母出现的次数
        int[] sArr = new int[26];
        //存放p中每个字母出现的次数
        int[] pArr = new int[26];

        for (char c : p.toCharArray()) {
            pArr[c - 'a']++;
        }

        int left = 0;
        int right = 0;

        while (right < s.length()) {
            //将右指针所指字符加入sArr
            sArr[s.charAt(right) - 'a']++;

            if (right - left + 1 == p.length()) {
                //如果sArr和pArr相等，说明找到一个异位词
                if (Arrays.equals(sArr, pArr)) {
                    result.add(left);
                }

                //左指针所指字符从sArr删除
                sArr[s.charAt(left) - 'a']--;
                left++;
            }

            right++;
        }

        return result;
    }

    /**
     * 滑动窗口，双指针
     * 同findAnagrams()，这里存储字符使用的是hashMap，而不是数组
     * 时间复杂度O(s.length*|C|)，空间复杂度O(|C|) (字符串仅包含小写字母，所以|C|=26)
     *
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams2(String s, String p) {
        if (s.length() < p.length()) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        int left = 0;
        int right = 0;
        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> pMap = new HashMap<>();

        for (char c : p.toCharArray()) {
            pMap.put(c, pMap.getOrDefault(c, 0) + 1);
        }

        while (right < s.length()) {
            char c = s.charAt(right);
            sMap.put(c, sMap.getOrDefault(c, 0) + 1);

            if (right - left + 1 == p.length()) {
                if (isCover(sMap, pMap)) {
                    list.add(left);
                }

                //左指针所指字符从sMap删除
                sMap.put(s.charAt(left), sMap.get(s.charAt(left)) - 1);
                left++;
            }

            right++;
        }

        return list;
    }

    private boolean isCover(Map<Character, Integer> sMap, Map<Character, Integer> pMap) {
        for (Map.Entry<Character, Integer> entry : pMap.entrySet()) {
            //注意Integer之间比较要使用equals，如果范围在[-128,127]之内能比较正确，其他范围比较失败
            if (!sMap.containsKey(entry.getKey()) || !sMap.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }

        return true;
    }
}
