package com.zhang.java;

/**
 * @Date 2022/5/9 12:21
 * @Author zsy
 * @Description 乘积最大子数组 数组类比Problem53、Problem135、Problem238、Problem416、Problem581、Offer42、Offer66
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续子数组（该子数组中至少包含一个数字），
 * 并返回该子数组所对应的乘积。
 * 测试用例的答案是一个 32-位 整数。
 * 子数组 是数组的连续子序列。
 * <p>
 * 输入: nums = [2,3,-2,4]
 * 输出: 6
 * 解释: 子数组 [2,3] 有最大乘积 6。
 * <p>
 * 输入: nums = [-2,0,-1]
 * 输出: 0
 * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
 * <p>
 * 1 <= nums.length <= 2 * 10^4
 * -10 <= nums[i] <= 10
 * nums 的任何前缀或后缀的乘积都 保证 是一个 32-位 整数
 */
public class Problem152 {
    public static void main(String[] args) {
        Problem152 problem152 = new Problem152();
        int[] nums = {5, 6, -3, 4, -3};
        System.out.println(problem152.maxProduct(nums));
        System.out.println(problem152.maxProduct2(nums));
        System.out.println(problem152.maxProduct3(nums));
    }

    /**
     * 动态规划
     * dpMax[i]：以nums[i]结尾的最大乘积子数组
     * dpMin[i]：以nums[i]结尾的最小乘积子数组
     * dpMax[i] = max(dpMax[i-1]*nums[i], dpMin[i-1]*nums[i], nums[i])
     * dpMin[i] = min(dpMax[i-1]*nums[i], dpMin[i-1]*nums[i], nums[i])
     * max = max(max, dpMax[i])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] dpMax = new int[nums.length];
        int[] dpMin = new int[nums.length];
        dpMax[0] = nums[0];
        dpMin[0] = nums[0];
        int max = nums[0];

        for (int i = 1; i < nums.length; i++) {
            dpMax[i] = Math.max(nums[i],
                    Math.max(dpMax[i - 1] * nums[i], dpMin[i - 1] * nums[i]));
            dpMin[i] = Math.min(nums[i],
                    Math.min(dpMax[i - 1] * nums[i], dpMin[i - 1] * nums[i]));

            max = Math.max(max, dpMax[i]);
        }

        return max;
    }

    /**
     * 动态规划优化，使用滚动数组
     * dpMax = max(上次循环的dpMax*nums[i], 上次循环的dpMin*nums[i], nums[i])
     * dpMin = min(上次循环的dpMax*nums[i], 上次循环的dpMin*nums[i], nums[i])
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int maxProduct2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int dpMax = nums[0];
        int dpMin = nums[0];
        int max = nums[0];

        for (int i = 1; i < nums.length; i++) {
            //上次循环的dpMax，如果直接使用dpMax，则会修改本次要使用的dpMax
            int tempMax = dpMax;
            //上次循环的dpMin，如果直接使用dpMin，则会修改本次要使用的dpMin
            int tempMin = dpMin;

            dpMax = Math.max(nums[i],
                    Math.max(tempMax * nums[i], tempMin * nums[i]));
            dpMin = Math.min(nums[i],
                    Math.min(tempMax * nums[i], tempMin * nums[i]));

            max = Math.max(max, dpMax);
        }

        return max;
    }

    /**
     * 两次遍历
     * 第一次从左往右遍历，进行相乘，如果相乘结果是0，就赋值为1，每次相乘都和最大值进行比较
     * 第二次从右往左遍历，进行相乘，如果相乘结果是0，就赋值为1，每次相乘都和最大值进行比较
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int maxProduct3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        int tempMax = 1;

        for (int i = 0; i < nums.length; i++) {
            tempMax = tempMax * nums[i];
            max = Math.max(max, tempMax);

            if (tempMax == 0) {
                tempMax = 1;
            }
        }

        tempMax = 1;

        for (int i = nums.length - 1; i >= 0; i--) {
            tempMax = tempMax * nums[i];
            max = Math.max(max, tempMax);

            if (tempMax == 0) {
                tempMax = 1;
            }
        }

        return max;
    }
}
