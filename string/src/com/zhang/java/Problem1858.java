package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/6/6 08:17
 * @Author zsy
 * @Description 包含所有前缀的最长单词 同Problem720 前缀树类比
 * 给定一个字符串数组 words，找出 words 中所有的前缀都在 words 中的最长字符串。
 * 例如，令 words = ["a", "app", "ap"]。
 * 字符串 "app" 含前缀 "ap" 和 "a" ，都在 words 中。
 * 返回符合上述要求的字符串。
 * 如果存在多个（符合条件的）相同长度的字符串，返回字典序中最小的字符串，如果这样的字符串不存在，返回 ""。
 * <p>
 * 输入： words = ["k","ki","kir","kira", "kiran"]
 * 输出： "kiran"
 * 解释： "kiran" 含前缀 "kira"、 "kir"、 "ki"、 和 "k"，这些前缀都出现在 words 中。
 * <p>
 * 输入： words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
 * 输出： "apple"
 * 解释： "apple" 和 "apply" 都在 words 中含有各自的所有前缀。
 * 然而，"apple" 在字典序中更小，所以我们返回之。
 * <p>
 * 输入： words = ["abc", "bc", "ab", "qwe"]
 * 输出： ""
 * <p>
 * 1 <= words.length <= 10^5
 * 1 <= words[i].length <= 10^5
 * 1 <= sum(words[i].length) <= 10^5
 */
public class Problem1858 {
    public static void main(String[] args) {
        Problem1858 problem1858 = new Problem1858();
        String[] words = {"a", "banana", "app", "appl", "ap", "apply", "apple"};
        System.out.println(problem1858.longestWord(words));
        System.out.println(problem1858.longestWord2(words));
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

        //初始化字典中最长单词为""
        String result = "";

        //存储已经遍历过的单词集合
        Set<String> set = new HashSet<>();
        set.add("");

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
