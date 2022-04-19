package com.zhang.java;

/**
 * @Date 2022/4/17 11:19
 * @Author zsy
 * @Description 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 * <p>
 * 输入：s = "(()"
 * 输出：2
 * 解释：最长有效括号子串是 "()"
 * <p>
 * 输入：s = ")()())"
 * 输出：4
 * 解释：最长有效括号子串是 "()()"
 * <p>
 * 输入：s = ""
 * 输出：0
 * <p>
 * 0 <= s.length <= 3 * 10^4
 * s[i] 为 '(' 或 ')'
 */
public class Problem032 {
    public static void main(String[] args) {
        Problem032 problem032 = new Problem032();
        String s = ")()(()))";
        System.out.println(problem032.longestValidParentheses(s));
        System.out.println(problem032.longestValidParentheses2(s));
    }

    /**
     * 动态规划，时间复杂度O(n)，空间复杂度O(n)
     * dp[i]：以s[i]结尾的最长有效括号长度
     * 1、dp[i] = 0                              (s[i] == '(')
     * 2、dp[i] = dp[i-2] + 2                    (s[i] == ')', s[i-1] == '(')
     * 3、dp[i] = dp[i-1] + 2 + dp[i-dp[i-1]-2]  (s[i] == ')', s[i-1] == ')', s[i-dp[i-1]-1] == '(')
     * 4、dp[i] = 0                              (s[i] == ')', s[i-1] == ')', s[i-dp[i-1]-1] == ')')
     * 情况3、4：当s[i]为')'，s[i-1]为')'，找到以s[i-1]结尾的最长有效括号的第一个字符的前一个字符s[i-dp[i-1]-1]，
     * 判断是否为'('，如果是，则s[i]和s[i-dp[i-1]-1]匹配，最长有效括号长度dp[i] = dp[i-dp[i-1]-2] + dp[i-1] + 2；
     * 如果s[i-dp[i-1]-1]不是')'，说明无法匹配，dp[i] = 0
     *
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }

        //以s[i]结尾的最长有效括号长度
        int[] dp = new int[s.length()];
        int max = 0;

        for (int i = 1; i < s.length(); i++) {
            //当前字符
            char c = s.charAt(i);
            //前一个字符
            char c2 = s.charAt(i - 1);

            if (c == ')') {
                //情况2
                if (c2 == '(') {
                    if (i >= 2) {
                        dp[i] = dp[i - 2] + 2;
                    } else {
                        dp[i] = 2;
                    }
                } else {
                    //情况3
                    if (i - dp[i - 1] - 1 >= 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                        if (i - dp[i - 1] - 2 >= 0) {
                            dp[i] = dp[i - 1] + 2 + dp[i - dp[i - 1] - 2];
                        } else {
                            dp[i] = dp[i - 1] + 2;
                        }
                    }
                }
            }

            max = Math.max(max, dp[i]);
        }

        return max;
    }

    /**
     * 贪心，双指针，两次循环，时间复杂度O(n)，空间复杂度O(1)
     * 第一次从左往右遍历，遇到'('left++，遇到')'right++，保证left>right，当left==right，计算括号长度
     * 第二次从右往左遍历，遇到'('left++，遇到')'right++，保证left<right，当left==right，计算括号长度
     * 第一次循环，有可能存在left始终大于right的情况，即"(()"，第二次循环则考虑到了这种情况
     *
     * @param s
     * @return
     */
    public int longestValidParentheses2(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }

        int max = 0;
        int left = 0;
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }

            if (left == right) {
                max = Math.max(max, left + right);
            } else if (left < right) {
                left = right = 0;
            }
        }

        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }

            if (left == right) {
                max = Math.max(max, left + right);
            } else if (left > right) {
                left = right = 0;
            }
        }

        return max;
    }
}
