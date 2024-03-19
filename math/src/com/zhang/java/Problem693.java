package com.zhang.java;

/**
 * @Date 2024/3/9 08:52
 * @Author zsy
 * @Description 交替位二进制数 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IPToInt
 * 给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换句话说，就是二进制表示中相邻两位的数字永不相同。
 * <p>
 * 输入：n = 5
 * 输出：true
 * 解释：5 的二进制表示是：101
 * <p>
 * 输入：n = 7
 * 输出：false
 * 解释：7 的二进制表示是：111
 * <p>
 * 输入：n = 11
 * 输出：false
 * 解释：11 的二进制表示是：1011
 * <p>
 * 1 <= n <= 2^31 - 1
 */
public class Problem693 {
    public static void main(String[] args) {
        Problem693 problem693 = new Problem693();
        int n = 5;
        System.out.println(problem693.hasAlternatingBits(n));
        System.out.println(problem693.hasAlternatingBits2(n));
    }

    /**
     * 模拟
     * 时间复杂度O(logn)=O(32)=O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public boolean hasAlternatingBits(int n) {
        //n的二进制表示的数从右往左当前位前一位的值
        int sign = n & 1;
        n = n >>> 1;

        while (n != 0) {
            //当前位的值
            int cur = n & 1;

            //当前位和前一位的值相同，则n不是01交替出现的数，返回false
            if ((sign == 1 && cur == 1) || (sign == 0 && cur == 0)) {
                return false;
            }

            n = n >>> 1;
            sign = sign == 1 ? 0 : 1;
        }

        //遍历结束，则n是01交替出现的数，返回true
        return true;
    }

    /**
     * 位运算
     * 假设n为01交替出现的数，则n^(n>>>1)的二进制表示的数为全1，
     * 假设a为二进制表示的数为全1的数，则a&(a+1)为0
     * 时间复杂度O(1)，空间复杂度O(1)
     * <p>
     * 例如：n=11，二进制表示为1010
     * n^(n>>>1)=1010^0101=1111
     * 1111&(1111+1)=1111&10000=0
     * 即n是01交替出现的数
     *
     * @param n
     * @return
     */
    public boolean hasAlternatingBits2(int n) {
        //如果n为01交替出现的数，则x的二进制表示的数为全1
        int x = n ^ (n >>> 1);

        //如果x的二进制表示的数为全1，则11...11&100..00为0，即n是01交替出现的数
        return (x & (x + 1)) == 0;
    }
}
