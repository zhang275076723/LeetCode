package com.zhang.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2025/5/2 08:28
 * @Author zsy
 * @Description 字符串中的额外字符 类比Problem139、Problem140、Problem212、Problem472 前缀树类比
 * 给你一个下标从 0 开始的字符串 s 和一个单词字典 dictionary 。
 * 你需要将 s 分割成若干个 互不重叠 的子字符串，每个子字符串都在 dictionary 中出现过。
 * s 中可能会有一些 额外的字符 不在任何子字符串中。
 * 请你采取最优策略分割 s ，使剩下的字符 最少 。
 * <p>
 * 输入：s = "leetscode", dictionary = ["leet","code","leetcode"]
 * 输出：1
 * 解释：将 s 分成两个子字符串：下标从 0 到 3 的 "leet" 和下标从 5 到 8 的 "code" 。
 * 只有 1 个字符没有使用（下标为 4），所以我们返回 1 。
 * 示例 2：
 * <p>
 * 输入：s = "sayhelloworld", dictionary = ["hello","world"]
 * 输出：3
 * 解释：将 s 分成两个子字符串：下标从 3 到 7 的 "hello" 和下标从 8 到 12 的 "world" 。
 * 下标为 0 ，1 和 2 的字符没有使用，所以我们返回 3 。
 * <p>
 * 1 <= s.length <= 50
 * 1 <= dictionary.length <= 50
 * 1 <= dictionary[i].length <= 50
 * dictionary[i] 和 s 只包含小写英文字母。
 * dictionary 中的单词互不相同。
 */
public class Problem2707 {
    public static void main(String[] args) {
        Problem2707 problem2707 = new Problem2707();
        String s = "leetscode";
        String[] dictionary = {"leet", "code", "leetcode"};
        System.out.println(problem2707.minExtraChar(s, dictionary));
        System.out.println(problem2707.minExtraChar2(s, dictionary));
    }

    /**
     * 动态规划
     * dp[i]：s[0]-s[i-1]拆成dictionary中单词，剩下的最少字符个数
     * dp[i] = min(dp[i-1]+1,dp[j]) (s[j]-s[i-1]是dictionary中的单词) (0 <= j < i)
     * 注意：dictionary中单词可以重复使用
     * 时间复杂度O(ml+n^3)，空间复杂度O(ml+n) (n=s.length，m=dictionary.length，l=dictionary[i].length())
     *
     * @param s
     * @param dictionary
     * @return
     */
    public int minExtraChar(String s, String[] dictionary) {
        //存储dictionary中单词的集合
        Set<String> set = new HashSet<>();

        for (String word : dictionary) {
            set.add(word);
        }

        int[] dp = new int[s.length() + 1];
        //dp初始化
        dp[0] = 0;

        for (int i = 1; i <= s.length(); i++) {
            //dp初始化，s[0]-s[i-1]由s[0]-s[i-2]拆成dictionary中单词，剩下的最少字符个数加1构成
            dp[i] = dp[i - 1] + 1;

            //判断s[j]-s[i-1]是否是dictionary中单词
            for (int j = 0; j < i; j++) {
                //s[j]-s[i-1]是dictionary中单词，则更新dp[i]
                if (set.contains(s.substring(j, i))) {
                    dp[i] = Math.min(dp[i], dp[j]);
                }
            }
        }

        return dp[s.length()];
    }

    /**
     * 动态规划+前缀树
     * dp[i]：s[0]-s[i-1]拆成dictionary中单词，剩下的最少字符个数
     * dp[i] = min(dp[i-1]+1,dp[j]) (s[j]-s[i-1]是dictionary中的单词) (0 <= j < i)
     * 将dictionary中单词的逆序插入前缀树中，通过前缀树O(1)判断子串s[j]-s[i-1]是否是dictionary中的单词
     * 注意：dictionary中单词可以重复使用
     * 时间复杂度O(ml+n^2)，空间复杂度O(ml+n) (n=s.length，m=dictionary.length，l=dictionary[i].length())
     *
     * @param s
     * @param dictionary
     * @return
     */
    public int minExtraChar2(String s, String[] dictionary) {
        Trie trie = new Trie();

        for (String word : dictionary) {
            trie.reverseInsert(word);
        }

        int[] dp = new int[s.length() + 1];
        //dp初始化
        dp[0] = 0;

        for (int i = 1; i <= s.length(); i++) {
            //dp初始化，s[0]-s[i-1]由s[0]-s[i-2]拆成dictionary中单词，剩下的最少字符个数加1构成
            dp[i] = dp[i - 1] + 1;
            //每次从前缀树根节点开始查找
            Trie.TrieNode node = trie.root;

            //判断s[j]-s[i-1]是否是dictionary中单词
            //注意：j要从后往前遍历
            for (int j = i - 1; j >= 0; j--) {
                char c = s.charAt(j);

                //前缀树中不存在当前节点c，则dictionary中不存在以s[i-1]结尾的单词，直接跳出循环
                if (!node.children.containsKey(c)) {
                    break;
                }

                node = node.children.get(c);

                //s[j]-s[i-1]是dictionary中单词，则更新dp[i]
                if (node.isEnd) {
                    dp[i] = Math.min(dp[i], dp[j]);
                }
            }
        }

        return dp[s.length()];
    }

    /**
     * 前缀树
     */
    private static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void reverseInsert(String word) {
            TrieNode node = root;

            for (int i = word.length() - 1; i >= 0; i--) {
                char c = word.charAt(i);

                if (!node.children.containsKey(c)) {
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
            private final Map<Character, TrieNode> children;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                isEnd = false;
            }
        }
    }
}
