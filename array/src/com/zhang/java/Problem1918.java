package com.zhang.java;

/**
 * @Date 2024/11/9 08:21
 * @Author zsy
 * @Description 第 K 小的子数组和 类比Problem713、Problem2110 二分查找类比 滑动窗口类比
 * 给你一个 长度为 n 的整型数组 nums 和一个数值 k ，返回 第 k 小的子数组和。
 * 子数组 是指数组中一个 非空 且不间断的子序列。
 * 子数组和 则指子数组中所有元素的和。
 * <p>
 * 输入: nums = [2,1,3], k = 4
 * 输出: 3
 * 解释: [2,1,3] 的子数组为：
 * - [2] 和为 2
 * - [1] 和为 1
 * - [3] 和为 3
 * - [2,1] 和为 3
 * - [1,3] 和为 4
 * - [2,1,3] 和为 6
 * 最小子数组和的升序排序为 1, 2, 3, 3, 4, 6。 第 4 小的子数组和为 3 。
 * <p>
 * 输入：nums = [3,3,5,5], k = 7
 * 输出：10
 * 解释：[3,3,5,5] 的子数组为：
 * - [3] 和为 3
 * - [3] 和为 3
 * - [5] 和为 5
 * - [5] 和为 5
 * - [3,3] 和为 6
 * - [3,5] 和为 8
 * - [5,5] 和为 10
 * - [3,3,5], 和为 11
 * - [3,5,5] 和为 13
 * - [3,3,5,5] 和为 16
 * 最小子数组和的升序排序为 3, 3, 5, 5, 6, 8, 10, 11, 13, 16。第 7 小的子数组和为 10 。
 * <p>
 * n == nums.length
 * 1 <= n <= 2 * 10^4
 * 1 <= nums[i] <= 5 * 10^4
 * 1 <= k <= n * (n + 1) / 2
 */
public class Problem1918 {
    public static void main(String[] args) {
        Problem1918 problem1918 = new Problem1918();
//        int[] nums = {2, 1, 3};
//        int k = 4;
        int[] nums = {3, 3, 5, 5};
        int k = 7;
        System.out.println(problem1918.kthSmallestSubarraySum(nums, k));
    }

    /**
     * 二分查找+滑动窗口
     * 对[left,right]进行二分查找，left为nums最小值，right为nums元素之和，统计nums中子数组和小于等于mid的个数count，
     * 如果count小于k，则nums中第k小子数组和在mid右边，left=mid+1；
     * 如果count大于等于k，则nums中第k小子数组和在mid或mid左边，right=mid
     * 时间复杂度O(nlog(sum(nums[i])-min(nums[i])))=O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int kthSmallestSubarraySum(int[] nums, int k) {
        int min = nums[0];
        int sum = 0;

        for (int num : nums) {
            min = Math.min(min, num);
            sum = sum + num;
        }

        int left = min;
        int right = sum;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //数组中子数组和小于等于mid的个数
            int count = 0;

            //滑动窗口指针
            int l = 0;
            int r = 0;
            //滑动窗口中子数组和
            sum = 0;

            //滑动窗口求数组中子数组和小于等于mid的个数
            while (r < nums.length) {
                sum = sum + nums[r];

                while (l <= r && sum > mid) {
                    sum = sum - nums[l];
                    l++;
                }

                //nums[l]-nums[r]中以nums[r]结尾的子数组有r-l+1个
                //nums[l]-nums[r]子数组和小于等于mid，则以nums[r]为右边界的子数组和都小于等于mid，
                //即nums[l]-nums[r]、nums[l+1]-nums[r]、...、nums[r]-nums[r]的子数组和都小于等于mid，
                //共r-l+1个
                count = count + (r - l + 1);
                r++;
            }

            if (count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
