package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/15 09:25
 * @Author zsy
 * @Description 最接近的二叉搜索树值 类比Problem235、Problem236、Problem272、Problem285、Problem450、Problem510、Problem700、Problem701、Offer68、Offer68_2
 * 给你二叉搜索树的根节点 root 和一个目标值 target ，请在该二叉搜索树中找到最接近目标值 target 的数值。
 * 如果有多个答案，返回最小的那个。
 * <p>
 * 输入：root = [4,2,5,1,3], target = 3.714286
 * 输出：4
 * <p>
 * 输入：root = [1], target = 4.428571
 * 输出：1
 * <p>
 * 树中节点的数目在范围 [1, 10^4] 内
 * 0 <= Node.val <= 10^9
 * -10^9 <= target <= 10^9
 */
public class Problem270 {
    public static void main(String[] args) {
        Problem270 problem270 = new Problem270();
        String[] data = {"4", "2", "5", "1", "3"};
        TreeNode root = problem270.buildTree(data);
        double target = 3.714286;
        System.out.println(problem270.closestValue(root, target));
        System.out.println(problem270.closestValue2(root, target));
    }

    /**
     * 中序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param target
     * @return
     */
    public int closestValue(TreeNode root, double target) {
        //二叉搜索树中最接近target的值
        int result = root.val;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            if (Math.abs(node.val - target) < Math.abs(result - target)) {
                result = node.val;
            }

            node = node.right;
        }

        return result;
    }

    /**
     * 利用二叉搜索树性质
     * 如果当前节点小于target，则往右子树找最接近target的节点；否则，往左子树找最接近target的节点
     * 时间复杂度O(h)，空间复杂度O(1) (h=logn，即树的高度)
     *
     * @param root
     * @param target
     * @return
     */
    public int closestValue2(TreeNode root, double target) {
        //二叉搜索树中最接近target的值
        int result = root.val;
        TreeNode node = root;

        while (node != null) {
            if (Math.abs(node.val - target) < Math.abs(result - target)) {
                result = node.val;
            }

            if (node.val < target) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        return result;
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
