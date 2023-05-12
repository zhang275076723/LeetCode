package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/4/9 10:05
 * @Author zsy
 * @Description 构建乘积数组 数组类比Problem53、Problem135、Problem152、Problem416、Problem581、Offer42 同Problem238
 * 给定一个数组 A[0,1,…,n-1]，请构建一个数组 B[0,1,…,n-1]，
 * 其中 B[i] 的值是数组 A 中除了下标 i 以外的元素的积, 即 B[i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1]。
 * 不能使用除法。
 * <p>
 * 输入: [1,2,3,4,5]
 * 输出: [120,60,40,30,24]
 * <p>
 * 所有元素乘积之和不会溢出 32 位整数
 * a.length <= 100000
 */
public class Offer66 {
    public static void main(String[] args) {
        Offer66 offer66 = new Offer66();
        int[] a = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(offer66.constructArr(a)));
        System.out.println(Arrays.toString(offer66.constructArr2(a)));
    }

    /**
     * 动态规划
     * left[i]：a[0]-a[i-1]所有元素乘积
     * right[i]：a[i+1]-a[a.length-1]所有元素乘积
     * result[i] = left[i] * right[i]
     * 时间复杂度O(n)，空间复杂的O(n)
     *
     * @param a
     * @return
     */
    public int[] constructArr(int[] a) {
        if (a == null || a.length == 0) {
            return a;
        }

        int[] left = new int[a.length];
        int[] right = new int[a.length];
        left[0] = 1;
        right[a.length - 1] = 1;

        for (int i = 1; i < a.length; i++) {
            left[i] = left[i - 1] * a[i - 1];
            right[a.length - i - 1] = right[a.length - i] * a[a.length - i];
        }

        int[] result = new int[a.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = left[i] * right[i];
        }

        return result;
    }

    /**
     * 动态规划优化，使用滚动数组
     * result结果数组作为left数组，与right相乘，获得最终结果
     * 时间复杂度O(n)，空间复杂的O(1)
     *
     * @param a
     * @return
     */
    public int[] constructArr2(int[] a) {
        if (a == null || a.length < 2) {
            return a;
        }

        int[] result = new int[a.length];
        result[0] = 1;

        //左前缀乘积
        for (int i = 1; i < a.length; i++) {
            result[i] = result[i - 1] * a[i - 1];
        }

        int right = a[a.length - 1];

        //右前缀乘积
        for (int i = a.length - 2; i >= 0; i--) {
            result[i] = result[i] * right;
            right = right * a[i];
        }

        return result;
    }
}