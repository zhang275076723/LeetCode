package com.zhang.java;

/**
 * @Date 2022/7/26 12:56
 * @Author zsy
 * @Description 排序数组求每个元素平方后不重复的元素个数 类比Problem88、Problem997 字节面试题
 * 输入：nums = [-10, -10, -5, 0, 1, 5, 8, 10]
 * 输出：5
 * 因为平方后不重复的元素数组为[0, 1, 25, 64, 100]，共5个
 */
public class DifferentSquareCount {
    public static void main(String[] args) {
        DifferentSquareCount differentSquareCount = new DifferentSquareCount();
        int[] nums = {-10, -10, -5, 0, 1, 5, 8, 10};
        System.out.println(differentSquareCount.findDifferentCount(nums));
    }

    /**
     * 双指针
     * 分别指向首尾位置，判断首位指针所指元素的绝对值是否和当前元素的绝对值相同，统计不同元素的绝对值的个数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findDifferentCount(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int i = 0;
        int j = nums.length - 1;
        int num = Integer.MIN_VALUE;
        int count = 0;

        while (i <= j) {
            if (Math.abs(nums[i]) > Math.abs(nums[j])) {
                if (Math.abs(nums[i]) != Math.abs(num)) {
                    count++;
                    num = nums[i];
                }

                i++;
            } else {
                if (Math.abs(nums[j]) != Math.abs(num)) {
                    count++;
                    num = nums[j];
                }

                j--;
            }
        }

        return count;
    }
}
