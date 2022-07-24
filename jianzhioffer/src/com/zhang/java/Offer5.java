package com.zhang.java;

/**
 * @Date 2022/3/13 11:56
 * @Author zsy
 * @Description 替换空格
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"
 * <p>
 * 输入：s = "We are happy."
 * 输出："We%20are%20happy."
 * <p>
 * 0 <= s 的长度 <= 10000
 */
public class Offer5 {
    public static void main(String[] args) {
        Offer5 offer5 = new Offer5();
        String s = "We are happy.";
        System.out.println(offer5.replaceSpace(s));
        System.out.println(offer5.replaceSpace2(s));
    }

    /**
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
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
