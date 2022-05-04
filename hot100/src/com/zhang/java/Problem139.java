package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/4 9:58
 * @Author zsy
 * @Description 单词拆分
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
        String s = "catsandog";
        String[] words = {"cats", "dog", "sand", "and", "cat"};
        List<String> wordDict = new ArrayList<>(Arrays.asList(words));
        System.out.println(problem139.wordBreak(s, wordDict));
        System.out.println(problem139.wordBreak2(s, wordDict));
    }

    /**
     * 动态规划，时间复杂度O(n^2)，空间复杂度O(n)
     * dp[i]：字符串s中前i个字符是否可以拆成wordDict中的单词
     * dp[i] = dp[j] && (s[j]-s[i]是否是wordDict中的单词) (0<=j<i)
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) {
            return false;
        }

        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        //从set中查询元素O(1)，比从list中查询元素速度快O(n)
        Set<String> wordDictSet = new HashSet<>(wordDict);

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                //s中前j个字符可以拆分为wordDict中的单词，并且s[j]-s[i]是wordDict中的单词，
                //则说明s中前i个字符可以拆分为wordDict中的单词
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    /**
     * 动态规划优化，时间复杂度O(n^2)，空间复杂度O(n)
     * dp[i]只需要从wordDict中最长的单词开始往后遍历即可，
     * 因为从超过wordDict中最长的单词卡死遍历，无法形成wordDict中的单词，即无法拆分
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak2(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) {
            return false;
        }

        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        //从set中查询元素O(1)，比从list中查询元素速度快O(n)
        Set<String> wordDictSet = new HashSet<>();
        //wordDict中的最长单词长度
        int maxWordLen = 0;

        for (String word : wordDict) {
            wordDictSet.add(word);
            maxWordLen = Math.max(maxWordLen, word.length());
        }

        for (int i = 1; i <= s.length(); i++) {
            //从wordDict中最长的单词开始往后遍历，因为再往前不可能匹配到wordDict中的单词
            for (int j = Math.max(0, i - maxWordLen); j < i; j++) {
                //s中前j个字符可以拆分为wordDict中的单词，并且s[j]-s[i]是wordDict中的单词，
                //则说明s中前i个字符可以拆分为wordDict中的单词
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }
}
