package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/8/24 9:30
 * @Author zsy
 * @Description 只出现一次的数字 III 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt 同Offer56
 * 给定一个整数数组 nums，其中恰好有两个元素只出现一次，其余所有元素均出现两次。
 * 找出只出现一次的那两个元素。你可以按 任意顺序 返回答案。
 * <p>
 * 输入：nums = [1,2,1,3,2,5]
 * 输出：[3,5]
 * 解释：[5, 3] 也是有效的答案。
 * <p>
 * 输入：nums = [-1,0]
 * 输出：[-1,0]
 * <p>
 * 输入：nums = [0,1]
 * 输出：[1,0]
 * <p>
 * 2 <= nums.length <= 3 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * 除两个只出现一次的整数外，nums 中的其他数字都出现两次
 */
public class Problem260 {
    public static void main(String[] args) {
        Problem260 problem260 = new Problem260();
        int[] nums = {1, 2, 1, 3, 2, 5};
        System.out.println(Arrays.toString(problem260.singleNumber(nums)));
        System.out.println(Arrays.toString(problem260.singleNumber2(nums)));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int[] singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
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
     * 数组元素进行异或，得到只出现一次的两个数异或结果，根据异或结果二进制中由低位到高位不同的一位，即二进制为1的一位表示的数，
     * 根据这个数将数组元素分为两部分，两部分元素分别进行异或，得到只出现一次的两个数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] singleNumber2(int[] nums) {
        //nums数组元素异或结果，即只出现一次的两个元素异或结果
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
