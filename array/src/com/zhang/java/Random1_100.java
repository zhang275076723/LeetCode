package com.zhang.java;

import java.util.Arrays;
import java.util.Random;

/**
 * @Date 2022/7/25 6:50
 * @Author zsy
 * @Description 生成100个1-100不重复的随机数 蔚来面试题 类比Problem384、Problem519、Problem710
 */
public class Random1_100 {
    public static void main(String[] args) {
        Random1_100 random1_100 = new Random1_100();
        System.out.println(Arrays.toString(random1_100.random(10)));
        System.out.println(Arrays.toString(random1_100.random(10)));
    }

    /**
     * 生成n个1-n不重复的随机数
     * 使用随机数组，每次选[0,i]的随机下标索引和当前下标索引i交换
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int[] random(int n) {
        int[] result = new int[n];

        //1-n随机数组赋初值，每次选一个随机下标索引和当前元素交换
        for (int i = 0; i < n; i++) {
            result[i] = i + 1;
            int index = new Random().nextInt(i + 1);
            swap(result, i, index);
        }

        return result;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
