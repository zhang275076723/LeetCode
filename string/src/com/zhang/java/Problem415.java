package com.zhang.java;

/**
 * @Date 2022/6/19 9:30
 * @Author zsy
 * @Description 字符串相加 类比Problem2、Problem66、Problem67、Problem369、Problem445 加减乘除类比Problem29、Problem43
 * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和并同样以字符串形式返回。
 * 你不能使用任何內建的用于处理大整数的库（比如 BigInteger）， 也不能直接将输入的字符串转换为整数形式。
 * <p>
 * 输入：num1 = "11", num2 = "123"
 * 输出："134"
 * <p>
 * 输入：num1 = "456", num2 = "77"
 * 输出："533"
 * <p>
 * 输入：num1 = "0", num2 = "0"
 * 输出："0"
 * <p>
 * 1 <= num1.length, num2.length <= 10^4
 * num1 和num2 都只包含数字 0-9
 * num1 和num2 都不包含任何前导零
 */
public class Problem415 {
    public static void main(String[] args) {
        Problem415 problem415 = new Problem415();
        String nums1 = "9999";
        String nums2 = "99";
        System.out.println(problem415.addStrings(nums1, nums2));
    }

    /**
     * 双指针
     * 从低位向高位相加和进行
     * 时间复杂度O(max(m,n))，空间复杂度O(max(m,n))
     *
     * @param num1
     * @param num2
     * @return
     */
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();

        int i = num1.length() - 1;
        int j = num2.length() - 1;
        //当前位的进位
        int carry = 0;
        //当前位之和
        int sum;

        while (i >= 0 && j >= 0) {
            sum = (num1.charAt(i) - '0') + (num2.charAt(j) - '0') + carry;
            carry = sum / 10;
            sb.append(sum % 10);
            i--;
            j--;
        }

        while (i >= 0) {
            sum = (num1.charAt(i) - '0') + carry;
            carry = sum / 10;
            sb.append(sum % 10);
            i--;
        }

        while (j >= 0) {
            sum  = (num2.charAt(j) - '0') + carry;
            carry = sum / 10;
            sb.append(sum % 10);
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
