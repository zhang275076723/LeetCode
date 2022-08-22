package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/3 11:06
 * @Author zsy
 * @Description 基本计算器 字节面试题 类比Problem150、Problem227、Problem394
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
 * 1 <= s.length <= 3 * 10^5
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
        String s = "(1+(4+5+2)-3)+(6+8)";
        System.out.println(problem224.calculate(s));
        System.out.println(problem224.calculate2(s));
        System.out.println(problem224.calculate3(s));

//        String[] tokens = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        String[] tokens = {"3", "5", "2", "/", "+"};
        String infix = problem224.suffixToInfix(tokens);
        String[] suffix = problem224.infixToSuffix(infix);
        System.out.println(infix);
        System.out.println(Arrays.toString(suffix));
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
        Deque<Integer> numStack = new LinkedList<>();
        Deque<Character> opsStack = new LinkedList<>();
        s = s.trim();

        //防止第一个数为负数的情况，在开头添加一个0
        s = '0' + s;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //空格
            if (c == ' ') {
                continue;
            }

            //数字
            if (c >= '0' && c <= '9') {
                int num = c - '0';
                while (i < s.length() - 1 && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
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
                //负号，转换为0-num
                if (i > 0 && s.charAt(i - 1) == '(' && c == '-') {
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

        return numStack.pollLast();
    }

    /**
     * 一个栈，数字栈和符号栈都使用这个栈
     * 1、如果遇到空格，则跳过
     * 2、如果遇到数字，则保存连续的数字
     * 3、如果遇到'+'，则设置符号标志位为1
     * 4、如果遇到'-'，则设置符号标志位为-1
     * 5、如果遇到'('，则将保存的数字入栈，符号标志位入栈
     * 6、如果遇到')'，则出栈符号标志位，出栈保存的数字，计算并保存
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int calculate2(String s) {
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
                while (i < s.length() - 1 && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
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
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (c == ')') {
                int tempSign = stack.pop();
                int tempResult = stack.pop();
                result = result * tempSign + tempResult;
            }
        }

        return result;
    }

    /**
     * 中缀表达式s转换为后缀表达式(逆波兰式)，然后再求结果
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int calculate3(String s) {
        Stack<Integer> stack = new Stack<>();
        String[] token = infixToSuffix(s);

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
     * 中缀表达式转换为后缀表达式
     * 1、如果遇到空格，则跳过
     * 2、如果遇到数字，则直接输出到后缀表达式中
     * 3、如果遇到'('，则直接入栈
     * 4、如果遇到')'，则将栈顶元素输出到后缀表达式中，直至遇到左括号(左括号出栈，但不输出)
     * 5、如果遇到运算符，则将栈顶运算符优先级大于等于当前运算符优先级的符号输出到后缀表达式中，再将当前运算符入栈
     * (需要判断'-'是负号，还是运算符，如果是负号，转换为0-num)
     * 6、当遍历完中缀表达式后，将栈中剩余元素输出到后缀表达式中
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    private String[] infixToSuffix(String s) {
        List<String> list = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //空格
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
            } else if (c == '(') {
                //左括号
                stack.push(c);
            } else if (c == ')') {
                //右括号
                while (!stack.isEmpty() && stack.peek() != '(') {
                    list.add(stack.pop() + "");
                }
                //左括号出栈
                stack.pop();
            } else {
                //负号，转换为0-num
                if ((i == 0 && c == '-') ||
                        (s.charAt(i - 1) == '(' && c == '-')) {
                    list.add("0");
                    stack.push(c);
                } else {
                    //运算符，将栈顶元素运算符优先级大于等于当前运算符优先级的符号输出
                    while (!stack.isEmpty() && (getPriority(stack.peek()) >= getPriority(c))) {
                        list.add(stack.pop() + "");
                    }
                    stack.push(c);
                }
            }
        }

        //栈中剩余元素输出
        while (!stack.isEmpty()) {
            list.add(stack.pop() + "");
        }

        return list.toArray(new String[0]);
    }

    /**
     * 后缀表达式转换为中缀表达式
     * 1、如果遇到数字，则直接入栈
     * 2、遇到符号，则出栈两个数进行拼接，再入栈
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param token
     * @return
     */
    private String suffixToInfix(String[] token) {
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < token.length; i++) {
            String str = token[i];

            if ("+".equals(str)) {
                String num2 = stack.pop();
                String num1 = stack.pop();

                //对于负数添加括号
                if (num1.charAt(0) == '-') {
                    num1 = "(" + num1 + ")";
                }
                if (num2.charAt(0) == '-') {
                    num2 = "(" + num2 + ")";
                }

                stack.push("(" + num1 + "+" + num2 + ")");
            } else if ("-".equals(str)) {
                String num2 = stack.pop();
                String num1 = stack.pop();

                //对于负数添加括号
                if (num1.charAt(0) == '-') {
                    num1 = "(" + num1 + ")";
                }
                if (num2.charAt(0) == '-') {
                    num2 = "(" + num2 + ")";
                }

                stack.push("(" + num1 + "-" + num2 + ")");
            } else if ("*".equals(str)) {
                String num2 = stack.pop();
                String num1 = stack.pop();

                //对于负数添加括号
                if (num1.charAt(0) == '-') {
                    num1 = "(" + num1 + ")";
                }
                if (num2.charAt(0) == '-') {
                    num2 = "(" + num2 + ")";
                }

                stack.push("(" + num1 + "*" + num2 + ")");
            } else if ("/".equals(str)) {
                String num2 = stack.pop();
                String num1 = stack.pop();

                //对于负数添加括号
                if (num1.charAt(0) == '-') {
                    num1 = "(" + num1 + ")";
                }
                if (num2.charAt(0) == '-') {
                    num2 = "(" + num2 + ")";
                }

                stack.push("(" + num1 + "/" + num2 + ")");
            } else {
                stack.push(str);
            }
        }

        return stack.pop();
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
        } else {
            return 2;
        }
    }
}
