package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/28 9:15
 * @Author zsy
 * @Description 删除无效的括号 类比Problem20、Problem22、Problem32、Problem678 去重类比Problem40、Offer38
 * 给你一个由若干括号和字母组成的字符串 s ，删除最小数量的无效括号，使得输入的字符串有效。
 * 返回所有可能的结果。答案可以按 任意顺序 返回。
 * <p>
 * 输入：s = "()())()"
 * 输出：["(())()","()()()"]
 * <p>
 * 输入：s = "(a)())()"
 * 输出：["(a())()","(a)()()"]
 * <p>
 * 输入：s = ")("
 * 输出：[""]
 * <p>
 * 1 <= s.length <= 25
 * s 由小写英文字母以及括号 '(' 和 ')' 组成
 * s 中至多含 20 个括号
 */
public class Problem301 {
    public static void main(String[] args) {
        Problem301 problem301 = new Problem301();
        String s = "()())()";
//        String s = ")((())))))()(((l((((";
        System.out.println(problem301.removeInvalidParentheses(s));
        System.out.println(problem301.removeInvalidParentheses2(s));
    }

    /**
     * 回溯+剪枝，难点在于如何确定要删除的最少括号数量和去重
     * 先确定要删除的左括号和右括号数量，回溯遍历所有删除左括号和右括号的情况，判断是否是有效字符串，去重，加入到结果集合
     * 时间复杂度O(n*2^n)，空间复杂度O(n^2) (递归栈的深度O(n)，每次递归都需要复制字符串O(n))
     *
     * @param s
     * @return
     */
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();

        if ("".equals(s) || "()".equals(s)) {
            result.add(s);
            return result;
        }

        int left = 0;
        int right = 0;

        //确定要删除的左括号和右括号数量，保证是有效字符串
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else if (s.charAt(i) == ')') {
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }

        backtrack(0, left, right, s, result);

        return result;
    }

    /**
     * bfs
     * 先删除一个括号，判断是否是有效字符串，直至找到删除的最少无效括号数量
     * 时间复杂度O(n*2^n)，空间复杂度O(n*Cnm) (m为要删除的最少无效括号数量)
     *
     * @param s
     * @return
     */
    public List<String> removeInvalidParentheses2(String s) {
        List<String> result = new ArrayList<>();

        if ("".equals(s) || "()".equals(s)) {
            result.add(s);
            return result;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(s);
        //去重，保证加入到队列中的字符串都是不一样的
        Set<String> set = new HashSet<>();
        //是否找到了有效字符串，从删除一个括号开始
        boolean isFound = false;

        while (!queue.isEmpty()) {
            //当前第n层的节点个数
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String str = queue.poll();

                //判断当前字符串是否是有效字符串
                if (isValid(str)) {
                    result.add(str);
                    isFound = true;
                }

                //当前是最少删除无效括号的字符，不再删除括号
                if (isFound) {
                    continue;
                }

                //当前字符串不是有效字符串，继续删除无效括号
                for (int j = 0; j < str.length(); j++) {
                    //只删除'('或')'
                    if (str.charAt(j) == '(' || str.charAt(j) == ')') {
                        String newStr = str.substring(0, j) + str.substring(j + 1);
                        //删除括号后的字符串是否已经重复，去重
                        if (!set.contains(newStr)) {
                            queue.offer(newStr);
                            set.add(newStr);
                        }
                    }
                }
            }

            //如果第n层节点满足是删除最少的括号数量，则不需遍历第n+1层
            if (isFound) {
                break;
            }
        }

        return result;
    }

    /**
     * @param t      当前字符串的起始索引
     * @param left   要删除的左括号数量
     * @param right  要删除的右括号数量
     * @param str    当前字符串
     * @param result 删除最少无效括号之后的有效字符串结果集合
     */
    private void backtrack(int t, int left, int right, String str, List<String> result) {
        //如果要删除的左括号和右括号都为0，则找到要删除的最少无效括号数量，直接返回
        if (left == 0 && right == 0) {
            if (isValid(str)) {
                result.add(str);
            }
            return;
        }

        for (int i = t; i < str.length(); i++) {
            //去重，本次的括号和上次的括号相同，说明本次删除括号的情况和上次删除括号的情况一样，进行下次循环
            if (i > t && str.charAt(i) == str.charAt(i - 1)) {
                continue;
            }

            //删除一个左括号
            if (left > 0 && str.charAt(i) == '(') {
                backtrack(i, left - 1, right, str.substring(0, i) + str.substring(i + 1), result);
            }

            //删除一个右括号
            if (right > 0 && str.charAt(i) == ')') {
                backtrack(i, left, right - 1, str.substring(0, i) + str.substring(i + 1), result);
            }
        }
    }

    /**
     * 判断当前字符串是否有效
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param str
     * @return
     */
    private boolean isValid(String str) {
        //记录左括号的数量
        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                count++;
            } else if (str.charAt(i) == ')') {
                count--;
                //此时右括号数量比左括号数量多，不是有效字符串
                if (count < 0) {
                    return false;
                }
            }
        }

        //遍历结束，如果左括号和右括号数量相等，即count为0，才是有效字符串
        return count == 0;
    }
}