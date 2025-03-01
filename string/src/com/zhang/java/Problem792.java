package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/5/27 08:57
 * @Author zsy
 * @Description 匹配子序列的单词数 类比Problem392、Problem522、Problem524、Problem1023 前缀树类比
 * 给定字符串 s 和字符串数组 words, 返回  words[i] 中是s的子序列的单词个数 。
 * 字符串的 子序列 是从原始字符串中生成的新字符串，可以从中删去一些字符(可以是none)，而不改变其余字符的相对顺序。
 * 例如， “ace” 是 “abcde” 的子序列。
 * <p>
 * 输入: s = "abcde", words = ["a","bb","acd","ace"]
 * 输出: 3
 * 解释: 有三个是 s 的子序列的单词: "a", "acd", "ace"。
 * <p>
 * 输入: s = "dsahjpjauf", words = ["ahjpjau","ja","ahbwzgqnuk","tnmlanowax"]
 * 输出: 2
 * <p>
 * 1 <= s.length <= 5 * 10^4
 * 1 <= words.length <= 5000
 * 1 <= words[i].length <= 50
 * words[i]和 s 都只由小写字母组成。
 */
public class Problem792 {
    public static void main(String[] args) {
        Problem792 problem792 = new Problem792();
        String s = "abcde";
        String[] words = {"a", "bb", "acd", "ace"};
        System.out.println(problem792.numMatchingSubseq(s, words));
        System.out.println(problem792.numMatchingSubseq2(s, words));
        System.out.println(problem792.numMatchingSubseq3(s, words));
    }

    /**
     * 双指针
     * 遍历字符串word，如果word[i]==s[j]，则i指针右移，最后判断i是否遍历到字符串word末尾，即i是否等于word.length()
     * 时间复杂度O(mn)，空间复杂度O(1) (n=s.length()，m=words.length，p=words[i].length())
     *
     * @param s
     * @param words
     * @return
     */
    public int numMatchingSubseq(String s, String[] words) {
        int count = 0;

        for (String word : words) {
            //s的长度小于word的长度，则word不是s的子序列
            if (s.length() < word.length()) {
                continue;
            }

            //字符串word的下标索引
            int i = 0;

            for (int j = 0; j < s.length(); j++) {
                if (word.charAt(i) == s.charAt(j)) {
                    i++;
                }

                //i已经遍历完，则word是s的子序列
                if (i == word.length()) {
                    count++;
                    break;
                }
            }
        }

        return count;
    }

    /**
     * 动态规划
     * dp[i][j]：从s[i]开始往后字符'a'+j第一次出现的下标索引
     * dp[i][j] = i          (s[i] == 'a'+j)
     * dp[i][j] = dp[i+1][j] (s[i] != 'a'+j)
     * 时间复杂度O(n*|Σ|+mp)=O(n+mp)，空间复杂度O(n*|Σ|)=O(n) (n=s.length()，m=words.length，p=words[i].length()) (|Σ|=26，只包含小写字母)
     *
     * @param s
     * @param words
     * @return
     */
    public int numMatchingSubseq2(String s, String[] words) {
        //只包含小写字母，所以数组二维长度为26
        int[][] dp = new int[s.length() + 1][26];

        //dp初始化
        for (int i = 0; i < 26; i++) {
            //-1表示""中不存在字符'a'+j
            dp[s.length()][i] = -1;
        }

        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                if (s.charAt(i) == 'a' + j) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }

        int count = 0;

        for (String word : words) {
            //word是否是s的子序列标志位
            boolean flag = true;
            //字符串s的下标索引
            int index = 0;

            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);

                //从s[index]开始往后存在字符c，更新index为从s[index]开始往后字符c第一次出现的下标索引dp[index][c-'a']+1
                if (dp[index][c - 'a'] != -1) {
                    index = dp[index][c - 'a'] + 1;
                } else {
                    //从s[index]开始往后不存在字符c，则flag置为false，直接跳出循环
                    flag = false;
                    break;
                }
            }

            if (flag) {
                count++;
            }
        }

        return count;
    }

    /**
     * 前缀树+回溯+剪枝
     * words中单词words[i]全部加入前缀树中，查询前缀树中字符串是s的子序列
     * 时间复杂度O(mp+|Σ|*n^2)=O(mp+n^2)，空间复杂度O(n*|Σ|)=O(n) (n=s.length()，m=words.length，p=words[i].length()) (|Σ|=26，只包含小写字母)
     *
     * @param s
     * @param words
     * @return
     */
    public int numMatchingSubseq3(String s, String[] words) {
        Trie trie = new Trie();

        for (String word : words) {
            trie.insert(word);
        }

        return trie.search(s);
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

            node.count++;
            node.isEnd = true;
        }

        /**
         * 查询前缀树中字符串是s的子序列的个数
         * 时间复杂度O(|Σ|*n^2)，空间复杂度O(1) (|Σ|=26，只包含小写字母)
         *
         * @param s
         * @return
         */
        public int search(String s) {
            return backtrack(0, s, root);
        }

        private int backtrack(int t, String s, TrieNode node) {
            int count = 0;

            //根节点到当前节点是s的子序列
            if (node.isEnd) {
                count = count + node.count;
            }

            //遍历当前节点的子节点，判断从s[t]开始是否存在当前节点的子节点字符c
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                //当前节点的子节点字符
                char c = entry.getKey();
                //从s[t]开始字符c的下标索引
                int index = s.indexOf(c, t);

                //从s[t]开始存在字符c，则从index+1继续往后遍历
                if (index != -1) {
                    count = count + backtrack(index + 1, s, entry.getValue());
                }
            }

            return count;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final Map<Character, TrieNode> children;
            //前缀树中根节点到当前节点字符串出现的次数
            private int count;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                count = 0;
                isEnd = false;
            }
        }
    }
}
