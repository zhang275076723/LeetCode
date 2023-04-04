package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/8 9:36
 * @Author zsy
 * @Description 前K个高频单词 微软面试题 类比Problem215、Problem347、Problem451、Problem703、Offer40
 * 给定一个单词列表 words 和一个整数 k ，返回前 k 个出现次数最多的单词。
 * 返回的答案应该按单词出现频率由高到低排序。
 * 如果不同的单词有相同出现频率，按 字典顺序 排序。
 * <p>
 * 输入: words = ["i", "love", "leetcode", "i", "love", "coding"], k = 2
 * 输出: ["i", "love"]
 * 解析: "i" 和 "love" 为出现次数最多的两个单词，均为2次。注意，按字母顺序 "i" 在 "love" 之前。
 * <p>
 * 输入: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
 * 输出: ["the", "is", "sunny", "day"]
 * 解析: "the", "is", "sunny" 和 "day" 是出现次数最多的四个单词，出现次数依次为 4, 3, 2 和 1 次。
 * <p>
 * 1 <= words.length <= 500
 * 1 <= words[i] <= 10
 * words[i] 由小写英文字母组成。
 * k 的取值范围是 [1, 不同 words[i] 的数量]
 */
public class Problem692 {
    public static void main(String[] args) {
        Problem692 problem692 = new Problem692();
        String[] words = {"i", "love", "leetcode", "i", "love", "coding"};
        int k = 2;
        System.out.println(problem692.topKFrequent(words, k));
        System.out.println(problem692.topKFrequent2(words, k));
    }

    /**
     * 哈希表+排序
     * 时间复杂度O(nl+lm*logm)，空间复杂度O(lm) (l:字符串平均长度, m:不同的字符串个数, n:字符串个数)
     *
     * @param words
     * @param k
     * @return
     */
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<>();

        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        List<String> list = new ArrayList<>();

        for (String word : map.keySet()) {
            list.add(word);
        }

        //按照频率由高到低排序，按照字典顺序排序
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                //Integer和Integer之间比较只能使用equals()，不能使用==，==比较的是地址是否相等
                if (!map.get(s1).equals(map.get(s2))) {
                    return map.get(s2) - map.get(s1);
                } else {
                    return s1.compareTo(s2);
                }
            }
        });

        return list.subList(0, k);
    }

    /**
     * 哈希表+小根堆
     * 时间复杂度O(nl+lm*logk)，空间复杂度O(lm+kl) (l:字符串平均长度, m:不同的字符串个数, n:字符串个数)
     *
     * @param words
     * @param k
     * @return
     */
    public List<String> topKFrequent2(String[] words, int k) {
        Map<String, Integer> map = new HashMap<>();

        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        //小根堆，先按照频率由小到大排序，在频率相等的情况下，再按照字典顺序逆序排序
        PriorityQueue<Map.Entry<String, Integer>> priorityQueue = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                //Integer和Integer之间比较只能使用equals()，不能使用==，==比较的是地址是否相等
                if (!entry1.getValue().equals(entry2.getValue())) {
                    //频率由小到大排序
                    return entry1.getValue() - entry2.getValue();
                } else {
                    //频率相等的情况下，按照字典序逆序排序
                    return entry2.getKey().compareTo(entry1.getKey());
                }
            }
        });

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            priorityQueue.offer(entry);

            //当小根堆大小超过k时，堆顶元素出堆
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        }

        LinkedList<String> list = new LinkedList<>();

        while (!priorityQueue.isEmpty()) {
            //小根堆先按照频率由小到大排序，再按照字典顺序逆序排序，所以list首添加
            list.addFirst(priorityQueue.poll().getKey());
        }

        return list;
    }
}
