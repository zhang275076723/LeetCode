package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/11/12 10:54
 * @Author zsy
 * @Description 丢失的数字 原地哈希类比Problem41、Problem287、Problem442、Problem448、Offer3
 * 给定一个包含 [0, n] 中 n 个数的数组 nums ，找出 [0, n] 这个范围内没有出现在数组中的那个数。
 * <p>
 * 输入：nums = [3,0,1]
 * 输出：2
 * 解释：n = 3，因为有 3 个数字，所以所有的数字都在范围 [0,3] 内。2 是丢失的数字，因为它没有出现在 nums 中。
 * <p>
 * 输入：nums = [0,1]
 * 输出：2
 * 解释：n = 2，因为有 2 个数字，所以所有的数字都在范围 [0,2] 内。2 是丢失的数字，因为它没有出现在 nums 中。
 * <p>
 * 输入：nums = [9,6,4,2,3,5,7,0,1]
 * 输出：8
 * 解释：n = 9，因为有 9 个数字，所以所有的数字都在范围 [0,9] 内。8 是丢失的数字，因为它没有出现在 nums 中。
 * <p>
 * 输入：nums = [0]
 * 输出：1
 * 解释：n = 1，因为有 1 个数字，所以所有的数字都在范围 [0,1] 内。1 是丢失的数字，因为它没有出现在 nums 中。
 * <p>
 * n == nums.length
 * 1 <= n <= 10^4
 * 0 <= nums[i] <= n
 * nums 中的所有数字都 独一无二
 */
public class Problem268 {
    public static void main(String[] args) {
        Problem268 problem268 = new Problem268();
        int[] nums = {3, 0, 1};
        System.out.println(problem268.missingNumber(nums));
        System.out.println(problem268.missingNumber2(nums));
        System.out.println(problem268.missingNumber3(nums));
        System.out.println(problem268.missingNumber4(nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        int num = 0;

        while (true) {
            if (!set.contains(num)) {
                return num;
            }
            num++;
        }
    }

    /**
     * 原地哈希
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int missingNumber2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        for (int i = 0; i < nums.length; i++) {
            //当nums[i]小于n，nums[i]和nums[nums[i]]不相等时，才进行交换
            while (nums[i] < nums.length && nums[i] != nums[nums[i]]) {
                swap(nums, i, nums[i]);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            //找第一个nums[i]和i不相等的下标索引
            if (i != nums[i]) {
                return i;
            }
        }

        //数组nums[i]和i都相等，则返回nums.length
        return nums.length;
    }

    /**
     * 数学
     * 0-n之和为n*(n+1)/2，减去数组中的元素，即得到缺失的那个数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int missingNumber3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int result = nums.length * (nums.length + 1) / 2;

        for (int num : nums) {
            result = result - num;
        }

        return result;
    }

    /**
     * 位运算
     * 0-n中缺失了一个数，数组元素和0-n分别进行异或，得到的结果就是缺失的那个数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int missingNumber4(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int result = 0;

        //0-n都进行异或
        for (int i = 0; i <= nums.length; i++) {
            result = result ^ i;
        }

        //数组中每个元素进行异或
        for (int num : nums) {
            result = result ^ num;
        }

        return result;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
