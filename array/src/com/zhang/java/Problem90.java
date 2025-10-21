package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/11/9 08:38
 * @Author zsy
 * @Description 子集 II 类比Problem78、Problem491 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem89、Problem97、Problem216、Problem301、Problem377、Problem491、Problem679、Problem698、Offer17、Offer38
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
        System.out.println(problem90.subsetsWithDup2(nums));
    }

    /**
     * 回溯+剪枝，难点在于如何去重(不建议使用set去重)
     * 核心思想：当前子集的末尾元素和当前元素相等，为了去重，只能添加当前元素，不能不添加当前元素
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

        //last：当前子集的最后一个元素，当前元素和last相等，为了去重，只能添加当前元素，不能不添加当前元素
        //初始化last为int最小值，表示当前子集为空
        backtrack(0, Integer.MIN_VALUE, nums, new ArrayList<>(), result);

        return result;
    }

    /**
     * 二进制状态压缩
     * 核心思想：当前子集的末尾元素和当前元素相等，为了去重，只能添加当前元素，不能不添加当前元素
     * nums的长度为10，使用int整数的每一位表示nums中元素是否存在，当前位为1，则当前元素存在；当前位为0，则当前元素不存在
     * 时间复杂度O(n*2^n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithDup2(int[] nums) {
        //先按照由小到大排序，便于去重
        quickSort(nums, 0, nums.length - 1);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < (1 << nums.length); i++) {
            List<Integer> list = new ArrayList<>();
            //当前二进制状态i对应的子集是否合法标志位
            boolean flag = true;

            for (int j = 0; j < nums.length; j++) {
                //当前二进制状态i中的当前元素nums[j]没有添加，但前一个元素nums[j-1]添加了，会导致重复，则不合法
                if (j > 0 && nums[j] == nums[j - 1] && ((i >>> j) & 1) == 0 && ((i >>> (j - 1)) & 1) == 1) {
                    flag = false;
                    break;
                }

                if (((i >>> j) & 1) == 1) {
                    list.add(nums[j]);
                }
            }

            if (flag) {
                result.add(list);
            }
        }

        return result;
    }

    private void backtrack(int t, int last, int[] nums, List<Integer> list, List<List<Integer>> result) {
        if (t == nums.length) {
            result.add(new ArrayList<>(list));
            return;
        }

        //当前元素和last相等，为了去重，只能添加当前元素，不能不添加当前元素
        if (last == nums[t]) {
            list.add(nums[t]);
            backtrack(t + 1, nums[t], nums, list, result);
            list.remove(list.size() - 1);
        } else {
            //当前元素和last不相等，可以添加当前元素，也可以不添加当前元素
            backtrack(t + 1, last, nums, list, result);

            list.add(nums[t]);
            backtrack(t + 1, nums[t], nums, list, result);
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
