package com.zhang.java;

/**
 * @Date 2022/5/14 8:48
 * @Author zsy
 * @Description 实现 Trie (前缀树) 腾讯、字节面试题 类比Problem14
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
        /**
         * 当前前缀树节点的子节点
         */
        private final Trie[] children;

        /**
         * 当前前缀树节点是否是结尾
         */
        private boolean isEnd;

        public Trie() {
            //一共就26个小写英文字母
            children = new Trie[26];
            isEnd = false;
        }

        /**
         * 时间复杂度O(|S|)，|S|是word的长度
         *
         * @param word
         */
        public void insert(String word) {
            //当前节点为根节点
            Trie node = this;
            char[] c = word.toCharArray();

            for (int i = 0; i < c.length; i++) {
                if (node.children[c[i] - 'a'] == null) {
                    node.children[c[i] - 'a'] = new Trie();
                }
                node = node.children[c[i] - 'a'];
            }

            //最后一个节点作为尾节点
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

            //当前节点为根节点
            Trie node = this;
            char[] c = word.toCharArray();

            for (int i = 0; i < c.length; i++) {
                //前缀树遍历到结尾，但字符串还没有遍历到结尾，例如：字符串"app"，前缀树"ap"
                if (node.children[c[i] - 'a'] == null) {
                    return false;
                }

                node = node.children[c[i] - 'a'];
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

            //当前节点为根节点
            Trie node = this;
            char[] c = prefix.toCharArray();

            for (int i = 0; i < c.length; i++) {
                //前缀树遍历到结尾，但字符串还没有遍历到结尾，例如：字符串"app"，前缀树"ap"
                if (node.children[c[i] - 'a'] == null) {
                    return false;
                }

                node = node.children[c[i] - 'a'];
            }

            //字符串匹配前缀树或者匹配前缀树的前半部分，例如：字符串"ap"，前缀树"app"
            return true;
        }
    }
}
