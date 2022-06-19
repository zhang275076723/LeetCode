package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/5/19 19:08
 * @Author zsy
 * @Description 加一 类比Problem369、Problem415
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
        int[] digits = {4, 3, 2, 1, 0};
        System.out.println(Arrays.toString(problem66.plusOne(digits)));
    }

    /**
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param digits
     * @return
     */
    public int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) {
            return digits;
        }

        digits[digits.length - 1]++;
        int index = digits.length - 1;
        while (digits[index] > 9) {
            //当前位置0
            digits[index] = 0;
            index--;
            if (index < 0) {
                break;
            }
            //高位进1
            digits[index]++;
        }

        //加一之后还是n位
        if (index != -1) {
            return digits;
        }

        //加一之后变成n+1位
        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }
}
