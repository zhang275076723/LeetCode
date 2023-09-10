package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2023/7/16 09:12
 * @Author zsy
 * @Description 检查替换后的词是否有效 类比Problem331 类比Problem71、Problem402、Problem1047、CharacterToInteger
 * 给你一个字符串 s ，请你判断它是否 有效 。
 * 字符串 s 有效 需要满足：假设开始有一个空字符串 t = "" ，你可以执行 任意次 下述操作将 t 转换为 s ：
 * 将字符串 "abc" 插入到 t 中的任意位置。形式上，t 变为 tleft + "abc" + tright，其中 t == tleft + tright 。
 * 注意，tleft 和 tright 可能为 空 。
 * 如果字符串 s 有效，则返回 true；否则，返回 false。
 * <p>
 * 输入：s = "aabcbc"
 * 输出：true
 * 解释："" -> "abc" -> "aabcbc"
 * 因此，"aabcbc" 有效。
 * <p>
 * 输入：s = "abcabcababcc"
 * 输出：true
 * 解释："" -> "abc" -> "abcabc" -> "abcabcabc" -> "abcabcababcc"
 * 因此，"abcabcababcc" 有效。
 * <p>
 * 输入：s = "abccba"
 * 输出：false
 * 解释：执行操作无法得到 "abccba" 。
 * <p>
 * 1 <= s.length <= 2 * 10^4
 * s 由字母 'a'、'b' 和 'c' 组成
 */
public class Problem1003 {
    public static void main(String[] args) {
        Problem1003 problem1003 = new Problem1003();
        String s = "aabcbc";
        System.out.println(problem1003.isValid(s));
    }

    /**
     * 栈
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

        for (char c : s.toCharArray()) {
            stack.push(c);

            //栈顶的三个元素为"abc"，则栈顶三个元素出栈
            while (stack.size() >= 3 &&
                    stack.get(stack.size() - 1) == 'c' &&
                    stack.get(stack.size() - 2) == 'b' &&
                    stack.get(stack.size() - 3) == 'a') {
                stack.pop();
                stack.pop();
                stack.pop();
            }
        }

        //遍历完之后，栈为空，则说明字符串s有效
        return stack.isEmpty();
    }
}
