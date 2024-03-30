package com.zhang.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/3/27 09:10
 * @Author zsy
 * @Description 统计范围内的元音字符串数 元音类比Problem345、Problem824、Problem966、Problem1119、Problem1371、Problem1456、Problem1704、Problem1839、Problem2062、Problem2063 前缀和类比
 * 给你一个下标从 0 开始的字符串数组 words 以及一个二维整数数组 queries 。
 * 每个查询 queries[i] = [li, ri] 会要求我们统计在 words 中下标在 li 到 ri 范围内（包含 这两个值）并且以元音开头和结尾的字符串的数目。
 * 返回一个整数数组，其中数组的第 i 个元素对应第 i 个查询的答案。
 * 注意：元音字母是 'a'、'e'、'i'、'o' 和 'u' 。
 * <p>
 * 输入：words = ["aba","bcb","ece","aa","e"], queries = [[0,2],[1,4],[1,1]]
 * 输出：[2,3,0]
 * 解释：以元音开头和结尾的字符串是 "aba"、"ece"、"aa" 和 "e" 。
 * 查询 [0,2] 结果为 2（字符串 "aba" 和 "ece"）。
 * 查询 [1,4] 结果为 3（字符串 "ece"、"aa"、"e"）。
 * 查询 [1,1] 结果为 0 。
 * 返回结果 [2,3,0] 。
 * <p>
 * 输入：words = ["a","e","i"], queries = [[0,2],[0,1],[2,2]]
 * 输出：[3,2,1]
 * 解释：每个字符串都满足这一条件，所以返回 [3,2,1] 。
 * <p>
 * 1 <= words.length <= 10^5
 * 1 <= words[i].length <= 40
 * words[i] 仅由小写英文字母组成
 * sum(words[i].length) <= 3 * 10^5
 * 1 <= queries.length <= 10^5
 * 0 <= queries[j][0] <= queries[j][1] < words.length
 */
public class Problem2559 {
    public static void main(String[] args) {
        Problem2559 problem2559 = new Problem2559();
        String[] words = {"aba", "bcb", "ece", "aa", "e"};
        int[][] queries = {{0, 2}, {1, 4}, {1, 1}};
        System.out.println(Arrays.toString(problem2559.vowelStrings(words, queries)));
    }

    /**
     * 前缀和
     * preSum[i]：words[0]-words[i-1]中以元音字母开头并且以元音字母结尾的字符串个数
     * 时间复杂度O(n+m)，空间复杂度O(n) (n=words.length，m=queries.length)
     *
     * @param words
     * @param queries
     * @return
     */
    public int[] vowelStrings(String[] words, int[][] queries) {
        //前缀和数组
        int[] preSum = new int[words.length + 1];
        //5个小写元音字符的集合，不需要包含大写元音字母
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
        }};

        for (int i = 1; i <= words.length; i++) {
            //words[i-1]的开头字符
            char c1 = words[i - 1].charAt(0);
            //words[i-1]的结尾字符
            char c2 = words[i - 1].charAt(words[i - 1].length() - 1);

            if (vowelSet.contains(c1) && vowelSet.contains(c2)) {
                preSum[i] = preSum[i - 1] + 1;
            } else {
                preSum[i] = preSum[i - 1];
            }
        }

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int left = queries[i][0];
            int right = queries[i][1];
            result[i] = preSum[right + 1] - preSum[left];
        }

        return result;
    }
}
