package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/21 15:24
 * @Author zsy
 * @Description 字母异位词分组 类比Problem438
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
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
     * 哈希
     * 将strs中每一个字符串排序，放入哈希表中，找到不同的字母异位词
     * 时间复杂度O(nklogk)，空间复杂度O(nk) (n:strs中字符串的个数，k:strs中字符串的最大长度)
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        //key：排序后的str，value：str的字母异位词集合
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            //以排序之后的数组为key
            char[] arr = str.toCharArray();
            heapSort(arr);
            //经排序之后的str
            String key = new String(arr);

            //如果map不存在key，则创建新的ArrayList
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }

            List<String> list = map.get(key);
            list.add(str);
        }

        List<List<String>> result = new ArrayList<>();

        for (List<String> list : map.values()) {
            result.add(list);
        }

        return result;
    }

    /**
     * 计数
     * 字母异位词中字母出现的次数相同，统计strs中每一个字符串中字母出现的次数，放入哈希表中，找到不同的字母异位词
     * 时间复杂度O(n(k+∣Σ∣))，空间复杂度O(n(k+∣Σ∣)) (n为strs中字符串的个数，k为strs中字符串的最大长度，Σ只包含小写字母，∣Σ∣=26)
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams2(String[] strs) {
        //key：str经过转换后的字符串，value：str的字母异位词集合
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            //str只包含小写字母
            int[] count = new int[26];

            for (int i = 0; i < str.length(); i++) {
                count[str.charAt(i) - 'a']++;
            }

            StringBuilder sb = new StringBuilder();

            //转换为字符串，例如：abad-->a2b1d1
            for (int i = 0; i < count.length; i++) {
                if (count[i] > 0) {
                    sb.append((char) ('a' + i));
                    sb.append(count[i]);
                }
            }

            //str经过转换后的表示
            String key = sb.toString();

            //如果map不存在key，则创建新的ArrayList
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }

            List<String> list = map.get(key);
            list.add(str);
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
        int maxIndex = index;
        int leftIndex = index * 2 + 1;
        int rightIndex = index * 2 + 2;

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
