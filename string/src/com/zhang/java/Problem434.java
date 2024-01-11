package com.zhang.java;

/**
 * @Date 2024/1/3 08:15
 * @Author zsy
 * @Description 字符串中的单词数 类比Problem8、Offer67
 * 统计字符串中的单词个数，这里的单词指的是连续的不是空格的字符。
 * 请注意，你可以假定字符串里不包括任何不可打印的字符。
 * <p>
 * 输入: "Hello, my name is John"
 * 输出: 5
 * 解释: 这里的单词是指连续的不是空格的字符，所以 "Hello," 算作 1 个单词。
 */
public class Problem434 {
    public static void main(String[] args) {
        Problem434 problem434 = new Problem434();
//        String s = "Hello, my name is John";
        String s = ", , , , a, eaefa";
        System.out.println(problem434.countSegments(s));
    }

    /**
     * 模拟
     * 单词以空格作为分隔
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public int countSegments(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int index = 0;
        int count = 0;

        //去除前导空格
        while (index < s.length() && s.charAt(index) == ' ') {
            index++;
        }

        while (index < s.length()) {
            //单词以空格作为分隔
            while (index < s.length() && s.charAt(index) != ' ') {
                index++;
            }

            count++;

            //去除当前单词后面的空格
            while (index < s.length() && s.charAt(index) == ' ') {
                index++;
            }
        }

        return count;
    }
}
