package com.zhang.java;

/**
 * @Date 2023/8/27 09:04
 * @Author zsy
 * @Description 3 的幂 类比Problem231、Problem342 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt
 * 给定一个整数，写一个函数来判断它是否是 3 的幂次方。
 * 如果是，返回 true ；否则，返回 false 。
 * 整数 n 是 3 的幂次方需满足：存在整数 x 使得 n == 3^x
 * <p>
 * 输入：n = 27
 * 输出：true
 * <p>
 * 输入：n = 0
 * 输出：false
 * <p>
 * 输入：n = 9
 * 输出：true
 * <p>
 * 输入：n = 45
 * 输出：false
 * <p>
 * -2^31 <= n <= 2^31 - 1
 */
public class Problem326 {
    public static void main(String[] args) {
        Problem326 problem326 = new Problem326();
        //0b101101
        int n = 45;
        System.out.println(problem326.isPowerOfThree(n));
        System.out.println(problem326.isPowerOfThree2(n));
    }

    /**
     * 模拟
     * n不断的除以3，直至不能被3整除，判断剩下的数是否为1
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1) (n=32，int范围长度为32)
     *
     * @param n
     * @return
     */
    public boolean isPowerOfThree(int n) {
        //3的整数次幂不可能为负数或0
        if (n <= 0) {
            return false;
        }

        while (n % 3 == 0) {
            n = n / 3;
        }

        return n == 1;
    }

    /**
     * 模拟
     * 取int范围内3的整数次幂表示的最大数max，如果n是3的整数次幂，则n*3^k=max，即最大数max能够整除n
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public boolean isPowerOfThree2(int n) {
        //3的整数次幂不可能为负数或0
        if (n <= 0) {
            return false;
        }

        //int范围内3的整数次幂表示的最大数，3^19
        int max = 3;

        //得到max，max*3避免int溢出
        while (max * 3 > 0 && max * 3 <= Integer.MAX_VALUE) {
            max = max * 3;
        }

        //如果n是3的整数次幂，则3的整数次幂表示的最大数max能够整除n
        return max % n == 0;
    }
}
