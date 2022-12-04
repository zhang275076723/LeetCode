package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/12/1 09:03
 * @Author zsy
 * @Description 汇总区间 类比Problem56、Problem57、Problem252、Problem253、Problem406、Problem435、Problem436、Problem763、Problem986、Problem1288
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
     * 从前往后遍历数组，当前元素和区间右边界相差1，可以汇总区间；否则，不能汇总
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
        //区间左边界
        int left = nums[0];
        //区间右边界
        int right = nums[0];

        for (int i = 1; i < nums.length; i++) {
            //当前元素和区间右边界相差1，可以汇总区间
            if (nums[i] == right + 1) {
                right = nums[i];
            } else {
                StringBuilder sb = new StringBuilder();

                if (left == right) {
                    sb.append(left);
                    list.add(sb.toString());
                } else {
                    sb.append(left).append("->").append(right);
                    list.add(sb.toString());
                }

                left = nums[i];
                right = nums[i];
            }
        }

        //最后一个区间添加到结果集合
        StringBuilder sb = new StringBuilder();

        if (left == right) {
            sb.append(left);
            list.add(sb.toString());
        } else {
            sb.append(left).append("->").append(right);
            list.add(sb.toString());
        }

        return list;
    }
}
