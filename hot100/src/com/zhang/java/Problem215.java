package com.zhang.java;


import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2022/5/15 8:30
 * @Author zsy
 * @Description 数组中的第K个最大元素 类比Problem347、Problem692、Offer40 字节面试题
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
//        System.out.println(problem215.findKthLargest2(nums, k));
//        System.out.println(problem215.findKthLargest3(nums, k));
    }

    /**
     * 快排划分变形
     * 快排的每次划分都能确定一个元素所在的位置，通过该位置和index比较，判断是否是第k大元素
     * 期望时间复杂度O(n)，空间复杂度O(logn)
     * 最坏时间复杂度O(n^2)，最坏空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        int left = 0;
        int right = nums.length - 1;
        int pivot = partition(nums, left, right);

        while (pivot != nums.length - k) {
            //基准pivot在前k大之前
            if (pivot < nums.length - k) {
                left = pivot + 1;
                pivot = partition(nums, left, right);
            } else if (pivot > nums.length - k) {
                //基准pivot在前k大之后
                right = pivot - 1;
                pivot = partition(nums, left, right);
            }
        }

        return nums[pivot];
    }

    /**
     * 大根堆
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(nums.length, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        //建大根堆
        for (int i = 0; i < nums.length; i++) {
            priorityQueue.offer(nums[i]);
        }

        //移除堆顶k-1个元素
        for (int i = 1; i < k; i++) {
            priorityQueue.poll();
        }

        //当前堆顶即为第k大元素
        return priorityQueue.poll();
    }

    /**
     * 手动实现大根堆
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest3(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        //建堆
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        int heapSize = nums.length;

        //移除堆顶k-1个元素
        for (int i = 1; i < k; i++) {
            int temp = nums[0];
            nums[0] = nums[heapSize - 1];
            nums[heapSize - 1] = temp;

            heapSize--;
            heapify(nums, 0, heapSize);
        }

        //当前堆顶即为第k大元素
        return nums[0];
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
        int maxIndex = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && nums[leftIndex] > nums[maxIndex]) {
            maxIndex = leftIndex;
        }
        if (rightIndex < heapSize && nums[rightIndex] > nums[maxIndex]) {
            maxIndex = rightIndex;
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
     * 平均时间复杂度O(n)，最坏时间复杂度O(n^2)，空间复杂度O(logn)
     *
     * @param nums  当前要排序数组
     * @param left  此次划分的左指针
     * @param right 此次划分的右指针
     * @return
     */
    private int partition(int[] nums, int left, int right) {
        //随机取一个元素作为划分基准，避免性能倒退为O(n^2)
        int index = (int) (Math.random() * (right - left + 1)) + left;

        int value = nums[index];
        nums[index] = nums[left];
        nums[left] = value;

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
