package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2025/1/30 08:51
 * @Author zsy
 * @Description 基本计算器 III 栈类比
 * 实现一个基本的计算器来计算简单的表达式字符串。
 * 表达式字符串只包含非负整数，算符 +、-、*、/ ，左括号 ( 和右括号 ) 。整数除法需要 向下截断 。
 * 你可以假定给定的表达式总是有效的。所有的中间结果的范围均满足 [-2^31, 2^31 - 1] 。
 * 注意：你不能使用任何将字符串作为表达式求值的内置函数，比如 eval() 。
 * <p>
 * 输入：s = "1+1"
 * 输出：2
 * 示例 2：
 * <p>
 * 输入：s = "6-4/2"
 * 输出：4
 * 示例 3：
 * <p>
 * 输入：s = "2*(5+5*2)/3+(6/2+8)"
 * 输出：21
 * <p>
 * 1 <= s <= 10^4
 * s 由整数、'+'、'-'、'*'、'/'、'(' 和 ')' 组成
 * s 是一个 有效的 表达式
 */
public class Problem772 {
    public static void main(String[] args) {
        Problem772 problem772 = new Problem772();
        String s = "2*(5+5*2)/3+(6/2+8)";
        System.out.println(problem772.calculate(s));
    }

    /**
     * 双栈，数字栈和操作符栈
     * 1、如果遇到空格，则跳过
     * 2、如果遇到数字，则保存连续的数字，入数字栈
     * 3、如果遇到'('，则直接入操作符栈
     * 4、如果遇到')'，则数字栈出栈两个元素，操作符栈出栈一个运算符，进行运算，再将结果入数字栈，直至操作符栈遇到左括号
     * (左括号出栈，但不进行运算)
     * 5、如果遇到运算符'+'、'-'、'*'、'/'，则将操作符栈顶运算符优先级大于等于当前运算符优先级的运算符出栈，
     * 再从数字栈出栈两个元素进行运算，再将结果入栈数字栈，最后将当前运算符入操作数栈
     * (需要判断'-'是负号，还是运算符，如果是负号，转换为0-num)
     * 6、遍历完之后如果操作符栈不为空，则依次出栈进行运算
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        //去除所有空格，不能在遍历过程中跳过空格，例如"( -3)"
        s = s.replaceAll(" ", "");
        //数字栈
        Stack<Integer> numStack = new Stack<>();
        //操作符栈
        Stack<Character> opsStack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //数字
            if (c >= '0' && c <= '9') {
                int num = c - '0';

                while (i + 1 < s.length() && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
                    num = num * 10 + s.charAt(i + 1) - '0';
                    i++;
                }

                numStack.push(num);
            } else if (c == '(') {
                //左括号，直接入操作符栈

                opsStack.push(c);
            } else if (c == ')') {
                //右括号，数字栈出栈两个元素，操作符栈出栈一个运算符，进行运算，再将结果入数字栈，直至操作符栈遇到左括号

                while (!opsStack.isEmpty() && opsStack.peek() != '(') {
                    int num = operate(numStack, opsStack);
                    numStack.push(num);
                }

                //左括号出栈
                opsStack.pop();
            } else {
                //运算符，+-*/

                //存在"-num"、"+num"、"(-"、"(+"的情况，数字栈需要补0
                if ((c == '-' || c == '+') && (i == 0 || s.charAt(i - 1) == '(')) {
                    numStack.push(0);
                }

                //操作符栈顶运算符优先级大于等于当前运算符优先级，则操作符栈顶运算符出栈，数字栈出栈，进行运算，再将运算结果重新入数字栈
                while (!opsStack.isEmpty() && getPriority(opsStack.peek()) >= getPriority(c)) {
                    int num = operate(numStack, opsStack);
                    numStack.push(num);
                }

                //当前运算符入操作符栈
                opsStack.push(c);
            }
        }

        //操作符栈非空，即存在未运算的运算符，操作符栈中剩余运算符出栈运算，再将结果入数字栈
        while (!opsStack.isEmpty()) {
            int num = operate(numStack, opsStack);
            numStack.push(num);
        }

        //数字栈中剩余的最后一个元素，即为运算结果
        return numStack.pop();
    }

    /**
     * 数字栈出栈两个数字，操作符栈出栈运算符，进行运算，并将结果返回
     *
     * @param numStack
     * @param opsStack
     * @return
     */
    private int operate(Stack<Integer> numStack, Stack<Character> opsStack) {
        //先出栈的元素为num2，后出栈的元素为num1
        int num2 = numStack.pop();
        int num1 = numStack.pop();
        char c = opsStack.pop();

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
     * '+'、'-'优先级为1
     * '*'、'/'优先级为2
     *
     * @param c
     * @return
     */
    private int getPriority(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/') {
            return 2;
        } else {
            return -1;
        }
    }
}
