package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2022/3/18 10:13
 * @Author zsy
 * @Description 输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。
 * <p>
 * 输入: n = 1
 * 输出: [1,2,3,4,5,6,7,8,9]
 */
public class Offer17 {
    public static void main(String[] args) {
        Offer17 offer17 = new Offer17();
        System.out.println(Arrays.toString(offer17.printNumbers(3)));
        System.out.println(offer17.printNumbers2(3));
    }

    /**
     * 未考虑大数溢出的情况，时间复杂度O(10^n)，空间复杂的O(10^n)
     *
     * @param n
     * @return
     */
    public int[] printNumbers(int n) {
        //因为是最大n位十进制数，所以要把最小n+1位十进制数1xxxx0去掉
        int size = (int) Math.pow(10, n) - 1;

        int[] a = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = i + 1;
        }

        return a;
    }

    /**
     * 考虑大数溢出的情况，用字符串模拟，排列树，时间复杂度O(10^n)，空间复杂的O(10^n)
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
     * 回溯法，全排列树，从最高位向低位拼接0~9，考虑首位0的情况
     *
     * @param n 结果集合上限，最大的 n 位十进制数
     * @param t 当前结果第t位
     * @param list 结果集合
     * @param stringBuilder 每个结果
     */
    public void backtrack(int n, int t, List<String> list, StringBuilder stringBuilder) {
        //当t为从左到右第n位时，剪枝
        if (t == n) {
            //如果当前数为空，则不添加
            if (stringBuilder.length() != 0) {
                list.add(stringBuilder.toString());
            }
            return;
        }

        for (int i = 0; i <= 9; i++) {
            //如果只有一位，且为0，则不添加
            if (i == 0 && stringBuilder.length() == 0) {
                backtrack(n, t + 1, list, stringBuilder);
                continue;
            }
            stringBuilder.append(i);
            backtrack(n, t + 1, list, stringBuilder);
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }
    }
}
