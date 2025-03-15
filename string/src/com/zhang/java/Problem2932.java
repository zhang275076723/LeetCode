package com.zhang.java;

/**
 * @Date 2025/4/29 08:45
 * @Author zsy
 * @Description 找出强数对的最大异或值 I 同Problem2935 类比Problem421、Problem1707、Problem1803、Problem1938、Problem2479、Problem2935 前缀树类比
 * 给你一个下标从 0 开始的整数数组 nums 。
 * 如果一对整数 x 和 y 满足以下条件，则称其为 强数对 ：
 * |x - y| <= min(x, y)
 * 你需要从 nums 中选出两个整数，且满足：这两个整数可以形成一个强数对，并且它们的按位异或（XOR）值是在该数组所有强数对中的 最大值 。
 * 返回数组 nums 所有可能的强数对中的 最大 异或值。
 * 注意，你可以选择同一个整数两次来形成一个强数对。
 * <p>
 * 输入：nums = [1,2,3,4,5]
 * 输出：7
 * 解释：数组 nums 中有 11 个强数对：(1, 1), (1, 2), (2, 2), (2, 3), (2, 4), (3, 3), (3, 4), (3, 5), (4, 4), (4, 5) 和 (5, 5) 。
 * 这些强数对中的最大异或值是 3 XOR 4 = 7 。
 * <p>
 * 输入：nums = [10,100]
 * 输出：0
 * 解释：数组 nums 中有 2 个强数对：(10, 10) 和 (100, 100) 。
 * 这些强数对中的最大异或值是 10 XOR 10 = 0 ，数对 (100, 100) 的异或值也是 100 XOR 100 = 0 。
 * <p>
 * 输入：nums = [5,6,25,30]
 * 输出：7
 * 解释：数组 nums 中有 6 个强数对：(5, 5), (5, 6), (6, 6), (25, 25), (25, 30) 和 (30, 30) 。
 * 这些强数对中的最大异或值是 25 XOR 30 = 7 ；另一个异或值非零的数对是 (5, 6) ，其异或值是 5 XOR 6 = 3 。
 * <p>
 * 1 <= nums.length <= 50
 * 1 <= nums[i] <= 100
 */
public class Problem2932 {
    public static void main(String[] args) {
        Problem2932 problem2932 = new Problem2932();
        int[] nums = {1, 2, 3, 4, 5};
//        int[] nums = {5, 6, 25, 30};
        System.out.println(problem2932.maximumStrongPairXor(nums));
        System.out.println(problem2932.maximumStrongPairXor2(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int maximumStrongPairXor(int[] nums) {
        int maxXorResult = 0;

        for (int num1 : nums) {
            for (int num2 : nums) {
                if (Math.abs(num1 - num2) <= Math.min(num1, num2)) {
                    maxXorResult = Math.max(maxXorResult, num1 ^ num2);
                }
            }
        }

        return maxXorResult;
    }

    /**
     * 前缀树
     * 核心思想：|x-y|<=min(x,y)，假设x<=y，则y-x<=x，得到x>=y/2，因为x、y都为整形，所以(y+1)/2<=x<=y
     * 前缀树中每个节点记录当前节点包含所有元素的最大值，如果当前遍历到节点的子节点的max大于等于(num+1)/2，并且小于等于num
     * 则当前进行异或运算的值num和当前节点的子节点对应的值满足强数对要求，继续往当前子节点遍历
     * 时间复杂度O(nlogC)=O(n)，空间复杂度O(nlogC)=O(n) (C=max(nums[i]))
     *
     * @param nums
     * @return
     */
    public int maximumStrongPairXor2(int[] nums) {
        Trie trie = new Trie();

        for (int num : nums) {
            trie.insert(num);
        }

        int maxXorResult = 0;

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
         * num二进制表示的每一位插入前缀树中，同时更新每个节点的max
         * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
         *
         * @param num
         */
        public void insert(int num) {
            TrieNode node = root;
            node.max = Math.max(node.max, num);

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                //num当前位的值
                int cur = (num >>> i) & 1;

                if (node.children[cur] == null) {
                    node.children[cur] = new TrieNode();
                }

                node = node.children[cur];
                node.max = Math.max(node.max, num);
            }

            node.isEnd = true;
        }

        /**
         * 查询前缀树中大于等于(num+1)/2，并且小于等于num的值，和num异或的最大值
         * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
         *
         * @param num
         * @return
         */
        public int searchMaxXor(int num) {
            //前缀树中大于等于(num+1)/2，并且小于等于num的值，和num异或的最大值
            int xor = 0;
            TrieNode node = root;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                //num当前位的值
                int cur = (num >>> i) & 1;

                //当前节点存在cur^1的子节点，并且cur^1的子节点包含所有元素的最大值大于等于(num+1)/2，并且小于等于num，则xor当前位为1
                if (node.children[cur ^ 1] != null && node.children[cur ^ 1].max >= (num + 1) / 2
                        && node.children[cur ^ 1].max <= num) {
                    node = node.children[cur ^ 1];
                    xor = (xor << 1) + 1;
                } else {
                    //其他情况，则xor当前位为0
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
            //当前节点包含所有元素的最大值
            private int max;
            private boolean isEnd;

            public TrieNode() {
                children = new TrieNode[2];
                max = Integer.MIN_VALUE;
                isEnd = false;
            }
        }
    }
}
