package com.zhang.java;

/**
 * @Date 2023/8/10 08:55
 * @Author zsy
 * @Description 整数的各位积和之差 类比Problem7、Problem8、Problem9、Problem190、Problem191、Offer67
 * 给你一个整数 n，请你帮忙计算并返回该整数「各位数字之积」与「各位数字之和」的差。
 * <p>
 * 输入：n = 234
 * 输出：15
 * 解释：
 * 各位数之积 = 2 * 3 * 4 = 24
 * 各位数之和 = 2 + 3 + 4 = 9
 * 结果 = 24 - 9 = 15
 * <p>
 * 输入：n = 4421
 * 输出：21
 * 解释：
 * 各位数之积 = 4 * 4 * 2 * 1 = 32
 * 各位数之和 = 4 + 4 + 2 + 1 = 11
 * 结果 = 32 - 11 = 21
 * <p>
 * 1 <= n <= 10^5
 */
public class Problem1281 {
    public static void main(String[] args) {
        Problem1281 problem1281 = new Problem1281();
        int n = 234;
        System.out.println(problem1281.subtractProductAndSum(n));
    }

    /**
     * 模拟
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int subtractProductAndSum(int n) {
        //n各位元素之积
        int mul = 1;
        //n各位元素之和
        int sum = 0;

        while (n != 0) {
            //当前位元素值
            int cur = n % 10;
            mul = mul * cur;
            sum = sum + cur;
            n = n / 10;
        }

        return mul - sum;
    }
}
