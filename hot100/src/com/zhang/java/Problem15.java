package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/14 15:17
 * @Author zsy
 * @Description 三数之和 类比Problem1、Problem16、Problem18、Problem454
 * 给你一个包含 n 个整数的数组 nums，
 * 判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * <p>
 * 输入：nums = []
 * 输出：[]
 * <p>
 * 输入：nums = [0]
 * 输出：[]
 * <p>
 * 0 <= nums.length <= 3000
 * -10^5 <= nums[i] <= 10^5
 */
public class Problem15 {
    public static void main(String[] args) {
        Problem15 problem15 = new Problem15();
//        int[] nums = {-1, 0, 1, 2, -1, -4};
        int[] nums = {0, 0, 0, 0};
        System.out.println(problem15.threeSum(nums));
        System.out.println(problem15.threeSum2(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^3)，空间复杂度O(n^3)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }

        //使用hashset去重
        Set<Set<Integer>> set = new HashSet<>();
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        //去重
                        int size = set.size();

                        Set<Integer> set1 = new HashSet<>();
                        set1.add(nums[i]);
                        set1.add(nums[j]);
                        set1.add(nums[k]);
                        set.add(set1);

                        //set集合大小变化说明不重复，则添加
                        if (size != set.size()) {
                            ArrayList<Integer> list = new ArrayList<>();
                            list.add(nums[i]);
                            list.add(nums[j]);
                            list.add(nums[k]);
                            result.add(list);
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * 双指针
     * 先排序，确定第一个元素，左右指针分别指向剩下的两个元素
     * 时间复杂度O(n^2)，空间复杂度O(logn)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum2(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }

        //排序，便于查找和去重
        heapSort(nums);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            //当前元素大于0，和之后两个元素相加，三者之和肯定大于0，直接跳出循环
            if (nums[i] > 0) {
                break;
            }

            //nums[i]去重
            while (i > 0 && i < nums.length - 2 && nums[i] == nums[i - 1]) {
                i++;
            }

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                //三数之和等于0
                if (sum == 0) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    result.add(list);

                    //nums[left]去重
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }

                    //nums[right]去重
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;
                } else if (sum < 0) {
                    //三数之和小于0
                    left++;
                } else {
                    //三数之和大于0
                    right--;
                }
            }
        }

        return result;
    }

    /**
     * 大根堆堆排序
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     */
    public void heapSort(int[] nums) {
        //建堆
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        //最后一个元素与堆顶元素交换
        for (int i = nums.length - 1; i > 0; i--) {
            int temp = nums[i];
            nums[i] = nums[0];
            nums[0] = temp;
            heapify(nums, 0, i);
        }
    }

    /**
     * 大根堆整堆
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param nums
     * @param i
     * @param heapSize
     */
    public void heapify(int[] nums, int i, int heapSize) {
        int maxIndex = i;

        //比较左子节点
        if (2 * i + 1 < heapSize && nums[2 * i + 1] > nums[maxIndex]) {
            maxIndex = 2 * i + 1;
        }

        //比较右子节点
        if (2 * i + 2 < heapSize && nums[2 * i + 2] > nums[maxIndex]) {
            maxIndex = 2 * i + 2;
        }

        if (maxIndex != i) {
            int temp = nums[i];
            nums[i] = nums[maxIndex];
            nums[maxIndex] = temp;

            //继续向下整堆
            heapify(nums, maxIndex, heapSize);
        }
    }
}
