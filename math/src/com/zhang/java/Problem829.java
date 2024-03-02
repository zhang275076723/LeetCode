package com.zhang.java;

/**
 * @Date 2024/2/22 08:21
 * @Author zsy
 * @Description 连续整数求和 类比Offer57_2 因子类比Problem172 479
 * 给定一个正整数 n，返回 连续正整数满足所有数字之和为 n 的组数 。
 * <p>
 * 输入: n = 5
 * 输出: 2
 * 解释: 5 = 2 + 3，共有两组连续整数([5],[2,3])求和后为 5。
 * <p>
 * 输入: n = 9
 * 输出: 3
 * 解释: 9 = 4 + 5 = 2 + 3 + 4
 * <p>
 * 输入: n = 15
 * 输出: 4
 * 解释: 15 = 8 + 7 = 4 + 5 + 6 = 1 + 2 + 3 + 4 + 5
 * <p>
 * 1 <= n <= 10^9
 */
public class Problem829 {
    public static void main(String[] args) {
        Problem829 problem829 = new Problem829();
//        int n = 15;
        int n = 246854111;
        System.out.println(problem829.consecutiveNumbersSum(n));
        System.out.println(problem829.consecutiveNumbersSum2(n));
        System.out.println(problem829.consecutiveNumbersSum3(n));
    }

    /**
     * 滑动窗口，双指针
     * 左指针left为连续整数的左边界，右指针right为连续整数的右边界，
     * 如果当前连续整数之和sum小于n，右指针右移right++，sum加上right；
     * 如果当前连续整数之和sum大于n，sum减去left，左指针右移left++；
     * 如果当前连续整数之和sum等于n，则left到right的连续正整数之和为n，sum减去left，左指针右移left++
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int consecutiveNumbersSum(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }

        //连续正整数之和为n的个数，初始化为1，即只有n一个正整数的情况
        int count = 1;
        int left = 1;
        int right = 1;
        //left-right连续整数之和
        int sum = 1;

        //right最多为n/2+1，right再往右移动连续整数之和超过n，不存在和为n的情况
        while (right <= n / 2 + 1) {
            //sum等于n，则left到right的连续正整数之和为n，sum减去left，左指针右移left++
            if (sum == n) {
                sum = sum - left;
                left++;
                count++;
            } else if (sum > n) {
                //sum大于n，sum减去left，左指针右移left++
                sum = sum - left;
                left++;
            } else {
                //sum小于n，右指针右移right++，sum加上right
                right++;
                sum = sum + right;
            }
        }

        return count;
    }

    /**
     * 数学
     * i到j连续整数之和为n，根据(i+j)(j-i+1)/2=n，得到j^2+j+i-i^2-2n=0，解方程组得到正整数j=(-1+(1+4(i^2-i+2n))^(1/2))/2
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int consecutiveNumbersSum2(int n) {
        //连续正整数之和为n的个数，初始化为1，即只有n一个正整数的情况
        int count = 1;

        for (int i = 1; i <= n / 2; i++) {
            //使用long，避免int溢出
            long delta = 1 + 4 * ((long) i * i - i + 2L * n);

            //如果delta小于等于0，则不存在i到j的连续正整数之和为n，进行下次循环
            if (delta <= 0) {
                continue;
            }

            int sqrtDelta = (int) Math.sqrt(delta);

            //delta开方为整数，并且j为整数，则i到j的连续正整数之和为n
            if ((long) sqrtDelta * sqrtDelta == delta && (-1 + sqrtDelta) % 2 == 0) {
                count++;
            }
        }

        return count;
    }

    /**
     * 数学
     * 当前连续整数从i开始，有k项，则i到i+k-1连续整数之和为n，根据(i+i+k-1)*k/2=n，得到2i=2n/k-k+1，
     * 因为i>=1，则2n/k-k+1>=2，2n/k>=k+1>k，2n/k>k，
     * 由2n/k>k和(2i+k-1)*k=2n，得到k为2n中较小一半的因子，并且k对应的i=(2n/k-k+1)/2存在，则i到i+k-1连续整数之和为n
     * 时间复杂度O(2n^(1/2))，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int consecutiveNumbersSum3(int n) {
        //连续正整数之和为n的个数，初始化为0
        int count = 0;

        //当前连续整数有k项
        for (int k = 1; k * k < 2 * n; k++) {
            //由2n/k>k和(2i+k-1)*k=2n，得到k为2n中较小一半的因子，并且k对应的i=(2n/k-k+1)/2存在，则i到i+k-1连续整数之和为n
            if ((2 * n) % k == 0 && (2 * n / k - k + 1) % 2 == 0) {
                count++;
            }
        }

        return count;
    }
}
