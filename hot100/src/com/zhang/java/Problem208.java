package com.zhang.java;


import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/5/14 8:48
 * @Author zsy
 * @Description 实现 Trie (前缀树) 腾讯面试题 字节面试题 前缀树类比Problem14、Problem211、Problem212、Problem421、Problem677、Problem1804
 * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。
 * 这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
 * 请你实现 Trie 类：
 * Trie() 初始化前缀树对象。
 * void insert(String word) 向前缀树中插入字符串 word 。
 * boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；
 * 否则，返回 false 。
 * boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；
 * 否则，返回 false 。
 * <p>
 * 输入
 * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
 * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
 * 输出
 * [null, null, true, false, true, null, true]
 * 解释
 * Trie trie = new Trie();
 * trie.insert("apple");
 * trie.search("apple");   // 返回 True
 * trie.search("app");     // 返回 False
 * trie.startsWith("app"); // 返回 True
 * trie.insert("app");
 * trie.search("app");     // 返回 True
 * <p>
 * 1 <= word.length, prefix.length <= 2000
 * word 和 prefix 仅由小写英文字母组成
 * insert、search 和 startsWith 调用次数 总计 不超过 3 * 10^4 次
 */
public class Problem208 {
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("app");
        System.out.println(trie.search("ap"));
        System.out.println(trie.search("app"));
        System.out.println(trie.search("appp"));
        System.out.println(trie.startsWith("ap"));
        System.out.println(trie.startsWith("app"));
        System.out.println(trie.startsWith("appp"));
    }

    /**
     * 前缀树
     * 空间复杂度O(|Σ|*|T|)，Σ为字符集的大小，Σ=26，T为字符串的个数
     */
    private static class Trie {
        //前缀树根节点
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        /**
         * 时间复杂度O(|S|)，|S|是word的长度
         *
         * @param word
         */
        public void insert(String word) {
            TrieNode node = root;

            //word从根节点往下插入
            for (char c : word.toCharArray()) {
                if (!node.children.containsKey(c)) {
                    node.children.put(c, new TrieNode());
                }

                node = node.children.get(c);
            }

            //最后一个节点作为word的尾节点
            node.isEnd = true;
        }

        /**
         * 时间复杂度O(|S|)，|S|是word的长度
         *
         * @param word
         * @return
         */
        public boolean search(String word) {
            if (word == null || word.length() == 0) {
                return false;
            }

            TrieNode node = root;

            for (char c : word.toCharArray()) {
                node = node.children.get(c);

                //前缀树中没有当前字符，例如：字符串"ab"，前缀树"ap"
                if (node == null) {
                    return false;
                }
            }

            //字符串匹配前缀树或者匹配前缀树的前半部分，通过node.isEnd判断是否匹配，例如：字符串"ap"，前缀树"app"
            return node.isEnd;
        }

        /**
         * 时间复杂度O(|S|)，|S|是word的长度
         *
         * @param prefix
         * @return
         */
        public boolean startsWith(String prefix) {
            if (prefix == null || prefix.length() == 0) {
                return false;
            }

            //得到根节点
            TrieNode node = root;

            for (char c : prefix.toCharArray()) {
                node = node.children.get(c);

                //前缀树中没有当前字符，例如：字符串"ab"，前缀树"ap"
                if (node == null) {
                    return false;
                }
            }

            //字符串匹配前缀树或者匹配前缀树的前半部分，例如：字符串"ap"，前缀树"app"
            return true;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            //当前节点的子节点map
            private final Map<Character, TrieNode> children;
            //当前节点是否是一个添加到前缀树的字符串的结尾节点
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                isEnd = false;
            }
        }
    }
}
