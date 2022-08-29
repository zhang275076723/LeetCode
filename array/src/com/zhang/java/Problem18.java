package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/8/29 8:27
 * @Author zsy
 * @Description 四数之和 类比Problem1、Problem15、Problem16、Problem454
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。
 * 请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]]
 * （若两个四元组元素一一对应，则认为两个四元组重复）：
 * 0 <= a, b, c, d < n
 * a、b、c 和 d 互不相同
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * 你可以按 任意顺序 返回答案 。
 * <p>
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * <p>
 * 输入：nums = [2,2,2,2,2], target = 8
 * 输出：[[2,2,2,2]]
 * <p>
 * 1 <= nums.length <= 200
 * -10^9 <= nums[i] <= 10^9
 * -10^9 <= target <= 10^9
 */
public class Problem18 {
    public static void main(String[] args) {
        Problem18 problem18 = new Problem18();
//        int[] nums = {1, 0, -1, 0, -2, 2};
//        int target = 0;
        int[] nums = {0, 0, 0, -1000000000, -1000000000, -1000000000, -1000000000};
        int target = -1000000000;
        System.out.println(problem18.fourSum(nums, target));
    }

    /**
     * 双指针
     * 先排序，确定第一个和第二个数，左右指针分为指向剩下的两个数
     * 时间复杂度O(n^3)，空间复杂度O(n)
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums == null || nums.length < 4) {
            return new ArrayList<>();
        }

        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 3; i++) {
            //nums[i]和之后的三个元素之和大于target，说明后面没有满足要求的四数之和，直接跳出循环
            //相加使用long，避免溢出
            if ((long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }

            //nums[i]去重
            while (i > 0 && i < nums.length - 3 && nums[i] == nums[i - 1]) {
                i++;
            }

            for (int j = i + 1; j < nums.length - 2; j++) {
                //nums[i]、nums[i]和之后的两个元素之和大于target，说明后面没有满足要求的四数之和，直接跳出循环
                //相加使用long，避免溢出
                if ((long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }

                //nums[j]去重
                while (j > i + 1 && j < nums.length - 2 && nums[j] == nums[j - 1]) {
                    j++;
                }

                int left = j + 1;
                int right = nums.length - 1;

                while (left < right) {
                    //四数之和等于target
                    if (nums[i] + nums[j] + nums[left] + nums[right] == target) {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
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
                    } else if (nums[i] + nums[j] + nums[left] + nums[right] < target) {
                        //四数之和小于target
                        left++;
                    } else {
                        //四数之和大于target
                        right--;
                    }
                }
            }
        }

        return result;
    }

    private void mergeSort(int[] nums, int left, int right, int[] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);

        mergeSort(nums, left, mid, tempArr);
        mergeSort(nums, mid + 1, right, tempArr);
        merge(nums, left, mid, right, tempArr);
    }

    private void merge(int[] nums, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (nums[i] < nums[j]) {
                tempArr[k] = nums[i];
                i++;
            } else {
                tempArr[k] = nums[j];
                j++;
            }
            k++;
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
