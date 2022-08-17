package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/10 8:48
 * @Author zsy
 * @Description 二叉搜索树的最近公共祖先 类比Offer68_2、Problem98、Problem236
 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，
 * 满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 * <p>
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
public class Offer68 {
    public static void main(String[] args) {
        Offer68 offer68 = new Offer68();
        String[] data = {"6", "2", "8", "0", "4", "7", "9", "null", "null", "3", "5"};
        //root=6
        TreeNode root = offer68.buildTree(data);
        //p=2
        TreeNode p = root.left;
        //q=4
        TreeNode q = root.left.right;
//        TreeNode node = offer68.lowestCommonAncestor(root, p, q);
//        TreeNode node = offer68.lowestCommonAncestor2(root, p, q);
        TreeNode node = offer68.lowestCommonAncestor3(root, p, q);
        System.out.println(node.val);
    }

    /**
     * 找到根节点到p和q的路径，遍历两个路径，路径最后一个相同的节点即为最近公共祖先
     * 时间复杂度O(n)，空间复杂度O(n)
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

        List<TreeNode> pPath = getPath(root, p);
        List<TreeNode> qPath = getPath(root, q);

        int index = 0;

        while (index < pPath.size() && index < qPath.size()) {
            if (pPath.get(index) == qPath.get(index)) {
                index++;
            } else {
                break;
            }
        }

        return pPath.get(index - 1);
    }

    /**
     * 递归找
     * 如果当前节点值大于p和q节点的值，则往当前节点左子树找
     * 如果当前节点值小于p和q节点的值，则往当前节点右子树找
     * 如果当前节点值在p和q节点的值之间，则找到近公共祖先，直接返回
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

        //根节点的值大于p和q的值，找根节点的左子树
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor2(root.left, p, q);
        }

        //根节点的值小于p和q的值，找根节点的右子树
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor2(root.right, p, q);
        }

        //p和q在根节点的两边，返回根节点
        return root;
    }

    /**
     * 非递归找
     * 如果当前节点值大于p和q节点的值，则往当前节点左子树找
     * 如果当前节点值小于p和q节点的值，则往当前节点右子树找
     * 如果当前节点值在p和q节点的值之间，则找到近公共祖先，直接返回
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode node = root;

        while (true) {
            //根节点的值大于p和q的值，找根节点的左子树
            if (node.val > p.val && node.val > q.val) {
                node = node.left;
            } else if (node.val < p.val && node.val < q.val) {
                //根节点的值小于p和q的值，找根节点的右子树
                node = node.right;
            } else {
                //p和q在根节点的两边，返回根节点
                return node;
            }
        }
    }

    /**
     * 得到根节点到当前节点的路径
     *
     * @param root
     * @param node
     * @return
     */
    private List<TreeNode> getPath(TreeNode root, TreeNode node) {
        List<TreeNode> list = new ArrayList<>();
        TreeNode temp = root;

        list.add(root);

        while (temp != node) {
            if (temp.val > node.val) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }

            list.add(temp);
        }

        return list;
    }

    public TreeNode buildTree(String[] data) {
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
