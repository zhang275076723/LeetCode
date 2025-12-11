package com.zhang.java;

import java.util.Random;

/**
 * @Date 2025/12/10 13:41
 * @Author zsy
 * @Description 跳跃燃烧的最大卡路里 跳跃问题类比
 * 给定一个长度为 n 的整数数组 heights，其中 heights[i] 表示训练计划中第 i 个块的高度。
 * 你从地面（高度0）开始，必须 按照任意顺序跳到每个方块上，且只能跳 一次。
 * 从一个高度为 a 的块起跳到另一个高度为 b 的块所消耗的卡路里是 (a - b)2。
 * 从地面跳到所选的第一个方块高度 heights[i] 所 消耗的卡路里 是 (0 - heights[i])2。
 * 返回通过选择最优跳跃序列所能燃烧的 最大 总卡路里。
 * 注意：一旦你跳到第一个方块上，就无法返回地面。
 * <p>
 * 输入：heights = [1,7,9]
 * 输出：181
 * 解释：
 * 最优序列是 [9, 1, 7]。
 * 从地面到 heights[2] = 9 的初始跳跃：(0 - 9)2 = 81。
 * 下一次跳跃到 heights[0] = 1：(9 - 1)2 = 64。
 * 最后一次跳跃到 heights[1] = 7：(1 - 7)2 = 36。
 * 消耗的总卡路里 = 81 + 64 + 36 = 181。
 * <p>
 * 输入：heights = [5,2,4]
 * 输出：38
 * 解释：
 * 最优序列是 [5, 2, 4]。
 * 从地面到 heights[0] = 5 的初始跳跃：(0 - 5)2 = 25。
 * 下一次跳跃到 heights[1] = 2：(5 - 2)2 = 9。
 * 最后一次跳跃到 heights[2] = 4：(2 - 4)2 = 4。
 * 消耗的总卡路里 = 25 + 9 + 4 = 38。
 * <p>
 * 输入：heights = [3,3]
 * 输出：9
 * 解释：
 * 最优序列是 [3, 3]。
 * 从地面到 heights[0] = 3 的初始跳跃：(0 - 3)2 = 9。
 * 下一次跳跃到 heights[1] = 3：(3 - 3)2 = 0。
 * 消耗的总卡路里 = 9 + 0 = 9。
 * <p>
 * 1 <= n == heights.length <= 10^5
 * 1 <= heights[i] <= 10^5
 */
public class Problem3730 {
    public static void main(String[] args) {
        Problem3730 problem3730 = new Problem3730();
        int[] heights = {1, 7, 9};
        System.out.println(problem3730.maxCaloriesBurnt(heights));
    }

    /**
     * 排序+双指针
     * heights由小到大排序，每次选择首尾的两个元素作为最大的高度差
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param heights
     * @return
     */
    public long maxCaloriesBurnt(int[] heights) {
        quickSort(heights, 0, heights.length - 1);

        long result = 0;
        //下次跳跃的方向
        //1：向右跳；-1：向左跳
        int direction = 1;
        //当前跳跃到的方块高度
        int pre = 0;
        int left = 0;
        int right = heights.length - 1;

        while (left <= right) {
            //从pre跳到heights[right]
            if (direction == 1) {
                result = result + (long) (heights[right] - pre) * (heights[right] - pre);
                pre = heights[right];
                right--;
                direction = -1;
            } else {
                //从pre跳到heights[left]
                result = result + (long) (heights[left] - pre) * (heights[left] - pre);
                pre = heights[left];
                left++;
                direction = 1;
            }
        }

        return result;
    }

    private void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    private int partition(int[] arr, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int value = arr[left];
        arr[left] = arr[randomIndex];
        arr[randomIndex] = value;

        int temp = arr[left];

        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }

            arr[left] = arr[right];

            while (left < right && arr[left] <= temp) {
                left++;
            }

            arr[right] = arr[left];
        }

        arr[left] = temp;

        return left;
    }
}
