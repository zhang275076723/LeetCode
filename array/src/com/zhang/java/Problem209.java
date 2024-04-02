package com.zhang.java;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Date 2021/11/25 10:05
 * @Author zsy
 * @Description 长度最小的子数组 滑动窗口类比Problem3、Problem30、Problem76、Problem219、Problem220、Problem239、Problem340、Problem438、Problem485、Problem487、Problem567、Problem632、Problem643、Problem713、Problem1004、Problem1456、Problem1839、Problem2062、Offer48、Offer57_2、Offer59 前缀和类比Problem325、Problem327、Problem437、Problem523、Problem525、Problem560、Problem862、Problem974、Problem1171、Problem1856、Problem1871、Offer57_2 单调队列类比Problem239、Problem862、Problem1696、Offer59、Offer59_2 子序列和子数组类比Problem53、Problem115、Problem152、Problem300、Problem325、Problem392、Problem491、Problem516、Problem525、Problem560、Problem581、Problem659、Problem673、Problem674、Problem718、Problem862、Problem1143、Offer42、Offer57_2
 * 给定一个含有n个正整数的数组和一个正整数target
 * 找出该数组中满足其和 ≥ target的长度最小的连续子数组，并返回其长度。如果不存在符合条件的子数组，返回0
 * <p>
 * 输入：target = 7, nums = [2,3,1,2,4,3]
 * 输出：2
 * 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
 * <p>
 * 输入：target = 4, nums = [1,4,4]
 * 输出：1
 * <p>
 * 输入：target = 11, nums = [1,1,1,1,1,1,1,1]
 * 输出：0
 * <p>
 * 1 <= target <= 10^9
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^5
 */
public class Problem209 {
    public static void main(String[] args) {
        Problem209 problem209 = new Problem209();
        int[] nums = {2, 3, 1, 2, 4, 3};
        System.out.println(problem209.minSubArrayLen(7, nums));
        System.out.println(problem209.minSubArrayLen2(7, nums));
        System.out.println(problem209.minSubArrayLen3(7, nums));
        System.out.println(problem209.minSubArrayLen4(7, nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            //使用long，避免int溢出
            long sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum = sum + nums[j];

                if (sum >= target) {
                    minLength = Math.min(minLength, j - i + 1);
                    break;
                }
            }
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    /**
     * 滑动窗口，双指针 (如果数组中元素存在负数，则只能使用前缀和)
     * 看到连续子数组，就要想到滑动窗口和前缀和(适合有负数的情况)
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen2(int target, int[] nums) {
        int minLength = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        //使用long，避免int溢出
        long sum = 0;

        while (right < nums.length) {
            sum = sum + nums[right];

            //sum大于等于target时，nums[left]-nums[right]、nums[left]-nums[right+1]、...、
            //nums[left]-nums[nums.length-1]都大于等于target，更新长度大于等于target的最小区间，left右移
            while (sum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                sum = sum - nums[left];
                left++;
            }

            right++;
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    /**
     * 前缀和+二分查找 (数组中元素都为正数，即前缀和递增，才能二分查找)
     * 看到连续子数组，就要想到滑动窗口和前缀和(前缀和适合有负数的情况)
     * 每个preSum[i]，通过二分查找确定preSum[i]+target在preSum中的下标索引j，
     * 即得到nums[i]-nums[j-1]元素之和大于等于target
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen3(int target, int[] nums) {
        //前缀和数组，preSum[i]：nums[0]-nums[i-1]之和
        //使用long避免int溢出
        long[] preSum = new long[nums.length + 1];

        for (int i = 0; i < nums.length; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }

        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            long sum = preSum[i] + target;
            //二分查找，找到sum在preSum中的下标索引j，没有找到则返回-(sum在preSum中插入位置下标索引+1)
            int j = binarySearch(preSum, sum, 0, preSum.length - 1);

            //sum不在数组中，返回值为负数，转化为sum插入到preSum中的下标索引
            if (j < 0) {
                j = -j - 1;
            }

            //sum最多不能超过preSum[nums.length]，如果超过，则nums[i]-nums[j-1]元素之和中的nums[j-1]数组越界
            if (j <= nums.length) {
                minLength = Math.min(minLength, j - i);
            }
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    /**
     * 前缀和+单调队列
     * 单调递增队列存放前缀和数组中元素的索引下标
     * 1、当前元素preSum[i]和队首元素preSum[j]之差大于等于target，即[j+1,i]满足子数组长度大于等于target，
     * 则队首元素出队，更新子数组长度
     * 2、当前元素preSum[i]不满足单调递增队列，队尾元素出队
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen4(int target, int[] nums) {
        //前缀和数组，preSum[i]：nums[0]-nums[i-1]之和
        //使用long避免int溢出
        long[] preSum = new long[nums.length + 1];

        for (int i = 0; i < nums.length; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }

        //单调递增队列
        Deque<Integer> queue = new ArrayDeque<>();
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < preSum.length; i++) {
            //preSum[i]和队首元素preSum[j]之差大于等于target，即nums[j]-nums[i-1]元素之和大于等于target，
            //则队首元素出队，更新子数组长度
            while (!queue.isEmpty() && preSum[i] - preSum[queue.peekFirst()] >= target) {
                int j = queue.pollFirst();
                minLength = Math.min(minLength, i - j);
            }

            //preSum[i]不满足单调递增队列，队尾元素出队
            while (!queue.isEmpty() && preSum[queue.peekLast()] > preSum[i]) {
                queue.pollLast();
            }

            //preSum[i]入队
            queue.offerLast(i);
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    /**
     * 递归二分查找
     * 查找成功，返回target在数组中的索引下标；
     * 查找失败，返回-(index+1)，index为target在数组中插入位置的下标索引
     *
     * @param nums
     * @param target
     * @param left
     * @param right
     * @return
     */
    private int binarySearch(long[] nums, long target, int left, int right) {
        if (left > right) {
            return -(left + 1);
        }

        int mid = left + ((right - left) >> 1);

        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] > target) {
            return binarySearch(nums, target, left, mid - 1);
        } else {
            return binarySearch(nums, target, mid + 1, right);
        }
    }

    /**
     * 非递归二分查找
     * 查找成功，返回target在数组中的索引下标；
     * 查找失败，返回-(index+1)，index为target在数组中插入位置的下标索引
     *
     * @param nums
     * @param target
     * @return
     */
    private int binarySearch(long[] nums, long target) {
        int left = 0;
        int right = nums.length - 1;
        int mid;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -(left + 1);
    }
}
