package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/4/26 10:39
 * @Author zsy
 * @Description 子集 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem46、Problem47、Problem77、Problem89、Problem90、Problem97、Problem216、Problem301、Problem377、Problem491、Problem679、Problem698、Offer17、Offer38
 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 * <p>
 * 输入：nums = [0]
 * 输出：[[],[0]]
 * <p>
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 * nums 中的所有元素 互不相同
 */
public class Problem78 {
    public static void main(String[] args) {
        Problem78 problem78 = new Problem78();
        int[] nums = {1, 2, 3};
        System.out.println(problem78.subsets(nums));
        System.out.println(problem78.subsets2(nums));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n*2^n)，空间复杂度O(n) (一共2^n种状态，每一种状态添加到集合中需要O(n))
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();

        backtrack(0, nums, new ArrayList<>(), result);

        return result;
    }

    /**
     * 每遍历到一个元素，就将原结果集合中每个list添加该元素，重新添加回结果集合
     * 时间复杂度O(n*2^n)，空间复杂度O(1) (一共2^n种状态，获取结果集合每个list需要O(n))
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        //先往结果集合中添加空集合，作为起始元素
        result.add(new ArrayList<>());

        for (int i = 0; i < nums.length; i++) {
            //因为result的大小size会变化，所以不能在里面的for循环中写成j < result.size()，会造成死循环
            int size = result.size();

            for (int j = 0; j < size; j++) {
                //结果集合每个list，需要当前list的副本，不能在原list上操作，会修改之后的list
                List<Integer> list = new ArrayList<>(result.get(j));
                //list添加当前元素
                list.add(nums[i]);
                //重新添加回结果集合
                result.add(list);
            }
        }

        return result;
    }

    private void backtrack(int t, int[] nums, List<Integer> list, List<List<Integer>> result) {
        if (t == nums.length) {
            result.add(new ArrayList<>(list));
            return;
        }

        //不添加当前元素
        backtrack(t + 1, nums, list, result);

        //添加当前元素
        list.add(nums[t]);
        backtrack(t + 1, nums, list, result);
        list.remove(list.size() - 1);
    }
}
