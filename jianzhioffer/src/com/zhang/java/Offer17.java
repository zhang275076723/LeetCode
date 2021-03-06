package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2022/3/18 10:13
 * @Author zsy
 * @Description 打印从1到最大的n位数
 * 输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。
 * 比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。
 * <p>
 * 输入: n = 1
 * 输出: [1,2,3,4,5,6,7,8,9]
 * <p>
 * 用返回一个整数列表来代替打印
 * n 为正整数
 */
public class Offer17 {
    public static void main(String[] args) {
        Offer17 offer17 = new Offer17();
        System.out.println(Arrays.toString(offer17.printNumbers(3)));
        System.out.println(offer17.printNumbers2(3));
    }

    /**
     * 未考虑大数溢出的情况
     * 时间复杂度O(10^n)，空间复杂的O(1)
     *
     * @param n
     * @return
     */
    public int[] printNumbers(int n) {
        //因为是最大n位十进制数，所以要把最小n+1位十进制数1xxxx0去掉
        int size = (int) Math.pow(10, n) - 1;
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = i + 1;
        }

        return result;
    }

    /**
     * 考虑大数溢出的情况，用字符串模拟，排列树
     * 时间复杂度O(10^n)，空间复杂的O(n)
     *
     * @param n
     * @return
     */
    public List<String> printNumbers2(int n) {
        List<String> list = new ArrayList<>();

        backtrack(n, 0, list, new StringBuilder());

        return list;
    }

    /**
     * 回溯+剪枝
     * 全排列树，从最高位向低位拼接0~9，考虑首位0的情况
     *
     * @param n     结果集合上限，最大的 n 位十进制数
     * @param index 当前结果第index位
     * @param list  结果集合
     * @param sb    每个结果
     */
    public void backtrack(int n, int index, List<String> list, StringBuilder sb) {
        //当index为从左到右第n位时，剪枝
        if (index == n) {
            //如果当前数为空，则不添加
            if (sb.length() != 0) {
                list.add(sb.toString());
            }

            return;
        }

        for (int i = 0; i <= 9; i++) {
            //如果当前位为0，且只有一位，则不添加，继续判断下一位
            if (i == 0 && sb.length() == 0) {
                backtrack(n, index + 1, list, sb);
                continue;
            }

            sb.append(i);
            backtrack(n, index + 1, list, sb);
            sb.delete(sb.length() - 1, sb.length());
        }
    }
}
