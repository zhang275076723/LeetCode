package com.zhang.java;

/**
 * @Date 2022/5/27 11:43
 * @Author zsy
 * @Description 最长递增子序列 华为面试题、思科面试题 类比Problem718、Problem1143
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。
 * 例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 * <p>
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 * <p>
 * 输入：nums = [0,1,0,3,2,3]
 * 输出：4
 * <p>
 * 输入：nums = [7,7,7,7,7,7,7]
 * 输出：1
 * <p>
 * 1 <= nums.length <= 2500
 * -10^4 <= nums[i] <= 10^4
 */
public class Problem300 {
    public static void main(String[] args) {
        Problem300 problem300 = new Problem300();
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(problem300.lengthOfLIS(nums));
        System.out.println(problem300.lengthOfLIS2(nums));
    }

    /**
     * 动态规划
     * dp[i]：以nums[i]结尾的最长递增子序列
     * dp[i] = max(dp[i], dp[j] + 1) (0 <= j < i，且nums[j] < nums[i])
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] dp = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            //赋以nums[i]结尾的最长递增子序列长度为1
            dp[i] = 1;
        }

        int maxLen = dp[0];

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    /**
     * 二分查找变形
     * 新建一个数组，保证其中的元素严格递增，遍历原数组，通过二分查找找到在新数组的中的位置，
     * 如果当前元素比新数组中元素都大，则放在尾部；如果在新数组中间，则进行替换
     * nums = [0, 6, 7, 8, 4, 5, 9]
     * 新数组 = [0]
     * 新数组 = [0, 6]
     * 新数组 = [0, 6, 7]
     * 新数组 = [0, 6, 7, 8]
     * 新数组 = [0, 4, 7, 8]
     * 新数组 = [0, 4, 5, 8]
     * 新数组 = [0, 4, 5, 8, 9]
     * 则最长递增子序列长度为5
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //严格递增数组
        int[] result = new int[nums.length];
        //严格递增数组的长度
        int maxLen = 1;
        result[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            //当前元素放到严格递增数组的尾部
            if (nums[i] > result[maxLen - 1]) {
                result[maxLen] = nums[i];
                maxLen++;
            } else {
                //当前元素替换严格递增数组中的元素
                int left = 0;
                int right = maxLen - 1;

                while (left <= right) {
                    int mid = left + ((right - left) >> 1);
                    if (nums[i] > result[mid]) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }

                result[left] = nums[i];
            }
        }

        return maxLen;
    }
}
