package com.zhang.java;

import com.sun.org.apache.regexp.internal.RE;

import java.util.*;

/**
 * @Date 2022/3/27 10:22
 * @Author zsy
 * @Description 输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 * 拼接起来的数字可能会有前导 0，最后结果不需要去掉前导 0
 * <p>
 * 输入: [10,2]
 * 输出: "102"
 * <p>
 * 输入: [3,30,34,5,9]
 * 输出: "3033459"
 */
public class Offer45 {
    public static void main(String[] args) {
        Offer45 offer45 = new Offer45();
        int[] nums = {3, 30, 34, 5, 9};
        System.out.println(offer45.minNumber(nums));
    }

    /**
     * 使用list集合对nums元素根据比较器排序，或者手写快排对数组排序
     * 期望时间复杂度O(nlogn)(因为有排序)，空间复杂度O(n)
     * 例如：3和30应该排序为30和3，因为303<330
     *
     * @param nums
     * @return
     */
    public String minNumber(int[] nums) {
//        List<String> list = new ArrayList<>();
//        for (int num : nums) {
//            list.add(String.valueOf(num));
//        }

//        list.sort((o1, o2) -> {
//            String s1 = o1 + o2;
//            String s2 = o2 + o1;
//            return s1.compareTo(s2);
//        });

        String[] strings = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strings[i] = String.valueOf(nums[i]);
        }

        quickSort(strings, 0, strings.length - 1);

        StringBuilder sb = new StringBuilder();
        for (String str : strings) {
            sb.append(str);
        }
        return sb.toString();
    }

    public void quickSort(String[] strings, int left, int right) {
        if (left < right) {
            int partition = partition(strings, left, right);
            quickSort(strings, left, partition - 1);
            quickSort(strings, partition + 1, right);
        }
    }

    public int partition(String[] strings, int left, int right) {
        String temp = strings[left];
        while (left < right) {
            while (left < right && (strings[right] + temp).compareTo(temp + strings[right]) >= 0) {
                right--;
            }
            strings[left] = strings[right];
            while (left < right && (strings[left] + temp).compareTo(temp + strings[left]) <= 0) {
                left++;
            }
            strings[right] = strings[left];
        }
        strings[left] = temp;
        return left;
    }
}
