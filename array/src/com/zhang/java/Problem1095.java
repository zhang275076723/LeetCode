package com.zhang.java;

/**
 * @Date 2023/5/11 08:58
 * @Author zsy
 * @Description 山脉数组中查找目标值 双调数组查找目标元素 拼多多面试题 字节面试题 山脉类比Problem845、Problem852、Problem941 类比Problem33、Problem34、Problem35、Problem81、Problem153、Problem154、Problem162、Problem275、Problem540、Problem852、Problem1150、Offer11、Offer53、Offer53_2、Interview_10_03、Interview_10_05
 * 给你一个 山脉数组 mountainArr，请你返回能够使得 mountainArr.get(index) 等于 target 最小 的下标 index 值。
 * 如果不存在这样的下标 index，就请返回 -1。
 * 何为山脉数组？如果数组 A 是一个山脉数组的话，那它满足如下条件：
 * 首先，A.length >= 3
 * 其次，在 0 < i < A.length - 1 条件下，存在 i 使得：
 * A[0] < A[1] < ... A[i-1] < A[i]
 * A[i] > A[i+1] > ... > A[A.length - 1]
 * 你将 不能直接访问该山脉数组，必须通过 MountainArray 接口来获取数据：
 * MountainArray.get(k) - 会返回数组中索引为k 的元素（下标从 0 开始）
 * MountainArray.length() - 会返回该数组的长度
 * <p>
 * 输入：array = [1,2,3,4,5,3,1], target = 3
 * 输出：2
 * 解释：3 在数组中出现了两次，下标分别为 2 和 5，我们返回最小的下标 2。
 * <p>
 * 输入：array = [0,1,2,4,2,1], target = 3
 * 输出：-1
 * 解释：3 在数组中没有出现，返回 -1。
 * <p>
 * 3 <= mountain_arr.length() <= 10000
 * 0 <= target <= 10^9
 * 0 <= mountain_arr.get(index) <= 10^9
 */
public class Problem1095 {
    public static void main(String[] args) {
        Problem1095 problem1095 = new Problem1095();
        int[] arr = {1, 2, 3, 4, 5, 3, 1};
        int target = 2;
        System.out.println(problem1095.findInMountainArray(target, arr));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 1、找到山脉数组的山顶下标索引
     * 2、左边山底到山顶递增数组进行二分查找target，如果找到，则返回target下标索引，如果没有找到，则进行第3步
     * 3、山顶的下一个元素到右边山底进行二分查找target，如果找到，则返回target下标索引，如果没有找到，则返回-1
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param target
     * @param arr
     * @return
     */
    public int findInMountainArray(int target, int[] arr) {
        //长度至少为3才能构成山脉数组
        if (arr == null || arr.length < 3) {
            return 0;
        }

        int left = 0;
        int right = arr.length - 1;
        int mid;

        //1、找到山脉数组的山顶下标索引
        while (left < right) {
            //mid往左偏，所以nums[mid]和nums[mid + 1]才能比较
            mid = left + ((right - left) >> 1);

            //山顶元素在mid右边
            if (arr[mid] < arr[mid + 1]) {
                left = mid + 1;
            } else {
                //山顶元素在mid或mid左边
                right = mid;
            }
        }

        //山顶元素下标索引
        int peekIndex = left;

        //2、左边山底到山顶递增数组进行二分查找target，arr[0]-arr[peekIndex]递增数组进行二分查找target
        int index = binarySearch(arr, 0, peekIndex, target, true);

        //arr[0]-arr[peekIndex]递增数组找到了target，直接返回target下标索引
        if (index != -1) {
            return index;
        }

        //3、山顶的下一个元素到右边山底进行二分查找target，arr[peekIndex+1]-arr[arr.length-1]递减数组进行二分查找target
        index = binarySearch(arr, peekIndex + 1, arr.length - 1, target, false);

        return index;
    }

    /**
     * arr[left]-arr[right]根据isAscend判断是升序数组还是降序数组进行二分查找target下标索引，如果没有找到，返回-1
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param arr
     * @param left
     * @param right
     * @param target
     * @param isAscend
     * @return
     */
    private int binarySearch(int[] arr, int left, int right, int target, boolean isAscend) {
        if (left > right) {
            return -1;
        }

        while (left <= right) {
            int mid = left + ((right - left) >> 1);

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                if (isAscend) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (isAscend) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }
}
