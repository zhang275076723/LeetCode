package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/4/4 9:09
 * @Author zsy
 * @Description 数组中数字出现的次数 类比Problem136、Problem137、Problem389、Offer56_2、Offer65 同Problem260
 * 一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。
 * 请写程序找出这两个只出现一次的数字。
 * 要求时间复杂度是O(n)，空间复杂度是O(1)。
 * <p>
 * 输入：nums = [4,1,4,6]
 * 输出：[1,6] 或 [6,1]
 * <p>
 * 输入：nums = [1,2,10,4,1,4,3,3]
 * 输出：[2,10] 或 [10,2]
 * <p>
 * 2 <= nums.length <= 10000
 */
public class Offer56 {
    public static void main(String[] args) {
        Offer56 offer56 = new Offer56();
        int[] nums = {1, 2, 10, 4, 1, 4, 3, 3};
        System.out.println(Arrays.toString(offer56.singleNumbers(nums)));
        System.out.println(Arrays.toString(offer56.singleNumbers2(nums)));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int[] singleNumbers(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        int[] result = new int[2];
        int index = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                result[index] = entry.getKey();
                index++;
            }
        }

        return result;
    }

    /**
     * 位运算，不使用额外的空间，就要想到位运算
     * a ^ 0 = a
     * a ^ a = 0
     * a ^ b = c  ==>  a ^ b ^ b = c ^ b  ==>  b ^ c = a
     * 所有元素异或，得到不同的两个数异或结果，找出异或结果二进制不同的一位，根据这一位将数组中元素分为两部分，
     * 根据当前不同的二进制位，将数组分为两部分，分别异或，得到不同的两个数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] singleNumbers2(int[] nums) {
        //所有元素异或结果，即不同的两个数异或结果
        int result = 0;

        for (int num : nums) {
            result = result ^ num;
        }

        //异或结果二进制不同的一位，根据这一位将数组中元素分为两部分
        int bit = 1;

        //找异或结果中不相同，即为1的一位
        while ((result & 1) != 1) {
            bit = bit << 1;
            result = result >>> 1;
        }

        //不同的两个数
        int result1 = 0;
        int result2 = 0;

        //根据bit位将不同的两个数分开
        for (int num : nums) {
            if ((num & bit) == 0) {
                result1 = result1 ^ num;
            } else {
                result2 = result2 ^ num;
            }
        }

        return new int[]{result1, result2};
    }
}
