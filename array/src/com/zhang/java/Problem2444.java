package com.zhang.java;

/**
 * @Date 2023/9/27 08:27
 * @Author zsy
 * @Description 统计定界子数组的数目 类比Problem898、Problem1521、Problem2411、Problem2419、Problem2447、Problem2470 子序列和子数组类比
 * 给你一个整数数组 nums 和两个整数 minK 以及 maxK 。
 * nums 的定界子数组是满足下述条件的一个子数组：
 * 子数组中的 最小值 等于 minK 。
 * 子数组中的 最大值 等于 maxK 。
 * 返回定界子数组的数目。
 * 子数组是数组中的一个连续部分。
 * <p>
 * 输入：nums = [1,3,5,2,7,5], minK = 1, maxK = 5
 * 输出：2
 * 解释：定界子数组是 [1,3,5] 和 [1,3,5,2] 。
 * <p>
 * 输入：nums = [1,1,1,1], minK = 1, maxK = 1
 * 输出：10
 * 解释：nums 的每个子数组都是一个定界子数组。共有 10 个子数组。
 * <p>
 * 2 <= nums.length <= 10^5
 * 1 <= nums[i], minK, maxK <= 10^6
 */
public class Problem2444 {
    public static void main(String[] args) {
        Problem2444 problem2444 = new Problem2444();
        int[] nums = {1, 3, 5, 2, 7, 5};
        int minK = 1;
        int maxK = 5;
        System.out.println(problem2444.countSubarrays(nums, minK, maxK));
        System.out.println(problem2444.countSubarrays2(nums, minK, maxK));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @param minK
     * @param maxK
     * @return
     */
    public long countSubarrays(int[] nums, int minK, int maxK) {
        long count = 0;

        for (int i = 0; i < nums.length; i++) {
            //nums[i]-nums[j]的最小值
            int curMin = nums[i];
            //nums[i]-nums[j]的最大值
            int curMax = nums[i];

            for (int j = i; j < nums.length; j++) {
                curMin = Math.min(curMin, nums[j]);
                curMax = Math.max(curMax, nums[j]);

                if (curMin < minK || curMax > maxK) {
                    break;
                }

                if (curMin == minK && curMax == maxK) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 动态规划
     * 核心思想：以nums[i]结尾子数组往前找
     * 从后往前找以nums[i]结尾子数组中第一个minK和maxK下标索引minKIndex和maxKIndex(不包含则为-1)，
     * 从后往前找找以nums[i]结尾子数组中第一个大于maxK或小于minK的下标索引index(不包含则为-1)，
     * 则nums[index+1]-nums[i]、nums[index+2]-nums[i]...nums[min(minKIndex,maxKIndex)]-nums[i]满足最小元素等于minK，最大元素等于maxK，
     * 个数为min(minKIndex,maxKIndex)-index(始终保持min(minKIndex,maxKIndex)>=index)
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param minK
     * @param maxK
     * @return
     */
    public long countSubarrays2(int[] nums, int minK, int maxK) {
        long count = 0;
        //从后往前找以nums[i]结尾子数组中第一个minK下标索引
        int minKIndex = -1;
        //从后往前找以nums[i]结尾子数组中第一个maxK下标索引
        int maxKIndex = -1;
        //从后往前找找以nums[i]结尾子数组中第一个大于maxK或小于minK的下标索引
        int index = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == minK) {
                minKIndex = i;
            }

            if (nums[i] == maxK) {
                maxKIndex = i;
            }

            if (nums[i] < minK || nums[i] > maxK) {
                index = i;
            }

            //nums[index+1]-nums[i]、nums[index+2]-nums[i]...nums[min(minKIndex,maxKIndex)]-nums[i]满足最小元素等于minK，最大元素等于maxK，
            //个数为min(minKIndex,maxKIndex)-index(始终保持min(minKIndex,maxKIndex)>=index)
            if (Math.min(minKIndex, maxKIndex) < index) {
                minKIndex = index;
                maxKIndex = index;
            } else {
                count = count + Math.min(minKIndex, maxKIndex) - index;
            }
        }

        return count;
    }
}
