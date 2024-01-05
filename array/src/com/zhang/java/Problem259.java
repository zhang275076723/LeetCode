package com.zhang.java;

/**
 * @Date 2023/12/29 08:10
 * @Author zsy
 * @Description 较小的三数之和 双指针类比
 * 给定一个长度为 n 的整数数组和一个目标值 target ，
 * 寻找能够使条件 nums[i] + nums[j] + nums[k] < target 成立的三元组  i, j, k 个数（0 <= i < j < k < n）。
 * <p>
 * 输入: nums = [-2,0,1,3], target = 2
 * 输出: 2
 * 解释: 因为一共有两个三元组满足累加和小于 2:
 * [-2,0,1]
 * [-2,0,3]
 * <p>
 * 输入: nums = [], target = 0
 * 输出: 0
 * <p>
 * 输入: nums = [0], target = 0
 * 输出: 0
 * <p>
 * n == nums.length
 * 0 <= n <= 3500
 * -100 <= nums[i] <= 100
 * -100 <= target <= 100
 */
public class Problem259 {
    public static void main(String[] args) {
        Problem259 problem259 = new Problem259();
        int[] nums = {-2, 0, 1, 3};
        int target = 2;
        System.out.println(problem259.threeSumSmaller(nums, target));
    }

    /**
     * 排序+双指针
     * 由小到大排序，从小到大确定最小的元素nums[i]，判断nums[i]+nums[left]+nums[right]和target大小关系，
     * 如果nums[i]+nums[left]+nums[right]<target，则nums[i]+nums[left]+nums[left+1]-nums[right]都小于target，
     * 有right-left个满足nums[left]+nums[right]+nums[i]<target，left++；
     * 如果nums[i]+nums[left]+nums[right]>target，则nums[i]+nums[left]+nums[left+1]-nums[right]都大于target，right--
     * 时间复杂度O(n^2)，空间复杂度O(n) (归并排序的空间复杂度为O(n))
     *
     * @param nums
     * @param target
     * @return
     */
    public int threeSumSmaller(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }

        //由小到大排序
        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);

        int count = 0;

        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                if (nums[left] + nums[right] + nums[i] < target) {
                    count = count + right - left;
                    left++;
                } else {
                    right--;
                }
            }
        }

        return count;
    }

    private void mergeSort(int[] arr, int left, int right, int[] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);
        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
    }

    private void merge(int[] arr, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                tempArr[k] = arr[i];
                i++;
                k++;
            } else {
                tempArr[k] = arr[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = arr[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            arr[k] = tempArr[k];
        }
    }
}
