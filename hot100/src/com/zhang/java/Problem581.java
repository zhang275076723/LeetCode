package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/6/14 8:50
 * @Author zsy
 * @Description 最短无序连续子数组 两次遍历类比Problem32、Problem135 数组类比Problem53、Problem135、Problem152、Problem238、Problem416、Problem628、Offer42、Offer66 子序列和子数组类比Problem53、Problem115、Problem152、Problem209、Problem300、Problem325、Problem392、Problem491、Problem516、Problem525、Problem560、Problem659、Problem673、Problem674、Problem718、Problem862、Problem1143、Offer42、Offer57_2
 * 给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
 * 请你找出符合题意的 最短 子数组，并输出它的长度。
 * <p>
 * 输入：nums = [2,6,4,8,10,9,15]
 * 输出：5
 * 解释：你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序。
 * <p>
 * 输入：nums = [1,2,3,4]
 * 输出：0
 * <p>
 * 输入：nums = [1]
 * 输出：0
 * <p>
 * 1 <= nums.length <= 10^4
 * -10^5 <= nums[i] <= 10^5
 */
public class Problem581 {
    public static void main(String[] args) {
        Problem581 problem581 = new Problem581();
        int[] nums = {2, 6, 4, 8, 10, 9, 15};
//        System.out.println(problem581.findUnsortedSubarray(nums));
        System.out.println(problem581.findUnsortedSubarray2(nums));
    }

    /**
     * 先排序，再比较
     * 时间复杂度O(nlogn)，空间复杂度O(n) (堆排序的空间复杂度为O(n))
     *
     * @param nums
     * @return
     */
    public int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        int[] newNums = Arrays.copyOf(nums, nums.length);
        heapSort(nums);

        int left = 0;
        int right = nums.length - 1;

        while (left <= right && nums[left] == newNums[left]) {
            left++;
        }

        while (left <= right && nums[right] == newNums[right]) {
            right--;
        }

        return right - left + 1;
    }

    /**
     * 两次遍历
     * 从前往后遍历，找右边界，如果当前元素nums[i]小于nums[0]-nums[i-1]的最大值，更新无序子数组右边界right；
     * 从后往前遍历，找左边界，如果当前元素nums[i]大于nums[i+1]-nums[nums.length-1]的最小值，更新无序子数组左边界left
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findUnsortedSubarray2(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        //无序子数组左边界
        int left = -1;
        //无序子数组右边界
        int right = -1;
        //nums[0]-nums[i-1]的最大值
        int max = Integer.MIN_VALUE;
        //nums[i+1]-nums[nums.length-1]的最小值
        int min = Integer.MAX_VALUE;

        //从左往右遍历，找中间部分的右边界，nums[i]比nums[0]-nums[i-1]的最大值max还小，说明存在无序子数组，更新right
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < max) {
                right = i;
            }

            //更新nums[0]-nums[i]的最大值
            max = Math.max(max, nums[i]);
        }

        //从右往左遍历，找中间部分的左边界，num[i]比nums[i+1]-nums[nums.length-1]的最小值还大，说明存在无序子数组，更新left
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] > min) {
                left = i;
            }

            //更新nums[i]-nums[nums.length-1]当前最小值
            min = Math.min(min, nums[i]);
        }

        return left == right ? 0 : right - left + 1;
    }

    private void heapSort(int[] nums) {
        //建堆
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        //与堆顶元素交换，整堆
        for (int i = nums.length - 1; i > 0; i--) {
            int temp = nums[i];
            nums[i] = nums[0];
            nums[0] = temp;
            heapify(nums, 0, i);
        }
    }

    private void heapify(int[] nums, int i, int heapSize) {
        int index = i;
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;

        if (leftIndex < heapSize && nums[leftIndex] > nums[index]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && nums[rightIndex] > nums[index]) {
            index = rightIndex;
        }

        if (index != i) {
            int temp = nums[i];
            nums[i] = nums[index];
            nums[index] = temp;
            heapify(nums, index, heapSize);
        }
    }
}
