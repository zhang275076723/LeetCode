package com.zhang.java;

/**
 * @Date 2023/2/17 08:23
 * @Author zsy
 * @Description 验证回文串 回文类比Problem5、Problem9、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem647、Problem680、Problem866、Problem1312、Problem1332
 * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
 * 字母和数字都属于字母数字字符。
 * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
 * <p>
 * 输入: s = "A man, a plan, a canal: Panama"
 * 输出：true
 * 解释："amanaplanacanalpanama" 是回文串。
 * <p>
 * 输入：s = "race a car"
 * 输出：false
 * 解释："raceacar" 不是回文串。
 * <p>
 * 输入：s = " "
 * 输出：true
 * 解释：在移除非字母数字字符之后，s 是一个空字符串 "" 。
 * 由于空字符串正着反着读都一样，所以是回文串。
 * <p>
 * 1 <= s.length <= 2 * 10^5
 * s 仅由可打印的 ASCII 字符组成
 */
public class Problem125 {
    public static void main(String[] args) {
        Problem125 problem125 = new Problem125();
        String s = "A man, a plan, a canal: Panama";
        System.out.println(problem125.isPalindrome(s));
    }

    /**
     * 双指针
     * 字符串s去掉非字母数字字符，大写字符转换为小写字符之后，双指针判断是否是回文串
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                //大写字符转换为小写字符
                if (c >= 'A' && c <= 'Z') {
                    c = (char) (c + 'a' - 'A');
                }

                sb.append(c);
            }
        }

        int left = 0;
        int right = sb.length() - 1;

        //双指针从两端判断是否是回文串
        while (left <= right) {
            if (sb.charAt(left) == sb.charAt(right)) {
                left++;
                right--;
            } else {
                return false;
            }
        }

        return true;
    }
}
