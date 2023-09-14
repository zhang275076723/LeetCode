package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/4 08:04
 * @Author zsy
 * @Description 缺失的区间 区间类比Problem56、Problem57、Problem228、Problem252、Problem253、Problem352、Problem406、Problem435、Problem436、Problem763、Problem986、Problem1288
 * 给定一个排序的整数数组 nums ，其中元素的范围在 闭区间 [lower, upper] 当中，返回不包含在数组中的缺失区间。
 * <p>
 * 输入: nums = [0, 1, 3, 50, 75], lower = 0 和 upper = 99,
 * 输出: ["2", "4->49", "51->74", "76->99"]
 */
public class Problem163 {
    public static void main(String[] args) {
        Problem163 problem163 = new Problem163();
        int[] nums = {0, 1, 3, 50, 75};
        int lower = 0;
        int upper = 99;
        System.out.println(problem163.findMissingRanges(nums, lower, upper));
        System.out.println(problem163.findMissingRanges2(nums, lower, upper));
    }

    /**
     * 模拟
     * 当前遍历到的元素值curNum和数组当前元素相等，则不存在缺失区间，curNum++；
     * 否则，存在缺失区间，添加缺失区间之后，更新curNum=数组当前元素+1
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> list = new ArrayList<>();
        //当前遍历到元素值，初始化为左边界lower
        int curNum = lower;

        for (int i = 0; i < nums.length; i++) {
            //curNum和nums[i]相等，则不存在缺失区间
            if (curNum == nums[i]) {
                curNum++;
            } else if (curNum < nums[i]) {
                //curNum小于nums[i]，则存在缺失区间

                //缺失区间只有一个元素
                if (curNum + 1 == nums[i]) {
                    list.add(curNum + "");
                    curNum = nums[i] + 1;
                } else if (curNum + 1 < nums[i]) {
                    //缺失区间元素个数大于1个
                    list.add(curNum + "->" + (nums[i] - 1));
                    curNum = nums[i] + 1;
                }
            }
        }

        //考虑最后一个元素和upper之间是否存在缺失区间
        if (curNum < upper) {
            if (curNum + 1 == upper) {
                list.add(curNum + "");
            } else if (curNum + 1 < upper) {
                list.add(curNum + "->" + upper);
            }
        }

        return list;
    }

    /**
     * 模拟
     * 如果数组中相邻元素相差超过1，则存在缺失区间；否则，不存在缺失区间
     * lower-1和upper+1分别作为nums数组的第一个元素和最后一个元素，避免考虑特殊情况
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public List<String> findMissingRanges2(int[] nums, int lower, int upper) {
        List<String> list = new ArrayList<>();
        //当前相邻元素的前一个元素
        int pre = lower - 1;

        for (int i = 0; i < nums.length; i++) {
            //数组相邻元素相差为1，则不存在缺失区间
            if (pre + 1 == nums[i]) {
                pre = nums[i];
            } else if (pre + 1 < nums[i]) {
                //数组相邻元素相差超过1，则存在缺失区间

                //缺失区间只有一个元素
                if (pre + 1 == nums[i] - 1) {
                    list.add(pre + 1 + "");
                    pre = nums[i];
                } else if (pre + 1 < nums[i] - 1) {
                    //缺失区间元素个数大于1个
                    list.add(pre + 1 + "->" + (nums[i] - 1));
                    pre = nums[i];
                }
            }
        }

        //最后一个元素upper+1处理
        if (pre + 1 != upper + 1) {
            if (pre + 1 == upper) {
                list.add(pre + 1 + "");
            } else if (pre + 1 < upper) {
                list.add(pre + 1 + "->" + upper);
            }
        }

        return list;
    }
}
