package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/12/30 08:17
 * @Author zsy
 * @Description 两数之和 IV - 输入二叉搜索树 类比Problem173、Problem538、Offer54 类比Problem1、Problem15、Problem16、Problem18、Problem167、Problem170、Problem454、Offer57
 * 给定一个二叉搜索树 root 和一个目标结果 k，如果二叉搜索树中存在两个元素且它们的和等于给定的目标结果，则返回 true。
 * <p>
 * 输入: root = [5,3,6,2,4,null,7], k = 9
 * 输出: true
 * <p>
 * 输入: root = [5,3,6,2,4,null,7], k = 28
 * 输出: false
 * <p>
 * 二叉树的节点个数的范围是  [1, 10^4].
 * -104 <= Node.val <= 10^4
 * 题目数据保证，输入的 root 是一棵 有效 的二叉搜索树
 * -10^5 <= k <= 10^5
 */
public class Problem653 {
    public static void main(String[] args) {
        Problem653 problem653 = new Problem653();
        String[] data = {"5", "3", "6", "2", "4", "null", "7"};
        TreeNode root = problem653.buildTree(data);
        int k = 9;
        System.out.println(problem653.findTarget(root, k));
        System.out.println(problem653.findTarget2(root, k));
        System.out.println(problem653.findTarget3(root, k));
        System.out.println(problem653.findTarget4(root, k));
    }

    /**
     * dfs(非递归前序遍历)+哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }

        Set<Integer> set = new HashSet<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        //非递归前序遍历
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);

                //set中包含k-node.val，则二叉树中存在两个节点和等于k，返回true
                if (set.contains(k - node.val)) {
                    return true;
                }

                //当前节点加入set
                set.add(node.val);
                node = node.left;
            }

            node = stack.pop();
            node = node.right;
        }

        //遍历结束，没有找到二叉树中两个节点和等于k的情况，返回false
        return false;
    }

    /**
     * bfs+哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget2(TreeNode root, int k) {
        if (root == null) {
            return false;
        }

        Set<Integer> set = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            //set中包含k-node.val，则二叉树中存在两个节点和等于k，返回true
            if (set.contains(k - node.val)) {
                return true;
            }

            //当前节点加入set
            set.add(node.val);

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        //遍历结束，没有找到二叉树中两个节点和等于k的情况，返回false
        return false;
    }

    /**
     * 中序遍历+双指针
     * 利用二叉搜索树中序遍历有序的性质，双指针分别指向中序遍历的第一个节点和最后一个节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget3(TreeNode root, int k) {
        if (root == null) {
            return false;
        }

        //存储中序遍历节点值
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        //非递归中序遍历
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            list.add(node.val);
            node = node.right;
        }

        int left = 0;
        int right = list.size() - 1;

        while (left < right) {
            int value1 = list.get(left);
            int value2 = list.get(right);

            if (value1 + value2 == k) {
                return true;
            } else if (value1 + value2 < k) {
                left++;
            } else {
                right--;
            }
        }

        return false;
    }

    /**
     * 中序遍历+逆序中序遍历+双指针
     * 利用二叉搜索树中序遍历有序的性质，双指针分别指向中序遍历的第一个节点和最后一个节点，
     * 在移动左右指针的过程中，动态得到非递归中序遍历和非递归逆序中序遍历的下一个元素
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget4(TreeNode root, int k) {
        if (root == null) {
            return false;
        }

        //非递归中序遍历栈
        Stack<TreeNode> stack1 = new Stack<>();
        //非递归逆序中序遍历栈
        Stack<TreeNode> stack2 = new Stack<>();
        //非递归中序遍历的当前节点
        TreeNode node1 = root;
        //非递归逆序中序遍历的当前节点
        TreeNode node2 = root;

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
            if (left + right == k) {
                return true;
            } else if (left + right < k) {
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
