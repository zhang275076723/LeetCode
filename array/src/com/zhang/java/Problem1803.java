package com.zhang.java;

/**
 * @Date 2024/6/10 08:55
 * @Author zsy
 * @Description 统计异或值在范围内的数对有多少 类比Problem421、Problem1707、Problem1938、Problem2479、Problem2932、Problem2935 前缀树类比
 * 给你一个整数数组 nums （下标 从 0 开始 计数）以及两个整数：low 和 high ，请返回 漂亮数对 的数目。
 * 漂亮数对 是一个形如 (i, j) 的数对，其中 0 <= i < j < nums.length 且 low <= (nums[i] XOR nums[j]) <= high 。
 * <p>
 * 输入：nums = [1,4,2,7], low = 2, high = 6
 * 输出：6
 * 解释：所有漂亮数对 (i, j) 列出如下：
 * - (0, 1): nums[0] XOR nums[1] = 5
 * - (0, 2): nums[0] XOR nums[2] = 3
 * - (0, 3): nums[0] XOR nums[3] = 6
 * - (1, 2): nums[1] XOR nums[2] = 6
 * - (1, 3): nums[1] XOR nums[3] = 3
 * - (2, 3): nums[2] XOR nums[3] = 5
 * <p>
 * 输入：nums = [9,8,4,2,1], low = 5, high = 14
 * 输出：8
 * 解释：所有漂亮数对 (i, j) 列出如下：
 * - (0, 2): nums[0] XOR nums[2] = 13
 * - (0, 3): nums[0] XOR nums[3] = 11
 * - (0, 4): nums[0] XOR nums[4] = 8
 * - (1, 2): nums[1] XOR nums[2] = 12
 * - (1, 3): nums[1] XOR nums[3] = 10
 * - (1, 4): nums[1] XOR nums[4] = 9
 * - (2, 3): nums[2] XOR nums[3] = 6
 * - (2, 4): nums[2] XOR nums[4] = 5
 * <p>
 * 1 <= nums.length <= 2 * 10^4
 * 1 <= nums[i] <= 2 * 10^4
 * 1 <= low <= high <= 2 * 10^4
 */
public class Problem1803 {
    public static void main(String[] args) {
        Problem1803 problem1803 = new Problem1803();
        int[] nums = {1, 4, 2, 7};
        int low = 2;
        int high = 6;
        System.out.println(problem1803.countPairs(nums, low, high));
        System.out.println(problem1803.countPairs2(nums, low, high));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param low
     * @param high
     * @return
     */
    public int countPairs(int[] nums, int low, int high) {
        int count = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (low <= (nums[i] ^ nums[j]) && (nums[i] ^ nums[j]) <= high) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 前缀树
     * 一个数和其他数求异或的最大值，如果当前数当前位为1，则需要找当前位为0的数；如果当前数当前位为0，则需要找当前位为1的数
     * 时间复杂度O(nlogC)=O(n)，空间复杂度O(nlogC)=O(n) (C=max(nums[i]))
     *
     * @param nums
     * @param low
     * @param high
     * @return
     */
    public int countPairs2(int[] nums, int low, int high) {
        Trie trie = new Trie();
        int count = 0;

        for (int num : nums) {
            //注意：先查询num对应的漂亮数对，再将num加入前缀树
            count = count + trie.searchXorCount(num, high) - trie.searchXorCount(num, low - 1);
            trie.insert(num);
        }

        return count;
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
         * num二进制表示的每一位插入前缀树中，同时更新每个节点的count
         * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
         *
         * @param num
         */
        public void insert(int num) {
            TrieNode node = root;
            node.count++;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                //num当前位的值
                int cur = (num >>> i) & 1;

                if (node.children[cur] == null) {
                    node.children[cur] = new TrieNode();
                }

                node = node.children[cur];
                node.count++;
            }

            node.isEnd = true;
        }

        /**
         * 查询前缀树中和num异或结果小于等于limit的个数
         * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
         *
         * @param num
         * @param limit
         * @return
         */
        public int searchXorCount(int num, int limit) {
            //前缀树中和num异或结果小于等于limit的个数
            int count = 0;
            TrieNode node = root;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                //num当前位的值
                int cur = (num >>> i) & 1;
                //limit当前位的值
                int cur2 = (limit >>> i) & 1;

                //limit当前位为1，则当前节点子节点为cur的值和num异或结果都小于limit，因为cur^cur得到的当前位为0，继续遍历当前节点子节点为cur^1的节点
                if (cur2 == 1) {
                    //当前节点存在子节点为cur的节点
                    if (node.children[cur] != null) {
                        count = count + node.children[cur].count;
                    }

                    //不存在当前节点子节点为cur^1的节点，则已经找到前缀树中和num异或结果小于等于limit的个数，直接返回
                    if (node.children[cur ^ 1] == null) {
                        return count;
                    }

                    //当前节点子节点为cur^1的节点
                    node = node.children[cur ^ 1];
                } else {
                    //limit当前位为0，继续遍历当前节点子节点为cur的节点

                    //不存在当前节点子节点为cur的节点，则已经找到前缀树中和num异或结果小于等于limit的个数，直接返回
                    if (node.children[cur] == null) {
                        return count;
                    }

                    //当前节点子节点为cur的节点
                    node = node.children[cur];
                }
            }

            //遍历到叶节点，叶节点表示的值和num异或结果等于limit，即叶节点count也要添加
            count = count + node.count;

            return count;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final TrieNode[] children;
            //前缀树中根节点到当前节点作为二进制前缀包含的元素个数
            private int count;
            private boolean isEnd;

            public TrieNode() {
                children = new TrieNode[2];
                count = 0;
                isEnd = false;
            }
        }
    }
}
