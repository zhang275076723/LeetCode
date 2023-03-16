package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Date 2023/3/16 10:44
 * @Author zsy
 * @Description 数组形式的整数加法 类比Problem2、Problem66、Problem67、Problem369、Problem415、Problem445
 * 整数的 数组形式 num 是按照从左到右的顺序表示其数字的数组。
 * 例如，对于 num = 1321 ，数组形式是 [1,3,2,1] 。
 * 给定 num ，整数的 数组形式 ，和整数 k ，返回 整数 num + k 的 数组形式 。
 * <p>
 * 输入：num = [1,2,0,0], k = 34
 * 输出：[1,2,3,4]
 * 解释：1200 + 34 = 1234
 * <p>
 * 输入：num = [2,7,4], k = 181
 * 输出：[4,5,5]
 * 解释：274 + 181 = 455
 * <p>
 * 输入：num = [2,1,5], k = 806
 * 输出：[1,0,2,1]
 * 解释：215 + 806 = 1021
 * <p>
 * 1 <= num.length <= 10^4
 * 0 <= num[i] <= 9
 * num 不包含任何前导零，除了零本身
 * 1 <= k <= 10^4
 */
public class Problem989 {
    public static void main(String[] args) {
        Problem989 problem989 = new Problem989();
        int[] num = {2, 1, 5};
        int k = 806;
        System.out.println(problem989.addToArrayForm(num, k));
    }

    /**
     * 双指针，类似归并排序中的合并操作
     * 从后往前相加，最后得到的list集合需要反转
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param num
     * @param k
     * @return
     */
    public List<Integer> addToArrayForm(int[] num, int k) {
        if (num == null || num.length == 0) {
            return new ArrayList<>(Arrays.asList(k));
        }

        List<Integer> list = new ArrayList<>();
        int i = num.length - 1;
        //当前位之和
        int cur = 0;
        //当前位的进行
        int carry = 0;

        while (i >= 0 && k != 0) {
            cur = num[i] + k % 10 + carry;
            carry = cur / 10;
            cur = cur % 10;

            list.add(cur);

            i--;
            k = k / 10;
        }

        while (i >= 0) {
            cur = num[i] + carry;
            carry = cur / 10;
            cur = cur % 10;

            list.add(cur);

            i--;
        }

        while (k != 0) {
            cur = k % 10 + carry;
            carry = cur / 10;
            cur = cur % 10;

            list.add(cur);

            k = k / 10;
        }

        if (carry != 0) {
            list.add(carry);
        }

        //因为逆序相加，所以需要反转
        Collections.reverse(list);

        return list;
    }
}
