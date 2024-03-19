package com.zhang.java;

/**
 * @Date 2024/3/10 09:07
 * @Author zsy
 * @Description 二进制表示中质数个计算置位 质数类比Problem204、Problem866、Problem952、Problem1175、Problem1998、Problem2523、Problem2614 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IPToInt
 * 给你两个整数 left 和 right ，在闭区间 [left, right] 范围内，统计并返回 计算置位位数为质数 的整数个数。
 * 计算置位位数 就是二进制表示中 1 的个数。
 * 例如， 21 的二进制表示 10101 有 3 个计算置位。
 * <p>
 * 输入：left = 6, right = 10
 * 输出：4
 * 解释：
 * 6 -> 110 (2 个计算置位，2 是质数)
 * 7 -> 111 (3 个计算置位，3 是质数)
 * 9 -> 1001 (2 个计算置位，2 是质数)
 * 10-> 1010 (2 个计算置位，2 是质数)
 * 共计 4 个计算置位为质数的数字。
 * <p>
 * 输入：left = 10, right = 15
 * 输出：5
 * 解释：
 * 10 -> 1010 (2 个计算置位, 2 是质数)
 * 11 -> 1011 (3 个计算置位, 3 是质数)
 * 12 -> 1100 (2 个计算置位, 2 是质数)
 * 13 -> 1101 (3 个计算置位, 3 是质数)
 * 14 -> 1110 (3 个计算置位, 3 是质数)
 * 15 -> 1111 (4 个计算置位, 4 不是质数)
 * 共计 5 个计算置位为质数的数字。
 * <p>
 * 1 <= left <= right <= 10^6
 * 0 <= right - left <= 10^4
 */
public class Problem762 {
    public static void main(String[] args) {
        Problem762 problem762 = new Problem762();
        int left = 10;
        int right = 15;
        System.out.println(problem762.countPrimeSetBits(left, right));
    }

    /**
     * 模拟
     * 时间复杂度O((right-left)*log(right))=O(right-left)，空间复杂度O(1)
     *
     * @param left
     * @param right
     * @return
     */
    public int countPrimeSetBits(int left, int right) {
        //int范围内只有32位，直接计算1-32中的质数
        boolean[] arr = new boolean[33];

        for (int i = 2; i <= 32; i++) {
            //i是否为质数标志位
            boolean flag = true;

            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                arr[i] = true;
            }
        }

        int result = 0;

        for (int i = left; i <= right; i++) {
            //i表示的二进制数中1的个数
            int count = 0;
            int num = i;

            while (num != 0) {
                count = count + (num & 1);
                num = num >>> 1;
            }

            //i表示的二进制数中1的个数为质数，则result加1
            if (arr[count]) {
                result++;
            }
        }

        return result;
    }
}
