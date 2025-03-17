package com.zhang.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2024/2/28 08:56
 * @Author zsy
 * @Description 单词的压缩编码 类比Problem271、Problem297、Problem331、Problem449、Problem535、Problem625、Problem1948、Offer37 前缀树类比Problem14、Problem208、Problem211、Problem212、Problem336、Problem421、Problem676、Problem677、Problem720、Problem745、Problem1166、Problem1804、Problem3043
 * 单词数组 words 的 有效编码 由任意助记字符串 s 和下标数组 indices 组成，且满足：
 * words.length == indices.length
 * 助记字符串 s 以 '#' 字符结尾
 * 对于每个下标 indices[i] ，s 的一个从 indices[i] 开始、到下一个 '#' 字符结束（但不包括 '#'）的 子字符串 恰好与 words[i] 相等
 * 给你一个单词数组 words ，返回成功对 words 进行编码的最小助记字符串 s 的长度 。
 * <p>
 * 输入：words = ["time", "me", "bell"]
 * 输出：10
 * 解释：一组有效编码为 s = "time#bell#" 和 indices = [0, 2, 5] 。
 * words[0] = "time" ，s 开始于 indices[0] = 0 到下一个 '#' 结束的子字符串，如加粗部分所示 "time#bell#"
 * words[1] = "me" ，s 开始于 indices[1] = 2 到下一个 '#' 结束的子字符串，如加粗部分所示 "time#bell#"
 * words[2] = "bell" ，s 开始于 indices[2] = 5 到下一个 '#' 结束的子字符串，如加粗部分所示 "time#bell#"
 * <p>
 * 输入：words = ["t"]
 * 输出：2
 * 解释：一组有效编码为 s = "t#" 和 indices = [0] 。
 * <p>
 * 1 <= words.length <= 2000
 * 1 <= words[i].length <= 7
 * words[i] 仅由小写字母组成
 */
public class Problem820 {
    public static void main(String[] args) {
        Problem820 problem820 = new Problem820();
//        //time#bell#
//        String[] words = {"time", "me", "bell"};
//        //dabc#ab#
//        String[] words = {"abc", "dabc", "ab"};
        //time#
        String[] words = {"time", "time", "time", "time"};
        System.out.println(problem820.minimumLengthEncoding(words));
    }

    /**
     * 前缀树
     * words[i]的逆序字符串插入前缀树中，words[i]的逆序字符串全部插入前缀树中之后，
     * words[i]的逆序字符串的末尾前缀树节点是前缀树的叶子节点并且words[i]是第一次访问，则words[i]+"#"加入助记字符串
     * 时间复杂度O(mn)，空间复杂度O(mn) (n=words.length，m=words[i]的平均长度)
     *
     * @param words
     * @return
     */
    public int minimumLengthEncoding(String[] words) {
        //前缀树
        Trie trie = new Trie();

        //words[i]的逆序字符串插入前缀树中
        for (String word : words) {
            trie.reverseInsert(word);
        }

        //最小助记字符串的长度
        int length = 0;
        //记录已经访问过的单词set集合，因为words中单词有可能重复，避免相同单词多次加入助记字符串的情况
        Set<String> set = new HashSet<>();

        for (String word : words) {
            //words[i]的逆序字符串的末尾前缀树节点
            Trie.TrieNode node = trie.search(word);

            //words[i]的逆序字符串的末尾前缀树节点是前缀树的叶子节点并且words[i]是第一次访问，则words[i]+"#"加入助记字符串
            if (trie.isLeafNode(node) && !set.contains(word)) {
                length = length + word.length() + 1;
                set.add(word);
            }
        }

        return length;
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
         * word的逆序字符串插入前缀树中
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param word
         */
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
         * 返回word的逆序字符串的末尾前缀树节点
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param word
         * @return
         */
        public TrieNode search(String word) {
            TrieNode node = root;

            for (int i = word.length() - 1; i >= 0; i--) {
                char c = word.charAt(i);
                node = node.children.get(c);
            }

            return node;
        }

        /**
         * 判断当前前缀树节点是否是前缀树的叶子节点
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @return
         */
        public boolean isLeafNode(TrieNode node) {
            if (node == null) {
                return false;
            }

            //叶子节点没有子节点
            return node.children.isEmpty();
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
