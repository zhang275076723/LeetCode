package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/6/3 9:47
 * @Author zsy
 * @Description 字符串解码 字节面试题 4399机试题 华为面试题 类比Problem471 类比Problem271、Problem443、Problem604、Problem900 栈类比Problem20、Problem71、Problem150、Problem224、Problem227、Problem331、Problem341、Problem678、Problem856、Problem946、Problem1003、Problem1047、Problem1096、Offer31、CharacterToInteger
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 * <p>
 * 输入：s = "3[a]2[bc]"
 * 输出："aaabcbc"
 * <p>
 * 输入：s = "3[a2[c]]"
 * 输出："accaccacc"
 * <p>
 * 输入：s = "2[abc]3[cd]ef"
 * 输出："abcabccdcdcdef"
 * <p>
 * 输入：s = "abc3[cd]xyz"
 * 输出："abccdcdcdxyz"
 * <p>
 * 1 <= s.length <= 30
 * s 由小写英文字母、数字和方括号 '[]' 组成
 * s 保证是一个 有效 的输入。
 * s 中所有整数的取值范围为 [1, 300]
 */
public class Problem394 {
    public static void main(String[] args) {
        Problem394 problem394 = new Problem394();
        String s = "11[a]12[b]";
        System.out.println(problem394.decodeString(s));
    }

    /**
     * 双栈，数字栈保存当前字符串重复的次数，字符串栈保存当前遍历到的字符串
     * 1、如果遇到数字，则保存连续数字
     * 2、如果遇到字符，则拼接字符串
     * 3、如果遇到'['，则将当前数字入数字栈，当前字符串入字符串栈
     * 4、如果遇到']'，则字符串栈出栈，数字栈出栈，字符串栈出栈的字符串拼接当前结果字符串数字栈出栈的次数，得到当前结果字符串
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String decodeString(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        //数字栈
        Stack<Integer> numStack = new Stack<>();
        //字母串栈
        Stack<String> strStack = new Stack<>();
        //结果字符串
        StringBuilder sb = new StringBuilder();
        //当前字符串重复的次数
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //数字
            if (c >= '0' && c <= '9') {
                num = c - '0';
                while (i + 1 < s.length() && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
                    num = num * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
            } else if (c == '[') {
                //左括号，当前数字和字符串入栈
                numStack.push(num);
                strStack.push(sb.toString());

                //结果字符串和当前字符串重复的次数重新赋值
                num = 0;
                sb = new StringBuilder();
            } else if (c == ']') {
                //右括号，出栈要重复的次数和重复字符串之前保存的字符串，拼接到当前字符串之后

                //当前字符串需要重复的次数
                int count = numStack.pop();
                StringBuilder tempSb = new StringBuilder(strStack.pop());

                for (int j = 0; j < count; j++) {
                    tempSb.append(sb);
                }

                //tempSb赋值给sb，进行下个字符判断
                sb = tempSb;
            } else {
                //字符
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
