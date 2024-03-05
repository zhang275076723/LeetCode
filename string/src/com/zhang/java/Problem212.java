package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/8/28 11:58
 * @Author zsy
 * @Description 单词搜索 II dfs和bfs类比Problem79、Problem130、Problem200、Problem463、Problem490、Problem499、Problem505、Problem529、Problem547、Problem694、Problem695、Problem711、Problem733、Problem827、Problem994、Problem1034、Problem1162、Problem1254、Problem1568、Problem1905、Offer12 前缀树类比Problem14、Problem208、Problem211、Problem336、Problem421、Problem676、Problem677、Problem720、Problem745、Problem1804、Problem3043
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
     * 时间复杂度O(kmn*(4^l))，空间复杂度O(mn+kl)
     * (m = board.length, n = board[0].length, l = word.length(), k = words.length)
     *
     * @param board
     * @param words
     * @return
     */
    public List<String> findWords(char[][] board, String[] words) {
        List<String> list = new ArrayList<>();

        for (String word : words) {
            //当前单词word是否找到标志
            boolean flag = false;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    flag = backtrack(0, i, j, new boolean[board.length][board[0].length], board, word);
                    //当前已经找到单词word，则加入list中，进行下个单词的搜索，相当于剪枝
                    if (flag) {
                        list.add(word);
                        break;
                    }
                }
                //跳出循环，进行下个单词的搜索，相当于剪枝
                if (flag) {
                    break;
                }
            }
        }

        return list;
    }

    /**
     * 回溯+剪枝+前缀树预处理
     * 将words中单词加入到前缀树中，每次遍历都判断当前路径是否是前缀树中单词的前缀，
     * 如果是，则继续遍历；如果不是，则不可能遍历到前缀树中的单词，直接剪枝
     * 时间复杂度O(mn*(4^l))，空间复杂度O(mn+kl)
     * (m = board.length, n = board[0].length, l = word.length(), k = words.length)
     *
     * @param board
     * @param words
     * @return
     */
    public List<String> findWords2(char[][] board, String[] words) {
        //前缀树
        Trie trie = new Trie();

        //words中单词加入前缀树
        for (String word : words) {
            trie.insert(word);
        }

        List<String> list = new ArrayList<>();
        //用于去重，当找到满足要求的word时，加入set，之后再次遍历到set中存在的相同word时不加入set，相当于剪枝
        Set<String> set = new HashSet<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                backtrack2(i, j, trie.root, board, new StringBuilder(),
                        new boolean[board.length][board[0].length], list, set);
            }
        }

        return list;
    }

    private boolean backtrack(int t, int i, int j, boolean[][] visited, char[][] board, String word) {
        //遍历到末尾，返回true
        if (t == word.length()) {
            return true;
        }

        //当前位置越界，或当前位置已经被访问，或当前位置字符和word中第t个字符不同时，直接剪枝，返回false
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length ||
                visited[i][j] || word.charAt(t) != board[i][j]) {
            return false;
        }

        visited[i][j] = true;

        //往上下左右找
        boolean flag = backtrack(t + 1, i - 1, j, visited, board, word) ||
                backtrack(t + 1, i + 1, j, visited, board, word) ||
                backtrack(t + 1, i, j - 1, visited, board, word) ||
                backtrack(t + 1, i, j + 1, visited, board, word);

        visited[i][j] = false;

        return flag;
    }

    private void backtrack2(int i, int j, Trie.TrieNode node, char[][] board, StringBuilder sb,
                            boolean[][] visited, List<String> list, Set<String> set) {
        //当前位置越界，或当前位置已经被访问时，直接返回
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j]) {
            return;
        }

        node = node.children[board[i][j] - 'a'];

        //当前节点不是前缀树节点，则sb中保存的当前单词不是前缀树中单词的前缀，直接返回，相当于剪枝
        if (node == null) {
            return;
        }

        visited[i][j] = true;
        sb.append(board[i][j]);

        //sb中保存的当前单词是前缀树中的一个单词，并且当前单词第一次被遍历到，才加入结果集合
        //注意：找到一个单词后，不能return，需要接着当前单词继续往后寻找
        if (node.isEnd && !set.contains(sb.toString())) {
            list.add(sb.toString());
            //加入set中，避免list中出现重复单词，用于去重
            set.add(sb.toString());
        }

        //往上下左右找
        backtrack2(i - 1, j, node, board, sb, visited, list, set);
        backtrack2(i + 1, j, node, board, sb, visited, list, set);
        backtrack2(i, j - 1, node, board, sb, visited, list, set);
        backtrack2(i, j + 1, node, board, sb, visited, list, set);

        sb.delete(sb.length() - 1, sb.length());
        visited[i][j] = false;
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

        public void insert(String word) {
            //得到根节点
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }

                node = node.children[c - 'a'];
            }

            //最后一个节点作为单词尾节点
            node.isEnd = true;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            //当前节点的子节点数组
            private final TrieNode[] children;
            //当前节点是否是一个添加到前缀树的字符串的结尾节点
            private boolean isEnd;

            public TrieNode() {
                //一共就26个小写英文字母
                children = new TrieNode[26];
                isEnd = false;
            }
        }
    }
}