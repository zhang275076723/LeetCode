package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/4/15 10:40
 * @Author zsy
 * @Description 有效的括号 类比Problem22、Problem32、Problem301、Problem678
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
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
 * 1 <= s.length <= 10^4
 * s 仅由括号 '()[]{}' 组成
 */
public class Problem20 {
    public static void main(String[] args) {
        Problem20 problem20 = new Problem20();
        String s = "{[]}";
        System.out.println(problem20.isValid(s));
    }

    /**
     * 栈
     * 遇到左括号，入栈；遇到右括号，和栈顶元素比较，如果匹配，栈顶元素出栈，如果不匹配，返回false
     * 时间复杂度O(n)，空间复杂度O(n)
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

}
