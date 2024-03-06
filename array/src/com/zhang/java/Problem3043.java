package com.zhang.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2024/2/27 08:21
 * @Author zsy
 * @Description 最长公共前缀的长度 前缀树类比Problem14、Problem208、Problem211、Problem212、Problem336、Problem421、Problem676、Problem677、Problem720、Problem745、Problem820、Problem1166、Problem1804
 * 给你两个 正整数 数组 arr1 和 arr2 。
 * 正整数的 前缀 是其 最左边 的一位或多位数字组成的整数。
 * 例如，123 是整数 12345 的前缀，而 234 不是 。
 * 设若整数 c 是整数 a 和 b 的 公共前缀 ，那么 c 需要同时是 a 和 b 的前缀。
 * 例如，5655359 和 56554 有公共前缀 565 ，而 1223 和 43456 没有 公共前缀。
 * 你需要找出属于 arr1 的整数 x 和属于 arr2 的整数 y 组成的所有数对 (x, y) 之中最长的公共前缀的长度。
 * 返回所有数对之中最长公共前缀的长度。
 * 如果它们之间不存在公共前缀，则返回 0 。
 * <p>
 * 输入：arr1 = [1,10,100], arr2 = [1000]
 * 输出：3
 * 解释：存在 3 个数对 (arr1[i], arr2[j]) ：
 * - (1, 1000) 的最长公共前缀是 1 。
 * - (10, 1000) 的最长公共前缀是 10 。
 * - (100, 1000) 的最长公共前缀是 100 。
 * 最长的公共前缀是 100 ，长度为 3 。
 * <p>
 * 输入：arr1 = [1,2,3], arr2 = [4,4,4]
 * 输出：0
 * 解释：任何数对 (arr1[i], arr2[j]) 之中都不存在公共前缀，因此返回 0 。
 * 请注意，同一个数组内元素之间的公共前缀不在考虑范围内。
 * <p>
 * 1 <= arr1.length, arr2.length <= 5 * 10^4
 * 1 <= arr1[i], arr2[i] <= 10^8
 */
public class Problem3043 {
    public static void main(String[] args) {
        Problem3043 problem3043 = new Problem3043();
        int[] arr1 = {1, 10, 100};
        int[] arr2 = {1000};
        System.out.println(problem3043.longestCommonPrefix(arr1, arr2));
        System.out.println(problem3043.longestCommonPrefix2(arr1, arr2));
    }

    /**
     * 哈希表
     * arr1中整数的全部前缀加入哈希表中，遍历arr2中整数的全部前缀，更新最长公共前缀的长度
     * 时间复杂度O(mlogC+nlogC)=O(m+n)，空间复杂度O(mlogC)=O(m)
     * (m=arr1.length，n=arr2.length，C=max(arr1[i],arr2[i])，arr1和arr2元素在int范围内)
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        Set<Integer> set = new HashSet<>();

        //arr1中整数的全部前缀加入哈希表中
        for (int num : arr1) {
            while (num != 0) {
                set.add(num);
                num = num / 10;
            }
        }

        //最长公共前缀的长度
        int length = 0;

        for (int num : arr2) {
            while (num != 0) {
                //arr1中整数和arr2中整数找到公共前缀，更新length，跳出循环，因为arr2中当前整数再次往后找公共前缀，公共前缀只能更小
                if (set.contains(num)) {
                    length = Math.max(length, String.valueOf(num).length());
                    break;
                }

                num = num / 10;
            }
        }

        return length;
    }

    /**
     * 前缀树
     * arr1中整数从高位到低位的每一位作为前缀树节点插入前缀树中，
     * arr2中整数在前缀树中查询，得到arr2中整数在前缀树中最长匹配的字符串长度，即为最长公共前缀的长度
     * 时间复杂度O(mlogC+nlogC)=O(m+n)，空间复杂度O(mlogC)=O(m)
     * (m=arr1.length，n=arr2.length，C=max(arr1[i],arr2[i])，arr1和arr2元素在int范围内)
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public int longestCommonPrefix2(int[] arr1, int[] arr2) {
        //前缀树
        Trie trie = new Trie();

        //arr1中整数从高位到低位的每一位作为前缀树节点加入前缀树中
        for (int num : arr1) {
            //num转换为string插入前缀树中
            String word = String.valueOf(num);
            trie.insert(word);
        }

        //最长公共前缀的长度
        int max = 0;

        //arr2中整数在前缀树中查询，得到arr2中整数在前缀树中最长匹配的字符串长度，即为最长公共前缀的长度
        for (int num : arr2) {
            //num转换为string在前缀树中查询
            String word = String.valueOf(num);
            max = Math.max(max, trie.search(word));
        }

        return max;
    }

    /**
     * 前缀树
     */
    private static class Trie {
        //根节点
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        /**
         * word插入前缀树中
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param word
         */
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
         * word在前缀树中查询，返回word在前缀树中最长匹配的字符串长度
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param word
         * @return
         */
        public int search(String word) {
            //word在前缀树中最长匹配的字符串长度
            int length = 0;
            TrieNode node = root;

            for (char c : word.toCharArray()) {
                node = node.children.get(c);

                if (node == null) {
                    return length;
                }

                length++;
            }

            return length;
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
