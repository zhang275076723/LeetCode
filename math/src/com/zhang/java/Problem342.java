package com.zhang.java;

/**
 * @Date 2023/8/27 09:15
 * @Author zsy
 * @Description 4的幂 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt
 * 给定一个整数，写一个函数来判断它是否是 4 的幂次方。
 * 如果是，返回 true ；否则，返回 false 。
 * 整数 n 是 4 的幂次方需满足：存在整数 x 使得 n == 4^x
 * <p>
 * 输入：n = 16
 * 输出：true
 * <p>
 * 输入：n = 5
 * 输出：false
 * <p>
 * 输入：n = 1
 * 输出：true
 * <p>
 * -2^31 <= n <= 2^31 - 1
 */
public class Problem342 {
    public static void main(String[] args) {
        Problem342 problem342 = new Problem342();
        int n = 16;
        System.out.println(problem342.isPowerOfFour(n));
        System.out.println(problem342.isPowerOfFour2(n));
        System.out.println(problem342.isPowerOfFour3(n));
    }

    /**
     * 模拟
     * n不断的除以4，直至不能被4整除，判断剩下的数是否为1
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1) (n=32，int范围长度为32)
     *
     * @param n
     * @return
     */
    public boolean isPowerOfFour(int n) {
        //4的整数次幂不可能为负数或0
        if (n <= 0) {
            return false;
        }

        while (n % 4 == 0) {
            n = n / 4;
        }

        return n == 1;
    }

    /**
     * 模拟
     * 如果n是4的整数次幂，则n表示的二进制数中只有1个1，其他位都是0，并且这个1出现在从低位到高位的奇数位上
     * n&(n-1)：得到n表示的二进制数中最低位的1置为0表示的数
     * n&(-n)：得到n表示的二进制数中最低位1表示的数
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public boolean isPowerOfFour2(int n) {
        //4的整数次幂不可能为负数或0
        if (n <= 0) {
            return false;
        }

        //如果n是4的整数次幂，则n表示的二进制数中只有1个1，其他位都是0，并且这个1出现在从低位到高位的奇数位上
        //n&0xaaaaaaaa：获取n所有偶数位1
        return ((n & (n - 1)) == 0) && ((n & 0xaaaaaaaa) == 0);
    }

    /**
     * 模拟
     * 如果n是4的整数次幂，则n表示的二进制数中只有1个1，其他位都是0，并且n%3等于1
     * 如果n是4的整数次幂，则n=4^k，n%3等于1；如果n是2的整数次幂，但不是4的整数次幂，则n=2*4^k，n%3等于2
     * n&(n-1)：得到n表示的二进制数中最低位的1置为0表示的数
     * n&(-n)：得到n表示的二进制数中最低位1表示的数
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public boolean isPowerOfFour3(int n) {
        //4的整数次幂不可能为负数或0
        if (n <= 0) {
            return false;
        }

        //如果n是4的整数次幂，则n=4^k，n%3等于1；如果n是2的整数次幂，但不是4的整数次幂，则n=2*4^k，n%3等于2
        //n%3为1，则说明n是4的整数次幂；n%3为2，则说明n是2的整数次幂，但不是4的整数次幂
        return ((n & (n - 1)) == 0) && (n % 3 == 1);
    }
}
