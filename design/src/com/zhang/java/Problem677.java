package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/4/30 08:06
 * @Author zsy
 * @Description 键值映射 前缀树类比Problem14、Problem208、Problem211、Problem212
 * 设计一个 map ，满足以下几点:
 * 字符串表示键，整数表示值
 * 返回具有前缀等于给定字符串的键的值的总和
 * 实现一个 MapSum 类：
 * MapSum() 初始化 MapSum 对象
 * void insert(String key, int val) 插入 key-val 键值对，字符串表示键 key ，整数表示值 val 。
 * 如果键 key 已经存在，那么原来的键值对 key-value 将被替代成新的键值对。
 * int sum(string prefix) 返回所有以该前缀 prefix 开头的键 key 的值的总和。
 * <p>
 * 输入：
 * ["MapSum", "insert", "sum", "insert", "sum"]
 * [[], ["apple", 3], ["ap"], ["app", 2], ["ap"]]
 * 输出：
 * [null, null, 3, null, 5]
 * 解释：
 * MapSum mapSum = new MapSum();
 * mapSum.insert("apple", 3);
 * mapSum.sum("ap");           // 返回 3 (apple = 3)
 * mapSum.insert("app", 2);
 * mapSum.sum("ap");           // 返回 5 (apple + app = 3 + 2 = 5)
 * <p>
 * 1 <= key.length, prefix.length <= 50
 * key 和 prefix 仅由小写英文字母组成
 * 1 <= val <= 1000
 * 最多调用 50 次 insert 和 sum
 */
public class Problem677 {
    public static void main(String[] args) {
        MapSum mapSum = new MapSum();
        mapSum.insert("apple", 3);
        // 返回 3 (apple = 3)
        System.out.println(mapSum.sum("ap"));
        mapSum.insert("app", 2);
        // 返回 5 (apple + app = 3 + 2 = 5)
        System.out.println(mapSum.sum("ap"));
    }

    /**
     * 前缀树
     */
    static class MapSum {
        //前缀树
        private final Trie trie;
        //字符串key和整数value的映射map
        private final Map<String, Integer> map;

        public MapSum() {
            trie = new Trie();
            map = new HashMap<>();
        }

        /**
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param key
         * @param val
         */
        public void insert(String key, int val) {
            //字符串中每个节点需要加上的值
            int addValue = val - map.getOrDefault(key, 0);
            trie.insert(key, addValue);
            map.put(key, val);
        }

        /**
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param prefix
         * @return
         */
        public int sum(String prefix) {
            //找到当前字符串的最后一个节点
            Trie.TrieNode trieNode = trie.search(prefix);
            return trieNode == null ? 0 : trieNode.sum;
        }

        private static class Trie {
            private final TrieNode root;

            public Trie() {
                root = new TrieNode();
            }

            /**
             * 当前字符串加入前缀树，字符串中每个节点值加上val
             *
             * @param word
             * @param val
             */
            public void insert(String word, int val) {
                TrieNode node = root;

                for (char c : word.toCharArray()) {
                    if (!node.children.containsKey(c)) {
                        node.children.put(c, new TrieNode());
                    }

                    node = node.children.get(c);
                    node.sum = node.sum + val;
                }

                node.isEnd = true;
            }

            /**
             * 找到当前字符串的最后一个节点，如果不存在返回null
             *
             * @param word
             * @return
             */
            public TrieNode search(String word) {
                TrieNode node = root;

                for (char c : word.toCharArray()) {
                    if (!node.children.containsKey(c)) {
                        return null;
                    }

                    node = node.children.get(c);
                }

                return node;
            }

            private static class TrieNode {
                //当前节点的子节点
                private final Map<Character, TrieNode> children;
                //根节点到当前节点作为前缀，前缀树中满足当前前缀的字符串值之和
                private int sum;
                //当前节点是否是一个单词的结尾
                private boolean isEnd;

                public TrieNode() {
                    children = new HashMap<>();
                    sum = 0;
                    isEnd = false;
                }
            }
        }
    }
}
