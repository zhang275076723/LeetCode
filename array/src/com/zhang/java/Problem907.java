package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2023/8/11 08:25
 * @Author zsy
 * @Description 子数组的最小值之和 子序列和子数组 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem654、Problem739、Problem1019、Problem1856、Problem2104、Problem2454、Problem2487、Offer33、DoubleStackSort
 * 给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。
 * 由于答案可能很大，因此 返回答案模 10^9 + 7 。
 * <p>
 * 输入：arr = [3,1,2,4]
 * 输出：17
 * 解释：
 * 子数组为 [3]，[1]，[2]，[4]，[3,1]，[1,2]，[2,4]，[3,1,2]，[1,2,4]，[3,1,2,4]。
 * 最小值为 3，1，2，4，1，1，2，1，1，1，和为 17。
 * <p>
 * 输入：arr = [11,81,94,43,3]
 * 输出：444
 * <p>
 * 1 <= arr.length <= 3 * 10^4
 * 1 <= arr[i] <= 3 * 10^4
 */
public class Problem907 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem907 problem907 = new Problem907();
        int[] arr = {3, 1, 2, 4};
        System.out.println(problem907.sumSubarrayMins(arr));
        System.out.println(problem907.sumSubarrayMins2(arr));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param arr
     * @return
     */
    public int sumSubarrayMins(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        //arr数组中所有子数组的最小值之和
        int result = 0;

        for (int i = 0; i < arr.length; i++) {
            //arr[i]-arr[j]子数组的最小值
            int min = arr[i];

            for (int j = i; j < arr.length; j++) {
                min = Math.min(min, arr[j]);
                result = (result + min) % MOD;
            }
        }

        return result;
    }

    /**
     * 单调栈
     * 当前元素不满足单调递增栈，栈顶元素出栈，作为区间中最小元素的下标索引，
     * 对每一个最小元素，得到以当前元素作为区间最小元素的最大左右边界，加上以当前元素作为最小元素的区间个数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public int sumSubarrayMins2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        //arr数组中所有子数组的最小值之和
        //使用long，避免int溢出
        long result = 0;
        //单调递增栈，用于求区间的最小元素
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            //不满足单调递增栈，则栈顶元素出栈，作为区间中最小元素的下标索引
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                //区间的最小元素下标索引
                int index = stack.pop();
                //区间的最小元素
                int min = arr[index];
                //以min作为最小元素的最大左边界下标索引
                int left;
                //以min作为最小元素的最大右边界下标索引
                int right = i - 1;

                if (stack.isEmpty()) {
                    left = 0;
                } else {
                    left = stack.peek() + 1;
                }

                //加上以min作为最小元素的区间个数，左边界有(index-left+1)种情况，右边界有(right-index+1)种情况，
                //两者相乘得到有多少个区间以min作为最小元素
                result = (result + (long) min * (index - left + 1) * (right - index + 1)) % MOD;
            }

            //当前元素入栈
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            //区间的最小元素下标索引
            int index = stack.pop();
            //区间的最小元素
            int min = arr[index];
            //以min作为最小元素的最大左边界下标索引
            int left;
            //以min作为最小元素的最大右边界下标索引
            int right = arr.length - 1;

            if (stack.isEmpty()) {
                left = 0;
            } else {
                left = stack.peek() + 1;
            }

            //加上以min作为最小元素的区间个数，左边界有(index-left+1)种情况，右边界有(right-index+1)种情况，
            //两者相乘得到有多少个区间以min作为最小元素
            result = (result + (long) min * (index - left + 1) * (right - index + 1)) % MOD;
        }

        return (int) result;
    }
}
