package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/5/4 9:32
 * @Author zsy
 * @Description 只出现一次的数字 位运算类比Problem29、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。
 * 找出那个只出现了一次的元素。
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 * <p>
 * 输入: [2,2,1]
 * 输出: 1
 * <p>
 * 输入: [4,1,2,1,2]
 * 输出: 4
 * <p>
 * 1 <= nums.length <= 3 * 104
 * -3 * 104 <= nums[i] <= 3 * 104
 * 除了某个元素只出现一次以外，其余每个元素均出现两次。
 */
public class Problem136 {
    public static void main(String[] args) {
        Problem136 problem136 = new Problem136();
        int[] nums = {4, 1, 2, 1, 2};
        System.out.println(problem136.singleNumber(nums));
        System.out.println(problem136.singleNumber2(nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

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
     * a^a=0，a^0=a，a^b=c ==> a^b^b=c^b ==> b^c=a ==> a^c=b
     * 位运算，将数组中所有元素异或，即得到只出现一次的元素
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int singleNumber2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int result = 0;

        for (int num : nums) {
            result = result ^ num;
        }

        return result;
    }
}
