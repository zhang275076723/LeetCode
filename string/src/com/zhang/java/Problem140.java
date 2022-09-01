package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/8/24 11:16
 * @Author zsy
 * @Description 单词拆分 II 类比Problem139
 * 给定一个字符串 s 和一个字符串字典 wordDict ，在字符串 s 中增加空格来构建一个句子，使得句子中所有的单词都在词典中。
 * 以任意顺序 返回所有这些可能的句子。
 * 注意：词典中的同一个单词可能在分段中被重复使用多次。
 * <p>
 * 输入:s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
 * 输出:["cats and dog","cat sand dog"]
 * <p>
 * 输入:s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
 * 输出:["pine apple pen apple","pineapple pen apple","pine applepen apple"]
 * 解释: 注意你可以重复使用字典中的单词。
 * <p>
 * 输入:s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * 输出:[]
 * <p>
 * 1 <= s.length <= 20
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 10
 * s 和 wordDict[i] 仅有小写英文字母组成
 * wordDict 中所有字符串都 不同
 */
public class Problem140 {
    public static void main(String[] args) {
        Problem140 problem140 = new Problem140();
        String s = "pineapplepenapple";
        String[] words = {"apple", "pen", "applepen", "pine", "pineapple"};
        List<String> wordDict = new ArrayList<>(Arrays.asList(words));
        System.out.println(problem140.wordBreak(s, wordDict));
        System.out.println(problem140.wordBreak2(s, wordDict));
    }

    /**
     * 回溯+剪枝
     *
     * @param s
     * @param wordDict
     * @return
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();

        backtrack(0, new StringBuilder(), s, wordDict, result);

        return result;
    }

    /**
     * 回溯+剪枝+动态规划预处理字符串s
     * dp[i]：s[0]-s[i-1]是否可以拆成wordDict中的单词
     * dp[i] = true (dp[j] && (s[j]-s[i-1]是否是wordDict中的单词)) (0 <= j < i)
     *
     * @param s
     * @param wordDict
     * @return
     */
    public List<String> wordBreak2(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }

        //从set中查询元素O(1)，比从list中查询元素速度快O(n)
        Set<String> wordDictSet = new HashSet<>();
        //wordDict中的最长单词长度
        int maxWordLen = wordDict.get(0).length();
        //wordDict中的最短单词长度
        int minWordLen = wordDict.get(0).length();

        for (String str : wordDict) {
            wordDictSet.add(str);
            maxWordLen = Math.max(maxWordLen, str.length());
            minWordLen = Math.min(minWordLen, str.length());
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

        List<String> result = new ArrayList<>();

        //s可以拆分
        if (dp[s.length()]) {
            //从后往前拆分，使用到dp数组
            backtrack2(s.length(), new LinkedList<>(), s, dp, wordDictSet, result);
        }

        return result;
    }

    private void backtrack(int t, StringBuilder sb, String s, List<String> wordDict, List<String> result) {
        if (t == s.length()) {
            //去除末尾空格
            result.add(sb.substring(0, sb.length() - 1));
            return;
        }

        for (String str : wordDict) {
            if (t + str.length() <= s.length() && s.substring(t, t + str.length()).equals(str)) {
                int start = sb.length();

                sb.append(str).append(' ');
                backtrack(t + str.length(), sb, s, wordDict, result);
                sb.delete(start, sb.length());
            }
        }
    }

    private void backtrack2(int t, Deque<String> deque, String s, boolean[] dp,
                            Set<String> wordDictSet, List<String> result) {
        if (t == 0) {
            //队列中每个元素之间添加空格
            result.add(String.join(" ", deque));
            return;
        }

        for (int i = t - 1; i >= 0; i--) {
            String str = s.substring(i, t);

            //s[0]-s[i-1]可以拆分为wordDict中的单词，并且str是wordDict中的单词
            if (dp[i] && wordDictSet.contains(str)) {
                //因为是从后往前遍历，所以尾添加
                deque.offerFirst(str);
                backtrack2(t - str.length(), deque, s, dp, wordDictSet, result);
                deque.removeFirst();
            }
        }
    }
}
