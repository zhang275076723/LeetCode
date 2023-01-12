package com.zhang.java;

/**
 * @Date 2022/9/10 8:40
 * @Author zsy
 * @Description 单调递增的数字 类比Problem31、Problem556
 * 当且仅当每个相邻位数上的数字 x 和 y 满足 x <= y 时，我们称这个整数是单调递增的。
 * 给定一个整数 n ，返回 小于或等于 n 的最大数字，且数字呈 单调递增 。
 * <p>
 * 输入: n = 10
 * 输出: 9
 * <p>
 * 输入: n = 1234
 * 输出: 1234
 * <p>
 * 输入: n = 332
 * 输出: 299
 * <p>
 * 0 <= n <= 10^9
 */
public class Problem738 {
    public static void main(String[] args) {
        Problem738 problem738 = new Problem738();
        int n = 1332;
        System.out.println(problem738.monotoneIncreasingDigits(n));
    }

    /**
     * 模拟
     * 从左往右遍历，找到从nums[0]开始的递增数组nums[0]-nums[i]，再往前找和nums[i]连续相等的第一个元素nums[j]，
     * nums[j]减1，nums[j]之后所有元素均赋值为9
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param n
     * @return
     */
    public int monotoneIncreasingDigits(int n) {
        if (n < 10) {
            return n;
        }

        char[] nums = (n + "").toCharArray();

        int i = 0;

        //找从nums[0]开始的递增数组nums[0]-nums[i]
        while (i < nums.length - 1 && nums[i] <= nums[i + 1]) {
            i++;
        }

        //n已经为单调递增的数字，直接返回
        if (i == nums.length - 1) {
            return n;
        }

        //往前找和nums[i]连续相等的第一个元素nums[j]
        while (i > 0 && nums[i] == nums[i - 1]) {
            i--;
        }

        //第一个元素nums[i]减1
        nums[i]--;

        //nums[i]之后所有元素均赋值为9
        for (int j = i + 1; j < nums.length; j++) {
            nums[j] = '9';
        }

        int result = 0;

        for (int j = 0; j < nums.length; j++) {
            result = result * 10 + nums[j] - '0';
        }

        return result;
    }
}
