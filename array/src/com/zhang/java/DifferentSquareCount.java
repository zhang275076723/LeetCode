package com.zhang.java;

/**
 * @Date 2022/7/26 12:56
 * @Author zsy
 * @Description 统计有序数组里平方和的数目 字节面试题 类比Problem88、Problem997
 * 返回这个数组所有数的平方值中有多少种不同的取值
 * <p>
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
     * 分别指向首尾位置，判断首位指针所指绝对值交大元素是否和当前元素前一个元素的绝对值相同
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
        //当前元素的上一个元素值
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
