package com.zhang.java;

/**
 * @Date 2022/9/2 11:29
 * @Author zsy
 * @Description 删除有序数组中的重复项 类比Problem27、Problem75、Problem80、Problem283
 * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
 * 元素的 相对顺序 应该保持 一致 。
 * 由于在某些语言中不能改变数组的长度，所以必须将结果放在数组nums的第一部分。
 * 更规范地说，如果在删除重复项之后有 k 个元素，那么 nums 的前 k 个元素应该保存最终结果。
 * 将最终结果插入 nums 的前 k 个位置后返回 k 。
 * 不要使用额外的空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * 输入：nums = [1,1,2]
 * 输出：2, nums = [1,2,_]
 * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。
 * 不需要考虑数组中超出新长度后面的元素。
 * <p>
 * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
 * 输出：5, nums = [0,1,2,3,4]
 * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。
 * 不需要考虑数组中超出新长度后面的元素。
 * <p>
 * 1 <= nums.length <= 3 * 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums 已按 升序 排列
 */
public class Problem26 {
    public static void main(String[] args) {
        Problem26 problem26 = new Problem26();
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println(problem26.removeDuplicates(nums));
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

        if (nums.length <= 1) {
            return nums.length;
        }

        //慢指针，指向当前要插入的下标索引，用于判断nums[slow-1]和nums[fast]是否相等，即nums[fast]能否赋值到nums[slow]
        int slow = 1;

        for (int fast = 1; fast < nums.length; fast++) {
            //nums[slow−1]和nums[fast]不相等，nums[slow]赋值为nums[fast]，slow指针右移
            if (nums[slow - 1] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }
}
