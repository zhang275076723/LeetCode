package com.zhang.java;

/**
 * @Date 2022/9/8 11:34
 * @Author zsy
 * @Description 分割数组的最大值 美团机试题 二分查找类比Problem4、Problem287、Problem378、problem658、Problem1482、FindMaxArrayMinAfterKMinus
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
//        int[] nums = {7, 2, 5, 10, 8};
//        int m = 2;
        int[] nums = {1, 4, 4};
        int m = 3;
        System.out.println(problem410.splitArray(nums, m));
    }

    /**
     * 二分查找变形，使...最大值尽可能小，就要想到二分查找
     * 对[left,right]进行二分查找，left为数组中最大值，right为数组元素之和，统计数组中子数组元素之和小于等于mid的元素个数count，
     * 如果count大于k，则最小的子数组元素之和在mid右边，left=mid+1；
     * 如果count小于等于k，则最小的子数组元素之和在mid或mid左边，right=mid
     * 时间复杂度O(n*log(right-left))，空间复杂度O(1) (n:nums长度，left:nums中最大值，right:nums元素之和)
     *
     * @param nums
     * @param k
     * @return
     */
    public int splitArray(int[] nums, int k) {
        //数组中最大值作为左边界
        int left = nums[0];
        //数组之和作为右边界
        int right = 0;
        int mid;

        for (int num : nums) {
            left = Math.max(left, num);
            right = right + num;
        }

        //二分查找当前子数组之和mid
        while (left < right) {
            mid = left + ((right - left) >> 1);

            //子数组元素之和小于等于mid的数量
            int count = 0;
            int sum = 0;

            for (int num : nums) {
                sum = sum + num;

                if (sum > mid) {
                    count++;
                    sum = num;
                }
            }

            //最后一个数组也要统计上
            count++;

            if (count > k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
