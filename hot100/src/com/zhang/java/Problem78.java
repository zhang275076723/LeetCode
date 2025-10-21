package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/4/26 10:39
 * @Author zsy
 * @Description 子集 类比Problem90 类比Problem320、Problem784、Problem1601、Problem1863、Problem2386 回溯+剪枝类比Problem17、Problem22、Problem37、Problem39、Problem40、Problem46、Problem47、Problem51、Problem52、Problem60、Problem77、Problem89、Problem90、Problem91、Problem93、Problem97、Problem216、Problem254、Problem282、Problem301、Problem306、Problem357、Problem377、Problem386、Problem491、Problem494、Problem679、Problem698、Problem784、Problem842、Problem967、Problem980、Problem1087、Problem1096、Problem1291、Offer17、Offer38、Offer46
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
        System.out.println(problem78.subsets3(nums));
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
     * 模拟
     * 每遍历到nums的一个元素，就将结果集合result中每个子集添加该元素，作为新的子集重新添加回result
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
                //结果集合中每个list，需要当前list的副本，不能在原list上操作，会修改之前的list
                List<Integer> list = new ArrayList<>(result.get(j));
                //list添加当前元素
                list.add(nums[i]);
                //list作为新的子集重新添加回result
                result.add(list);
            }
        }

        return result;
    }

    /**
     * 二进制状态压缩
     * nums的长度为10，使用int整数的每一位表示nums中元素是否存在，当前位为1，则当前元素存在；当前位为0，则当前元素不存在
     * 时间复杂度O(n*2^n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets3(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < (1 << nums.length); i++) {
            List<Integer> list = new ArrayList<>();

            for (int j = 0; j < nums.length; j++) {
                if (((i >>> j) & 1) == 1) {
                    list.add(nums[j]);
                }
            }

            result.add(list);
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
