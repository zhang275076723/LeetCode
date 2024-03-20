package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/2/14 08:45
 * @Author zsy
 * @Description 回文对 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem409、Problem479、Problem516、Problem647、Problem680、Problem866、Problem1147、Problem1177、Problem1312、Problem1328、Problem1332、Problem1400 前缀树类比Problem14、Problem208、Problem211、Problem212、Problem421、Problem676、Problem677、Problem720、Problem745、Problem820、Problem1166、Problem1804、Problem3043
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
        System.out.println(problem336.palindromePairs3(words));
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

                //words[i]+words[j]是否是回文的标志位
                boolean flag = true;

                String s = words[i] + words[j];
                int left = 0;
                int right = s.length() - 1;

                while (left < right) {
                    if (s.charAt(left) != s.charAt(right)) {
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
     * 1、words[i]的逆序字符串reverseWord插入前缀树，在插入过程中，当reverseWord[j]-reverseWord[reverseWord.length()-1]是回文字符串时，
     * 当前前缀树节点的list中加入words[i]在words中的下标索引i，即reverseWord[0]-reverseWord[j-1]加上words[i]是回文字符串，
     * reverseWord的末尾节点的index赋值为words[i]在words中的下标索引i
     * 2、words[i]在前缀树中查询，如果words[i]加上前缀树中某个字符串是回文字符串，将构成回文字符串的两个字符串在words中的下标索引加入result
     * 时间复杂度O(n*m^2)，空间复杂度O(n*m) (n=words.length，m=words[i]的平均长度)
     * <p>
     * 例如：words = ["acc", "bbcca", "cca", "a", "", "aca"]
     * 前缀树中list和index存储的都是words中下标索引，在当前前缀树中便于显示，所以表示为字符串
     * <                             root (list=["a","aca"]，index="")
     * <                           /      \
     * <                         c         a (list=["cca"]，index=a)
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
        trie.insert(words);

        //words[i]在前缀树中查询，查询words[i]加上前缀树中某个字符串是回文字符串
        return trie.search(words);
    }

    /**
     * 双前缀树 (超时，但正确)
     * words[i]插入前缀树trie1，words[i]的逆序字符串插入前缀树trie2，
     * trie1中查询words[i]的逆序字符串reverseWord，判断trie1中是否有字符串加上words[i]构成回文字符串；
     * trie2中查询words[i]，判断words[i]加上trie2中是否有字符串构成回文字符串
     * 注意：插入words[i]的前缀树对应insert()和search()，插入words[i]的逆序字符串reverseWord的前缀树对应reverseInsert()和reverseSearch()
     * 时间复杂度O(n*m^2)，空间复杂度O(n*m) (n=words.length，m=words[i]的平均长度)
     * <p>
     * 例如：words = ["acc", "bbcca", "cca", "a", "", "aca"]
     * 前缀树中index存储的是words中下标索引，在当前前缀树中便于显示，所以表示为字符串
     * words[i]插入root1，words[i]的逆序字符串插入roo2
     * <                             root1 (index="")
     * <                           /   |   \
     * <             (index="a") a     b     c
     * <                       /       |      \
     * <                      c        b       c
     * <                   /   \       |        \
     * <   (index="aca") a      c      c         a (index="cca")
     * <                 (index="acc") |
     * <                               c
     * <                               |
     * <                               a (index="bbcca")
     * <
     * <
     * <                             root2 (index="")
     * <                           /      \
     * <                         c         a (index="a")
     * <                       /            \
     * <                     c               c
     * <                   /               /  \
     * <   (index="acc") a               a     c (index="cca")
     * <                          (index="aca") \
     * <                                         b
     * <                                          \
     * <                                           b (index="bbcca")
     *
     * @param words
     * @return
     */
    public List<List<Integer>> palindromePairs3(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        Trie2 trie1 = new Trie2();
        Trie2 trie2 = new Trie2();

        //words[i]插入前缀树trie1
        trie1.insert(words);
        //words[i]的逆序字符串插入前缀树trie2
        trie2.reverseInsert(words);

        //trie1中查询words[i]的逆序字符串reverseWord，判断trie1中是否有字符串加上words[i]构成回文字符串
        trie1.search(words, result);
        //trie2中查询words[i]，判断words[i]加上trie2中是否有字符串构成回文字符串
        trie2.reverseSearch(words, result);

        return result;
    }

    /**
     * 前缀树 (palindromePairs2中使用的前缀树)
     */
    private static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        /**
         * words[i]的逆序字符串reverseWord插入前缀树，在插入过程中，当reverseWord[j]-reverseWord[reverseWord.length()-1]是回文字符串时，
         * 当前前缀树节点的list中加入words[i]在words中的下标索引i，即reverseWord[0]-reverseWord[j-1]加上words[i]是回文字符串，
         * reverseWord的末尾节点的index赋值为words[i]在words中的下标索引i
         * 时间复杂度O(n*m^2)，空间复杂度O(n*m) (n=words.length，m=words[i]的平均长度)
         *
         * @param words
         */
        public void insert(String[] words) {
            for (int i = 0; i < words.length; i++) {
                TrieNode node = root;
                //words[i]的逆序字符串
                String reverseWord = new StringBuilder(words[i]).reverse().toString();

                //reverseWord插入前缀树
                for (int j = 0; j < reverseWord.length(); j++) {
                    char c = reverseWord.charAt(j);

                    //reverseWord[j]-reverseWord[reverseWord.length()-1]是回文字符串，当前前缀树节点的list中加入words[i]在words中的下标索引i，
                    //即reverseWord[0]-reverseWord[j-1]加上words[i]是回文字符串
                    if (isPalindrome(reverseWord, j, reverseWord.length() - 1)) {
                        node.list.add(i);
                    }

                    if (!node.children.containsKey(c)) {
                        node.children.put(c, new TrieNode());
                    }

                    node = node.children.get(c);
                }

                //根节点到当前节点的字符串的逆序在words中的下标索引为i
                node.index = i;
                node.isEnd = true;
            }
        }

        /**
         * words[i]在前缀树中查询，如果words[i]加上前缀树中某个字符串是回文字符串，将构成回文字符串的两个字符串在words中的下标索引加入result
         * words[i]和前缀树中字符串构成回文字符串的3种情况：
         * 1、words[i]每个字符在前缀树查询过程中，当words[i][j]-words[i][words[i].length()-1]是回文字符串时，
         * 并且根节点到当前前缀树节点的字符串的逆序是words中的字符串，即当前前缀树节点node.index不等于-1，则words[i]加上words[node.index]是回文字符串
         * 2、words[i]遍历结束，并且根节点到当前前缀树节点的字符串的逆序是words中的字符串，同时words[i]和根节点到当前前缀树节点的字符串的逆序不相等，
         * 即当前前缀树节点node.index不等于-1，且node.index不等于i，则words[i]加上words[node.index]是回文字符串
         * 3、words[i]遍历结束，words[i]和当前前缀树节点的list中字符串构成回文字符串
         * 时间复杂度O(n*m^2)，空间复杂度O(1) (n=words.length，m=words[i]的平均长度)
         * <p>
         * 例如：
         * 情况1：words[i]="abc"，在逆序前缀树中查询"abc"，逆序前缀树中存在"ab"，
         * 并且words[i][2]-words[i][words[i].length()-1]是回文字符串，则"abc"+"ba"是回文字符串
         * 情况2：words[i]="abc"，在逆序前缀树中查询"abc"，逆序前缀树中存在"abc"，则"abc"+"cba"是回文字符串
         * 情况3：words[i]="abc"，在逆序前缀树中查询"abc"，逆序前缀树中存在"abc"，
         * 并且"xxcba"和"xcba"插入了逆序前缀树，前缀"xx"和"x"是回文字符串，则"abc"+"xxcba"、"abc"+"xcba"是回文字符串
         *
         * @param words
         * @return
         */
        public List<List<Integer>> search(String[] words) {
            List<List<Integer>> result = new ArrayList<>();

            for (int i = 0; i < words.length; i++) {
                TrieNode node = root;

                for (int j = 0; j < words[i].length(); j++) {
                    char c = words[i].charAt(j);

                    //情况1：words[i][j]-words[i][words[i].length()-1]是回文字符串时，
                    //并且根节点到当前前缀树节点的字符串的逆序是words中的字符串，即当前前缀树节点node.index不等于-1，
                    //则words[i]加上words[node.index]是回文字符串
                    if (node.index != -1 && isPalindrome(words[i], j, words[i].length() - 1)) {
                        List<Integer> list = new ArrayList<>();
                        list.add(i);
                        list.add(node.index);
                        result.add(list);
                    }

                    node = node.children.get(c);

                    //前缀树中没有字符c节点，直接跳出循环
                    if (node == null) {
                        break;
                    }
                }

                //不存在当前节点，直接进行下次循环
                if (node == null) {
                    continue;
                }

                //情况2：words[i]遍历结束，并且根节点到当前前缀树节点的字符串的逆序是words中的字符串，同时words[i]和根节点到当前前缀树节点的字符串的逆序不相等，
                //即当前前缀树节点node.index不等于-1，且node.index不等于i，则words[i]加上words[node.index]是回文字符串
                if (node.index != -1 && node.index != i) {
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    list.add(node.index);
                    result.add(list);
                }

                //情况3：words[i]遍历结束，words[i]和当前前缀树节点的list中字符串构成回文字符串
                for (int index : node.list) {
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    list.add(index);
                    result.add(list);
                }
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
            int left = i;
            int right = j;

            while (left < right) {
                if (word.charAt(left) != word.charAt(right)) {
                    return false;
                } else {
                    left++;
                    right--;
                }
            }

            return true;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            //当前节点的子节点map
            private final Map<Character, TrieNode> children;
            //存储words中字符串的下标索引，根节点到当前节点的字符串加上list中下标索引在words中的字符串是回文字符串
            private final List<Integer> list;
            //根节点到当前节点的字符串的逆序在words中的下标索引
            private int index;
            //根节点到当前节点的字符串的逆序是否是words中的字符串
            //注意：当前前缀树可以只使用index替代isEnd
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                list = new ArrayList<>();
                index = -1;
                isEnd = false;
            }
        }
    }

    /**
     * 前缀树 (palindromePairs3中使用的前缀树)
     * 注意：一个前缀树插入words[i]，另一个前缀树插入words[i]的逆序字符串reverseWord，
     * 插入words[i]的前缀树对应insert()和search()，插入words[i]的逆序字符串reverseWord的前缀树对应reverseInsert()和reverseSearch()
     */
    private static class Trie2 {
        private final TrieNode root;

        public Trie2() {
            root = new TrieNode();
        }

        /**
         * words[i]插入前缀树，words[i]的末尾节点的index赋值为words[i]在words中的下标索引i
         * 时间复杂度O(n*m)，空间复杂度O(n*m) (n=words.length，m=words[i]的平均长度)
         *
         * @param words
         */
        public void insert(String[] words) {
            for (int i = 0; i < words.length; i++) {
                TrieNode node = root;

                //words[i]插入前缀树
                for (char c : words[i].toCharArray()) {
                    if (!node.children.containsKey(c)) {
                        node.children.put(c, new TrieNode());
                    }

                    node = node.children.get(c);
                }

                //根节点到当前节点的字符串在words中的下标索引为i
                node.index = i;
                node.isEnd = true;
            }
        }

        /**
         * words[i]的逆序字符串reverseWord插入前缀树，reverseWord的末尾节点的index赋值为words[i]在words中的下标索引i
         * 时间复杂度O(n*m)，空间复杂度O(n*m) (n=words.length，m=words[i]的平均长度)
         *
         * @param words
         */
        public void reverseInsert(String[] words) {
            for (int i = 0; i < words.length; i++) {
                TrieNode node = root;
                //words[i]的逆序字符串
                String reverseWord = new StringBuilder(words[i]).reverse().toString();

                //reverseWord插入前缀树
                for (char c : reverseWord.toCharArray()) {
                    if (!node.children.containsKey(c)) {
                        node.children.put(c, new TrieNode());
                    }

                    node = node.children.get(c);
                }

                //根节点到当前节点的字符串的逆序在words中的下标索引为i
                node.index = i;
                node.isEnd = true;
            }
        }

        /**
         * insert()对应前缀树中查询words[i]的逆序字符串reverseWord，如果前缀树中某个字符串加上words[i]是回文字符串，
         * 将构成回文字符串的两个字符串在words中的下标索引加入result
         * 前缀树中字符串和words[i]构成回文字符串的2种情况：
         * 1、reverseWord每个字符在前缀树查询过程中，当reverseWord[j]-reverseWord[reverseWord.length()-1]是回文字符串时，
         * 并且根节点到当前前缀树节点的字符串是words中的字符串，即当前前缀树节点node.index不等于-1，则words[node.index]加上words[i]是回文字符串
         * 2、reverseWord遍历结束，并且根节点到当前前缀树节点的字符串是words中的字符串，同时words[i]和根节点到当前前缀树节点的字符串不相等，
         * 即当前前缀树节点node.index不等于-1，且node.index不等于i，则words[node.index]加上words[i]是回文字符串
         * 注意：search()是words[node.index]加上words[i]构成回文字符串，reverseSearch()是words[i]加上words[node.index]构成回文字符串
         * 时间复杂度O(n*m^2)，空间复杂度O(m) (n=words.length，m=words[i]的平均长度)
         * <p>
         * 例如：
         * 情况1：words[i]="abc"，reverseWord="cba"，在正序前缀树中查询"cba"，正序前缀树中存在"cb"，
         * 并且reverseWord[2]-reverseWord[reverseWord.length()-1]是回文字符串，则"cb"+"abc"是回文字符串
         * 情况2：words[i]="abc"，reverseWord="cba"，在正序前缀树中查询"cba"，正序前缀树中存在"cba"，则"cba"+"abc"是回文字符串
         *
         * @param words
         * @param result
         * @return
         */
        public void search(String[] words, List<List<Integer>> result) {
            for (int i = 0; i < words.length; i++) {
                TrieNode node = root;
                //words[i]的逆序字符串
                String reverseWord = new StringBuilder(words[i]).reverse().toString();

                for (int j = 0; j < reverseWord.length(); j++) {
                    char c = reverseWord.charAt(j);

                    //情况1：reverseWord[j]-reverseWord[reverseWord.length()-1]是回文字符串时，
                    //并且根节点到当前前缀树节点的字符串是words中的字符串，即当前前缀树节点node.index不等于-1，
                    //则words[node.index]加上words[i]是回文字符串
                    if (node.index != -1 && isPalindrome(reverseWord, j, reverseWord.length() - 1)) {
                        List<Integer> list = new ArrayList<>();
                        list.add(node.index);
                        list.add(i);
                        result.add(list);
                    }

                    node = node.children.get(c);

                    //前缀树中没有字符c节点，直接跳出循环
                    if (node == null) {
                        break;
                    }
                }

                //不存在当前节点，直接进行下次循环
                if (node == null) {
                    continue;
                }

                //情况2：reverseWord遍历结束，并且根节点到当前前缀树节点的字符串是words中的字符串，同时words[i]和根节点到当前前缀树节点的字符串不相等，
                //即当前前缀树节点node.index不等于-1，且node.index不等于i，则words[node.index]加上words[i]是回文字符串
                if (node.index != -1 && node.index != i) {
                    List<Integer> list = new ArrayList<>();
                    list.add(node.index);
                    list.add(i);
                    result.add(list);
                }
            }
        }

        /**
         * reverseInsert()对应前缀树中查询words[i]，如果words[i]加上前缀树中某个字符串是回文字符串，
         * 将构成回文字符串的两个字符串在words中的下标索引加入result
         * words[i]和前缀树中字符串构成回文字符串的2种情况：
         * 1、words[i]每个字符在前缀树查询过程中，当words[i][j]-words[i][words[i].length()-1]是回文字符串时，
         * 并且根节点到当前前缀树节点的字符串的逆序是words中的字符串，即当前前缀树节点node.index不等于-1，则words[i]加上words[node.index]是回文字符串
         * 2、words[i]遍历结束，并且根节点到当前前缀树节点的字符串的逆序是words中的字符串，同时words[i]和根节点到当前前缀树节点的字符串的逆序不相等，
         * 即当前前缀树节点node.index不等于-1，且node.index不等于i，则words[i]加上words[node.index]是回文字符串
         * 注意：情况2和search()中情况2相同，即可以省略
         * 注意：search()是words[node.index]加上words[i]构成回文字符串，reverseSearch()是words[i]加上words[node.index]构成回文字符串
         * 时间复杂度O(n*m^2)，空间复杂度O(1) (n=words.length，m=words[i]的平均长度)
         * <p>
         * 例如：
         * 情况1：words[i]="abc"，在逆序前缀树中查询"abc"，逆序前缀树中存在"ab"，
         * 并且words[i][2]-words[i][words[i].length()-1]是回文字符串，则"abc"+"ba"是回文字符串
         * 情况2：words[i]="abc"，在逆序前缀树中查询"abc"，逆序前缀树中存在"abc"，则"abc"+"cba"是回文字符串
         * (和search()中情况2相同，省略当前情况)
         *
         * @param words
         * @param result
         */
        public void reverseSearch(String[] words, List<List<Integer>> result) {
            for (int i = 0; i < words.length; i++) {
                TrieNode node = root;

                for (int j = 0; j < words[i].length(); j++) {
                    char c = words[i].charAt(j);

                    //情况1：words[i][j]-words[i][words[i].length()-1]是回文字符串时，
                    //并且根节点到当前前缀树节点的字符串的逆序是words中的字符串，即当前前缀树节点node.index不等于-1，
                    //则words[i]加上words[node.index]是回文字符串
                    if (node.index != -1 && isPalindrome(words[i], j, words[i].length() - 1)) {
                        List<Integer> list = new ArrayList<>();
                        list.add(i);
                        list.add(node.index);
                        result.add(list);
                    }

                    node = node.children.get(c);

                    //前缀树中没有字符c节点，直接跳出循环
                    if (node == null) {
                        break;
                    }
                }

//                //不存在当前节点，直接进行下次循环
//                if (node == null) {
//                    continue;
//                }
//
//                //情况2：words[i]遍历结束，并且根节点到当前前缀树节点的字符串的逆序是words中的字符串，同时words[i]和根节点到当前前缀树节点的字符串的逆序不相等，
//                //即当前前缀树节点node.index不等于-1，且node.index不等于i，则words[i]加上words[node.index]是回文字符串
//                //注意：情况2和search()中情况2相同，即可以省略
//                if (node.index != -1 && node.index != i) {
//                    List<Integer> list = new ArrayList<>();
//                    list.add(i);
//                    list.add(node.index);
//                    result.add(list);
//                }
            }
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
            int left = i;
            int right = j;

            while (left < right) {
                if (word.charAt(left) != word.charAt(right)) {
                    return false;
                } else {
                    left++;
                    right--;
                }
            }

            return true;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final Map<Character, TrieNode> children;
            //word是正序插入：根节点到当前节点的字符串在words中的下标索引
            //word是逆序插入：根节点到当前节点的字符串的逆序在words中的下标索引
            private int index;
            //word是正序插入：根节点到当前节点的字符串是否是words中的字符串
            //word是逆序插入：根节点到当前节点的字符串的逆序是否是words中的字符串
            //注意：当前前缀树可以只使用index替代isEnd
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                index = -1;
                isEnd = false;
            }
        }
    }
}
