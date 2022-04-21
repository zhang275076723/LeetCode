package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/10 10:23
 * @Author zsy
 * @Description 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，
 * 满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 * <p>
 * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * 输出: 3
 * 解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
 * <p>
 * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 * 输出: 5
 * 解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
 * <p>
 * 所有节点的值都是唯一的。
 * p、q 为不同节点且均存在于给定的二叉树中。
 */
public class Offer68_2 {
    public static void main(String[] args) {
        Offer68_2 offer68_2 = new Offer68_2();
        String[] data = {"3", "5", "1", "6", "2", "0", "8", "null", "null", "7", "4"};
        //root=3
        TreeNode root = offer68_2.builderTree(data);
        //p=5
        TreeNode p = root.left.right.right;
        //q=4
        TreeNode q = root.right.left;
//        TreeNode node = offer68_2.lowestCommonAncestor(root, p, q);
        TreeNode node = offer68_2.lowestCommonAncestor2(root, p, q);
        System.out.println(node.val);
    }

    /**
     * 自己的解，时间复杂度O(n)，空间复杂度O(n)
     * 遍历两遍，找到根节点到p和q的路径，路径最后一个相同的节点即为最近公共祖先
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

        TreeNode node = null;
        while (!pPath.isEmpty() && !qPath.isEmpty()) {
            if (pPath.get(0) == qPath.get(0)) {
                node = pPath.get(0);
                pPath.remove(0);
                qPath.remove(0);
            } else {
                break;
            }
        }
        return node;
    }

    /**
     * 递归找，时间复杂度O(n)，空间复杂度O(n)
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

        //p或q等于root，说明p或q最近公共祖先为root
        if (root == p || root == q) {
            return root;
        }

        //p或q是否在左子树这里
        TreeNode left = lowestCommonAncestor2(root.left, p, q);
        //p或q是否在右子树这里
        TreeNode right = lowestCommonAncestor2(root.right, p, q);

        //p和q一个在左子树一个在右子树，说明最近公共祖先为root
        if (left != null && right != null) {
            return root;
        }
        //p和q在左子树这里，说明最近公共祖先为left
        if (left != null) {
            return left;
        }
        //p和q在右子树这里，说明最近公共祖先为right
        if (right != null) {
            return right;
        }
        //p和q既不在左子树这里，也不在右子树这里
        return null;
    }

    /**
     * 先序遍历，得到根节点到当前节点的路径
     *
     * @param root
     * @param node
     * @param path
     */
    public void getPath(TreeNode root, TreeNode node, List<TreeNode> path) {
        if (root == null) {
            return;
        }

        path.add(root);
        if (root == node) {
            return;
        }

        //if作用：当找到node时，不再进行遍历，能够加快运行速度
        if (path.get(path.size() - 1) != node) {
            getPath(root.left, node, path);
        }
        if (path.get(path.size() - 1) != node) {
            getPath(root.right, node, path);
        }
        if (path.get(path.size() - 1) != node) {
            path.remove(path.size() - 1);
        }
    }

    public TreeNode builderTree(String[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        List<String> list = new ArrayList<>(Arrays.asList(data));
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(list.remove(0)));
        queue.add(root);

        while (list.size() >= 2) {
            TreeNode node = queue.remove();
            String leftValue = list.remove(0);
            String rightValue = list.remove(0);
            if (!"null".equals(leftValue)) {
                TreeNode leftNode = new TreeNode(Integer.parseInt(leftValue));
                node.left = leftNode;
                queue.add(leftNode);
            }
            if (!"null".equals(rightValue)) {
                TreeNode rightNode = new TreeNode(Integer.parseInt(rightValue));
                node.right = rightNode;
                queue.add(rightNode);
            }
        }

        return root;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
