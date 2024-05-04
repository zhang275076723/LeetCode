package com.zhang.java;

/**
 * @Date 2023/9/12 08:07
 * @Author zsy
 * @Description 6 和 9 组成的最大数字 类比Problem31、Problem556、Problem670、Problem738
 * 给你一个仅由数字 6 和 9 组成的正整数 num。
 * 你最多只能翻转一位数字，将 6 变成 9，或者把 9 变成 6 。
 * 请返回你可以得到的最大数字。
 * <p>
 * 输入：num = 9669
 * 输出：9969
 * 解释：
 * 改变第一位数字可以得到 6669 。
 * 改变第二位数字可以得到 9969 。
 * 改变第三位数字可以得到 9699 。
 * 改变第四位数字可以得到 9666 。
 * 其中最大的数字是 9969 。
 * <p>
 * 输入：num = 9996
 * 输出：9999
 * 解释：将最后一位从 6 变到 9，其结果 9999 是最大的数。
 * <p>
 * 输入：num = 9999
 * 输出：9999
 * 解释：无需改变就已经是最大的数字了。
 * <p>
 * 1 <= num <= 10^4
 * num 每一位上的数字都是 6 或者 9 。
 */
public class Problem1323 {
    public static void main(String[] args) {
        Problem1323 problem1323 = new Problem1323();
        int num = 9669;
        System.out.println(problem1323.maximum69Number(num));
    }

    /**
     * 模拟
     * 从高位向低位遍历，找第一个6，将其转换为9，得到最大数字
     * 时间复杂度O(log(num))，空间复杂度O(log(num))
     *
     * @param num
     * @return
     */
    public int maximum69Number(int num) {
        char[] strArr = (num + "").toCharArray();

        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i] == '6') {
                strArr[i] = '9';
                return Integer.parseInt(new String(strArr));
            }
        }

        //遍历结束，则num都为9，不需要修改，已经是最大的数字，直接返回
        return num;
    }
}
