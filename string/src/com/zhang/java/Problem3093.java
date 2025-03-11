package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2025/4/21 08:10
 * @Author zsy
 * @Description 最长公共后缀查询 前缀树类比
 * 给你两个字符串数组 wordsContainer 和 wordsQuery 。
 * 对于每个 wordsQuery[i] ，你需要从 wordsContainer 中找到一个与 wordsQuery[i] 有 最长公共后缀 的字符串。
 * 如果 wordsContainer 中有两个或者更多字符串有最长公共后缀，那么答案为长度 最短 的。
 * 如果有超过两个字符串有 相同 最短长度，那么答案为它们在 wordsContainer 中出现 更早 的一个。
 * 请你返回一个整数数组 ans ，其中 ans[i]是 wordsContainer中与 wordsQuery[i] 有 最长公共后缀 字符串的下标。
 * <p>
 * 输入：wordsContainer = ["abcd","bcd","xbcd"], wordsQuery = ["cd","bcd","xyz"]
 * 输出：[1,1,1]
 * 解释：
 * 我们分别来看每一个 wordsQuery[i] ：
 * 对于 wordsQuery[0] = "cd" ，wordsContainer 中有最长公共后缀 "cd" 的字符串下标分别为 0 ，1 和 2 。
 * 这些字符串中，答案是下标为 1 的字符串，因为它的长度为 3 ，是最短的字符串。
 * 对于 wordsQuery[1] = "bcd" ，wordsContainer 中有最长公共后缀 "bcd" 的字符串下标分别为 0 ，1 和 2 。
 * 这些字符串中，答案是下标为 1 的字符串，因为它的长度为 3 ，是最短的字符串。
 * 对于 wordsQuery[2] = "xyz" ，wordsContainer 中没有字符串跟它有公共后缀，所以最长公共后缀为 "" ，下标为 0 ，1 和 2 的字符串都得到这一公共后缀。
 * 这些字符串中， 答案是下标为 1 的字符串，因为它的长度为 3 ，是最短的字符串。
 * <p>
 * 输入：wordsContainer = ["abcdefgh","poiuygh","ghghgh"], wordsQuery = ["gh","acbfgh","acbfegh"]
 * 输出：[2,0,2]
 * 解释：
 * 我们分别来看每一个 wordsQuery[i] ：
 * 对于 wordsQuery[0] = "gh" ，wordsContainer 中有最长公共后缀 "gh" 的字符串下标分别为 0 ，1 和 2 。
 * 这些字符串中，答案是下标为 2 的字符串，因为它的长度为 6 ，是最短的字符串。
 * 对于 wordsQuery[1] = "acbfgh" ，只有下标为 0 的字符串有最长公共后缀 "fgh" 。
 * 所以尽管下标为 2 的字符串是最短的字符串，但答案是 0 。
 * 对于 wordsQuery[2] = "acbfegh" ，wordsContainer 中有最长公共后缀 "gh" 的字符串下标分别为 0 ，1 和 2 。
 * 这些字符串中，答案是下标为 2 的字符串，因为它的长度为 6 ，是最短的字符串。
 * <p>
 * 1 <= wordsContainer.length, wordsQuery.length <= 10^4
 * 1 <= wordsContainer[i].length <= 5 * 10^3
 * 1 <= wordsQuery[i].length <= 5 * 10^3
 * wordsContainer[i] 只包含小写英文字母。
 * wordsQuery[i] 只包含小写英文字母。
 * wordsContainer[i].length 的和至多为 5 * 10^5 。
 * wordsQuery[i].length 的和至多为 5 * 10^5 。
 */
public class Problem3093 {
    public static void main(String[] args) {
        Problem3093 problem3093 = new Problem3093();
//        String[] wordsContainer = {"abcd", "bcd", "xbcd"};
//        String[] wordsQuery = {"cd", "bcd", "xyz"};
        String[] wordsContainer = {"abcdefgh", "poiuygh", "ghghgh"};
        String[] wordsQuery = {"gh", "acbfgh", "acbfegh"};
        System.out.println(Arrays.toString(problem3093.stringIndices(wordsContainer, wordsQuery)));
    }

    /**
     * 前缀树
     * 时间复杂度O(m*p+n*q)，空间复杂度O(m*p) (m=wordsContainer.length，n=wordsQuery.length，p=wordsContainer[i].length()，q=wordsQuery[i].length())
     *
     * @param wordsContainer
     * @param wordsQuery
     * @return
     */
    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        Trie trie = new Trie();

        for (int i = 0; i < wordsContainer.length; i++) {
            trie.reverseInsert(wordsContainer[i], i);
        }

        int[] result = new int[wordsQuery.length];

        for (int i = 0; i < wordsQuery.length; i++) {
            result[i] = trie.reverseSearch(wordsQuery[i]);
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
         * word逆序插入前缀树中，并且更新路径中每个节点的minLen和index
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param word
         * @param index
         */
        public void reverseInsert(String word, int index) {
            TrieNode node = root;

            if (word.length() < node.minLen) {
                node.minLen = word.length();
                node.index = index;
            }

            for (int i = word.length() - 1; i >= 0; i--) {
                char c = word.charAt(i);

                if (!node.children.containsKey(c)) {
                    node.children.put(c, new TrieNode());
                }

                node = node.children.get(c);

                if (word.length() < node.minLen) {
                    node.minLen = word.length();
                    node.index = index;
                }
            }

            node.isEnd = true;
        }

        /**
         * 前缀树中逆序查询word，返回wordsContainer中和word有最长公共后缀的单词下标索引
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param word
         * @return
         */
        public int reverseSearch(String word) {
            TrieNode node = root;

            for (int i = word.length() - 1; i >= 0; i--) {
                char c = word.charAt(i);

                if (!node.children.containsKey(c)) {
                    return node.index;
                }

                node = node.children.get(c);
            }

            return node.index;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final Map<Character, TrieNode> children;
            //前缀树中根节点到当前节点作为wordsContainer中单词后缀的最短单词长度
            private int minLen;
            //前缀树中根节点到当前节点作为wordsContainer中单词后缀的最短单词在wordsContainer中的下标索引
            private int index;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                minLen = Integer.MAX_VALUE;
                index = -1;
                isEnd = false;
            }
        }
    }
}
