package com.zhang.java;

/**
 * @Date 2025/1/12 08:58
 * @Author zsy
 * @Description 是否所有 1 都至少相隔 k 个元素 类比Problem849、Problem855
 * 给你一个由若干 0 和 1 组成的数组 nums 以及整数 k。
 * 如果所有 1 都至少相隔 k 个元素，则返回 true ；否则，返回 false 。
 * <p>
 * 输入：nums = [1,0,0,0,1,0,0,1], k = 2
 * 输出：true
 * 解释：每个 1 都至少相隔 2 个元素。
 * <p>
 * 输入：nums = [1,0,0,1,0,1], k = 2
 * 输出：false
 * 解释：第二个 1 和第三个 1 之间只隔了 1 个元素。
 * <p>
 * 1 <= nums.length <= 10^5
 * 0 <= k <= nums.length
 * nums[i] 的值为 0 或 1
 */
public class Problem1437 {
    public static void main(String[] args) {
        Problem1437 problem1437 = new Problem1437();
//        int[] nums = {1, 0, 0, 0, 1, 0, 0, 1};
//        int k = 2;
        int[] nums = {1, 0, 0, 0, 1, 0, 0, 1, 0};
        int k = 2;
        System.out.println(problem1437.kLengthApart(nums, k));
    }

    /**
     * 双指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean kLengthApart(int[] nums, int k) {
        //当前为1的下标索引
        int left = 0;

        while (left < nums.length && nums[left] != 1) {
            left++;
        }

        int right = left + 1;

        while (right < nums.length) {
            while (right < nums.length && nums[right] != 1) {
                right++;
            }

            //遍历结束，直接跳出循环
            if (right == nums.length) {
                break;
            }

            //相邻的两个1间隔的元素小于k，返回false
            if (right - left <= k) {
                return false;
            }

            left = right;
            right = left + 1;
        }

        //遍历结束，相邻的两个1间隔的元素大于等于k，返回true
        return true;
    }
}
