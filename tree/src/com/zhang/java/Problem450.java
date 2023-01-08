package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/10/7 10:56
 * @Author zsy
 * @Description 删除二叉搜索树中的节点 类比Problem173、Problem700、Problem701
 * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。
 * 返回二叉搜索树（有可能被更新）的根节点的引用。
 * 一般来说，删除节点可分为两个步骤：
 * 1、首先找到需要删除的节点；
 * 2、如果找到了，删除它。
 * <p>
 * 输入：root = [5,3,6,2,4,null,7], key = 3
 * 输出：[5,4,6,2,null,null,7]
 * 解释：给定需要删除的节点值是 3，所以我们首先找到 3 这个节点，然后删除它。
 * <p>
 * 输入: root = [5,3,6,2,4,null,7], key = 0
 * 输出: [5,3,6,2,4,null,7]
 * 解释: 二叉树不包含值为 0 的节点
 * <p>
 * 输入: root = [], key = 0
 * 输出: []
 * <p>
 * 节点数的范围 [0, 10^4].
 * -10^5 <= Node.val <= 10^5
 * 节点值唯一
 * root 是合法的二叉搜索树
 * -10^5 <= key <= 10^5
 */
public class Problem450 {
    public static void main(String[] args) {
        Problem450 problem450 = new Problem450();
        String[] data = {"5", "3", "6", "2", "4", "null", "7"};
        TreeNode root = problem450.buildTree(data);
        int key = 7;
        root = problem450.deleteNode(root, key);
//        root = problem450.deleteNode2(root, key);
    }

    /**
     * 递归
     * 找到要删除节点右子树的最左下节点，用右子树的最左下节点的值替换要删除节点的值，递归删除右子树的最左下节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param key
     * @return
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        //往右找
        if (root.val < key) {
            root.right = deleteNode(root.right, key);
            return root;
        } else if (root.val > key) {
            //往左找
            root.left = deleteNode(root.left, key);
            return root;
        } else {
            //找到要删除的节点

            //要删除节点的左右子树为空，直接返回null
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                //要删除节点的左子树为空，直接返回右子树
                return root.right;
            } else if (root.right == null) {
                //要删除节点的右子树为空，直接返回左子树
                return root.left;
            } else {
                //要删除节点的左右子树均不为空，找到要删除节点右子树的最左下节点，进行替换，再递归删除右子树的最左下节点

                //要删除节点右子树的最左下节点
                TreeNode mostLeftNode = root.right;

                while (mostLeftNode.left != null) {
                    mostLeftNode = mostLeftNode.left;
                }

                //将mostLeftChild节点值赋值给root节点
                root.val = mostLeftNode.val;

                //递归删除右子树的最左下节点mostLeftChild
                root.right = deleteNode(root.right, mostLeftNode.val);

                return root;
            }
        }
    }

    /**
     * 非递归
     * 找到要删除节点右子树的最左下节点，进行替换删除
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param root
     * @param key
     * @return
     */
    public TreeNode deleteNode2(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        TreeNode node = root;
        //node节点的父节点，用于删除node
        TreeNode parentNode = null;

        while (node != null) {
            //往右找
            if (node.val < key) {
                parentNode = node;
                node = node.right;
            } else if (node.val > key) {
                //往左找
                parentNode = node;
                node = node.left;
            } else {
                //找到要删除的节点，分情况讨论

                //要删除节点的左右子树均为null
                if (node.left == null && node.right == null) {
                    //要删除的节点为根节点，直接返回null
                    if (root == node) {
                        return null;
                    }

                    if (parentNode.val < node.val) {
                        parentNode.right = null;
                        return root;
                    } else {
                        parentNode.left = null;
                        return root;
                    }
                } else if (node.left == null) {
                    //要删除节点的左子树为null

                    //要删除的节点为根节点，直接返回root.right
                    if (root == node) {
                        return root.right;
                    }

                    if (parentNode.val < node.val) {
                        parentNode.right = node.right;
                        return root;
                    } else {
                        parentNode.left = node.right;
                        return root;
                    }
                } else if (node.right == null) {
                    //要删除节点的右子树为null

                    //要删除的节点为根节点，直接返回root.left
                    if (root == node) {
                        return root.left;
                    }

                    if (parentNode.val < node.val) {
                        parentNode.right = node.left;
                        return root;
                    } else {
                        parentNode.left = node.left;
                        return root;
                    }
                } else {
                    //要删除节点的左右子树均不为null

                    //要删除节点右子树的最左下节点
                    TreeNode mostLeftChild = node.right;
                    //要删除节点右子树的最左下节点的父节点
                    TreeNode mostLeftChildParent = node;

                    while (mostLeftChild.left != null) {
                        mostLeftChildParent = mostLeftChild;
                        mostLeftChild = mostLeftChild.left;
                    }

                    //修改当前节点的值为右子树的最左下节点的值
                    node.val = mostLeftChild.val;

                    //要删除节点就是右子树的最左下节点的父节点
                    if (mostLeftChildParent == node) {
                        mostLeftChildParent.right = mostLeftChild.right;
                    } else {
                        mostLeftChildParent.left = mostLeftChild.right;
                    }

                    return root;
                }
            }
        }

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
