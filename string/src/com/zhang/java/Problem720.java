package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/1/18 09:02
 * @Author zsy
 * @Description 词典中最长的单词 前缀树类比Problem14、Problem208、Problem211、Problem212、Problem336、Problem421、Problem676、Problem677、Problem745、Problem1804
 * 给出一个字符串数组 words 组成的一本英语词典。
 * 返回 words 中最长的一个单词，该单词是由 words 词典中其他单词逐步添加一个字母组成。
 * 若其中有多个可行的答案，则返回答案中字典序最小的单词。
 * 若无答案，则返回空字符串。
 * <p>
 * 输入：words = ["w","wo","wor","worl", "world"]
 * 输出："world"
 * 解释： 单词"world"可由"w", "wo", "wor", 和 "worl"逐步添加一个字母组成。
 * <p>
 * 输入：words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
 * 输出："apple"
 * 解释："apply" 和 "apple" 都能由词典中的单词组成。但是 "apple" 的字典序小于 "apply"
 * <p>
 * 1 <= words.length <= 1000
 * 1 <= words[i].length <= 30
 * 所有输入的字符串 words[i] 都只包含小写字母。
 */
public class Problem720 {
    public static void main(String[] args) {
        Problem720 problem720 = new Problem720();
        String[] word = {"a", "banana", "app", "appl", "ap", "apply", "apple"};
        System.out.println(problem720.longestWord(word));
    }

    /**
     * 前缀树
     * 将字典中所有单词加入前缀树中，再次遍历字典，得到由其他单词逐步添加一个字母组成，并且字典序最小的最长单词
     * 时间复杂度O(nl)，空间复杂度O(nl) (n=words.length，l=words[i].length())
     *
     * @param words
     * @return
     */
    public String longestWord(String[] words) {
        Trie trie = new Trie();

        for (String word : words) {
            trie.insert(word);
        }

        //初始化字典中最长单词为空字符串
        String result = "";

        for (String word : words) {
            //word是前缀树中的单词，并且word由前缀树中其他单词逐步添加一个字母组成，则更新字典序最小的最长单词result
            if (trie.search(word) &&
                    (word.length() > result.length() || (word.length() == result.length() && word.compareTo(result) < 0))) {
                result = word;
            }
        }

        return result;
    }

    /**
     * 前缀树
     */
    private static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        /**
         * 将word加入前缀树
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param word
         */
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

        /**
         * 判断word是否是前缀树中的单词，并且word是否由前缀树中其他单词逐步添加一个字母组成
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param word
         * @return
         */
        public boolean search(String word) {
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                node = node.children.get(c);

                //前缀树中不存在字符c，或者以字符c结尾的单词不是前缀树中的单词，即word不能由前缀树中其他单词逐步添加一个字母组成，返回false
                if (node == null || !node.isEnd) {
                    return false;
                }
            }

            //遍历结束，word肯定是前缀树中的单词，并且word肯定能由前缀树中其他单词逐步添加一个字母组成，返回true
            return true;
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
