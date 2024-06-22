package com.zhang.java;

/**
 * @Date 2024/6/13 08:32
 * @Author zsy
 * @Description 数字的补数 类比Problem1009 位运算类比
 * 对整数的二进制表示取反（0 变 1 ，1 变 0）后，再转换为十进制表示，可以得到这个整数的补数。
 * 例如，整数 5 的二进制表示是 "101" ，取反后得到 "010" ，再转回十进制表示得到补数 2 。
 * 给你一个整数 num ，输出它的补数。
 * <p>
 * 输入：num = 5
 * 输出：2
 * 解释：5 的二进制表示为 101（没有前导零位），其补数为 010。所以你需要输出 2 。
 * <p>
 * 输入：num = 1
 * 输出：0
 * 解释：1 的二进制表示为 1（没有前导零位），其补数为 0。所以你需要输出 0 。
 * <p>
 * 1 <= num < 2^31
 */
public class Problem476 {
    public static void main(String[] args) {
        Problem476 problem476 = new Problem476();
        int num = 5;
        System.out.println(problem476.findComplement(num));
    }

    /**
     * 位运算
     * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public int findComplement(int num) {
        //num二进制表示的最高位为1的下标索引
        int index = -1;

        //num为正数，不需要考虑最高位符号位
        for (int i = 30; i >= 0; i--) {
            if (((num >>> i) & 1) == 1) {
                index = i;
                break;
            }
        }

        int result = 0;

        for (int i = index - 1; i >= 0; i--) {
            //num当前位的值
            int cur = (num >>> i) & 1;
            result = (result << 1) + (cur ^ 1);
        }

        return result;
    }
}
