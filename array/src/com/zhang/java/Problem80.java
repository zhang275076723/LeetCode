package com.zhang.java;

/**
 * @Date 2022/11/10 09:14
 * @Author zsy
 * @Description 删除有序数组中的重复项 II 类比Problem26、Problem27、Problem75、Problem283
 * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * 输入：nums = [1,1,1,2,2,3]
 * 输出：5, nums = [1,1,2,2,3]
 * 解释：函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。 不需要考虑数组中超出新长度后面的元素。
 * <p>
 * 输入：nums = [0,0,1,1,1,1,2,3,3]
 * 输出：7, nums = [0,0,1,1,2,3,3]
 * 解释：函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为0, 0, 1, 1, 2, 3, 3 。 不需要考虑数组中超出新长度后面的元素。
 * <p>
 * 1 <= nums.length <= 3 * 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums 已按升序排列
 */
public class Problem80 {
    public static void main(String[] args) {
        Problem80 problem80 = new Problem80();
        int[] nums = {0, 0, 1, 1, 1, 1, 2, 3, 3};
        System.out.println(problem80.removeDuplicates(nums));
    }

    /**
     * 双指针
     * 第一个指针指向当前遍历的数组下标索引，第二个指针指向当前要插入的下标索引
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return 1;
        }

        int index = 1;
        //与nums[i]相同元素的个数
        int count = 1;
        //遍历到nums[i]的前一个不同元素
        int num = nums[0];

        for (int i = 1; i < nums.length; i++) {
            //当前元素和前一个元素不相同
            if (num != nums[i]) {
                nums[index] = nums[i];
                num = nums[i];
                count = 1;
                index++;
            } else if (num == nums[i] && count < 2) {
                //当前元素和前一个元素相同，且相同次数不超过2次
                nums[index] = nums[i];
                count++;
                index++;
            }
        }

        return index;
    }
}
