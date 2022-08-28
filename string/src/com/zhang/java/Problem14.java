package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/7/1 9:24
 * @Author zsy
 * @Description 最长公共前缀 类比Problem208、Problem212
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * <p>
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 * <p>
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] 仅由小写英文字母组成
 */
public class Problem14 {
    public static void main(String[] args) {
        Problem14 problem14 = new Problem14();
        String[] strs = {"flower", "flow", "flight"};
        System.out.println(problem14.longestCommonPrefix(strs));
        System.out.println(problem14.longestCommonPrefix2(strs));
        System.out.println(problem14.longestCommonPrefix3(strs));
    }

    /**
     * 横向比较，从两个字符串找出其最长公共前缀，将当前最长公共前缀与第三个字符串找最长公共前缀，直至遍历结束
     * 时间复杂度O(m*n)，空间复杂度O(n) (m = strs.length, n = min(strs[i]))
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        if (strs.length == 1) {
            return strs[0];
        }

        String s = findPrefix(strs[0], strs[1]);

        for (int i = 2; i < strs.length; i++) {
            s = findPrefix(s, strs[i]);
        }

        return s;
    }

    /**
     * 纵向比较，判断所有字符串的每一个下标索引对应的值是否相等
     * 时间复杂度O(m*n)，空间复杂度O(1) (m = strs.length, n = min(strs[i]))
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        if (strs.length == 1) {
            return strs[0];
        }

        int index = 0;
        int minLength = strs[0].length();

        for (int i = 1; i < strs.length; i++) {
            minLength = Math.min(minLength, strs[i].length());
        }

        while (index < minLength) {
            char c = strs[0].charAt(index);

            for (int i = 1; i < strs.length; i++) {
                if (c != strs[i].charAt(index)) {
                    return strs[0].substring(0, index);
                }
            }

            index++;
        }

        return strs[0].substring(0, index);
    }

    /**
     * 前缀树，第一个分叉之前即为最长公共前缀
     * 时间复杂度O(mn)，空间复杂度O(mn) (m = strs.length, n = min(strs[i]))
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix3(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        if (strs.length == 1) {
            return strs[0];
        }

        Trie trie = new Trie();

        for (int i = 0; i < strs.length; i++) {
            trie.insert(strs[i]);
        }

        int index = 0;
        Trie.TrieNode node = trie.root;

        //找前缀树中第一个分叉之前的所有元素，即为最长公共前缀
        //当前节点必须不是尾节点，保证空串""也能够找到最长公共前缀
        while (node.children.size() == 1 && !node.isEnd) {
            node = node.children.get(strs[0].charAt(index));
            index++;
        }

        return strs[0].substring(0, index);
    }

    /**
     * str1和str2的最长公共前缀
     *
     * @param str1
     * @param str2
     * @return
     */
    private String findPrefix(String str1, String str2) {
        int index = 0;

        while (index < str1.length() && index < str2.length() && str1.charAt(index) == str2.charAt(index)) {
            index++;
        }

        return str1.substring(0, index);
    }

    /**
     * 前缀树
     */
    private static class Trie {
        /**
         * 前缀树根节点
         */
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        /**
         * 前缀树插入字符串
         * 时间复杂度O(str.length())
         *
         * @param str
         */
        public void insert(String str) {
            //得到根节点
            TrieNode node = root;

            for (char c : str.toCharArray()) {
                if (node.children.get(c) == null) {
                    node.children.put(c, new TrieNode());
                }

                node = node.children.get(c);
            }

            node.isEnd = true;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            /**
             * 当前前缀树节点的子节点
             */
            private final Map<Character, TrieNode> children;

            /**
             * 当前前缀树节点是否是尾节点，即从根到当前节点是否是一个字符串
             */
            private boolean isEnd;

            TrieNode() {
                this.children = new HashMap<>();
                this.isEnd = false;
            }
        }
    }
}
