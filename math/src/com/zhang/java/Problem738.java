package com.zhang.java;

/**
 * @Date 2022/9/10 8:40
 * @Author zsy
 * @Description 单调递增的数字 类比Problem31、Problem556、Problem670、Problem1323、Problem1328、Problem1842、Problem2231
 * 当且仅当每个相邻位数上的数字 x 和 y 满足 x <= y 时，我们称这个整数是单调递增的。
 * 给定一个整数 n ，返回 小于或等于 n 的最大数字，且数字呈 单调递增 。
 * <p>
 * 输入: n = 10
 * 输出: 9
 * <p>
 * 输入: n = 1234
 * 输出: 1234
 * <p>
 * 输入: n = 332
 * 输出: 299
 * <p>
 * 0 <= n <= 10^9
 */
public class Problem738 {
    public static void main(String[] args) {
        Problem738 problem738 = new Problem738();
        int n = 1332;
        System.out.println(problem738.monotoneIncreasingDigits(n));
    }

    /**
     * 模拟
     * 从左往右遍历，找到从arr[0]开始的递增数组arr[0]-arr[i]，再往前找和arr[i]连续相等的第一个元素arr[j]，
     * arr[j]减1，arr[j]之后所有元素均赋值为9
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param n
     * @return
     */
    public int monotoneIncreasingDigits(int n) {
        if (n <= 9) {
            return n;
        }

        //n的char数组
        char[] arr = (n + "").toCharArray();

        int i = 0;

        //找从arr[0]开始的递增数组arr[0]-arr[i]
        while (i + 1 < arr.length && arr[i] <= arr[i + 1]) {
            i++;
        }

        //n已经为单调递增的数字，直接返回
        if (i == arr.length - 1) {
            return n;
        }

        //往前找和arr[i]连续相等的第一个元素arr[j]，保证arr[i]减1之后前面元素都大于等于arr[i]，保证单调递增
        while (i > 0 && arr[i] == arr[i - 1]) {
            i--;
        }

        //arr[i]减1
        arr[i]--;

        //arr[i]之后所有元素均赋值为9
        for (int j = i + 1; j < arr.length; j++) {
            arr[j] = '9';
        }

        int result = 0;

        for (int j = 0; j < arr.length; j++) {
            result = result * 10 + (arr[j] - '0');
        }

        return result;
    }
}
