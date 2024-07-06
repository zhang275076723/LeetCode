package com.zhang.java;

/**
 * @Date 2024/8/17 08:04
 * @Author zsy
 * @Description 移除字符串中的尾随零 类比Problem2980
 * 给你一个用字符串表示的正整数 num ，请你以字符串形式返回不含尾随零的整数 num 。
 * <p>
 * 输入：num = "51230100"
 * 输出："512301"
 * 解释：整数 "51230100" 有 2 个尾随零，移除并返回整数 "512301" 。
 * <p>
 * 输入：num = "123"
 * 输出："123"
 * 解释：整数 "123" 不含尾随零，返回整数 "123" 。
 * <p>
 * 1 <= num.length <= 1000
 * num 仅由数字 0 到 9 组成
 * num 不含前导零
 */
public class Problem2710 {
    public static void main(String[] args) {
        Problem2710 problem2710 = new Problem2710();
        String num = "51230100";
        System.out.println(problem2710.removeTrailingZeros(num));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public String removeTrailingZeros(String num) {
        int index = num.length() - 1;

        while (index >= 0 && num.charAt(index) == '0') {
            index--;
        }

        return num.substring(0, index + 1);
    }
}
