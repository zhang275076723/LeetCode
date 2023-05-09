package com.zhang.java;

import java.util.Random;

/**
 * @Date 2023/5/8 09:13
 * @Author zsy
 * @Description 按权重随机选择 类比Problem382、Problem398
 * 给你一个 下标从 0 开始 的正整数数组 w ，其中 w[i] 代表第 i 个下标的权重。
 * 请你实现一个函数 pickIndex ，它可以 随机地 从范围 [0, w.length - 1] 内（含 0 和 w.length - 1）选出并返回一个下标。
 * 选取下标 i 的 概率 为 w[i] / sum(w) 。
 * 例如，对于 w = [1, 3]，挑选下标 0 的概率为 1 / (1 + 3) = 0.25 （即，25%），
 * 而选取下标 1 的概率为 3 / (1 + 3) = 0.75（即，75%）。
 * <p>
 * 输入：
 * ["Solution","pickIndex"]
 * [[[1]],[]]
 * 输出：
 * [null,0]
 * 解释：
 * Solution solution = new Solution([1]);
 * solution.pickIndex(); // 返回 0，因为数组中只有一个元素，所以唯一的选择是返回下标 0。
 * <p>
 * 输入：
 * ["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
 * [[[1,3]],[],[],[],[],[]]
 * 输出：
 * [null,1,1,1,1,0]
 * 解释：
 * Solution solution = new Solution([1, 3]);
 * solution.pickIndex(); // 返回 1，返回下标 1，返回该下标概率为 3/4 。
 * solution.pickIndex(); // 返回 1
 * solution.pickIndex(); // 返回 1
 * solution.pickIndex(); // 返回 1
 * solution.pickIndex(); // 返回 0，返回下标 0，返回该下标概率为 1/4 。
 * <p>
 * 由于这是一个随机问题，允许多个答案，因此下列输出都可以被认为是正确的:
 * [null,1,1,1,1,0]
 * [null,1,1,1,1,1]
 * [null,1,1,1,0,0]
 * [null,1,1,1,0,1]
 * [null,1,0,1,0,0]
 * ......
 * 诸若此类。
 * <p>
 * 1 <= w.length <= 10^4
 * 1 <= w[i] <= 10^5
 * pickIndex 将被调用不超过 10^4 次
 */
public class Problem528 {
    public static void main(String[] args) {
        Solution solution = new Solution(new int[]{1, 3});
        // 返回 1，返回下标 1，返回该下标概率为 3/4 。
        System.out.println(solution.pickIndex());
        // 返回 1
        System.out.println(solution.pickIndex());
        // 返回 1
        System.out.println(solution.pickIndex());
        // 返回 1
        System.out.println(solution.pickIndex());
        // 返回 0，返回下标 0，返回该下标概率为 1/4 。
        System.out.println(solution.pickIndex());
    }

    /**
     * 前缀和+二分查找
     */
    static class Solution {
        //前缀和数组，preSum[i]：w[0]-w[i-1]之和
        private final int[] preSum;
        //概率权值数组
        private final int[] w;
        //概率总权值，即preSum[w.length]
        private final int n;
        private final Random random;

        public Solution(int[] w) {
            this.w = w;
            preSum = new int[w.length + 1];
            random = new Random();

            for (int i = 1; i <= w.length; i++) {
                preSum[i] = preSum[i - 1] + w[i - 1];
            }

            n = preSum[w.length];
        }

        /**
         * 根据二分查找确定1-n中随机数在preSum中的下标索引
         * 时间复杂度O(logn)，空间复杂度O(1)
         *
         * @return
         */
        public int pickIndex() {
            //生成1-n的随机数
            int randomNum = random.nextInt(n) + 1;
            int left = 0;
            int right = preSum.length - 1;
            int mid;

            //二分查找
            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (preSum[mid] == randomNum) {
                    //preSum[0]为0，所以需要减1，得到w数组的下标索引
                    return mid - 1;
                } else if (preSum[mid] < randomNum) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            //preSum[0]为0，所以需要减1，得到w数组的下标索引
            return left - 1;
        }
    }
}
