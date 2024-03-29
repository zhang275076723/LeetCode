package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/4/4 11:45
 * @Author zsy
 * @Description 数组中数字出现的次数 II 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer64、Offer65、IpToInt 同Problem137
 * 在一个数组 nums 中除一个数字只出现一次之外，其他数字都出现了三次。
 * 请找出那个只出现一次的数字。
 * <p>
 * 输入：nums = [3,4,3,3]
 * 输出：4
 * <p>
 * 输入：nums = [9,1,7,9,7,9,7]
 * 输出：1
 * <p>
 * 1 <= nums.length <= 10000
 * 1 <= nums[i] < 2^31
 */
public class Offer56_2 {
    public static void main(String[] args) {
        Offer56_2 offer56_2 = new Offer56_2();
        int[] nums = {9, 1, 7, 9, 7, 9, 7};
        System.out.println(offer56_2.singleNumber(nums));
        System.out.println(offer56_2.singleNumber2(nums));
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
     * 位运算，不需要额外的空间，就要想到位运算
     * 把数组中元素的每一位都相加，
     * 如果能被3整除，说明这一位对于只出现一次的数来说为0；
     * 否则，说明这一位对于只出现一次的数来说为1
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int singleNumber2(int[] nums) {
        int result = 0;

        //num[i]取值范围在[1,2^31-1]，所以只需要遍历31位
        for (int i = 0; i < 31; i++) {
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
