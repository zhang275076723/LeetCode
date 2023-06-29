package com.zhang.java;

/**
 * @Date 2023/6/25 09:54
 * @Author zsy
 * @Description 平方数之和 双指针类比Problem15、Problem16、Problem18、Problem456、Problem532、Problem611
 * 给定一个非负整数 c ，你要判断是否存在两个整数 a 和 b，使得 a^2 + b^2 = c 。
 * <p>
 * 输入：c = 5
 * 输出：true
 * 解释：1 * 1 + 2 * 2 = 5
 * <p>
 * 输入：c = 3
 * 输出：false
 * <p>
 * 0 <= c <= 2^31 - 1
 */
public class Problem633 {
    public static void main(String[] args) {
        Problem633 problem633 = new Problem633();
        int c = 4;
        System.out.println(problem633.judgeSquareSum(c));
        System.out.println(problem633.judgeSquareSum2(c));
    }

    /**
     * 暴力
     * i从0-c^(1/2)判断c-i^2是否是完全平方数
     * 时间复杂度O(c^(1/2))，空间复杂度O(1)
     *
     * @param c
     * @return
     */
    public boolean judgeSquareSum(int c) {
        for (int i = 0; i <= Math.sqrt(c); i++) {
            int num = c - i * i;
            int sqrt = (int) Math.sqrt(num);

            //判断num是否是完全平方数
            if (sqrt * sqrt == num) {
                return true;
            }
        }

        return false;
    }

    /**
     * 双指针
     * 左指针起始为0，右指针起始为c^(1/2)，判断c和左右指针的平方和的大小关系，如果相等，则c能够由两个整数平方和组成；
     * 如果c大于左右指针的平方和，则左指针右移；如果c小于左右指针的平方和，则右指针左移
     * 时间复杂度O(c^(1/2))，空间复杂度O(1)
     *
     * @param c
     * @return
     */
    public boolean judgeSquareSum2(int c) {
        int left = 0;
        int right = (int) Math.sqrt(c);

        while (left <= right) {
            //使用long，避免int溢出
            long result = (long) left * left + (long) right * right;

            if (result == c) {
                return true;
            } else if (result < c) {
                left++;
            } else {
                right--;
            }
        }

        return false;
    }
}
