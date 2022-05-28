package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/28 9:15
 * @Author zsy
 * @Description 删除无效的括号 类比Problem20、Problem22、Problem32、Problem678
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
        System.out.println(problem301.removeInvalidParentheses(s));
        System.out.println(problem301.removeInvalidParentheses2(s));
    }

    /**
     * 回溯+剪枝
     * 确定要删除的左括号和右括号数量，回溯遍历所有删除左括号和右括号的情况，判断是否是有效字符串，去重，加入到结果集合
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

        //确定当前字符串要删除的最少左括号和右括号数量，才能保证是有效字符串
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else if (s.charAt(i) == ')') {
                if (left == 0) {
                    right++;
                } else {
                    left--;
                }
            }
        }

        backtrack(s, left, right, result, new HashSet<>());

        return result;
    }

    /**
     * bfs
     * 先从删除一个括号开始，判断是否是有效字符串，找到删除的最少无效括号数量
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
        //去重，保证加入到队列中的字符串都是不一样的
        Set<String> set = new HashSet<>();
        //是否找到了有效字符串，从删除一个括号开始
        boolean isFound = false;

        queue.offer(s);

        while (!queue.isEmpty()) {
            int size = queue.size();

            //删除i个无效括号的字符串
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
                            queue.add(newStr);
                            set.add(newStr);
                        }
                    }
                }
            }

            //当前删除i个无效括号的字符串是最少删除无效括号的有效字符串时，直接跳出循环
            if (isFound) {
                break;
            }
        }

        return result;
    }

    /**
     * @param str    当前字符串
     * @param left   要删除的左括号数量
     * @param right  要删除的右括号数量
     * @param result 删除最小括号之后的结果集合
     * @param set    用于结果去重
     */
    private void backtrack(String str, int left, int right, List<String> result, Set<String> set) {
        //如果要删除的左括号和右括号都为0，说明已经找到要删除的最少无效括号数量，直接返回
        if (left == 0 && right == 0) {
            //如果当前字符串是有效字符串，且不重复，才往结果集合中添加
            if (!set.contains(str) && isValid(str)) {
                set.add(str);
                result.add(str);
            }
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            //结果集合去重，本次的括号和上次的括号相同，说明本次删除括号的情况和上次删除括号的情况一样，剪枝，进行下次循环
            if (i != 0 && str.charAt(i) == str.charAt(i - 1)) {
                continue;
            }

            // 删除一个左括号
            if (left > 0 && str.charAt(i) == '(') {
                backtrack(str.substring(0, i) + str.substring(i + 1),
                        left - 1, right, result, set);
            }

            // 删除一个右括号
            if (right > 0 && str.charAt(i) == ')') {
                backtrack(str.substring(0, i) + str.substring(i + 1),
                        left, right - 1, result, set);
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
        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                count++;
            } else if (str.charAt(i) == ')') {
                count--;
            }
            if (count < 0) {
                return false;
            }
        }

        //遍历结束，如果左括号和右括号数量相等，即count为0，才是有效字符串
        return count == 0;
    }
}