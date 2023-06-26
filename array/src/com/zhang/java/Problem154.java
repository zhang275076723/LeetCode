package com.zhang.java;

/**
 * @Date 2022/7/17 9:49
 * @Author zsy
 * @Description 寻找旋转排序数组中的最小值 II 类比Problem33、Problem34、Problem35、Problem81、Problem153、Problem162、Problem275、Problem540、Problem852、Problem1095、Offer53、Offer53_2、Interview_10_03、Interview_10_05 同Offer11
 * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。
 * 例如，原数组 nums = [0,1,4,4,5,6,7] 在变化后可能得到：
 * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,4]
 * 若旋转 7 次，则可以得到 [0,1,4,4,5,6,7]
 * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
 * 给你一个可能存在 重复 元素值的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。
 * 请你找出并返回数组中的 最小元素 。
 * 你必须尽可能减少整个过程的操作步骤。
 * <p>
 * 输入：nums = [1,3,5]
 * 输出：1
 * <p>
 * 输入：nums = [2,2,2,0,1]
 * 输出：0
 * <p>
 * n == nums.length
 * 1 <= n <= 5000
 * -5000 <= nums[i] <= 5000
 * nums 原来是一个升序排序的数组，并进行了 1 至 n 次旋转
 */
public class Problem154 {
    public static void main(String[] args) {
        Problem154 problem154 = new Problem154();
        int[] nums = {2, 2, 2, 0, 1};
        System.out.println(problem154.findMin(nums));
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

            //nums[mid]和nums[right]相等，去重，右指针左移
            if (nums[mid] == nums[right]) {
                right--;
            } else if (nums[mid] > nums[right]) {
                //nums[mid]大于nums[right]，则最小元素在右边
                left = mid + 1;
            } else {
                //nums[mid]小于nums[right]，则最小元素在左边或mid
                right = mid;
            }
        }

        return nums[left];
    }
}
