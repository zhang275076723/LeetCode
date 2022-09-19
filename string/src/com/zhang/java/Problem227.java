package com.zhang.java;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Date 2022/7/8 7:29
 * @Author zsy
 * @Description 基本计算器 II 类比Problem150、Problem224、Problem394
 * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
 * 整数除法仅保留整数部分。
 * 你可以假设给定的表达式总是有效的。所有中间结果将在 [-2^31, 2^31 - 1] 的范围内。
 * 注意：不允许使用任何将字符串作为数学表达式计算的内置函数，比如 eval() 。
 * <p>
 * 输入：s = "3+2*2"
 * 输出：7
 * <p>
 * 输入：s = " 3/2 "
 * 输出：1
 * <p>
 * 输入：s = " 3+5 / 2 "
 * 输出：5
 * <p>
 * 1 <= s.length <= 3 * 10^5
 * s 由整数和算符 ('+', '-', '*', '/') 组成，中间由一些空格隔开
 * s 表示一个 有效表达式
 * 表达式中的所有整数都是非负整数，且在范围 [0, 2^31 - 1] 内
 * 题目数据保证答案是一个 32-bit 整数
 */
public class Problem227 {
    public static void main(String[] args) {
        Problem227 problem227 = new Problem227();
        String s = " 3+5 / 2 ";
        System.out.println(problem227.calculate(s));
        System.out.println(problem227.calculate2(s));
    }

    /**
     * 双栈，数字栈和操作符栈
     * 1、如果遇到空格，则跳过
     * 2、如果遇到数字，则保存连续的数字
     * 3、如果遇到'('，则直接入操作符栈
     * 4、如果遇到')'，则数字栈出栈两个元素，操作符栈出栈一个运算符，进行运算，再将结果入数字栈，直至操作符栈遇到左括号
     * (左括号出栈，但不进行运算)
     * 5、如果遇到运算符'+'、'-'、'*'、'/'，则将栈顶运算符优先级大于等于当前运算符优先级的符号出栈，
     * 再从数字栈出栈两个元素进行运算，将结果入栈数字栈，最后将当前运算符入操作数栈
     * (需要判断'-'是负号，还是运算符，如果是负号，转换为0-num)
     * 6、遍历完之后如果操作符栈不为空，则依次出栈进行运算
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        //数字栈
        Deque<Integer> numStack = new LinkedList<>();
        //操作符栈
        Deque<Character> opsStack = new LinkedList<>();

        //去除所有空格，不能在遍历过程中跳过空格，例如"( -3)"
        s = s.replaceAll(" ", "");

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //数字
            if (c >= '0' && c <= '9') {
                int num = c - '0';
                while (i + 1 < s.length() && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
                    num = num * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                numStack.offerLast(num);
            } else if (c == '(') {
                //左括号
                opsStack.offerLast(c);
            } else if (c == ')') {
                //右括号
                while (!opsStack.isEmpty() && opsStack.peekLast() != '(') {
                    int num = operation(numStack, opsStack);
                    numStack.offerLast(num);
                }
                //左括号出栈
                opsStack.pollLast();
            } else {
                //首位为'-'或存在"(-"这种情况，需要添0
                if (c == '-' && (i == 0 || s.charAt(i - 1) == '(')) {
                    numStack.offerLast(0);
                    opsStack.offerLast(c);
                } else {
                    //操作符，将栈顶操作符优先级大于等于当前操作符优先级的符号出栈，数字栈出栈，进行运算，再将结果入数字栈
                    while (!opsStack.isEmpty() && getPriority(opsStack.peekLast()) >= getPriority(c)) {
                        int num = operation(numStack, opsStack);
                        numStack.offerLast(num);
                    }
                    //当前操作符入栈
                    opsStack.offerLast(c);
                }
            }
        }

        //操作符栈非空，运算符栈中剩余符号出栈运算
        while (!opsStack.isEmpty()) {
            int num = operation(numStack, opsStack);
            numStack.offerLast(num);
        }

        //数字栈中剩余的最后一个元素，即为运算结果
        return numStack.pollLast();
    }

    /**
     * 一个栈，数字栈和符号栈都使用这个栈
     * 1、如果遇到空格，则跳过
     * 2、如果遇到数字，则保存连续的数字
     * 3、如果遇到'+'，则将当前数字入栈
     * 4、如果遇到'-'，则将当前数字的相反数入栈
     * 5、如果遇到'*'，则将当前数字和栈顶元素相乘，并将结果入栈
     * 6、如果遇到'/'，则将当前数字和栈顶元素相除，并将结果入栈
     * 7、遍历完之后如果操作符栈不为空，则依次出栈进行相加
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int calculate2(String s) {
        Deque<Integer> stack = new LinkedList<>();
        s = s.trim();

        int num = 0;
        char sign = '+';

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //空格
            if (c == ' ') {
                continue;
            }

            //数字
            if (c >= '0' && c <= '9') {
                num = num + c - '0';
                while (i < s.length() - 1 && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
                    num = num * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
            }

            //操作符，i == s.length() - 1确保最后一个数字能够和操作符运算
            if (c < '0' || c > '9' || i == s.length() - 1) {
                switch (sign) {
                    case '+':
                        stack.offerLast(num);
                        break;
                    case '-':
                        stack.offerLast(-num);
                        break;
                    case '*':
                        stack.offerLast(stack.pollLast() * num);
                        break;
                    default:
                        stack.offerLast(stack.pollLast() / num);
                }

                num = 0;
                sign = c;
            }
        }

        int result = 0;

        while (!stack.isEmpty()) {
            result = result + stack.pollLast();
        }

        return result;
    }

    /**
     * 数字栈出栈两个数字，操作符栈出栈运算符，进行运算，并将结果返回
     *
     * @param numStack
     * @param opsStack
     * @return
     */
    private int operation(Deque<Integer> numStack, Deque<Character> opsStack) {
        int num2 = numStack.pollLast();
        int num1 = numStack.pollLast();
        char c = opsStack.pollLast();

        if (c == '+') {
            return num1 + num2;
        } else if (c == '-') {
            return num1 - num2;
        } else if (c == '*') {
            return num1 * num2;
        } else {
            return num1 / num2;
        }
    }

    /**
     * 返回当前运算符的优先级
     * '('、')'优先级为0
     * '+'、'-'优先级为1
     * '*'、'/'优先级为2
     *
     * @param c
     * @return
     */
    private int getPriority(char c) {
        if (c == '(' || c == ')') {
            return 0;
        } else if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/') {
            return 2;
        } else {
            return -1;
        }
    }
}
