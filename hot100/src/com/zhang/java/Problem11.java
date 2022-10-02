package com.zhang.java;

/**
 * @Date 2022/4/14 14:45
 * @Author zsy
 * @Description 盛最多水的容器 类比Problem42、Problem84、Problem238、Offer66
 * 给定一个长度为 n 的整数数组 height 。
 * 有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 * 说明：你不能倾斜容器。
 * <p>
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 * <p>
 * 输入：height = [1,1]
 * 输出：1
 * <p>
 * n == height.length
 * 2 <= n <= 10^5
 * 0 <= height[i] <= 10^4
 */
public class Problem11 {
    public static void main(String[] args) {
        Problem11 problem11 = new Problem11();
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(problem11.maxArea(height));
        System.out.println(problem11.maxArea2(height));
    }

    /**
     * 暴力
     * 遍历所有的两个边界，得到所容纳的最大水量
     * 容纳的水 = 两端的最小值 * 宽度
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int result = 0;

        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                result = Math.max(result, Math.min(height[i], height[j]) * (j - i));
            }
        }

        return result;
    }

    /**
     * 双指针
     * 容纳的水 = 两端的最小值 * 宽度
     * 每次移动高度较低的指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param height
     * @return
     */
    public int maxArea2(int[] height) {
        int result = 0;
        int i = 0;
        int j = height.length - 1;

        while (i < j) {
            if (height[i] < height[j]) {
                result = Math.max(result, height[i] * (j - i));
                i++;
            } else {
                result = Math.max(result, height[j] * (j - i));
                j--;
            }
        }

        return result;
    }
}
