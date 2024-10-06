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
//        String s = "aab";
        String s = "aaababaacbb";
        System.out.println(problem767.reorganizeString(s));
        System.out.println(problem767.reorganizeString2(s));
    }

    /**
     * 优先队列，大根堆
     * 统计s中字符出现的次数，将出现次数大于0的字符加入大根堆，当大根堆元素个数大于等于2个时，从大根堆中出堆2个元素，
     * 拼接这2个不相邻的字符，这2个字符出现次数减1，出现次数大于0的字符再重新加入大根堆，直至大根堆中元素个数小于2个，
     * 大根堆剩余元素出堆，如果剩余元素出现次数大于1，则无法构成不相邻的字符串，直接返回""，否则拼接得到结果字符串
     * 时间复杂度O(n+|Σ|+nlog|Σ|)，空间复杂度O(|Σ|) (|Σ|=26，s中只包含小写字母)
     *
     * @param s
     * @return
     */
    public String reorganizeString(String s) {
        //s中字符出现次数数组
        int[] count = new int[26];

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        //优先队列，大根堆，存放字符出现的次数和字符
        //按照字符出现的次数由大到小排序，再按照字典序由小到大排序
        PriorityQueue<Pair> priorityQueue = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair pair1, Pair pair2) {
                if (pair1.count != pair2.count) {
                    return pair2.count - pair1.count;
                } else {
                    return pair1.c - pair2.c;
                }
            }
        });

        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                priorityQueue.offer(new Pair((char) ('a' + i), count[i]));
            }
        }

        StringBuilder sb = new StringBuilder();

        //大根堆元素个数大于等于2，依次从大根堆中出堆两个元素拼接
        while (priorityQueue.size() >= 2) {
            Pair pair1 = priorityQueue.poll();
            Pair pair2 = priorityQueue.poll();

            sb.append(pair1.c).append(pair2.c);

            pair1.count--;
            pair2.count--;

            //出现次数大于0的字符再重新加入大根堆
            if (pair1.count > 0) {
                priorityQueue.offer(pair1);
            }
            if (pair2.count > 0) {
                priorityQueue.offer(pair2);
            }
        }

        //大根堆剩余元素出堆，拼接得到结果字符串
        while (!priorityQueue.isEmpty()) {
            Pair pair = priorityQueue.poll();

            //当前字符出现次数大于1，则无法构成不相邻的字符串，返回""
            if (pair.count > 1) {
                return "";
            }

            sb.append(pair.c);
        }

        return sb.toString();
    }

    /**
     * 模拟
     * 统计s中字符出现的次数，先将出现次数最多的字符放到结果字符串的偶数下标，再接着偶数下标放置其他字符，
     * 当下标超过s的范围时，从奇数下标开始继续放置其他字符
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

        char[] arr = new char[s.length()];
        //arr当前遍历到的下标索引
        int index = 0;

        //先将出现次数最多的字符放到结果字符串的偶数下标
        while (count[maxChar - 'a'] > 0) {
            arr[index] = maxChar;
            index = index + 2;
            count[maxChar - 'a']--;

            //偶数下标都放置maxChar之后，奇数下标索引还要放置maxChar，则无法构成不相邻的字符串，直接返回""
            if (count[maxChar - 'a'] > 0 && index >= s.length()) {
                return "";
            }
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

    private static class Pair {
        private char c;
        private int count;

        public Pair(char c, int count) {
            this.c = c;
            this.count = count;
        }
    }
}
