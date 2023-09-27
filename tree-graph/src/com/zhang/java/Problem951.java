package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/6/29 11:13
 * @Author zsy
 * @Description 翻转等价二叉树 类比Problem100、Problem101、Problem226、Problem572、Problem1367、Offer26、Offer27、Offer28
 * 我们可以为二叉树 T 定义一个 翻转操作 ，如下所示：选择任意节点，然后交换它的左子树和右子树。
 * 只要经过一定次数的翻转操作后，能使 X 等于 Y，我们就称二叉树 X 翻转 等价 于二叉树 Y。
 * 这些树由根节点 root1 和 root2 给出。
 * 如果两个二叉树是否是翻转 等价 的函数，则返回 true ，否则返回 false 。
 * <p>
 * 输入：root1 = [1,2,3,4,5,6,null,null,null,7,8], root2 = [1,3,2,null,6,4,5,null,null,null,null,8,7]
 * 输出：true
 * 解释：我们翻转值为 1，3 以及 5 的三个节点。
 * <p>
 * 输入: root1 = [], root2 = []
 * 输出: true
 * <p>
 * 输入: root1 = [], root2 = [1]
 * 输出: false
 * <p>
 * 每棵树节点数在 [0, 100] 范围内
 * 每棵树中的每个值都是唯一的、在 [0, 99] 范围内的整数
 */
public class Problem951 {
    public static void main(String[] args) {
        Problem951 problem951 = new Problem951();
        String[] data1 = {"1", "2", "3", "4", "5", "6", "null", "null", "null", "7", "8"};
        String[] data2 = {"1", "3", "2", "null", "6", "4", "5", "null", "null", "null", "null", "8", "7"};
        TreeNode root1 = problem951.buildTree(data1);
        TreeNode root2 = problem951.buildTree(data2);
        System.out.println(problem951.flipEquiv(root1, root2));
    }

    /**
     * dfs
     * 时间复杂度O(min(m,n))，平均空间复杂度O(min(m,n)) (m：root1节点个数，n：root2节点个数)
     *
     * @param root1
     * @param root2
     * @return
     */
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        //root1和root2都为空，则是翻转等价二叉树，返回true
        if (root1 == null && root2 == null) {
            return true;
        }

        //root1、root2有个一为空，或者root1和root2值不同，则不是翻转等价二叉树，返回false
        if (root1 == null || root2 == null || root1.val != root2.val) {
            return false;
        }

        //递归判断root1左子树和root2左子树是否是翻转等价二叉树，root1右子树和root2右子树是否是翻转等价二叉树，
        //或者递归判断root1左子树和root2右子树是否是翻转等价二叉树，root1右子树和root2左子树是否是翻转等价二叉树
        return (flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right)) ||
                (flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left));
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
