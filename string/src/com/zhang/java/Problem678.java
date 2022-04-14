package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/3/15 21:39
 * @Author zsy
 * @Description 给定一个只包含三种字符的字符串：（，）和 *，写一个函数来检验这个字符串是否为有效字符串。
 * 有效字符串具有如下规则：
 * 1.任何左括号 (必须有相应的右括号 )。
 * 2.任何右括号 )必须有相应的左括号 (。
 * 3.左括号 ( 必须在对应的右括号之前 )。
 * 4.*可以被视为单个右括号 )，或单个左括号 (，或一个空字符串。
 * 5.一个空字符串也被视为有效字符串
 * <p>
 * 输入: "()"
 * 输出: True
 * <p>
 * 输入: "(*)"
 * 输出: True
 * <p>
 * 输入: "(*))"
 * 输出: True
 */
public class Problem678 {
    public static void main(String[] args) {
        Problem678 problem678 = new Problem678();
        String s = "(*())";
        System.out.println(problem678.checkValidString(s));
        System.out.println(problem678.checkValidString2(s));
        System.out.println(problem678.checkValidString3(s));
    }

    /**
     * 动态规划，时间复杂度O(n^3)，空间复杂的O(n^2)
     * dp[i][j] 表示字符串 s 从下标 i 到 j 的子串是否为有效的括号字符串，其中 0 ≤ i ≤ j < n
     * 情况：
     * 1、当 s 长度为1时， * 才有效
     * 2、当 s 长度为2时， () (* *) ** 才有效
     * 3、1)当 s 长度大于2时，s[i]为 ( 或 * ，s[j]为 ) 或 * ，s[i+1][j-1] = true时，s[i][j]才有效
     * 3、2)当 s 长度大于2时，i ≤ k < j，s[i][k-1]和s[k+1][j]都为true时，s[i][j]才有效
     *
     * @param s
     * @return dp[0][s.length() - 1]
     */
    public boolean checkValidString(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        char c1;
        char c2;

        //情况1
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '*') {
                dp[i][i] = true;
            }
        }

        //情况2
        for (int i = 1; i < s.length(); i++) {
            c1 = s.charAt(i - 1);
            c2 = s.charAt(i);
            dp[i - 1][i] = (c1 == '(' || c1 == '*') && (c2 == ')' || c2 == '*');
        }

        //情况3
        for (int i = 2; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                c1 = s.charAt(j - i);
                c2 = s.charAt(j);
                //情况3、1
                if ((c1 == '(' || c1 == '*') && (c2 == ')' || c2 == '*') && dp[j - i + 1][j - 1]) {
                    dp[j - i][j] = true;
                    continue;
                }
                //情况3、2
                for (int k = j - i; k < j; k++) {
                    if (dp[j - i][k] && dp[k + 1][j]) {
                        dp[j - i][j] = true;
                        break;
                    }
                }
            }
        }

        return dp[0][s.length() - 1];
    }

    /**
     * 使用符号栈，时间复杂度O(n)，空间复杂的O(n)
     * 一个栈存放 ( ，一个栈存放 *
     *
     * @param s
     * @return
     */
    public boolean checkValidString2(String s) {
        //存放 (
        Stack<Integer> stack1 = new Stack<>();
        //存放 *
        Stack<Integer> stack2 = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                //如果是 ( ，入栈stack1
                stack1.push(i);
            } else if (c == '*') {
                //如果是 * ，入栈stack1
                stack2.push(i);
            } else {
                //如果是 ) ，先看stack1是否为空，如果为空，再看stack2是否为空，如果为空，返回false
                if (stack1.empty()) {
                    if (stack2.empty()) {
                        return false;
                    } else {
                        //stack2中 * 匹配 )
                        stack2.pop();
                    }
                } else {
                    //stack1中 ( 匹配 )
                    stack1.pop();
                }
            }
        }

        //遍历完之后，看两个栈的情况
        //1、stack1和stack2都不为空，则需要用stack2中的 * 匹配stack1中的 ( ，必须保证 * 的索引大于 ( 的索引
        //2、stack1为空，stack2不为空，返回true
        //3、stack1不为空，stack2为空，返回false
        while (!stack1.empty() && !stack2.empty()) {
            int c1 = stack1.pop();
            int c2 = stack2.pop();
            if (c1 > c2) {
                return false;
            }
        }
        return stack1.empty();
    }

    /**
     * 贪心，时间复杂度O(n)，空间复杂的O(1)
     * 维护未匹配的 ( 数量可能的最小值和最大值:
     * 1、遇到 ( ，最小值和最大值分别加1
     * 2、遇到 ) ，最小值和最大值分别减1
     * 3、遇到 * ，最小值减1，将最大值1
     *
     * @param s
     * @return
     */
    public boolean checkValidString3(String s) {
        int min = 0;
        int max = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                min++;
                max++;
            } else if (c == ')') {
                //未匹配的 ( 的min最小值为0，表示( 和 ) 正好完全匹配
                //如果min为负，说明把 * 当做 ) 和 ( 匹配，此时将 * 当做 ( ，使min最小值为0
                min = Math.max(min - 1, 0);
                max--;

                //当未匹配的 ( 的max小于0，说明没有 ( 可以和 ) 匹配，返回false
                if (max < 0) {
                    return false;
                }
            } else {
                //未匹配的 ( 的min最小值为0，表示( 和 ) 正好完全匹配
                //如果min为负，说明把 * 当做 ) 和 ( 匹配，此时将 * 当做 ( ，使min最小值为0
                min = Math.max(min - 1, 0);
                max++;
            }
        }

        //min为0，说明 ( 都和 ) 匹配，返回true
        return min == 0;
    }
}
