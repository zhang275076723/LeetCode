package com.zhang.java;

import java.util.Random;

/**
 * @Date 2024/4/30 08:57
 * @Author zsy
 * @Description 最小操作次数使数组元素相等 II 类比Problem453、Problem1685、Problem2448、Problem2602、Problem2607、Problem2615、Problem2967 快排划分类比Problem215、Problem324、Problem347、Problem973、Offer40
 * 给你一个长度为 n 的整数数组 nums ，返回使所有数组元素相等需要的最小操作数。
 * 在一次操作中，你可以使数组中的一个元素加 1 或者减 1 。
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：2
 * 解释：
 * 只需要两次操作（每次操作指南使一个元素加 1 或减 1）：
 * [1,2,3]  =>  [2,2,3]  =>  [2,2,2]
 * <p>
 * 输入：nums = [1,10,2,9]
 * 输出：16
 * <p>
 * n == nums.length
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */
public class Problem462 {
    public static void main(String[] args) {
        Problem462 problem462 = new Problem462();
        int[] nums = {1, 10, 2, 9};
        System.out.println(problem462.minMoves2(nums));
        System.out.println(problem462.minMoves2_2(nums));
    }

    /**
     * 排序+模拟
     * nums由小到大排序，数组中元素都变为中位数nums[nums.length/2]的操作次数即为所有数组元素相等需要的最小操作数
     * 注意：nums长度为偶数，只考虑数组中元素都变为中位数nums[nums.length/2-1]或nums[nums.length/2]
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     * <p>
     * 1、假设nums中有2n*1个元素，由小到大排序后为：... a m b ...，其中m为中位数，m左边有n个元素，m右边有n个元素，
     * 假设将m左边n个元素都变为m的操作数为x，将m右边n个元素都变为m的操作数为y，则总操作数为x+y
     * 此时，将数组中m左边n个元素都变为a的操作数为x-(m-a)n，将数组中m右边n个元素都变为a的操作数为y+(m-a)n，将m变为a的操作数为m-a，
     * 则总操作数为x-(m-a)n+y+(m-a)n+m-a=x+y+m-a，大于x+y，同理数组中元素都变为b的操作数为x+y+b-m，大于x+y，
     * 则数组长度为奇数时，中位数即为最优解
     * 2、假设nums中有2n个元素，由小到大排序后为：... a b ...，其中a、b为中位数，a左边有n-1个元素，b右边有n-1个元素，
     * 假设将a左边n-1个元素都变为a的操作数为x，将b右边n-1个元素都变为a的操作数为y，则总操作数为x+y+b-a
     * 此时，将数组中a左边n-1个元素都变为b的操作数为x+(b-a)(n-1)，将数组中b右边n-1个元素都变为b的操作数为y-(b-a)(n-1)，
     * 将a变为b的操作数为b-a，则总操作数为x+(b-a)(n-1)+y-(b-a)(n-1)+b-a=x+y+b-a，即两个中位数的总操作数相等，
     * 此时，将数组中a左边n-1个元素都变为小于a的数c的操作数为x-(a-c)(n-1)，将数组中b右边n-1个元素都变为小于a的数c的操作数为y+(a-c)(n-1)，
     * 将a变为c的操作数为a-c，将b变为c的操作数为b-c，则总操作数为x-(a-c)(n-1)+y+(a-c)(n-1)+a-c+b-c=x+y+a+b-2c，大于x+y，
     * 同理数组中元素都变为大于b的数d操作数为x+y+2d-a-b，大于x+y，
     * 则数组长度为偶数时，任意一个中位数即为最优解
     *
     * @param nums
     * @return
     */
    public int minMoves2(int[] nums) {
        //nums由小到大排序
        quickSort(nums, 0, nums.length - 1);

        int count = 0;
        //nums中位数
        int median = nums[nums.length / 2];

        //数组中元素都变为中位数value的操作次数即为所有数组元素相等需要的最小操作数
        for (int num : nums) {
            count = count + Math.abs(num - median);
        }

        return count;
    }

    /**
     * 快排划分
     * 快排划分确定数组的中位数，数组中元素都变为中位数nums[nums.length/2]的操作次数即为所有数组元素相等需要的最小操作数
     * 注意：nums长度为偶数，只考虑数组中元素都变为中位数nums[nums.length/2-1]或nums[nums.length/2]
     * 时间复杂度O(n)，空间复杂度O(logn)
     *
     * @param nums
     * @return
     */
    public int minMoves2_2(int[] nums) {
        //中位数即为数组中第nums.length/2+1大元素(最大元素为第1大元素)
        int k = nums.length / 2 + 1;
        int left = 0;
        int right = nums.length - 1;
        int pivot = partition(nums, left, right);

        //nums[pivot]不是第k大元素时，更新pivot
        while (pivot != nums.length - k) {
            if (pivot < nums.length - k) {
                left = pivot + 1;
                pivot = partition(nums, left, right);
            } else if (pivot > nums.length - k) {
                right = pivot - 1;
                pivot = partition(nums, left, right);
            }
        }

        int count = 0;
        //nums中位数
        int median = nums[pivot];

        //数组中元素都变为中位数value的操作次数即为所有数组元素相等需要的最小操作数
        for (int num : nums) {
            count = count + Math.abs(num - median);
        }

        return count;
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(nums, left, right);
        quickSort(nums, left, pivot - 1);
        quickSort(nums, pivot + 1, right);
    }

    private int partition(int[] nums, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int value = nums[left];
        nums[left] = nums[randomIndex];
        nums[randomIndex] = value;

        int temp = nums[left];

        while (left < right) {
            while (left < right && nums[right] >= temp) {
                right--;
            }

            nums[left] = nums[right];

            while (left < right && nums[left] <= temp) {
                left++;
            }

            nums[right] = nums[left];
        }

        nums[left] = temp;

        return left;
    }
}
