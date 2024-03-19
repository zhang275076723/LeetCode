package com.zhang.java;

/**
 * @Date 2022/7/27 7:34
 * @Author zsy
 * @Description 颠倒二进制位 位运算类比Problem29、Problem136、Problem137、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt
 * 颠倒给定的 32 位无符号整数的二进制位。
 * 请注意，在某些语言（如 Java）中，没有无符号整数类型。
 * 在这种情况下，输入和输出都将被指定为有符号整数类型，并且不应影响您的实现，
 * 因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
 * 在 Java 中，编译器使用二进制补码记法来表示有符号整数。
 * 因此，在 示例 2 中，输入表示有符号整数 -3，输出表示有符号整数 -1073741825。
 * <p>
 * 输入：n = 00000010100101000001111010011100
 * 输出：964176192 (00111001011110000010100101000000)
 * 解释：输入的二进制串 00000010100101000001111010011100 表示无符号整数 43261596，
 * 因此返回 964176192，其二进制表示形式为 00111001011110000010100101000000。
 * <p>
 * 输入：n = 11111111111111111111111111111101
 * 输出：3221225471 (10111111111111111111111111111111)
 * 解释：输入的二进制串 11111111111111111111111111111101 表示无符号整数 4294967293，
 * 因此返回 3221225471 其二进制表示形式为 10111111111111111111111111111111 。
 * <p>
 * 输入是一个长度为 32 的二进制字符串
 */
public class Problem190 {
    public static void main(String[] args) {
        Problem190 problem190 = new Problem190();
        System.out.println(problem190.reverseBits(-3));
        System.out.println(problem190.reverseBits2(-3));
    }

    /**
     * 模拟
     * n & 1，得到n表示的二进制中最低位的值
     * n ^ 1，将n表示的二进制中最低位翻转，原先为1，置为0，原先为0，置为1
     * 时间复杂度O(1)，空间复杂度O(1) (int长度为32，即需要循环32次)
     *
     * @param n
     * @return
     */
    public int reverseBits(int n) {
        int result = 0;

        //注意：不能跳出循环条件为n==0，因为有可能n表示的二进制中最高几位为0，导致最高几位的0丢失
        for (int i = 0; i < 32; i++) {
            result = (result << 1) + (n & 1);
            //n可能为负数，所以必须使用无符号右移
            n = n >>> 1;
        }

        return result;
    }

    /**
     * 分治法，类比bitCount()源码
     * 每2位中1位1位进行交换，每4位中2位2位进行交换，每8位中4位4位进行交换，每16位中8位8位进行交换，每32位中16位16位进行交换，得到32位反转之后的结果
     * 时间复杂度O(1)，空间复杂度O(1)
     * <p>
     * 例如：n=-3，二进制表示为1111_1111_1111_1111_1111_1111_1111_1101
     * 每2位中1位1位进行交换，得到1111_1111_1111_1111_1111_1111_1111_1110
     * 每4位中2位2位进行交换，得到1111_1111_1111_1111_1111_1111_1111_1011
     * 每8位中4位4位进行交换，得到1111_1111_1111_1111_1111_1111_1011_1111
     * 每16位中8位8位进行交换，得到1111_1111_1111_1111_1011_1111_1111_1111
     * 每32位中16位16位进行交换，得到1011_1111_1111_1111_1111_1111_1111_1111
     * 即n=-3反转之后的结果为-1073741825
     *
     * @param n
     * @return
     */
    public int reverseBits2(int n) {
        //每2位中1位1位进行交换
        //0xaaaaaaaa：得到2位中的高1位，0x55555555：得到2位中的低1位
        n = ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1);

        //每3位中2位2位进行交换
        //0xcccccccc：得到4位中的高2位，0x33333333：得到4位中的低2位
        n = ((n & 0xcccccccc) >>> 2) | ((n & 0x33333333) << 2);

        //每8位中4位4位进行交换
        //0xf0f0f0f0：得到8位中的高4位，0x0f0f0f0f：得到8位中的低4位
        n = ((n & 0xf0f0f0f0) >>> 4) | ((n & 0x0f0f0f0f) << 4);

        //每16位中8位8位进行交换
        //0xff00ff00：得到16位中的高8位，0x00ff00ff：得到16位中的低8位
        n = ((n & 0xff00ff00) >>> 8) | ((n & 0x00ff00ff) << 8);

        //每32位中16位16位进行交换
        //0xffff0000：得到16位中的高8位，0x0000ffff：得到16位中的低8位
        n = ((n & 0xffff0000) >>> 16) | ((n & 0x0000ffff) << 16);

        return n;
    }
}
