package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/1/17 08:27
 * @Author zsy
 * @Description 实现一个魔法字典 类比Problem211、Problem472、Problem1233 前缀树类比Problem14、Problem208、Problem211、Problem212、Problem336、Problem421、Problem677、Problem720、Problem745、Problem820、Problem1166、Problem1804、Problem3043
 * 设计一个使用单词列表进行初始化的数据结构，单词列表中的单词 互不相同 。
 * 如果给出一个单词，请判定能否只将这个单词中一个字母换成另一个字母，使得所形成的新单词存在于你构建的字典中。
 * 实现 MagicDictionary 类：
 * MagicDictionary() 初始化对象
 * void buildDict(String[] dictionary) 使用字符串数组 dictionary 设定该数据结构，dictionary 中的字符串互不相同
 * bool search(String searchWord) 给定一个字符串 searchWord ，判定能否只将字符串中 一个 字母换成另一个字母，
 * 使得所形成的新字符串能够与字典中的任一字符串匹配。如果可以，返回 true ；否则，返回 false 。
 * <p>
 * 输入
 * ["MagicDictionary", "buildDict", "search", "search", "search", "search"]
 * [[], [["hello", "leetcode"]], ["hello"], ["hhllo"], ["hell"], ["leetcoded"]]
 * 输出
 * [null, null, false, true, false, false]
 * 解释
 * MagicDictionary magicDictionary = new MagicDictionary();
 * magicDictionary.buildDict(["hello", "leetcode"]);
 * magicDictionary.search("hello"); // 返回 False
 * magicDictionary.search("hhllo"); // 将第二个 'h' 替换为 'e' 可以匹配 "hello" ，所以返回 True
 * magicDictionary.search("hell"); // 返回 False
 * magicDictionary.search("leetcoded"); // 返回 False
 * <p>
 * 1 <= dictionary.length <= 100
 * 1 <= dictionary[i].length <= 100
 * dictionary[i] 仅由小写英文字母组成
 * dictionary 中的所有字符串 互不相同
 * 1 <= searchWord.length <= 100
 * searchWord 仅由小写英文字母组成
 * buildDict 仅在 search 之前调用一次
 * 最多调用 100 次 search
 */
public class Problem676 {
    public static void main(String[] args) {
        MagicDictionary magicDictionary = new MagicDictionary();
        magicDictionary.buildDict(new String[]{"hello", "leetcode"});
        // 返回 False
        System.out.println(magicDictionary.search("hello"));
        // 将第二个 'h' 替换为 'e' 可以匹配 "hello" ，所以返回 True
        System.out.println(magicDictionary.search("hhllo"));
        // 返回 False
        System.out.println(magicDictionary.search("he1ll"));
        // 返回 False
        System.out.println(magicDictionary.search("leetcoded"));

//        MagicDictionary magicDictionary = new MagicDictionary();
//        magicDictionary.buildDict(new String[]{"judge", "judgg"});
//        System.out.println(magicDictionary.search("juggg"));
    }

    /**
     * 前缀树+回溯+剪枝
     */
    static class MagicDictionary {
        private final Trie trie;

        public MagicDictionary() {
            trie = new Trie();
        }

        /**
         * 时间复杂度O(nl)，空间复杂度O(nl) (n=dictionary.length，l=dictionary[i].length())
         *
         * @param dictionary
         */
        public void buildDict(String[] dictionary) {
            for (String word : dictionary) {
                trie.insert(word);
            }
        }

        public boolean search(String searchWord) {
            return trie.search(searchWord);
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
                //初始化修改标志位为false
                return backtrack(0, false, root, word);
            }

            /**
             * 时间复杂度O(n|Σ|)，空间复杂度O(n) (n=word.length()，|Σ|=26，只包含小写英文字母)
             *
             * @param t
             * @param flag word中某一位是否修改标志位
             * @param node
             * @param word
             * @return
             */
            private boolean backtrack(int t, boolean flag, TrieNode node, String word) {
                //遍历到单词末尾，当前节点是前缀树中一个单词的结尾，并且word中某一位发生修改，则返回true
                if (t == word.length()) {
                    return node.isEnd && flag;
                }

                //当前字符word[t]
                char c = word.charAt(t);

                for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                    //当前字符c和前缀树中当前节点得子节点字符相等，则当前字符c不修改，继续往后遍历
                    if (c == entry.getKey()) {
                        if (backtrack(t + 1, flag, entry.getValue(), word)) {
                            return true;
                        }
                    } else {
                        //当前字符c和前缀树中当前节点的子节点字符不相等，则当前字符c必须修改，才能继续往后遍历
                        if (!flag && backtrack(t + 1, true, entry.getValue(), word)) {
                            return true;
                        }
                    }
                }

                //遍历结束，word中某一位发生修改也不是前缀树中的单词，返回false
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
