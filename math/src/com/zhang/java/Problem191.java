package com.zhang.java;

/**
 * @Date 2022/7/27 9:10
 * @Author zsy
 * @Description 位1的个数 位运算类比Problem29、Problem136、Problem137、Problem190、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt 同Offer15
 * 编写一个函数，输入是一个无符号整数（以二进制串的形式），
 * 返回其二进制表达式中数字位数为 '1' 的个数（也被称为汉明重量）。
 * 请注意，在某些语言（如 Java）中，没有无符号整数类型。
 * 在这种情况下，输入和输出都将被指定为有符号整数类型，并且不应影响您的实现，
 * 因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
 * 在 Java 中，编译器使用二进制补码记法来表示有符号整数。
 * 因此，在上面的 示例 3 中，输入表示有符号整数 -3。
 * <p>
 * 输入：00000000000000000000000000001011
 * 输出：3
 * 解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。
 * <p>
 * 输入：00000000000000000000000010000000
 * 输出：1
 * 解释：输入的二进制串 00000000000000000000000010000000 中，共有一位为 '1'。
 * <p>
 * 输入：11111111111111111111111111111101
 * 输出：31
 * 解释：输入的二进制串 11111111111111111111111111111101 中，共有 31 位为 '1'。
 * <p>
 * 输入必须是长度为 32 的 二进制串 。
 */
public class Problem191 {
    public static void main(String[] args) {
        Problem191 problem191 = new Problem191();
        System.out.println(problem191.hammingWeight(-3));
        System.out.println(problem191.hammingWeight2(-3));
        System.out.println(problem191.hammingWeight3(-3));
    }

    /**
     * 模拟
     * (n>>i)&1，如果结果为0，说明n表示的二进制中由右往左的第i位为1
     * 时间复杂度O(1)，空间复杂度O(1) (int长度为32，即需要循环32次)
     *
     * @param n
     * @return
     */
    public int hammingWeight(int n) {
        int count = 0;

        while (n != 0) {
            count = count + (n & 1);
            //计算n二进制表示中1的个数，必须使用无符号右移
            n = n >>> 1;
        }

        return count;
    }

    /**
     * 模拟
     * x^y：得到x、y表示的二进制数中当前位不同则置为1，相同则置为0表示的数
     * n&(n-1)：n表示的二进制数中最低位的1置为0表示的数
     * n&(-n)=n&(~n+1)：n表示的二进制数中最低位的1表示的数
     * n^1：将n表示的二进制中最低位翻转，原先为1，置为0，原先为0，置为1
     * 时间复杂度O(1)，空间复杂度O(1) (int长度为32，即需要循环32次)
     *
     * @param n
     * @return
     */
    public int hammingWeight2(int n) {
        int count = 0;

        while (n != 0) {
            //将n表示的二进制中的最低位的1置为0
            n = n & (n - 1);
            count++;
        }

        return count;
    }

    /**
     * 分治法，bitCount()源码
     * 统计每2位中1的个数，存放在这2位中
     * 统计每4位中1的个数，存放在这4位中
     * 统计每8位中1的个数，存放在这8位中
     * 统计每16位中1的个数，存放在这16位中
     * 统计每32位中1的个数，存放在这32位中
     * 返回这32位表示的数，即为1的个数
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int hammingWeight3(int n) {
        //统计每2位中1的个数，存放在这2位中
        //0xaaaaaaaa：得到2位中的高1位，0x55555555：得到2位中的低1位
        n = ((n & 0xaaaaaaaa) >>> 1) + (n & 0x55555555);

        //统计每4位中1的个数，存放在这4位中
        //0xcccccccc：得到4位中的高2位，0x33333333：得到4位中的低2位
        n = ((n & 0xcccccccc) >>> 2) + (n & 0x33333333);

        //统计每8位中1的个数，存放在这8位中
        //0xf0f0f0f0：得到8位中的高4位，0x0f0f0f0f：得到8位中的低4位
        n = ((n & 0xf0f0f0f0) >>> 4) + (n & 0x0f0f0f0f);

        //统计每16位中1的个数，存放在这16位中
        //0xff00ff00：得到16位中的高8位，0x00ff00ff：得到16位中的低8位
        n = ((n & 0xff00ff00) >>> 8) + (n & 0x00ff00ff);

        //统计每32位中1的个数，存放在这32位中
        //0xffff0000：得到32位中的高16位，0x0000ffff：得到32位中的低16位
        n = ((n & 0xffff0000) >>> 16) + (n & 0x0000ffff);

        return n;
    }
}
