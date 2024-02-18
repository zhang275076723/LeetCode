package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/2/10 08:30
 * @Author zsy
 * @Description 重构字符串 华为面试题 类比Problem358、Problem621 优先队列类比
 * 给定一个字符串 s ，检查是否能重新排布其中的字母，使得两相邻的字符不同。
 * 返回 s 的任意可能的重新排列。若不可行，返回空字符串 "" 。
 * <p>
 * 输入: s = "aab"
 * 输出: "aba"
 * <p>
 * 输入: s = "aaab"
 * 输出: ""
 * <p>
 * 1 <= s.length <= 500
 * s 只包含小写字母
 */
public class Problem767 {
    public static void main(String[] args) {
        Problem767 problem767 = new Problem767();
        String s = "aaababaacbb";
        System.out.println(problem767.reorganizeString(s));
        System.out.println(problem767.reorganizeString2(s));
    }

    /**
     * 优先队列，大根堆
     * 统计s中字符出现的次数，将出现次数大于0的字符加入大根堆，当大根堆元素个数大于等于2个，依次从大根堆中出堆2个元素，
     * 拼接这2个不相邻的字符，这2个字符出现次数减1，出现次数大于0的字符再重新加入大根堆，直至大根堆中元素个数小于2个，
     * 大根堆剩余元素出堆，拼接得到结果字符串
     * 时间复杂度O(n+|Σ|+nlog|Σ|)，空间复杂度O(|Σ|) (|Σ|=26，s中只包含小写字母)
     *
     * @param s
     * @return
     */
    public String reorganizeString(String s) {
        //s中字符出现次数数组
        int[] count = new int[26];
        //s中字符出现的最大次数
        int maxCount = 0;

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
            maxCount = Math.max(maxCount, count[c - 'a']);
        }

        //maxCount大于s长度加1的一半，则无法构成不相邻的字符串，直接返回""
        if (maxCount > (s.length() + 1) / 2) {
            return "";
        }

        //大根堆，先按照字符出现次数由大到小排序，再按照字典序由小到大排序
        PriorityQueue<Character> priorityQueue = new PriorityQueue<>(new Comparator<Character>() {
            @Override
            public int compare(Character c1, Character c2) {
                if (count[c1 - 'a'] != count[c2 - 'a']) {
                    return count[c2 - 'a'] - count[c1 - 'a'];
                } else {
                    return c1 - c2;
                }
            }
        });

        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                priorityQueue.offer((char) ('a' + i));
            }
        }

        StringBuilder sb = new StringBuilder();

        //大根堆元素个数大于等于2，依次从大根堆中出堆两个元素拼接
        while (priorityQueue.size() >= 2) {
            char c1 = priorityQueue.poll();
            char c2 = priorityQueue.poll();

            sb.append(c1).append(c2);

            count[c1 - 'a']--;
            count[c2 - 'a']--;

            //出现次数大于0的字符再重新加入大根堆
            if (count[c1 - 'a'] > 0) {
                priorityQueue.offer(c1);
            }
            if (count[c2 - 'a'] > 0) {
                priorityQueue.offer(c2);
            }
        }

        //大根堆剩余元素出堆，拼接得到结果字符串
        while (!priorityQueue.isEmpty()) {
            char c = priorityQueue.poll();
            sb.append(c);
        }

        return sb.toString();
    }

    /**
     * 模拟
     * 统计s中字符出现的次数，先将出现次数最多的字符放到结果字符串的偶数下标，再接着偶数下标放置其他字符，当下标超过s的范围时，
     * 从奇数下标开始继续放置其他字符
     * 时间复杂度O(n+|Σ|)，空间复杂度O(|Σ|+n) (|Σ|=26，s中只包含小写字母) (因为arr数组要转换为String才能返回，所以空间复杂度多了O(n))
     *
     * @param s
     * @return
     */
    public String reorganizeString2(String s) {
        //s中字符出现次数数组
        int[] count = new int[26];
        //s中字符出现的最大次数
        int maxCount = 0;
        //s中出现次数最大的字符
        char maxChar = 0;

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
            if (count[c - 'a'] > maxCount) {
                maxCount = count[c - 'a'];
                maxChar = c;
            }
        }

        //maxCount大于s长度加1的一半，则无法构成不相邻的字符串，直接返回""
        if (maxCount > (s.length() + 1) / 2) {
            return "";
        }

        char[] arr = new char[s.length()];
        int index = 0;

        //先将出现次数最多的字符放到结果字符串的偶数下标
        while (count[maxChar - 'a'] > 0) {
            arr[index] = maxChar;
            index = index + 2;
            count[maxChar - 'a']--;
        }

        //再接着偶数下标放置其他字符
        for (int i = 0; i < 26; i++) {
            while (count[i] > 0) {
                //当下标超过s的范围时，从奇数下标开始继续放置其他字符
                if (index >= s.length()) {
                    index = 1;
                }

                arr[index] = (char) ('a' + i);
                index = index + 2;
                count[i]--;
            }
        }

        return new String(arr);
    }
}
