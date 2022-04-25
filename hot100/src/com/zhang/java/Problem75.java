package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/4/25 8:44
 * @Author zsy
 * @Description 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，
 * 原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 * 必须在不使用库的sort函数的情况下解决这个问题。
 * 仅使用常数空间的一趟扫描算法。
 * <p>
 * 输入：nums = [2,0,2,1,1,0]
 * 输出：[0,0,1,1,2,2]
 * <p>
 * 输入：nums = [2,0,1]
 * 输出：[0,1,2]
 * <p>
 * n == nums.length
 * 1 <= n <= 300
 * nums[i] 为 0、1 或 2
 */
public class Problem75 {
    public static void main(String[] args) {
        Problem75 problem75 = new Problem75();
        int[] nums = {2, 0, 2, 1, 1, 0};
//        problem75.sortColors(nums);
//        problem75.sortColors2(nums);
        problem75.sortColors3(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 两趟遍历，第一次遍历把0放到前面，第二次遍历把1放到0之后
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        if (nums.length == 1) {
            return;
        }

        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                swap(nums, i, index);
                index++;
            }
        }
        for (int i = index; i < nums.length; i++) {
            if (nums[i] == 1) {
                swap(nums, i, index);
                index++;
            }
        }
    }

    /**
     * 一趟遍历，双指针，index1指向要交换的0索引，index2指向要交换的1的索引
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     */
    public void sortColors2(int[] nums) {
        if (nums.length < 2) {
            return;
        }

        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < nums.length; i++) {
            //nums[i]为0的情况
            if (nums[i] == 0) {
                swap(nums, i, index1);
                //nums[i]为0时，index1和i交换之后，如果原来index1指向的元素是1，即nums[i]变为1，则需要把1再交换回来
                if (nums[i] == 1) {
                    swap(nums, i, index2);
                }
                index1++;
                index2++;
            } else if (nums[i] == 1) {
                //nums[i]为1的情况
                swap(nums, i, index2);
                index2++;
            }
        }
    }

    /**
     * 一趟遍历，双指针，index1指向要交换的0索引，index2指向要交换的2的索引
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     */
    public void sortColors3(int[] nums) {
        if (nums.length < 2) {
            return;
        }

        int index1 = 0;
        int index2 = nums.length - 1;
        for (int i = 0; i <= index2; i++) {
            //nums[i]为2的情况
            while (i <= index2 && nums[i] == 2) {
                swap(nums, i, index2);
                index2--;
            }
            //nums[i]为0的情况
            if (nums[i] == 0) {
                swap(nums, i, index1);
                index1++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
