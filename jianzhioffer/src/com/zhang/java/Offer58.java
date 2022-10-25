package com.zhang.java;


/**
 * @Date 2022/4/5 9:12
 * @Author zsy
 * @Description 翻转单词顺序 类比Offer58_2 同Problem151
 * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。
 * 为简单起见，标点符号和普通字母一样处理。
 * 例如输入字符串"I am a student. "，则输出"student. a am I"。
 * 无空格字符构成一个单词。
 * 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 * <p>
 * 输入: "the sky is blue"
 * 输出: "blue is sky the"
 * <p>
 * 输入: " hello world! "
 * 输出: "world! hello"
 * 解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * <p>
 * 输入: "a good   example"
 * 输出: "example good a"
 * 解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 * <p>
 * 无空格字符构成一个单词。
 * 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 */
public class Offer58 {
    public static void main(String[] args) {
        Offer58 offer58 = new Offer58();
//        String s = " hello   world! ";
        String s = "  ";
        System.out.println(offer58.reverseWords(s));
        System.out.println(offer58.reverseWords2(s));
    }

    /**
     * 分割+反向遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        String[] split = s.trim().split(" ");

        for (int i = split.length - 1; i >= 0; i--) {
            if (!"".equals(split[i])) {
                sb.append(split[i]).append(" ");
            }
        }

        //去除末尾空格
        return sb.toString().trim();
    }

    /**
     * 双指针
     * 从后往前找每一个单词进行拼接
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String reverseWords2(String s) {
        s = s.trim();

        //s只有空格，直接返回
        if (s.length() == 0) {
            return "";
        }

        int left = s.length() - 1;
        int right = s.length() - 1;
        StringBuilder sb = new StringBuilder();

        while (left >= 0) {
            while (left >= 0 && s.charAt(left) != ' ') {
                left--;
            }

            sb.append(s, left + 1, right + 1).append(' ');

            //跳过空格
            while (left >= 0 && s.charAt(left) == ' ') {
                left--;
            }

            right = left;
        }

        //去除末尾空格
        return sb.delete(sb.length() - 1, sb.length()).toString();
    }
}
