package com.zhang.java;

/**
 * @Date 2023/5/9 08:43
 * @Author zsy
 * @Description 字符串相减 大数减法 字节面试题 加减乘除类比Problem29、Problem43、Problem415
 * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的差。
 * <p>
 * 输入：num1 = "11", num2 = "123"
 * 输出："-112"
 * <p>
 * num1 和num2 都只会包含数字 0-9
 * num1 和num2 都不包含任何前导零
 * 你不能使用任何內建 BigInteger 库
 */
public class BigNumberSubtract {
    public static void main(String[] args) {
        BigNumberSubtract bigNumberSubtract = new BigNumberSubtract();
        String num1 = "11";
        String num2 = "123";
        System.out.println(bigNumberSubtract.subtract(num1, num2));
    }

    /**
     * 双指针，类似归并排序中的合并操作
     * 从低位向高位相减
     * 时间复杂度O(max(m,n))，空间复杂度O(max(m,n))
     *
     * @param num1
     * @param num2
     * @return
     */
    public String subtract(String num1, String num2) {
        //num1和num2相等，相减结果为0
        if (num1.equals(num2)) {
            return "0";
        }

        //num1为0，相减结果为-num2
        if ("0".equals(num1)) {
            return "-" + num2;
        }

        //num2为0，相减结果为num1
        if ("0".equals(num2)) {
            return num1;
        }

        //相减结果标志位，1为正，-1为负
        int sign = 1;

        //num1长度小于num2长度，或者num1和num2长度相同的情况下，num1小于num2，相减结果为负数
        if (num1.length() < num2.length()) {
            sign = -1;
        } else if (num1.length() == num2.length()) {
            for (int i = 0; i < num1.length(); i++) {
                if (num1.charAt(i) < num2.charAt(i)) {
                    sign = -1;
                    break;
                }
            }
        }

        //相减结果为负数，两数交换，大数减小数方便处理，最后结果添加负号
        if (sign == -1) {
            String temp = num1;
            num1 = num2;
            num2 = temp;
        }

        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        //前一位的借位
        int borrow = 0;
        //当前位相减结果
        int cur;

        while (i >= 0 && j >= 0) {
            //当前位相减结果，有可能为负数
            cur = (num1.charAt(i) - '0') - borrow - (num2.charAt(j) - '0');

            //当前位小于0，则需要向高位借1位
            if (cur < 0) {
                borrow = 1;
                cur = cur + 10;
                sb.append(cur);
            } else {
                borrow = 0;
                sb.append(cur);
            }

            i--;
            j--;
        }

        while (i >= 0) {
            cur = (num1.charAt(i) - '0') - borrow;

            //当前位小于0，则需要向高位借1位
            if (cur < 0) {
                borrow = 1;
                cur = cur + 10;
                sb.append(cur);
            } else {
                borrow = 0;
                sb.append(cur);
            }

            i--;
        }

        //num1和num2相减结果
        String result = sb.reverse().toString();

        int index = 0;

        //删除前导0
        while (index < result.length() && result.charAt(index) == '0') {
            index++;
        }

        return sign == 1 ? result.substring(index) : "-" + result.substring(index);
    }
}
