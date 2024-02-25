package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/4 08:04
 * @Author zsy
 * @Description 缺失的区间 区间类比Problem56、Problem57、Problem228、Problem252、Problem253、Problem352、Problem406、Problem435、Problem436、Problem632、Problem763、Problem855、Problem986、Problem1288、Problem2402
 * 给你一个闭区间 [lower, upper] 和一个 按从小到大排序 的整数数组 nums ，其中元素的范围在闭区间 [lower, upper] 当中。
 * 如果一个数字 x 在 [lower, upper] 区间内，并且 x 不在 nums 中，则认为 x 缺失。
 * 返回 准确涵盖所有缺失数字 的 最小排序 区间列表。
 * 也就是说，nums 的任何元素都不在任何区间内，并且每个缺失的数字都在其中一个区间内。
 * <p>
 * 输入: nums = [0, 1, 3, 50, 75], lower = 0 和 upper = 99
 * 输出: ["2", "4->49", "51->74", "76->99"]
 * <p>
 * 输入： nums = [-1], lower = -1, upper = -1
 * 输出： []
 * 解释： 没有缺失的区间，因为没有缺失的数字。
 * <p>
 * -10^9 <= lower <= upper <= 10^9
 * 0 <= nums.length <= 100
 * lower <= nums[i] <= upper
 * nums 中的所有值 互不相同
 */
public class Problem163 {
    public static void main(String[] args) {
        Problem163 problem163 = new Problem163();
        int[] nums = {0, 1, 3, 50, 75};
        int lower = 0;
        int upper = 99;
        System.out.println(problem163.findMissingRanges(nums, lower, upper));
    }

    /**
     * 模拟
     * 如果数组中相邻元素相差超过1，则存在缺失区间；否则，不存在缺失区间
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> list = new ArrayList<>();

        //lower和nums[0]之间缺失的区间
        if (lower < nums[0]) {
            //缺失区间只有一个元素
            if (lower + 1 == nums[0]) {
                list.add(lower + "");
            } else {
                //缺失区间元素个数大于1个
                list.add(lower + "->" + (nums[0] - 1));
            }
        }

        for (int i = 1; i < nums.length; i++) {
            //数组中相邻元素相差超过1，则存在缺失的区间
            if (nums[i - 1] + 1 != nums[i]) {
                //缺失区间只有一个元素
                if (nums[i - 1] + 1 == nums[i] - 1) {
                    list.add(nums[i - 1] + 1 + "");
                } else {
                    //缺失区间元素个数大于1个
                    list.add(nums[i - 1] + 1 + "->" + (nums[i] - 1));
                }
            }
        }

        //nums[nums.length-1]和upper之间缺失的区间
        if (nums[nums.length - 1] < upper) {
            //缺失区间只有一个元素
            if (nums[nums.length - 1] + 1 == upper) {
                list.add(upper + "");
            } else {
                //缺失区间元素个数大于1个
                list.add((nums[nums.length - 1] + 1) + "->" + upper);
            }
        }

        return list;
    }
}
