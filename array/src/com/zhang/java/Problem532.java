package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/6/26 08:38
 * @Author zsy
 * @Description 数组中的 k-diff 数对 排序+滑动窗口类比Problem632、Problem1838、Problem2009 哈希表类比Problem1、Problem128、Problem166、Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem535、Problem554、Problem609、Problem763、Problem1500、Problem1640、Problem2657、Offer50
 * 给你一个整数数组 nums 和一个整数 k，请你在数组中找出 不同的 k-diff 数对，并返回不同的 k-diff 数对 的数目。
 * k-diff 数对定义为一个整数对 (nums[i], nums[j]) ，并满足下述全部条件：
 * 0 <= i, j < nums.length
 * i != j
 * |nums[i] - nums[j]| == k
 * 注意，|val| 表示 val 的绝对值。
 * <p>
 * 输入：nums = [3, 1, 4, 1, 5], k = 2
 * 输出：2
 * 解释：数组中有两个 2-diff 数对, (1, 3) 和 (3, 5)。
 * 尽管数组中有两个 1 ，但我们只应返回不同的数对的数量。
 * <p>
 * 输入：nums = [1, 2, 3, 4, 5], k = 1
 * 输出：4
 * 解释：数组中有四个 1-diff 数对, (1, 2), (2, 3), (3, 4) 和 (4, 5) 。
 * <p>
 * 输入：nums = [1, 3, 1, 5, 4], k = 0
 * 输出：1
 * 解释：数组中只有一个 0-diff 数对，(1, 1) 。
 * <p>
 * 1 <= nums.length <= 10^4
 * -10^7 <= nums[i] <= 10^7
 * 0 <= k <= 10^7
 */
public class Problem532 {
    public static void main(String[] args) {
        Problem532 problem532 = new Problem532();
//        int[] nums = {3, 1, 4, 1, 5};
//        int k = 2;
        int[] nums = {1, 1, 1, 1, 1};
        int k = 0;
        System.out.println(problem532.findPairs(nums, k));
        System.out.println(problem532.findPairs2(nums, k));
        System.out.println(problem532.findPairs3(nums, k));
    }

    /**
     * 哈希表
     * 统计nums数组中每个元素的出现次数，遍历map中元素num，判断map中是否存在num+k
     * 注意：遍历map中元素num时，只能找num-k或者num+k其中之一，两个都找则会存在重复的情况
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int findPairs(int[] nums, int k) {
        //key：nums中元素，value：当前元素出现的次数
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int count = 0;

        //注意：遍历map，而不是遍历nums，避免nums元素重复
        for (int num : map.keySet()) {
            //k为0的特殊情况，如果map中当前元素num出现次数大于1，则存在两个数的差为0，count++
            if (k == 0) {
                if (map.get(num) > 1) {
                    count++;
                }
            } else {
                //注意：遍历map中元素num时，只能找num-k或者num+k其中之一，两个都找则会存在重复的情况
                if (map.containsKey(num + k)) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 排序+二分查找
     * nums由小到大排序，对数组中每个元素，往右二分查找nums[i]+k
     * 时间复杂度O(nlogn)，空间复杂度O(logn) (堆排序的空间复杂度为O(logn))
     *
     * @param nums
     * @param k
     * @return
     */
    public int findPairs2(int[] nums, int k) {
        //nums数组中元素由小到大排序
        heapSort(nums);

        int count = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            //去重，nums[i]和nums[i-1]相等，直接进行下次循环
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            //nums[i]对应要二分查找的元素
            int target = nums[i] + k;
            int left = i + 1;
            int right = nums.length - 1;
            int mid;

            //二分查找target
            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (nums[mid] == target) {
                    count++;
                    break;
                } else if (nums[mid] > target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }

        return count;
    }

    /**
     * 排序+滑动窗口
     * nums由小到大排序，如果nums[right]-nums[left]等于k，则count++，left++；
     * 如果nums[right]-nums[left]大于k，则left++；如果nums[right]-nums[left]小于k，则right++
     * 时间复杂度O(nlogn)，空间复杂度O(logn) (堆排序的空间复杂度为O(logn))
     *
     * @param nums
     * @param k
     * @return
     */
    public int findPairs3(int[] nums, int k) {
        //nums数组中元素由小到大排序
        heapSort(nums);

        int count = 0;
        int left = 0;
        int right = 1;

        while (right < nums.length) {
            if (nums[right] - nums[left] == k) {
                count++;
                left++;
            } else if (nums[right] - nums[left] > k) {
                left++;
            } else {
                right++;
            }

            //nums[left]去重，nums[i]和nums[i-1]相等，直接进行下次循环
            //因为找到left和right时，是left++，所以left去重，如果是right++，则需要right去重
            while (left > 0 && left < nums.length && nums[left] == nums[left - 1]) {
                left++;
            }

            //始终保持right大于left
            if (left >= right) {
                right = left + 1;
            }
        }

        return count;
    }

    private void heapSort(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && arr[leftIndex] > arr[index]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[rightIndex] > arr[index]) {
            index = rightIndex;
        }

        if (index != i) {
            int temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
