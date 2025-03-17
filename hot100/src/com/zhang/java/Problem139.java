package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/4 9:58
 * @Author zsy
 * @Description 单词拆分 类比Problem140、Problem212、Problem472、Problem2707 动态规划类比Problem72、Problem97、Problem115、Problem132、Problem221、Problem392、Problem516、Problem1143、Problem1312 前缀树类比
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。
 * 如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
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
     * dp[i] = dp[j] && (s[j]-s[i-1]是否是wordDict中的单词) (0 <= j < i)
     * 时间复杂度O(ml+n^3)，空间复杂度O(ml+n) (n=s.length，m=wordDict.size()，l=wordDict[i].length())
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        //存储wordDict中单词的集合
        //从set中查询元素O(1)，比从list中查询元素O(n)速度快
        Set<String> set = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        //dp初始化
        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            //判断s[j]-s[i-1]是否是wordDict中单词
            for (int j = 0; j < i; j++) {
                //s[0]-s[j-1]可以拆成wordDict中的单词，并且s[j]-s[i-1]是wordDict中的单词，
                //则s[0]-s[i-1]可以拆成为wordDict中的单词
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    /**
     * 前缀树+动态规划
     * dp[i]：s[0]-s[i-1]是否可以拆成wordDict中的单词
     * dp[i] = dp[j] && (s[j]-s[i-1]是否是wordDict中的单词) (0 <= j < i)
     * 将dictionary中单词的逆序插入前缀树中，通过前缀树O(1)判断子串s[j]-s[i-1]是否是wordDict中的单词
     * 时间复杂度O(ml+n^2)，空间复杂度O(ml+n) (n=s.length，m=wordDict.size()，l=wordDict[i].length())
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak2(String s, List<String> wordDict) {
        Trie trie = new Trie();

        for (String word : wordDict) {
            trie.reverseInsert(word);
        }

        boolean[] dp = new boolean[s.length() + 1];
        //dp初始化
        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            //每次从前缀树根节点开始查找
            Trie.TrieNode node = trie.root;

            //判断s[j]-s[i-1]是否是wordDict中单词
            //注意：j要从后往前遍历
            for (int j = i - 1; j >= 0; j--) {
                char c = s.charAt(j);

                //前缀树中不存在当前节点c，则wordDict中不存在以s[i-1]结尾的单词，直接跳出循环
                if (!node.children.containsKey(c)) {
                    dp[i] = false;
                    break;
                }

                node = node.children.get(c);

                //s[j]-s[i-1]是wordDict中的单词，并且s[0]-s[j-1]可以拆成wordDict中的单词，
                //则s[0]-s[i-1]可以拆成为wordDict中的单词
                if (node.isEnd && dp[j]) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    /**
     * 前缀树
     */
    private static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void reverseInsert(String word) {
            TrieNode node = root;

            for (int i = word.length() - 1; i >= 0; i--) {
                char c = word.charAt(i);

                if (!node.children.containsKey(c)) {
                    node.children.put(c, new TrieNode());
                }

                node = node.children.get(c);
            }

            node.isEnd = true;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final Map<Character, TrieNode> children;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                isEnd = false;
            }
        }
    }
}
