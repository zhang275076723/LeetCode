package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/4/20 8:44
 * @Author zsy
 * @Description 全排列 全排列类比Problem47、Problem60、Problem784 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem47、Problem77、Problem78、Problem89、Problem90、Problem97、Problem216、Problem301、Problem377、Problem491、Problem679、Problem698、Offer17、Offer38
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
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
public class Problem46 {
    public static void main(String[] args) {
        Problem46 problem46 = new Problem46();
        int[] nums = {1, 2, 3};
        System.out.println(problem46.permute(nums));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n*n!)，空间复杂度O(n) (排列树共n!个叶节点，每一个叶节点对应的解需要O(n)复制到结果集合)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        backtrack(0, nums, new boolean[nums.length], result, new ArrayList<>());

        return result;
    }

    private void backtrack(int t, int[] nums, boolean[] visited, List<List<Integer>> result, List<Integer> list) {
        if (t == nums.length) {
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            //当前元素已被访问，直接进行下次循环
            if (visited[i]) {
                continue;
            }

            list.add(nums[i]);
            visited[i] = true;

            backtrack(t + 1, nums, visited, result, list);

            visited[i] = false;
            list.remove(list.size() - 1);
        }
    }
}
