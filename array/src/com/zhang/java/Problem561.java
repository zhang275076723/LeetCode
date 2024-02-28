package com.zhang.java;

/**
 * @Date 2022/9/22 11:21
 * @Author zsy
 * @Description 数组拆分 类比Problem717、Problem2498 类比Problem128、Problem506、Problem539、Problem628
 * 给定长度为 2n 的整数数组 nums ，你的任务是将这些数分成 n 对,
 * 例如 (a1, b1), (a2, b2), ..., (an, bn) ，使得从 1 到 n 的 min(ai, bi) 总和最大。
 * 返回该 最大总和 。
 * <p>
 * 输入：nums = [1,4,3,2]
 * 输出：4
 * 解释：所有可能的分法（忽略元素顺序）为：
 * 1. (1, 4), (2, 3) -> min(1, 4) + min(2, 3) = 1 + 2 = 3
 * 2. (1, 3), (2, 4) -> min(1, 3) + min(2, 4) = 1 + 2 = 3
 * 3. (1, 2), (3, 4) -> min(1, 2) + min(3, 4) = 1 + 3 = 4
 * 所以最大总和为 4
 * <p>
 * 输入：nums = [6,2,6,5,1,2]
 * 输出：9
 * 解释：最优的分法为 (2, 1), (2, 5), (6, 6). min(2, 1) + min(2, 5) + min(6, 6) = 1 + 2 + 6 = 9
 * <p>
 * 1 <= n <= 10^4
 * nums.length == 2 * n
 * -10^4 <= nums[i] <= 10^4
 */
public class Problem561 {
    public static void main(String[] args) {
        Problem561 problem561 = new Problem561();
        int[] nums = {6, 2, 6, 5, 1, 2};
        System.out.println(problem561.arrayPairSum(nums));
    }

    /**
     * 贪心
     * 先按照由小到大排序，由小到大每两个相邻元素作为一组，排序后数组奇数位元素之和即为每组最小值之和的最大值
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     * @return
     */
    public int arrayPairSum(int[] nums) {
        //由小到大排序
        quickSort(nums, 0, nums.length - 1);

        int result = 0;

        //排序后奇数位的元素之和，即为每组最小值之和的最大值
        for (int i = 0; i < nums.length; i = i + 2) {
            result = result + nums[i];
        }

        return result;
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int pivot = partition(nums, left, right);
            quickSort(nums, left, pivot - 1);
            quickSort(nums, pivot + 1, right);
        }
    }

    private int partition(int[] nums, int left, int right) {
        int temp = nums[left];

        while (left < right) {
            while (left < right && nums[right] >= temp) {
                right--;
            }
            nums[left] = nums[right];

            while (left < right && nums[left] <= temp) {
                left++;
            }
            nums[right] = nums[left];
        }

        nums[left] = temp;

        return left;
    }
}
