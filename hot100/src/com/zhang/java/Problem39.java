package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/4/19 8:45
 * @Author zsy
 * @Description 组合总和 回溯+剪枝类比Problem17、Problem40、Problem46、Problem47、Problem77、Problem78、Problem90、Problem97、Problem216、Problem377、Problem491、Problem679、Offer17、Offer38
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，
 * 找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。
 * 你可以按 任意顺序 返回这些组合。
 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
 * 对于给定的输入，保证和为target 的不同组合数少于 150 个。
 * <p>
 * 输入：candidates = [2,3,6,7], target = 7
 * 输出：[[2,2,3],[7]]
 * 解释：
 * 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
 * 7 也是一个候选， 7 = 7 。
 * 仅有这两种组合。
 * <p>
 * 输入: candidates = [2,3,5], target = 8
 * 输出: [[2,2,2,2],[2,3,3],[3,5]]
 * <p>
 * 输入: candidates = [2], target = 1
 * 输出: []
 * <p>
 * 1 <= candidates.length <= 30
 * 1 <= candidates[i] <= 200
 * candidate 中的每个元素都 互不相同
 * 1 <= target <= 500
 */
public class Problem39 {
    public static void main(String[] args) {
        Problem39 problem39 = new Problem39();
        int[] candidates = {5, 3, 2};
        int target = 8;
        System.out.println(problem39.combinationSum(candidates, target));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n*2^n)，空间复杂度O(n) (共有2^n种组合，每个组合需要O(n)复制到结果集合中)
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return new ArrayList<>();
        }

        //将元素从小到大排序，便于剪枝
        quickSort(candidates, 0, candidates.length - 1);

        List<List<Integer>> result = new ArrayList<>();

        backtrack(0, candidates, target, result, new ArrayList<>());

        return result;
    }

    /**
     * @param t          当前元素索引
     * @param candidates 目标数组
     * @param target     要求元素之和
     * @param result     结果集合
     * @param list       每个满足元素之和为target的结果
     */
    public void backtrack(int t, int[] candidates, int target, List<List<Integer>> result, List<Integer> list) {
        if (target == 0) {
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = t; i < candidates.length; i++) {
            //当target - candidates[i] < 0时，因为数组经过排序是有序的，所以当前元素和之后元素都不满足要求，直接剪枝
            if (target - candidates[i] < 0) {
                return;
            }

            list.add(candidates[i]);

            //和40题区别，这里是i，因为每个元素可以使用多次，而40题是i+1
            backtrack(i, candidates, target - candidates[i], result, list);

            list.remove(list.size() - 1);
        }
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int pivot = partition(nums, left, right);
            quickSort(nums, left, pivot - 1);
            quickSort(nums, pivot + 1, right);
        }
    }

    private int partition(int[] nums, int left, int right) {
        int temp = nums[left];

        while (left < right) {
            while (left < right && nums[right] >= temp) {
                right--;
            }
            nums[left] = nums[right];

            while (left < right && nums[left] <= temp) {
                left++;
            }
            nums[right] = nums[left];
        }

        nums[left] = temp;

        return left;
    }
}
