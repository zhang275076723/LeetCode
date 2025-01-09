package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2024/10/3 08:08
 * @Author zsy
 * @Description 设计带解析函数的表达式树 Amazon面试题 栈类比Problem150
 * 给定一个算术表达式的后缀表示法的标记（token） postfix ，构造并返回该表达式对应的二叉表达式树。
 * 后缀表示法是一种将操作数写在运算符之前的表示法。例如，表达式 4*(5-(2+7)) 的后缀表示法表示为数组
 * postfix = ["4","5","7","2","+","-","*"] 。
 * 抽象类 Node 需要用于实现二叉表达式树。我们将通过 evaluate 函数来测试返回的树是否能够解析树中的值。
 * 你不可以移除 Node 类，但你可以按需修改此类，也可以定义其他类来实现它。
 * 二叉表达式树是一种表达算术表达式的二叉树。二叉表达式树中的每一个节点都有零个或两个子节点。
 * 叶节点（有 0 个子节点的节点）表示操作数，非叶节点（有 2 个子节点的节点）表示运算符：
 * '+' （加）、 '-' （减）、 '*' （乘）和 '/' （除）。
 * 我们保证任何子树对应值的绝对值不超过 10^9 ，且所有操作都是有效的（即没有除以零的操作）
 * 进阶： 你可以将表达式树设计得更模块化吗？例如，你的设计能够不修改现有的 evaluate 的实现就能支持更多的操作符吗？
 * <p>
 * 输入： s = ["3","4","+","2","*","7","/"]
 * 输出： 2
 * 解释： 此表达式可解析为上述二叉树，其对应表达式为 ((3+4)*2)/7) = 14/7 = 2.
 * <p>
 * 输入: s = ["4","5","7","2","+","-","*"]
 * 输出: -16
 * 解释: 此表达式可解析为上述二叉树，其对应表达式为 4*(5-(2+7)) = 4*(-4) = -16.
 * <p>
 * 输入: s = ["4","2","+","3","5","1","-","*","+"]
 * 输出: 18
 * <p>
 * 输入: s = ["100","200","+","2","/","5","*","7","+"]
 * 输出: 757
 * <p>
 * 1 <= s.length < 100
 * s.length 是奇数。
 * s 包含数字和字符 '+' 、 '-' 、 '*' 以及 '/' 。
 * 如果 s[i] 是数，则对应的整数不超过 10^5 。
 * s 保证是一个有效的表达式。
 * 结果值和所有过程值的绝对值均不超过 10^9 。
 * 保证表达式不包含除以零的操作。
 */
public class Problem1628 {
    public static void main(String[] args) {
        String[] postfix = {"4", "5", "7", "2", "+", "-", "*"};
        TreeBuilder treeBuilder = new TreeBuilder();
        Node expTree = treeBuilder.buildTree(postfix);
        System.out.println(expTree.evaluate());
    }

    /**
     * 栈
     */
    static class TreeBuilder {
        /**
         * 建立后缀表达树
         *
         * @param postfix
         * @return
         */
        Node buildTree(String[] postfix) {
            Stack<MyNode> stack = new Stack<>();

            for (String s : postfix) {
                MyNode myNode = new MyNode(s);

                //当前节点为运算符，出栈两个节点，作为当前节点的右左节点
                if ("+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s)) {
                    myNode.right = stack.pop();
                    myNode.left = stack.pop();
                    stack.push(myNode);
                } else {
                    stack.push(myNode);
                }
            }

            return stack.pop();
        }
    }

    /**
     * 自定义继承Node的节点
     */
    static class MyNode extends Node {
        public MyNode(String val) {
            this.val = val;
        }

        /**
         * 返回当前节点作为后缀表达树根节点表示的值
         *
         * @return
         */
        @Override
        public int evaluate() {
            //当前节点不为运算符，直接返回当前节点的值
            if (!"+".equals(this.val) && !"-".equals(this.val) && !"*".equals(this.val) && !"/".equals(this.val)) {
                return Integer.parseInt(this.val);
            }

            int leftValue = this.left.evaluate();
            int rightValue = this.right.evaluate();

            if ("+".equals(this.val)) {
                return leftValue + rightValue;
            } else if ("-".equals(this.val)) {
                return leftValue - rightValue;
            } else if ("*".equals(this.val)) {
                return leftValue * rightValue;
            } else if ("/".equals(this.val)) {
                return leftValue / rightValue;
            }

            return 0;
        }
    }

    static abstract class Node {
        public abstract int evaluate();

        // define your fields here
        protected String val;
        protected Node left;
        protected Node right;
    }
}
