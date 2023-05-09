package com.zhang.java;

/**
 * @Date 2022/9/30 08:12
 * @Author zsy
 * @Description 山脉数组的峰顶索引 类比Problem33、Problem34、Problem35、Problem81、Problem153、Problem154、Problem162、Offer11、Offer53、Offer53_2、Interview_10_03 同AscendAndDescendArrayFindMaxIndex
 * 符合下列属性的数组 arr 称为 山脉数组 ：
 * arr.length >= 3
 * 存在 i（0 < i < arr.length - 1）使得：
 * arr[0] < arr[1] < ... arr[i-1] < arr[i]
 * arr[i] > arr[i+1] > ... > arr[arr.length - 1]
 * 给你由整数组成的山脉数组 arr ，返回任何满足
 * arr[0] < arr[1] < ... arr[i - 1] < arr[i] > arr[i + 1] > ... > arr[arr.length - 1] 的下标 i 。
 * <p>
 * 输入：arr = [0,1,0]
 * 输出：1
 * <p>
 * 输入：arr = [0,2,1,0]
 * 输出：1
 * <p>
 * 输入：arr = [0,10,5,2]
 * 输出：1
 * <p>
 * 输入：arr = [3,4,5,1]
 * 输出：2
 * <p>
 * 输入：arr = [24,69,100,99,79,78,67,36,26,19]
 * 输出：2
 * <p>
 * 3 <= arr.length <= 10^4
 * 0 <= arr[i] <= 10^6
 * 题目数据保证 arr 是一个山脉数组
 */
public class Problem852 {
    public static void main(String[] args) {
        Problem852 problem852 = new Problem852();
        int[] arr = {3, 5, 3, 2, 0};
        System.out.println(problem852.peakIndexInMountainArray(arr));
        System.out.println(problem852.peakIndexInMountainArray2(arr));
    }

    /**
     * 顺序遍历
     * 当前元素大于下一个元素时，即为峰值
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param arr
     * @return
     */
    public int peakIndexInMountainArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            //当前元素大于下一个元素时，即为峰值
            if (arr[i] > arr[i + 1]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param arr
     * @return
     */
    public int peakIndexInMountainArray2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int left = 0;
        int right = arr.length - 1;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //arr[mid]小于arr[mid+1]，峰值在nums[mid+1]-nums[right]
            if (arr[mid] < arr[mid + 1]) {
                left = mid + 1;
            } else {
                //arr[mid]大于arr[mid+1]，峰值在nums[left]-nums[mid]
                right = mid;
            }
        }

        return left;
    }
}
