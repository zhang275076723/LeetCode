package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/24 8:58
 * @Author zsy
 * @Description 序列化二叉树 类比Problem297
 * 请实现两个函数，分别用来序列化和反序列化二叉树。
 * 你需要设计一个算法来实现二叉树的序列化与反序列化。
 * 这里不限定你的序列 / 反序列化算法执行逻辑，
 * 你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
 */
public class Offer37 {
    public static void main(String[] args) {
        Offer37 offer37 = new Offer37();
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;

        //先序遍历
//        String data = offer37.serialize(node1);
//        System.out.println(data);
//
//        TreeNode node = offer37.deserialize(data);
//        offer37.preorderPrint(node);

        //层次遍历
        String data = offer37.serialize2(node1);
        System.out.println(data);

        TreeNode node = offer37.deserialize2(data);
        offer37.levelPrint(node);
    }

    /**
     * 先序遍历的序列化，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        preorderSerialize(root, sb);
        return sb.toString();
    }

    public void preorderSerialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("null,");
            return;
        }

        sb.append(root.val).append(",");
        preorderSerialize(root.left, sb);
        preorderSerialize(root.right, sb);
    }

    /**
     * 先序遍历的反序列化，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param data
     * @return
     */
    public TreeNode deserialize(String data) {
        //去除末尾的','
        data = data.substring(0, data.length() - 1);
        String[] values = data.split(",");

        //不能写成List<String> list = Arrays.asList(values);
        //因为Arrays.asList(values)将数组转为集合之后，底层还是数组，
        //返回的是java.util.Arrays的内部类ArrayList，这个类没有重写add、remove等方法，调用这些方法会抛出异常
        List<String> list = new ArrayList<>(Arrays.asList(values));

        TreeNode node = preorderDeserialize(list);
        return node;
    }

    public TreeNode preorderDeserialize(List<String> list) {
        if ("null".equals(list.get(0))) {
            list.remove(0);
            return null;
        }

        int value = Integer.parseInt(list.remove(0));
        TreeNode node = new TreeNode(value);
        node.left = preorderDeserialize(list);
        node.right = preorderDeserialize(list);
        return node;
    }

    /**
     * 层次遍历的序列化，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public String serialize2(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(',');
                queue.add(node.left);
                queue.add(node.right);
            }
        }

        return sb.toString();
    }

    /**
     * 层次遍历的反序列化，时间复杂度O(n)，空间复杂度O(n)
     *
     * @param data
     * @return
     */
    public TreeNode deserialize2(String data) {
        //去除末尾的','
        data = data.substring(0, data.length() - 1);
        String[] values = data.split(",");

        //如果第一个为null，则为空树，直接返回null
        if ("null".equals(values[0])) {
            return null;
        }

        //不能写成List<String> list = Arrays.asList(values);
        //因为Arrays.asList(values)将数组转为集合之后，底层还是数组，
        //返回的是java.util.Arrays的内部类ArrayList，这个类没有重写add、remove等方法，调用这些方法会抛出异常
        List<String> list = new ArrayList<>(Arrays.asList(values));
        Queue<TreeNode> queue = new LinkedList<>();
        //记录根节点
        TreeNode root = new TreeNode(Integer.parseInt(list.remove(0)));

        queue.add(root);
        //要保证队列不为空，且list集合中元素大于1个，才能从list中拿出2个
        while (!queue.isEmpty() && list.size() > 1) {
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

    /**
     * 先序遍历输出
     *
     * @param root
     */
    public void preorderPrint(TreeNode root) {
        if (root == null) {
            System.out.print("null,");
            return;
        }
        System.out.print(root.val + ",");
        preorderPrint(root.left);
        preorderPrint(root.right);
    }

    /**
     * 层次遍历输出
     *
     * @param root
     */
    public void levelPrint(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(',');
                queue.add(node.left);
                queue.add(node.right);
            }
        }

        System.out.println(sb);
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
