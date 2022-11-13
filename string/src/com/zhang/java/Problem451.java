package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/11/13 10:24
 * @Author zsy
 * @Description 根据字符出现频率排序 类比Problem215、Problem347、Problem692、Offer40
 * 给定一个字符串 s ，根据字符出现的 频率 对其进行 降序排序 。一个字符出现的 频率 是它出现在字符串中的次数。
 * 返回 已排序的字符串 。如果有多个答案，返回其中任何一个。
 * <p>
 * 输入: s = "tree"
 * 输出: "eert"
 * 解释: 'e'出现两次，'r'和't'都只出现一次。
 * 因此'e'必须出现在'r'和't'之前。此外，"eetr"也是一个有效的答案。
 * <p>
 * 输入: s = "cccaaa"
 * 输出: "cccaaa"
 * 解释: 'c'和'a'都出现三次。此外，"aaaccc"也是有效的答案。
 * 注意"cacaca"是不正确的，因为相同的字母必须放在一起。
 * <p>
 * 输入: s = "Aabb"
 * 输出: "bbAa"
 * 解释: 此外，"bbaA"也是一个有效的答案，但"Aabb"是不正确的。
 * 注意'A'和'a'被认为是两种不同的字符。
 * <p>
 * 1 <= s.length <= 5 * 10^5
 * s 由大小写英文字母和数字组成
 */
public class Problem451 {
    public static void main(String[] args) {
        Problem451 problem451 = new Problem451();
        String s = "Aabb";
        System.out.println(problem451.frequencySort(s));
    }

    /**
     * 哈希表+大根堆
     * 时间复杂度O(n+ΣlogΣ)，空间复杂度O(n+Σ) (|Σ| = 62，s只包含大小写字母和，共62个字符)
     *
     * @param s
     * @return
     */
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        //大根堆
        Queue<Map.Entry<Character, Integer>> queue = new PriorityQueue<>(new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> entry1, Map.Entry<Character, Integer> entry2) {
                return entry2.getValue() - entry1.getValue();
            }
        });

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            queue.offer(entry);
        }

        StringBuilder sb = new StringBuilder();

        while (!queue.isEmpty()) {
            Map.Entry<Character, Integer> entry = queue.poll();

            for (int i = 0; i < entry.getValue(); i++) {
                sb.append(entry.getKey());
            }
        }

        return sb.toString();
    }
}
