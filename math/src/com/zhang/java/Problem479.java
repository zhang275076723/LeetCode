package com.zhang.java;

/**
 * @Date 2024/2/17 08:17
 * @Author zsy
 * @Description 最大回文数乘积 因子类比 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem516、Problem647、Problem680、Problem866、Problem1147、Problem1177、Problem1312、Problem1328、Problem1332
 * 给定一个整数 n ，返回 可表示为两个 n 位整数乘积的 最大回文整数 。
 * 因为答案可能非常大，所以返回它对 1337 取余 。
 * <p>
 * 输入：n = 2
 * 输出：987
 * 解释：99 x 91 = 9009, 9009 % 1337 = 987
 * <p>
 * 输入： n = 1
 * 输出： 9
 * <p>
 * 1 <= n <= 8
 */
public class Problem479 {
    public static void main(String[] args) {
        Problem479 problem479 = new Problem479();
        int n = 3;
        System.out.println(problem479.largestPalindrome(n));
    }

    /**
     * 模拟
     * 2个n位数乘积最大为2n位，只需要考虑从大到小2n位回文数的一半，即从最大的n位数(10^n-1)开始，
     * 从大到小的n位数作为2n位回文数的高n位，如果当前回文数为2个n位数的乘积，则当前回文数为2个n位数乘积的最大回文数
     * 时间复杂度O(10^n*10^n)=O(10^2n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int largestPalindrome(int n) {
        //只有n为1的情况下，最大回文数为2n-1=1位，非2n=2位，即为9
        if (n == 1) {
            return 9;
        }

        //最大的n位数
        int max = quickPow(10, n) - 1;

        //从大到小的n位数作为2n位回文数的高n位，即i为2n为回文数的高n位
        for (int i = max; i > 0; i--) {
            //2n位的回文数
            //使用long，避免int溢出
            long palindrome = i;

            for (int j = i; j != 0; j = j / 10) {
                palindrome = palindrome * 10 + j % 10;
            }

            //判断当前回文数palindrome是否为2个n位数的乘积
            //只需要判断较大的n位数能否被palindrome整除
            //使用long，避免int溢出
            for (long j = max; j * j >= palindrome; j--) {
                if (palindrome % j == 0) {
                    return (int) (palindrome % 1337);
                }
            }
        }

        //不存在2个n位数的乘积为回文数，返回-1
        return -1;
    }

    /**
     * 非递归快速幂
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param a
     * @param n
     * @return
     */
    private int quickPow(int a, int n) {
        if (n == 0) {
            return 1;
        }

        int result = 1;

        while (n != 0) {
            if ((n & 1) == 1) {
                result = result * a;
            }

            a = a * a;
            n = n >>> 1;
        }

        return result;
    }
}
