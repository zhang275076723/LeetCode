package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/12 17:08
 * @Author zsy
 * @Description 去除重复字母 腾讯面试题 华为面试题 单调栈类比Problem42、Problem84、Problem255、Problem321、Problem402、Problem456、Problem496、Problem503、Problem739、Problem1019、Offer33、IntervalMinMultiplyIntervalSumMax
 * 给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。
 * 需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
 * <p>
 * 输入：s = "bcabc"
 * 输出："abc"
 * <p>
 * 输入：s = "cbacdcbc"
 * 输出："acdb"
 * <p>
 * 1 <= s.length <= 10^4
 * s 由小写英文字母组成
 */
public class Problem316 {
    public static void main(String[] args) {
        Problem316 problem316 = new Problem316();
        String s = "cbacdcbc";
        System.out.println(problem316.removeDuplicateLetters(s));
    }

    /**
     * 单调栈+哈希 (求当前元素之后，比当前元素大或小的元素，就要想到单调栈)
     * 单调递增栈存放字典序最小，并不打乱其他字符的相对位置的字符串
     * 哈希统计不同字符和出现的次数
     * 时间复杂度O(n)，空间复杂度O(|Σ|) (|Σ| = 26，s只包含小写字母，共26个字符)
     *
     * @param s
     * @return
     */
    public String removeDuplicateLetters(String s) {
        if (s.length() == 1) {
            return s;
        }

        //统计s中字符和出现的次数，用于保证每个字符都出现
        Map<Character, Integer> map = new HashMap<>();
        //访问数组
        boolean[] visited = new boolean[26];
        //单调递增栈
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (char c : s.toCharArray()) {
            //栈不为空，栈顶元素字典序大于当前字符c字典序，字符c未被访问，且栈顶元素在后面还有的情况下，栈顶元素出栈
            while (!stack.isEmpty() && stack.peekLast() > c && !visited[c - 'a'] && map.get(stack.peekLast()) > 0) {
                //栈顶元素出栈
                char c2 = stack.pollLast();
                //当前元素未访问
                visited[c2 - 'a'] = false;
            }

            //当前字符c已被访问，则字符c已入栈，字符c的个数减1
            if (visited[c - 'a']) {
                map.put(c, map.get(c) - 1);
            } else {
                //当期字符c未被访问，则字符c入栈，标记为已访问，字符c的个数减1
                stack.offerLast(c);
                visited[c - 'a'] = true;
                map.put(c, map.get(c) - 1);
            }
        }

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()) {
            sb.append(stack.pollFirst());
        }

        return sb.toString();
    }
}
