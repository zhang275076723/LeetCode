package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/8/24 11:16
 * @Author zsy
 * @Description 单词拆分 II 小米面试题 类比Problem139、Problem212、Problem472、Problem2707 动态规划预处理类比Problem131 前缀树类比
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
        String s = "catsanddog";
        String[] words = {"cat", "cats", "and", "sand", "dog"};
        List<String> wordDict = new ArrayList<>(Arrays.asList(words));
        System.out.println(problem140.wordBreak(s, wordDict));
        System.out.println(problem140.wordBreak2(s, wordDict));
    }

    /**
     * 动态规划预处理字符串s+回溯+剪枝
     * dp[i]：s[0]-s[i-1]是否可以拆成wordDict中的单词
     * dp[i] = dp[j] && (s[j]-s[i-1]是否是wordDict中的单词) (0 <= j < i)
     *
     * @param s
     * @param wordDict
     * @return
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
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

        List<String> list = new ArrayList<>();

        //s可以拆分为wordDict中单词才进行遍历
        if (dp[s.length()]) {
            backtrack(s.length() - 1, s, new LinkedList<>(), dp, set, list);
        }

        return list;
    }

    /**
     * 前缀树+回溯+剪枝
     * 将wordDict中单词插入前缀树中，在回溯+剪枝中通过前缀树判断当前遍历的子串是否是wordDict中的单词
     *
     * @param s
     * @param wordDict
     * @return
     */
    public List<String> wordBreak2(String s, List<String> wordDict) {
        Trie trie = new Trie();

        for (String word : wordDict) {
            trie.insert(word);
        }

        return trie.search(s);
    }

    private void backtrack(int t, String s, Deque<String> deque, boolean[] dp, Set<String> set, List<String> list) {
        //因为是从后往前遍历，当t为-1时，即找到字符串s的一个拆分
        if (t == -1) {
            StringBuilder sb = new StringBuilder();

            for (String word : deque) {
                sb.append(word).append(' ');
            }

            //去除末尾空格
            list.add(sb.delete(sb.length() - 1, sb.length()).toString());
            return;
        }

        for (int i = t; i >= 0; i--) {
            //s[0]-s[i-1]可以拆成wordDict中的单词，并且s[i]-s[t]是wordDict中的单词，则s继续往前拆分
            if (dp[i] && set.contains(s.substring(i, t + 1))) {
                //因为s是从后往前遍历，所以需要首添加
                deque.addFirst(s.substring(i, t + 1));
                backtrack(i - 1, s, deque, dp, set, list);
                deque.pollFirst();
            }
        }
    }

    /**
     * 前缀树
     */
    private static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                if (!node.children.containsKey(c)) {
                    node.children.put(c, new TrieNode());
                }

                node = node.children.get(c);
            }

            node.isEnd = true;
        }

        public List<String> search(String word) {
            List<String> list = new ArrayList<>();

            backtrack(0, word, new StringBuilder(), list);

            return list;
        }

        private void backtrack(int t, String word, StringBuilder sb, List<String> list) {
            if (t == word.length()) {
                //去除末尾空格
                list.add(sb.delete(sb.length() - 1, sb.length()).toString());
                return;
            }

            TrieNode node = root;

            for (int i = t; i < word.length(); i++) {
                char c = word.charAt(i);

                if (!node.children.containsKey(c)) {
                    return;
                }

                node = node.children.get(c);

                //s[t]-s[i]是wordDict中单词
                if (node.isEnd) {
                    //用于回溯时删除当前添加的单词
                    int length = sb.length();
                    sb.append(word.substring(t, i + 1)).append(' ');
                    backtrack(i + 1, word, sb, list);
                    sb.delete(length, sb.length());
                }
            }
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
