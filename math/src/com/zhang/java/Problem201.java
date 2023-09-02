package com.zhang.java;

/**
 * @Date 2023/7/14 08:20
 * @Author zsy
 * @Description 数字范围按位与 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt
 * 给你两个整数 left 和 right ，表示区间 [left, right] ，
 * 返回此区间内所有数字 按位与 的结果（包含 left 、right 端点）。
 * <p>
 * 输入：left = 5, right = 7
 * 输出：4
 * <p>
 * 输入：left = 0, right = 0
 * 输出：0
 * <p>
 * 输入：left = 1, right = 2147483647
 * 输出：0
 * <p>
 * 0 <= left <= right <= 2^31 - 1
 */
public class Problem201 {
    public static void main(String[] args) {
        Problem201 problem201 = new Problem201();
        int left = 5;
        int right = 7;
        System.out.println(problem201.rangeBitwiseAnd(left, right));
        System.out.println(problem201.rangeBitwiseAnd2(left, right));
    }

    /**
     * 暴力
     * 时间复杂度O(right-left)，空间复杂度O(1)
     *
     * @param left
     * @param right
     * @return
     */
    public int rangeBitwiseAnd(int left, int right) {
        int result = left;

        for (int i = left; i <= right; i++) {
            result = result & i;
        }

        return result;
    }

    /**
     * 模拟
     * 只要有一个数二进制的当前位是0，则当前位的与运算结果为0
     * 找left和right二进制表示的最长公共前缀，即为[left,right]的与运算结果
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1) (n=32，int范围长度为32)
     *
     * @param left
     * @param right
     * @return
     */
    public int rangeBitwiseAnd2(int left, int right) {
        //left和right二进制右移的次数
        int count = 0;

        //找left和right二进制表示的最长公共前缀
        while (left != right) {
            left = left >>> 1;
            right = right >>> 1;
            count++;
        }

        //右移之后再重新左移count次，得到[left,right]的与运算结果
        return left << count;
    }
}
