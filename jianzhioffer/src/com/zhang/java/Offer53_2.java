package com.zhang.java;

/**
 * @Date 2022/4/2 15:53
 * @Author zsy
 * @Description 0～n-1中缺失的数字 类比Problem33、Problem34、Problem81、Problem153、Problem154、Problem162、Offer11、Offer53
 * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。
 * 在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
 * <p>
 * 输入: [0,1,3]
 * 输出: 2
 * <p>
 * 输入: [0,1,2,3,4,5,6,7,9]
 * 输出: 8
 * <p>
 * 1 <= 数组长度 <= 10000
 */
public class Offer53_2 {
    public static void main(String[] args) {
        Offer53_2 offer53_2 = new Offer53_2();
        int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 9};
        System.out.println(offer53_2.missingNumber(nums));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            //求mid时不使用(left + right) / 2，因为(left + right)有可能会溢出
            int mid = left + ((right - left) >> 1);

            if (nums[mid] == mid) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }
}
