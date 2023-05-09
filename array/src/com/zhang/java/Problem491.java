package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/5/5 09:01
 * @Author zsy
 * @Description 递增子序列 回溯+剪枝类比Problem17、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem90、Problem97、Problem216、Problem377、Problem679、Offer17、Offer38
 * 给你一个整数数组 nums ，找出并返回所有该数组中不同的递增子序列，递增子序列中 至少有两个元素 。
 * 你可以按 任意顺序 返回答案。
 * 数组中可能含有重复元素，如出现两个整数相等，也可以视作递增序列的一种特殊情况。
 * <p>
 * 输入：nums = [4,6,7,7]
 * 输出：[[4,6],[4,6,7],[4,6,7,7],[4,7],[4,7,7],[6,7],[6,7,7],[7,7]]
 * <p>
 * 输入：nums = [4,4,3,2,1]
 * 输出：[[4,4]]
 * <p>
 * 1 <= nums.length <= 15
 * -100 <= nums[i] <= 100
 */
public class Problem491 {
    public static void main(String[] args) {
        Problem491 problem491 = new Problem491();
        int[] nums = {4, 4, 3, 2, 1};
        System.out.println(problem491.findSubsequences(nums));
    }

    /**
     * 回溯+剪枝，难点在于如何去重(不建议使用set去重，因为数组顺序不能变，所以不能先排序再去重)
     * 时间复杂度O(n*2^n)，空间复杂度O(n) (共有2^n种状态，每种状态需要O(n)复制到结果集合中)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> findSubsequences(int[] nums) {
        if (nums == null || nums.length < 2) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();

        //last表示前一个选择的元素，当前元素和last不相等，当前元素才能不添加
        backtrack(0, nums, Integer.MIN_VALUE, new ArrayList<>(), result);

        return result;
    }

    private void backtrack(int t, int[] nums, int last, List<Integer> list, List<List<Integer>> result) {
        if (t == nums.length) {
            //递增子序列中至少有2个元素
            if (list.size() >= 2) {
                result.add(new ArrayList<>(list));
            }
            return;
        }

        //当前元素和last不相等，nums[t]才能不添加
        if (nums[t] != last) {
            backtrack(t + 1, nums, last, list, result);
        }

        //last大于nums[t]，则构不成递增子序列，直接返回
        if (last > nums[t]) {
            return;
        }

        list.add(nums[t]);
        //添加nums[t]
        backtrack(t + 1, nums, nums[t], list, result);
        list.remove(list.size() - 1);
    }
}
