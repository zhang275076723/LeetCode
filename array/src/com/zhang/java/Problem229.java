package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2023/7/2 08:54
 * @Author zsy
 * @Description 多数元素 II 类比Problem169、Offer39
 * 给定一个大小为 n 的整数数组，找出其中所有出现超过 ⌊ n/3 ⌋ 次的元素。
 * <p>
 * 输入：nums = [3,2,3]
 * 输出：[3]
 * <p>
 * 输入：nums = [1]
 * 输出：[1]
 * <p>
 * 输入：nums = [1,2]
 * 输出：[1,2]
 * <p>
 * 1 <= nums.length <= 5 * 10^4
 * -10^9 <= nums[i] <= 10^9
 */
public class Problem229 {
    public static void main(String[] args) {
        Problem229 problem229 = new Problem229();
//        int[] nums = {3, 2, 3};
        int[] nums = {2, 1, 1, 3, 1, 4, 5, 6};
        System.out.println(problem229.majorityElement(nums));
        System.out.println(problem229.majorityElement2(nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public List<Integer> majorityElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        List<Integer> list = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > nums.length / 3) {
                list.add(entry.getKey());
            }
        }

        return list;
    }


    /**
     * 摩尔投票
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public List<Integer> majorityElement2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        //摩尔投票，统计result1和result2抵消之后出现的次数
        int vote1 = 0;
        int vote2 = 0;
        int result1 = -1;
        int result2 = -1;

        for (int num : nums) {
            if (result1 == num) {
                vote1++;
            } else if (result2 == num) {
                vote2++;
            } else {
                if (vote1 == 0) {
                    result1 = num;
                    vote1++;
                } else if (vote2 == 0) {
                    result2 = num;
                    vote2++;
                } else {
                    vote1--;
                    vote2--;
                }
            }
        }

        //票数重新赋值为0，统计result1和result2出现的次数是否超过n/3次
        vote1 = 0;
        vote2 = 0;

        //统计result1和result2出现的次数是否超过n/3次
        for (int num : nums) {
            if (result1 == num) {
                vote1++;
            } else if (result2 == num) {
                vote2++;
            }
        }

        List<Integer> list = new ArrayList<>();

        //判断result1和result2是否超过n/3
        if (vote1 > nums.length / 3) {
            list.add(result1);
        }

        if (vote2 > nums.length / 3) {
            list.add(result2);
        }

        return list;
    }
}
