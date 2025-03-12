package com.zhang.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2024/5/26 08:41
 * @Author zsy
 * @Description 字符串的不同子字符串个数 字符串哈希类比Problem187、Problem1044、Problem1316、Problem1392、Problem3029、Problem3031 前缀树类比
 * 给定一个字符串 s，返回 s 的不同子字符串的个数。
 * 字符串的 子字符串 是由原字符串删除开头若干个字符（可能是 0 个）并删除结尾若干个字符（可能是 0 个）形成的字符串。
 * <p>
 * 输入：s = "aabbaba"
 * 输出：21
 * 解释：不同子字符串的集合是 ["a","b","aa","bb","ab","ba","aab","abb","bab","bba","aba","aabb","abba","bbab","baba","aabba","abbab","bbaba","aabbab","abbaba","aabbaba"]
 * <p>
 * 输入：s = "abcdefg"
 * 输出：28
 * <p>
 * 1 <= s.length <= 500
 * s 由小写英文字母组成。
 */
public class Problem1698 {
    public static void main(String[] args) {
        Problem1698 problem1698 = new Problem1698();
        String s = "aabbaba";
        System.out.println(problem1698.countDistinct(s));
        System.out.println(problem1698.countDistinct2(s));
    }

    /**
     * 前缀树
     * s[i]作为起点字符加入前缀树中，如果前缀树中没有当前字符s[j]，则s[i]-s[j]是一个不同的子串
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param s
     * @return
     */
    public int countDistinct(String s) {
        Trie trie = new Trie();

        return trie.insert(s);
    }

    /**
     * 字符串哈希
     * hash[i]：s[0]-s[i-1]的哈希值
     * prime[i]：p^i的值
     * hash[j+1]-hash[i]*prime[j-i+1]：s[i]-s[j]的哈希值
     * 核心思想：将字符串看成P进制数，再对MOD取余，作为当前字符串的哈希值，只要两个字符串哈希值相等，则认为两个字符串相等
     * 一般取P为较大的质数，P=131或P=13331或P=131313，此时产生的哈希冲突低；
     * 一般取MOD=2^63(long类型最大值+1)，在计算时不处理溢出问题，产生溢出相当于自动对MOD取余；
     * 如果产生哈希冲突，则使用双哈希来减少冲突
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param s
     * @return
     */
    public int countDistinct2(String s) {
        //大质数，p进制
        int p = 131;
        long[] hash = new long[s.length() + 1];
        long[] prime = new long[s.length() + 1];

        //p^0初始化
        prime[0] = 1;

        for (int i = 1; i <= s.length(); i++) {
            char c = s.charAt(i - 1);
            //注意：不需要进行取模运算，产生溢出相当于自动对MOD取模
            hash[i] = hash[i - 1] * p + c;
            prime[i] = prime[i - 1] * p;
        }

        //存储s子串的哈希值
        Set<Long> set = new HashSet<>();

        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                //s[i]-s[j]的哈希值
                //hash[i]乘以prime[j-i+1]相当于hash[i]在p进制情况下左移j-i+1位
                long h = hash[j + 1] - hash[i] * prime[j - i + 1];
                set.add(h);
            }
        }

        return set.size();
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
         * s中每一个子串都加入前缀树中，并返回s中不同子串的个数
         * 时间复杂度O(n^2)，空间复杂度O(1)
         *
         * @param s
         * @return
         */
        public int insert(String s) {
            int count = 0;

            for (int i = 0; i < s.length(); i++) {
                TrieNode node = root;

                for (int j = i; j < s.length(); j++) {
                    char c = s.charAt(j);

                    if (!node.children.containsKey(c)) {
                        node.children.put(c, new TrieNode());
                        //前缀树中不存在s[i]-s[j]的字符串，则s[i]-s[j]是一个不同的子串
                        count++;
                        node.children.get(c).isEnd = true;
                    }

                    node = node.children.get(c);
                }
            }

            return count;
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
