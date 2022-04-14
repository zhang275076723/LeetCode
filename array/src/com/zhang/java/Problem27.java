package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2021/11/24 19:15
 * @Author zsy
 * @Description 给定一个数组nums和一个值val，你需要原地移除所有数值等于val的元素，并返回移除后数组的新长度
 * 不使用额外的数组空间，你必须仅使用O(1)额外空间并原地修改输入数组
 * 元素的顺序可以改变，不需要考虑数组中超出新长度后面的元素
 * <p>
 * 输入：nums = [3,2,2,3], val = 3
 * 输出：2, nums = [2,2]
 * 解释：函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。你不需要考虑数组中超出新长度后面的元素。例如，函数返回的新长度为 2 ，而 nums = [2,2,3,3] 或 nums = [2,2,0,0]，也会被视作正确答案。
 * <p>
 * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
 * 输出：5, nums = [0,1,4,0,3]
 * 解释：函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。注意这五个元素可为任意顺序。你不需要考虑数组中超出新长度后面的元素。
 */
public class Problem27 {
    public static void main(String[] args) {
        Problem27 p = new Problem27();
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        int length = p.removeElement(nums, 2);
        for (int i = 0; i < length; i++) {
            System.out.print(nums[i] + " ");
        }

        System.out.println();

        nums = Arrays.copyOf(new int[]{0, 1, 2, 2, 3, 0, 4, 2}, nums.length);
        length = p.removeElement2(nums, 2);
        for (int i = 0; i < length; i++) {
            System.out.print(nums[i] + " ");
        }
    }

    /**
     * 双指针
     * 时间复杂度T(n) = O(n)，空间复杂度T(n) = O(1)
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[index++] = nums[i];
            }
        }
        return index;
    }

    /**
     * 自己的解，也是双指针，从左边找第一个值等于val的元素，从右开始找第一个值不等于val的元素，将右边元素赋值给左边元素
     * 时间复杂度T(n) = O(n)，空间复杂度T(n) = O(1)
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement2(int[] nums, int val) {
        int i = 0;
        int j = nums.length - 1;
        while (i < j) {
            //从右开始找第一个值不等于val的元素
            if (nums[j] == val) {
                j--;
                continue;
            }
            //从左开始找第一个值等于val的元素
            if (nums[i] != val) {
                i++;
                continue;
            }
            //将右边不等于val的元素赋值给左边等于val的元素
            nums[i] = nums[j];
            i++;
            j--;
        }

        //考虑边界情况
        if (i == j) {
            if (nums[i] == val) {
                return i;
            } else {
                return i + 1;
            }
        } else {
            return i;
        }
    }
}
