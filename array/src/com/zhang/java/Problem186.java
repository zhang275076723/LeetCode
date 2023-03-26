package com.zhang.java;

/**
 * @Date 2023/2/10 10:25
 * @Author zsy
 * @Description 反转字符串中的单词 II 旋转问题类比Problem61、Problem189、Offer58_2 类比Problem58、Problem151、Problem344、Problem541、Problem557、Offer58、Offer58_2
 * 给定一个字符串，逐个翻转字符串中的每个单词。
 * <p>
 * 输入: ["t","h","e"," ","s","k","y"," ","i","s"," ","b","l","u","e"]
 * 输出: ["b","l","u","e"," ","i","s"," ","s","k","y"," ","t","h","e"]
 * <p>
 * 注意：
 * 单词的定义是不包含空格的一系列字符
 * 输入字符串中不会包含前置或尾随的空格
 * 单词与单词之间永远是以单个空格隔开的
 */
public class Problem186 {
    public static void main(String[] args) {
        Problem186 problem186 = new Problem186();
        char[] s = {'t', 'h', 'e', ' ', 's', 'k', 'y', ' ', 'i', 's', ' ', 'b', 'l', 'u', 'e'};
        problem186.reverseWords(s);
        System.out.println(s);
    }

    /**
     * 双指针
     * 对每个单词进行反转，最后字符串s整体进行反转
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     */
    public void reverseWords(char[] s) {
        int left = 0;
        int right = 0;

        while (left < s.length) {
            while (right < s.length && s[right] != ' ') {
                right++;
            }

            //单词进行反转
            reverse(s, left, right - 1);

            left = right + 1;
            right = left;
        }

        //字符串s整体进行反转
        reverse(s, 0, s.length - 1);
    }

    private void reverse(char[] s, int left, int right) {
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;

            left++;
            right--;
        }
    }
}
