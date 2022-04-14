package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/4/4 11:45
 * @Author zsy
 * @Description 在一个数组 nums 中除一个数字只出现一次之外，其他数字都出现了三次。
 * 请找出那个只出现一次的数字。
 * 1 <= nums[i] < 2^31
 * <p>
 * 输入：nums = [3,4,3,3]
 * 输出：4
 * <p>
 * 输入：nums = [9,1,7,9,7,9,7]
 * 输出：1
 */
public class Offer56_2 {
    public static void main(String[] args) {
        Offer56_2 offer56_2 = new Offer56_2();
        int[] nums = {9, 1, 7, 9, 7, 9, 7};
        System.out.println(offer56_2.singleNumber(nums));
        System.out.println(offer56_2.singleNumber2(nums));
    }

    /**
     * 哈希表，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            Integer count = map.getOrDefault(num, 0);
            map.put(num, ++count);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }

        return -1;
    }

    /**
     * 位运算，时间复杂度O(n)，空间复杂度O(1)
     * 把数组中元素的每一位都相加，
     * 如果能被3整除，说明这一位对于只出现一次的数来说为0；
     * 否则，说明这一位对于只出现一次的数来说为1
     *
     * @param nums
     * @return
     */
    public int singleNumber2(int[] nums) {
        int result = 0;
        //某一位之和
        int bit = 0;

        //因为元素都在1到2^31-1之前，所以只需要遍历31位
        for (int i = 0; i < 31; i++) {
            //得到每一位之和
            for (int num : nums) {
                bit = bit + ((num >> i) & 1);
            }
            //如果某一位之和不能够被3整除，说明对于只出现一次的数来说为1
            if (bit % 3 != 0) {
                result = result + ((bit % 3) << i);
            }
            //对某一位之和重新赋值
            bit = 0;
        }

        return result;
    }
}
