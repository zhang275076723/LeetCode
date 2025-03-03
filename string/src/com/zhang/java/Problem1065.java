package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/25 08:45
 * @Author zsy
 * @Description 字符串的索引对 类比Problem616、Problem758 前缀树类比
 * 给出 字符串 text 和 字符串列表 words,
 * 返回所有的索引对 [i, j] 使得在索引对范围内的子字符串 text[i]...text[j]（包括 i 和 j）属于字符串列表 words。
 * <p>
 * 输入: text = "thestoryofleetcodeandme", words = ["story","fleet","leetcode"]
 * 输出: [[3,7],[9,13],[10,17]]
 * <p>
 * 输入: text = "ababa", words = ["aba","ab"]
 * 输出: [[0,1],[0,2],[2,3],[2,4]]
 * 解释:
 * 注意，返回的配对可以有交叉，比如，"aba" 既在 [0,2] 中也在 [2,4] 中
 * <p>
 * 所有字符串都只包含小写字母。
 * 保证 words 中的字符串无重复。
 * 1 <= text.length <= 100
 * 1 <= words.length <= 20
 * 1 <= words[i].length <= 50
 * 按序返回索引对 [i,j]（即，按照索引对的第一个索引进行排序，当第一个索引对相同时按照第二个索引对排序）。
 */
public class Problem1065 {
    public static void main(String[] args) {
        Problem1065 problem1065 = new Problem1065();
//        String text = "thestoryofleetcodeandme";
//        String[] words = {"story","fleet","leetcode"};
        String text = "ababa";
        String[] words = {"aba", "ab"};
        System.out.println(Arrays.deepToString(problem1065.indexPairs(text, words)));
        System.out.println(Arrays.deepToString(problem1065.indexPairs2(text, words)));
    }

    /**
     * 暴力
     * 时间复杂度O(mp+n^3)，空间复杂度O(mp+n^2) (n=text.length()，m=words.length，p=words[i].length())
     *
     * @param text
     * @param words
     * @return
     */
    public int[][] indexPairs(String text, String[] words) {
        //words中单词集合
        Set<String> wordSet = new HashSet<>();

        for (String word : words) {
            wordSet.add(word);
        }

        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            StringBuilder sb = new StringBuilder();

            for (int j = i; j < text.length(); j++) {
                sb.append(text.charAt(j));

                if (wordSet.contains(sb.toString())) {
                    list.add(new int[]{i, j});
                }
            }
        }

        return list.toArray(new int[list.size()][2]);
    }

    /**
     * 前缀树
     * 时间复杂度O(mp+n^2)，空间复杂度O(mp+n^2) (n=text.length()，m=words.length，p=words[i].length())
     *
     * @param text
     * @param words
     * @return
     */
    public int[][] indexPairs2(String text, String[] words) {
        Trie trie = new Trie();

        for (String word : words) {
            trie.insert(word);
        }

        return trie.search(text);
    }

    /**
     * 前缀树
     */
    private static class Trie {
        public final TrieNode root;

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

        /**
         * 遍历s，返回s中属于words的下标索引区间
         * 时间复杂度O(n^2)，空间复杂度O(n^2)
         *
         * @param s
         * @return
         */
        public int[][] search(String s) {
            List<int[]> list = new ArrayList<>();

            for (int i = 0; i < s.length(); i++) {
                TrieNode node = root;

                for (int j = i; j < s.length(); j++) {
                    char c = s.charAt(j);
                    node = node.children.get(c);

                    //node不存在子节点c，则从s[j]往后找不到属于words的字符串，直接跳出循环
                    if (node == null) {
                        break;
                    }

                    //s[i]-s[j]是words的字符串，加入list中
                    if (node.isEnd) {
                        list.add(new int[]{i, j});
                    }
                }
            }

            return list.toArray(new int[list.size()][2]);
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
