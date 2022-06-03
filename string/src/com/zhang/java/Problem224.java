package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/3 11:06
 * @Author zsy
 * @Description 基本计算器 类比Problem150、Problem394 字节面试题
 * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
 * 注意:不允许使用任何将字符串作为数学表达式计算的内置函数，比如 eval() 。
 * <p>
 * 输入：s = "1 + 1"
 * 输出：2
 * <p>
 * 输入：s = " 2-1 + 2 "
 * 输出：3
 * <p>
 * 输入：s = "(1+(4+5+2)-3)+(6+8)"
 * 输出：23
 * <p>
 * 1 <= s.length <= 3 * 105
 * s 由数字、'+'、'-'、'('、')'、和 ' ' 组成
 * s 表示一个有效的表达式
 * '+' 不能用作一元运算(例如， "+1" 和 "+(2 + 3)" 无效)
 * '-' 可以用作一元运算(即 "-1" 和 "-(2 + 3)" 是有效的)
 * 输入中不存在两个连续的操作符
 * 每个数字和运行的计算将适合于一个有符号的 32位 整数
 */
public class Problem224 {
    public static void main(String[] args) {
        Problem224 problem224 = new Problem224();
//        String s = "(1+(4+5+2)-3)+(6+8)";
        String s = "1-11";
        System.out.println(problem224.calculate(s));
        System.out.println(problem224.calculate2(s));
    }

    /**
     * 栈
     * 1、如果遇到数字，则保存连续的数字
     * 2、如果遇到'+'，则设置符号标志位为1
     * 3、如果遇到'-'，则设置符号标志位为-1
     * 4、如果遇到'('，则将保存的数字入栈，符号标志位入栈
     * 4、如果遇到')'，则出栈符号标志位，出栈保存的数字，计算并保存
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        //符号标志位，1：正，-1：负
        int sign = 1;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //去除空格
            if (c == ' ') {
                continue;
            }

            if (c >= '0' && c <= '9') {
                int temp = c - '0';
                while (i + 1 < s.length() && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
                    temp = temp * 10 + (s.charAt(i + 1) - '0');
                    i++;
                }
                result = result + temp * sign;
            } else if (c == '+') {
                sign = 1;
            } else if (c == '-') {
                sign = -1;
            } else if (c == '(') {
                stack.push(result);
                result = 0;
                stack.push(sign);
                sign = 1;
            } else if (c == ')') {
                result = result * stack.pop() + stack.pop();
            }
        }

        return result;
    }

    /**
     * 中缀表达式s转换为逆波兰式，然后再求结果
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int calculate2(String s) {
        String[] token = convert(s);

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < token.length; i++) {
            String str = token[i];
            if ("+".equals(str)) {
                int num2 = stack.pop();
                int num1 = stack.pop();
                stack.push(num1 + num2);
            } else if ("-".equals(str)) {
                int num2 = stack.pop();
                int num1 = stack.pop();
                stack.push(num1 - num2);
            } else {
                stack.push(Integer.parseInt(str));
            }
        }

        return stack.pop();
    }

    /**
     * 中缀表达式转换为后缀表达式
     * 1、如果遇到数字，则直接输出到后缀表达式中
     * 2、如果遇到左括号，则直接入栈
     * 3、如果遇到右括号，则将栈顶元素输出到后缀表达式中，直至遇到左括号(左括号出栈，但不输出)
     * 4、如果遇到运算符，则将栈顶运算符优先级大于等于当前运算符的元素输出到后缀表达式中，再将当前运算符入栈
     * (需要判断'-'是负号，还是运算符，如果是负号，转换为0-num)
     * 5、当遍历完中缀表达式后，将栈中剩余元素输出到后缀表达式中
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    private String[] convert(String s) {
        List<String> list = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //去除空格
            if (c == ' ') {
                continue;
            }

            //数字
            if (c >= '0' && c <= '9') {
                num = num * 10 + (c - '0');
                //当前是数字的最后一位
                if (i == s.length() - 1 || (s.charAt(i + 1) < '0' || s.charAt(i + 1) > '9')) {
                    list.add(num + "");
                    num = 0;
                }
            } else if (c == '(') { //左括号
                stack.push(c);
            } else if (c == ')') { //右括号
                while (!stack.isEmpty() && stack.peek() != '(') {
                    list.add(stack.pop() + "");
                }
                stack.pop();
            } else {
                //负号，转换为0-num
                if ((i == 0 && c == '-') || (s.charAt(i - 1) == '(' && c == '-')) {
                    list.add("0");
                    stack.push(c);
                } else {
                    //运算符
                    while (!stack.isEmpty() && (stack.peek() == '+' || stack.peek() == '-')) {
                        list.add(stack.pop() + "");
                    }
                    stack.push(c);
                }
            }
        }

        while (!stack.isEmpty()) {
            list.add(stack.pop() + "");
        }

        return list.toArray(new String[0]);
    }
}
