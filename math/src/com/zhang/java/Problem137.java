package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/8/24 9:09
 * @Author zsy
 * @Description 只出现一次的数字 II 位运算类比Problem29、Problem136、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt 同Offer56_2
 * 给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。
 * 请你找出并返回那个只出现了一次的元素。
 * <p>
 * 输入：nums = [2,2,3,2]
 * 输出：3
 * <p>
 * 输入：nums = [0,1,0,1,0,1,99]
 * 输出：99
 * <p>
 * 1 <= nums.length <= 3 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次
 */
public class Problem137 {
    public static void main(String[] args) {
        Problem137 problem137 = new Problem137();
        int[] nums = {0, 1, 0, 1, 0, 1, 99};
        System.out.println(problem137.singleNumber(nums));
        System.out.println(problem137.singleNumber2(nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }

        return -1;
    }

    /**
     * 位运算
     * 不使用额外的空间，就要想到位运算
     * 数组中二进制表示的每一位相加，如果能被3整除，说明只出现一次的元素当前位为0；否则，为1
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int singleNumber2(int[] nums) {
        int result = 0;

        //nums[i]取值范围为32位
        for (int i = 0; i < 32; i++) {
            //二进制表示nums中元素从右往左第i位1的个数
            int count = 0;

            //统计nums中元素当前位为1的个数
            for (int num : nums) {
                count = count + ((num >> i) & 1);
            }

            //如果当前位之和不能够被3整除，说明对于只出现一次的数来说为1
            if (count % 3 != 0) {
                result = result + (1 << i);
            }
        }

        return result;
    }
}
