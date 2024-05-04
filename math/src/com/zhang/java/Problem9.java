package com.zhang.java;

/**
 * @Date 2022/9/1 17:13
 * @Author zsy
 * @Description 回文数 回文类比Problem5、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem564、Problem647、Problem680、Problem866、Problem1147、Problem1177、Problem1312、Problem1328、Problem1332、Problem1400、Problem1457、Problem1542、Problem1616、Problem1930、Problem2002、Problem2108、Problem2131、Problem2217、Problem2384、Problem2396、Problem2484、Problem2490、Problem2663、Problem2697、Problem2791、Problem3035
 * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
 * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * 例如，121 是回文，而 123 不是。
 * <p>
 * 输入：x = 121
 * 输出：true
 * <p>
 * 输入：x = -121
 * 输出：false
 * 解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * <p>
 * 输入：x = 10
 * 输出：false
 * 解释：从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * -2^31 <= x <= 2^31 - 1
 */
public class Problem9 {
    public static void main(String[] args) {
        Problem9 problem9 = new Problem9();
        System.out.println(problem9.isPalindrome(121));
        System.out.println(problem9.isPalindrome2(121));
    }

    /**
     * 将整数转换为字符串，双指针比较
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x == 0) {
            return true;
        }

        if (x < 0) {
            return false;
        }

        String s = String.valueOf(x);
        int left = 0;
        int right = s.length() - 1;

        while (left <= right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * 不将x转换为字符串，而是反转x，比较是否相等
     * x为回文数，所以不会溢出，不需要考虑溢出的情况
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param x
     * @return
     */
    public boolean isPalindrome2(int x) {
        if (x == 0) {
            return true;
        }

        if (x < 0) {
            return false;
        }

        //x反转之后的值
        int reverse = 0;
        int num = x;

        while (num != 0) {
            //回文数逆序不会溢出，不需要考虑溢出的情况
            reverse = reverse * 10 + num % 10;
            num = num / 10;
        }

        //判断反转之后是否和x相等
        return reverse == x;
    }
}
