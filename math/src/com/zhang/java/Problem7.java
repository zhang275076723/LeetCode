package com.zhang.java;

/**
 * @Date 2022/7/26 17:36
 * @Author zsy
 * @Description 整数反转 类比Problem8、Problem9、Problem190、Problem191、Problem1281、Offer67
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 * 如果反转后整数超过 32 位的有符号整数的范围 [−2^31, 2^31 − 1] ，就返回 0。
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 * <p>
 * 输入：x = 123
 * 输出：321
 * <p>
 * 输入：x = -123
 * 输出：-321
 * <p>
 * 输入：x = 120
 * 输出：21
 * <p>
 * 输入：x = 0
 * 输出：0
 * <p>
 * -2^31 <= x <= 2^31 - 1
 */
public class Problem7 {
    public static void main(String[] args) {
        Problem7 problem7 = new Problem7();
        System.out.println(problem7.reverse(-123));
    }

    /**
     * 模拟
     * 注意溢出情况的处理
     * 时间复杂度O(logx)，空间复杂度O(1)
     *
     * @param x
     * @return
     */
    public int reverse(int x) {
        int result = 0;
        //当前位的值
        int cur;

        while (x != 0) {
            cur = x % 10;
            x = x / 10;

            //上溢出
            if (result > Integer.MAX_VALUE / 10 ||
                    (result == Integer.MAX_VALUE / 10 && cur > Integer.MAX_VALUE % 10)) {
                return 0;
            }

            //下溢出
            if (result < Integer.MIN_VALUE / 10 ||
                    (result == Integer.MIN_VALUE / 10 && cur < Integer.MIN_VALUE % 10)) {
                return 0;
            }

            result = result * 10 + cur;
        }

        return result;
    }
}
