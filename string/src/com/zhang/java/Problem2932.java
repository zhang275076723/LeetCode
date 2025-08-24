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
     * 排序+前缀树
     * 核心思想：|x-y|<=min(x,y)，假设x<=y，则y-x<=x，得到y<=2x，即x<=y<=2x
     * nums中元素由小到大排序，当前遍历到nums[i]，保证前缀树中nums[j]都满足nums[i]<=nums[j]<=2*nums[i]
     * 时间复杂度O(nlogn+nlogC)=O(nlogn)，空间复杂度O(logn+nlogC)=O(n) (C=max(nums[i]))
     *
     * @param nums
     * @return
     */
    public int maximumStrongPairXor2(int[] nums) {
        //由小到大排序
        heapSort(nums);

        Trie trie = new Trie();
        int maxXorResult = 0;
        int j = 0;

        for (int i = 0; i < nums.length; i++) {
            //当前遍历到nums[i]，保证前缀树中nums[j]都满足nums[j]<=2*nums[i]
            while (j < nums.length && nums[j] <= 2 * nums[i]) {
                trie.insert(nums[j]);
                j++;
            }

            maxXorResult = Math.max(maxXorResult, trie.searchMaxXor(nums[i]));
            //遍历下一个nums[i]，保证前缀树中nums[j]都满足nums[j]>=nums[i]，则移除当前nums[i]
            trie.remove(nums[i]);
        }

        return maxXorResult;
    }

    private void heapSort(int[] nums) {
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        for (int i = nums.length - 1; i > 0; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            heapify(nums, 0, i);
        }
    }

    private void heapify(int[] nums, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && nums[leftIndex] > nums[index]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && nums[rightIndex] > nums[index]) {
            index = rightIndex;
        }

        if (index != i) {
            int temp = nums[i];
            nums[i] = nums[index];
            nums[index] = temp;

            heapify(nums, index, heapSize);
        }
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
         * num二进制表示的每一位从前缀树中删除
         * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
         *
         * @param num
         */
        public void remove(int num) {
            TrieNode node = root;
            node.count--;

            //num都为正数，不需要考虑最高位符号位
            for (int i = 30; i >= 0; i--) {
                int cur = (num >>> i) & 1;
                node = node.children[cur];
                node.count--;
            }

            if (node.count == 0) {
                node.isEnd = false;
            }
        }

        /**
         * 查询前缀树中和num异或的最大值
         * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
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
                int cur = (num >>> i) & 1;

                //前缀树中节点会删除，所以不能只判断子节点是否为空，还需要考虑子节点count
                if (node.children[cur ^ 1] != null && node.children[cur ^ 1].count > 0) {
                    node = node.children[cur ^ 1];
                    xor = (xor << 1) + 1;
                } else {
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
