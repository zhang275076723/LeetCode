package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/10/28 18:34
 * @Author zsy
 * @Description 序列化和反序列化二叉搜索树 类比Problem297、Problem1008、Offer37
 * 序列化是将数据结构或对象转换为一系列位的过程，以便它可以存储在文件或内存缓冲区中，
 * 或通过网络连接链路传输，以便稍后在同一个或另一个计算机环境中重建。
 * 设计一个算法来序列化和反序列化 二叉搜索树 。
 * 对序列化/反序列化算法的工作方式没有限制。
 * 您只需确保二叉搜索树可以序列化为字符串，并且可以将该字符串反序列化为最初的二叉搜索树。
 * 编码的字符串应尽可能紧凑。
 * <p>
 * 输入：root = [2,1,3]
 * 输出：[2,1,3]
 * <p>
 * 输入：root = []
 * 输出：[]
 * <p>
 * 树中节点数范围是 [0, 10^4]
 * 0 <= Node.val <= 10^4
 * 题目数据 保证 输入的树是一棵二叉搜索树。
 */
public class Problem449 {
    public static void main(String[] args) {
        Problem449 problem449 = new Problem449();
        String[] data = {"2", "1", "3"};
        TreeNode root = problem449.buildTree(data);

        //层序遍历序列化和反序列化
        String serializeData = problem449.serialize(root);
        System.out.println(serializeData);
        TreeNode deserializeRoot = problem449.deserialize(serializeData);

        //利用二叉搜索树性质，前序遍历序列化和反序列化
        String serializeData2 = problem449.serialize2(root);
        System.out.println(serializeData2);
        TreeNode deserializeRoot2 = problem449.deserialize2(serializeData2);
    }

    /**
     * 层序遍历序列化
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(',');
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        //去除末尾','
        return sb.delete(sb.length() - 1, sb.length()).toString();
    }

    /**
     * 层次遍历反序列化
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param data
     * @return
     */
    public TreeNode deserialize(String data) {
        if (data == null || "null".equals(data)) {
            return null;
        }

        String[] values = data.split(",");
        List<String> list = new ArrayList<>(Arrays.asList(values));
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
     * 前序遍历序列化
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public String serialize2(TreeNode root) {
        if (root == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                sb.append(node.val).append(',');
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            node = node.right;
        }

        //去除末尾','
        return sb.delete(sb.length() - 1, sb.length()).toString();
    }

    /**
     * 反前序遍历
     * 二叉搜索树的中序遍历为顺序，即只根据二叉搜索树的前序遍历结果反序列化
     * 二叉搜索树的前序遍历第一个节点为根节点，之后小于根节点值的都为左子树节点，之后都为右子树节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param data
     * @return
     */
    public TreeNode deserialize2(String data) {
        if (data == null || "null".equals(data)) {
            return null;
        }

        String[] values = data.split(",");
        //二叉搜索树的前序遍历数组
        int[] preorder = new int[values.length];

        for (int i = 0; i < values.length; i++) {
            preorder[i] = Integer.parseInt(values[i]);
        }

        //根据前序遍历数组构件二叉搜索树
        return deserialize2(0, preorder.length - 1, preorder);
    }

    private TreeNode deserialize2(int left, int right, int[] preorder) {
        if (left > right) {
            return null;
        }

        if (left == right) {
            return new TreeNode(preorder[left]);
        }

        //右子树根节点为第一个比preorder[left]节点值大的元素
        int rightRootIndex = left + 1;

        while (rightRootIndex <= right && preorder[left] > preorder[rightRootIndex]) {
            rightRootIndex++;
        }

        TreeNode root = new TreeNode(preorder[left]);

        root.left = deserialize2(left + 1, rightRootIndex - 1, preorder);
        root.right = deserialize2(rightRootIndex, right, preorder);

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

        TreeNode(int x) {
            val = x;
        }
    }
}
