package com.zhang.java;

/**
 * @Date 2022/7/2 8:57
 * @Author zsy
 * @Description 寻找旋转排序数组中的最小值 类比Problem33、Problem34、Problem35、Problem81、Problem154、Problem162、Problem275、Problem540、Problem852、Problem1095、Problem1150、Offer11、Offer53、Offer53_2、Interview_10_03、Interview_10_05
 * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。
 * 例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
 * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
 * 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
 * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
 * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。
 * 请你找出并返回数组中的 最小元素 。
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 * <p>
 * 输入：nums = [3,4,5,1,2]
 * 输出：1
 * 解释：原数组为 [1,2,3,4,5] ，旋转 3 次得到输入数组。
 * <p>
 * 输入：nums = [4,5,6,7,0,1,2]
 * 输出：0
 * 解释：原数组为 [0,1,2,4,5,6,7] ，旋转 4 次得到输入数组。
 * <p>
 * 输入：nums = [11,13,15,17]
 * 输出：11
 * 解释：原数组为 [11,13,15,17] ，旋转 4 次得到输入数组。
 * <p>
 * n == nums.length
 * 1 <= n <= 5000
 * -5000 <= nums[i] <= 5000
 * nums 中的所有整数 互不相同
 * nums 原来是一个升序排序的数组，并进行了 1 至 n 次旋转
 */
public class Problem153 {
    public static void main(String[] args) {
        Problem153 problem153 = new Problem153();
        int[] nums = {3, 4, 5, 1, 2};
        System.out.println(problem153.findMin(nums));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0];
        }

        int left = 0;
        int right = nums.length - 1;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //nums[mid]大于nums[right]，则最小元素在右边
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                //nums[mid]小于等于nums[right]，则最小元素在左边或mid
                right = mid;
            }
        }

        return nums[left];
    }
}
