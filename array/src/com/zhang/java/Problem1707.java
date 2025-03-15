package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/6/9 08:38
 * @Author zsy
 * @Description 与数组中元素的最大异或值 类比Problem421、Problem1803、Problem1938、Problem2479 前缀树类比
 * 给你一个由非负整数组成的数组 nums 。
 * 另有一个查询数组 queries ，其中 queries[i] = [xi, mi] 。
 * 第 i 个查询的答案是 xi 和任何 nums 数组中不超过 mi 的元素按位异或（XOR）得到的最大值。
 * 换句话说，答案是 max(nums[j] XOR xi) ，其中所有 j 均满足 nums[j] <= mi 。
 * 如果 nums 中的所有元素都大于 mi，最终答案就是 -1 。
 * 返回一个整数数组 answer 作为查询的答案，其中 answer.length == queries.length 且 answer[i] 是第 i 个查询的答案。
 * <p>
 * 输入：nums = [0,1,2,3,4], queries = [[3,1],[1,3],[5,6]]
 * 输出：[3,3,7]
 * 解释：
 * 1) 0 和 1 是仅有的两个不超过 1 的整数。0 XOR 3 = 3 而 1 XOR 3 = 2 。二者中的更大值是 3 。
 * 2) 1 XOR 2 = 3.
 * 3) 5 XOR 2 = 7.
 * <p>
 * 输入：nums = [5,2,4,6,6,3], queries = [[12,4],[8,1],[6,3]]
 * 输出：[15,-1,5]
 * <p>
 * 1 <= nums.length, queries.length <= 10^5
 * queries[i].length == 2
 * 0 <= nums[j], xi, mi <= 10^9
 */
public class Problem1707 {
    public static void main(String[] args) {
        Problem1707 problem1707 = new Problem1707();
//        int[] nums = {0, 1, 2, 3, 4};
//        int[][] queries = {{3, 1}, {1, 3}, {5, 6}};
        int[] nums = {5, 2, 4, 6, 6, 3};
        int[][] queries = {{12, 4}, {8, 1}, {6, 3}};
        System.out.println(Arrays.toString(problem1707.maximizeXor(nums, queries)));
        System.out.println(Arrays.toString(problem1707.maximizeXor2(nums, queries)));
    }

    /**
     * 暴力
     * 时间复杂度O(mn)，空间复杂度O(1) (m=nums.length，n=queries.length)
     *
     * @param nums
     * @param queries
     * @return
     */
    public int[] maximizeXor(int[] nums, int[][] queries) {
        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            //queries[i][0]和nums中小于等于queries[i][1]异或的最大值
            int xor = 0;

            for (int j = 0; j < nums.length; j++) {
                if (nums[j] <= queries[i][1]) {
                    xor = Math.max(xor, queries[i][0] ^ nums[j]);
                }
            }

            result[i] = xor;
        }

        return result;
    }

    /**
     * 前缀树
     * 一个数和其他数求异或的最大值，如果当前数当前位为1，则需要找当前位为0的数；如果当前数当前位为0，则需要找当前位为1的数
     * 时间复杂度O(m*logC+nlogC)=O(m+n)，空间复杂度O(m*logC)=O(m) (m=nums.length，n=queries.length) (C=max(nums[i]))
     *
     * @param nums
     * @param queries
     * @return
     */
    public int[] maximizeXor2(int[] nums, int[][] queries) {
        Trie trie = new Trie();

        for (int num : nums) {
            trie.insert(num);
        }

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            result[i] = trie.searchMaxXor(queries[i][0], queries[i][1]);
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
         * num二进制表示的每一位插入前缀树中，同时更新每个节点的min
         * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
         *
         * @param num
         */
        public void insert(int num) {
            TrieNode node = root;
            node.min = Math.min(node.min, num);

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                //num当前位的值
                int cur = (num >>> i) & 1;

                if (node.children[cur] == null) {
                    node.children[cur] = new TrieNode();
                }

                node = node.children[cur];
                node.min = Math.min(node.min, num);
            }

            node.isEnd = true;
        }

        /**
         * 查询前缀树中小于等于limit的值和num异或的最大值，前缀树中不存在小于等于limit的值，则返回-1
         * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
         *
         * @param num
         * @param limit
         * @return
         */
        public int searchMaxXor(int num, int limit) {
            //前缀树中不存在小于等于limit的值，则返回-1
            if (root.min > limit) {
                return -1;
            }

            //前缀树中小于等于limit的值和num异或的最大值
            int xor = 0;
            TrieNode node = root;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                //num当前位的值
                int cur = (num >>> i) & 1;

                //当前节点存在cur的异或值cur^1，并且当前节点包含所有元素的最小值小于等于limit，则xor当前位为1
                if (node.children[cur ^ 1] != null && node.children[cur ^ 1].min <= limit) {
                    node = node.children[cur ^ 1];
                    xor = (xor << 1) + 1;
                } else {
                    //当前节点存在和cur的相同值cur，并且当前节点包含所有元素的最小值小于等于limit，则xor当前位为0
                    node = node.children[cur];
                    xor = xor << 1;
                }
            }

            return xor;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final TrieNode[] children;
            //当前节点包含所有元素的最小值
            private int min;
            private boolean isEnd;

            public TrieNode() {
                children = new TrieNode[2];
                min = Integer.MAX_VALUE;
                isEnd = false;
            }
        }
    }
}
