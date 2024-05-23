package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/2/18 08:05
 * @Author zsy
 * @Description 分数到小数 拼多多面试题 加减乘除类比Problem29、Problem43、Problem415、Add36Strings、BigNumberSubtract、Subtract36Strings 哈希表类比Problem1、Problem128、Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem535、Problem554、Problem763、Problem1500、Problem1640、Offer50
 * 给定两个整数，分别表示分数的分子 numerator 和分母 denominator，以 字符串形式返回小数 。
 * 如果小数部分为循环小数，则将循环的部分括在括号内。
 * 如果存在多个答案，只需返回 任意一个 。
 * 对于所有给定的输入，保证 答案字符串的长度小于 10^4 。
 * <p>
 * 输入：numerator = 1, denominator = 2
 * 输出："0.5"
 * <p>
 * 输入：numerator = 2, denominator = 1
 * 输出："2"
 * <p>
 * 输入：numerator = 4, denominator = 333
 * 输出："0.(012)"
 * <p>
 * -2^31 <= numerator, denominator <= 2^31 - 1
 * denominator != 0
 */
public class Problem166 {
    public static void main(String[] args) {
        Problem166 problem166 = new Problem166();
        int numerator = 4;
        int denominator = 333;
        System.out.println(problem166.fractionToDecimal(numerator, denominator));
    }

    /**
     * 模拟+哈希表
     * a/b作为结果字符串当前位的值，a/b的余数和余数出现的下标索引加入map，a/b的余数作为下次循环的a，每次循环之前a需要乘10，相当于找下一位小数
     * 如果余数为0，则结果字符串为有限小数；如果余数在map中已经存在，则结果字符串为循环小数，[map.get(余数),sb.length()-1]为循环部分
     * 时间复杂度O(sb.length())，空间复杂度O(sb.length()) (哈希表中最多存放denominator个余数，因为除法的余数都小于denominator)
     * <p>
     * 例如：a=4，b=333
     * a=4，4/333=0，4%333=4，sb="0."，map={{key=4,value=2}}
     * a=4*10=40，40/333=0，40%333=40，sb="0.0"，map={{key=4,value=2},{key=40,value=3}}
     * a=40*10=400，400/333=1，400%333=67，sb="0.01"，map={{key=4,value=2},{key=40,value=3},{key=67,value=4}}
     * a=67*10=670，670/333=2，670%333=4，sb="0.012"，map={{key=4,value=2},{key=40,value=3},{key=67,value=4}}，
     * 此时map中已经存在key=4的值，则为sb中下标索引[map.get(4),sb.length()-1]=[2,4]为循环部分，即sb="0.(012)"
     *
     * @param numerator
     * @param denominator
     * @return
     */
    public String fractionToDecimal(int numerator, int denominator) {
        //numerator为0，返回"0"
        if (numerator == 0) {
            return "0";
        }

        //denominator为0，则不合法，返回"-1"
        if (denominator == 0) {
            return "-1";
        }

        //使用long，避免int溢出
        long a = numerator;
        long b = denominator;

        //a能整除b，直接返回a/b的值
        if (a % b == 0) {
            return (a / b) + "";
        }

        StringBuilder sb = new StringBuilder();
        //key：a/b的余数，value：a/b的余数在结果字符串的下标索引
        Map<Long, Integer> map = new HashMap<>();

        //结果为负数
        if ((a > 0 && b < 0) || (a < 0 && b > 0)) {
            sb.append('-');
        }

        //都取正数，避免考虑负数情况
        a = Math.abs(a);
        b = Math.abs(b);

        //拼接小数点
        sb.append(a / b).append('.');
        a = a % b;
        map.put(a, sb.length());

        //余数a为0，则说明结果为有限小数
        while (a != 0) {
            //每次循环之前a需要乘10，相当于找下一位小数
            a = a * 10;
            sb.append(a / b);
            a = a % b;

            //map中存在余数a，则结果字符串为循环小数，[map.get(余数),sb.length()-1]为循环部分，直接返回
            if (map.containsKey(a)) {
                int index = map.get(a);
                sb.insert(index, '(');
                sb.append(')');
                return sb.toString();
            } else {
                //map中不存在余数a，则余数a加入到map中
                map.put(a, sb.length());
            }
        }

        //余数为0，则结果字符串为有限小数
        return sb.toString();
    }
}
