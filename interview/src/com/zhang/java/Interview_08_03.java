package com.zhang.java;

/**
 * @Date 2025/11/2 19:36
 * @Author zsy
 * @Description 魔术索引 二分查找类比 分治法类比
 * 魔术索引。在数组A[0...n-1]中，有所谓的魔术索引，满足条件A[i] = i。
 * 给定一个有序整数数组，编写一种方法找出魔术索引，若有的话，在数组A中找出一个魔术索引，如果没有，则返回-1。
 * 若有多个魔术索引，返回索引值最小的一个。
 * <p>
 * 输入：nums = [0, 2, 3, 4, 5]
 * 输出：0
 * 说明：0下标的元素为0
 * <p>
 * 输入：nums = [1, 1, 1]
 * 输出：1
 * <p>
 * nums长度在[1, 1000000]之间
 * 此题为原书中的 Follow-up，即数组中可能包含重复元素的版本
 */
public class Interview_08_03 {
    public static void main(String[] args) {
        Interview_08_03 interview_08_03 = new Interview_08_03();
        int[] nums = {0, 2, 3, 4, 5};
        System.out.println(interview_08_03.findMagicIndex(nums));
        nums = new int[]{1, 1, 1};
        System.out.println(interview_08_03.findMagicIndex2(nums));
    }

    /**
     * 二分查找，看到有序数组，就要想到二分查找
     * 注意：本题假设nums中不存在重复元素
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findMagicIndex(int[] nums) {
        int index = -1;
        int left = 0;
        int right = nums.length - 1;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == mid) {
                index = mid;
                right = mid - 1;
            } else if (nums[mid] > mid) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return index;
    }

    /**
     * 分治法
     * 注意：本题假设nums中存在重复元素，不能二分
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int findMagicIndex2(int[] nums) {
        return search(nums, 0, nums.length - 1);
    }

    private int search(int[] nums, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + ((right - left) >> 1);
        //查找mid左边nums[0]-nums[mid-1]中是否存在nums[index]=index的下标索引
        int leftIndex = search(nums, left, mid - 1);

        if (leftIndex != -1) {
            return leftIndex;
        }

        if (nums[mid] == mid) {
            return mid;
        }

        //查找mid右边nums[mid+1]-nums[right]中是否存在nums[index]=index的下标索引
        return search(nums, mid + 1, right);
    }
}
