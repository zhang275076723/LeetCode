package com.zhang.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2025/4/16 08:25
 * @Author zsy
 * @Description 最长重复子串 腾讯面试题 美团面试题 字节面试题 类比Problem1062 字符串哈希类比Problem187、Problem1316、Problem1392、Problem1698、Problem3029、Problem3031、Problem3042、Problem3045、Problem3076 前缀树类比 子序列和子数组类比
 * 给你一个字符串 s ，考虑其所有 重复子串 ：即 s 的（连续）子串，在 s 中出现 2 次或更多次。
 * 这些出现之间可能存在重叠。
 * 返回 任意一个 可能具有最长长度的重复子串。
 * 如果 s 不含重复子串，那么答案为 "" 。
 * <p>
 * 输入：s = "banana"
 * 输出："ana"
 * <p>
 * 输入：s = "abcd"
 * 输出：""
 * <p>
 * 2 <= s.length <= 3 * 10^4
 * s 由小写英文字母组成
 */
public class Problem1044 {
    public static void main(String[] args) {
        Problem1044 problem1044 = new Problem1044();
        String s = "banana";
        System.out.println(problem1044.longestDupSubstring(s));
        System.out.println(problem1044.longestDupSubstring2(s));
        System.out.println(problem1044.longestDupSubstring3(s));
    }

    /**
     * 暴力
     * 时间复杂度O(n^3)，空间复杂度O(n^2)
     *
     * @param s
     * @return
     */
    public String longestDupSubstring(String s) {
        String result = "";
        Set<String> set = new HashSet<>();

        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                String subStr = s.substring(i, j + 1);

                if (set.contains(subStr)) {
                    if (subStr.length() > result.length()) {
                        result = subStr;
                    }
                } else {
                    set.add(subStr);
                }
            }
        }

        return result;
    }

    /**
     * 前缀树
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param s
     * @return
     */
    public String longestDupSubstring2(String s) {
        Trie trie = new Trie();

        return trie.insert(s);
    }

    /**
     * 字符串哈希+二分查找
     * hash[i]：s[0]-s[i-1]的哈希值
     * prime[i]：p^i的值
     * hash[j+1]-hash[i]*prime[j-i+1]：s[i]-s[j]的哈希值
     * 核心思想：将字符串看成P进制数，再对MOD取余，作为当前字符串的哈希值，只要两个字符串哈希值相等，则认为两个字符串相等
     * 一般取P为较大的质数，P=131或P=13331或P=131313，此时产生的哈希冲突低；
     * 一般取MOD=2^63(long类型最大值+1)，在计算时不处理溢出问题，产生溢出相当于自动对MOD取余；
     * 如果产生哈希冲突，则使用双哈希来减少冲突
     * 对[left,right]进行二分查找，left为0，right为s.length()，判断s中是否存在长度为mid的重复子串，
     * 如果s中存在长度为mid的重复子串，则s中最长的重复子串长度在mid或在mid右边，left=mid；
     * 如果s中不存在长度为mid的重复子串，则s中最长的重复子串长度在mid左边，right=mid-1
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String longestDupSubstring3(String s) {
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

        String result = "";

        int left = 0;
        int right = s.length();
        int mid;

        while (left < right) {
            //mid往右偏移，因为转移条件是right=mid-1，避免无法跳出循环
            mid = left + ((right - left) >> 1) + 1;

            //存储s中长度为mid的字符串的哈希值
            Set<Long> set = new HashSet<>();
            //s中长度为mid的重复子串
            String curStr = "";

            for (int i = 0; i <= s.length() - mid; i++) {
                //s[i]-s[i+mid-1]的哈希值
                long h = hash[i + mid] - hash[i] * prime[mid];

                if (set.contains(h)) {
                    curStr = s.substring(i, i + mid);
                    break;
                }

                set.add(h);
            }

            //s中存在长度为mid的重复子串，则left=mid
            if (curStr.length() != 0) {
                left = mid;

                if (curStr.length() > result.length()) {
                    result = curStr;
                }
            } else {
                //s中不存在长度为mid的重复子串，则right=mid-1
                right = mid - 1;
            }
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
         * s中每一个子串都加入前缀树中，并返回s中最长长度的重复子串
         * 时间复杂度O(n^2)，空间复杂度O(1)
         *
         * @param s
         * @return
         */
        public String insert(String s) {
            String result = "";

            for (int i = 0; i < s.length(); i++) {
                TrieNode node = root;

                for (int j = i; j < s.length(); j++) {
                    char c = s.charAt(j);

                    if (!node.children.containsKey(c)) {
                        node.children.put(c, new TrieNode());
                        node.children.get(c).isEnd = true;
                    } else {
                        if (j - i + 1 > result.length()) {
                            result = s.substring(i, j + 1);
                        }
                    }

                    node = node.children.get(c);
                }
            }

            return result;
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
