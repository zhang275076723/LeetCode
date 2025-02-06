package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/5/5 09:01
 * @Author zsy
 * @Description 递增子序列 类比Problem300、Problem354、Problem376、Problem673、Problem674、Problem1143、Problem2407、Problem2771 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem89、Problem90、Problem97、Problem216、Problem301、Problem377、Problem679、Problem698、Offer17、Offer38 子序列和子数组类比Problem53、Problem115、Problem152、Problem209、Problem300、Problem325、Problem392、Problem516、Problem525、Problem560、Problem581、Problem659、Problem673、Problem674、Problem718、Problem862、Problem1143、Offer42、Offer57_2
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
//        int[] nums = {4, 4, 3, 2, 1};
        int[] nums = {2, 2, 3};
        System.out.println(problem491.findSubsequences(nums));
    }

    /**
     * 回溯+剪枝，难点在于如何去重(不建议使用set去重，因为数组顺序不能变，所以不能先排序再去重)
     * 核心思想：当前递增子序列末尾元素和当前元素相等，为了去重，只能添加当前元素，不能不添加当前元素
     * 时间复杂度O(n*2^n)，空间复杂度O(n) (共有2^n种状态，每种状态需要O(n)复制到结果集合中)
     * <p>
     * 例如：nums=[2,2,3]
     * 当前递增子序列为[2]，当前元素为2，两者相等，则只能添加当前元素2，得到[2,2]；
     * 如果不添加当前元素2，得到[2,-,3]和[2,-,-]，如果当前子序列为[-]，同样能得到[-,2,3]和[-,2,-]，则产生重复递增子序列
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> findSubsequences(int[] nums) {
        if (nums == null || nums.length < 2) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();

        //last：当前递增子序列list的最后一个元素，当前元素和last相等，为了去重，只能添加当前元素，不能不添加当前元素
        //初始化last为int最小值，表示当前递增子序列为空
        backtrack(0, Integer.MIN_VALUE, nums, new ArrayList<>(), result);

        return result;
    }

    private void backtrack(int t, int last, int[] nums, List<Integer> list, List<List<Integer>> result) {
        if (t == nums.length) {
            //递增子序列中至少有2个元素
            if (list.size() >= 2) {
                result.add(new ArrayList<>(list));
            }
            return;
        }

        //当前元素和last相等，为了去重，只能添加当前元素，不能不添加当前元素
        if (last == nums[t]) {
            list.add(nums[t]);
            backtrack(t + 1, nums[t], nums, list, result);
            list.remove(list.size() - 1);
        } else if (last < nums[t]) {
            //当前元素大于last，可以添加当前元素，也可以不添加当前元素
            backtrack(t + 1, last, nums, list, result);

            list.add(nums[t]);
            backtrack(t + 1, nums[t], nums, list, result);
            list.remove(list.size() - 1);
        } else {
            //当前元素大于last，只能不添加当前元素
            backtrack(t + 1, last, nums, list, result);
        }
    }
}
