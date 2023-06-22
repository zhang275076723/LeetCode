package com.zhang.java;


import java.util.Stack;

/**
 * @Date 2023/5/3 08:43
 * @Author zsy
 * @Description 132 模式 双指针类比Problem15、Problem16、Problem18、Problem611 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem496、Problem503、Problem739、Problem1019、Offer33、DoubleStackSort、IntervalMinMultiplyIntervalSumMax
 * 给你一个整数数组 nums ，数组中共有 n 个整数。
 * 132 模式的子序列 由三个整数 nums[i]、nums[j] 和 nums[k] 组成，并同时满足：i < j < k 和 nums[i] < nums[k] < nums[j] 。
 * 如果 nums 中存在 132 模式的子序列 ，返回 true ；否则，返回 false 。
 * <p>
 * 输入：nums = [1,2,3,4]
 * 输出：false
 * 解释：序列中不存在 132 模式的子序列。
 * <p>
 * 输入：nums = [3,1,4,2]
 * 输出：true
 * 解释：序列中有 1 个 132 模式的子序列： [1, 4, 2] 。
 * <p>
 * 输入：nums = [-1,3,2,0]
 * 输出：true
 * 解释：序列中有 3 个 132 模式的的子序列：[-1, 3, 2]、[-1, 3, 0] 和 [-1, 2, 0] 。
 * <p>
 * n == nums.length
 * 1 <= n <= 2 * 10^5
 * -10^9 <= nums[i] <= 10^9
 */
public class Problem456 {
    public static void main(String[] args) {
        Problem456 problem456 = new Problem456();
//        int[] nums = {1, 2, 3, 4};
        int[] nums = {-1, 3, 2, 0};
        System.out.println(problem456.find132pattern(nums));
        System.out.println(problem456.find132pattern2(nums));
        System.out.println(problem456.find132pattern3(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^3)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public boolean find132pattern(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }

        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                //位置1的值大于位置3的值，则不是132模式，返回false
                if (nums[i] >= nums[j]) {
                    continue;
                }

                for (int k = j + 1; k < nums.length; k++) {
                    //存在132模式的子序列，返回true
                    if (nums[i] < nums[k] && nums[k] < nums[j]) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 双指针
     * 每次确定nums[j]，往nums[j]左边找小于nums[j]的最小值nums[i]，往nums[j]右边找小于nums[j]的最大值nums[k]，
     * 如果存在nums[i]和nums[k]，满足nums[i]小于nums[k]，nums[k]小于nums[j]，则存在132模式的子序列，返回true；
     * 否则，返回false
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public boolean find132pattern2(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }

        //先确定nums[j]，然后往nums[j]两边找nums[i]和nums[k]
        for (int j = 1; j < nums.length - 1; j++) {
            int i = j - 1;
            int k = j + 1;

            //往nums[j]左边找小于nums[j]的最小值nums[i]
            for (int m = 0; m < j; m++) {
                if (nums[m] < nums[j] && nums[m] < nums[i]) {
                    i = m;
                }
            }

            //往nums[j]右边找小于nums[j]的最大值nums[k]
            for (int m = j + 1; m < nums.length; m++) {
                if (nums[m] < nums[j] && nums[m] > nums[k]) {
                    k = m;
                }
            }

            //存在132模式的子序列，返回true
            if (nums[i] < nums[k] && nums[k] < nums[j]) {
                return true;
            }
        }

        return false;
    }

    /**
     * 单调栈
     * 逆序遍历数组，如果当前元素小于当前最大值，则存在132模式，返回true
     * 如果当前元素不满足单调递减栈，栈中元素出栈，更新当前的最大元素，作为132模式中的2
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public boolean find132pattern3(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }

        //单调递减栈，作为132模式中的3
        Stack<Integer> stack = new Stack<>();
        //从后往前遍历中当前的最大元素，作为132模式中的2，初始化为int最小值
        int max = Integer.MIN_VALUE;

        for (int i = nums.length - 1; i >= 0; i--) {
            //当前元素小于当前最大值，则当前元素同样小于栈顶元素，即存在132模式，返回true
            if (nums[i] < max) {
                return true;
            }

            //当前元素不满足单调递减栈，栈中元素出栈，更新当前的最大元素max，作为132模式中的2
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                int index = stack.pop();
                max = Math.max(max, nums[index]);
            }

            //当前元素入栈
            stack.push(i);
        }

        //遍历结束，则不存在132模式，返回false
        return false;
    }
}
