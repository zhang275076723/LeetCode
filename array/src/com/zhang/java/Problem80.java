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
     * fast指针指向遍历数组的下标索引，slow指针指向当前要插入的下标索引
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        }

        if (nums.length <= 2) {
            return nums.length;
        }

        //慢指针，指向当前要插入的下标索引，用于判断nums[slow-2]和nums[fast]是否相等，即nums[fast]能否赋值到nums[slow]
        int slow = 2;

        for (int fast = 2; fast < nums.length; fast++) {
            //nums[slow−2]和nums[fast]不相等，即nums[slow−2]、nums[slow−1]、nums[fast]相等元素出现次数小于2次，
            //nums[slow]赋值为nums[fast]，slow指针右移
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }
}
