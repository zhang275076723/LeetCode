package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/2 10:09
 * @Author zsy
 * @Description 前 K 个高频元素 华为机试题、腾讯机试题 类比Problem215
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
 * <p>
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * <p>
 * 输入: nums = [1], k = 1
 * 输出: [1]
 * <p>
 * 1 <= nums.length <= 10^5
 * k 的取值范围是 [1, 数组中不相同的元素的个数]
 * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的
 */
public class Problem347 {
    public static void main(String[] args) {
        Problem347 problem347 = new Problem347();
//        int[] nums = {1, 1, 1, 2, 2, 3};
        int[] nums = {4, 1, -1, 2, -1, 2, 3};
        System.out.println(Arrays.toString(problem347.topKFrequent(nums, 2)));
        System.out.println(Arrays.toString(problem347.topKFrequent2(nums, 2)));
        System.out.println(Arrays.toString(problem347.topKFrequent3(nums, 2)));
    }

    /**
     * 小根堆
     * 使用哈希表统计数组中元素出现次数，再将哈希表中元素存放到大小为k的小根堆中，
     * 如果小根堆元素个数小于k，则直接插入；
     * 如果小根堆元素个数大于等于k，则与堆顶元素比较，
     * 如果堆顶元素出现的次数小于当前元素出现的次数，则当前元素替换堆顶元素；否则，不替换
     * 时间复杂度O(nlogk)，空间复杂度O(n)，哈希表大小为O(n)，堆大小为O(k)
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent(int[] nums, int k) {
        if (nums.length == k) {
            return nums;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        //小根堆，使用数组存放元素，int[0]：当前元素，int[1]：出现次数
        Queue<int[]> priorityQueue = new PriorityQueue<>(k, new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] - arr2[1];
            }
        });

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (priorityQueue.size() < k) {
                priorityQueue.offer(new int[]{entry.getKey(), entry.getValue()});
            } else {
                if (priorityQueue.peek()[1] < entry.getValue()) {
                    priorityQueue.poll();
                    priorityQueue.offer(new int[]{entry.getKey(), entry.getValue()});
                }
            }
        }

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = priorityQueue.poll()[0];
        }
        return result;
    }

    /**
     * 手动建立小根堆实现，同topKFrequent(int[] nums, int k)
     * 时间复杂度O(nlogk)，空间复杂度O(n)，哈希表大小为O(n)，堆大小为O(k)
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent2(int[] nums, int k) {
        if (nums.length == k) {
            return nums;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        //小根堆数组索引下标
        int index = 0;
        //小根堆数组，arr[i][0]：当前元素，arr[i][1]：出现次数
        int[][] arr = new int[k][2];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (index < k) {
                arr[index] = new int[]{entry.getKey(), entry.getValue()};
                index++;
                //建堆
                if (index == k) {
                    for (int i = k / 2 - 1; i >= 0; i--) {
                        heapify(arr, i, k);
                    }
                }
            } else {
                if (arr[0][1] < entry.getValue()) {
                    arr[0] = new int[]{entry.getKey(), entry.getValue()};
                    heapify(arr, 0, k);
                }
            }
        }

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = arr[i][0];
        }
        return result;
    }

    /**
     * 快排划分变形
     * 使用哈希表统计数组中元素出现次数，使用数组放元素，arr[i][0]：当前元素，arr[i][1]：出现次数
     * 根据快排划分思想，得到前k大的元素
     * 平均时间复杂度O(n)，最坏时间复杂度O(n^2)，空间复杂度O(n)，
     * 哈希表大小为O(n)，快排划分最好空间复杂度O(logn)，快排划分最坏空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent3(int[] nums, int k) {
        if (nums.length == k) {
            return nums;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        //出现次数数组，result[i][0]：当前元素，result[i][1]：出现次数
        int[][] arr = new int[map.size()][2];
        int index = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            arr[index] = new int[]{entry.getKey(), entry.getValue()};
            index++;
        }

        int pivot = partition(arr, 0, arr.length - 1, arr.length - k);

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = arr[pivot + i][0];
        }
        return result;
    }

    /**
     * 小根堆整堆
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param arr
     * @param index
     * @param heapSize
     */
    private void heapify(int[][] arr, int index, int heapSize) {
        int leftIndex = 2 * index + 1;
        int rightIndex = 2 * index + 2;
        int minIndex = index;

        if (leftIndex < heapSize && arr[leftIndex][1] < arr[minIndex][1]) {
            minIndex = leftIndex;
        }
        if (rightIndex < heapSize && arr[rightIndex][1] < arr[minIndex][1]) {
            minIndex = rightIndex;
        }

        if (minIndex != index) {
            int[] temp = arr[index];
            arr[index] = arr[minIndex];
            arr[minIndex] = temp;
            heapify(arr, minIndex, heapSize);
        }
    }

    /**
     * 快排划分变形
     * 平均时间复杂度O(n)，最坏时间复杂度O(n^2)，最好空间复杂度O(logn)，最坏空间复杂度O(n)
     *
     * @param arr   当前排序数组
     * @param left  本次划分的左基准
     * @param right 本次划分的右基准
     * @param index 第k大元素的下标索引
     * @return
     */
    private int partition(int[][] arr, int left, int right, int index) {
        //随机取一个元素作为划分基准，避免性能倒退为O(n^2)
        int randomIndex = (int) Math.random() * (right - left + 1) + left;
        int[] temp = arr[randomIndex];
        arr[randomIndex] = arr[left];
        arr[left] = temp;

        //用于递归
        int l = left;
        int r = right;

        while (left < right) {
            while (left < right && arr[right][1] >= temp[1]) {
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left][1] <= temp[1]) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = temp;

        if (left == index) {
            return left;
        } else if (left < index) {
            return partition(arr, left + 1, r, index);
        } else {
            return partition(arr, l, left - 1, index);
        }
    }
}