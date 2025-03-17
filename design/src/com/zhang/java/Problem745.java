package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/2/20 08:46
 * @Author zsy
 * @Description 前缀和后缀搜索 前缀树类比Problem14、Problem208、Problem211、Problem212、Problem336、Problem421、Problem676、Problem677、Problem720、Problem820、Problem1166、Problem1804、Problem3043
 * 设计一个包含一些单词的特殊词典，并能够通过前缀和后缀来检索单词。
 * 实现 WordFilter 类：
 * WordFilter(string[] words) 使用词典中的单词 words 初始化对象。
 * f(string pref, string suff) 返回词典中具有前缀 prefix 和后缀 suff 的单词的下标。
 * 如果存在不止一个满足要求的下标，返回其中 最大的下标 。
 * 如果不存在这样的单词，返回 -1 。
 * <p>
 * 输入
 * ["WordFilter", "f"]
 * [[["apple"]], ["a", "e"]]
 * 输出
 * [null, 0]
 * 解释
 * WordFilter wordFilter = new WordFilter(["apple"]);
 * wordFilter.f("a", "e"); // 返回 0 ，因为下标为 0 的单词：前缀 prefix = "a" 且 后缀 suff = "e" 。
 * <p>
 * 1 <= words.length <= 10^4
 * 1 <= words[i].length <= 7
 * 1 <= pref.length, suff.length <= 7
 * words[i]、pref 和 suff 仅由小写英文字母组成
 * 最多对函数 f 执行 10^4 次调用
 */
public class Problem745 {
    public static void main(String[] args) {
        WordFilter wordFilter = new WordFilter(new String[]{"apple"});
        // 返回 0 ，因为下标为 0 的单词：前缀 prefix = "a" 且 后缀 suff = "e" 。
        System.out.println(wordFilter.f("a", "e"));
    }

    /**
     * 双前缀树
     */
    static class WordFilter {
        //存储words[i]的前缀树
        private final Trie trie1;
        //存储words[i]逆序的前缀树
        private final Trie trie2;

        /**
         * words[i]插入trie1中，words[i]的逆序字符串插入trie2中
         * 时间复杂度O(n*m)，空间复杂度O(n*m) (n=words.length，m=words[i].length())
         *
         * @param words
         */
        public WordFilter(String[] words) {
            trie1 = new Trie();
            trie2 = new Trie();

            //words[i]插入trie1中
            for (int i = 0; i < words.length; i++) {
                trie1.insert(words[i], i);
            }

            //words[i]的逆序字符串插入trie2中
            for (int i = 0; i < words.length; i++) {
                trie2.reverseInsert(words[i], i);
            }
        }

        /**
         * trie1中查询满足prefix的words[i]的下标索引，trie2中查询满足suffix逆序的words[i]的下标索引，
         * list1和list2中从后往前遍历，第一个相等的单词即为同时满足prefix和suffix，并且words[i]下标索引最大的单词
         * 注意：插入words[i]的前缀树对应insert()和searchPrefix()，插入words[i]的逆序字符串的前缀树对应reverseInsert()和searchSuffix()
         * 时间复杂度O(prefix.length()+suffix.length()+n+m)，空间复杂度O(n) (n=words.length，m=words[i]的平均长度)
         *
         * @param prefix
         * @param suffix
         * @return
         */
        public int f(String prefix, String suffix) {
            List<Integer> list1 = trie1.searchPrefix(prefix);
            List<Integer> list2 = trie2.searchSuffix(suffix);

            //words[i]中不存在满足prefix或suffix的单词，返回-1
            if (list1 == null || list2 == null) {
                return -1;
            }

            //words[i]按照下标索引插入前缀树，从后往前找，优先找到满足前缀后缀的最大下标索引
            int i = list1.size() - 1;
            int j = list2.size() - 1;

            while (i >= 0 && j >= 0) {
                //words[i]中满足prefix的下标索引
                int index1 = list1.get(i);
                //words[i]中满足suffix的下标索引
                int index2 = list2.get(j);

                if (index1 == index2) {
                    return index1;
                } else if (index1 > index2) {
                    i--;
                } else {
                    j--;
                }
            }

            //遍历结束，list1和list2中不存在同时满足prefix和suffix的单词，返回-1
            return -1;
        }

        /**
         * 前缀树
         */
        private static class Trie {
            private final TrieNode root;

            public Trie() {
                root = new TrieNode();
            }

            /**
             * word插入前缀树中，并且插入过程中每个节点的list中加入word在words中的下标索引index
             * 时间复杂度O(n)，空间复杂度O(1)
             *
             * @param word
             * @param index
             */
            public void insert(String word, int index) {
                TrieNode node = root;

                for (char c : word.toCharArray()) {
                    if (node.children[c - 'a'] == null) {
                        node.children[c - 'a'] = new TrieNode();
                    }

                    node = node.children[c - 'a'];
                    //插入过程中每个节点的list中加入word在words中的下标索引index
                    node.list.add(index);
                }

                node.isEnd = true;
            }

            /**
             * word的逆序插入前缀树中，并且插入过程中每个节点的list中加入word在words中的下标索引index
             * 时间复杂度O(n)，空间复杂度O(1)
             *
             * @param word
             * @param index
             */
            public void reverseInsert(String word, int index) {
                TrieNode node = root;

                //逆序需要从后往前遍历
                for (int j = word.length() - 1; j >= 0; j--) {
                    char c = word.charAt(j);

                    if (node.children[c - 'a'] == null) {
                        node.children[c - 'a'] = new TrieNode();
                    }

                    node = node.children[c - 'a'];
                    //插入过程中每个节点的list中加入word在words中的下标索引index
                    node.list.add(index);
                }

                node.isEnd = true;
            }

            /**
             * insert()对应前缀树中查询满足prefix的words[i]，返回words[i]在words中的下标索引，不存在返回null
             * 时间复杂度O(prefix.length())，空间复杂度O(1)
             *
             * @param prefix
             * @return
             */
            public List<Integer> searchPrefix(String prefix) {
                TrieNode node = root;

                for (char c : prefix.toCharArray()) {
                    if (node.children[c - 'a'] == null) {
                        return null;
                    }

                    node = node.children[c - 'a'];
                }

                return node.list;
            }

            /**
             * reverseInsert()对应前缀树中查询满足suffix逆序的words[i]，返回words[i]在words中的下标索引，不存在返回null
             * 时间复杂度O(suffix.length())，空间复杂度O(1)
             *
             * @param suffix
             * @return
             */
            public List<Integer> searchSuffix(String suffix) {
                TrieNode node = root;

                //逆序需要从后往前遍历
                for (int i = suffix.length() - 1; i >= 0; i--) {
                    char c = suffix.charAt(i);

                    if (node.children[c - 'a'] == null) {
                        return null;
                    }

                    node = node.children[c - 'a'];
                }

                return node.list;
            }

            /**
             * 前缀树节点
             */
            private static class TrieNode {
                private final TrieNode[] children;
                //存储words中字符串的下标索引，根节点到当前节点的字符串是words中的字符串前缀或后缀
                private List<Integer> list;
                private boolean isEnd;

                public TrieNode() {
                    //只包含小写英文字母
                    children = new TrieNode[26];
                    list = new ArrayList<>();
                    isEnd = false;
                }
            }
        }
    }
}
