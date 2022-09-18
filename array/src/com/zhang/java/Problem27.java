package com.zhang.java;


/**
 * @Date 2021/11/24 19:15
 * @Author zsy
 * @Description 移除元素 类比Problem26、Problem75、Problem283
 * 给定一个数组nums和一个值val，你需要原地移除所有数值等于val的元素，并返回移除后数组的新长度
 * 不使用额外的数组空间，你必须仅使用O(1)额外空间并原地修改输入数组
 * 元素的顺序可以改变，不需要考虑数组中超出新长度后面的元素
 * <p>
 * 输入：nums = [3,2,2,3], val = 3
 * 输出：2, nums = [2,2]
 * 解释：函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
 * 你不需要考虑数组中超出新长度后面的元素。
 * 例如，函数返回的新长度为 2 ，而 nums = [2,2,3,3] 或 nums = [2,2,0,0]，也会被视作正确答案。
 * <p>
 * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
 * 输出：5, nums = [0,1,4,0,3]
 * 解释：函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。
 * 注意这五个元素可为任意顺序。
 * 你不需要考虑数组中超出新长度后面的元素。
 * <p>
 * 0 <= nums.length <= 100
 * 0 <= nums[i] <= 50
 * 0 <= val <= 100
 */
public class Problem27 {
    public static void main(String[] args) {
        Problem27 problem27 = new Problem27();
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
//        System.out.println(problem27.removeElement(nums, 2));
        System.out.println(problem27.removeElement2(nums, 2));
    }

    /**
     * 双指针
     * 第一个指针指向当前遍历的数组下标索引，第二个指针指向当前要插入的下标索引
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //nums[i]要插入的下标索引
        int index = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val){
                nums[index] = nums[i];
                index++;
            }
        }

        return index;
    }

    /**
     * 双指针
     * 从左边找第一个值等于val的元素，从右边找第一个值不等于val的元素，将右边元素赋值给左边元素
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement2(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            //从左边开始找第一个值等于val的元素
            while (left <= right && nums[left] != val) {
                left++;
            }

            //从右边开始找第一个值不等于val的元素
            while (left <= right && nums[right] == val) {
                right--;
            }

            if (left <= right) {
                //将右边不等于val的元素赋值给左边等于val的元素
                nums[left] = nums[right];
                left++;
                right--;
            }
        }

        return left;
    }
}
