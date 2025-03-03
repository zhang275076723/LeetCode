package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2024/5/28 09:05
 * @Author zsy
 * @Description 驼峰式匹配 类比Problem392、Problem522、Problem524、Problem792 前缀树类比
 * 给你一个字符串数组 queries，和一个表示模式的字符串 pattern，请你返回一个布尔数组 answer 。
 * 只有在待查项 queries[i] 与模式串 pattern 匹配时， answer[i] 才为 true，否则为 false。
 * 如果可以将小写字母插入模式串 pattern 得到待查询项 query，那么待查询项与给定模式串匹配。
 * 可以在任何位置插入每个字符，也可以不插入字符。
 * <p>
 * 输入：queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FB"
 * 输出：[true,false,true,true,false]
 * 示例：
 * "FooBar" 可以这样生成："F" + "oo" + "B" + "ar"。
 * "FootBall" 可以这样生成："F" + "oot" + "B" + "all".
 * "FrameBuffer" 可以这样生成："F" + "rame" + "B" + "uffer".
 * <p>
 * 输入：queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FoBa"
 * 输出：[true,false,true,false,false]
 * 解释：
 * "FooBar" 可以这样生成："Fo" + "o" + "Ba" + "r".
 * "FootBall" 可以这样生成："Fo" + "ot" + "Ba" + "ll".
 * <p>
 * 输入：queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FoBaT"
 * 输出：[false,true,false,false,false]
 * 解释：
 * "FooBarTest" 可以这样生成："Fo" + "o" + "Ba" + "r" + "T" + "est".
 * <p>
 * 1 <= pattern.length, queries.length <= 100
 * 1 <= queries[i].length <= 100
 * queries[i] 和 pattern 由英文字母组成
 */
public class Problem1023 {
    public static void main(String[] args) {
        Problem1023 problem1023 = new Problem1023();
        String[] queries = {"FooBar", "FooBarTest", "FootBall", "FrameBuffer", "ForceFeedBack"};
        String pattern = "FB";
        System.out.println(problem1023.camelMatch(queries, pattern));
        System.out.println(problem1023.camelMatch2(queries, pattern));
    }

    /**
     * 双指针
     * 遍历queries中字符串word，如果word[i]==pattern[j]，则j指针右移；
     * 如果word[i]!=pattern[j]，并且word[i]为小写字母，则i指针右移；
     * 如果word[i]!=pattern[j]，并且word[i]为大写字母，则word和pattern不匹配，
     * 时间复杂度O(mn)，空间复杂度O(1) (m=queries.length，m=queries[i].length()，p=pattern.length())
     *
     * @param queries
     * @param pattern
     * @return
     */
    public List<Boolean> camelMatch(String[] queries, String pattern) {
        List<Boolean> list = new ArrayList<>();

        for (String word : queries) {
            //word和pattern是否匹配标志位
            boolean flag = true;
            //字符串pattern的下标索引
            int j = 0;

            for (int i = 0; i < word.length(); i++) {
                //word[i]==pattern[j]，则j指针右移
                if (j < pattern.length() && word.charAt(i) == pattern.charAt(j)) {
                    j++;
                } else if ('A' <= word.charAt(i) && word.charAt(i) <= 'Z') {
                    //word[i]!=pattern[j]，并且word[i]为大写字母，则word和pattern不匹配，直接跳出循环
                    flag = false;
                    break;
                }
            }

            //j没有遍历到字符串pattern末尾，则word和pattern不匹配
            if (j < pattern.length()) {
                flag = false;
            }

            list.add(flag);
        }

        return list;
    }

    /**
     * 前缀树
     * pattern加入前缀树中，查询word是否和前缀树中pattern匹配
     * 时间复杂度O(p+mn)，空间复杂度O(p) (m=queries.length，m=queries[i].length()，p=pattern.length())
     *
     * @param queries
     * @param pattern
     * @return
     */
    public List<Boolean> camelMatch2(String[] queries, String pattern) {
        Trie trie = new Trie();
        trie.insert(pattern);

        List<Boolean> list = new ArrayList<>();

        for (String word : queries) {
            list.add(trie.search(word));
        }

        return list;
    }

    /**
     * 前缀树
     */
    private static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String pattern) {
            TrieNode node = root;

            for (char c : pattern.toCharArray()) {
                if (!node.children.containsKey(c)) {
                    node.children.put(c, new TrieNode());
                }

                node = node.children.get(c);
            }

            node.isEnd = true;
        }

        /**
         * 查询word是否和前缀树中pattern匹配
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param word
         * @return
         */
        public boolean search(String word) {
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                //当前节点子节点存在字符c，则继续往子节点查找
                if (node.children.containsKey(c)) {
                    node = node.children.get(c);
                } else {
                    //当前节点子节点不存在字符c，并且字符c为大写字母，则word和pattern不匹配，返回false
                    if ('A' <= c && c <= 'Z') {
                        return false;
                    }
                }
            }

            //word遍历结束，则返回前缀树中pattern是否遍历结束
            return node.isEnd;
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
