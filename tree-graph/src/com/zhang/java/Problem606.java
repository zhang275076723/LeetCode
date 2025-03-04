package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/6/2 08:34
 * @Author zsy
 * @Description 根据二叉树创建字符串 类比Problem536、Problem652、Problem1948
 * 给你二叉树的根节点 root ，请你采用前序遍历的方式，将二叉树转化为一个由括号和整数组成的字符串，返回构造出的字符串。
 * 空节点使用一对空括号对 "()" 表示，转化后需要省略所有不影响字符串与原始二叉树之间的一对一映射关系的空括号对。
 * <p>
 * 输入：root = [1,2,3,4]
 * 输出："1(2(4))(3)"
 * 解释：初步转化后得到 "1(2(4)())(3()())" ，但省略所有不必要的空括号对后，字符串应该是"1(2(4))(3)" 。
 * <p>
 * 输入：root = [1,2,3,null,4]
 * 输出："1(2()(4))(3)"
 * 解释：和第一个示例类似，但是无法省略第一个空括号对，否则会破坏输入与输出一一映射的关系。
 * <p>
 * 树中节点的数目范围是 [1, 10^4]
 * -1000 <= Node.val <= 1000
 */
public class Problem606 {
    public static void main(String[] args) {
        Problem606 problem606 = new Problem606();
//        String[] data = {"1", "2", "3", "4"};
        String[] data = {"1", "2", "3", "null", "4"};
        TreeNode root = problem606.buildTree(data);
        System.out.println(problem606.tree2str(root));
        System.out.println(problem606.tree2str2(root));
    }

    /**
     * 递归前序遍历
     * 1、root左右子树都为空，返回root
     * 2、root左子树为空，返回root()(right)
     * 3、root右子树为空，返回root(left)
     * 4、root左右子树都不为空，返回root(left)(right)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public String tree2str(TreeNode root) {
        if (root == null) {
            return "";
        }

        //1、root左右子树都为空，返回root
        if (root.left == null && root.right == null) {
            return root.val + "";
        }

        //2、root左子树为空，返回root()(right)
        if (root.left == null) {
            return root.val + "()(" + tree2str(root.right) + ")";
        }

        //3、root右子树为空，返回root(left)
        if (root.right == null) {
            return root.val + "(" + tree2str(root.left) + ")";
        }

        //4、root左右子树都不为空，返回root(left)(right)
        return root.val + "(" + tree2str(root.left) + ")(" + tree2str(root.right) + ")";
    }

    /**
     * 非递归前序遍历
     * 以当前节点为根节点前序遍历时，开始时添加'('，结束时添加')'，即每个节点需要进出栈两次，使用访问集合记录当前节点是第几次进出栈
     * 1、root左右子树都为空，返回root
     * 2、root左子树为空，返回root()(right)
     * 3、root右子树为空，返回root(left)
     * 4、root左右子树都不为空，返回root(left)(right)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public String tree2str2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        //节点访问集合，记录当前节点是第几次进出栈
        Set<TreeNode> visitedSet = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();

            //当前节点是第二次访问，即结束时添加')'
            if (visitedSet.contains(node)) {
                sb.append(')');
            } else {
                //当前节点是第一次访问，即开始时添加'('

                sb.append('(').append(node.val);
                visitedSet.add(node);
                //当前节点重新入栈
                stack.push(node);

                if (node.right != null) {
                    stack.push(node.right);
                }

                if (node.left != null) {
                    stack.push(node.left);
                }

                //2、root左子树为空，返回root()(right)，需要添加空左子树"()"
                if (node.left == null && node.right != null) {
                    sb.append("()");
                }
            }
        }

        //删除根节点开始和结束添加的"()"
        return sb.substring(1, sb.length() - 1);
    }

    private TreeNode buildTree(String[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        List<String> list = new ArrayList<>(Arrays.asList(data));
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(list.remove(0)));
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (!list.isEmpty()) {
                String leftValue = list.remove(0);
                if (!"null".equals(leftValue)) {
                    TreeNode leftNode = new TreeNode(Integer.parseInt(leftValue));
                    node.left = leftNode;
                    queue.offer(leftNode);
                }
            }
            if (!list.isEmpty()) {
                String rightValue = list.remove(0);
                if (!"null".equals(rightValue)) {
                    TreeNode rightNode = new TreeNode(Integer.parseInt(rightValue));
                    node.right = rightNode;
                    queue.offer(rightNode);
                }
            }
        }

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
