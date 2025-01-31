package com.zhang.java;

import java.util.Arrays;
import java.util.Random;

/**
 * @Date 2025/3/21 08:31
 * @Author zsy
 * @Description 咒语和药水的成功对数
 * 给你两个正整数数组 spells 和 potions ，长度分别为 n 和 m ，
 * 其中 spells[i] 表示第 i 个咒语的能量强度，potions[j] 表示第 j 瓶药水的能量强度。
 * 同时给你一个整数 success 。一个咒语和药水的能量强度 相乘 如果 大于等于 success ，那么它们视为一对 成功 的组合。
 * 请你返回一个长度为 n 的整数数组 pairs，其中 pairs[i] 是能跟第 i 个咒语成功组合的 药水 数目。
 * <p>
 * 输入：spells = [5,1,3], potions = [1,2,3,4,5], success = 7
 * 输出：[4,0,3]
 * 解释：
 * - 第 0 个咒语：5 * [1,2,3,4,5] = [5,10,15,20,25] 。总共 4 个成功组合。
 * - 第 1 个咒语：1 * [1,2,3,4,5] = [1,2,3,4,5] 。总共 0 个成功组合。
 * - 第 2 个咒语：3 * [1,2,3,4,5] = [3,6,9,12,15] 。总共 3 个成功组合。
 * 所以返回 [4,0,3] 。
 * <p>
 * 输入：spells = [3,1,2], potions = [8,5,8], success = 16
 * 输出：[2,0,2]
 * 解释：
 * - 第 0 个咒语：3 * [8,5,8] = [24,15,24] 。总共 2 个成功组合。
 * - 第 1 个咒语：1 * [8,5,8] = [8,5,8] 。总共 0 个成功组合。
 * - 第 2 个咒语：2 * [8,5,8] = [16,10,16] 。总共 2 个成功组合。
 * 所以返回 [2,0,2] 。
 * <p>
 * n == spells.length
 * m == potions.length
 * 1 <= n, m <= 10^5
 * 1 <= spells[i], potions[i] <= 10^5
 * 1 <= success <= 10^10
 */
public class Problem2300 {
    public static void main(String[] args) {
        Problem2300 problem2300 = new Problem2300();
        int[] spells = {5, 1, 3};
        int[] potions = {1, 2, 3, 4, 5};
        long success = 7;
        System.out.println(Arrays.toString(problem2300.successfulPairs(spells, potions, success)));
    }

    /**
     * 排序+二分查找
     * 时间复杂度O(mlogm+nlogm)，空间复杂度O(logm) (n=spells.length，m=potions.length)
     *
     * @param spells
     * @param potions
     * @param success
     * @return
     */
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        //potions由小到大排序
        Arrays.sort(potions);
        //注意：自己写的排序超时
//        quickSort(potions, 0, potions.length - 1);

        int[] result = new int[spells.length];

        for (int i = 0; i < spells.length; i++) {
            int left = 0;
            int right = potions.length;
            int mid;

            //二分查找potions由小到大排序后中第一个和spells[i]相乘大于等于success的potions[left]
            while (left < right) {
                mid = left + ((right - left) >> 1);

                if ((long) spells[i] * potions[mid] < success) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            //potions[left]-potions[potions.length-1]共potions.length-left个potions和spells[i]相乘大于等于success
            result[i] = potions.length - left;
        }

        return result;
    }

    private void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    private int partition(int[] arr, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int value = arr[left];
        arr[left] = arr[randomIndex];
        arr[randomIndex] = value;

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
}
