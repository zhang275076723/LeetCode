package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/13 10:38
 * @Author zsy
 * @Description 查找两棵二叉搜索树之和 类比Problem173、Problem538、Problem653、Offer54 双指针类比
 * 给出两棵二叉搜索树的根节点 root1 和 root2 ，请你从两棵树中各找出一个节点，使得这两个节点的值之和等于目标值 Target。
 * 如果可以找到返回 True，否则返回 False。
 * <p>
 * 输入：root1 = [2,1,4], root2 = [1,0,3], target = 5
 * 输出：true
 * 解释：2 加 3 和为 5 。
 * <p>
 * 输入：root1 = [0,-10,10], root2 = [5,1,7,0,2], target = 18
 * 输出：false
 * <p>
 * 每棵树上节点数在 [1, 5000] 范围内。
 * -10^9 <= Node.val, target <= 10^9
 */
public class Problem1214 {
    public static void main(String[] args) {
        Problem1214 problem1214 = new Problem1214();
        String[] data1 = {"2", "1", "4"};
        String[] data2 = {"1", "0", "3"};
        TreeNode root1 = problem1214.buildTree(data1);
        TreeNode root2 = problem1214.buildTree(data2);
        int target = 5;
        System.out.println(problem1214.twoSumBSTs(root1, root2, target));
        System.out.println(problem1214.twoSumBSTs2(root1, root2, target));
    }

    /**
     * 中序遍历+二分查找
     * 时间复杂度(m+n)，空间复杂度O(m+n) (m：root1中节点个数，n：root2中节点个数)
     *
     * @param root1
     * @param root2
     * @param target
     * @return
     */
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        //存储root1中序遍历节点值
        List<Integer> list1 = new ArrayList<>();
        //存储root2中序遍历节点值
        List<Integer> list2 = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        TreeNode node1 = root1;
        TreeNode node2 = root2;

        //非递归root1中序遍历
        while (!stack1.isEmpty() || node1 != null) {
            while (node1 != null) {
                stack1.push(node1);
                node1 = node1.left;
            }

            node1 = stack1.pop();
            list1.add(node1.val);
            node1 = node1.right;
        }

        //非递归root2中序遍历
        while (!stack2.isEmpty() || node2 != null) {
            while (node2 != null) {
                stack2.push(node2);
                node2 = node2.left;
            }

            node2 = stack2.pop();
            list2.add(node2.val);
            node2 = node2.right;
        }

        int left = 0;
        int right = list2.size() - 1;

        while (left < right) {
            if (list1.get(left) + list2.get(right) == target) {
                return true;
            } else if (list1.get(left) + list2.get(right) < target) {
                left++;
            } else {
                right--;
            }
        }

        return false;
    }

    /**
     * 中序遍历+逆序中序遍历+二分查找
     * 在移动左右指针的过程中，动态得到非递归中序遍历和非递归逆序中序遍历的下一个元素
     * 时间复杂度(m+n)，空间复杂度O(m+n) (m：root1中节点个数，n：root2中节点个数)
     *
     * @param root1
     * @param root2
     * @param target
     * @return
     */
    public boolean twoSumBSTs2(TreeNode root1, TreeNode root2, int target) {
        //非递归中序遍历栈
        Stack<TreeNode> stack1 = new Stack<>();
        //非递归逆序中序遍历栈
        Stack<TreeNode> stack2 = new Stack<>();
        //非递归中序遍历的当前节点
        TreeNode node1 = root1;
        //非递归逆序中序遍历的当前节点
        TreeNode node2 = root2;

        //中序遍历当前节点的左子节点依次入栈
        while (node1 != null) {
            stack1.push(node1);
            node1 = node1.left;
        }

        //逆序中序遍历当前节点的右子节点依次入栈
        while (node2 != null) {
            stack2.push(node2);
            node2 = node2.right;
        }

        node1 = stack1.pop();
        //中序遍历的当前节点值作为左指针
        int left = node1.val;
        //非递归中序遍历当前节点node1指向右子节点
        node1 = node1.right;

        node2 = stack2.pop();
        //逆序中序遍历的当前节点值作为右指针
        int right = node2.val;
        //非递归逆序中序遍历当前节点node2指向左子节点
        node2 = node2.left;

        while (left < right) {
            if (left + right == target) {
                return true;
            } else if (left + right < target) {
                //通过非递归中序遍历，左指针右移
                while (node1 != null) {
                    stack1.push(node1);
                    node1 = node1.left;
                }
                node1 = stack1.pop();
                left = node1.val;
                node1 = node1.right;
            } else {
                //通过非递归逆序中序遍历，右指针左移
                while (node2 != null) {
                    stack2.push(node2);
                    node2 = node2.right;
                }
                node2 = stack2.pop();
                right = node2.val;
                node2 = node2.left;
            }
        }

        return false;
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
