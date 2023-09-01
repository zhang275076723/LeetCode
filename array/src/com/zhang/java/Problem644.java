package com.zhang.java;

/**
 * @Date 2023/8/31 08:30
 * @Author zsy
 * @Description 子数组最大平均数 II 类比Problem643 二分查找类比Problem4、Problem287、Problem378、Problem410、Problem658、Problem1482、CutWood、FindMaxArrayMinAfterKMinus
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

        //长度大于等于k的子数组之和的最大平均值，初始化为第一个长度为k的子数组之和的平均值
        double maxAvg = ((double) preSum[k] - preSum[0]) / k;

        for (int i = k; i <= nums.length; i++) {
            //长度为i的子数组之和的最大值，初始化为第一个长度为i的子数组之和
            int sum = preSum[i] - preSum[0];

            for (int j = 0; j <= nums.length - i; j++) {
                sum = Math.max(sum, preSum[j + i] - preSum[j]);
            }

            maxAvg = Math.max(maxAvg, (double) sum / i);
        }

        return maxAvg;
    }

    /**
     * 二分查找变形
     * 对[left,right]进行二分查找，left为数组中的最小值，right为数组中的最大值，
     * 判断数组中是否存在长度大于等于k，平均值大于等于mid的子数组，
     * 如果存在，则left=mid，继续往右边找；
     * 如果不存在，则right=mid，继续往左边找
     * 时间复杂度O(n*log(max-min))=O(n)，空间复杂度O(1) (left和right为int范围内的数，log(right-left)<32)
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

        //二分查找左边界，长度大于等于k的子数组之和的最大平均值的左边界，初始化为nums数组的最小值
        double left = min;
        //二分查找右边界，长度大于等于k的子数组之和的最大平均值的右边界，初始化为nums数组的最大值
        double right = max;
        double mid;

        //误差小于10^-5，则认为找到了长度大于等于k的子数组之和的最大平均值
        while (right - left >= 1e-5) {
            //判断nums数组中是否存在长度大于等于k，平均值大于等于mid的子数组
            mid = left + ((right - left) / 2);

            //nums数组中存在长度大于等于k，平均值大于等于mid的子数组，则left=mid，继续往右边找
            if (isBiggerEqualThanAvg(nums, k, mid)) {
                left = mid;
            } else {
                //nums数组中不存在长度大于等于k，平均值大于等于mid的子数组，则right=mid，继续往左边找
                right = mid;
            }
        }

        return left;
    }

    /**
     * nums数组中是否存在长度大于等于k，平均值大于等于avg的子数组
     * 注意：求子数组之和的平均值和avg之间的大小关系，不是先求子数组之和再除以数组长度，
     * 而是加上当前元素的时候减去avg，得到的结果和0进行比较，
     * 如果大于等于0，则子数组之和的平均值大于等于avg；否则，子数组之和的平均值小于avg
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param k
     * @param avg
     * @return
     */
    private boolean isBiggerEqualThanAvg(int[] nums, int k, double avg) {
        //nums[0]-nums[i]减去avg之和，如果curAvg大于等于0，则存在长度大于等于k，平均值大于等于avg的子数组
        double curSum = 0;

        for (int i = 0; i < k; i++) {
            curSum = curSum + nums[i] - avg;
        }

        if (curSum >= 0) {
            return true;
        }

        //nums[0]-nums[i-k]减去avg之和
        double preSum = 0;
        //nums[0]-nums[0]、nums[0]-nums[1]、...、nums[0]-nums[i-k]减去avg之和中的最小值，
        //通过minPreSum得到nums[0]-nums[i]中长度大于等于k的最大子数组
        double minPreSum = 0;

        for (int i = k; i < nums.length; i++) {
            curSum = curSum + nums[i] - avg;
            //只考虑nums[i-k]，保证curSum-minPreSum子数组长度大于等于k
            preSum = preSum + nums[i - k] - avg;
            minPreSum = Math.min(minPreSum, preSum);

            if (curSum - minPreSum >= 0) {
                return true;
            }
        }

        //遍历结束还没有找到长度大于等于k，平均值大于等于avg的子数组，返回false
        return false;
    }
}
