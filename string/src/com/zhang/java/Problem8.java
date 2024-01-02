package com.zhang.java;

/**
 * @Date 2022/6/23 16:28
 * @Author zsy
 * @Description 字符串转换整数 (atoi) 类比Problem7、Problem12、Problem13、Problem434、Problem1281、CharacterToInteger 同Offer67
 * 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
 * 函数 myAtoi(string s) 的算法如下：
 * 1、读入字符串并丢弃无用的前导空格
 * 2、检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。
 * 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
 * 3、读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
 * 4、将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。
 * 如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
 * 5、如果整数数超过 32 位有符号整数范围 [−2^31,  2^31−1] ，需要截断这个整数，使其保持在这个范围内。
 * 具体来说，小于 −2^31 的整数应该被固定为 −2^31 ，大于 2^31−1 的整数应该被固定为 2^31−1 。
 * 6、返回整数作为最终结果。
 * <p>
 * 注意：
 * 本题中的空白字符只包括空格字符 ' ' 。
 * 除前导空格或数字后的其余字符串外，请勿忽略 任何其他字符。
 * <p>
 * 输入：s = "42"
 * 输出：42
 * 解释：加粗的字符串为已经读入的字符，插入符号是当前读取的字符。
 * 第 1 步："42"（当前没有读入字符，因为没有前导空格）
 * 第 2 步："42"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
 * 第 3 步："42"（读入 "42"）
 * 解析得到整数 42 。
 * 由于 "42" 在范围 [-2^31, 2^31-1] 内，最终结果为 42 。
 * <p>
 * 输入：s = "   -42"
 * 输出：-42
 * 解释：
 * 第 1 步："   -42"（读入前导空格，但忽视掉）
 * 第 2 步："   -42"（读入 '-' 字符，所以结果应该是负数）
 * 第 3 步："   -42"（读入 "42"）
 * 解析得到整数 -42 。
 * 由于 "-42" 在范围 [-2^31, 2^31-1] 内，最终结果为 -42 。
 * <p>
 * 输入：s = "4193 with words"
 * 输出：4193
 * 解释：
 * 第 1 步："4193 with words"（当前没有读入字符，因为没有前导空格）
 * 第 2 步："4193 with words"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
 * 第 3 步："4193 with words"（读入 "4193"；由于下一个字符不是一个数字，所以读入停止）
 * 解析得到整数 4193 。
 * 由于 "4193" 在范围 [-2^31, 2^31-1] 内，最终结果为 4193 。
 * <p>
 * 0 <= s.length <= 200
 * s 由英文字母（大写和小写）、数字（0-9）、' '、'+'、'-' 和 '.' 组成
 */
public class Problem8 {
    public static void main(String[] args) {
        Problem8 problem8 = new Problem8();
        String s = "    +0004193 with words";
        System.out.println(problem8.myAtoi(s));
    }

    /**
     * 模拟，注意溢出情况的处理
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public int myAtoi(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int index = 0;

        //去除前导空格
        while (index < s.length() && s.charAt(index) == ' ') {
            index++;
        }

        //都是空格的情况
        if (index == s.length()) {
            return 0;
        }

        //符号标志位，1：正，-1：负
        int flag = 1;

        if (s.charAt(index) == '+') {
            index++;
        } else if (s.charAt(index) == '-') {
            flag = -1;
            index++;
        }

        int num = 0;

        while (index < s.length()) {
            char c = s.charAt(index);

            if (c >= '0' && c <= '9') {
                //上溢出
                if (num > Integer.MAX_VALUE / 10 ||
                        (num == Integer.MAX_VALUE / 10 && (c - '0') >= Integer.MAX_VALUE % 10)) {
                    return Integer.MAX_VALUE;
                }

                //下溢出
                if (num < Integer.MIN_VALUE / 10 ||
                        (num == Integer.MIN_VALUE / 10 && (c - '0') >= -(Integer.MIN_VALUE % 10))) {
                    return Integer.MIN_VALUE;
                }

                num = num * 10 + (c - '0') * flag;

                index++;
            } else {
                //当前不是数字，则直接返回
                return num;
            }
        }

        //遍历到末尾，直接返回
        //不能写成return flag == 1 ? num : -num，因为int类型的最大值和最小值不一样，需要每次累加都判断是否溢出
        return num;
    }
}
