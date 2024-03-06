package com.zhang.java;


/**
 * @Date 2023/9/28 08:38
 * @Author zsy
 * @Description 数组中两个数的最大异或值 前缀树类比Problem14、Problem208、Problem211、Problem212、Problem336、Problem676、Problem677、Problem720、Problem745、Problem820、Problem1804、Problem3043 位运算类比
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

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                maxXorResult = Math.max(maxXorResult, nums[i] ^ nums[j]);
            }
        }

        return maxXorResult;
    }

    /**
     * 前缀树
     * 一个数和其他数求异或的最大值，如果当前数当前位为1，则需要找当前位为0的数，接着往低位找，直至找到和当前数异或的最大值对应的数
     * 时间复杂度O(nlogC)=O(n*32)=O(n)，空间复杂度O(n) (C：int表示的最大数)
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

        //找前缀树中和nums[i]异或的最大值对应的数
        for (int i = 0; i < nums.length; i++) {
            //前缀树中从nums[i]表示的二进制数中从高位到低位尽可能不同的数，
            //即nums[i]当前位为0，则要找前缀树中当前位为1的值；nums[i]当前位为1，则要找前缀树中当前位为0的值
            int num = trie.search(nums[i]);
            maxXorResult = Math.max(maxXorResult, nums[i] ^ num);
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
         * 将num表示的二进制数由高位到低位(num都大于等于0，不考虑最高位符号位，共31位)插入前缀树
         * 时间复杂度O(logC)，空间复杂度O(1)
         *
         * @param num
         */
        public void insert(int num) {
            TrieNode node = root;

            //num表示的二进制数由高位到低位(num都大于等于0，不考虑最高位符号位，共31位)插入前缀树
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
         * 找前缀树中和num异或的最大值对应的数
         * num当前位为0，则需要找前缀树中当前位为1的节点，如果前缀树中不存在当前位为1的节点，则只能找前缀树中当前位为0的节点；
         * num当前位为1，则需要找前缀树中当前位为0的节点，如果前缀树中不存在当前位为0的节点，则只能找前缀树中当前位为1的节点
         * 时间复杂度O(logC)，空间复杂度O(1)
         *
         * @param num
         * @return
         */
        public int search(int num) {
            //前缀树中和num异或的最大值对应的数
            int result = 0;
            TrieNode node = root;

            //找前缀树中和num表示的二进制数由高位到低位(num都大于等于0，不考虑最高位符号位，共31位)异或的最大值对应的数
            for (int i = 30; i >= 0; i--) {
                //num当前位的值
                int cur = (num >>> i) & 1;

                //num当前位为0，则需要找前缀树中当前位为1的节点，如果前缀树中不存在当前位为1的节点，则只能找前缀树中当前位为0的节点
                if (cur == 0) {
                    if (node.children[1] != null) {
                        node = node.children[1];
                        result = (result << 1) + 1;
                    } else {
                        node = node.children[0];
                        result = result << 1;
                    }
                } else {
                    //num当前位为1，则需要找前缀树中当前位为0的节点，如果前缀树中不存在当前位为0的节点，则只能找前缀树中当前位为1的节点
                    if (node.children[0] != null) {
                        node = node.children[0];
                        result = result << 1;
                    } else {
                        node = node.children[1];
                        result = (result << 1) + 1;
                    }
                }
            }

            return result;
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
