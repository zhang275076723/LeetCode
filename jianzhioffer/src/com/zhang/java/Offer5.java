package com.zhang.java;

/**
 * @Date 2022/3/13 11:56
 * @Author zsy
 * @Description 请实现一个函数，把字符串 s 中的每个空格替换成"%20"
 * 输入：s = "We are happy."
 * 输出："We%20are%20happy."
 */
public class Offer5 {
    public static void main(String[] args) {
        Offer5 offer5 = new Offer5();
        String s = "We are happy.";
        System.out.println(offer5.replaceSpace(s));
        System.out.println(offer5.replaceSpace2(s));
    }

    public String replaceSpace(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                stringBuilder.append("%20");
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public String replaceSpace2(String s) {
        return s.replace(" ", "%20");
    }
}
