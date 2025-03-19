package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/2/14 08:45
 * @Author zsy
 * @Description 回文对 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem409、Problem479、Problem516、Problem564、Problem647、Problem680、Problem866、Problem1147、Problem1177、Problem1312、Problem1328、Problem1332、Problem1400、Problem1457、Problem1542、Problem1616、Problem1930、Problem2002、Problem2108、Problem2131、Problem2217、Problem2384、Problem2396、Problem2484、Problem2490、Problem2663、Problem2697、Problem2791、Problem3035 前缀树类比
 * 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem564、Problem647、Problem680、Problem866、Problem1147、Problem1177、Problem1312、Problem1328、Problem1332、Problem1400、Problem1457、Problem1542、Problem1616、Problem1930、Problem2002、Problem2108、Problem2131、Problem2217、Problem2384、Problem2396、Problem2484、Problem2490、Problem2663、Problem2697、Problem2791、Problem3035
 * 给定一个由唯一字符串构成的 0 索引 数组 words 。
 * 回文对 是一对整数 (i, j) ，满足以下条件：
 * 0 <= i, j < words.length，
 * i != j ，并且
 * words[i] + words[j]（两个字符串的连接）是一个回文。
 * 返回一个数组，它包含 words 中所有满足 回文对 条件的字符串。
 * 你必须设计一个时间复杂度为 O(sum of words[i].length) 的算法。
 * <p>
 * 输入：words = ["abcd","dcba","lls","s","sssll"]
 * 输出：[[0,1],[1,0],[3,2],[2,4]]
 * 解释：可拼接成的回文串为 ["dcbaabcd","abcddcba","slls","llssssll"]
 * <p>
 * 输入：words = ["bat","tab","cat"]
 * 输出：[[0,1],[1,0]]
 * 解释：可拼接成的回文串为 ["battab","tabbat"]
 * <p>
 * 输入：words = ["a",""]
 * 输出：[[0,1],[1,0]]
 * <p>
 * 1 <= words.length <= 5000
 * 0 <= words[i].length <= 300
 * words[i] 由小写英文字母组成
 * words[i] 互不相同
 */
public class Problem336 {
    public static void main(String[] args) {
        Problem336 problem336 = new Problem336();
//        //[[0, 1], [1, 0], [2, 4], [3, 2]]
//        //"abcd"+"dcba","dcba"+"abcd","lls"+"sssll","s"+"lls"
//        String[] words = {"abcd", "dcba", "lls", "s", "sssll"};
        //[[0, 3], [0, 2], [0, 1], [2, 0], [3, 4], [3, 2], [4, 3], [4, 5], [5, 4]]
        //"acc"+"a","acc"+"cca","acc"+"bbcca","cca"+"acc","a"+"","a"+"cca",""+"a",""+"aca","aca"+""
        String[] words = {"acc", "bbcca", "cca", "a", "", "aca"};
        System.out.println(problem336.palindromePairs(words));
        System.out.println(problem336.palindromePairs2(words));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2*m)，空间复杂度O(1) (n=words.length，m=words[i]的平均长度)
     *
     * @param words
     * @return
     */
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (i == j) {
                    continue;
                }

                String str = words[i] + words[j];
                //str是否是会回文标志位
                boolean flag = true;

                int left = 0;
                int right = str.length() - 1;

                while (left < right) {
                    if (str.charAt(left) != str.charAt(right)) {
                        flag = false;
                        break;
                    } else {
                        left++;
                        right--;
                    }
                }

                if (flag) {
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    list.add(j);
                    result.add(list);
                }
            }
        }

        return result;
    }

    /**
     * 前缀树
     * 1、words[i]的逆序字符串reverseWord插入前缀树，在插入过程中，如果reverseWord[j]-reverseWord[reverseWord.length()-1]是回文串，
     * 则当前前缀树节点的list中加入words[i]在words中的下标索引i，reverseWord的末尾节点的index赋值为words[i]在words中的下标索引i
     * 2、查询words[i]加上前缀树中某个字符串是回文字符串的下标索引集合
     * 时间复杂度O(n*m^2)，空间复杂度O(n*m) (n=words.length，m=words[i]的平均长度)
     * <p>
     * 例如：words = ["acc", "bbcca", "cca", "a", "", "aca"]
     * 注意：前缀树中list和index存储的都是words中下标索引，在当前前缀树中为了便于显示，所以表示为字符串
     * <                             root (list=["a","aca"]，index="")
     * <                           /      \
     * <                         c         a (list=["cca"]，index="a")
     * <                       /            \
     * <        (list=[acc]) c               c (list=["cca","aca"])
     * <                   /               /  \
     * <   (index="acc") a               a     c (list=["bbcca"]，index="cca")
     * <                          (index="aca") \
     * <                                         b (list=["bbcca"])
     * <                                          \
     * <                                           b (index="bbcca")
     *
     * @param words
     * @return
     */
    public List<List<Integer>> palindromePairs2(String[] words) {
        Trie trie = new Trie();

        //words[i]的逆序字符串插入前缀树
        for (int i = 0; i < words.length; i++) {
            trie.reverseInsert(words[i], i);
        }

        List<List<Integer>> result = new ArrayList<>();

        //查询words[i]加上前缀树中某个字符串是回文字符串的下标索引集合
        for (int i = 0; i < words.length; i++) {
            result.addAll(trie.search(words[i], i));
        }

        return result;
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
         * word的逆序字符串reverseWord插入前缀树，在插入过程中，当reverseWord[j]-reverseWord[reverseWord.length()-1]是回文字符串时，
         * 当前前缀树节点的list中加入word在words中的下标索引index，即reverseWord[0]-reverseWord[j-1]加上word是回文字符串，
         * reverseWord的末尾节点的index赋值为word在words中的下标索引index
         * 时间复杂度O(n*m^2)，空间复杂度O(n*m) (n=words.length，m=word的平均长度)
         *
         * @param word
         * @param index
         */
        public void reverseInsert(String word, int index) {
            TrieNode node = root;
            //word的逆序字符串
            String reverseWord = new StringBuilder(word).reverse().toString();

            //reverseWord插入前缀树
            for (int i = 0; i < reverseWord.length(); i++) {
                char c = reverseWord.charAt(i);

                //reverseWord[i]-reverseWord[reverseWord.length()-1]是回文字符串，当前前缀树节点的list中加入words[i]在words中的下标索引i，
                //即reverseWord[0]-reverseWord[i-1]加上words[i]是回文字符串
                if (isPalindrome(reverseWord, i, reverseWord.length() - 1)) {
                    node.list.add(index);
                }

                if (!node.children.containsKey(c)) {
                    node.children.put(c, new TrieNode());
                }

                node = node.children.get(c);
            }

            //根节点到当前节点作为words中字符串的逆序在words中的下标索引为i
            node.index = index;
            node.isEnd = true;
        }

        /**
         * 查询word加上前缀树中某个字符串是回文字符串的下标索引集合
         * word加上前缀树中字符串构成回文字符串的3种情况：
         * 1、word每个字符在前缀树查询过程中，当word[i]-word[word.length()-1]是回文字符串时，
         * 并且根节点到当前节点的字符串的逆序是words中的字符串，即当前节点node.index不等于-1，
         * 则word加上words[node.index]构成回文字符串
         * 2、word遍历结束，并且根节点到当前节点的字符串的逆序是words中的字符串，即当前节点node.index不等于-1，
         * 同时word和根节点到当前节点的字符串的逆序不相等，即node.index不等于index，不能存在word加上word的回文，
         * 则word加上words[node.index]构成回文字符串
         * 3、word遍历结束，word加上当前节点的list中字符串构成回文字符串
         * 时间复杂度O(n*m^2)，空间复杂度O(1) (n=words.length，m=word的平均长度)
         * <p>
         * 例如：
         * 情况1：word="abc"，在逆序前缀树中查询"abc"，逆序前缀树中存在"ab"，
         * 并且word[2]-word[words[i].length()-1]是回文字符串，即"c"是回文字符串，则"abc"+"ba"是回文字符串
         * 情况2：word="abc"，在逆序前缀树中查询"abc"，逆序前缀树中存在"abc"，则"abc"+"cba"是回文字符串
         * 情况3：word="abc"，在逆序前缀树中查询"abc"，逆序前缀树中存在"abc"，并且前缀树中存在"xxcba"和"xcba"，
         * 即遍历到word的末尾节点时，当前节点的list中前缀"xx"和"x"是回文字符串，则"abc"+"xxcba"、"abc"+"xcba"是回文字符串
         *
         * @param word
         * @param index
         */
        public List<List<Integer>> search(String word, int index) {
            List<List<Integer>> result = new ArrayList<>();
            TrieNode node = root;

            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);

                //情况1：word每个字符在前缀树查询过程中，当word[i]-word[word.length()-1]是回文字符串时，
                //并且根节点到当前节点的字符串的逆序是words中的字符串，即当前节点node.index不等于-1，
                //则word加上words[node.index]构成回文字符串
                if (node.index != -1 && isPalindrome(word, i, word.length() - 1)) {
                    List<Integer> list = new ArrayList<>();
                    list.add(index);
                    list.add(node.index);
                    result.add(list);
                }

                node = node.children.get(c);

                //当前节点不存在字符c节点，直接返回result
                if (node == null) {
                    return result;
                }
            }

            //情况2：word遍历结束，并且根节点到当前节点的字符串的逆序是words中的字符串，即当前节点node.index不等于-1，
            //同时word和根节点到当前节点的字符串的逆序不相等，即node.index不等于index，不能存在word加上word的回文，
            //则word加上words[node.index]构成回文字符串
            if (node.index != -1 && node.index != index) {
                List<Integer> list = new ArrayList<>();
                list.add(index);
                list.add(node.index);
                result.add(list);
            }

            //情况3：word遍历结束，word加上当前节点的list中字符串构成回文字符串
            for (int index2 : node.list) {
                List<Integer> list = new ArrayList<>();
                list.add(index);
                list.add(index2);
                result.add(list);
            }

            return result;
        }

        /**
         * 判断word[i]-word[j]是否是回文串
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param word
         * @param i
         * @param j
         * @return
         */
        private boolean isPalindrome(String word, int i, int j) {
            while (i < j) {
                if (word.charAt(i) != word.charAt(j)) {
                    return false;
                } else {
                    i++;
                    j--;
                }
            }

            return true;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final Map<Character, TrieNode> children;
            //根节点到当前节点作为words中字符串的后缀，并且words中当前字符串剩余部分是回文串在words中的下标索引集合
            private final List<Integer> list;
            //根节点到当前节点作为words中字符串的逆序在words中的下标索引，如果不存在，则为-1
            private int index;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                list = new ArrayList<>();
                index = -1;
                isEnd = false;
            }
        }
    }
}
