package com.zhang.java;

/**
 * @Date 2022/11/8 12:53
 * @Author zsy
 * @Description 二进制求和 类比Problem2、Problem66、Problem369、Problem415、Problem445、Problem989
 * 给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。
 * <p>
 * 输入:a = "11", b = "1"
 * 输出："100"
 * <p>
 * 输入：a = "1010", b = "1011"
 * 输出："10101"
 * <p>
 * 1 <= a.length, b.length <= 10^4
 * a 和 b 仅由字符 '0' 或 '1' 组成
 * 字符串如果不是 "0" ，就不含前导零
 */
public class Problem67 {
    public static void main(String[] args) {
        Problem67 problem67 = new Problem67();
        String a = "1010";
        String b = "1011";
        System.out.println(problem67.addBinary(a, b));
    }

    /**
     * 双指针，类似归并排序中的合并操作
     * 时间复杂度O(max(m,n))，空间复杂度O(Math.max(m,n)) (m=a.length(), n=b.length())
     *
     * @param a
     * @param b
     * @return
     */
    public String addBinary(String a, String b) {
        if (a == null || a.length() == 0) {
            return b;
        }

        if (b == null || b.length() == 0) {
            return a;
        }

        StringBuilder sb = new StringBuilder();

        int i = a.length() - 1;
        int j = b.length() - 1;
        //当前位的进位
        int carry = 0;
        //当前位之和
        int cur;

        while (i >= 0 && j >= 0) {
            cur = (a.charAt(i) - '0') + (b.charAt(j) - '0') + carry;
            carry = cur / 2;
            cur = cur % 2;

            sb.append(cur);

            i--;
            j--;
        }

        while (i >= 0) {
            cur = (a.charAt(i) - '0') + carry;
            carry = cur / 2;
            cur = cur % 2;

            sb.append(cur);

            i--;
        }

        while (j >= 0) {
            cur = (b.charAt(j) - '0') + carry;
            carry = cur / 2;
            cur = cur % 2;

            sb.append(cur);

            j--;
        }

        //最高位进位处理
        if (carry != 0) {
            sb.append(carry);
        }

        //因为是从后往前遍历相加，所以需要反转
        return sb.reverse().toString();
    }
}
