package com.zhang.java;

/**
 * @Date 2024/6/27 09:00
 * @Author zsy
 * @Description 或运算的最小翻转次数 类比Problem2220、Problem2997 位运算类比
 * 给你三个正整数 a、b 和 c。
 * 你可以对 a 和 b 的二进制表示进行位翻转操作，返回能够使按位或运算   a OR b == c  成立的最小翻转次数。
 * 「位翻转操作」是指将一个数的二进制表示任何单个位上的 1 变成 0 或者 0 变成 1 。
 * <p>
 * 输入：a = 2, b = 6, c = 5
 * 输出：3
 * 解释：翻转后 a = 1 , b = 4 , c = 5 使得 a OR b == c
 * <p>
 * 输入：a = 4, b = 2, c = 7
 * 输出：1
 * <p>
 * 输入：a = 1, b = 2, c = 3
 * 输出：0
 * <p>
 * 1 <= a <= 10^9
 * 1 <= b <= 10^9
 * 1 <= c <= 10^9
 */
public class Problem1318 {
    public static void main(String[] args) {
        Problem1318 problem1318 = new Problem1318();
        int a = 2;
        int b = 6;
        int c = 5;
        System.out.println(problem1318.minFlips(a, b, c));
    }

    /**
     * 位运算
     * c当前位为0，则当前位a和b必须都为0，需要翻转(a当前位的值+b当前位的值)次；
     * c当前位为1，则当前位a和b至少有一个为1，当a当前位的值和b当前位的值都为0时，才需要翻转1次
     * 时间复杂度O(logC)=O(1)，空间复杂度O(1) (C=10^9)
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public int minFlips(int a, int b, int c) {
        int count = 0;

        //a、b、c都为正数，不需要考虑最高位符号位
        for (int i = 30; i >= 0; i--) {
            int bitA = (a >>> i) & 1;
            int bitB = (b >>> i) & 1;
            int bitC = (c >>> i) & 1;

            //c当前位为0，则当前位a和b必须都为0，需要翻转(bitA+bitB)次
            if (bitC == 0) {
                count = count + bitA + bitB;
            } else {
                //c当前位为1，则当前位a和b至少有一个为1，当bitA和bitB都为0时，才需要翻转1次
                if (bitA == 0 && bitB == 0) {
                    count++;
                }
            }
        }

        return count;
    }
}
