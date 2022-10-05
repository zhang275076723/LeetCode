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
        System.out.println(Arrays.toString(random1_100.random(10)));
        System.out.println(Arrays.toString(random1_100.random(10)));
    }

    /**
     * 使用随机数组，每次随机选择未被选中的随机数，与末尾元素交换
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int[] random(int n) {
        int[] result = new int[n];

        //1-n随机数组赋初值
        for (int i = 0; i < n; i++) {
            result[i] = i + 1;
        }

        //从后往前，每次选择随机元素与末尾元素交换
        for (int i = result.length - 1; i > 0; i--) {
            int randomIndex = new Random().nextInt(i + 1);

            //将当前随机数和末尾随机数交换，下次取随机数时不会取到重复随机数
            swap(result, i, randomIndex);
        }

        return result;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
