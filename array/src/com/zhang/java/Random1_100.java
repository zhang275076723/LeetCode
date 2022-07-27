package com.zhang.java;

import java.util.Arrays;
import java.util.Random;

/**
 * @Date 2022/7/25 6:50
 * @Author zsy
 * @Description 生成100个1-100不重复的随机数 蔚来面试题
 */
public class Random1_100 {
    public static void main(String[] args) {
        Random1_100 random1_100 = new Random1_100();
        System.out.println(Arrays.toString(random1_100.random(100)));
    }

    /**
     * 使用随机数组
     * 每次随机选择未被选中的随机数，与末尾元素交换
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    private int[] random(int n) {
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[i] = i + 1;
        }

        for (int i = 0; i < result.length; i++) {
            int index = new Random().nextInt(n - i);

            //将当前随机数和末尾随机数交换，下次取随机数时不会取到重复随机数
            int temp = result[index];
            result[index] = result[n - i - 1];
            result[n - i - 1] = temp;
        }

        return result;
    }
}
