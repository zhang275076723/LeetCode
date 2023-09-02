package com.zhang.java;

/**
 * @Date 2023/3/16 09:58
 * @Author zsy
 * @Description 两整数之和 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt 同Offer65
 * 给你两个整数 a 和 b ，不使用 运算符 + 和 - ，计算并返回两整数之和。
 * <p>
 * 输入：a = 1, b = 2
 * 输出：3
 * <p>
 * 输入：a = 2, b = 3
 * 输出：5
 * <p>
 * -1000 <= a, b <= 1000
 */
public class Problem371 {
    public static void main(String[] args) {
        Problem371 problem371 = new Problem371();
        int a = 13;
        int b = 15;
        System.out.println(problem371.getSum(a, b));
    }

    /**
     * 不使用加减乘除，则使用位运算
     * a和b相加，二进制表示的每一位的非进位和为a ^ b，二进制表示的每一位的进位值为(a & b) << 1
     * 当前位的值和进位的值循环相加，直至进位的值为0，得到a+b的值
     * 例如：13+15=28
     * 13=1101，15=1111
     * 非进位和a^b：0010(2)
     * 进位值(a&b)<<1：11010(26)
     * 2+26=28
     * 非进位和a^b：11000(24)
     * 进位值(a&b)<<1：100(4)
     * 24+4=28
     * 非进位和a^b：11100(28)
     * 进位值(a&b)<<1：0(0)
     * 当前进位值为0，则a^b值28为a+b的结果
     * 时间复杂度O(1)，空间复杂度O(1) (int的二进制表示最长为32位)
     *
     * @param a
     * @param b
     * @return
     */
    public int getSum(int a, int b) {
        //当进位为0时，当前位即为a+b结果
        while (b != 0) {
            //当前位
            int temp1 = a ^ b;
            //进位
            int temp2 = (a & b) << 1;

            a = temp1;
            b = temp2;
        }

        return a;
    }
}
