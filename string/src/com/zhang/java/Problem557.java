package com.zhang.java;

/**
 * @Date 2023/2/10 10:38
 * @Author zsy
 * @Description 反转字符串中的单词 III 类比Problem58、Problem151、Problem186、Problem344、Problem541、Offer58、Offer58_2
 * 给定一个字符串 s ，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
 * <p>
 * 输入：s = "Let's take LeetCode contest"
 * 输出："s'teL ekat edoCteeL tsetnoc"
 * <p>
 * 输入： s = "God Ding"
 * 输出："doG gniD"
 * <p>
 * 1 <= s.length <= 5 * 10^4
 * s 包含可打印的 ASCII 字符。
 * s 不包含任何开头或结尾空格。
 * s 里 至少 有一个词。
 * s 中的所有单词都用一个空格隔开。
 */
public class Problem557 {
    public static void main(String[] args) {
        Problem557 problem557 = new Problem557();
        String s = "Let's take LeetCode contest";
        System.out.println(problem557.reverseWords(s));
    }

    /**
     * 双指针
     * 对每个单词进行反转，再拼接
     * 时间复杂度O(n)，空间复杂度O(n) (如果s是可变长，空间复杂度O(1))
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int left = 0;
        int right = 0;

        while (left < s.length()) {
            while (right < s.length() && s.charAt(right) != ' ') {
                right++;
            }

            sb.append(s.substring(left, right));
            //翻转[left,right)字符串
            reverse(sb, left, right - 1);
            //拼接' '
            sb.append(' ');

            left = right + 1;
            right = left;
        }

        //去除末尾空格
        return sb.delete(sb.length() - 1, sb.length()).toString();
    }

    private void reverse(StringBuilder sb, int left, int right) {
        while (left < right) {
            char c = sb.charAt(left);
            sb.setCharAt(left, sb.charAt(right));
            sb.setCharAt(right, c);

            left++;
            right--;
        }
    }
}
