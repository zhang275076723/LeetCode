package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/5/19 19:08
 * @Author zsy
 * @Description 加一 类比Problem2、Problem67、Problem369、Problem415、Problem445、Problem989
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 * <p>
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 * <p>
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 * <p>
 * 输入：digits = [0]
 * 输出：[1]
 * <p>
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 */
public class Problem66 {
    public static void main(String[] args) {
        Problem66 problem66 = new Problem66();
        int[] digits = {4, 3, 2, 9, 9};
        System.out.println(Arrays.toString(problem66.plusOne(digits)));
    }

    /**
     * 模拟
     * 末尾元素加1，由后往前遍历
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param digits
     * @return
     */
    public int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) {
            return digits;
        }

        int i = digits.length - 1;
        //当前位的进位
        int carry = 0;
        //末尾元素加1
        digits[i]++;

        while (i >= 0) {
            digits[i] = digits[i] + carry;
            carry = digits[i] / 10;
            digits[i] = digits[i] % 10;

            //当前位进位为0，则高位元素不需要修改
            if (carry == 0) {
                break;
            }

            i--;
        }

        //最高位有进位
        if (i == -1) {
            int[] result = new int[digits.length + 1];
            result[0] = carry;

            for (int j = 1; j < result.length; j++) {
                result[j] = digits[j - 1];
            }

            return result;
        } else {
            return digits;
        }
    }
}
