package com.zhang.java;

/**
 * @Date 2022/9/10 8:40
 * @Author zsy
 * @Description 单调递增的数字 类比Problem31、Problem556、Problem670、Problem1323、Problem1328、Problem1842、Problem1850、Problem2231
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
     * 从前往后遍历，找从numArr[0]开始的递增数组numArr[0]-numArr[i]，再往前找和numArr[i]连续相等的第一个元素numArr[j]，
     * numArr[j]减1，numArr[j+1]-numArr[numArr.length-1]均赋值为9
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param n
     * @return
     */
    public int monotoneIncreasingDigits(int n) {
        //n的char数组
        char[] numArr = (n + "").toCharArray();
        int i = 0;

        //找从numArr[0]开始的递增数组numArr[0]-numArr[i]
        while (i + 1 < numArr.length && numArr[i] <= numArr[i + 1]) {
            i++;
        }

        //numArr为单调递增的数字，直接返回n
        if (i == numArr.length - 1) {
            return n;
        }

        //往前找和numArr[i]连续相等的第一个元素numArr[j]
        while (i > 0 && numArr[i] == numArr[i - 1]) {
            i--;
        }

        //numArr[i]减1
        numArr[i]--;

        //numArr[i+1]-numArr[numArr.length-1]均赋值为9
        for (int j = i + 1; j < numArr.length; j++) {
            numArr[j] = '9';
        }

        int result = 0;

        for (char c : numArr) {
            result = result * 10 + (c - '0');
        }

        return result;
    }
}
