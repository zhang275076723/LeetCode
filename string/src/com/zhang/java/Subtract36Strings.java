package com.zhang.java;

/**
 * @Date 2023/5/15 09:17
 * @Author zsy
 * @Description 36进制减法 字节面试题 进制转换类比Problem12、Problem13、Problem168、Problem171、Add36Strings 加减乘除类比Problem29、Problem43、Problem415、Add36Strings、BigNumberSubtract
 * 36进制由0-9，a-z，共36个字符表示。
 * 要求按照减法规则计算出任意两个36进制正整数的差。
 * <p>
 * 输入：num1 = "48", num2 = "2x"
 * 输出："1b"
 * 解释：
 * 48=36*4+8=152
 * 2x=36*2+33=105
 * 152-105=47(即为36进制中的1b)
 * <p>
 * 要求：不允许使用先将36进制数字整体转为10进制，相减后再转回为36进制的做法
 */
public class Subtract36Strings {
    public static void main(String[] args) {
        Subtract36Strings subtract36Strings = new Subtract36Strings();
        String num1 = "48";
        String num2 = "2x";
        System.out.println(subtract36Strings.sub(num1, num2));
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
    public String sub(String num1, String num2) {
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
                } else if (num1.charAt(i) > num2.charAt(i)) {
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
            //num1当前字符c1
            char c1 = num1.charAt(i);
            //num2当前字符c2
            char c2 = num2.charAt(j);

            if (c1 >= '0' && c1 <= '9') {
                cur = c1 - '0';
            } else {
                cur = c1 - 'a' + 10;
            }

            if (c2 >= '0' && c2 <= '9') {
                cur = cur - (c2 - '0');
            } else {
                cur = cur - (c2 - 'a' + 10);
            }

            //减去借位borrow
            cur = cur - borrow;

            //当前位小于0，则需要向高位借1位
            if (cur < 0) {
                cur = cur + 36;
                borrow = 1;
            } else {
                borrow = 0;
            }

            //当前位相加之和小于10，直接拼接cur
            if (cur < 10) {
                sb.append(cur);
            } else {
                //当前位相加之和大于等于10，拼接36进制中的a-z
                sb.append((char) (cur + 'a' - 10));
            }

            i--;
            j--;
        }

        while (i >= 0) {
            char c = num1.charAt(i);

            if (c >= '0' && c <= '9') {
                cur = c - '0';
            } else {
                cur = c - 'a' + 10;
            }

            cur = cur - borrow;

            //当前位小于0，则需要向高位借1位
            if (cur < 0) {
                cur = cur + 36;
                borrow = 1;
            } else {
                borrow = 0;
            }

            //当前位相加之和小于10，直接拼接cur
            if (cur < 10) {
                sb.append(cur);
            } else {
                //当前位相加之和大于等于10，拼接36进制中的a-z
                sb.append((char) (cur + 'a' - 10));
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
