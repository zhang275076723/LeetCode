package com.zhang.java;

/**
 * @Date 2022/5/24 21:10
 * @Author zsy
 * @Description 颠倒字符串中的单词 七牛云面试题
 * 给你一个字符串 s ，颠倒字符串中 单词 的顺序。
 * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
 * 返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
 * 注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。
 * 返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
 * <p>
 * 输入：s = "the sky is blue"
 * 输出："blue is sky the"
 * <p>
 * 输入：s = "  hello world  "
 * 输出："world hello"
 * 解释：颠倒后的字符串中不能存在前导空格和尾随空格。
 * <p>
 * 输入：s = "a good  example"
 * 输出："example good a"
 * 解释：如果两个单词间有多余的空格，颠倒后的字符串需要将单词间的空格减少到仅有一个。
 * <p>
 * 1 <= s.length <= 10^4
 * s 包含英文大小写字母、数字和空格 ' '
 * s 中 至少存在一个 单词
 */
public class Problem151 {
    public static void main(String[] args) {
        Problem151 problem151 = new Problem151();
        String s = "  hello    world  ";
        System.out.println(problem151.reverseWords(s));
    }

    /**
     * 先去除多余的空格，再反转字符串，最后对字符串中每个单词进行反转
     * 时间复杂度O(n)，空间复杂度O(n)，如果说字符串是可变类型，则空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        //去除多余的空格
        StringBuilder sb = trim(s);

        //反转字符串
        reverse(sb, 0, sb.length() - 1);

        //反转字符串中每个单词
        int start = 0;
        while (start < sb.length()) {
            int left = start;
            int right;
            while (start < sb.length() && sb.charAt(start) != ' ') {
                start++;
            }
            right = start;
            reverse(sb, left, right - 1);
            start++;
        }

        return sb.toString();
    }

    /**
     * 去除s中首位空字符，和每个单词之间多余的空字符
     *
     * @param s
     * @return
     */
    private StringBuilder trim(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left <= right && s.charAt(left) == ' ') {
            left++;
        }
        while (left <= right && s.charAt(right) == ' ') {
            right--;
        }

        if (left > right) {
            return new StringBuilder("");
        }

        //去除每个单词之间多余的空字符
        StringBuilder sb = new StringBuilder();
        for (int i = left + 1; i <= right; i++) {
            char c1 = s.charAt(i - 1);
            char c2 = s.charAt(i);
            if (c1 != ' ' || c2 != ' ') {
                sb.append(c1);
            }
        }
        sb.append(s.charAt(right));

        return sb;
    }

    /**
     * 反转字符串
     *
     * @param sb
     * @param left
     * @param right
     */
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
