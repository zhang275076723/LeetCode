package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/4/25 8:44
 * @Author zsy
 * @Description 颜色分类 类比Problem26、Problem27、Problem80、Problem283
 * 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，
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
        problem75.sortColors2(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 两次遍历
     * 第一次遍历把0放到前面，第二次遍历把1放到0之后
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
     * 一次遍历，三指针
     * 第一个指针i指向要交换的0索引下标，第二个指针j指向要交换的2索引下标，第三个指针k指向当前元素索引下标
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     */
    public void sortColors2(int[] nums) {
        if (nums.length == 1) {
            return;
        }

        //i指向要交换的0索引下标
        int i = 0;
        //j指向要交换的2索引下标
        int j = nums.length - 1;
        //k指向当前元素索引下标
        int k;

        //起始元素为0，i右移
        while (i <= j && nums[i] == 0) {
            i++;
        }

        //末尾元素为2，j左移
        while (i <= j && nums[j] == 2) {
            j--;
        }

        k = i;

        while (i <= k && k <= j) {
            //当前元素为0时，nums[i]和nums[k]交换，指针i、k右移
            if (nums[k] == 0) {
                swap(nums, k, i);
                i++;
                k++;
            } else if (nums[k] == 2) {
                //当前元素为2时，num[j]和nums[k]交换，指针j左移
                swap(nums, k, j);
                j--;
            } else {
                //当前元素为1时，k指针右移
                k++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
