package com.zhang.java;

/**
 * @Date 2022/11/2 09:48
 * @Author zsy
 * @Description 最后一个单词的长度 类比Problem151、Problem186、Problem344、Problem541、Problem557、Offer58、Offer58_2
 * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。
 * 返回字符串中 最后一个 单词的长度。
 * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
 * <p>
 * 输入：s = "Hello World"
 * 输出：5
 * 解释：最后一个单词是“World”，长度为5。
 * <p>
 * 输入：s = "   fly me   to   the moon  "
 * 输出：4
 * 解释：最后一个单词是“moon”，长度为4。
 * <p>
 * 输入：s = "luffy is still joyboy"
 * 输出：6
 * 解释：最后一个单词是长度为6的“joyboy”。
 * <p>
 * 1 <= s.length <= 10^4
 * s 仅有英文字母和空格 ' ' 组成
 * s 中至少存在一个单词
 */
public class Problem58 {
    public static void main(String[] args) {
        Problem58 problem58 = new Problem58();
        String s = "   fly me   to   the moon  ";
        System.out.println(problem58.lengthOfLastWord(s));
    }

    /**
     * 双指针，由后往前遍历
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public int lengthOfLastWord(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int index = s.length() - 1;

        //去除末尾空格
        while (index >= 0 && s.charAt(index) == ' ') {
            index--;
        }

        //字符串s都为空格，直接返回0
        if (index == -1) {
            return 0;
        }

        //最右单词的左指针和右指针
        int left = index;
        int right = index;

        while (left >= 0 && s.charAt(left) != ' ') {
            left--;
        }

        return right - left;
    }
}
