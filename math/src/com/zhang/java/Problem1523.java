package com.zhang.java;

/**
 * @Date 2024/8/18 09:11
 * @Author zsy
 * @Description 在区间范围内统计奇数数目 类比Problem201
 * 给你两个非负整数 low 和 high 。请你返回 low 和 high 之间（包括二者）奇数的数目。
 * <p>
 * 输入：low = 3, high = 7
 * 输出：3
 * 解释：3 到 7 之间奇数数字为 [3,5,7] 。
 * <p>
 * 输入：low = 8, high = 10
 * 输出：1
 * 解释：8 到 10 之间奇数数字为 [9] 。
 * <p>
 * 0 <= low <= high <= 10^9
 */
public class Problem1523 {
    public static void main(String[] args) {
        Problem1523 problem1523 = new Problem1523();
        int low = 3;
        int high = 7;
        System.out.println(problem1523.countOdds(low, high));
    }

    /**
     * 模拟
     * low为偶数，则从low+1开始考虑[left,right]中的奇数个数；high为偶数，则从high-1开始考虑中的奇数个数
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param low
     * @param high
     * @return
     */
    public int countOdds(int low, int high) {
        if (low % 2 == 0) {
            low++;
        }

        if (high % 2 == 0) {
            high--;
        }

        if (low > high) {
            return 0;
        }

        return (high - low) / 2 + 1;
    }
}
