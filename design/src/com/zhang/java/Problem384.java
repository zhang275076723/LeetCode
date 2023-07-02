package com.zhang.java;

import java.util.Arrays;
import java.util.Random;

/**
 * @Date 2023/4/22 08:33
 * @Author zsy
 * @Description 打乱数组 腾讯面试题 字节面试题 类比Problem519、Random1_100
 * 给你一个整数数组 nums ，设计算法来打乱一个没有重复元素的数组。打乱后，数组的所有排列应该是 等可能 的。
 * 实现 Solution class:
 * Solution(int[] nums) 使用整数数组 nums 初始化对象
 * int[] reset() 重设数组到它的初始状态并返回
 * int[] shuffle() 返回数组随机打乱后的结果
 * <p>
 * 输入
 * ["Solution", "shuffle", "reset", "shuffle"]
 * [[[1, 2, 3]], [], [], []]
 * 输出
 * [null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]
 * 解释
 * Solution solution = new Solution([1, 2, 3]);
 * solution.shuffle();    // 打乱数组 [1,2,3] 并返回结果。任何 [1,2,3]的排列返回的概率应该相同。例如，返回 [3, 1, 2]
 * solution.reset();      // 重设数组到它的初始状态 [1, 2, 3] 。返回 [1, 2, 3]
 * solution.shuffle();    // 随机返回数组 [1, 2, 3] 打乱后的结果。例如，返回 [1, 3, 2]
 * <p>
 * 1 <= nums.length <= 50
 * -10^6 <= nums[i] <= 10^6
 * nums 中的所有元素都是 唯一的
 * 最多可以调用 104 次 reset 和 shuffle
 */
public class Problem384 {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        Solution solution = new Solution(nums);
        // 打乱数组 [1,2,3] 并返回结果。任何 [1,2,3]的排列返回的概率应该相同。例如，返回 [3, 1, 2]
        System.out.println(Arrays.toString(solution.shuffle()));
        // 重设数组到它的初始状态 [1, 2, 3] 。返回 [1, 2, 3]
        System.out.println(Arrays.toString(solution.reset()));
        // 随机返回数组 [1, 2, 3] 打乱后的结果。例如，返回 [1, 3, 2]
        System.out.println(Arrays.toString(solution.shuffle()));
    }

    static class Solution {
        //用于获取随机数
        private final Random random;
        //保存原始数组，用于复原
        private final int[] originNums;
        //进行随机洗牌的数组
        private final int[] nums;

        public Solution(int[] nums) {
            random = new Random();
            originNums = new int[nums.length];
            this.nums = nums;

            for (int i = 0; i < nums.length; i++) {
                originNums[i] = nums[i];
            }
        }

        /**
         * 通过originNums对nums进行复原
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @return
         */
        public int[] reset() {
            for (int i = 0; i < originNums.length; i++) {
                nums[i] = originNums[i];
            }

            return nums;
        }

        /**
         * 遍历nums数组，每次选[0,i]的随机下标索引和当前下标索引i交换
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @return
         */
        public int[] shuffle() {
            for (int i = 0; i < nums.length; i++) {
                int index = random.nextInt(i + 1);
                swap(nums, i, index);
            }

            return nums;
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
