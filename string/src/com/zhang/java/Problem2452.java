package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2025/4/26 08:51
 * @Author zsy
 * @Description 距离字典两次编辑以内的单词 类比Problem72、Problem161 类比Problem676 前缀树类比
 * 给你两个字符串数组 queries 和 dictionary 。数组中所有单词都只包含小写英文字母，且长度都相同。
 * 一次 编辑 中，你可以从 queries 中选择一个单词，将任意一个字母修改成任何其他字母。
 * 从 queries 中找到所有满足以下条件的字符串：不超过 两次编辑内，字符串与 dictionary 中某个字符串相同。
 * 请你返回 queries 中的单词列表，这些单词距离 dictionary 中的单词 编辑次数 不超过 两次 。
 * 单词返回的顺序需要与 queries 中原本顺序相同。
 * <p>
 * 输入：queries = ["word","note","ants","wood"], dictionary = ["wood","joke","moat"]
 * 输出：["word","note","wood"]
 * 解释：
 * - 将 "word" 中的 'r' 换成 'o' ，得到 dictionary 中的单词 "wood" 。
 * - 将 "note" 中的 'n' 换成 'j' 且将 't' 换成 'k' ，得到 "joke" 。
 * - "ants" 需要超过 2 次编辑才能得到 dictionary 中的单词。
 * - "wood" 不需要修改（0 次编辑），就得到 dictionary 中相同的单词。
 * 所以我们返回 ["word","note","wood"] 。
 * <p>
 * 输入：queries = ["yes"], dictionary = ["not"]
 * 输出：[]
 * 解释：
 * "yes" 需要超过 2 次编辑才能得到 "not" 。
 * 所以我们返回空数组。
 * <p>
 * 1 <= queries.length, dictionary.length <= 100
 * n == queries[i].length == dictionary[j].length
 * 1 <= n <= 100
 * 所有 queries[i] 和 dictionary[j] 都只包含小写英文字母。
 */
public class Problem2452 {
    public static void main(String[] args) {
        Problem2452 problem2452 = new Problem2452();
        String[] queries = {"word", "note", "ants", "wood"};
        String[] dictionary = {"wood", "joke", "moat"};
        System.out.println(problem2452.twoEditWords(queries, dictionary));
    }

    /**
     * 前缀树+回溯+剪枝
     * 时间复杂度O(nq+mp)，空间复杂度O(nq) (m=queries.length，n=dictionary.length，p=queries[i].length()，q=dictionary[i].length())
     *
     * @param queries
     * @param dictionary
     * @return
     */
    public List<String> twoEditWords(String[] queries, String[] dictionary) {
        Trie trie = new Trie();

        for (String word : dictionary) {
            trie.insert(word);
        }

        List<String> list = new ArrayList<>();

        for (String word : queries) {
            if (trie.search(word)) {
                list.add(word);
            }
        }

        return list;
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

        public boolean search(String word) {
            return backtrack(0, 0, root, word);
        }

        private boolean backtrack(int t, int count, TrieNode node, String word) {
            if (t == word.length()) {
                return node.isEnd && count <= 2;
            }

            char c = word.charAt(t);

            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                if (c == entry.getKey()) {
                    if (backtrack(t + 1, count, entry.getValue(), word)) {
                        return true;
                    }
                } else {
                    if (backtrack(t + 1, count + 1, entry.getValue(), word)) {
                        return true;
                    }
                }
            }

            return false;
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
