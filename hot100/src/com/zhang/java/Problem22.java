package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2022/4/16 8:31
 * @Author zsy
 * @Description 括号生成 类比Problem20、Problem32、Problem301、Problem678
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * <p>
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 * <p>
 * 输入：n = 1
 * 输出：["()"]
 * <p>
 * 1 <= n <= 8
 */
public class Problem22 {
    public static void main(String[] args) {
        Problem22 problem22 = new Problem22();
        System.out.println(problem22.generateParenthesis(2));
        System.out.println(problem22.generateParenthesis2(3));
    }

    /**
     * 回溯+剪枝，时间复杂度O(4^n/(n)^(1/2))(第n个卡特兰)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        backtrack(0, 0, n, new StringBuilder(), result);
        return result;
    }

    /**
     * bfs实现
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis2(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(0, 0, ""));

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            //当前节点的左括号数量小于右括号数量，则不是有效括号
            if (node.left < node.right) {
                continue;
            }
            if (node.left == n && node.right == n) {
                result.add(node.s);
                continue;
            }

            //当前节点左括号数量小于n时，添加一个左括号节点
            if (node.left < n) {
                queue.add(new Node(node.left + 1, node.right, node.s + '('));
            }
            //当前节点右括号数量小于n时，添加一个右括号节点
            if (node.right < n) {
                queue.add(new Node(node.left, node.right + 1, node.s + ')'));
            }
        }

        return result;
    }

    /**
     * @param left   当前左括号数量
     * @param right  当前右括号数量
     * @param n      所需的括号对数
     * @param sb     一个合法的括号组合
     * @param result 返回结果集合
     */
    public void backtrack(int left, int right, int n, StringBuilder sb, List<String> result) {
        //当右括号数量和左括号数量都为n时，说明括号完全匹配
        if (left == n && right == n) {
            result.add(sb.toString());
            return;
        }

        //当前右括号数量大于左括号数量，则不合法，剪枝
        if (left < right) {
            return;
        }

        if (left < n) {
            sb.append('(');
            backtrack(left + 1, right, n, sb, result);
            sb.delete(sb.length() - 1, sb.length());
        }
        if (right < n) {
            sb.append(')');
            backtrack(left, right + 1, n, sb, result);
            sb.delete(sb.length() - 1, sb.length());
        }
    }

    /**
     * bfs中每个节点
     */
    private static class Node {
        //当前左括号数量
        int left;
        //当前右括号数量
        int right;
        //当前节点中的括号
        String s;

        public Node(int left, int right, String s) {
            this.left = left;
            this.right = right;
            this.s = s;
        }
    }
}
