package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/3/18 10:13
 * @Author zsy
 * @Description 打印从1到最大的n位数 类比Problem357、Problem386、Problem440 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem89、Problem90、Problem97、Problem216、Problem301、Problem377、Problem491、Problem679、Problem698、Offer38
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
        int n = 2;
//        System.out.println(Arrays.toString(offer17.printNumbers(n)));
        System.out.println(offer17.printNumbers2(n));
    }

    /**
     * 未考虑大数溢出的情况
     * 时间复杂度O(10^n)，空间复杂度O(1)
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
     * 回溯+剪枝
     * 考虑大数溢出的情况，用字符串模拟
     * 时间复杂度O(10^n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public List<String> printNumbers2(int n) {
        List<String> list = new ArrayList<>();

        backtrack(0, n, new StringBuilder(), list);

        return list;
    }

    /**
     * 回溯+剪枝
     * 全排列树，从最高位向低位拼接0~9，考虑首位0的情况
     *
     * @param t    当前结果第index位
     * @param n    结果集合上限，最大的 n 位十进制数
     * @param sb   每个结果
     * @param list 结果集合
     */
    public void backtrack(int t, int n, StringBuilder sb, List<String> list) {
        if (t == n) {
            //如果拼接的数为空，即当前数为0，则不添加
            if (sb.length() != 0) {
                list.add(sb.toString());
            }
            return;
        }

        for (int i = 0; i <= 9; i++) {
            //当前拼接的数为0，并且当前数字为0，则不能添加前导0
            if (i == 0 && sb.length() == 0) {
                backtrack(t + 1, n, sb, list);
            } else {
                sb.append(i);
                backtrack(t + 1, n, sb, list);
                sb.delete(sb.length() - 1, sb.length());
            }
        }
    }
}
