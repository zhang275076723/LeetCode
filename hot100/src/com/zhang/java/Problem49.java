package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/21 15:24
 * @Author zsy
 * @Description 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。
 * <p>
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"], ["nat", "tan"], ["ate", "eat", "tea"]]
 * <p>
 * 输入: strs = [""]
 * 输出: [[""]]
 * <p>
 * 输入: strs = ["a"]
 * 输出: [["a"]]
 * <p>
 * 1 <= strs.length <= 10^4
 * 0 <= strs[i].length <= 100
 * strs[i] 仅包含小写字母
 */
public class Problem49 {
    public static void main(String[] args) {
        Problem49 problem49 = new Problem49();
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(problem49.groupAnagrams(strs));
        System.out.println(problem49.groupAnagrams2(strs));
    }

    /**
     * 时间复杂度O(nklogk)，空间复杂度O(nk)，n为strs中元素个数，k为strs中元素最大长度
     * 对strs中每个元素进行排序，作为key，strs中每个元素作为value，使用map存储
     * eat，排序为aet，key：aet，value：eat
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        //key为排序后的str，value为list集合的字母异位词
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            heapSort(chars);
            String key = new String(chars);
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(str);
            map.put(key, list);
        }

        List<List<String>> result = new ArrayList<>();
        for (List<String> list : map.values()) {
            result.add(list);
        }
        return result;
    }

    /**
     * 时间复杂度O(n(k+∣Σ∣))，空间复杂度O(n(k+∣Σ∣))，n为strs中元素个数，k为strs中元素最大长度，
     * Σ是所有小写字母，∣Σ∣=26
     * str中每个元素出现的次数组成的字符串作为key，strs中每个元素作为value，使用map存储
     * eat，出现次数字符串为a1e1t1，key：a1e1t1，value：eat
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams2(String[] strs) {
        //key为str中每个元素出现的次数组成的字符串作，value为list集合的字母异位词
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            //str只包含小写字母
            int[] count = new int[26];
            for (int i = 0; i < str.length(); i++) {
                count[str.charAt(i) - 'a']++;
            }
            //转换为字符串，例如：abad-->a2b1d1
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count.length; i++) {
                if (count[i] > 0) {
                    sb.append((char) ('a' + i));
                    sb.append(count[i]);
                }
            }
            String key = sb.toString();
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(str);
            map.put(key, list);
        }

        List<List<String>> result = new ArrayList<>();
        for (List<String> list : map.values()) {
            result.add(list);
        }
        return result;
    }

    private void heapSort(char[] chars) {
        for (int i = chars.length / 2 - 1; i >= 0; i--) {
            heapify(chars, i, chars.length);
        }

        for (int i = chars.length - 1; i > 0; i--) {
            char temp = chars[i];
            chars[i] = chars[0];
            chars[0] = temp;
            heapify(chars, 0, i);
        }
    }

    private void heapify(char[] chars, int index, int heapSize) {
        int leftIndex = index * 2 + 1;
        int rightIndex = index * 2 + 2;
        int maxIndex = index;

        if (leftIndex < heapSize && chars[leftIndex] > chars[maxIndex]) {
            maxIndex = leftIndex;
        }
        if (rightIndex < heapSize && chars[rightIndex] > chars[maxIndex]) {
            maxIndex = rightIndex;
        }

        if (maxIndex != index) {
            char temp = chars[maxIndex];
            chars[maxIndex] = chars[index];
            chars[index] = temp;
            heapify(chars, maxIndex, heapSize);
        }
    }
}
