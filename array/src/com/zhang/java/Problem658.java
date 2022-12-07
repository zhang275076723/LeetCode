package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/12/7 09:16
 * @Author zsy
 * @Description 找到 K 个最接近的元素 类比Problem4、Problem378、Problem410、Problem1482、FindMaxArrayMinAfterKMinus
 * 给定一个 排序好 的数组 arr ，两个整数 k 和 x ，从数组中找到最靠近 x（两数之差最小）的 k 个数。
 * 返回的结果必须要是按升序排好的。
 * 整数 a 比整数 b 更接近 x 需要满足：
 * |a - x| < |b - x| 或者
 * |a - x| == |b - x| 且 a < b
 * <p>
 * 输入：arr = [1,2,3,4,5], k = 4, x = 3
 * 输出：[1,2,3,4]
 * <p>
 * 输入：arr = [1,2,3,4,5], k = 4, x = -1
 * 输出：[1,2,3,4]
 * <p>
 * 1 <= k <= arr.length
 * 1 <= arr.length <= 10^4
 * arr 按 升序 排列
 * -10^4 <= arr[i], x <= 10^4
 */
public class Problem658 {
    public static void main(String[] args) {
        Problem658 problem658 = new Problem658();
        int[] arr = {1, 2, 3, 4, 5};
        int k = 2;
        int x = 3;
        System.out.println(problem658.findClosestElements(arr, k, x));
        System.out.println(problem658.findClosestElements2(arr, k, x));
        System.out.println(problem658.findClosestElements3(arr, k, x));
    }

    /**
     * 大根堆
     * 时间复杂度O(nlogn)，空间复杂度O(k)
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        //大根堆，arr[0]：当前元素距离x的距离，arr[1]：当前元素的值
        Queue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                if (arr1[0] != arr2[0]) {
                    return arr2[0] - arr1[0];
                } else {
                    return arr2[1] - arr1[1];
                }
            }
        });

        for (int i = 0; i < arr.length; i++) {
            priorityQueue.offer(new int[]{Math.abs(arr[i] - x), arr[i]});

            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        }

        List<Integer> list = new ArrayList<>();

        while (!priorityQueue.isEmpty()) {
            list.add(priorityQueue.poll()[1]);
        }

        //结果由小到大排序
        Collections.sort(list);

        return list;
    }

    /**
     * 双指针
     * 保留k个元素，即要删除n-k个元素，两个指针分别指向首尾元素，将距离x较远的元素删除
     * 时间复杂度O(n-k)，空间复杂度O(1)
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public List<Integer> findClosestElements2(int[] arr, int k, int x) {
        int left = 0;
        int right = arr.length - 1;

        //删除n-k个元素
        for (int i = 0; i < arr.length - k; i++) {
            //arr[left]和arr[right]相比，arr[right]距离x更远或两者距离相同，右指针左移
            if (x - arr[left] <= arr[right] - x) {
                right--;
            } else {
                left++;
            }
        }

        List<Integer> list = new ArrayList<>();

        for (int i = left; i <= right; i++) {
            list.add(arr[i]);
        }

        return list;
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 对长度为k+1的区间左边界进行二分查找，判断左边界arr[mid]和右边界arr[mid+k]哪个更接近x，确定删除左边界还是右边界
     * 时间复杂度O(log(n-k))，空间复杂度O(1)
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public List<Integer> findClosestElements3(int[] arr, int k, int x) {
        //left-right都是长度为k+1的区间左边界
        int left = 0;
        int right = arr.length - k;
        int mid;

        while (left < right) {
            //长度k+1的区间[mid,mid+k]，判断左边界arr[mid]和右边界arr[mid+k]哪个更接近x，确定删除左边界还是右边界
            mid = left + ((right - left) >> 1);

            //右边界arr[mid+k]更接近x，往右边找
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
            } else {
                //左边界arr[mid+k]更接近x，或左右边界距离x相同，往左边找
                right = mid;
            }
        }

        List<Integer> list = new ArrayList<>();

        for (int i = left; i < left + k; i++) {
            list.add(arr[i]);
        }

        return list;
    }
}
