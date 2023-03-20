package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/3/20 10:20
 * @Author zsy
 * @Description 二叉树中第二小的节点 类比Problem230
 * 给定一个非空特殊的二叉树，每个节点都是正数，并且每个节点的子节点数量只能为 2 或 0。
 * 如果一个节点有两个子节点的话，那么该节点的值等于两个子节点中较小的一个。
 * 更正式地说，即 root.val = min(root.left.val, root.right.val) 总成立。
 * 给出这样的一个二叉树，你需要输出所有节点中的 第二小的值 。
 * 如果第二小的值不存在的话，输出 -1 。
 * <p>
 * 输入：root = [2,2,5,null,null,5,7]
 * 输出：5
 * 解释：最小的值是 2 ，第二小的值是 5 。
 * <p>
 * 输入：root = [2,2,2]
 * 输出：-1
 * 解释：最小的值是 2, 但是不存在第二小的值。
 * <p>
 * 树中节点数目在范围 [1, 25] 内
 * 1 <= Node.val <= 2^31 - 1
 * 对于树中每个节点 root.val == min(root.left.val, root.right.val)
 */
public class Problem671 {
    public static void main(String[] args) {
        Problem671 problem671 = new Problem671();
//        String[] data = {"2", "2", "5", "null", "null", "5", "7"};
        String[] data = {"1", "1", "3", "1", "2", "3", "4"};
        TreeNode root = problem671.buildTree(data);
        System.out.println(problem671.findSecondMinimumValue(root));
    }

    /**
     * 非递归前序遍历
     * 根节点的值即为当前树节点的最小值，找比根节点值大的所有节点中值最小的节点，即为第二小节点，如果没有找到返回-1
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param root
     * @return
     */
    public int findSecondMinimumValue(TreeNode root) {
        if (root == null) {
            return -1;
        }

        //第二小节点值
        int secondMinValue = Integer.MAX_VALUE;
        //是否存在第二小节点的值
        boolean flag = false;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                //当前节点值大于根节点值，更新第二小节点值
                if (node.val > root.val) {
                    flag = true;
                    secondMinValue = Math.min(secondMinValue, node.val);
                }
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            node = node.right;
        }

        //如果不存在第二小节点的值，返回-1
        return flag ? secondMinValue : -1;
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
