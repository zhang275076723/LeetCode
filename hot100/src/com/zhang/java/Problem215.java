package com.zhang.java;

import java.util.Random;

/**
 * @Date 2022/5/15 8:30
 * @Author zsy
 * @Description 数组中的第K个最大元素
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * <p>
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 * <p>
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 * <p>
 * 1 <= k <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 */
public class Problem215 {
    public static void main(String[] args) {
        Problem215 problem215 = new Problem215();
        int[] nums = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int k = 4;
        System.out.println(problem215.findKthLargest(nums, k));
        System.out.println(problem215.findKthLargest2(nums, k));
    }

    /**
     * 堆排序，找第k大的元素
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        heapSort(nums);
        int index = nums.length - k;
        return nums[index];
    }

    /**
     * 快排划分变形，快排的每次划分都能确定一个元素所在的位置，通过该位置和index比较，判断是否是第k大元素
     * 时间复杂度O(n)，空间复杂度O(logn)
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        int index = nums.length - k;
        int pivot = partition(nums, 0, nums.length - 1, index);
        return nums[pivot];
    }

    /**
     * 大根堆排序
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     */
    private void heapSort(int[] nums) {
        //建堆
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        //最后一个元素与堆顶元素交换，并整堆
        for (int i = nums.length - 1; i > 0; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;
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
    private void heapify(int[] nums, int i, int heapSize) {
        int leftChild = i * 2 + 1;
        int rightChild = i * 2 + 2;
        int maxIndex = i;

        if (leftChild < heapSize && nums[leftChild] > nums[maxIndex]) {
            maxIndex = leftChild;
        }
        if (rightChild < heapSize && nums[rightChild] > nums[maxIndex]) {
            maxIndex = rightChild;
        }

        //继续向下整堆
        if (maxIndex != i) {
            int temp = nums[i];
            nums[i] = nums[maxIndex];
            nums[maxIndex] = temp;
            heapify(nums, maxIndex, heapSize);
        }
    }

    /**
     * 快排划分变形
     * 时间复杂度O(n)，空间复杂度O(logn)
     *
     * @param nums  当前要排序数组
     * @param left  此次划分的左指针
     * @param right 此次划分的右指针
     * @param index 第k大元素的下标索引
     * @return
     */
    private int partition(int[] nums, int left, int right, int index) {
        //随机取一个元素作为划分基准，避免性能倒退为O(n^2)
        int randomIndex = (int) (Math.random() * (right - left + 1)) + left;
        int temp = nums[randomIndex];
        nums[randomIndex] = nums[left];
        nums[left] = temp;

        //用于递归
        int l = left;
        int r = right;

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

        if (left == index) {
            return left;
        } else if (left < index) {
            return partition(nums, left + 1, r, index);
        } else {
            return partition(nums, l, left - 1, index);
        }
    }
}
