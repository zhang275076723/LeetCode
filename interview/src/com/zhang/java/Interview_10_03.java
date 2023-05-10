package com.zhang.java;

/**
 * @Date 2023/5/8 10:52
 * @Author zsy
 * @Description 搜索旋转数组 快手面试题 汤商面试题 类比Problem33、Problem34、Problem35、Problem81、Problem153、Problem154、Problem162、Problem852、Offer11、Offer53、Offer53_2
 * 搜索旋转数组。给定一个排序后的数组，包含n个整数，但这个数组已被旋转过很多次了，次数不详。
 * 请编写代码找出数组中的某个元素，假设数组元素原先是按升序排列的。
 * 若有多个相同元素，返回索引值最小的一个。
 * <p>
 * 输入: arr = [15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14], target = 5
 * 输出: 8（元素5在该数组中的索引）
 * <p>
 * 输入：arr = [15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14], target = 11
 * 输出：-1 （没有找到）
 * <p>
 * arr 长度范围在[1, 1000000]之间
 */
public class Interview_10_03 {
    public static void main(String[] args) {
        Interview_10_03 interview_10_03 = new Interview_10_03();
        int[] arr = {14, 1, 3, 4, 5, 7, 10, 14};
        int target = 14;
        System.out.println(interview_10_03.search(arr, target));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param arr
     * @param target
     * @return
     */
    public int search(int[] arr, int target) {
        //target在arr中的最小下标索引，初始化为int最大值，表示没有找到target
        int result = Integer.MAX_VALUE;
        int left = 0;
        int right = arr.length - 1;
        int mid;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            //arr[left]和target相等，则找到等于target的最小下标索引，直接返回left
            if (arr[left] == target) {
                return left;
            }

            //arr[mid]和target相等，则找到等于target的下标索引，更新result，继续往左边找等于target的更小下标索引
            if (arr[mid] == target) {
                result = mid;
                right = mid - 1;
            } else {
                //arr[mid]-arr[right]单调递增
                if (arr[mid] < arr[right]) {
                    //target在arr[mid+1]-arr[right]之中，往右边找
                    if (arr[mid] < target && target <= arr[right]) {
                        left = mid + 1;
                    } else {
                        //target在arr[left]-arr[mid-1]之中，往左边找
                        right = mid - 1;
                    }
                } else if (arr[mid] > arr[right]) {
                    //arr[left]-arr[mid]单调递增

                    //target在arr[left]-arr[mid-1]之中，往左边找
                    if (arr[left] <= target && target < arr[mid]) {
                        right = mid - 1;
                    } else {
                        //target在arr[mid+1]-arr[right]之中，往右边找
                        left = mid + 1;
                    }
                } else {
                    //arr[mid]和arr[right]相等，去重，右指针左移
                    right--;
                }
            }
        }

        //如果没有找到target，则返回-1
        return result == Integer.MAX_VALUE ? -1 : result;
    }
}
