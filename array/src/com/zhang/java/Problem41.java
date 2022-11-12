package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/4/21 10:30
 * @Author zsy
 * @Description 缺失的第一个正数 字节面试题 类比Problem268、Problem287、Problem448、Offer3
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 * <p>
 * 输入：nums = [1,2,0]
 * 输出：3
 * <p>
 * 输入：nums = [3,4,-1,1]
 * 输出：2
 * <p>
 * 输入：nums = [7,8,9,11,12]
 * 输出：1
 * <p>
 * 1 <= nums.length <= 5*10^5
 * -2^31 <= nums[i] <= 2^31-1
 */
public class Problem41 {
    public static void main(String[] args) {
        Problem41 problem41 = new Problem41();
        int[] nums = {3, 4, -1, 1};
        System.out.println(problem41.firstMissingPositive(nums));
        System.out.println(problem41.firstMissingPositive2(nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }

        Set<Integer> set = new HashSet<>(nums.length);

        //将大于0的元素，放入map中
        for (int num : nums) {
            if (num > 0) {
                set.add(num);
            }
        }

        int num = 1;

        //从1开始遍历map，找没有出现的最小的正整数
        while (true) {
            if (!set.contains(num)) {
                return num;
            }

            num++;
        }
    }

    /**
     * 原地哈希，原数组作为哈希表，将正整数i放在nums[i-1]
     * 遍历数组nums[i]和i+1是否相等，如果不等，则说明找到了出现的最小的正整数；
     * 如果数组遍历结束，则说明数组nums[i]和i+1都相等，返回nums.length+1
     * 例如：[(3), 4, (-1), 1]
     * 第一次交换：[-1, (4), 3, (1)]
     * 第二次交换：[(-1), (1), 3, 4]
     * 第三次交换：[1, (-1), 3, 4]，找到没有出现的最小的正整数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int firstMissingPositive2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }

        for (int i = 0; i < nums.length; i++) {
            //将数组元素nums[i]放到nums[nums[i]-1]，数组下标nums[i]-1和数组元素nums[i]对应，例如元素3放到下标2
            //只有nums[i]为正数，且不超过数组能够存放的范围时，才进行交换
            while (nums[i] > 0 && nums[i] <= nums.length && nums[i] != nums[nums[i] - 1]) {
                //交换时，只能先保存nums[nums[i]-1]，如果先保存nums[i]，对nums[i]的修改会导致无法找到nums[nums[i]-1]
                int temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            //找第一个nums[i]和i+1不相等的下标
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        //数组nums[i]和i+1都相等，则返回nums.length+1
        return nums.length + 1;
    }
}
