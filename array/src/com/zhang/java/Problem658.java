package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/12/7 09:16
 * @Author zsy
 * @Description 找到 K 个最接近的元素 优先队列类比 双指针类比 类比Interview_10_05 二分查找类比Problem4、Problem287、Problem373、Problem378、Problem410、Problem441、Problem644、Problem668、Problem719、Problem786、Problem878、Problem1201、Problem1482、Problem1508、Problem1723、Problem2305、Problem2498、CutWood、FindMaxArrayMinAfterKMinus
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
        System.out.println(problem658.findClosestElements4(arr, k, x));
    }

    /**
     * 优先队列，大根堆
     * 时间复杂度O(nlogk+klogk)，空间复杂度O(k)
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
                //先按照距离x的大小由大到小排序，再按照元素值由大到小排序
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

        //list中元素由小到大排序
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        return list;
    }

    /**
     * 双指针
     * 保留k个元素，则需要删除n-k个元素，左右指针分别指向首尾下标，比较arr[left]和arr[right]哪个离x远，将距离x较远的指针移动
     * 时间复杂度O(n)，空间复杂度O(1)
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
            //arr[left]和arr[right]相比，arr[right]距离x更远或两者距离相同时，右指针左移
            if (x - arr[left] <= arr[right] - x) {
                right--;
            } else {
                left++;
            }
        }

        List<Integer> list = new ArrayList<>();

        //arr[left,right]为最接近x的k个元素
        for (int i = left; i <= right; i++) {
            list.add(arr[i]);
        }

        return list;
    }

    /**
     * 二分查找+双指针
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 二分查找得到arr中大于等于x的最小下标索引，双指针从arr中大于等于x的最小下标索引开始向两边找，得到最接近x的k个元素
     * 时间复杂度O(logn+k)，空间复杂度O(1)
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public List<Integer> findClosestElements3(int[] arr, int k, int x) {
        int left = 0;
        int right = arr.length - 1;
        int mid;

        //二分查找得到arr中大于等于x的最小下标索引
        while (left < right) {
            mid = left + ((right - left) >> 1);

            if (arr[mid] < x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        int l = left - 1;
        int r = left;

        for (int i = 0; i < k; i++) {
            //左指针l越界，则arr[r]为接近x的元素，右指针r右移
            if (l == -1) {
                r++;
            } else if (r == arr.length) {
                //右指针r越界，则arr[l]为接近x的元素，左指针l左移
                l--;
            } else if (x - arr[l] <= arr[r] - x) {
                //arr[l]为接近x的元素，左指针l左移
                l--;
            } else {
                //arr[r]为接近x的元素，右指针r右移
                r++;
            }
        }

        List<Integer> list = new ArrayList<>();

        //arr[l+1,r-1]为最接近x的k个元素
        for (int i = l + 1; i < r; i++) {
            list.add(arr[i]);
        }

        return list;
    }

    /**
     * 二分查找
     * 对[left,right]进行二分查找，left为0，right为n-k，判断arr[mid]和arr[mid+k]作为最接近x的k个元素的左区间，arr[mid]和arr[mid+k]距离x的大小关系，
     * 如果arr[mid]和x的距离大于arr[mid+k]和x的距离，即x-arr[mid]>arr[mid+k]-x，则arr[mid+k]比arr[mid]作为最接近x的k个元素的左区间更接近x，left=mid+1；
     * 如果arr[mid]和x的距离小于等于arr[mid+k]和x的距离，即x-arr[mid]<=arr[mid+k]-x，则arr[mid]比arr[mid+k]作为最接近x的k个元素的左区间更接近x，right=mid
     * 时间复杂度O(log(n-k))，空间复杂度O(1)
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public List<Integer> findClosestElements4(int[] arr, int k, int x) {
        int left = 0;
        int right = arr.length - k;
        int mid;

        //判断arr[mid]和arr[mid+k]作为最接近x的k个元素的左区间，arr[mid]和arr[mid+k]距离x的大小关系
        while (left < right) {
            mid = left + ((right - left) >> 1);

            //arr[mid+k]比arr[mid]作为最接近x的k个元素的左区间更接近x，left=mid+1
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
            } else {
                //arr[mid]比arr[mid+k]作为最接近x的k个元素的左区间更接近x，right=mid
                right = mid;
            }
        }

        List<Integer> list = new ArrayList<>();

        //arr[left,left+k-1]为最接近x的k个元素
        for (int i = left; i < left + k; i++) {
            list.add(arr[i]);
        }

        return list;
    }
}
