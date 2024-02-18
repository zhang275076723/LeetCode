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
     * 优先队列，大根堆
     * 统计s中字符出现的次数，将出现次数大于0的字符加入大根堆，当大根堆元素个数大于等于k个，依次从大根堆中出堆k个元素，
     * 拼接这k个不相邻的字符，这k个字符出现次数减1，出现次数大于0的字符再重新加入大根堆，直至大根堆中元素个数小于k个，
     * 大根堆剩余元素出堆，拼接出堆的元素，如果剩余元素的出现次数都为1，则得到结果字符串，否则，结果字符串不合法，返回""
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

        //大根堆元素个数大于等于k，依次从大根堆中出堆k个元素拼接
        while (priorityQueue.size() >= k) {
            //存储出堆的k个元素出现次数减1后大于0的字符队列
            Queue<Character> queue = new LinkedList<>();

            //从大根堆中出堆k个元素的出现次数减1
            for (int i = 0; i < k; i++) {
                char c = priorityQueue.poll();
                sb.append(c);
                count[c - 'a']--;

                //出堆的k个元素出现次数减1后大于0的字符入队
                if (count[c - 'a'] > 0) {
                    queue.offer(c);
                }
            }

            //k个字符出现次数减1，出现次数大于0的字符再重新加入大根堆
            while (!queue.isEmpty()) {
                priorityQueue.offer(queue.poll());
            }
        }

        //大根堆剩余元素出堆，拼接出堆的元素
        while (!priorityQueue.isEmpty()) {
            int c = priorityQueue.poll();
            sb.append(c);

            //剩余元素的出现次数大于1，则结果字符串不合法，返回""
            if (count[c - 'a'] > 1) {
                return "";
            }
        }

        return sb.toString();
    }
}
