package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/4/28 9:35
 * @Author zsy
 * @Description 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
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
     * 暴力，当前位置的最大面积：当前位置为高，两边第一个小于它的位置为宽，所形成的矩形面积
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            //大于等于当前元素的左边元素索引下标
            int leftIndex = i;
            while (leftIndex > 0 && heights[leftIndex - 1] >= heights[i]) {
                leftIndex--;
            }

            //大于等于当前元素的右边元素索引下标
            int rightIndex = i;
            while (rightIndex < heights.length - 1 && heights[rightIndex + 1] >= heights[i]) {
                rightIndex++;
            }

            //当前矩形的宽
            int width = rightIndex - leftIndex + 1;
            maxArea = Math.max(maxArea, heights[i] * width);
        }

        return maxArea;
    }

    /**
     * 单调递增栈，时间复杂度O(n)，空间复杂度O(n)
     * 当前位置的最大面积：当前位置为高，两边第一个小于它的位置为宽，所形成的矩形面积
     * 如果height[i]大于等于栈顶元素，则将对应索引下标i入栈；
     * 如果height[i]小于栈顶元素，则依次出栈，直至栈空或满足单调递增栈，并将对应索引下标i入栈
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea2(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < heights.length; i++) {
            //不满足要求，则栈顶元素出栈
            while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
                //矩形的高
                int height = heights[stack.pop()];
                //矩形的宽
                int width;

                //栈顶索引对应元素与当前矩形的高相等，则可以直接出栈
                while (!stack.isEmpty() && height == heights[stack.peek()]) {
                    stack.pop();
                }

                if (stack.isEmpty()) {
                    width = i;
                } else {
                    width = i - stack.peek() - 1;
                }
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }

        //当栈不为空时，说明栈中索引对应元素递增，需要分别计算对应面积
        while (!stack.isEmpty()) {
            //矩形的高
            int height = heights[stack.pop()];
            //矩形的宽
            int width;

            //栈顶索引对应元素与当前矩形的高相等，则可以直接出栈
            while (!stack.isEmpty() && height == heights[stack.peek()]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                width = heights.length;
            } else {
                width = heights.length - stack.peek() - 1;
            }
            maxArea = Math.max(maxArea, height * width);
        }

        return maxArea;
    }
}
