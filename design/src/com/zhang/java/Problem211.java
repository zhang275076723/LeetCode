package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/10/28 11:05
 * @Author zsy
 * @Description 添加与搜索单词 - 数据结构设计 类比Problem14、Problem208、Problem212
 * 请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配 。
 * 实现词典类 WordDictionary ：
 * WordDictionary() 初始化词典对象
 * void addWord(word) 将 word 添加到数据结构中，之后可以对它进行匹配
 * bool search(word) 如果数据结构中存在字符串与 word 匹配，则返回 true ；否则，返回  false 。
 * word 中可能包含一些 '.' ，每个 . 都可以表示任何一个字母。
 * <p>
 * 输入：
 * ["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
 * [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
 * 输出：[null,null,null,null,false,true,true,true]
 * 解释：
 * WordDictionary wordDictionary = new WordDictionary();
 * wordDictionary.addWord("bad");
 * wordDictionary.addWord("dad");
 * wordDictionary.addWord("mad");
 * wordDictionary.search("pad"); // 返回 False
 * wordDictionary.search("bad"); // 返回 True
 * wordDictionary.search(".ad"); // 返回 True
 * wordDictionary.search("b.."); // 返回 True
 * <p>
 * 1 <= word.length <= 25
 * addWord 中的 word 由小写英文字母组成
 * search 中的 word 由 '.' 或小写英文字母组成
 * 最多调用 10^4 次 addWord 和 search
 */
public class Problem211 {
    public static void main(String[] args) {
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("bad");
        wordDictionary.addWord("dad");
        wordDictionary.addWord("mad");
        System.out.println(wordDictionary.search("pad"));
        System.out.println(wordDictionary.search("bad"));
        System.out.println(wordDictionary.search(".ad"));
        System.out.println(wordDictionary.search("b.."));
    }

    /**
     * 前缀树
     */
    static class WordDictionary {
        private final TrieNode root;

        public WordDictionary() {
            root = new TrieNode();
        }

        public void addWord(String word) {
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
         * 带有通配符的字符串查询
         *
         * @param word
         * @return
         */
        public boolean search(String word) {
            return backtrack(0, word, root);
        }

        private boolean backtrack(int t, String word, TrieNode node) {
            //遍历到单词末尾，判断当前节点是否是前缀树中一个单词的结尾
            if (t == word.length()) {
                return node.isEnd;
            }

            char c = word.charAt(t);

            //当前字符为'.'，遍历当前节点所包含的所有字符
            if (c == '.') {
                for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                    if (backtrack(t + 1, word, entry.getValue())) {
                        return true;
                    }
                }

                return false;
            } else {
                //当前字符为普通字符，从当前节点继续遍历
                if (node.children.containsKey(c)) {
                    return backtrack(t + 1, word, node.children.get(c));
                }

                return false;
            }
        }

        private static class TrieNode {
            private final Map<Character, TrieNode> children;

            private boolean isEnd;

            TrieNode() {
                children = new HashMap<>();
                isEnd = false;
            }
        }
    }
}
