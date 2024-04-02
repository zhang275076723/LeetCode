package com.zhang.java;

/**
 * @Date 2023/8/30 08:38
 * @Author zsy
 * @Description 乘积小于 K 的子数组 京东面试题 滑动窗口类比Problem3、Problem30、Problem76、Problem209、Problem219、Problem220、Problem239、Problem340、Problem438、Problem485、Problem487、Problem567、Problem632、Problem643、Problem1004、Problem1456、Problem1839、Problem2062、Offer48、Offer57_2、Offer59 子序列和子数组类比
 * 给你一个整数数组 nums 和一个整数 k ，请你返回子数组内所有元素的乘积严格小于 k 的连续子数组的数目。
 * <p>
 * 输入：nums = [10,5,2,6], k = 100
 * 输出：8
 * 解释：8 个乘积小于 100 的子数组分别为：[10]、[5]、[2]、[6]、[10,5]、[5,2]、[2,6]、[5,2,6]。
 * 需要注意的是 [10,5,2] 并不是乘积小于 100 的子数组。
 * <p>
 * 输入：nums = [1,2,3], k = 0
 * 输出：0
 * <p>
 * 1 <= nums.length <= 3 * 10^4
 * 1 <= nums[i] <= 1000
 * 0 <= k <= 10^6
 */
public class Problem713 {
    public static void main(String[] args) {
        Problem713 problem713 = new Problem713();
//        int[] nums = {10, 5, 2, 6};
//        int k = 100;
        int[] nums = {1, 1, 1};
        int k = 1;
        System.out.println(problem713.numSubarrayProductLessThanK(nums, k));
        System.out.println(problem713.numSubarrayProductLessThanK2(nums, k));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            //nums[i]-nums[j]乘积
            int product = 1;

            for (int j = i; j < nums.length; j++) {
                product = product * nums[j];

                if (product < k) {
                    count++;
                } else {
                    //因为nums数组中元素都大于0，当乘积product大于k时，之后再乘nums[j]都大于k，直接跳出循环
                    break;
                }
            }
        }

        return count;
    }

    /**
     * 滑动窗口，双指针
     * nums[left]-nums[right]乘积小于k，则以nums[right]为右边界的数组乘积都小于k，
     * 即nums[left]-nums[right]、nums[left+1]-nums[right]、...、nums[right]-nums[right]的乘积都小于k，共right-left+1个；
     * nums[left]-nums[right]乘积大于k，则以nums[left]为左边界的数组乘积都大于k，
     * 即nums[left]-nums[right]、nums[left]-nums[right+1]、...、nums[right]-nums[nums.length-1]的乘积都大于k，共nums.length-right个；
     * 注意：滑动窗口只适用于nums数组中元素都为正数的情况，如果存在负数，就要考虑前缀和、前缀积
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int numSubarrayProductLessThanK2(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return 0;
        }

        int count = 0;
        int left = 0;
        int right = 0;
        //nums[left]-nums[right]乘积
        int product = 1;

        while (right < nums.length) {
            product = product * nums[right];

            //nums[left]-nums[right]乘积大于等于k，则left右移，直至nums[left]-nums[right]乘积小于k，
            //即nums[left]-nums[right]、nums[left]-nums[right+1]、...、nums[right]-nums[nums.length-1]的乘积都大于k
            while (left <= right && product >= k) {
                product = product / nums[left];
                left++;
            }

            //nums[left]-nums[right]乘积小于k，则以nums[right]为右边界的数组乘积都小于k，
            //即nums[left]-nums[right]、nums[left+1]-nums[right]、...、nums[right]-nums[right]的乘积都小于k，
            //共right-left+1个
            count = count + (right - left + 1);
            //right右移
            right++;
        }

        return count;
    }
}
