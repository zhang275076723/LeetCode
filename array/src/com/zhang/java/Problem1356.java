package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/7/1 08:55
 * @Author zsy
 * @Description 根据数字二进制下 1 的数目排序 位运算类比
 * 给你一个整数数组 arr 。请你将数组中的元素按照其二进制表示中数字 1 的数目升序排序。
 * 如果存在多个数字二进制中 1 的数目相同，则必须将它们按照数值大小升序排列。
 * 请你返回排序后的数组。
 * <p>
 * 输入：arr = [0,1,2,3,4,5,6,7,8]
 * 输出：[0,1,2,4,8,3,5,6,7]
 * 解释：[0] 是唯一一个有 0 个 1 的数。
 * [1,2,4,8] 都有 1 个 1 。
 * [3,5,6] 有 2 个 1 。
 * [7] 有 3 个 1 。
 * 按照 1 的个数排序得到的结果数组为 [0,1,2,4,8,3,5,6,7]
 * <p>
 * 输入：arr = [1024,512,256,128,64,32,16,8,4,2,1]
 * 输出：[1,2,4,8,16,32,64,128,256,512,1024]
 * 解释：数组中所有整数二进制下都只有 1 个 1 ，所以你需要按照数值大小将它们排序。
 * <p>
 * 输入：arr = [10000,10000]
 * 输出：[10000,10000]
 * <p>
 * 输入：arr = [2,3,5,7,11,13,17,19]
 * 输出：[2,3,5,17,7,11,13,19]
 * <p>
 * 输入：arr = [10,100,1000,10000]
 * 输出：[10,100,10000,1000]
 * <p>
 * 1 <= arr.length <= 500
 * 0 <= arr[i] <= 10^4
 */
public class Problem1356 {
    public static void main(String[] args) {
        Problem1356 problem1356 = new Problem1356();
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(Arrays.toString(problem1356.sortByBits(arr)));
    }

    /**
     * 位运算
     * 时间复杂度O(nlogC+nlogn)=O(nlogn)，空间复杂度O(n) (C=10^4)
     *
     * @param arr
     * @return
     */
    public int[] sortByBits(int[] arr) {
        //arr2[i][0]：arr中元素，arr2[i][1]：当前元素的二进制中1的个数
        int[][] arr2 = new int[arr.length][2];

        for (int i = 0; i < arr.length; i++) {
            arr2[i] = new int[]{arr[i], bitCount(arr[i])};
        }

        //先按照arr2[i][1]由小到大排序，在按照arr2[i][0]由小到大排序
        mergeSort(arr2, 0, arr2.length - 1, new int[arr2.length][2]);

        int[] result = new int[arr2.length];

        for (int i = 0; i < arr2.length; i++) {
            result[i] = arr2[i][0];
        }

        return result;
    }

    private int bitCount(int n) {
        int count = 0;

        while (n != 0) {
            count = count + (n & 1);
            n = n >>> 1;
        }

        return count;
    }

    private void mergeSort(int[][] arr, int left, int right, int[][] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);
        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
    }

    private void merge(int[][] arr, int left, int mid, int right, int[][] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if ((arr[i][1] < arr[j][1]) || (arr[i][1] == arr[j][1] && arr[i][0] < arr[j][0])) {
                tempArr[k] = arr[i];
                i++;
                k++;
            } else {
                tempArr[k] = arr[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = arr[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            arr[k] = tempArr[k];
        }
    }
}
