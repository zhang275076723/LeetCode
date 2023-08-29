package com.zhang.java;

/**
 * @Date 2023/5/11 08:27
 * @Author zsy
 * @Description 数组中的最长山脉 山脉类比Problem852、Problem941、Problem1095 数组类比Problem53、Problem135、Problem152、Problem238、Problem581、Problem628、Problem1749、Offer42、Offer66、FindLeftBiggerRightLessIndex
 * 把符合下列属性的数组 arr 称为 山脉数组 ：
 * arr.length >= 3
 * 存在下标 i（0 < i < arr.length - 1），满足
 * arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
 * arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
 * 给出一个整数数组 arr，返回最长山脉子数组的长度。如果不存在山脉子数组，返回 0 。
 * <p>
 * 输入：arr = [2,1,4,7,3,2,5]
 * 输出：5
 * 解释：最长的山脉子数组是 [1,4,7,3,2]，长度为 5。
 * <p>
 * 输入：arr = [2,2,2]
 * 输出：0
 * 解释：不存在山脉子数组。
 * <p>
 * 1 <= arr.length <= 10^4
 * 0 <= arr[i] <= 10^4
 */
public class Problem845 {
    public static void main(String[] args) {
        Problem845 problem845 = new Problem845();
        int[] arr = {2, 1, 4, 7, 3, 2, 5};
        System.out.println(problem845.longestMountain(arr));
        System.out.println(problem845.longestMountain2(arr));
    }

    /**
     * 动态规划
     * left[i]：以arr[i]作为山顶，左边满足连续递增子数组的长度
     * right[i]：以arr[i]作为山顶，右边满足连续递减子数组的长度
     * left[i] = left[i-1]+1   (arr[i] > arr[i-1])
     * right[i] = right[i+1]+1 (arr[i] > arr[i+1])
     * result = max(left[i]+right[i]+1)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public int longestMountain(int[] arr) {
        //长度至少为3才能构成山脉数组
        if (arr == null || arr.length < 3) {
            return 0;
        }

        int[] left = new int[arr.length];
        int[] right = new int[arr.length];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 0;
            }

            if (arr[arr.length - i - 1] > arr[arr.length - i]) {
                right[arr.length - i - 1] = right[arr.length - i] + 1;
            } else {
                right[arr.length - 1 - i] = 0;
            }
        }

        //最大山脉子数组长度
        int max = 0;

        //arr两边不能为山顶
        for (int i = 1; i < arr.length - 1; i++) {
            //i左右两边存在比arr[i]小的连续递增子数组，才能构成山脉子数组
            if (left[i] > 0 && right[i] > 0) {
                max = Math.max(max, left[i] + right[i] + 1);
            }
        }

        return max;
    }

    /**
     * 双指针
     * 每次确定左边山底，往右找山顶，找到山顶之后继续找右边山底
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param arr
     * @return
     */
    public int longestMountain2(int[] arr) {
        //长度至少为3才能构成山脉数组
        if (arr == null || arr.length < 3) {
            return 0;
        }

        //最大山脉子数组长度
        int max = 0;
        //当前遍历到的下标索引
        int index = 0;

        //长度至少为3才能构成山脉数组
        while (index < arr.length - 2) {
            //当前下标索引作为左边山底
            if (arr[index] < arr[index + 1]) {
                //左边山底下标索引
                int left = index;
                //右边山底下标索引
                int right = index;

                //往右找山顶
                while (right + 1 < arr.length && arr[right] < arr[right + 1]) {
                    right++;
                }

                //找到了山顶，如果右边元素比山顶元素小，则继续往右找右边山底
                if (right + 1 < arr.length && arr[right] > arr[right + 1]) {
                    while (right + 1 < arr.length && arr[right] > arr[right + 1]) {
                        right++;
                    }

                    //更新最大山脉子数组长度
                    max = Math.max(max, right - left + 1);
                }

                //index更新为右边山底right
                index = right;
            } else {
                //当前不能元素作为左边山底，index右移
                index++;
            }
        }

        return max;
    }
}
