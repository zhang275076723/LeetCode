package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/3/5 08:50
 * @Author zsy
 * @Description 二叉树的堂兄弟节点 II 类比Problem993 类比Problem199、Problem513、Problem515、Problem637、Problem662、Problem993、Problem1161、Problem1302
 * 给你一棵二叉树的根 root ，请你将每个节点的值替换成该节点的所有 堂兄弟节点值的和 。
 * 如果两个节点在树中有相同的深度且它们的父节点不同，那么它们互为 堂兄弟 。
 * 请你返回修改值之后，树的根 root 。
 * 注意，一个节点的深度指的是从树根节点到这个节点经过的边数。
 * <p>
 * 输入：root = [5,4,9,1,10,null,7]
 * 输出：[0,0,0,7,7,null,11]
 * 解释：上图展示了初始的二叉树和修改每个节点的值之后的二叉树。
 * - 值为 5 的节点没有堂兄弟，所以值修改为 0 。
 * - 值为 4 的节点没有堂兄弟，所以值修改为 0 。
 * - 值为 9 的节点没有堂兄弟，所以值修改为 0 。
 * - 值为 1 的节点有一个堂兄弟，值为 7 ，所以值修改为 7 。
 * - 值为 10 的节点有一个堂兄弟，值为 7 ，所以值修改为 7 。
 * - 值为 7 的节点有两个堂兄弟，值分别为 1 和 10 ，所以值修改为 11 。
 * <p>
 * 输入：root = [3,1,2]
 * 输出：[0,0,0]
 * 解释：上图展示了初始的二叉树和修改每个节点的值之后的二叉树。
 * - 值为 3 的节点没有堂兄弟，所以值修改为 0 。
 * - 值为 1 的节点没有堂兄弟，所以值修改为 0 。
 * - 值为 2 的节点没有堂兄弟，所以值修改为 0 。
 * <p>
 * 树中节点数目的范围是 [1, 10^5] 。
 * 1 <= Node.val <= 10^4
 */
public class Problem2641 {
    public static void main(String[] args) {
        Problem2641 problem2641 = new Problem2641();
        String[] data = {"5", "4", "9", "1", "10", "null", "7"};
        TreeNode root = problem2641.buildTree(data);
        root = problem2641.replaceValueInTree(root);
//        root = problem2641.replaceValueInTree2(root);
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode replaceValueInTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        //存储每层元素和的集合
        List<Integer> list = new ArrayList<>();

        //根节点为第0层
        dfs(root, 0, list);

        //根节点没有堂兄弟节点，修改后为0
        root.val = 0;

        dfs2(root, 0, list);

        return root;
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public TreeNode replaceValueInTree2(TreeNode root) {
        if (root == null) {
            return null;
        }

        //根节点没有堂兄弟节点，修改后为0
        root.val = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            //当前层节点集合
            List<TreeNode> list = new ArrayList<>();
            //当前层的下一层元素和
            int sum = 0;

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node);

                if (node.left != null) {
                    sum = sum + node.left.val;
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    sum = sum + node.right.val;
                    queue.offer(node.right);
                }
            }

            //已经得到当前层的下一层元素和，再次遍历当前层节点，更新当前层的下一层节点值
            for (TreeNode node : list) {
                //左子节点值，不存在左子节点，则为0
                int leftValue = node.left == null ? 0 : node.left.val;
                //右子节点值，不存在右子节点，则为0
                int rightValue = node.right == null ? 0 : node.right.val;

                //更新左子节点值
                if (node.left != null) {
                    node.left.val = sum - leftValue- rightValue;
                }
                //更新右子节点值
                if (node.right != null) {
                    node.right.val = sum - leftValue- rightValue;
                }
            }
        }

        return root;
    }

    private void dfs(TreeNode node, int level, List<Integer> list) {
        if (node == null) {
            return;
        }

        if (level == list.size()) {
            list.add(node.val);
        } else {
            list.set(level, list.get(level) + node.val);
        }

        dfs(node.left, level + 1, list);
        dfs(node.right, level + 1, list);
    }

    private void dfs2(TreeNode node, int level, List<Integer> list) {
        if (node == null) {
            return;
        }

        //左子节点值，不存在左子节点，则为0
        int leftValue = node.left == null ? 0 : node.left.val;
        //右子节点值，不存在右子节点，则为0
        int rightValue = node.right == null ? 0 : node.right.val;

        //更新左子节点值
        if (node.left != null) {
            node.left.val = list.get(level + 1) - leftValue - rightValue;
        }

        //更新右子节点值
        if (node.right != null) {
            node.right.val = list.get(level + 1) - leftValue - rightValue;
        }

        dfs2(node.left, level + 1, list);
        dfs2(node.right, level + 1, list);
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
