package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Date 2022/7/9 9:44
 * @Author zsy
 * @Description 最大数 类比Problem56、Problem252、Problem253、Problem406、Offer45
 * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
 * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
 * <p>
 * 输入：nums = [10,2]
 * 输出："210"
 * <p>
 * 输入：nums = [3,30,34,5,9]
 * 输出："9534330"
 * <p>
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 10^9
 */
public class Problem179 {
    public static void main(String[] args) {
        Problem179 problem179 = new Problem179();
        int[] nums = {3, 30, 34, 5, 9};
        System.out.println(problem179.largestNumber(nums));
    }

    /**
     * 按照数字组合由大到小排序，最后拼接得到结果
     * 时间复杂度O(nlognlogm)，空间复杂度O(logn) (m：数组中每个数的最大长度，|m|<=32，compareTo时间复杂度O(logm))
     *
     * @param nums
     * @return
     */
    public String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }

        quickSort(nums, 0, nums.length - 1);

//        String[] strs = new String[nums.length];
//
//        for (int i = 0; i < nums.length; i++) {
//            strs[i] = nums[i] + "";
//        }
//
//        Arrays.sort(strs, new Comparator<String>() {
//            @Override
//            public int compare(String str1, String str2) {
//                //按照组合的由大到小排序，例如：9、3，而不是3、9
//                //返回值大于0，升序，小于0，降序
//                return (str2 + str1).compareTo(str1 + str2);
//            }
//        });

        //特殊情况，如果第一个元素为0，则直接返回0
        if (nums[0] == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        for (int num : nums) {
            sb.append(num);
        }

        return sb.toString();
    }

    /**
     * 按照数字组合由大到小的快排
     *
     * @param nums
     * @param left
     * @param right
     */
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
            while (left < right && (nums[right] + "" + temp).compareTo(temp + "" + nums[right]) <= 0) {
                right--;
            }
            nums[left] = nums[right];

            while (left < right && (nums[left] + "" + temp).compareTo(temp + "" + nums[left]) >= 0) {
                left++;
            }
            nums[right] = nums[left];
        }

        nums[left] = temp;

        return left;
    }
}
