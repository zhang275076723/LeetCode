package com.zhang.java;

/**
 * @Date 2023/8/31 08:30
 * @Author zsy
 * @Description 子数组最大平均数 II 类比Problem643 类比Problem774 类比Problem786 类比Problem373、Problem378、Problem668、Problem719、Problem786、Problem1439、Problem1508、Problem2040、Problem2386 二分查找类比 前缀和类比
 * 给你一个包含 n 个整数的数组 nums，和一个整数 k 。
 * 请你找出长度 大于等于 k 且含最大平均值的连续子数组。并输出这个最大平均值。
 * 任何计算误差小于 10^-5 的结果都将被视为正确答案。
 * <p>
 * 输入：nums = [1,12,-5,-6,50,3], k = 4
 * 输出：12.75
 * 说明：
 * - 当长度为 4 的时候，连续子数组平均值分别为 [0.5, 12.75, 10.5] ，其中最大平均值是12.75 。
 * - 当长度为 5 的时候，连续子数组平均值分别为 [10.4, 10.8] ，其中最大平均值是10.8 。
 * - 当长度为 6 的时候，连续子数组平均值分别为 [9.16667] ，其中最大平均值是9.16667 。
 * 当取长度为 4 的子数组 (即，子数组[12, -5, -6, 50]) 的时候，可以得到最大的连续子数组平均值 12.75 ，所以返回 12.75 。
 * 根据题目要求，无需考虑长度小于 4 的子数组。
 * <p>
 * 输入：nums = [1,12,-5,-6,50,3,90,80,-100], k = 4
 * 输出：55.75
 * <p>
 * 输入：nums = [5], k = 1
 * 输出：5.00000
 * <p>
 * n == nums.length
 * 1 <= k <= n <= 10^4
 * -10^4 <= nums[i] <= 10^4
 */
public class Problem644 {
    public static void main(String[] args) {
        Problem644 problem644 = new Problem644();
        int[] nums = {1, 12, -5, -6, 50, 3};
        int k = 4;
        System.out.println(problem644.findMaxAverage(nums, k));
        System.out.println(problem644.findMaxAverage2(nums, k));
    }

    /**
     * 暴力+前缀和
     * 时间复杂度O(n(n-k))，空间复杂度O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public double findMaxAverage(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] preSum = new int[nums.length + 1];

        for (int i = 1; i <= nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        //长度大于等于k的子数组之和的最大平均值
        //初始化为第一个长度为k的子数组之和的平均值
        double maxAvg = (double) (preSum[k] - preSum[0]) / k;

        //当前子数组的长度i
        for (int i = k; i <= nums.length; i++) {
            //长度为i的子数组之和的最大值
            //初始化为第一个长度为i的子数组之和
            int sum = preSum[i] - preSum[0];

            for (int j = 0; j <= nums.length - i; j++) {
                sum = Math.max(sum, preSum[j + i] - preSum[j]);
            }

            maxAvg = Math.max(maxAvg, (double) sum / i);
        }

        return maxAvg;
    }

    /**
     * 二分查找+前缀和
     * 对[left,right]进行二分查找，left为nums最小值，right为nums最大值，判断nums中是否存在长度大于等于k，平均值大于等于mid的子数组，
     * 如果存在，则nums中长度大于等于k的最大平均值在mid或mid右边，left=mid；
     * 如果不存在，则nums中长度大于等于k的最大平均值在mid左边，right=mid
     * 注意：mid为double类型，所以二分时，right不能赋值为mid-1，right只能赋值为mid
     * 时间复杂度O(n*log(max(nums[i])-min(nums[i])))=O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public double findMaxAverage2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int max = nums[0];
        int min = nums[0];

        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }

        double left = min;
        double right = max;
        double mid;

        //误差小于10^-5，则认为找到了nums中长度大于等于k的最大平均值
        while (right - left >= 1e-5) {
            //注意：double类型不能使用>>来除以2
            mid = left + ((right - left) / 2);

            //nums中存在长度大于等于k，平均值大于等于mid的子数组，则nums中长度大于等于k的最大平均值在mid或mid右边，left=mid
            if (isBiggerEqualThanAvg(nums, k, mid)) {
                left = mid;
            } else {
                //nums中不存在长度大于等于k，平均值大于等于mid的子数组，则nums中长度大于等于k的最大平均值在mid左边，right=mid
                //注意：mid为double类型，所以二分时，right不能赋值为mid-1，right只能赋值为mid
                right = mid;
            }
        }

        return left;
    }

    /**
     * 前缀和判断nums中是否存在长度大于等于k，平均值大于等于avg的子数组
     * 核心思想：判断nums中子数组平均值和avg之间的大小关系，不是直接求出子数组平均值，而是判断子数组之和减去子数组长度个avg和0之间的大小关系，
     * 如果大于等于0，则子数组平均值大于等于avg；否则，子数组平均值小于avg
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @param avg
     * @return
     */
    private boolean isBiggerEqualThanAvg(int[] nums, int k, double avg) {
        //当前子数组nums[0]-nums[i]之和减去子数组长度个avg
        double curSum = 0;

        for (int i = 0; i < k; i++) {
            curSum = curSum + nums[i] - avg;
        }

        //curSum大于等于0，则存在长度大于等于k，平均值大于等于avg的子数组，返回true
        if (curSum >= 0) {
            return true;
        }

        //当前子数组nums[0]-nums[i]之前nums[0]-nums[i-k]之和减去(i-k+1)个avg
        //curSum-preSum得到当前遍历到nums[i]，长度大于等于k的子数组平均值
        double preSum = 0;
        //preSum中的最小值
        //curSum-minPreSum得到当前遍历到nums[i]，长度大于等于k的最大子数组平均值
        double minPreSum = 0;

        for (int i = k; i < nums.length; i++) {
            curSum = curSum + nums[i] - avg;
            preSum = preSum + nums[i - k] - avg;
            minPreSum = Math.min(minPreSum, preSum);

            //curSum-minPreSum得到当前遍历到nums[i]，长度大于等于k的最大子数组平均值，最大的平均值大于等于0，
            //则存在长度大于等于k，平均值大于等于avg的子数组，返回true
            if (curSum - minPreSum >= 0) {
                return true;
            }
        }

        //遍历结束，则不存在长度大于等于k，平均值大于等于avg的子数组，返回false
        return false;
    }
}
