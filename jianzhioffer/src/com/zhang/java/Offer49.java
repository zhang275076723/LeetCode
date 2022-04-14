package com.zhang.java;

/**
 * @Date 2022/3/30 9:41
 * @Author zsy
 * @Description 我们把只包含质因子 2、3 和 5 的数称作丑数（Ugly Number）。求按从小到大的顺序的第 n 个丑数。
 * <p>
 * 输入: n = 10
 * 输出: 12
 * 解释: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 是前 10 个丑数。
 */
public class Offer49 {
    public static void main(String[] args) {
        Offer49 offer49 = new Offer49();
        System.out.println(offer49.nthUglyNumber(10));
    }

    /**
     * 三指针，分别指向丑数数组乘上2、3、5的数组，时间复杂度O(n)，空间复杂度O(n)
     * 1, 2, 3, 4, 5, 6, 8, 9, 10, 12是丑数数组前几项
     * 乘上2的数组：1*2, 2*2, 3*2, 4*2, 5*2, 6*2, 8*2, 9*2, 10*2, 12*2
     * 乘上3的数组：1*3, 2*3, 3*3, 4*3, 5*3, 6*3, 8*3, 9*3, 10*3, 12*3
     * 乘上5的数组：1*5, 2*5, 3*5, 4*5, 5*5, 6*5, 8*5, 9*5, 10*5, 12*5
     *
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        if (n <= 0) {
            return -1;
        }

        int[] ugly = new int[n];
        ugly[0] = 1;
        //i、j、k分别表示丑数数组下标索引，乘上2、3、5
        int i = 0;
        int j = 0;
        int k = 0;

        for (int l = 1; l < n; l++) {
            //取三者最小值即为当前丑数
            int nextUgly = Math.min(ugly[i] * 2, Math.min(ugly[j] * 3, ugly[k] * 5));
            ugly[l] = nextUgly;
            if (nextUgly == ugly[i] * 2) {
                i++;
            }
            if (nextUgly == ugly[j] * 3) {
                j++;
            }
            if (nextUgly == ugly[k] * 5) {
                k++;
            }
        }

        return ugly[n - 1];
    }
}
