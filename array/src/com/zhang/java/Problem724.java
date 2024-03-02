package com.zhang.java;

/**
 * @Date 2023/12/23 08:17
 * @Author zsy
 * @Description 寻找数组的中心下标 双指针类比 数组中的动态规划类比Problem53、Problem135、Problem152、Problem238、Problem768、Problem769、Problem845、Problem1749、Offer42、Offer66、FindLeftBiggerRightLessIndex
 * 1574 1214 1099 1089 986 977 823 724 696 658 653 633 611 532 524 522 456 455 443 408 392 350 349 283 259 167 165 88 80 75 27 26 18 16 15 680 849 offer21 offer57
 * 给你一个整数数组 nums ，请计算数组的 中心下标 。
 * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
 * 如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。
 * 这一点对于中心下标位于数组最右端同样适用。
 * 如果数组有多个中心下标，应该返回 最靠近左边 的那一个。
 * 如果数组不存在中心下标，返回 -1 。
 * <p>
 * 输入：nums = [1, 7, 3, 6, 5, 6]
 * 输出：3
 * 解释：
 * 中心下标是 3 。
 * 左侧数之和 sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11 ，
 * 右侧数之和 sum = nums[4] + nums[5] = 5 + 6 = 11 ，二者相等。
 * <p>
 * 输入：nums = [1, 2, 3]
 * 输出：-1
 * 解释：
 * 数组中不存在满足此条件的中心下标。
 * <p>
 * 输入：nums = [2, 1, -1]
 * 输出：0
 * 解释：
 * 中心下标是 0 。
 * 左侧数之和 sum = 0 ，（下标 0 左侧不存在元素），
 * 右侧数之和 sum = nums[1] + nums[2] = 1 + -1 = 0 。
 * <p>
 * 1 <= nums.length <= 10^4
 * -1000 <= nums[i] <= 1000
 */
public class Problem724 {
    public static void main(String[] args) {
        Problem724 problem724 = new Problem724();
        int[] nums = {1, 7, 3, 6, 5, 6};
        System.out.println(problem724.pivotIndex(nums));
        System.out.println(problem724.pivotIndex2(nums));
        System.out.println(problem724.pivotIndex3(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int pivotIndex(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int leftSum = 0;
            int rightSum = 0;

            for (int j = 0; j < i; j++) {
                leftSum = leftSum + nums[j];
            }

            for (int j = i + 1; j < nums.length; j++) {
                rightSum = rightSum + nums[j];
            }

            if (leftSum == rightSum) {
                return i;
            }
        }

        //没有找到，返回-1
        return -1;
    }

    /**
     * 动态规划
     * left[i]：nums[i]左边，即nums[0]-nums[i-1]元素之和
     * right[i]：nums[i]右边，即nums[i+1]-nums[nums.length-1]元素之和
     * left[i] = left[i-1] + nums[i-1]
     * right[i] = right[i+1] + nums[i+1]
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int pivotIndex2(int[] nums) {
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];

        for (int i = 1; i < nums.length; i++) {
            left[i] = left[i - 1] + nums[i - 1];
        }

        for (int i = nums.length - 2; i >= 0; i--) {
            right[i] = right[i + 1] + nums[i + 1];
        }

        for (int i = 0; i < nums.length; i++) {
            if (left[i] == right[i]) {
                return i;
            }
        }

        //没有找到，返回-1
        return -1;
    }

    /**
     * 双指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int pivotIndex3(int[] nums) {
        //nums[0]-nums[i-1]元素之和
        int left = 0;
        //nums[i+1]-nums[nums.length-1]元素之和
        int right = 0;

        for (int num : nums) {
            right = right + num;
        }

        for (int i = 0; i < nums.length; i++) {
            right = right - nums[i];

            if (left == right) {
                return i;
            }

            left = left + nums[i];
        }

        //没有找到，返回-1
        return -1;
    }
}
