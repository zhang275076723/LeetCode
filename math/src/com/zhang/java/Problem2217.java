package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/4/8 08:49
 * @Author zsy
 * @Description 找到指定长度的回文数 类比Problem479、Problem564、Problem866、Problem1842、Problem2967 回文类比
 * 给你一个整数数组 queries 和一个 正 整数 intLength ，请你返回一个数组 answer ，
 * 其中 answer[i] 是长度为 intLength 的 正回文数 中第 queries[i] 小的数字，
 * 如果不存在这样的回文数，则为 -1 。
 * 回文数 指的是从前往后和从后往前读一模一样的数字。
 * 回文数不能有前导 0 。
 * <p>
 * 输入：queries = [1,2,3,4,5,90], intLength = 3
 * 输出：[101,111,121,131,141,999]
 * 解释：
 * 长度为 3 的最小回文数依次是：
 * 101, 111, 121, 131, 141, 151, 161, 171, 181, 191, 202, ...
 * 第 90 个长度为 3 的回文数是 999 。
 * <p>
 * 输入：queries = [2,4,6], intLength = 4
 * 输出：[1111,1331,1551]
 * 解释：
 * 长度为 4 的前 6 个回文数是：
 * 1001, 1111, 1221, 1331, 1441 和 1551 。
 * <p>
 * 1 <= queries.length <= 5 * 10^4
 * 1 <= queries[i] <= 10^9
 * 1 <= intLength <= 15
 */
public class Problem2217 {
    public static void main(String[] args) {
        Problem2217 problem2217 = new Problem2217();
        int[] queries = {2, 4, 6};
        int intLength = 4;
        System.out.println(Arrays.toString(problem2217.kthPalindrome(queries, intLength)));
    }

    /**
     * 模拟
     * 长度为n的回文只需要考虑前一半，从1000这样的数字开始，每次加1，拼接上后半部分得到n位回文
     * 如果n为奇数，最小的前一半值为10^(n/2)，最大的前一半值为10*10^(n/2)-1，即共9*10^(n/2)个回文
     * 如果n为偶数，最小的前一半值为10^(n/2-1)，最大的前一半值为10*10^(n/2-1)-1，即共9*10^(n/2-1)个回文
     * 时间复杂度O(n*intLength)，空间复杂度O(1)
     *
     * @param queries
     * @param intLength
     * @return
     */
    public long[] kthPalindrome(int[] queries, int intLength) {
        long[] result = new long[queries.length];
        //所有长度为n的回文个数
        int maxCount;

        //n为奇数，最小的前一半值为10^(n/2)，最大的前一半值为10*10^(n/2)-1，即共9*10^(n/2)个回文
        if (intLength % 2 == 1) {
            maxCount = 9 * quickPow(10, intLength / 2);
        } else {
            //n为偶数，最小的前一半值为10^(n/2-1)，最大的前一半值为10*10^(n/2-1)-1，即共9*10^(n/2-1)个回文
            maxCount = 9 * quickPow(10, intLength / 2 - 1);
        }

        for (int i = 0; i < queries.length; i++) {
            //不存在长度为intLength的第queries[i]小回文，则赋值为-1
            if (queries[i] > maxCount) {
                result[i] = -1;
            } else {
                //存在长度为intLength的第queries[i]小回文，第1小回文从1000这样的数字开始，每次加1，拼接上后半部分得到n位回文

                StringBuilder sb = new StringBuilder();
                //长度为intLength的第queries[i]小回文的前一半值
                int preHalf;

                //n为奇数，最小的前一半值为10^(n/2)，第queries[i]小回文需要加上queries[i]-1
                if (intLength % 2 == 1) {
                    preHalf = quickPow(10, intLength / 2) + queries[i] - 1;
                } else {
                    //n为偶数，最小的前一半值为10^(n/2-1)，第queries[i]小回文需要加上queries[i]-1
                    preHalf = quickPow(10, intLength / 2 - 1) + queries[i] - 1;
                }

                //拼接前一半值
                sb.append(preHalf);

                //n为奇数，拼接后一半时不考虑preHalf的最后一位
                if (intLength % 2 == 1) {
                    preHalf = preHalf / 10;
                }

                //拼接后一半值
                while (preHalf != 0) {
                    sb.append(preHalf % 10);
                    preHalf = preHalf / 10;
                }

                result[i] = Long.parseLong(sb.toString());
            }
        }

        return result;
    }

    /**
     * 非递归快速幂
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param a
     * @param n
     * @return
     */
    private int quickPow(int a, int n) {
        int result = 1;

        while (n != 0) {
            if ((n & 1) == 1) {
                result = result * a;
            }

            a = a * a;
            n = n >>> 1;
        }

        return result;
    }
}
