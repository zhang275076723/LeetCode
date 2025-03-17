package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/13 08:27
 * @Author zsy
 * @Description 连接词 类比Problem139、Problem140、Problem212、Problem2707 前缀树类比
 * 给你一个 不含重复 单词的字符串数组 words ，请你找出并返回 words 中的所有 连接词 。
 * 连接词 定义为：一个完全由给定数组中的至少两个较短单词（不一定是不同的两个单词）组成的字符串。
 * <p>
 * 输入：words = ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
 * 输出：["catsdogcats","dogcatsdog","ratcatdogcat"]
 * 解释："catsdogcats" 由 "cats", "dog" 和 "cats" 组成;
 * "dogcatsdog" 由 "dog", "cats" 和 "dog" 组成;
 * "ratcatdogcat" 由 "rat", "cat", "dog" 和 "cat" 组成。
 * <p>
 * 输入：words = ["cat","dog","catdog"]
 * 输出：["catdog"]
 * <p>
 * 1 <= words.length <= 10^4
 * 1 <= words[i].length <= 30
 * words[i] 仅由小写英文字母组成。
 * words 中的所有字符串都是 唯一 的。
 * 1 <= sum(words[i].length) <= 10^5
 */
public class Problem472 {
    public static void main(String[] args) {
        Problem472 problem472 = new Problem472();
        String[] words = {"cat", "cats", "catsdogcats", "dog", "dogcatsdog", "hippopotamuses", "rat", "ratcatdogcat"};
        System.out.println(problem472.findAllConcatenatedWordsInADict(words));
    }

    /**
     * 排序+前缀树+回溯+剪枝
     * words中单词按照长度由小到大排序，如果当前单词是连接词，则加入结果集合；
     * 如果不是连接词，则加入前缀树中，供后续比当前单词长度长的单词判断是否是连接词
     * 注意：如果当前单词是连接词，则不需要加入前缀树中，因为前缀树中存在的单词就已经可以构成当前单词
     * 时间复杂度O(nlogn+sum(words[i].length()))，空间复杂度O(logn+sum(words[i].length())) (快排的平均空间复杂度为O(logn))
     *
     * @param words
     * @return
     */
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> list = new ArrayList<>();
        Trie trie = new Trie();

        //words中单词按照长度由小到大排序，判断当前单词是否是连接词时，只需要考虑已经遍历过比当前单词长度短的单词，
        //保证当前单词如果是连接词，则至少由两个较短单词组成
        quickSort(words, 0, words.length - 1);

        for (String word : words) {
            //当前单词word是连接词，则加入结果集合
            //注意：如果当前单词是连接词，则不需要加入前缀树中，因为前缀树中存在的单词就已经可以构成当前单词
            if (trie.search(word)) {
                list.add(word);
            } else {
                //当前单词word不是连接词，则加入前缀树中，供后续比当前单词长度长的单词判断是否是连接词
                trie.insert(word);
            }
        }

        return list;
    }

    private void quickSort(String[] words, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(words, left, right);
        quickSort(words, left, pivot - 1);
        quickSort(words, pivot + 1, right);
    }

    private int partition(String[] words, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        String value = words[randomIndex];
        words[randomIndex] = words[left];
        words[left] = value;

        String temp = words[left];

        while (left < right) {
            while (left < right && words[right].length() >= temp.length()) {
                right--;
            }

            words[left] = words[right];

            while (left < right && words[left].length() <= temp.length()) {
                left++;
            }

            words[right] = words[left];
        }

        words[left] = temp;

        return left;
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
            return backtrack(0, word, new boolean[word.length()]);
        }

        /**
         * 判断当前单词word是否是连接词，即判断当前单词word能否由前缀树中至少两个单词组成
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param t
         * @param word
         * @param visited
         * @return
         */
        private boolean backtrack(int t, String word, boolean[] visited) {
            //遍历到单词末尾，则word是连接词，返回false
            if (t == word.length()) {
                return true;
            }

            //word[t]已访问，即word[0]-word[t]已经由前缀树中单词组成，word[t]-word[word.length()-1]已经遍历过，
            //word[t]-word[word.length()-1]不能由前缀树中单词组成，不需要继续后往遍历，直接返回false
            if (visited[t]) {
                return false;
            }

            //从前缀树根节点开始查找
            TrieNode node = root;

            for (int i = t; i < word.length(); i++) {
                char c = word.charAt(i);

                //前缀树中没有当前字符c，则word[t]-word[i]不能由前缀树中单词组成，返回false
                if (!node.children.containsKey(c)) {
                    return false;
                }

                node = node.children.get(c);

                //word[t]-word[i]由前缀树中单词组成，继续往后遍历
                if (node.isEnd) {
                    //设置word[i]已访问，即word[0]-word[i]已经由前缀树中单词组成
                    visited[i] = true;

                    if (backtrack(i + 1, word, visited)) {
                        return true;
                    }
                }
            }

            //遍历结束，word不是连接词，返回false
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
