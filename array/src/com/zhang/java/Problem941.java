package com.zhang.java;

/**
 * @Date 2023/5/11 08:37
 * @Author zsy
 * @Description 有效的山脉数组 山脉类比Problem845、Problem852、Problem1095
 * 给定一个整数数组 arr，如果它是有效的山脉数组就返回 true，否则返回 false。
 * 让我们回顾一下，如果 arr 满足下述条件，那么它是一个山脉数组：
 * arr.length >= 3
 * 在 0 < i < arr.length - 1 条件下，存在 i 使得：
 * arr[0] < arr[1] < ... arr[i-1] < arr[i]
 * arr[i] > arr[i+1] > ... > arr[arr.length - 1]
 * <p>
 * 输入：arr = [2,1]
 * 输出：false
 * <p>
 * 输入：arr = [3,5,5]
 * 输出：false
 * <p>
 * 输入：arr = [0,3,2,1]
 * 输出：true
 * <p>
 * 1 <= arr.length <= 10^4
 * 0 <= arr[i] <= 10^4
 */
public class Problem941 {
    public static void main(String[] args) {
        Problem941 problem941 = new Problem941();
//        int[] arr = {0, 3, 2, 1};
        int[] arr = {0, 1, 2, 1, 2};
        System.out.println(problem941.validMountainArray(arr));
    }

    /**
     * 模拟
     * 从左往右遍历，如果当前元素小于下一个元素，则当前元素作为左边山底，往右找山顶，
     * 找到山顶元素之后，继续找右边山底，如果右边山底为末尾元素，则是山脉数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param arr
     * @return
     */
    public boolean validMountainArray(int[] arr) {
        //长度至少为3才能构成山脉数组
        if (arr == null || arr.length < 3) {
            return false;
        }

        //当前元素大于等于下一个元素，则是山脉数组
        if (arr[0] > arr[1]) {
            return false;
        }

        //左边山底下标索引
        int left = 0;

        //当前元素小于下一个元素，则当前元素作为左边山底，往右找山顶
        while (left + 1 < arr.length && arr[left] < arr[left + 1]) {
            left++;
        }

        //不存在右边山底，即数组为单调递增的，不是山脉数组
        if (left == arr.length - 1) {
            return false;
        }

        //找到山顶元素之后，继续找右边山底，如果右边山底为末尾元素，则是山脉数组
        while (left + 1 < arr.length && arr[left] > arr[left + 1]) {
            left++;
        }

        //右边山底为末尾元素，则是山脉数组
        return left == arr.length - 1;
    }
}
