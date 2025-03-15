package com.zhang.java;


/**
 * @Date 2023/9/28 08:38
 * @Author zsy
 * @Description 数组中两个数的最大异或值 类比Problem1707、Problem1803、Problem1938、Problem2479 前缀树类比Problem14、Problem208、Problem211、Problem212、Problem336、Problem676、Problem677、Problem720、Problem745、Problem820、Problem1166、Problem1804、Problem3043 位运算类比
 * 给你一个整数数组 nums ，返回 nums[i] XOR nums[j] 的最大运算结果，其中 0 ≤ i ≤ j < n 。
 * <p>
 * 输入：nums = [3,10,5,25,2,8]
 * 输出：28
 * 解释：最大运算结果是 5 XOR 25 = 28.
 * <p>
 * 输入：nums = [14,70,53,83,49,91,36,80,92,51,66,70]
 * 输出：127
 * <p>
 * 1 <= nums.length <= 2 * 10^5
 * 0 <= nums[i] <= 2^31 - 1
 */
public class Problem421 {
    public static void main(String[] args) {
        Problem421 problem421 = new Problem421();
        int[] nums = {3, 10, 5, 25, 2, 8};
        System.out.println(problem421.findMaximumXOR(nums));
        System.out.println(problem421.findMaximumXOR2(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findMaximumXOR(int[] nums) {
        int maxXorResult = 0;

        //自己异或自己为0，所以不考虑自己和自己的异或
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                maxXorResult = Math.max(maxXorResult, nums[i] ^ nums[j]);
            }
        }

        return maxXorResult;
    }

    /**
     * 前缀树
     * 一个数和其他数求异或的最大值，如果当前数当前位为1，则需要找当前位为0的数；如果当前数当前位为0，则需要找当前位为1的数
     * 时间复杂度O(nlogC)=O(n)，空间复杂度O(nlogC)=O(n) (C=max(nums[i]))
     *
     * @param nums
     * @return
     */
    public int findMaximumXOR2(int[] nums) {
        Trie trie = new Trie();

        //nums数组中元素表示的二进制数由高位到低位插入前缀树中
        for (int num : nums) {
            trie.insert(num);
        }

        int maxXorResult = 0;

        //找前缀树中和num异或的最大值
        for (int num : nums) {
            maxXorResult = Math.max(maxXorResult, trie.searchMaxXor(num));
        }

        return maxXorResult;
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
         * num二进制表示的每一位插入前缀树中
         * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
         *
         * @param num
         */
        public void insert(int num) {
            TrieNode node = root;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                //num当前位的值
                int cur = (num >>> i) & 1;

                if (node.children[cur] == null) {
                    node.children[cur] = new TrieNode();
                }

                node = node.children[cur];
            }

            node.isEnd = true;
        }

        /**
         * 查询前缀树中和num异或的最大值
         * 时间复杂度O(log(num))，空间复杂度O(1)
         *
         * @param num
         * @return
         */
        public int searchMaxXor(int num) {
            //前缀树中和num异或的最大值
            int xor = 0;
            TrieNode node = root;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                //num当前位的值
                int cur = (num >>> i) & 1;

                //当前节点存在cur的异或值cur^1，则xor当前位为1
                if (node.children[cur ^ 1] != null) {
                    node = node.children[cur ^ 1];
                    xor = (xor << 1) + 1;
                } else {
                    //当前节点存在和cur的相同值cur，则xor当前位为0
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
            //当前节点的子节点数组，只包含0或1
            private final TrieNode[] children;
            //当前节点是否是一个添加到前缀树的字符串的结尾节点
            private boolean isEnd;

            public TrieNode() {
                children = new TrieNode[2];
                isEnd = false;
            }
        }
    }
}
