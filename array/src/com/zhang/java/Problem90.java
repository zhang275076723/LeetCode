package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/11/9 08:38
 * @Author zsy
 * @Description 子集 II 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem89、Problem97、Problem216、Problem301、Problem377、Problem491、Problem679、Problem698、Offer17、Offer38
 * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。
 * 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
 * <p>
 * 输入：nums = [1,2,2]
 * 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
 * <p>
 * 输入：nums = [0]
 * 输出：[[],[0]]
 * <p>
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 */
public class Problem90 {
    public static void main(String[] args) {
        Problem90 problem90 = new Problem90();
        int[] nums = {1, 2, 2};
        System.out.println(problem90.subsetsWithDup(nums));
    }

    /**
     * 回溯+剪枝，难点在于如何去重(不建议使用set去重)
     * 时间复杂度O(n*2^n)，空间复杂度O(n) (一共2^n种状态，每一种状态添加到集合中需要O(n))
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        //先按照由小到大排序，便于去重
        quickSort(nums, 0, nums.length - 1);

        List<List<Integer>> result = new ArrayList<>();

        //flag标志位表示前一个的元素是否被添加，0：前一个的元素没有被添加，1：前一个的元素被添加
        backtrack(0, nums, 0, new ArrayList<>(), result);

        return result;
    }

    private void backtrack(int t, int[] nums, int flag, List<Integer> list, List<List<Integer>> result) {
        if (t == nums.length) {
            result.add(new ArrayList<>(list));
            return;
        }

        //不添加当前元素
        backtrack(t + 1, nums, 0, list, result);

        //去重，当前元素和前一个元素相等，且前一个元素未被添加，则当前元素不能添加，直接返回
        if (t > 0 && flag == 0 && nums[t - 1] == nums[t]) {
            return;
        }

        //添加当前元素
        list.add(nums[t]);
        backtrack(t + 1, nums, 1, list, result);
        list.remove(list.size() - 1);
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
