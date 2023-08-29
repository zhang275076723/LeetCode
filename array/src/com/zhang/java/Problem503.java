package com.zhang.java;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Date 2022/11/18 10:07
 * @Author zsy
 * @Description 下一个更大元素 II 类比Problem496、Problem556、Problem2454 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem654、Problem739、Problem795、Problem907、Problem1019、Problem1856、Problem2104、Problem2454、Problem2487、Offer33、DoubleStackSort
 * 给定一个循环数组 nums（ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素 。
 * 数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。
 * 如果不存在，则输出 -1 。
 * <p>
 * 输入: nums = [1,2,1]
 * 输出: [2,-1,2]
 * 解释: 第一个 1 的下一个更大的数是 2；
 * 数字 2 找不到下一个更大的数；
 * 第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
 * <p>
 * 输入: nums = [1,2,3,4,3]
 * 输出: [2,3,4,-1,4]
 * <p>
 * 1 <= nums.length <= 10^4
 * -10^9 <= nums[i] <= 10^9
 */
public class Problem503 {
    public static void main(String[] args) {
        Problem503 problem503 = new Problem503();
        int[] nums = {1, 2, 3, 4, 3};
        System.out.println(Arrays.toString(problem503.nextGreaterElements(nums)));
        System.out.println(Arrays.toString(problem503.nextGreaterElements2(nums)));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            result[i] = -1;

            for (int j = 1; j < nums.length; j++) {
                //当前索引i之后的元素，循环数组，所以需要模length
                int index = (i + j) % nums.length;

                if (nums[i] < nums[index]) {
                    result[i] = nums[index];
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 单调栈 (求当前元素之后，比当前元素大或小的元素，就要想到单调栈)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int[] nextGreaterElements2(int[] nums) {
        int[] result = new int[nums.length];
        //单调递减栈，存放nums中元素下标索引
        Stack<Integer> stack = new Stack<>();

        //循环数组，遍历原数组的2倍，相当于原数组后面又拼接了一遍原数组，除去最后一个元素
        for (int i = 0; i < 2 * nums.length - 1; i++) {
            //结果数组赋初值-1，表示没有比nums[i]更大的元素
            if (i < nums.length) {
                result[i] = -1;
            }

            while (!stack.isEmpty() && nums[stack.peek()] < nums[i % nums.length]) {
                int index = stack.pop();
                result[index] = nums[i % nums.length];
            }

            stack.push(i % nums.length);
        }

        return result;
    }
}
