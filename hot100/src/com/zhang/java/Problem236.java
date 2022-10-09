package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/18 8:50
 * @Author zsy
 * @Description 二叉树的最近公共祖先 字节面试题 类比Problem235、Offer68 同Offer68_2
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，
 * 满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 * <p>
 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * 输出：3
 * 解释：节点 5 和节点 1 的最近公共祖先是节点 3 。
 * <p>
 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 * 输出：5
 * 解释：节点 5 和节点 4 的最近公共祖先是节点 5 。因为根据定义最近公共祖先节点可以为节点本身。
 * <p>
 * 输入：root = [1,2], p = 1, q = 2
 * 输出：1
 * <p>
 * 树中节点数目在范围 [2, 10^5] 内。
 * -10^9 <= Node.val <= 10^9
 * 所有 Node.val 互不相同 。
 * p != q
 * p 和 q 均存在于给定的二叉树中。
 */
public class Problem236 {
    public static void main(String[] args) {
        Problem236 problem236 = new Problem236();
        String[] data = {"3", "5", "1", "6", "2", "0", "8", "null", "null", "7", "4"};
        TreeNode root = problem236.buildTree(data);
        TreeNode p = root.left;
        TreeNode q = root.right;
//        TreeNode lowestCommonAncestor = problem236.lowestCommonAncestor(root, p, q);
        TreeNode lowestCommonAncestor = problem236.lowestCommonAncestor2(root, p, q);
        System.out.println(lowestCommonAncestor.val);
    }

    /**
     * 得到root到p和root到q节点的路径，最后一个相同的节点即为最近公共祖先
     * 时间复杂度O(n)，平均空间复杂度O(logn)，最坏空间复杂度O(n)
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        List<TreeNode> pPath = new ArrayList<>();
        List<TreeNode> qPath = new ArrayList<>();
        getPath(root, p, pPath);
        getPath(root, q, qPath);

        int index = 0;

        while (index < pPath.size() && index < qPath.size() && pPath.get(index) == qPath.get(index)) {
            index++;
        }

        return pPath.get(index - 1);
    }

    /**
     * 递归查找
     * 时间复杂度O(n)，平均空间复杂度O(logn)，最坏空间复杂度O(n)
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        //最近公共祖先为p或q
        if (root == p || root == q) {
            return root;
        }

        //p或q是否在左子树
        TreeNode left = lowestCommonAncestor2(root.left, p, q);
        //p或q是否在右子树
        TreeNode right = lowestCommonAncestor2(root.right, p, q);

        //p和q一个在左子树一个在右子树，说明最近公共祖先为root
        if (left != null && right != null) {
            return root;
        }

        //p和q都在左子树，说明最近公共祖先为left
        if (left != null) {
            return left;
        }

        //p和q都在右子树，说明最近公共祖先为right
        if (right != null) {
            return right;
        }

        //p和q既不在左子树这里，也不在右子树这里
        return null;
    }

    /**
     * 前序遍历，得到根节点到当前节点的路径
     * 时间复杂度O(n)，平均空间复杂度O(logn)，最坏空间复杂度O(n)
     *
     * @param root
     * @param node
     */
    private void getPath(TreeNode root, TreeNode node, List<TreeNode> path) {
        if (root == null) {
            return;
        }

        path.add(root);

        if (root == node) {
            return;
        }

        //只有在路径中最后一个节点不是node的情况下才继续寻找，已经找到，直接返回，相当于剪枝
        if (path.get(path.size() - 1) != node) {
            getPath(root.left, node, path);
        }

        //只有在路径中最后一个节点不是node的情况下才继续寻找，已经找到，直接返回，相当于剪枝
        if (path.get(path.size() - 1) != node) {
            getPath(root.right, node, path);
        }

        //只有在路径中最后一个节点不是node的情况下才继续寻找，已经找到，直接返回，相当于剪枝
        if (path.get(path.size() - 1) != node) {
            path.remove(path.size() - 1);
        }
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

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
