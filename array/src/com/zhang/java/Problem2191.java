package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2025/4/15 08:04
 * @Author zsy
 * @Description 将杂乱无章的数字排序
 * 给你一个下标从 0 开始的整数数组 mapping ，它表示一个十进制数的映射规则，mapping[i] = j 表示这个规则下将数位 i 映射为数位 j 。
 * 一个整数 映射后的值 为将原数字每一个数位 i （0 <= i <= 9）映射为 mapping[i] 。
 * 另外给你一个整数数组 nums ，请你将数组 nums 中每个数按照它们映射后对应数字非递减顺序排序后返回。
 * 注意：
 * 如果两个数字映射后对应的数字大小相同，则将它们按照输入中的 相对顺序 排序。
 * nums 中的元素只有在排序的时候需要按照映射后的值进行比较，返回的值应该是输入的元素本身。
 * <p>
 * 输入：mapping = [8,9,4,0,2,1,3,5,7,6], nums = [991,338,38]
 * 输出：[338,38,991]
 * 解释：
 * 将数字 991 按照如下规则映射：
 * 1. mapping[9] = 6 ，所有数位 9 都会变成 6 。
 * 2. mapping[1] = 9 ，所有数位 1 都会变成 9 。
 * 所以，991 映射的值为 669 。
 * 338 映射为 007 ，去掉前导 0 后得到 7 。
 * 38 映射为 07 ，去掉前导 0 后得到 7 。
 * 由于 338 和 38 映射后的值相同，所以它们的前后顺序保留原数组中的相对位置关系，338 在 38 的前面。
 * 所以，排序后的数组为 [338,38,991] 。
 * <p>
 * 输入：mapping = [0,1,2,3,4,5,6,7,8,9], nums = [789,456,123]
 * 输出：[123,456,789]
 * 解释：789 映射为 789 ，456 映射为 456 ，123 映射为 123 。所以排序后数组为 [123,456,789] 。
 * <p>
 * mapping.length == 10
 * 0 <= mapping[i] <= 9
 * mapping[i] 的值 互不相同 。
 * 1 <= nums.length <= 3 * 10^4
 * 0 <= nums[i] < 10^9
 */
public class Problem2191 {
    public static void main(String[] args) {
        Problem2191 problem2191 = new Problem2191();
//        int[] mapping = {8, 9, 4, 0, 2, 1, 3, 5, 7, 6};
//        int[] nums = {991, 338, 38};
        int[] mapping = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(Arrays.toString(problem2191.sortJumbled(mapping, nums)));
    }

    /**
     * 排序+模拟
     * 时间复杂度O(nlgon)，空间复杂度O(logn) (n=nums.length)
     *
     * @param mapping
     * @param nums
     * @return
     */
    public int[] sortJumbled(int[] mapping, int[] nums) {
        int[][] arr = new int[nums.length][2];

        for (int i = 0; i < nums.length; i++) {
            arr[i][0] = getMappedNum(nums[i], mapping);
            arr[i][1] = i;
        }

        //按照arr[i][0]由小到大排序，再按照arr[i][0]由小到大排序
        heapSort(arr);

        int[] result = new int[nums.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = nums[arr[i][1]];
        }

        return result;
    }

    /**
     * 返回num通过mapping映射后的数字
     * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
     *
     * @param num
     * @param mapping
     * @return
     */
    private int getMappedNum(int num, int[] mapping) {
        //num为0的特殊情况映射
        if (num == 0) {
            return mapping[0];
        }

        int mappedNum = 0;
        //num每一位需要乘上的值
        int digit = 1;

        while (num != 0) {
            int cur = num % 10;
            mappedNum = mappedNum + mapping[cur] * digit;
            digit = digit * 10;
            num = num / 10;
        }

        return mappedNum;
    }

    private void heapSort(int[][] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int[] temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[][] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && (arr[leftIndex][0] > arr[index][0] ||
                (arr[leftIndex][0] == arr[index][0] && arr[leftIndex][1] > arr[index][1]))) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && (arr[rightIndex][0] > arr[index][0] ||
                (arr[rightIndex][0] == arr[index][0] && arr[rightIndex][1] > arr[index][1]))) {
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
