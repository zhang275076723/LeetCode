package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/12/1 09:03
 * @Author zsy
 * @Description 汇总区间 区间类比Problem56、Problem57、Problem252、Problem253、Problem352、Problem406、Problem435、Problem436、Problem763、Problem986、Problem1288
 * 给定一个 无重复元素 的 有序 整数数组 nums 。
 * 返回 恰好覆盖数组中所有数字 的 最小有序 区间范围列表 。
 * 也就是说，nums 的每个元素都恰好被某个区间范围所覆盖，并且不存在属于某个范围但不属于 nums 的数字 x 。
 * 列表中的每个区间范围 [a,b] 应该按如下格式输出：
 * "a->b" ，如果 a != b
 * "a" ，如果 a == b
 * <p>
 * 输入：nums = [0,1,2,4,5,7]
 * 输出：["0->2","4->5","7"]
 * 解释：区间范围是：
 * [0,2] --> "0->2"
 * [4,5] --> "4->5"
 * [7,7] --> "7"
 * <p>
 * 输入：nums = [0,2,3,4,6,8,9]
 * 输出：["0","2->4","6","8->9"]
 * 解释：区间范围是：
 * [0,0] --> "0"
 * [2,4] --> "2->4"
 * [6,6] --> "6"
 * [8,9] --> "8->9"
 * <p>
 * 0 <= nums.length <= 20
 * -2^31 <= nums[i] <= 2^31 - 1
 * nums 中的所有值都 互不相同
 * nums 按升序排列
 */
public class Problem228 {
    public static void main(String[] args) {
        Problem228 problem228 = new Problem228();
        int[] nums = {0, 2, 3, 4, 6, 8, 9};
        System.out.println(problem228.summaryRanges(nums));
    }

    /**
     * 模拟
     * 从前往后遍历数组，当前元素等于要加入区间的右边界加1，则当前元素可以和当前区间合并，end加1；
     * 否则，不能合并，将之前的区间字符串加入结果集合list中，并更新要加入的区间左右边界
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public List<String> summaryRanges(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        List<String> list = new ArrayList<>();
        //要加入的区间左边界
        int start = nums[0];
        //要加入区间的右边界
        int end = nums[0];

        for (int i = 1; i < nums.length; i++) {
            //当前元素等于要加入区间的右边界加1，则当前元素可以和当前区间合并，end加1
            if (nums[i] == end + 1) {
                end = end + 1;
            } else {
                ///当前元素不等于要加入区间的右边界加1，将之前的区间字符串加入结果集合list中，并更新要加入的区间左右边界

                //区间只有一个元素
                if (start == end) {
                    list.add(start + "");
                } else {
                    //区间元素个数大于1个
                    list.add(start + "->" + end);
                }

                start = nums[i];
                end = nums[i];
            }
        }

        //最后一个区间添加到结果集合
        if (start == end) {
            list.add(start + "");
        } else {
            list.add(start + "->" + end);
        }

        return list;
    }
}
