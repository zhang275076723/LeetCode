package com.zhang.java;

/**
 * @Date 2022/9/29 08:43
 * @Author zsy
 * @Description 双调数组查找元素的下标索引 拼多多面试题 字节面试题 类比AscendAndDescendArrayFindMax
 * 双调数组是指一个数组中的所有元素是先递增后递减(或先递减后递增)，在双调数组中找输入元素的下标索引。
 * 如果有多个相同的值，返回任意一个索引位置即可。
 * 未找到，返回-1。
 * <p>
 * 输入：nums = [1, 2, 5, 8, 9, 6, 4, 4, 4, 4, -1, -9], target = 4
 * 输出：6或7或8或9
 */
public class AscendAndDescendArrayFindTargetIndex {
    public static void main(String[] args) {
        AscendAndDescendArrayFindTargetIndex ascendAndDescendArrayFindTargetIndex = new AscendAndDescendArrayFindTargetIndex();
        int[] nums = {1, 2, 5, 8, 9, 6, 4, 4, 4, 4, -1, -9};
        int target = 4;
        System.out.println(ascendAndDescendArrayFindTargetIndex.findTargetIndex(nums, target));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int findTargetIndex(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;
        int mid;
        int index;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == target) {
                return mid;
            }

            //只有一个元素，且mid+1超过右边界，则没有找到，返回-1
            if (mid + 1 > right) {
                return -1;
            }

            //[left,mid+1]升序，二分查找判断target是否在[left,mid+1]区间中
            if (nums[mid] < nums[mid + 1]) {
                index = binarySearch(nums, left, mid + 1, target, true);

                //target在[left,mid+1]区间中
                if (index != -1) {
                    return index;
                }

                //target不在[left,mid+1]区间中，继续往右找
                left = mid + 2;
            } else {
                //[mid,right]降序，二分查找判断target是否在[mid,right]区间中
                index = binarySearch(nums, mid, right, target, false);

                //target在[mid,right]区间中
                if (index != -1) {
                    return index;
                }

                //target不在[mid,right]区间中，继续往左找
                right = mid - 1;
            }
        }

        return -1;
    }

    /**
     * 二分查找target是否在nums数组的[left,right]区间内，根据isAscend标志位判断nums数组是升序还是降序
     *
     * @param nums
     * @param left
     * @param right
     * @param target
     * @param isAscend
     * @return
     */
    private int binarySearch(int[] nums, int left, int right, int target, boolean isAscend) {
        int mid;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                //升序数组，往左边找
                if (isAscend) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                //升序数组，往右边找
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
