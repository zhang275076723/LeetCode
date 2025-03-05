package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/18 09:02
 * @Author zsy
 * @Description 词典中最长的单词 同Problem1858 前缀树类比Problem14、Problem208、Problem211、Problem212、Problem336、Problem421、Problem676、Problem677、Problem745、Problem820、Problem1166、Problem1804、Problem3043
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
//        String[] words = {"a", "banana", "app", "appl", "ap", "apply", "apple"};
        String[] words = {"yo", "ew", "fc", "zrc", "yodn", "fcm", "qm", "qmo", "fcmz", "z", "ewq", "yod", "ewqz", "y"};
        System.out.println(problem720.longestWord(words));
        System.out.println(problem720.longestWord2(words));
    }

    /**
     * 排序+哈希表
     * words[i]按照字典序排序，""作为初始字符串加入哈希集合，如果当前单词words[i]删除末尾字符的字符串在哈希集合中，
     * 则当前单词words[i]可以作为后续遍历到的单词前缀，加入哈希集合，如果当前单词words[i]长度大于最长单词长度，则更新最长单词
     * 时间复杂度O(nl*logn+nl)，空间复杂度O(nl) (n=words.length，l=words[i].length())
     *
     * @param words
     * @return
     */
    public String longestWord(String[] words) {
        //words[i]按照字典序排序
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });

        //存储已经遍历过的单词集合
        Set<String> set = new HashSet<>();
        set.add("");

        //初始化字典中最长单词为""
        String result = "";

        for (String word : words) {
            //当前单词words[i]删除末尾字符的字符串在哈希集合中，则当前单词words[i]可以作为后续遍历到的单词前缀，加入哈希集合
            if (set.contains(word.substring(0, word.length() - 1))) {
                set.add(word);

                //当前单词words[i]长度大于最长单词长度，则更新最长单词
                if (word.length() > result.length()) {
                    result = word;
                }
            }
        }

        return result;
    }

    /**
     * 前缀树
     * words中所有单词加入前缀树中，遍历words[i]，更新最长单词
     * 时间复杂度O(nl)，空间复杂度O(nl) (n=words.length，l=words[i].length())
     *
     * @param words
     * @return
     */
    public String longestWord2(String[] words) {
        Trie trie = new Trie();

        for (String word : words) {
            trie.insert(word);
        }

        //初始化字典中最长单词为""
        String result = "";

        for (String word : words) {
            //word由前缀树中其他单词逐步添加一个字符组成的前缀树中的单词，则更新字典序最小的最长单词
            if (trie.search(word) && (word.length() > result.length() ||
                    (word.length() == result.length() && word.compareTo(result) < 0))) {
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
         * word加入前缀树
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
         * 判断word是否由前缀树中其他单词逐步添加一个字符组成的前缀树中的单词
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param word
         * @return
         */
        public boolean search(String word) {
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                node = node.children.get(c);

                //前缀树中不存在字符c，或者以字符c结尾的单词不是前缀树中的单词，
                //则word不能由前缀树中其他单词逐步添加一个字符组成的前缀树中的单词，返回false
                if (node == null || !node.isEnd) {
                    return false;
                }
            }

            //遍历结束，word由前缀树中其他单词逐步添加一个字符组成的前缀树中的单词，返回true
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
