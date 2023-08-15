package com.zhang.java;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Date 2023/8/14 08:22
 * @Author zsy
 * @Description 下一个更大元素 IV 类比Problem496、Problem503、Problem556 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem654、Problem739、Problem907、Problem1019、Problem1856、Problem2104、Problem2487、Offer33、DoubleStackSort
 * 给你一个下标从 0 开始的非负整数数组 nums 。对于 nums 中每一个整数，你必须找到对应元素的 第二大 整数。
 * 如果 nums[j] 满足以下条件，那么我们称它为 nums[i] 的 第二大 整数：
 * j > i
 * nums[j] > nums[i]
 * 恰好存在 一个 k 满足 i < k < j 且 nums[k] > nums[i] 。
 * 如果不存在 nums[j] ，那么第二大整数为 -1 。
 * 比方说，数组 [1, 2, 4, 3] 中，1 的第二大整数是 4 ，2 的第二大整数是 3 ，3 和 4 的第二大整数是 -1 。
 * 请你返回一个整数数组 answer ，其中 answer[i]是 nums[i] 的第二大整数。
 * <p>
 * 输入：nums = [2,4,0,9,6]
 * 输出：[9,6,6,-1,-1]
 * 解释：
 * 下标为 0 处：2 的右边，4 是大于 2 的第一个整数，9 是第二个大于 2 的整数。
 * 下标为 1 处：4 的右边，9 是大于 4 的第一个整数，6 是第二个大于 4 的整数。
 * 下标为 2 处：0 的右边，9 是大于 0 的第一个整数，6 是第二个大于 0 的整数。
 * 下标为 3 处：右边不存在大于 9 的整数，所以第二大整数为 -1 。
 * 下标为 4 处：右边不存在大于 6 的整数，所以第二大整数为 -1 。
 * 所以我们返回 [9,6,6,-1,-1] 。
 * <p>
 * 输入：nums = [3,3]
 * 输出：[-1,-1]
 * 解释：
 * 由于每个数右边都没有更大的数，所以我们返回 [-1,-1] 。
 * <p>
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 */
public class Problem2454 {
    public static void main(String[] args) {
        Problem2454 problem2454 = new Problem2454();
        int[] nums = {2, 4, 0, 9, 6};
        System.out.println(Arrays.toString(problem2454.secondGreaterElement(nums)));
        System.out.println(Arrays.toString(problem2454.secondGreaterElement2(nums)));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] secondGreaterElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            //初始化-1，表示nums[i]右边不存在2个比nums[i]大的元素
            result[i] = -1;
            //nums[i]右边比nums[i]大的元素个数
            int count = 0;

            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] < nums[j]) {
                    count++;
                    if (count == 2) {
                        result[i] = nums[j];
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * 双单调栈
     * 第一个单调递减栈存放右边暂时没有更大值的元素下标索引，第二个单调递减栈存放右边暂时只有一个更大值的元素下标索引
     * 第二个单调递减栈小于当前元素，则第二个单调递减栈栈顶元素右边存在2个比栈顶元素大的元素；
     * 第一个单调递减栈小于当前元素，则第一个单调递减栈栈顶元素右边存在比栈顶元素大的元素，栈顶元素出栈，入第二个单调递减栈
     * (注意：从第一个单调递减栈出栈入第二个单调递减栈的元素依然要保持单调递减，所以使用一个临时栈保存从第一个单调递减栈出栈的元素)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int[] secondGreaterElement2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        int[] result = new int[nums.length];
        //单调递减栈，存放右边暂时没有更大值的元素下标索引
        Stack<Integer> stack1 = new Stack<>();
        //单调递减栈，存放右边暂时只有一个更大值的元素下标索引
        Stack<Integer> stack2 = new Stack<>();

        for (int i = 0; i < nums.length; i++) {
            //初始化-1，表示nums[i]右边不存在2个比nums[i]大的元素
            result[i] = -1;

            //第二个单调递减栈小于当前元素，则第二个单调递减栈栈顶元素右边存在2个比栈顶元素大的元素
            while (!stack2.isEmpty() && nums[stack2.peek()] < nums[i]) {
                int index = stack2.pop();
                result[index] = nums[i];
            }

            //临时栈，保存从第一个单调递减栈出栈的元素，保证从stack1出栈的元素入stack2仍然保持单调递减
            Stack<Integer> tempStack = new Stack<>();

            //第一个单调递减栈小于当前元素，则第一个单调递减栈栈顶元素右边存在比栈顶元素大的元素，
            //栈顶元素出栈，先入临时栈，再入第二个单调递减栈
            while (!stack1.isEmpty() && nums[stack1.peek()] < nums[i]) {
                int index = stack1.pop();
                tempStack.push(index);
            }

            //临时栈中元素出栈，入第二个单调递减栈，保证从stack1出栈的元素入stack2仍然保持单调递减
            while (!tempStack.isEmpty()) {
                stack2.push(tempStack.pop());
            }

            //当前元素下标索引入第一个单调递减栈，表示当前元素右边暂时没有更大值的元素
            stack1.push(i);
        }

        return result;
    }
}
