package com.zhang.java;

/**
 * @Date 2022/9/8 11:34
 * @Author zsy
 * @Description 分割数组的最大值 美团机试题 二分查找类比Problem4、Problem287、Problem373、Problem378、Problem441、Problem644、Problem658、Problem668、Problem719、Problem786、Problem878、Problem1201、Problem1482、Problem1508、Problem1723、Problem2305、Problem2498、CutWood、FindMaxArrayMinAfterKMinus
 * 给定一个非负整数数组 nums 和一个整数 m ，你需要将这个数组分成 m 个非空的连续子数组。
 * 设计一个算法使得这 m 个子数组各自和的最大值最小。
 * 输入：nums = [7,2,5,10,8], m = 2
 * 输出：18
 * 解释：
 * 一共有四种方法将 nums 分割为 2 个子数组。
 * 其中最好的方式是将其分为 [7,2,5] 和 [10,8] 。
 * 因为此时这两个子数组各自的和的最大值为18，在所有情况中最小。
 * <p>
 * 输入：nums = [1,2,3,4,5], m = 2
 * 输出：9
 * <p>
 * 输入：nums = [1,4,4], m = 3
 * 输出：4
 * <p>
 * 1 <= nums.length <= 1000
 * 0 <= nums[i] <= 10^6
 * 1 <= m <= min(50, nums.length)
 */
public class Problem410 {
    public static void main(String[] args) {
        Problem410 problem410 = new Problem410();
        int[] nums = {7, 2, 5, 10, 8};
        int m = 2;
//        int[] nums = {1, 4, 4};
//        int m = 3;
        System.out.println(problem410.splitArray(nums, m));
    }

    /**
     * 二分查找变形，使...最大值尽可能小，就要想到二分查找
     * 对[left,right]进行二分查找，left为数组中最大值，right为数组元素之和，统计数组中分割的子数组元素之和小于等于mid的个数count，
     * 如果count大于m，则最大的子数组元素之和的最小值在mid右边，left=mid+1；
     * 如果count小于等于m，则最大的子数组元素之和的最小值在mid或mid左边，right=mid
     * 时间复杂度O(n*log(sum(nums[i])-max(nums[i])))=O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param m
     * @return
     */
    public int splitArray(int[] nums, int m) {
        int max = nums[0];
        int sum = 0;

        for (int num : nums) {
            max = Math.max(max, num);
            sum = sum + num;
        }

        int left = max;
        int right = sum;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //数组中分割的子数组元素之和小于等于mid的个数
            int count = 0;
            //当前子数组元素之和
            int curSum = 0;

            for (int num : nums) {
                curSum = curSum + num;

                if (curSum > mid) {
                    count++;
                    curSum = num;
                }
            }

            //统计最后一个子数组
            count++;

            if (count > m) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
