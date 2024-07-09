package com.zhang.java;

import java.util.Arrays;
import java.util.Random;

/**
 * @Date 2024/4/1 08:55
 * @Author zsy
 * @Description 摆动排序 类比Problem324、Problem376
 * 给你一个的整数数组 nums, 将该数组重新排序后使 nums[0] <= nums[1] >= nums[2] <= nums[3]...
 * 输入数组总是有一个有效的答案。
 * 进阶：你能在 O(n) 时间复杂度下解决这个问题吗？
 * <p>
 * 输入：nums = [3,5,2,1,6,4]
 * 输出：[3,5,1,6,2,4]
 * 解释：[1,6,2,5,3,4]也是有效的答案
 * <p>
 * 输入：nums = [6,6,5,6,3,8]
 * 输出：[6,6,5,6,3,8]
 * <p>
 * 1 <= nums.length <= 5 * 10^4
 * 0 <= nums[i] <= 10^4
 * 输入的 nums 保证至少有一个答案。
 */
public class Problem280 {
    public static void main(String[] args) {
        Problem280 problem280 = new Problem280();
        int[] nums = {3, 5, 2, 1, 6, 4};
        problem280.wiggleSort(nums);
        System.out.println(Arrays.toString(nums));
        //需要对原数组重新赋值
        nums = new int[]{3, 5, 2, 1, 6, 4};
        problem280.wiggleSort2(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 排序+模拟
     * nums由小到大排序，前一半元素(数组长度为奇数，则前一半元素包含中间元素)按顺序放在偶数下标索引中，后一半元素按顺序放在奇数下标索引中
     * 时间复杂度O(nlogn)，空间复杂度O(n) (快排的平均空间复杂度为O(logn)，需要O(n)的额外数组)
     *
     * @param nums
     */
    public void wiggleSort(int[] nums) {
        //nums的拷贝数组
        int[] arr = Arrays.copyOfRange(nums, 0, nums.length);

        //arr由小到大排序
        quickSort(arr, 0, arr.length - 1);

        //偶数下标索引
        int index1 = 0;
        //奇数下标索引
        int index2 = 1;

        //前一半元素(数组长度为奇数，则前一半元素包含中间元素)按顺序放在偶数下标索引中
        for (int i = 0; i < (arr.length % 2 == 0 ? arr.length / 2 : arr.length / 2 + 1); i++) {
            nums[index1] = arr[i];
            index1 = index1 + 2;
        }

        //后一半元素按顺序放在奇数下标索引中
        for (int i = (arr.length % 2 == 0 ? arr.length / 2 : arr.length / 2 + 1); i < arr.length; i++) {
            nums[index2] = arr[i];
            index2 = index2 + 2;
        }
    }

    /**
     * 模拟
     * 从下标索引1开始遍历，奇数下标索引元素小于前一个元素，则两者交换；偶数下标索引元素大于前一个元素，则两者交换
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     */
    public void wiggleSort2(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            //奇数下标索引元素小于前一个元素，则两者交换；偶数下标索引元素大于前一个元素，则两者交换
            if ((i % 2 == 1 && nums[i - 1] > nums[i]) || (i % 2 == 0 && nums[i - 1] < nums[i])) {
                int temp = nums[i - 1];
                nums[i - 1] = nums[i];
                nums[i] = temp;
            }
        }
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
