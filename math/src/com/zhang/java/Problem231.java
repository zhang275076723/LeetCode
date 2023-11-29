package com.zhang.java;

/**
 * @Date 2023/8/27 08:46
 * @Author zsy
 * @Description 2 的幂 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt
 * 给你一个整数 n，请你判断该整数是否是 2 的幂次方。
 * 如果是，返回 true ；否则，返回 false 。
 * 如果存在一个整数 x 使得 n == 2^x ，则认为 n 是 2 的幂次方。
 * <p>
 * 输入：n = 1
 * 输出：true
 * 解释：2^0 = 1
 * <p>
 * 输入：n = 16
 * 输出：true
 * 解释：2^4 = 16
 * <p>
 * 输入：n = 3
 * 输出：false
 * <p>
 * 输入：n = 4
 * 输出：true
 * <p>
 * 输入：n = 5
 * 输出：false
 * <p>
 * -2^31 <= n <= 2^31 - 1
 */
public class Problem231 {
    public static void main(String[] args) {
        Problem231 problem231 = new Problem231();
        //0b110
        int n = 6;
        System.out.println(problem231.isPowerOfTwo(n));
        System.out.println(problem231.isPowerOfTwo2(n));
        System.out.println(problem231.isPowerOfTwo3(n));
    }

    /**
     * 模拟
     * n不断的除以2，直至不能被2整除，判断剩下的数是否为1
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1) (n=32，int范围长度为32)
     *
     * @param n
     * @return
     */
    public boolean isPowerOfTwo(int n) {
        //2的整数次幂不可能为负数或0
        if (n <= 0) {
            return false;
        }

        while (n % 2 == 0) {
            n = n / 2;
        }

        return n == 1;
    }

    /**
     * 模拟
     * 如果n是2的整数次幂，则n表示的二进制数中只有1个1，其他位都是0
     * n&(n-1)：得到n表示的二进制数中最低位的1置为0表示的数
     * n&(-n)=n&(~n+1)：得到n表示的二进制数中最低位1表示的数
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public boolean isPowerOfTwo2(int n) {
        //2的整数次幂不可能为负数或0
        if (n <= 0) {
            return false;
        }

        //如果n是2的整数次幂，则n表示的二进制数中只有1个1，其他位都是0
        return (n & (n - 1)) == 0;
    }

    /**
     * 模拟
     * 取int范围内2的整数次幂表示的最大数max，如果n是2的整数次幂，则n*2^k=max，即最大数max能够整除n
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public boolean isPowerOfTwo3(int n) {
        //2的整数次幂不可能为负数或0
        if (n <= 0) {
            return false;
        }

        //int范围内2的整数次幂表示的最大数，2^30
        int max = 0b0100_0000_0000_0000_0000_0000_0000_0000;

        //如果n是2的整数次幂，则2的整数次幂表示的最大数max能够整除n
        return max % n == 0;
    }
}
