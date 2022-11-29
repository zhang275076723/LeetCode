package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/8/28 11:58
 * @Author zsy
 * @Description 单词搜索 II 类比Problem79、Problem200、Offer12 前缀树类比Problem14、Problem208、Problem211
 * 给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words， 返回所有二维网格上的单词 。
 * 单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
 * 同一个单元格内的字母在一个单词中不允许被重复使用。
 * <p>
 * 输入：board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]],
 * words = ["oath","pea","eat","rain"]
 * 输出：["eat","oath"]
 * <p>
 * 输入：board = [["a","b"],["c","d"]], words = ["abcb"]
 * 输出：[]
 * <p>
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 12
 * board[i][j] 是一个小写英文字母
 * 1 <= words.length <= 3 * 10^4
 * 1 <= words[i].length <= 10
 * words[i] 由小写英文字母组成
 * words 中的所有字符串互不相同
 */
public class Problem212 {
    public static void main(String[] args) {
        Problem212 problem212 = new Problem212();
//        char[][] board = {
//                {'o', 'a', 'a', 'n'},
//                {'e', 't', 'a', 'e'},
//                {'i', 'h', 'k', 'r'},
//                {'i', 'f', 'l', 'v'}
//        };
//        String[] words = {"oath", "pea", "eat", "rain"};
        char[][] board = {
                {'o', 'a', 'b', 'n'},
                {'o', 't', 'a', 'e'},
                {'a', 'h', 'k', 'r'},
                {'a', 'f', 'l', 'v'}
        };
        String[] words = {"oa", "oaa"};
        System.out.println(problem212.findWords(board, words));
        System.out.println(problem212.findWords2(board, words));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(mn*(4^l)*words.length)，空间复杂度O(words.length*l)
     * (m = board.length, n = board[0].length, l = word.length())
     *
     * @param board
     * @param words
     * @return
     */
    public List<String> findWords(char[][] board, String[] words) {
        List<String> list = new ArrayList<>();

        for (String word : words) {
            //用于去重，当找到满足要求的word时，直接返回，相当于剪枝
            Set<String> set = new HashSet<>();

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    backtrack(0, i, j, board, word,
                            new boolean[board.length][board[0].length], set, list);
                }
            }
        }

        return list;
    }

    /**
     * 回溯+剪枝+前缀树预处理
     * 每次遍历都判断当前路径是否是words中任意单词的前缀，如果不是，则剪枝
     * 时间复杂度O(mn*(4^l)*words.length)，空间复杂度O(words.length*l)
     * (m = board.length, n = board[0].length, l = word.length())
     *
     * @param board
     * @param words
     * @return
     */
    public List<String> findWords2(char[][] board, String[] words) {
        List<String> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        Trie trie = new Trie();

        for (String word : words) {
            set.add(word);
            trie.insert(word);
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                backtrack2(i, j, board, new StringBuilder(), trie.root,
                        new boolean[board.length][board[0].length], set, list);
            }
        }

        return list;
    }

    private void backtrack(int t, int i, int j, char[][] board, String word,
                           boolean[][] visited, Set<String> set, List<String> list) {
        //用于去重，当找到word时，直接返回，相当于剪枝
        if (set.contains(word)) {
            return;
        }

        if (t == word.length()) {
            set.add(word);
            list.add(word);
            return;
        }

        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length ||
                visited[i][j] || word.charAt(t) != board[i][j]) {
            return;
        }

        visited[i][j] = true;

        //往上下左右找
        backtrack(t + 1, i - 1, j, board, word, visited, set, list);
        backtrack(t + 1, i + 1, j, board, word, visited, set, list);
        backtrack(t + 1, i, j - 1, board, word, visited, set, list);
        backtrack(t + 1, i, j + 1, board, word, visited, set, list);

        visited[i][j] = false;
    }

    private void backtrack2(int i, int j, char[][] board, StringBuilder sb,
                            Trie.TrieNode node, boolean[][] visited, Set<String> set, List<String> list) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j]) {
            return;
        }

        node = node.children[board[i][j] - 'a'];

        //当前节点不是前缀树节点，说明当前字符不是word中字符，直接返回，相当于剪枝
        if (node == null) {
            return;
        }

        visited[i][j] = true;
        sb.append(board[i][j]);

        //当前单词是前缀树中的一个单词，且当前单词第一次被找到
        //注意：找到一个单词后，不能return，需要继续寻找
        if (node.isEnd && set.contains(sb.toString())) {
            list.add(sb.toString());
            set.remove(sb.toString());
        }

        //往上下左右找
        backtrack2(i - 1, j, board, sb, node, visited, set, list);
        backtrack2(i + 1, j, board, sb, node, visited, set, list);
        backtrack2(i, j - 1, board, sb, node, visited, set, list);
        backtrack2(i, j + 1, board, sb, node, visited, set, list);

        sb.delete(sb.length() - 1, sb.length());
        visited[i][j] = false;
    }

    private static class Trie {
        /**
         * 前缀树根节点
         */
        private final TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            //得到根节点
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }
                node = node.children[c - 'a'];
            }

            //最后一个节点作为尾节点
            node.isEnd = true;
        }

        private static class TrieNode {
            /**
             * 当前前缀树节点的子节点
             */
            private final TrieNode[] children;

            /**
             * 当前前缀树节点是否是一个单词的结尾
             */
            private boolean isEnd;

            TrieNode() {
                //一共就26个小写英文字母
                children = new TrieNode[26];
                isEnd = false;
            }
        }
    }
}