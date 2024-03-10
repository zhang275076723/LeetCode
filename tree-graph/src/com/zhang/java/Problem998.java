package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/8/12 09:13
 * @Author zsy
 * @Description 最大二叉树 II 类比Problem654 类比Problem156、Problem206、Problem226、Problem814
 * 最大树 定义：一棵树，并满足：其中每个节点的值都大于其子树中的任何其他值。
 * 给你最大树的根节点 root 和一个整数 val 。
 * 就像 之前的问题 那样，给定的树是利用 Construct(a) 例程从列表 a（root = Construct(a)）递归地构建的：
 * 如果 a 为空，返回 null 。
 * 否则，令 a[i] 作为 a 的最大元素。创建一个值为 a[i] 的根节点 root 。
 * root 的左子树将被构建为 Construct([a[0], a[1], ..., a[i - 1]]) 。
 * root 的右子树将被构建为 Construct([a[i + 1], a[i + 2], ..., a[a.length - 1]]) 。
 * 返回 root 。
 * 请注意，题目没有直接给出 a ，只是给出一个根节点 root = Construct(a) 。
 * 假设 b 是 a 的副本，并在末尾附加值 val。题目数据保证 b 中的值互不相同。
 * 返回 Construct(b) 。
 * <p>
 * 输入：root = [4,1,3,null,null,2], val = 5
 * 输出：[5,4,null,1,3,null,null,2]
 * 解释：a = [1,4,2,3], b = [1,4,2,3,5]
 * <p>
 * 输入：root = [5,2,4,null,1], val = 3
 * 输出：[5,2,4,null,1,null,3]
 * 解释：a = [2,1,5,4], b = [2,1,5,4,3]
 * <p>
 * 输入：root = [5,2,3,null,1], val = 4
 * 输出：[5,2,4,null,1,3]
 * 解释：a = [2,1,5,3], b = [2,1,5,3,4]
 * <p>
 * 树中节点数目在范围 [1, 100] 内
 * 1 <= Node.val <= 100
 * 树中的所有值 互不相同
 * 1 <= val <= 100
 */
public class Problem998 {
    public static void main(String[] args) {
        Problem998 problem998 = new Problem998();
        String[] data = {"4", "1", "3", "null", "null", "2"};
        TreeNode root = problem998.buildTree(data);
        int val = 5;
//        TreeNode newRoot = problem998.insertIntoMaxTree(root, val);
        TreeNode newRoot = problem998.insertIntoMaxTree2(root, val);
    }

    /**
     * 递归插入val
     * 如果val大于root.val，则val为根节点，val左子树为root；如果val小于root.val，则val为root右子树节点，往右子树递归
     * 注意：val是添加到原nums数组末尾
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        //val大于root.val，则val为根节点，val左子树为root
        if (val > root.val) {
            TreeNode newRoot = new TreeNode(val);
            //因为val置于数组末尾，所以根节点val的左子树为root
            newRoot.left = root;
            return newRoot;
        } else {
            //val小于root.val，则val为root右子树节点，往右子树递归
            root.right = insertIntoMaxTree(root.right, val);
            return root;
        }
    }

    /**
     * 非递归插入val
     * 如果val大于root.val，则parent右子树为val，val左子树为node，返回根节点；
     * 如果val小于root.val，则继续往node右子树找，更新parent
     * 注意：val是添加到原nums数组末尾
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoMaxTree2(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        //val大于root.val，则val为根节点，val左子树为root
        if (val > root.val) {
            TreeNode newRoot = new TreeNode(val);
            //因为val置于数组末尾，所以根节点val的左子树为root
            newRoot.left = root;
            return newRoot;
        }

        //当前遍历到的节点
        TreeNode node = root;
        //node节点的父节点
        TreeNode parent = null;
        //要插入的节点
        TreeNode insertNode = new TreeNode(val);

        while (node != null) {
            //val大于root.val，则parent右子树为val，val左子树为node，返回根节点
            if (val > node.val) {
                parent.right = insertNode;
                insertNode.left = node;
                return root;
            } else {
                //val小于root.val，则继续往node右子树找，更新parent
                parent = node;
                node = node.right;
            }
        }

        //遍历结束还没有找到val要插入的位置，则parent右子树为val
        parent.right = insertNode;

        return root;
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
