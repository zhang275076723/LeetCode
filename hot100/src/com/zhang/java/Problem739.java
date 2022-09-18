package com.zhang.java;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/6/18 9:37
 * @Author zsy
 * @Description 每日温度 类比Problem42、Problem84、Problem316、Problem321、Problem402
 * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，
 * 其中 answer[i] 是指在第 i 天之后，才会有更高的温度。如果气温在这之后都不会升高，请在该位置用 0 来代替。
 * <p>
 * 输入: temperatures = [73,74,75,71,69,72,76,73]
 * 输出: [1,1,4,2,1,1,0,0]
 * <p>
 * 输入: temperatures = [30,40,50,60]
 * 输出: [1,1,1,0]
 * <p>
 * 输入: temperatures = [30,60,90]
 * 输出: [1,1,0]
 * <p>
 * 1 <= temperatures.length <= 10^5
 * 30 <= temperatures[i] <= 100
 */
public class Problem739 {
    public static void main(String[] args) {
        Problem739 problem739 = new Problem739();
        int[] temperatures = {34, 80, 80, 34, 34, 80, 80, 80, 80, 34};
        System.out.println(Arrays.toString(problem739.dailyTemperatures(temperatures)));
        System.out.println(Arrays.toString(problem739.dailyTemperatures2(temperatures)));
        System.out.println(Arrays.toString(problem739.dailyTemperatures3(temperatures)));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures.length == 1) {
            return new int[1];
        }

        int[] result = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            for (int j = i + 1; j < temperatures.length; j++) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 暴力优化，动态规划，从后往前遍历
     * result[i] = 1   (temperatures[i] < temperatures[i+1])
     * result[i] = 0   (temperatures[i] >= temperatures[i+1]，result[i+1] == 0)
     * result[i] = j-i (temperatures[i] >= temperatures[i+1]，result[i+1] != 0，j = i+1+result[i+1])
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures2(int[] temperatures) {
        if (temperatures.length == 1) {
            return new int[1];
        }

        int[] result = new int[temperatures.length];

        for (int i = temperatures.length - 2; i >= 0; i--) {
            if (temperatures[i] < temperatures[i + 1]) {
                result[i] = 1;
            } else {
                if (result[i + 1] == 0) {
                    result[i] = 0;
                } else {
                    //找到比temperatures[i+1]温度高的索引下标
                    int j = i + 1 + result[i + 1];

                    while (j < temperatures.length) {
                        if (temperatures[i] < temperatures[j]) {
                            result[i] = j - i;
                            break;
                        }

                        //当前位置无法后移，说明未找到
                        if (result[j] == 0) {
                            break;
                        }

                        j = j + result[j];
                    }
                }
            }
        }

        return result;
    }

    /**
     * 单调栈
     * 单调递减栈，存放数组元素的下标索引
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures3(int[] temperatures) {
        if (temperatures.length == 1) {
            return new int[1];
        }

        int[] result = new int[temperatures.length];
        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[stack.peekLast()] < temperatures[i]) {
                int index = stack.pollLast();
                result[index] = i - index;
            }

            stack.offerLast(i);
        }

        return result;
    }
}
