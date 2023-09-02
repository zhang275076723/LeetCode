package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/4/4 9:09
 * @Author zsy
 * @Description 数组中数字出现的次数 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56_2、Offer64、Offer65、IpToInt 同Problem260
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
     * 数组元素进行异或，得到只出现一次的两个数异或结果，根据异或结果二进制中由低位到高位不同的一位，即二进制为1的一位表示的数，
     * 根据这个数将数组元素分为两部分，两部分元素分别进行异或，得到只出现一次的两个数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] singleNumbers2(int[] nums) {
        //nums数组元素异或结果
        int result = 0;

        for (int num : nums) {
            result = result ^ num;
        }

        //result二进制中由低位到高位不同的一位表示的数，根据bit将数组元素分为两部分
        int bit = 1;

        //由低位到高位不同的一位，即二进制为1的一位表示的数
        while ((result & bit) == 0) {
            bit = bit << 1;
        }

        //只出现一次的两个数
        int result1 = 0;
        int result2 = 0;

        //根据bit将数组元素分为两部分
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
