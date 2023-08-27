package com.zhang.java;

/**
 * @Date 2023/5/14 09:45
 * @Author zsy
 * @Description 36进制加法 字节面试题 进制转换类比Problem12、Problem13、Problem168、Problem171、Subtract36Strings 加减乘除类比Problem29、Problem43、Problem415、BigNumberSubtract、Subtract36Strings
 * 36进制由0-9，a-z，共36个字符表示。
 * 要求按照加法规则计算出任意两个36进制正整数的和。
 * <p>
 * 输入：num1 = "1b", num2 = "2x"
 * 输出："48"
 * 解释：
 * 1b=36*1+11=47
 * 2x=36*2+33=105
 * 47+105=152(即为36进制中的48)
 * <p>
 * 要求：不允许使用先将36进制数字整体转为10进制，相加后再转回为36进制的做法
 */
public class Add36Strings {
    public static void main(String[] args) {
        Add36Strings add36Strings = new Add36Strings();
        String num1 = "1b";
        String num2 = "2x";
        System.out.println(add36Strings.add(num1, num2));
    }

    /**
     * 双指针，类似归并排序中的合并操作
     * 从低位向高位相加
     * 时间复杂度O(max(m,n))，空间复杂度O(max(m,n))
     *
     * @param num1
     * @param num2
     * @return
     */
    public String add(String num1, String num2) {
        if ("0".equals(num1)) {
            return num2;
        }

        if ("0".equals(num2)) {
            return num1;
        }

        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        //当前位的进位
        int carry = 0;
        //当前位相加之和
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
                cur = cur + c2 - '0';
            } else {
                cur = cur + c2 - 'a' + 10;
            }

            //加上进位carry
            cur = cur + carry;
            carry = cur / 36;
            cur = cur % 36;

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

            cur = cur + carry;
            carry = cur / 36;
            cur = cur % 36;

            //当前位相加之和小于10，直接拼接cur
            if (cur < 10) {
                sb.append(cur);
            } else {
                //当前位相加之和大于等于10，拼接36进制中的a-z
                sb.append((char) ('a' + cur - 10));
            }

            i--;
        }

        while (j >= 0) {
            char c = num2.charAt(j);

            if (c >= '0' && c <= '9') {
                cur = c - '0';
            } else {
                cur = c - 'a' + 10;
            }

            cur = cur + carry;
            carry = cur / 36;
            cur = cur % 36;

            //当前位相加之和小于10，直接拼接cur
            if (cur < 10) {
                sb.append(cur);
            } else {
                //当前位相加之和大于等于10，拼接36进制中的a-z
                sb.append((char) ('a' + cur - 10));
            }

            j--;
        }

        //最高位有进位
        if (carry != 0) {
            sb.append(carry);
        }

        //因为是从后往前遍历相加，所以需要反转
        return sb.reverse().toString();
    }
}
