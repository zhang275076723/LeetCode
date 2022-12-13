package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/1 9:24
 * @Author zsy
 * @Description 二叉搜索树中第K小的元素 字节面试题 类比Problem95、Problem96、Problem98、Problem99、Offer33、Offer36、Offer54 二分搜索树类比Problem440  线段树类比Problem307、Problem729、Problem731、Problem732
 * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，
 * 请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）。
 * <p>
 * 输入：root = [3,1,4,null,2], k = 1
 * 输出：1
 * <p>
 * 输入：root = [5,3,6,2,4,null,null,1], k = 3
 * 输出：3
 * <p>
 * 树中的节点数为 n 。
 * 1 <= k <= n <= 10^4
 * 0 <= Node.val <= 10^4
 */
public class Problem230 {
    public static void main(String[] args) {
        Problem230 problem230 = new Problem230();
        String[] data = {"5", "3", "6", "2", "4", "null", "null", "1"};
        TreeNode root = problem230.buildTree(data);
        System.out.println(problem230.kthSmallest(root, 3));
        System.out.println(problem230.kthSmallest2(root, 3));
    }

    /**
     * 中序遍历
     * 二叉搜索树的中序遍历是按照节点值的由小到大遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            k--;

            if (k == 0) {
                return node.val;
            }

            node = node.right;
        }

        //没有找到第k小节点，返回-1
        return -1;
    }

    /**
     * 优化，记录以每个节点为根节点的子节点个数，用于频繁查找二叉搜索树的第k小节点值的情况
     * 1、当前节点左节点为根节点的节点个数left+1小于k，则第k小节点在当前节点的右子树，查找当前节点的右子树的第k-left-1小
     * 2、当前节点左节点为根节点的节点个数left+1大于k，则第k小节点在当前节点的左子树，查找当前节点的左子树的第k小
     * 3、当前节点左节点为根节点的节点个数left+1等于k，则第k小节点即为当前节点，返回当前节点
     * 时间复杂度O(n)，空间复杂度O(n) (第一次查询时间复杂度O(n)，之后每次查询时间复杂度O(logn))
     *
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest2(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }

        MyTree myTree = new MyTree(root);

        return myTree.getMinKVal(k);
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

    /**
     * 记录以每个节点为根节点的节点个数的树
     */
    private static class MyTree {
        private TreeNode root;

        /**
         * 记录每个节点为根节点的节点个数
         */
        private Map<TreeNode, Integer> nodeCountMap;

        MyTree(TreeNode root) {
            this.root = root;
            this.nodeCountMap = new HashMap<>();
            countNode(root);
        }

        public int getMinKVal(int k) {
            TreeNode node = root;

            //以当前节点左节点为根的数节点个数，考虑node.left为null，即当前节点没有左子树，count为0
            int count = nodeCountMap.getOrDefault(node.left, 0);

            while (count + 1 != k) {
                //往左子树找
                if (count + 1 > k) {
                    node = node.left;
                } else {
                    //往右子树找
                    k = k - count - 1;
                    node = node.right;
                }

                //node为空说明没有第k小节点，返回-1，表示未找到
                if (node == null) {
                    return -1;
                }

                //考虑node.left为null，即当前节点没有左子树，count为0
                count = nodeCountMap.getOrDefault(node.left, 0);
            }

            return node.val;
        }

        private int countNode(TreeNode node) {
            if (node == null) {
                return 0;
            }

            int leftCount = countNode(node.left);
            int rightCount = countNode(node.right);

            nodeCountMap.put(node, leftCount + rightCount + 1);

            return leftCount + rightCount + 1;
        }
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
