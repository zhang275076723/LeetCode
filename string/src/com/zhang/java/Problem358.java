package com.zhang.java;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2024/2/11 09:17
 * @Author zsy
 * @Description K 距离间隔重排字符串 类比Problem621、Problem767 优先队列类比
 * 给你一个非空的字符串 s 和一个整数 k ，你要将这个字符串 s 中的字母进行重新排列，
 * 使得重排后的字符串中相同字母的位置间隔距离 至少 为 k 。
 * 如果无法做到，请返回一个空字符串 ""。
 * <p>
 * 输入: s = "aabbcc", k = 3
 * 输出: "abcabc"
 * 解释: 相同的字母在新的字符串中间隔至少 3 个单位距离。
 * <p>
 * 输入: s = "aaabc", k = 3
 * 输出: ""
 * 解释: 没有办法找到可能的重排结果。
 * <p>
 * 输入: s = "aaadbbcc", k = 2
 * 输出: "abacabcd"
 * 解释: 相同的字母在新的字符串中间隔至少 2 个单位距离。
 * <p>
 * 1 <= s.length <= 3 * 10^5
 * s 仅由小写英文字母组成
 * 0 <= k <= s.length
 */
public class Problem358 {
    public static void main(String[] args) {
        Problem358 problem358 = new Problem358();
        String s = "aaadbbcc";
        int k = 2;
        System.out.println(problem358.rearrangeString(s, k));
    }

    /**
     * 优先队列，大根堆+队列
     * 统计s中字符出现的次数，将出现次数大于0的字符加入大根堆，当大根堆元素个数大于等于k个时，从大根堆中出堆k个元素，
     * 拼接这k个不相邻的字符，这k个字符出现次数减1，出现次数大于0的字符再重新加入大根堆，直至大根堆中元素个数小于k个，
     * 大根堆剩余元素出堆，如果剩余元素出现次数大于1，则无法构成不相邻的字符串，直接返回""，否则拼接得到结果字符串
     * 时间复杂度O(n+|Σ|+nlog|Σ|)，空间复杂度O(|Σ|) (|Σ|=26，s中只包含小写字母)
     *
     * @param s
     * @param k
     * @return
     */
    public String rearrangeString(String s, int k) {
        if (k == 0 || k == 1) {
            return s;
        }

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

        //大根堆元素个数大于等于k，依次从大根堆中出堆k个元素拼接
        while (priorityQueue.size() >= k) {
            //存储出堆的k个元素的队列
            Queue<Pair> queue = new LinkedList<>();

            //从大根堆中出堆k个元素的出现次数减1
            for (int i = 0; i < k; i++) {
                Pair pair = priorityQueue.poll();
                sb.append(pair.c);
                pair.count--;

                //出堆的k个元素出现次数减1后大于0的字符入队
                if (pair.count > 0) {
                    queue.offer(pair);
                }
            }

            //队列中出堆的k个元素出现次数大于0的字符再重新加入大根堆
            while (!queue.isEmpty()) {
                priorityQueue.offer(queue.poll());
            }
        }

        //大根堆剩余元素出堆，拼接得到结果字符串
        while (!priorityQueue.isEmpty()) {
            Pair pair = priorityQueue.poll();

            //当前字符出现次数大于1，则无法构成相同字符的位置间隔距离至少为k的字符串，返回""
            if (pair.count > 1) {
                return "";
            }

            sb.append(pair.c);
        }

        return sb.toString();
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
