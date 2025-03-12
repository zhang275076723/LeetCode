package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2025/4/21 08:42
 * @Author zsy
 * @Description 字符串的前缀分数和 前缀树类比
 * 给你一个长度为 n 的数组 words ，该数组由 非空 字符串组成。
 * 定义字符串 term 的 分数 等于以 term 作为 前缀 的 words[i] 的数目。
 * 例如，如果 words = ["a", "ab", "abc", "cab"] ，那么 "ab" 的分数是 2 ，因为 "ab" 是 "ab" 和 "abc" 的一个前缀。
 * 返回一个长度为 n 的数组 answer ，其中 answer[i] 是 words[i] 的每个非空前缀的分数 总和 。
 * 注意：字符串视作它自身的一个前缀。
 * <p>
 * 输入：words = ["abc","ab","bc","b"]
 * 输出：[5,4,3,2]
 * 解释：对应每个字符串的答案如下：
 * - "abc" 有 3 个前缀："a"、"ab" 和 "abc" 。
 * - 2 个字符串的前缀为 "a" ，2 个字符串的前缀为 "ab" ，1 个字符串的前缀为 "abc" 。
 * 总计 answer[0] = 2 + 2 + 1 = 5 。
 * - "ab" 有 2 个前缀："a" 和 "ab" 。
 * - 2 个字符串的前缀为 "a" ，2 个字符串的前缀为 "ab" 。
 * 总计 answer[1] = 2 + 2 = 4 。
 * - "bc" 有 2 个前缀："b" 和 "bc" 。
 * - 2 个字符串的前缀为 "b" ，1 个字符串的前缀为 "bc" 。
 * 总计 answer[2] = 2 + 1 = 3 。
 * - "b" 有 1 个前缀："b"。
 * - 2 个字符串的前缀为 "b" 。
 * 总计 answer[3] = 2 。
 * <p>
 * 输入：words = ["abcd"]
 * 输出：[4]
 * 解释：
 * "abcd" 有 4 个前缀 "a"、"ab"、"abc" 和 "abcd"。
 * 每个前缀的分数都是 1 ，总计 answer[0] = 1 + 1 + 1 + 1 = 4 。
 * <p>
 * 1 <= words.length <= 1000
 * 1 <= words[i].length <= 1000
 * words[i] 由小写英文字母组成
 */
public class Problem2416 {
    public static void main(String[] args) {
        Problem2416 problem2416 = new Problem2416();
        String[] words = {"abc", "ab", "bc", "b"};
        System.out.println(Arrays.toString(problem2416.sumPrefixScores(words)));
    }

    /**
     * 前缀树
     * 时间复杂度O(mn)，空间复杂度O(mn) (n=words.length，m=words[i].length())
     *
     * @param words
     * @return
     */
    public int[] sumPrefixScores(String[] words) {
        int[] result = new int[words.length];
        Trie trie = new Trie();

        for (String word : words) {
            trie.insert(word);
        }

        for (int i = 0; i < words.length; i++) {
            result[i] = trie.search(words[i]);
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
                node.count++;
            }

            node.isEnd = true;
        }

        public int search(String word) {
            TrieNode node = root;
            int score = 0;

            for (char c : word.toCharArray()) {
                node = node.children.get(c);
                score = score + node.count;
            }

            return score;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final Map<Character, TrieNode> children;
            //前缀树中根节点到当前节点作为前缀的字符串个数
            private int count;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                count = 0;
                isEnd = false;
            }
        }
    }
}
