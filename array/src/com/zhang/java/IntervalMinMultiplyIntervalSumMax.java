package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2023/5/13 09:14
 * @Author zsy
 * @Description 求区间最小数乘区间和的最大值 字节面试题 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem739、Problem1019、Offer33、DoubleStackSort
 * 给定一个数组，要求选出一个区间, 使得该区间是所有区间中经过如下计算的值最大的一个：区间中的最小数 * 区间所有数的和。
 * 数组中的元素都是非负数。
 * 输入两行，第一行n表示数组长度，第二行为数组序列。输出最大值。
 * <p>
 * 输入：arr = [6,2,1]
 * 输出：36
 * 解释：
 * [6] = 6*6 = 36
 * [2] = 2*2 = 4
 * [1] = 1*1 = 1
 * [6,2] = 2*8 = 16
 * [2,1] = 1*3 = 3
 * [6,2,1] = 1*9 = 9
 * 满足条件区间是[6] = 6 * 6 = 36;
 * <p>
 * 区间内所有数字都在[0,100]的范围内
 */
public class IntervalMinMultiplyIntervalSumMax {
    public static void main(String[] args) {
        IntervalMinMultiplyIntervalSumMax intervalMinMultiplyIntervalSumMax = new IntervalMinMultiplyIntervalSumMax();
        int[] arr = {3, 6, 2, 1};
        System.out.println(intervalMinMultiplyIntervalSumMax.find(arr));
        System.out.println(intervalMinMultiplyIntervalSumMax.find2(arr));
    }

    /**
     * 暴力
     * 遍历每一个区间，得到区间最小值和区间和，求最大的区间最小值和区间和乘积
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param arr
     * @return
     */
    public int find(int[] arr) {
        //最大的区间最小值和区间和乘积
        int result = 0;

        //区间起始下标索引i
        for (int i = 0; i < arr.length; i++) {
            //区间[i,j]的最小值
            int intervalMin = Integer.MAX_VALUE;
            //区间[i,j]元素之和
            int intervalSum = 0;
            //区间末尾下标索引j
            for (int j = i; j < arr.length; j++) {
                intervalMin = Math.min(intervalMin, arr[j]);
                intervalSum = intervalSum + arr[j];
                result = Math.max(result, intervalMin * intervalSum);
            }
        }

        return result;
    }

    /**
     * 前缀和+单调栈
     * 单调递增栈存放arr中元素下标索引，前缀和用于相减求区间元素之和
     * 当前元素不满足单调递增栈，栈顶元素出栈，作为区间中最小元素的下标索引
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public int find2(int[] arr) {
        //前缀和用于相减求区间元素之和
        int[] preSum = new int[arr.length + 1];

        for (int i = 1; i <= arr.length; i++) {
            preSum[i] = preSum[i - 1] + arr[i - 1];
        }

        //最大的区间最小值和区间和乘积
        int result = 0;
        //单调递增栈，用于求区间的最小元素
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            //不满足单调递增栈，则栈顶元素出栈，作为区间中最小元素的下标索引
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                //区间[left,right]中最小元素
                int min = arr[stack.pop()];
                //区间[left,right]左边界
                int left;
                //区间[left,right]右边界
                int right = i - 1;

                //栈为空，区间[left,right]左边界为0
                if (stack.isEmpty()) {
                    left = 0;
                } else {
                    //栈不为空，区间[left,right]左边界为栈顶元素下标索引加1
                    left = stack.peek() + 1;
                }

                //通过preSum相减得到区间和
                result = Math.max(result, min * (preSum[right + 1] - preSum[left]));
            }

            //当前元素入栈
            stack.push(i);
        }

        //栈中元素都满足单调递增栈
        while (!stack.isEmpty()) {
            //区间[left,right]中最小元素
            int min = arr[stack.pop()];
            //区间[left,right]左边界
            int left;
            //区间[left,right]右边界
            int right = arr.length - 1;

            //栈为空，区间[left,right]左边界为0
            if (stack.isEmpty()) {
                left = 0;
            } else {
                //栈不为空，区间[left,right]左边界为栈顶元素下标索引加1
                left = stack.peek() + 1;
            }

            //通过preSum相减得到区间和
            result = Math.max(result, min * (preSum[right + 1] - preSum[left]));
        }

        return result;
    }
}
