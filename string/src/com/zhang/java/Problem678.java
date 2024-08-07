package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/3/15 21:39
 * @Author zsy
 * @Description 有效的括号字符串 括号类比Problem20、Problem22、Problem32、Problem301、Problem856、Problem1087、Problem1096 栈类比Problem20、Problem71、Problem150、Problem224、Problem227、Problem331、Problem341、Problem394、Problem856、Problem946、Problem1003、Problem1047、Problem1096、Offer31、CharacterToInteger
 * 给定一个只包含三种字符的字符串：（，）和 *，写一个函数来检验这个字符串是否为有效字符串。
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
 * <p>
 * 字符串大小将在 [1，100] 范围内。
 */
public class Problem678 {
    public static void main(String[] args) {
        Problem678 problem678 = new Problem678();
        String s = "(*())";
        System.out.println(problem678.checkValidString(s));
        System.out.println(problem678.checkValidString2(s));
    }

    /**
     * 双栈
     * 一个栈存放'('，另一个栈存放'*'，遇到')'先和'('匹配，再和'*'匹配
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public boolean checkValidString(String s) {
        //存放'('的索引下标
        Stack<Integer> stack1 = new Stack<>();
        //存放'*'的索引下标
        Stack<Integer> stack2 = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                //如果是'('，入栈stack1
                stack1.push(i);
            } else if (c == '*') {
                //如果是'*'，入栈stack2
                stack2.push(i);
            } else {
                //如果是')'，先和stack1中的'('匹配，再和stack2中的'*'匹配
                if (!stack1.isEmpty()) {
                    //stack1中'('匹配')'
                    stack1.pop();
                } else {
                    if (!stack2.isEmpty()) {
                        //stack2中'*'匹配')'
                        stack2.pop();
                    } else {
                        return false;
                    }
                }
            }
        }

        //s遍历完之后，看两个栈的情况
        //1、stack1和stack2都不为空，则需要用stack2中的'*'匹配stack1中的'('，必须保证'*'的索引大于'('的索引
        //2、stack1为空，stack2不为空，说明'*'有剩余，返回true
        //3、stack1不为空，stack2为空，说明还有未匹配的'('，返回false
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            int index1 = stack1.pop();
            int index2 = stack2.pop();

            //stack1中的'('下标索引index1比stack2中的'*'下标索引index2大，说明存在情况"*("，无法匹配，返回false
            if (index1 > index2) {
                return false;
            }
        }

        //双栈遍历之后，如果左括号栈为空，则是有效括号字符串，返回true；左括号栈不为空，则不是有效括号字符串，返回false
        return stack1.isEmpty();
    }

    /**
     * 贪心 (不理解)
     * 维护未匹配的 ( 数量可能的最小值和最大值:
     * 1、遇到 ( ，最小值和最大值分别加1
     * 2、遇到 ) ，最小值和最大值分别减1
     * 3、遇到 * ，最小值减1，将最大值1
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public boolean checkValidString2(String s) {
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
