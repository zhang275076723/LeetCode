package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/1 11:38
 * @Author zsy
 * @Description 二叉搜索树的最近公共祖先 类比Problem236、Problem270、Problem272、Problem285、Problem450、Problem510、Problem700、Problem701、Offer68、Offer68_2 同Offer68
 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，
 * 最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
 * 输出: 6
 * 解释: 节点 2 和节点 8 的最近公共祖先是 6。
 * <p>
 * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
 * 输出: 2
 * 解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
 * <p>
 * 所有节点的值都是唯一的。
 * p、q 为不同节点且均存在于给定的二叉搜索树中。
 */
public class Problem235 {
    public static void main(String[] args) {
        Problem235 problem235 = new Problem235();
//        String[] data = {"6", "2", "8", "0", "4", "7", "9", "null", "null", "3", "5"};
        String[] data = {"2", "1"};
        TreeNode root = problem235.buildTree(data);
//        TreeNode p = root.left;
//        TreeNode q = root.left.right;
        TreeNode p = root;
        TreeNode q = root.left;
        TreeNode commonNode = problem235.lowestCommonAncestor(root, p, q);
//        TreeNode commonNode = problem235.lowestCommonAncestor2(root, p, q);
//        TreeNode commonNode = problem235.lowestCommonAncestor3(root, p, q);
    }

    /**
     * 非递归
     * 最近公共祖先的值比p的值大，比q的值小
     * 时间复杂度O(n)，空间复杂度O(1)
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

        TreeNode node = root;

        while (node != null) {
            //往左子树找
            if (node.val > p.val && node.val > q.val) {
                node = node.left;
            } else if (node.val < p.val && node.val < q.val) {
                //往右子树找
                node = node.right;
            } else {
                //当前节点的值在p和q节点值之间，即找到最近公共祖先
                return node;
            }
        }

        //p、q不是树中节点，返回null，表示没有找到
        return null;
    }

    /**
     * 递归
     * 最近公共祖先的值比p的值大，比q的值小
     * 时间复杂度O(n)，空间复杂度O(n)
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

        //往左子树找
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor2(root.left, p, q);
        } else if (root.val < p.val && root.val < q.val) {
            //往右子树找
            return lowestCommonAncestor2(root.right, p, q);
        } else {
            //当前节点的值在p和q节点值之间，即找到最近公共祖先
            return root;
        }
    }

    /**
     * 得到root到p和root到q节点的路径，最后一个相同的节点即为最近公共祖先
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        List<TreeNode> pPath = getPath(root, p);
        List<TreeNode> qPath = getPath(root, q);

        int index = 0;

        //最后一个相同的节点即为最近公共祖先
        while (index < pPath.size() && index < qPath.size() && pPath.get(index) == qPath.get(index)) {
            index++;
        }

        return pPath.get(index - 1);
    }

    private List<TreeNode> getPath(TreeNode root, TreeNode node) {
        if (node == null) {
            return new ArrayList<>();
        }

        List<TreeNode> path = new ArrayList<>();
        TreeNode temp = root;

        while (temp != node) {
            path.add(temp);

            if (temp.val > node.val) {
                temp = temp.left;
            } else if (temp.val < node.val) {
                temp = temp.right;
            }
        }

        path.add(temp);
        return path;
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

        TreeNode(int x) {
            val = x;
        }
    }
}
