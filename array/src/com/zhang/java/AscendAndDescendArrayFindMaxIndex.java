package com.zhang.java;

/**
 * @Date 2022/9/29 12:26
 * @Author zsy
 * @Description 双调数组找最大值的下标索引 字节面试题 类比AscendAndDescendArrayFindTargetIndex 同Problem852
 * 双调数组是指一个数组中的所有元素是先递增后递减(或先递减后递增)，在双调数组中找数组最大值的下标索引。
 * <p>
 * 输入：nums = [1, 3, 5, 8, 9, -1, -3]
 * 输出：4
 */
public class AscendAndDescendArrayFindMaxIndex {
    public static void main(String[] args) {
        AscendAndDescendArrayFindMaxIndex ascendAndDescendArrayFindMaxIndex = new AscendAndDescendArrayFindMaxIndex();
        int[] nums = {1, 3, 5, 8, 9, -1, -3};
        System.out.println(ascendAndDescendArrayFindMaxIndex.findMaxIndex(nums));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findMaxIndex(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //当前元素小于下一个元素时，峰值在当前元素右边
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                //当前元素大于下一个元素时，峰值在当前元素或当前元素左边
                right = mid;
            }
        }

        return left;
    }
}
