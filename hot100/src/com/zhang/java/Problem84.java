package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/4/28 9:35
 * @Author zsy
 * @Description 柱状图中最大的矩形 类比Problem11、Problem85 单调栈类比Problem42、Problem255、Problem316、Problem321、Problem402、Problem456、Problem496、Problem503、Problem654、Problem739、Problem795、Problem907、Problem1019、Problem1856、Problem2104、Problem2454、Problem2487、Offer33、DoubleStackSort
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 * <p>
 * 输入：heights = [2,1,5,6,2,3]
 * 输出：10
 * 解释：最大的矩形为图中红色区域，面积为 10
 * <p>
 * 输入： heights = [2,4]
 * 输出： 4
 * <p>
 * 1 <= heights.length <=10^5
 * 0 <= heights[i] <= 10^4
 */
public class Problem84 {
    public static void main(String[] args) {
        Problem84 problem84 = new Problem84();
        int[] height = {2, 1, 5, 6, 2, 3};
        System.out.println(problem84.largestRectangleArea(height));
        System.out.println(problem84.largestRectangleArea2(height));
    }

    /**
     * 暴力，双指针
     * 以heights[i]为矩形的高的最大面积：当前位置为高，往左右找第一个小于当前高的位置为宽
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int max = 0;

        for (int i = 0; i < heights.length; i++) {
            //以heights[i]为矩形的高，矩形的左边界
            int left = i;
            //以heights[i]为矩形的高，矩形的右边界
            int right = i;

            while (left - 1 >= 0 && heights[left - 1] >= heights[i]) {
                left--;
            }

            while (right + 1 < heights.length && heights[right + 1] >= heights[i]) {
                right++;
            }

            //当前矩形的宽
            int w = right - left + 1;
            max = Math.max(max, heights[i] * w);
        }

        return max;
    }

    /**
     * 单调栈 (求当前元素之后，比当前元素大或小的元素，就要想到单调栈)
     * 当前位置的最大面积：当前位置为高，往左右找第一个小于当前高的位置为宽
     * 如果height[i]大于等于栈顶元素，则将对应索引下标i入栈；
     * 如果height[i]小于栈顶元素，则依次出栈，直至栈空或满足单调递增栈，并将对应索引下标i入栈
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea2(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int max = 0;
        //单调递增栈，存放元素下标索引
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < heights.length; i++) {
            //不满足单调递增栈，则栈顶元素出栈
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                //矩形的高
                int h = heights[stack.pop()];
                //矩形的宽
                int w;

                if (!stack.isEmpty()) {
                    w = i - stack.peek() - 1;
                } else {
                    w = i;
                }

                max = Math.max(max, h * w);
            }

            stack.push(i);
        }

        //栈不为空，说明栈中剩余索引对应元素递增，需要分别计算对应面积
        while (!stack.isEmpty()) {
            //矩形的高
            int h = heights[stack.pop()];
            //矩形的宽
            int w;

            if (!stack.isEmpty()) {
                w = heights.length - stack.peek() - 1;
            } else {
                w = heights.length;
            }

            max = Math.max(max, h * w);
        }

        return max;
    }
}
