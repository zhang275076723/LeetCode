package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/4/20 8:44
 * @Author zsy
 * @Description 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]]
 * <p>
 * 输入：nums = [0,1]
 * 输出：[[0,1], [1,0]]
 * <p>
 * 输入：nums = [1]
 * 输出：[[1]]
 * <p>
 * 1 <= nums.length <= 6
 * -10 <= nums[i] <= 10
 * nums 中的所有整数 互不相同
 */
public class Problem046 {
    public static void main(String[] args) {
        Problem046 problem046 = new Problem046();
        int[] nums = {1, 2, 3};
        System.out.println(problem046.permute(nums));
    }

    /**
     * 回溯+剪枝，时间复杂度O(n*n!)，空间复杂度O(n)，排列树共n!个叶节点，每一个叶节点对应的解需要O(n)复制到结果集合
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, nums, new boolean[nums.length], result, new ArrayList<>());
        return result;
    }

    private void backtrack(int t, int[] nums, boolean[] visited,
                           List<List<Integer>> result, List<Integer> list) {
        if (t == nums.length) {
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                list.add(nums[i]);
                visited[i] = true;
                backtrack(t + 1, nums, visited, result, list);
                list.remove(list.size() - 1);
                visited[i] = false;
            }
        }
    }
}
