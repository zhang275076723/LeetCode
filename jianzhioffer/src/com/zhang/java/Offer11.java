package com.zhang.java;

/**
 * @Date 2022/3/14 15:59
 * @Author zsy
 * @Description 旋转数组的最小数字 类比Problem33、Problem34、Problem35、Problem81、Problem153、Problem162、Problem852、Offer53、Offer53_2、Interview_10_03 同Problem154
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
 * 给你一个可能存在重复元素值的数组numbers，它原来是一个升序排列的数组，并按上述情形进行了一次旋转。
 * 请返回旋转数组的最小元素。例如，数组[3,4,5,1,2] 为 [1,2,3,4,5] 的一次旋转，该数组的最小值为1。
 * <p>
 * 输入：[3,4,5,1,2]
 * 输出：1
 * <p>
 * 输入：[2,2,2,0,1]
 * 输出：0
 * <p>
 * n == numbers.length
 * 1 <= n <= 5000
 * -5000 <= numbers[i] <= 5000
 * numbers 原来是一个升序排序的数组，并进行了 1 至 n 次旋转
 */
public class Offer11 {
    public static void main(String[] args) {
        Offer11 offer11 = new Offer11();
        int[] numbers = {2, 2, 3, 4, 4, 5, 6, 7, 8, 9, 1, 2};
        System.out.println(offer11.minArray(numbers));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度为O(logn)，空间复杂度O(1)
     *
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {
        if (numbers.length == 1) {
            return numbers[0];
        }

        int left = 0;
        int right = numbers.length - 1;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //numbers[mid]和numbers[right]相等，右指针左移
            if (numbers[mid] == numbers[right]) {
                right--;
            } else if (numbers[mid] < numbers[right]) {
                ///nums[mid]-nums[right]单调递增，最小值在nums[left]-nums[mid]
                right = mid;
            } else {
                //nums[left]-nums[mid]单调递增，最小值在nums[mid+1]-nums[right]
                left = mid + 1;
            }
        }

        return numbers[left];
    }
}
