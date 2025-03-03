package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/5/29 08:42
 * @Author zsy
 * @Description 字符流 前缀树类比
 * 设计一个算法：接收一个字符流，并检查这些字符的后缀是否是字符串数组 words 中的一个字符串。
 * 例如，words = ["abc", "xyz"] 且字符流中逐个依次加入 4 个字符 'a'、'x'、'y' 和 'z' ，
 * 你所设计的算法应当可以检测到 "axyz" 的后缀 "xyz" 与 words 中的字符串 "xyz" 匹配。
 * 按下述要求实现 StreamChecker 类：
 * StreamChecker(String[] words) ：构造函数，用字符串数组 words 初始化数据结构。
 * boolean query(char letter)：从字符流中接收一个新字符，如果字符流中的任一非空后缀能匹配 words 中的某一字符串，返回 true ；
 * 否则，返回 false。
 * <p>
 * 输入：
 * ["StreamChecker", "query", "query", "query", "query", "query", "query", "query", "query", "query", "query", "query", "query"]
 * [[["cd", "f", "kl"]], ["a"], ["b"], ["c"], ["d"], ["e"], ["f"], ["g"], ["h"], ["i"], ["j"], ["k"], ["l"]]
 * 输出：
 * [null, false, false, false, true, false, true, false, false, false, false, false, true]
 * 解释：
 * StreamChecker streamChecker = new StreamChecker(["cd", "f", "kl"]);
 * streamChecker.query("a"); // 返回 False
 * streamChecker.query("b"); // 返回 False
 * streamChecker.query("c"); // 返回 False
 * streamChecker.query("d"); // 返回 True ，因为 'cd' 在 words 中
 * streamChecker.query("e"); // 返回 False
 * streamChecker.query("f"); // 返回 True ，因为 'f' 在 words 中
 * streamChecker.query("g"); // 返回 False
 * streamChecker.query("h"); // 返回 False
 * streamChecker.query("i"); // 返回 False
 * streamChecker.query("j"); // 返回 False
 * streamChecker.query("k"); // 返回 False
 * streamChecker.query("l"); // 返回 True ，因为 'kl' 在 words 中
 * <p>
 * 1 <= words.length <= 2000
 * 1 <= words[i].length <= 200
 * words[i] 由小写英文字母组成
 * letter 是一个小写英文字母
 * 最多调用查询 4 * 10^4 次
 */
public class Problem1032 {
    public static void main(String[] args) {
        String[] words = {"cd", "f", "kl"};
        StreamChecker streamChecker = new StreamChecker(words);
        // 返回 False
        System.out.println(streamChecker.query('a'));
        // 返回 False
        System.out.println(streamChecker.query('b'));
        // 返回 False
        System.out.println(streamChecker.query('c'));
        // 返回 True ，因为 'cd' 在 words 中
        System.out.println(streamChecker.query('d'));
        // 返回 False
        System.out.println(streamChecker.query('e'));
        // 返回 True ，因为 'f' 在 words 中
        System.out.println(streamChecker.query('f'));
        // 返回 False
        System.out.println(streamChecker.query('g'));
        // 返回 False
        System.out.println(streamChecker.query('h'));
        // 返回 False
        System.out.println(streamChecker.query('i'));
        // 返回 False
        System.out.println(streamChecker.query('j'));
        // 返回 False
        System.out.println(streamChecker.query('k'));
        // 返回 True ，因为 'kl' 在 words 中
        System.out.println(streamChecker.query('l'));
    }

    /**
     * 前缀树
     * words中单词的逆序加入前缀树中，每次查询前缀树中是否存在当前字符串的逆序前缀单词
     */
    static class StreamChecker {
        private final Trie trie;
        private final StringBuilder sb;

        public StreamChecker(String[] words) {
            trie = new Trie();
            sb = new StringBuilder();

            //words中单词的逆序加入前缀树中
            for (String word : words) {
                trie.reverseInsert(word);
            }
        }

        public boolean query(char letter) {
            sb.append(letter);

            //传入sb比传入字符串sb.toString()速度快
            return trie.searchSuffix(sb);
        }

        /**
         * 前缀树
         */
        private static class Trie {
            private final TrieNode root;

            public Trie() {
                root = new TrieNode();
            }

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
             * 查询前缀树中是否存在当前字符串sb的逆序前缀单词
             * 时间复杂度O(n)，空间复杂度O(1)
             *
             * @param sb
             * @return
             */
            public boolean searchSuffix(StringBuilder sb) {
                TrieNode node = root;

                for (int i = sb.length() - 1; i >= 0; i--) {
                    char c = sb.charAt(i);

                    //当前节点子节点存在字符c，则继续往子节点查找
                    if (node.children.containsKey(c)) {
                        node = node.children.get(c);
                    } else {
                        //当前节点子节点不存在字符c，则前缀树中不存在sb的逆序前缀单词，返回false
                        return false;
                    }

                    //前缀树中存在sb的逆序前缀单词，返回true
                    if (node.isEnd) {
                        return true;
                    }
                }

                //遍历结束，则前缀树中不存在sb的逆序前缀单词，返回false
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
}
