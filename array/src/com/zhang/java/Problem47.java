package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/8/6 10:00
 * @Author zsy
 * @Description 全排列 II 类比Problem39、Problem40、Problem46
 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 * <p>
 * 输入：nums = [1,1,2]
 * 输出：[[1,1,2],[1,2,1],[2,1,1]]
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * <p>
 * 1 <= nums.length <= 8
 * -10 <= nums[i] <= 10
 */
public class Problem47 {
    public static void main(String[] args) {
        Problem47 problem47 = new Problem47();
        int[] nums = {1, 1, 2};
        System.out.println(problem47.permuteUnique(nums));
    }

    /**
     * 回溯+剪枝，难点在于如何去重
     * 时间复杂度O(n*n!)，空间复杂度O(n) (排列树共n!个叶节点，每一个叶节点对应的解需要O(n)复制到结果集合)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        heapSort(nums);

        List<List<Integer>> result = new ArrayList<>();

        backtrack(nums, 0, new boolean[nums.length], new ArrayList<>(), result);

        return result;
    }

    private void backtrack(int[] nums, int t, boolean[] visited, List<Integer> list, List<List<Integer>> result) {
        if (t == nums.length) {
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            //当前元素值和前一个元素值相同，直接剪枝，去重
            if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) {
                continue;
            }

            if (!visited[i]) {
                list.add(nums[i]);
                visited[i] = true;

                backtrack(nums, t + 1, visited, list, result);

                visited[i] = false;
                list.remove(list.size() - 1);
            }
        }
    }

    private void heapSort(int[] nums) {
        //建堆
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        //堆顶元素和最后一个元素交换，再整堆
        for (int i = nums.length - 1; i > 0; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            heapify(nums, 0, i);
        }
    }

    private void heapify(int[] nums, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && nums[leftIndex] > nums[index]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && nums[rightIndex] > nums[index]) {
            index = rightIndex;
        }

        if (index != i) {
            int temp = nums[index];
            nums[index] = nums[i];
            nums[i] = temp;

            heapify(nums, index, heapSize);
        }
    }
}
