package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/9/28 09:25
 * @Author zsy
 * @Description 实现 Trie (前缀树) II 前缀树类比Problem14、Problem208、Problem211、Problem212、Problem336、Problem421、Problem676、Problem677、Problem720、Problem745、Problem820、Problem1166、Problem3043
 * 前缀树（trie ，发音为 "try"）是一个树状的数据结构，用于高效地存储和检索一系列字符串的前缀。
 * 前缀树有许多应用，如自动补全和拼写检查。
 * 实现前缀树 Trie 类：
 * Trie() 初始化前缀树对象。
 * void insert(String word) 将字符串 word 插入前缀树中。
 * int countWordsEqualTo(String word) 返回前缀树中字符串 word 的实例个数。
 * int countWordsStartingWith(String prefix) 返回前缀树中以 prefix 为前缀的字符串个数。
 * void erase(String word) 从前缀树中移除字符串 word 。
 * <p>
 * 输入
 * ["Trie", "insert", "insert", "countWordsEqualTo", "countWordsStartingWith", "erase", "countWordsEqualTo", "countWordsStartingWith", "erase", "countWordsStartingWith"]
 * [[], ["apple"], ["apple"], ["apple"], ["app"], ["apple"], ["apple"], ["app"], ["apple"], ["app"]]
 * 输出
 * [null, null, null, 2, 2, null, 1, 1, null, 0]
 * 解释
 * Trie trie = new Trie();
 * trie.insert("apple");               // 插入 "apple"。
 * trie.insert("apple");               // 插入另一个 "apple"。
 * trie.countWordsEqualTo("apple");    // 有两个 "apple" 实例，所以返回 2。
 * trie.countWordsStartingWith("app"); // "app" 是 "apple" 的前缀，所以返回 2。
 * trie.erase("apple");                // 移除一个 "apple"。
 * trie.countWordsEqualTo("apple");    // 现在只有一个 "apple" 实例，所以返回 1。
 * trie.countWordsStartingWith("app"); // 返回 1
 * trie.erase("apple");                // 移除 "apple"。现在前缀树是空的。
 * trie.countWordsStartingWith("app"); // 返回 0
 * <p>
 * 1 <= word.length, prefix.length <= 2000
 * word 和 prefix 只包含小写英文字母。
 * insert、 countWordsEqualTo、 countWordsStartingWith 和 erase 总共调用最多 3 * 10^4 次。
 * 保证每次调用 erase 时，字符串 word 总是存在于前缀树中。
 */
public class Problem1804 {
    public static void main(String[] args) {
        Trie trie = new Trie();
        // 插入 "apple"。
        trie.insert("apple");
        // 插入另一个 "apple"。
        trie.insert("apple");
        // 有两个 "apple" 实例，所以返回 2。
        System.out.println(trie.countWordsEqualTo("apple"));
        // "app" 是 "apple" 的前缀，所以返回 2。
        System.out.println(trie.countWordsStartingWith("app"));
        // 移除一个 "apple"。
        trie.erase("apple");
        // 现在只有一个 "apple" 实例，所以返回 1。
        System.out.println(trie.countWordsEqualTo("apple"));
        // 返回 1
        System.out.println(trie.countWordsStartingWith("app"));
        // 移除 "apple"。现在前缀树是空的。
        trie.erase("apple");
        // 返回 0
        System.out.println(trie.countWordsStartingWith("app"));
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
                //添加一个字符，根节点到当前节点作为前缀的字符串个数加1
                node.preCount++;
            }

            //字符串遍历结束，根节点到当前节点的字符串个数加1
            node.count++;
            node.isEnd = true;
        }

        public int countWordsEqualTo(String word) {
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                node = node.children.get(c);

                if (node == null) {
                    return 0;
                }
            }

            return node.count;
        }

        public int countWordsStartingWith(String prefix) {
            TrieNode node = root;

            for (char c : prefix.toCharArray()) {
                node = node.children.get(c);

                if (node == null) {
                    return 0;
                }
            }

            return node.preCount;
        }

        public void erase(String word) {
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                //node的子节点，避免删除node的子节点之后无法通过map继续往下删除
                TrieNode temp = node.children.get(c);

                //temp为空，则说明前缀树中不存在word，直接返回
                if (temp == null) {
                    return;
                }

                //根节点到temp节点的字符串个数为1，才能移除temp节点
                if (temp.count == 1) {
                    node.children.remove(c);
                }

                node = temp;

                //根节点到当前节点作为前缀的字符串个数减1
                node.preCount--;
            }

            //根节点到当前节点的字符串个数减1
            node.count--;

            //根节点到当前节点的字符串个数为0，则根节点到当前节点不是前缀树的字符串，isEnd设置为false
            if (node.count == 0) {
                node.isEnd = false;
            }
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            //当前节点的子节点map
            private final Map<Character, TrieNode> children;
            //根节点到当前节点的字符串个数
            private int count;
            //根节点到当前节点作为前缀的字符串个数
            private int preCount;
            //当前节点是否是一个添加到前缀树的字符串的结尾节点
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                isEnd = false;
            }
        }
    }
}
