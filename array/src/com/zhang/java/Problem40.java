package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/4/19 10:25
 * @Author zsy
 * @Description 组合总和 II 回溯+剪枝类比Problem17、Problem39、Problem46、Problem47、Problem77、Problem78、Problem90、Problem97、Offer17、Offer38
 * 给定一个候选人编号的集合 candidates 和一个目标数 target ，
 * 找出 candidates中所有可以使数字和为 target 的组合。
 * candidates 中的每个数字在每个组合中只能使用 一次 。
 * 注意：解集不能包含重复的组合。
 * <p>
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 输出: [[1,1,6], [1,2,5], [1,7], [2,6]]
 * <p>
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 输出: [[1,2,2], [5]]
 * <p>
 * 1 <= candidates.length <= 100
 * 1 <= candidates[i] <= 50
 * 1 <= target <= 30
 */
public class Problem40 {
    public static void main(String[] args) {
        Problem40 problem40 = new Problem40();
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;
        System.out.println(problem40.combinationSum2(candidates, target));
    }

    /**
     * 回溯+剪枝，难点在于如何去重(不建议使用set去重)
     * 时间复杂度O(n*2^n)，空间复杂度O(n) (共有2^n种组合，每个组合需要O(n)复制到结果集合中)
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return new ArrayList<>();
        }

        //将元素从小到大排序，便于剪枝去重
        mergeSort(candidates, 0, candidates.length - 1, new int[candidates.length]);

        List<List<Integer>> result = new ArrayList<>();

        //使用访问数组
        backtrack(0, candidates, target, new boolean[candidates.length], result, new ArrayList<>());

        //不使用访问数组
//        backtrack(0, candidates, target, result, new ArrayList<>());

        return result;
    }

    /**
     * @param t          当前元素索引
     * @param candidates 目标数组
     * @param target     要求元素之和
     * @param visited    结果去重，在i > 0 && candidates[i] == candidates[i - 1] && !visited[i-1]的情况下去重
     * @param result     结果集合
     * @param list       每个满足元素之和为target的结果
     */
    private void backtrack(int t, int[] candidates, int target, boolean[] visited,
                           List<List<Integer>> result, List<Integer> list) {
        if (target == 0) {
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = t; i < candidates.length; i++) {
            //当target - candidates[i] < 0时，因为数组经过排序是有序的，所以当前元素和之后元素都不满足要求，直接剪枝
            if (target - candidates[i] < 0) {
                return;
            }

            //去重，当candidates[i]和candidates[i-1]相等时，上次循环已经把candidates[i-1]的情况遍历过了，
            //本次candidates[i]就不需要再遍历了
            //注意：是i > t，不是i > 0，相同递归深度的list不能添加重复
//            if (i > t && candidates[i] == candidates[i - 1]) {
//                continue;
//            }

            //或者使用visited访问数组，在i > 0 && candidates[i] == candidates[i - 1] && !visited[i-1]的情况下，
            //当前字符和前一个字符相同，并且前一个字符没有被访问，说明本次和上次情况相同，直接进行下次循环
            if (i > 0 && !visited[i - 1] && candidates[i] == candidates[i - 1]) {
                continue;
            }

            list.add(candidates[i]);
            visited[i] = true;

            //和39题区别，这里是i+1，因为元素只能使用一次，而39题是i
            backtrack(i + 1, candidates, target - candidates[i], visited, result, list);

            visited[i] = false;
            list.remove(list.size() - 1);
        }
    }

    private void mergeSort(int[] nums, int left, int right, int[] tempArr) {
        if (left < right) {
            //注意 >> 的运算优先级比 + 低，所以要添加括号
            int mid = left + ((right - left) >> 1);
            mergeSort(nums, left, mid, tempArr);
            mergeSort(nums, mid + 1, right, tempArr);
            merge(nums, left, mid, right, tempArr);
        }
    }

    private void merge(int[] nums, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (nums[i] < nums[j]) {
                tempArr[k] = nums[i];
                i++;
                k++;
            } else {
                tempArr[k] = nums[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = nums[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = nums[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            nums[k] = tempArr[k];
        }
    }
}
