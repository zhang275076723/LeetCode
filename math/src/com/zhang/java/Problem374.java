package com.zhang.java;

/**
 * @Date 2022/12/8 08:40
 * @Author zsy
 * @Description 猜数字大小 类比Problem278、Problem375
 * 猜数字游戏的规则如下：
 * 每轮游戏，我都会从 1 到 n 随机选择一个数字。
 * 请你猜选出的是哪个数字。
 * 如果你猜错了，我会告诉你，你猜测的数字比我选出的数字是大了还是小了。
 * 你可以通过调用一个预先定义好的接口 int guess(int num) 来获取猜测结果，返回值一共有 3 种可能的情况（-1，1 或 0）：
 * -1：我选出的数字比你猜的数字小 pick < num
 * 1：我选出的数字比你猜的数字大 pick > num
 * 0：我选出的数字和你猜的数字一样。恭喜！你猜对了！pick == num
 * 返回我选出的数字。
 * <p>
 * 输入：n = 10, pick = 6
 * 输出：6
 * <p>
 * 输入：n = 1, pick = 1
 * 输出：1
 * <p>
 * 输入：n = 2, pick = 1
 * 输出：1
 * <p>
 * 输入：n = 2, pick = 2
 * 输出：2
 * <p>
 * 1 <= n <= 2^31 - 1
 * 1 <= pick <= n
 */
public class Problem374 {
    public static void main(String[] args) {
        Problem374 problem374 = new Problem374();
        int n = 10;
        System.out.println();
    }

    /**
     * 二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int guessNumber(int n) {
        int pick = (int) (Math.random() * n) + 1;

        int left = 1;
        int right = n;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            if (mid == pick) {
                return mid;
            } else if (mid < pick) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }
}
