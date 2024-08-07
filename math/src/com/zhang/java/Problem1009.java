package com.zhang.java;

/**
 * @Date 2024/6/19 08:12
 * @Author zsy
 * @Description 十进制整数的反码 类比Problem476 位运算类比
 * 每个非负整数 N 都有其二进制表示。
 * 例如， 5 可以被表示为二进制 "101"，11 可以用二进制 "1011" 表示，依此类推。
 * 注意，除 N = 0 外，任何二进制表示中都不含前导零。
 * 二进制的反码表示是将每个 1 改为 0 且每个 0 变为 1。例如，二进制数 "101" 的二进制反码为 "010"。
 * 给你一个十进制数 N，请你返回其二进制表示的反码所对应的十进制整数。
 * <p>
 * 输入：5
 * 输出：2
 * 解释：5 的二进制表示为 "101"，其二进制反码为 "010"，也就是十进制中的 2 。
 * <p>
 * 输入：7
 * 输出：0
 * 解释：7 的二进制表示为 "111"，其二进制反码为 "000"，也就是十进制中的 0 。
 * <p>
 * 输入：10
 * 输出：5
 * 解释：10 的二进制表示为 "1010"，其二进制反码为 "0101"，也就是十进制中的 5 。
 * <p>
 * 0 <= N < 10^9
 */
public class Problem1009 {
    public static void main(String[] args) {
        Problem1009 problem1009 = new Problem1009();
        int n = 5;
        System.out.println(problem1009.bitwiseComplement(n));
    }

    /**
     * 位运算
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int bitwiseComplement(int n) {
        //0的补码为1
        if (n == 0) {
            return 1;
        }

        //n二进制表示的最高位为1的下标索引
        int index = -1;

        //n为正数，不需要考虑最高位符号位
        for (int i = 30; i >= 0; i--) {
            if (((n >>> i) & 1) == 1) {
                index = i;
                break;
            }
        }

        int result = 0;

        for (int i = index; i >= 0; i--) {
            //n当前位的值
            int cur = (n >>> i) & 1;
            result = (result << 1) + (cur ^ 1);
        }

        return result;
    }
}
