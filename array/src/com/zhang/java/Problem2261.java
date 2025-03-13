package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2025/4/25 08:47
 * @Author zsy
 * @Description 含最多 K 个可整除元素的子数组 前缀树类比
 * 给你一个整数数组 nums 和两个整数 k 和 p ，找出并返回满足要求的不同的子数组数，要求子数组中最多 k 个可被 p 整除的元素。
 * 如果满足下述条件之一，则认为数组 nums1 和 nums2 是 不同 数组：
 * 两数组长度 不同 ，或者
 * 存在 至少 一个下标 i 满足 nums1[i] != nums2[i] 。
 * 子数组 定义为：数组中的连续元素组成的一个 非空 序列。
 * <p>
 * 输入：nums = [2,3,3,2,2], k = 2, p = 2
 * 输出：11
 * 解释：
 * 位于下标 0、3 和 4 的元素都可以被 p = 2 整除。
 * 共计 11 个不同子数组都满足最多含 k = 2 个可以被 2 整除的元素：
 * [2]、[2,3]、[2,3,3]、[2,3,3,2]、[3]、[3,3]、[3,3,2]、[3,3,2,2]、[3,2]、[3,2,2] 和 [2,2] 。
 * 注意，尽管子数组 [2] 和 [3] 在 nums 中出现不止一次，但统计时只计数一次。
 * 子数组 [2,3,3,2,2] 不满足条件，因为其中有 3 个元素可以被 2 整除。
 * <p>
 * 输入：nums = [1,2,3,4], k = 4, p = 1
 * 输出：10
 * 解释：
 * nums 中的所有元素都可以被 p = 1 整除。
 * 此外，nums 中的每个子数组都满足最多 4 个元素可以被 1 整除。
 * 因为所有子数组互不相同，因此满足所有限制条件的子数组总数为 10 。
 * <p>
 * 1 <= nums.length <= 200
 * 1 <= nums[i], p <= 200
 * 1 <= k <= nums.length
 */
public class Problem2261 {
    public static void main(String[] args) {
        Problem2261 problem2261 = new Problem2261();
        int[] nums = {2, 3, 3, 2, 2};
        int k = 2;
        int p = 2;
        System.out.println(problem2261.countDistinct(nums, k, p));
        System.out.println(problem2261.countDistinct2(nums, k, p));
    }

    /**
     * 模拟
     * 时间复杂度O(n^3)，空间复杂度O(n^3) (共O(n^2)个子数组，每个子数组长度O(n))
     *
     * @param nums
     * @param k
     * @param p
     * @return
     */
    public int countDistinct(int[] nums, int k, int p) {
        //存储最多包含k个被p整除元素的子数组的集合
        Set<String> set = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            StringBuilder sb = new StringBuilder();
            //nums[i]-nums[j]包含被p整除元素的个数
            int count = 0;

            for (int j = i; j < nums.length; j++) {
                if (nums[j] % p == 0) {
                    count++;

                    if (count > k) {
                        break;
                    }
                }

                //必须添加间隔符，避免出现"12"，不知道是1个元素还是2个元素构成的情况
                sb.append(nums[j]).append(' ');
                set.add(sb.toString());
            }
        }

        return set.size();
    }

    /**
     * 前缀树
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param nums
     * @param k
     * @param p
     * @return
     */
    public int countDistinct2(int[] nums, int k, int p) {
        Trie trie = new Trie();

        return trie.insert(nums, k, p);
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
         * nums中元素插入前缀树中，并返回最多有k个被p整除元素的不同子数组的个数
         *
         * @param nums
         * @param k
         * @param p
         * @return
         */
        public int insert(int[] nums, int k, int p) {
            int result = 0;

            for (int i = 0; i < nums.length; i++) {
                TrieNode node = root;
                //nums[i]-nums[j]包含被p整除元素的个数
                int count = 0;

                for (int j = i; j < nums.length; j++) {
                    if (nums[j] % p == 0) {
                        count++;

                        if (count > k) {
                            break;
                        }
                    }

                    if (node.children[nums[j]] == null) {
                        node.children[nums[j]] = new TrieNode();
                        node.children[nums[j]].isEnd = true;
                        result++;
                    }

                    node = node.children[nums[j]];
                }
            }

            return result;
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            private final TrieNode[] children;
            private boolean isEnd;

            public TrieNode() {
                //nums[i]范围在1-200之前
                children = new TrieNode[201];
                isEnd = false;
            }
        }
    }
}
