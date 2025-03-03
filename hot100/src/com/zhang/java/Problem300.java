package com.zhang.java;

/**
 * @Date 2022/5/27 11:43
 * @Author zsy
 * @Description 最长递增子序列 华为面试题 思科面试题 类比Problem354、Problem376、Problem491、Problem673、Problem674、Problem1143、Problem2407、Problem2771 动态规划类比Problem132、Problem139、Problem435 子序列和子数组类比Problem53、Problem115、Problem152、Problem209、Problem325、Problem392、Problem491、Problem516、Problem525、Problem560、Problem581、Problem659、Problem673、Problem674、Problem718、Problem862、Problem1143、Offer42、Offer57_2
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
//        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        int[] nums = {0, 6, 7, 8, 4, 5, 9};
        System.out.println(problem300.lengthOfLIS(nums));
        System.out.println(problem300.lengthOfLIS2(nums));
    }

    /**
     * 动态规划
     * dp[i]：以nums[i]结尾的最长递增子序列的长度
     * dp[i] = max(dp[j] + 1) (0 <= j < i，nums[j] < nums[i])
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return 1;
        }

        //最长递增子序列的长度
        int maxLen = 1;
        int[] dp = new int[nums.length];
        //初始化，以nums[0]结尾的最长递增子序列的长度为1
        dp[0] = 1;

        for (int i = 1; i < nums.length; i++) {
            //初始化，以nums[i]结尾的最长递增子序列的长度为1
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    //更新以nums[i]结尾的最长递增子序列的长度
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            //更新最长递增子序列长度
            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    /**
     * 二分查找变形
     * 严格递增数组arr作为nums中的最长递增子序列，遍历nums，通过二分查找找arr中大于等于nums[i]的最小值，
     * 如果arr中不存在大于等于nums[i]的最小值，则nums[i]插入arr末尾；如果存在，则nums[i]替换arr中元素
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     * <p>
     * 例如：
     * nums = [0, 6, 7, 8, 4, 5, 9]
     * 新数组 = [0]
     * 新数组 = [0, 6]
     * 新数组 = [0, 6, 7]
     * 新数组 = [0, 6, 7, 8]
     * 新数组 = [0, 4, 7, 8]
     * 新数组 = [0, 4, 5, 8]
     * 新数组 = [0, 4, 5, 8, 9]
     * 则最长递增子序列长度为5
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return 1;
        }

        //严格递增数组，作为nums中的最长递增子序列
        int[] arr = new int[nums.length];
        arr[0] = nums[0];
        //初始化arr数组长度为1
        int maxLen = 1;

        for (int i = 1; i < nums.length; i++) {
            //arr末尾元素小于nums[i]，则nums[i]插入arr末尾，作为最长递增子序列中的元素
            if (arr[maxLen - 1] < nums[i]) {
                arr[maxLen] = nums[i];
                maxLen++;
            } else {
                //arr末尾元素大于等于nums[i]，则二分查找找arr中大于等于nums[i]的最小值，替换为nums[i]

                int left = 0;
                int right = maxLen - 1;
                int mid;

                //二分查找找arr中大于等于nums[i]的最小值
                while (left < right) {
                    mid = left + ((right - left) >> 1);

                    if (arr[mid] < nums[i]) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }

                //arr中大于等于nums[i]的最小值arr[left]替换为nums[i]
                arr[left] = nums[i];
            }
        }

        return maxLen;
    }
}
