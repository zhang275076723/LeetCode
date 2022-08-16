package com.zhang.java;


/**
 * @Date 2022/3/27 10:22 类比Problem56、Problem179、Problem252、Problem253、Problem406
 * @Author zsy
 * @Description 把数组排成最小的数
 * 输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 * <p>
 * 输入: [10,2]
 * 输出: "102"
 * <p>
 * 输入: [3,30,34,5,9]
 * 输出: "3033459"
 * <p>
 * 0 < nums.length <= 100
 * <p>
 * 输出结果可能非常大，所以你需要返回一个字符串而不是整数
 * 拼接起来的数字可能会有前导 0，最后结果不需要去掉前导 0
 */
public class Offer45 {
    public static void main(String[] args) {
        Offer45 offer45 = new Offer45();
        int[] nums = {3, 30, 34, 5, 9};
        System.out.println(offer45.minNumber(nums));
    }

    /**
     * 由小到大排序，拼接得到结果
     * 时间复杂度O(nlognlogm)，空间复杂度O(n) (m：数组中每个数的最大长度，|m|<=32，compareTo时间复杂度O(logm))
     *
     * @param nums
     * @return
     */
    public String minNumber(int[] nums) {
        quickSort(nums, 0, nums.length - 1);

        StringBuilder sb = new StringBuilder();

        for (int num : nums) {
            sb.append(num);
        }

        return sb.toString();
    }

    public void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int partition = partition(nums, left, right);
            quickSort(nums, left, partition - 1);
            quickSort(nums, partition + 1, right);
        }
    }

    public int partition(int[] nums, int left, int right) {
        int temp = nums[left];

        while (left < right) {
            while (left < right && (nums[right] + "" + temp).compareTo(temp + "" + nums[right]) >= 0) {
                right--;
            }
            nums[left] = nums[right];

            while (left < right && (nums[left] + "" + temp).compareTo(temp + "" + nums[left]) <= 0) {
                left++;
            }
            nums[right] = nums[left];
        }

        nums[left] = temp;

        return left;
    }
}
