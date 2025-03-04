package com.zhang.java;

/**
 * @Date 2024/6/3 08:20
 * @Author zsy
 * @Description 从字符串生成二叉树 类比Problem606、Problem652、Problem1948 分治法类比
 * 你需要用一个包括括号和整数的字符串构建一棵二叉树。
 * 输入的字符串代表一棵二叉树。它包括整数和随后的 0 、1 或 2 对括号。
 * 整数代表根的值，一对括号内表示同样结构的子树。
 * 若存在子结点，则从左子结点开始构建。
 * <p>
 * 输入： s = "4(2(3)(1))(6(5))"
 * 输出： [4,2,6,3,1,5]
 * <p>
 * 输入： s = "4(2(3)(1))(6(5)(7))"
 * 输出： [4,2,6,3,1,5,7]
 * <p>
 * 输入： s = "-4(2(3)(1))(6(5)(7))"
 * 输出： [-4,2,6,3,1,5,7]
 * <p>
 * 0 <= s.length <= 3 * 10^4
 * 输入字符串中只包含 '(', ')', '-' 和 '0' ~ '9'
 * 空树由 "" 而非"()"表示。
 */
public class Problem536 {
    public static void main(String[] args) {
        Problem536 problem536 = new Problem536();
        String s = "4(2(3)(1))(6(5))";
        TreeNode root = problem536.str2tree(s);
    }

    /**
     * 分治法
     * 找s中第一个'('下标索引和第一个'('匹配的')'下标索引，确定当前树的根节点和左右子树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public TreeNode str2tree(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        return dfs(s, 0, s.length() - 1);
    }

    private TreeNode dfs(String s, int left, int right) {
        if (left > right) {
            return null;
        }

        if (left == right) {
            return new TreeNode(Integer.parseInt(s.substring(left, right + 1)));
        }

        //从s[left]往后第一个'('的下标索引
        int index1 = s.indexOf('(', left);

        //从s[left]往后不存在'('，则s[left]-s[right]为一个节点
        if (index1 == -1) {
            return new TreeNode(Integer.parseInt(s.substring(left, right + 1)));
        }

        //根节点
        TreeNode root = new TreeNode(Integer.parseInt(s.substring(left, index1)));

        //和第一个'('匹配的')'的下标索引
        int index2 = index1;
        //当前未和')'匹配的'('个数
        int count = 0;

        //找和第一个'('匹配的')'的下标索引
        while (index2 <= right) {
            char c = s.charAt(index2);

            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
            }

            if (count == 0) {
                break;
            }

            index2++;
        }

        root.left = dfs(s, index1 + 1, index2 - 1);
        root.right = dfs(s, index2 + 2, right - 1);

        return root;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
