package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/4 9:58
 * @Author zsy
 * @Description 单词拆分 类比Problem72、Problem140、Problem300
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。
 * 请你判断是否可以利用字典中出现的单词拼接出 s 。
 * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
 * <p>
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
 * <p>
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
 * 注意，你可以重复使用字典中的单词。
 * <p>
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 * <p>
 * 1 <= s.length <= 300
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 20
 * s 和 wordDict[i] 仅有小写英文字母组成
 * wordDict 中的所有字符串 互不相同
 */
public class Problem139 {
    public static void main(String[] args) {
        Problem139 problem139 = new Problem139();
//        String s = "catsandog";
//        String[] words = {"cats", "dog", "sand", "and", "cat"};
        String s = "leetcode";
        String[] words = {"leet", "code"};
        List<String> wordDict = new ArrayList<>(Arrays.asList(words));
        System.out.println(problem139.wordBreak(s, wordDict));
        System.out.println(problem139.wordBreak2(s, wordDict));
    }

    /**
     * 动态规划
     * dp[i]：s[0]-s[i-1]是否可以拆成wordDict中的单词
     * dp[i] = true (dp[j] && (s[j]-s[i-1]是否是wordDict中的单词)) (0 <= j < i)
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) {
            return false;
        }

        //从set中查询元素O(1)，比从list中查询元素O(n)速度快
        Set<String> wordDictSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                //s[0]-s[j-1]可以拆分为wordDict中的单词，s[j]-s[i-1]是wordDict中的单词，
                //则说明字符串s中前i个字符可以拆分为wordDict中的单词
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    /**
     * 动态规划优化
     * i从wordDict中最短的单词开始遍历，因为小于最短单词长度，不是wordDict中的单词
     * j从i-wordDict中最长的单词开始遍历，因为再往前s[j]-s[i-1]不是wordDict中的单词
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak2(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) {
            return false;
        }

        //从set中查询元素O(1)，比从list中查询元素速度快O(n)
        Set<String> wordDictSet = new HashSet<>();
        //wordDict中的最长单词长度
        int maxWordLen = wordDict.get(0).length();
        //wordDict中的最短单词长度
        int minWordLen = wordDict.get(0).length();

        for (String word : wordDict) {
            wordDictSet.add(word);
            maxWordLen = Math.max(maxWordLen, word.length());
            minWordLen = Math.min(minWordLen, word.length());
        }

        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        //从最短单词开始遍历
        for (int i = minWordLen; i <= s.length(); i++) {
            //从wordDict中最长的单词开始遍历，因为再往前s[j]-s[i-1]不是wordDict中的单词
            for (int j = Math.max(0, i - maxWordLen); j < i; j++) {
                //s[0]-s[j-1]可以拆分为wordDict中的单词，s[j]-s[i-1]是wordDict中的单词，
                //则说明字符串s中前i个字符可以拆分为wordDict中的单词
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }
}
