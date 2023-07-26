package com.zhang.java;

/**
 * @Date 2023/7/1 11:20
 * @Author zsy
 * @Description 查一个数是否在数组中占绝大多数 类比Problem33、Problem34、Problem35、Problem81、Problem153、Problem154、Problem162、Problem275、Problem540、Problem852、Problem1095、Offer11、Offer53、Offer53_2、Interview_10_03、Interview_10_05
 * 给出一个按 非递减 顺序排列的数组 nums，和一个目标数值 target。
 * 假如数组 nums 中绝大多数元素的数值都等于 target，则返回 True，否则请返回 False。
 * 所谓占绝大多数，是指在长度为 N 的数组中出现必须 超过 N/2 次。
 * <p>
 * 输入：nums = [2,4,5,5,5,5,5,6,6], target = 5
 * 输出：true
 * 解释：
 * 数字 5 出现了 5 次，而数组的长度为 9。
 * 所以，5 在数组中占绝大多数，因为 5 次 > 9/2。
 * <p>
 * 输入：nums = [10,100,101,101], target = 101
 * 输出：false
 * 解释：
 * 数字 101 出现了 2 次，而数组的长度是 4。
 * 所以，101 不是 数组占绝大多数的元素，因为 2 次 = 4/2。
 * <p>
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= 10^9
 * 1 <= target <= 10^9
 */
public class Problem1150 {
    public static void main(String[] args) {
        Problem1150 problem1150 = new Problem1150();
        int[] nums = {2, 4, 5, 5, 5, 5, 5, 6, 6};
        int target = 5;
        System.out.println(problem1150.isMajorityElement(nums, target));
        System.out.println(problem1150.isMajorityElement2(nums, target));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public boolean isMajorityElement(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int count = 0;

        for (int num : nums) {
            if (num == target) {
                count++;
            }
        }

        return count >= nums.length / 2;
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 通过二分查找确定target在nums中第一个和最后一个下标索引，判断是否超过n/2
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public boolean isMajorityElement2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        //数组中第一个值为target的下标索引
        int first = -1;
        //数组中最后一个值为target的下标索引
        int last = -1;
        int left = 0;
        int right = nums.length - 1;
        int mid;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == target) {
                first = mid;
                right = mid - 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        //nums不存在target，则直接返回false
        if (first == -1) {
            return false;
        }

        last = first;
        left = first + 1;
        right = nums.length - 1;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == target) {
                last = mid;
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return last - first + 1 >= nums.length / 2;
    }
}
