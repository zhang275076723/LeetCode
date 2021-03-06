package com.zhang.java;

import java.util.Arrays;
import java.util.Random;

/**
 * @Date 2022/3/25 9:04
 * @Author zsy
 * @Description 最小的k个数
 * 输入整数数组 arr ，找出其中最小的 k 个数。
 * 例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
 * 0 <= k <= arr.length <= 10000
 * 0 <= arr[i] <= 10000
 * <p>
 * 输入：arr = [3,2,1], k = 2
 * 输出：[1,2] 或者 [2,1]
 * <p>
 * 输入：arr = [0,1,2,1], k = 1
 * 输出：[0]
 * <p>
 * 0 <= k <= arr.length <= 10000
 * 0 <= arr[i] <= 10000
 */
public class Offer40 {
    public static void main(String[] args) {
        Offer40 offer40 = new Offer40();
//        int[] arr = {4, 5, 1, 6, 2, 7, 3, 8};
        int[] arr = {0, 0, 1, 2, 4, 2, 2, 3, 1, 4};
//        System.out.println(Arrays.toString(offer40.getLeastNumbers(arr, 4)));
        System.out.println(Arrays.toString(offer40.getLeastNumbers2(arr, 8)));
//        System.out.println(Arrays.toString(offer40.getLeastNumbers3(arr, 4)));
    }

    /**
     * 排序，再取前k小元素
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        if (k == 0) {
            return new int[0];
        }

        mergeSort(arr, 0, arr.length - 1, new int[arr.length]);

//        heapSort(arr);

        return Arrays.copyOfRange(arr, 0, k);
    }

    /**
     * 快排划分思想
     * 左边都小于划分值，右边都大于划分值，即一次划分可以确定数组第k小元素的值
     * 期望时间复杂度O(n)，期望空间复杂度O(logn)
     * 最坏时间复杂度O(n^2)，最坏空间复杂度O(n)
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers2(int[] arr, int k) {
        if (k == 0) {
            return new int[0];
        }

        randomizedSelectK(arr, 0, arr.length - 1, k);

        return Arrays.copyOfRange(arr, 0, k);
    }

    /**
     * 大根堆
     * 如果当前元素小于堆顶元素，说明堆顶元素不是前k小元素，使用当前元素替换堆顶元素，再整堆，保持大根堆性质
     * 时间复杂度O(nlogk)，空间复杂度O(k)
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers3(int[] arr, int k) {
        if (k == 0) {
            return new int[0];
        }

        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            result[i] = arr[i];
        }

        //建立大小为k的大根堆
        for (int i = result.length / 2 - 1; i >= 0; i--) {
            heapify(result, i, result.length);
        }

        for (int i = k; i < arr.length; i++) {
            //当前元素小于堆顶元素，说明堆顶元素不是前k小元素
            if (arr[i] < result[0]) {
                int temp = arr[i];
                arr[i] = result[0];
                result[0] = temp;

                heapify(result, 0, result.length);
            }
        }

        return result;
    }

    private void randomizedSelectK(int[] arr, int left, int right, int k) {
        int pivot = randomizedPartition(arr, left, right);

        //基准在前k小元素之后
        if (pivot > k - 1) {
            randomizedSelectK(arr, left, pivot - 1, k);
        } else if (pivot < k - 1) {
            //基准在前k小元素之前
            randomizedSelectK(arr, pivot + 1, right, k);
        }

        //pivot == k - 1，基准刚好在前k小元素，直接返回
    }

    private int randomizedPartition(int[] arr, int left, int right) {
        //随机化
        int index = new Random().nextInt(right - left + 1) + left;
//        int index = (int) (Math.random() * (right - left + 1) + left);
        int value = arr[left];
        arr[left] = arr[index];
        arr[index] = value;

        int temp = arr[left];

        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] <= temp) {
                left++;
            }
            arr[right] = arr[left];
        }

        arr[left] = temp;

        return left;
    }

    /**
     * 堆排序
     * 时间复杂度O(nlogn)，空间复杂度O(1)
     *
     * @param arr
     */
    private void heapSort(int[] arr) {
        //建堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            heapify(arr, 0, i);
        }
    }

    /**
     * 大根堆递归调整
     * 时间复杂度O(logn)
     *
     * @param arr
     * @param index
     * @param heapSize
     */
    private void heapify(int[] arr, int index, int heapSize) {
        int leftIndex = 2 * index + 1;
        int rightIndex = 2 * index + 2;
        int maxIndex = index;

        if (leftIndex < heapSize && arr[maxIndex] < arr[leftIndex]) {
            maxIndex = leftIndex;
        }
        if (rightIndex < heapSize && arr[maxIndex] > arr[rightIndex]) {
            maxIndex = rightIndex;
        }

        if (index != maxIndex) {
            int temp = arr[index];
            arr[index] = arr[maxIndex];
            arr[maxIndex] = temp;

            //继续向下整堆
            heapify(arr, maxIndex, heapSize);
        }
    }

    /**
     * 归并排序
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param arr
     * @param left
     * @param right
     * @param tempArr
     */
    private void mergeSort(int[] arr, int left, int right, int[] tempArr) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, tempArr);
            mergeSort(arr, mid + 1, right, tempArr);
            merge(arr, left, mid, right, tempArr);
        }
    }

    private void merge(int[] arr, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        //tempArr索引
        int index = left;

        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                tempArr[index] = arr[i];
                i++;
            } else {
                tempArr[index] = arr[j];
                j++;
            }
            index++;
        }

        while (i <= mid) {
            tempArr[index] = arr[i];
            i++;
            index++;
        }
        while (j <= right) {
            tempArr[index] = arr[j];
            j++;
            index++;
        }

        for (index = left; index <= right; index++) {
            arr[index] = tempArr[index];
        }
    }

}
