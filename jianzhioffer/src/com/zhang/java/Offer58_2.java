package com.zhang.java;

/**
 * @Date 2022/4/5 9:50
 * @Author zsy
 * @Description 左旋转字符串 类比Offer58
 * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。
 * 请定义一个函数实现字符串左旋转操作的功能。
 * 比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
 * <p>
 * 输入: s = "abcdefg", k = 2
 * 输出: "cdefgab"
 * <p>
 * 输入: s = "lrloseumgh", k = 6
 * 输出: "umghlrlose"
 * <p>
 * 1 <= k < s.length <= 10000
 */
public class Offer58_2 {
    public static void main(String[] args) {
        Offer58_2 offer58_2 = new Offer58_2();
        String s = "abcdefg";
        System.out.println(offer58_2.reverseLeftWords(s, 2));
        System.out.println(offer58_2.reverseLeftWords2(s, 2));
        System.out.println(offer58_2.reverseLeftWords3(s, 2));
    }

    /**
     * 暴力
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @param n
     * @return
     */
    public String reverseLeftWords(String s, int n) {
        if (s.length() < 2) {
            return s;
        }

        StringBuilder sb = new StringBuilder();

        return sb.append(s, n, s.length()).append(s, 0, n).toString();
    }

    /**
     * 两个s拼接在一起，其中包含了所有的旋转字符串
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @param n
     * @return
     */
    public String reverseLeftWords2(String s, int n) {
        if (s.length() < 2) {
            return s;
        }

        StringBuilder sb = new StringBuilder(s + s);

        return sb.substring(n, n + s.length());
    }

    /**
     * 不使用额外的空间，只在本串上操作
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @param n
     * @return
     */
    public String reverseLeftWords3(String s, int n) {
        if (s.length() < 2) {
            return s;
        }

        //因为String无法修改本串，所以需要使用StringBuilder
        StringBuilder sb = new StringBuilder(s);

        reverse(sb, 0, n);
        reverse(sb, n, s.length());
        reverse(sb, 0, s.length());

        return sb.toString();
    }

    /**
     * 字符串反转，左闭右开
     *
     * @param sb
     * @param left
     * @param right
     */
    public void reverse(StringBuilder sb, int left, int right) {
        while (left < right) {
            char temp = sb.charAt(left);
            sb.setCharAt(left, sb.charAt(right - 1));
            sb.setCharAt(right - 1, temp);

            left++;
            right--;
        }
    }
}
