package com.zhang.java;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Date 2022/3/22 10:26
 * @Author zsy
 * @Description 给定一个整数数组 temperatures ，表示每天的温度，
 * 返回一个数组 answer ，其中 answer[i] 是指在第 i 天之后，才会有更高的温度。
 * 如果气温在这之后都不会升高，请在该位置用 0 来代替。
 * <p>
 * 输入: temperatures = [73,74,75,71,69,72,76,73]
 * 输出: [1,1,4,2,1,1,0,0]
 * <p>
 * 输入: temperatures = [30,40,50,60]
 * 输出: [1,1,1,0]
 * <p>
 * 输入: temperatures = [30,60,90]
 * 输出: [1,1,0]
 */
public class Problem739 {
    public static void main(String[] args) {
        Problem739 problem739 = new Problem739();
        int[] temperatures = {34,80,80,34,34,80,80,80,80,34};
        System.out.println(Arrays.toString(problem739.dailyTemperatures(temperatures)));
        System.out.println(Arrays.toString(problem739.dailyTemperatures2(temperatures)));
        System.out.println(Arrays.toString(problem739.dailyTemperatures3(temperatures)));
    }

    /**
     * 暴力，时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int[] result = new int[temperatures.length];
        for (int i = 0; i < temperatures.length - 1; i++) {
            for (int j = i + 1; j < temperatures.length; j++) {
                if (temperatures[i] < temperatures[j]) {
                    result[i] = j - i;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 单调栈，时间复杂度O(n)，空间复杂度O(n)
     * 单调栈适用于找某一个元素左边或右边，第一个比当前元素大或小的元素
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures2(int[] temperatures) {
        //递减栈，存放temperatures数组的下标索引
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            //当单调栈不为空，且当前索引温度大于栈顶索引温度时，为result数组赋值
            while (!stack.empty() && temperatures[stack.peek()] < temperatures[i]) {
                Integer index = stack.pop();
                result[index] = i - index;
            }
            stack.push(i);
        }

        return result;
    }

    /**
     * 动态规划，从后往前遍历
     * 1、如果temperatures[i] < temperatures[i+1]，则result[i] = 1
     * 2、如果temperatures[i] > temperatures[i+1]
     * 2.1、如果result[i+1] = 0，则result[i] = 0
     * 2.2、如果result[i+1] ≠ 0，则比较temperatures[i]和temperatures[i+1+result[i+1]]，即可以归纳为情况1、2
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures3(int[] temperatures) {
        int[] result = new int[temperatures.length];

        for (int i = temperatures.length - 2; i >= 0; i--) {
            if (temperatures[i] < temperatures[i + 1]) {
                result[i] = 1;
            } else if (result[i + 1] == 0) {
                result[i] = 0;
            } else {
                for (int j = i + 1 + result[i + 1]; j < temperatures.length; j = j + result[j]) {
                    if (temperatures[i] < temperatures[j]) {
                        result[i] = j - i;
                        break;
                    }

                    //如果result[j]为0，则j指针无法后移，说明未找到
                    if (result[j] == 0) {
                        break;
                    }
                }
            }
        }

        return result;
    }
}
