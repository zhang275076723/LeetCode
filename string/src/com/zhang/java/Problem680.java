package com.zhang.java;

/**
 * @Date 2023/2/17 08:44
 * @Author zsy
 * @Description 验证回文串 II 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem409、Problem516、Problem647
 * 给你一个字符串 s，最多 可以从中删除一个字符。
 * 请你判断 s 是否能成为回文字符串：如果能，返回 true ；否则，返回 false 。
 * <p>
 * 输入：s = "aba"
 * 输出：true
 * <p>
 * 输入：s = "abca"
 * 输出：true
 * 解释：你可以删除字符 'c' 。
 * <p>
 * 输入：s = "abc"
 * 输出：false
 * <p>
 * 1 <= s.length <= 10^5
 * s 由小写英文字母组成
 */
public class Problem680 {
    public static void main(String[] args) {
        Problem680 problem680 = new Problem680();
        String s = "abdda";
        System.out.println(problem680.validPalindrome(s));
    }

    /**
     * 双指针
     * s[left]和s[right]不相等，则需要删除左指针或右指针字符，
     * 即判断s[left+1]-s[right]或s[left]-s[right-1]是否是回文串，如果有一个是回文串，则s删除一个字符是回文串
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public boolean validPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left <= right) {
            //s[left]和s[right]不相等，则需要删除左指针或右指针字符，
            //即判断s[left+1]-s[right]或s[left]-s[right-1]是否是回文串，如果有一个是回文串，则s删除一个字符是回文串
            if (s.charAt(left) != s.charAt(right)) {
                return isValid(s, left + 1, right) || isValid(s, left, right - 1);
            } else {
                left++;
                right--;
            }
        }

        return true;
    }

    /**
     * s[left]-s[right]是否是回文串
     * 时间复杂度O(right-left)，空间复杂度O(1)
     *
     * @param s
     * @param left
     * @param right
     * @return
     */
    private boolean isValid(String s, int left, int right) {
        while (left <= right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
            } else {
                return false;
            }
        }

        return true;
    }
}
