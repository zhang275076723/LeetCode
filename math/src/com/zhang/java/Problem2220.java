package com.zhang.java;

/**
 * @Date 2024/6/28 08:26
 * @Author zsy
 * @Description 转换数字的最少位翻转次数 类比Problem1318、Problem2997 位运算类比
 * 一次 位翻转 定义为将数字 x 二进制中的一个位进行 翻转 操作，即将 0 变成 1 ，或者将 1 变成 0 。
 * 比方说，x = 7 ，二进制表示为 111 ，我们可以选择任意一个位（包含没有显示的前导 0 ）并进行翻转。
 * 比方说我们可以翻转最右边一位得到 110 ，或者翻转右边起第二位得到 101 ，
 * 或者翻转右边起第五位（这一位是前导 0 ）得到 10111 等等。
 * 给你两个整数 start 和 goal ，请你返回将 start 转变成 goal 的 最少位翻转 次数。
 * <p>
 * 输入：start = 10, goal = 7
 * 输出：3
 * 解释：10 和 7 的二进制表示分别为 1010 和 0111 。我们可以通过 3 步将 10 转变成 7 ：
 * - 翻转右边起第一位得到：1010 -> 1011 。
 * - 翻转右边起第三位：1011 -> 1111 。
 * - 翻转右边起第四位：1111 -> 0111 。
 * 我们无法在 3 步内将 10 转变成 7 。所以我们返回 3 。
 * <p>
 * 输入：start = 3, goal = 4
 * 输出：3
 * 解释：3 和 4 的二进制表示分别为 011 和 100 。我们可以通过 3 步将 3 转变成 4 ：
 * - 翻转右边起第一位：011 -> 010 。
 * - 翻转右边起第二位：010 -> 000 。
 * - 翻转右边起第三位：000 -> 100 。
 * 我们无法在 3 步内将 3 变成 4 。所以我们返回 3 。
 * <p>
 * 0 <= start, goal <= 10^9
 */
public class Problem2220 {
    public static void main(String[] args) {
        Problem2220 problem2220 = new Problem2220();
        int start = 10;
        int goal = 7;
        System.out.println(problem2220.minBitFlips(start, goal));
        System.out.println(problem2220.minBitFlips2(start, goal));
    }

    /**
     * 位运算
     * start当前位的值和goal当前位的值异或运算结果即为当前位需要翻转的次数
     * 时间复杂度O(logC)=O(1)，空间复杂度O(1) (C=10^9)
     *
     * @param start
     * @param goal
     * @return
     */
    public int minBitFlips(int start, int goal) {
        int count = 0;

        //start和goal都为正数，不需要考虑最高位符号位
        for (int i = 0; i < 31; i++) {
            //start当前位的值
            int cur1 = (start >>> i) & 1;
            //goal当前位的值
            int cur2 = (goal >>> i) & 1;
            //cur1和cur2不相等，则当前位需要翻转
            count = count + (cur1 ^ cur2);
        }

        return count;
    }

    /**
     * 位运算
     * start和goal异或运算结果中二进制中1的个数即为需要翻转的次数
     * 时间复杂度O(logC)=O(1)，空间复杂度O(1) (C=10^9)
     *
     * @param start
     * @param goal
     * @return
     */
    public int minBitFlips2(int start, int goal) {
        int xor = start ^ goal;
        int count = 0;

        while (xor != 0) {
            count++;
            xor = xor & (xor - 1);
        }

        return count;
    }
}
