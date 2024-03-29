package com.zhang.java;

/**
 * @Date 2022/12/6 09:27
 * @Author zsy
 * @Description 丑数 各种数类比Problem202、Problem204、Problem264、Problem306、Problem313、Problem507、Problem509、Problem728、Problem842、Problem878、Problem1175、Problem1201、Problem1291、Offer10、Offer49
 * 丑数 就是只包含质因数 2、3 和 5 的正整数。
 * 给你一个整数 n ，请你判断 n 是否为 丑数 。
 * 如果是，返回 true ；否则，返回 false 。
 * <p>
 * 输入：n = 6
 * 输出：true
 * 解释：6 = 2 × 3
 * <p>
 * 输入：n = 1
 * 输出：true
 * 解释：1 没有质因数，因此它的全部质因数是 {2, 3, 5} 的空集。习惯上将其视作第一个丑数。
 * <p>
 * 输入：n = 14
 * 输出：false
 * 解释：14 不是丑数，因为它包含了另外一个质因数 7 。
 * <p>
 * -2^31 <= n <= 2^31 - 1
 */
public class Problem263 {
    public static void main(String[] args) {
        Problem263 problem263 = new Problem263();
        int n = 14;
        System.out.println(problem263.isUgly(n));
    }

    /**
     * 模拟
     * n不断除以2、3、5，直至不能整除，剩下的数为1，则是丑数
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }

        while (n % 5 == 0) {
            n = n / 5;
        }

        while (n % 3 == 0) {
            n = n / 3;
        }

        while (n % 2 == 0) {
            n = n / 2;
        }

        return n == 1;
    }
}
