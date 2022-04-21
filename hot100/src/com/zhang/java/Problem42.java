package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/3/30 16:24
 * @Author zsy
 * @Description 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * <p>
 * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出：6
 * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
 * <p>
 * 输入：height = [4,2,0,3,2,5]
 * 输出：9
 */
public class Problem42 {
    public static void main(String[] args) {
        Problem42 problem42 = new Problem42();
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(problem42.trap(height));
        System.out.println(problem42.trap2(height));
        System.out.println(problem42.trap3(height));
        System.out.println(problem42.trap4(height));
    }

    /**
     * 暴力，按列求雨水，时间复杂度O(n^2)，空间复杂度O(1)
     * 找到height[i]左边的最大值和右边的最大值，两边最大值中的较小值减去height[i]，即为height[i]所能接的雨水
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int result = 0;

        for (int i = 0; i < height.length; i++) {
            int leftMaxHeight = 0;
            int rightMaxHeight = 0;

            for (int j = i; j >= 0; j--) {
                leftMaxHeight = Math.max(leftMaxHeight, height[j]);
            }
            for (int j = i; j < height.length; j++) {
                rightMaxHeight = Math.max(rightMaxHeight, height[j]);
            }
            result = result + Math.min(leftMaxHeight, rightMaxHeight) - height[i];
        }

        return result;
    }

    /**
     * 动态规划，按列求雨水，时间复杂度O(n)，空间复杂度O(n)
     * 暴力每个位置都需要遍历一遍才能够找到最大和最小值，而动态规划在O(1)就能找到最大最小值
     * leftMax[i]：索引下标i左边的最大值
     * rightMax[i]：索引下标i右边的最大值
     * leftMax[i] = max(leftMax[i-1], height[i])
     * rightMax[i] = max(rightMax[i+1], height[i])
     *
     * @param height
     * @return
     */
    public int trap2(int[] height) {
        int result = 0;
        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];

        leftMax[0] = height[0];
        rightMax[height.length - 1] = height[height.length - 1];

        for (int i = 1; i < leftMax.length; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        for (int i = rightMax.length - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        for (int i = 0; i < height.length; i++) {
            result = result + Math.min(leftMax[i], rightMax[i]) - height[i];
        }

        return result;
    }

    /**
     * 双指针，按列求雨水，时间复杂度O(n)，空间复杂度O(1)
     *
     * @param height
     * @return
     */
    public int trap3(int[] height) {
        int result = 0;
        //左指针
        int left = 0;
        //右指针
        int right = height.length - 1;
        //左指针左边的最大值
        int leftMax = 0;
        //右指针右边的最大值
        int rightMax = 0;

        while (left < right) {
            leftMax = Math.max(height[left], leftMax);
            rightMax = Math.max(height[right], rightMax);

            if (height[left] < height[right]) {
                result = result + leftMax - height[left];
                left++;
            } else {
                result = result + rightMax - height[right];
                right--;
            }
        }

        return result;
    }

    /**
     * 单调栈，按行求雨水，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param height
     * @return
     */
    public int trap4(int[] height) {
        int result = 0;
        //单调递减栈，存放位置索引
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < height.length; i++) {
            //当栈不为空，且当前高度大于栈顶索引对应高度时，即可接到雨水
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int stackTopIndex = stack.pop();

                //如果当前栈为空，说明没有左边界，接不到雨水，直接跳出循环
                if (stack.isEmpty()) {
                    break;
                }

                //所接雨水宽度
                int curWidth = i - stack.peek() - 1;
                //所接雨水高度
                int curHeight = Math.min(height[i], height[stack.peek()]) - height[stackTopIndex];
                result = result + curHeight * curWidth;
            }
            stack.push(i);
        }

        return result;
    }

}
