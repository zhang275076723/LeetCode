package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/27 9:52
 * @Author zsy
 * @Description 二叉树的序列化与反序列化 类比Offer37
 * 序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，
 * 同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
 * 请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，
 * 你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
 * <p>
 * 输入：root = [1,2,3,null,null,4,5]
 * 输出：[1,2,3,null,null,4,5]
 * <p>
 * 输入：root = []
 * 输出：[]
 * <p>
 * 输入：root = [1]
 * 输出：[1]
 * <p>
 * 输入：root = [1,2]
 * 输出：[1,2]
 * <p>
 * 树中结点数在范围 [0, 10^4] 内
 * -1000 <= Node.val <= 1000
 */
public class Problem297 {
    public static void main(String[] args) {
        Problem297 problem297 = new Problem297();
        String[] data = {"1", "2", "3", "null", "null", "4", "5"};
        TreeNode root = problem297.buildTree(data);

        //层序遍历序列化和反序列化
        String serializeData = problem297.serialize(root);
        System.out.println(serializeData);
        TreeNode deserializeRoot = problem297.deserialize(serializeData);

        //先序遍历序列化和反序列化
        String serializeData2 = problem297.serialize2(root);
        System.out.println(serializeData2);
        TreeNode deserializeRoot2 = problem297.deserialize2(serializeData2);
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

        Queue<TreeNode> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                sb.append(node.val).append(',');
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                sb.append("null,");
            }
        }

        return sb.toString();
    }

    /**
     * 层序遍历反序列化
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param data
     * @return
     */
    public TreeNode deserialize(String data) {
        if (data == null || "null".equals(data)) {
            return null;
        }

        //去除末尾的','
        data = data.substring(0, data.length() - 1);
        String[] values = data.split(",");
        //不能写成List<String> list = Arrays.asList(values);
        //因为Arrays.asList(values)将数组转为集合之后，底层还是数组，
        //返回的是java.util.Arrays的内部类ArrayList，这个类没有重写add、remove等方法，调用这些方法会抛出异常
        List<String> list = new ArrayList<>(Arrays.asList(values));
        TreeNode root = new TreeNode(Integer.parseInt(list.remove(0)));
        Queue<TreeNode> queue = new LinkedList<>();
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
     * 先序遍历序列化二叉树
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
        serialize2(root, sb);
        return sb.toString();
    }

    private void serialize2(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("null,");
            return;
        }

        sb.append(root.val).append(",");
        serialize2(root.left, sb);
        serialize2(root.right, sb);
    }

    /**
     * 先序遍历反序列化二叉树
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param data
     * @return
     */
    public TreeNode deserialize2(String data) {
        if (data == null || "null".equals(data)) {
            return null;
        }

        //去除末尾的','
        data = data.substring(0, data.length() - 1);
        String[] values = data.split(",");
        //不能写成List<String> list = Arrays.asList(values);
        //因为Arrays.asList(values)将数组转为集合之后，底层还是数组，
        //返回的是java.util.Arrays的内部类ArrayList，这个类没有重写add、remove等方法，调用这些方法会抛出异常
        List<String> list = new ArrayList<>(Arrays.asList(values));

        return deserialize2(list);
    }

    private TreeNode deserialize2(List<String> list) {
        String value = list.remove(0);

        if ("null".equals(value)) {
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(value));

        node.left = deserialize2(list);
        node.right = deserialize2(list);

        return node;
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
