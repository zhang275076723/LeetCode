package com.zhang.java;

/**
 * @Date 2022/11/4 10:39
 * @Author zsy
 * @Description 反转字符串 II 类比Problem58、Problem151、Problem186、Problem344、Problem557、Offer58、Offer58_2
 * 给定一个字符串 s 和一个整数 k，从字符串开头算起，每计数至 2k 个字符，就反转这 2k 字符中的前 k 个字符。
 * 如果剩余字符少于 k 个，则将剩余字符全部反转。
 * 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。
 * 通俗理解：反转k个，之后k个不反转。
 * <p>
 * 输入：s = "abcdefg", k = 2
 * 输出："bacdfeg"
 * <p>
 * 输入：s = "abcd", k = 2
 * 输出："bacd"
 * <p>
 * 1 <= s.length <= 10^4
 * s 仅由小写英文组成
 * 1 <= k <= 10^4
 */
public class Problem541 {
    public static void main(String[] args) {
        Problem541 problem541 = new Problem541();
        String s = "abcdefg";
        int k = 2;
        System.out.println(problem541.reverseStr(s, k));
    }

    /**
     * 双指针
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @param k
     * @return
     */
    public String reverseStr(String s, int k) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        int left = 0;
        int right;
        StringBuilder sb = new StringBuilder(s);

        while (left < s.length()) {
            right = Math.min(left + k, s.length());
            //反转s[left]-s[right-1]
            reverse(sb, left, right - 1);
            //左指针右移到right+k，表示s[right]-s[right+k-1]不反转
            left = right + k;
        }

        return sb.toString();
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
