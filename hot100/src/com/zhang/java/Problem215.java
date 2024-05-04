package com.zhang.java;


import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * @Date 2022/5/15 8:30
 * @Author zsy
 * @Description 数组中的第K个最大元素 字节面试题 快排划分类比Problem324、Problem347、Problem462、Problem973、Offer40
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
     * 快排划分
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
     * 小根堆
     * 时间复杂度O(nlogn)，空间复杂度O(k)
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        Queue<Integer> queue = new PriorityQueue<>(nums.length, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });


        for (int i = 0; i < nums.length; i++) {
            queue.offer(nums[i]);
            //小根堆大小超过k时，堆顶元素出堆，保证小根堆中保存最大的k个数
            if (queue.size() > k) {
                queue.poll();
            }
        }

        //当前小根堆堆顶元素即为第k大元素
        return queue.peek();
    }

    /**
     * 手动实现小根堆
     * 时间复杂度O(nlogn)，空间复杂度O(k)
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest3(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }

        //小根堆数组
        int[] arr = new int[k];

        for (int i = 0; i < k; i++) {
            arr[i] = nums[i];
        }

        //建立小根堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = k; i < nums.length; i++) {
            //当前元素大于小根堆堆顶元素，说明堆顶元素不是前k大元素，替换堆顶元素，再进行整堆
            if (nums[i] > arr[0]) {
                arr[0] = nums[i];
                heapify(arr, 0, arr.length);
            }
        }

        //小根堆堆顶元素即为第k大元素
        return arr[0];
    }

    /**
     * 快排划分
     * 平均时间复杂度O(n)，最坏时间复杂度O(n^2)，空间复杂度O(logn)
     *
     * @param nums  当前要排序数组
     * @param left  此次划分的左指针
     * @param right 此次划分的右指针
     * @return
     */
    private int partition(int[] nums, int left, int right) {
        //随机取一个元素作为划分基准，避免性能倒退为O(n^2)
        int randomIndex = (int) (Math.random() * (right - left + 1)) + left;

        int value = nums[randomIndex];
        nums[randomIndex] = nums[left];
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

    /**
     * 小根堆整堆
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param nums
     * @param i
     * @param heapSize
     */
    private void heapify(int[] nums, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && nums[leftIndex] < nums[index]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && nums[rightIndex] < nums[index]) {
            index = rightIndex;
        }

        //继续向下整堆
        if (index != i) {
            int temp = nums[i];
            nums[i] = nums[index];
            nums[index] = temp;
            heapify(nums, index, heapSize);
        }
    }
}
