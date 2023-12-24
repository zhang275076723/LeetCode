package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2023/7/17 08:27
 * @Author zsy
 * @Description 括号的分数 括号类比Problem20、Problem22、Problem32、Problem301、Problem678、Problem1087、Problem1096 栈类比Problem20、Problem71、Problem150、Problem224、Problem227、Problem331、Problem341、Problem394、Problem678、Problem946、Problem1003、Problem1047、Problem1096、Offer31、CharacterToInteger
 * 给定一个平衡括号字符串 S，按下述规则计算该字符串的分数：
 * () 得 1 分。
 * AB 得 A + B 分，其中 A 和 B 是平衡括号字符串。
 * (A) 得 2 * A 分，其中 A 是平衡括号字符串。
 * <p>
 * 输入： "()"
 * 输出： 1
 * <p>
 * 输入： "(())"
 * 输出： 2
 * <p>
 * 输入： "()()"
 * 输出： 2
 * <p>
 * 输入： "(()(()))"
 * 输出： 6
 * <p>
 * S 是平衡括号字符串，且只含有 ( 和 ) 。
 * 2 <= S.length <= 50
 */
public class Problem856 {
    public static void main(String[] args) {
        Problem856 problem856 = new Problem856();
        //(()(()))=(1(1))=(12)=(3)=6
        String s = "(()(()))";
        System.out.println(problem856.scoreOfParentheses(s));
    }

    /**
     * 栈
     * 字符串s依次入栈，遇到'('，直接入栈；
     * 遇到')'，如果栈顶是'('，则栈顶'('出栈，1入栈，
     * 如果栈顶不是'('，则栈顶数字相加，直至栈顶'('出栈，相加结果乘2入栈
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int scoreOfParentheses(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else {
                //栈顶元素是'('，形成"()"，则栈顶元素'('出栈，'1'入栈
                if (stack.peek() == '(') {
                    stack.pop();
                    stack.push('1');
                } else {
                    //栈顶元素是数字，遇到'('之前的数字相加，作为"(ABC)"，则栈顶元素'('出栈，相加结果乘2入栈
                    int sum = 0;

                    while (!stack.isEmpty() && stack.peek() != '(') {
                        sum = sum + stack.pop() - '0';
                    }

                    //栈顶元素'('出栈
                    stack.pop();
                    //相加结果乘2入栈
                    stack.push((char) ((sum * 2) + '0'));
                }
            }
        }

        int result = 0;

        //栈中所有数字相加得到最终结果
        while (!stack.isEmpty()) {
            result = result + stack.pop() - '0';
        }

        return result;
    }
}
