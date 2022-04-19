package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/4/15 10:40
 * @Author zsy
 * @Description 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * 1、左括号必须用相同类型的右括号闭合。
 * 2、左括号必须以正确的顺序闭合。
 * <p>
 * 输入：s = "()"
 * 输出：true
 * <p>
 * 输入：s = "()[]{}"
 * 输出：true
 * <p>
 * 输入：s = "(]"
 * 输出：false
 * <p>
 * 输入：s = "([)]"
 * 输出：false
 * <p>
 * 输入：s = "{[]}"
 * 输出：true
 * <p>
 * 1 <= s.length <= 104
 * s 仅由括号 '()[]{}' 组成
 */
public class Problem020 {
    public static void main(String[] args) {
        Problem020 problem020 = new Problem020();
        String s = "{[]}";
        System.out.println(problem020.isValid(s));
        System.out.println(problem020.isValid2(s));
    }

    /**
     * 自己的解，使用栈存放括号，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.isEmpty() || c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' && stack.peek() == '(') {
                stack.pop();
            } else if (c == ']' && stack.peek() == '[') {
                stack.pop();
            } else if (c == '}' && stack.peek() == '{') {
                stack.pop();
            } else {
                return false;
            }
        }

        return stack.isEmpty();
    }

    /**
     * 答案的解，遇到左括号，对应右括号入栈，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public boolean isValid2(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            } else if (stack.isEmpty() || c != stack.pop()) {
                return false;
            }
        }

        return stack.isEmpty();
    }
}
