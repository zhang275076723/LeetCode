package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/2 10:09
 * @Author zsy
 * @Description 前 K 个高频元素 华为机试题 腾讯机试题 字节面试题 快排划分类比Problem215、Problem324、Problem462、Problem973、Offer40
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
        int k = 2;
        System.out.println(Arrays.toString(problem347.topKFrequent(nums, k)));
        System.out.println(Arrays.toString(problem347.topKFrequent2(nums, k)));
        System.out.println(Arrays.toString(problem347.topKFrequent3(nums, k)));
    }

    /**
     * 快排划分
     * 使用哈希表统计数组中元素出现次数，使用数组放元素，arr[i][0]：当前元素，arr[i][1]：出现次数
     * 根据快排划分思想，得到前k大的元素
     * 平均时间复杂度O(n)，最坏时间复杂度O(n^2)，空间复杂度O(n)，
     * 哈希表大小为O(n)，快排划分最好空间复杂度O(logn)，快排划分最坏空间复杂度O(n)
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

        //出现次数数组，arr[i][0]：当前元素，arr[i][1]：出现次数
        int[][] arr = new int[map.size()][2];
        int index = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            arr[index] = new int[]{entry.getKey(), entry.getValue()};
            index++;
        }

        int left = 0;
        int right = arr.length - 1;
        int pivot = partition(arr, left, right);

        while (pivot != arr.length - k) {
            //基准pivot在前k大频率之前
            if (pivot < arr.length - k) {
                left = pivot + 1;
                pivot = partition(arr, left, right);
            } else if (pivot > arr.length - k) {
                //基准pivot在前k大频率之后
                right = pivot - 1;
                pivot = partition(arr, left, right);
            }
        }

        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            result[i] = arr[arr.length - k + i][0];
        }

        return result;
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
    public int[] topKFrequent2(int[] nums, int k) {
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
            priorityQueue.offer(new int[]{entry.getKey(), entry.getValue()});

            //小根堆大小超过k时，堆顶元素出堆，保证小根堆中保存前k个高频单词
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        }

        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            result[i] = priorityQueue.poll()[0];
        }

        return result;
    }

    /**
     * 手动实现小根堆
     * 时间复杂度O(nlogk)，空间复杂度O(n) (哈希表大小为O(n)，堆大小为O(k))
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

        //小根堆数组索引下标
        int index = 0;
        //小根堆数组，arr[i][0]：当前元素，arr[i][1]：出现次数
        int[][] arr = new int[k][2];

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (index < k) {
                arr[index] = new int[]{entry.getKey(), entry.getValue()};
                index++;

                //建立大小为k的小根堆
                if (index == k) {
                    for (int i = k / 2 - 1; i >= 0; i--) {
                        heapify(arr, i, k);
                    }
                }
            } else {
                //当前元素频率大于小根堆堆顶元素频率，说明堆顶元素不是前k个高频单词，替换堆顶元素，再进行整堆
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
     * 快排划分
     * 平均时间复杂度O(n)，最坏时间复杂度O(n^2)，最好空间复杂度O(logn)，最坏空间复杂度O(n)
     *
     * @param arr   当前排序数组
     * @param left  本次划分的左基准
     * @param right 本次划分的右基准
     * @return
     */
    private int partition(int[][] arr, int left, int right) {
        //随机取一个元素作为划分基准，避免性能倒退为O(n^2)
        int randomIndex = (int) (Math.random() * (right - left + 1)) + left;

        int[] value = arr[left];
        arr[left] = arr[randomIndex];
        arr[randomIndex] = value;

        int[] temp = arr[left];

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

        return left;
    }

    /**
     * 小根堆整堆
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param arr
     * @param i
     * @param heapSize
     */
    private void heapify(int[][] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;

        if (leftIndex < heapSize && arr[leftIndex][1] < arr[index][1]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[rightIndex][1] < arr[index][1]) {
            index = rightIndex;
        }

        if (index != i) {
            int[] temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;

            heapify(arr, index, heapSize);
        }
    }
}