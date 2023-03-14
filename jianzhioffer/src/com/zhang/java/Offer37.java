package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/24 8:58
 * @Author zsy
 * @Description 序列化二叉树 序列化类比Problem449 同Problem297
 * 请实现两个函数，分别用来序列化和反序列化二叉树。
 * 你需要设计一个算法来实现二叉树的序列化与反序列化。
 * 这里不限定你的序列 / 反序列化算法执行逻辑，
 * 你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
 * <p>
 * 输入：root = [1,2,3,null,null,4,5]
 * 输出：[1,2,3,null,null,4,5]
 */
public class Offer37 {
    public static void main(String[] args) {
        Offer37 offer37 = new Offer37();
        String[] value = {"1", "2", "3", "null", "null", "4", "5"};
        TreeNode root = offer37.buildTree(value);

        //层次遍历
        String data = offer37.serialize(root);
        System.out.println(data);
        TreeNode node = offer37.deserialize(data);
        offer37.levelPrint(node);

        //前序遍历
        String data2 = offer37.serialize2(root);
        System.out.println(data2);
        TreeNode node2 = offer37.deserialize2(data2);
        offer37.preorderPrint(node2);
    }

    /**
     * 层次遍历的序列化
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
        queue.offer(root);
        StringBuilder sb = new StringBuilder();

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        //去除末尾','
        return sb.delete(sb.length() - 1, sb.length()).toString();
    }

    /**
     * 层次遍历的反序列化
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
        //不能写成List<String> list = Arrays.asList(values);
        //因为Arrays.asList(values)将数组转为集合之后，底层还是数组，
        //返回的是java.util.Arrays的内部类ArrayList，这个类没有重写add、remove等方法，调用这些方法会抛出异常
        List<String> list = new ArrayList<>(Arrays.asList(values));
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(list.remove(0)));
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (!list.isEmpty()) {
                String leftValue = list.remove(0);
                if (!"null".equals(leftValue)) {
                    node.left = new TreeNode(Integer.parseInt(leftValue));
                    queue.offer(node.left);
                }
            }
            if (!list.isEmpty()) {
                String rightValue = list.remove(0);
                if (!"null".equals(rightValue)) {
                    node.right = new TreeNode(Integer.parseInt(rightValue));
                    queue.offer(node.right);
                }
            }
        }

        return root;
    }

    /**
     * 前序遍历的序列化
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
                sb.append(node.val).append(",");
                stack.push(node);
                node = node.left;
            }

            sb.append("null,");
            node = stack.pop();
            node = node.right;
        }

        //去除末尾','
        return sb.delete(sb.length() - 1, sb.length()).toString();
    }

    /**
     * 前序遍历的反序列化
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
        //不能写成List<String> list = Arrays.asList(values);
        //因为Arrays.asList(values)将数组转为集合之后，底层还是数组，
        //返回的是java.util.Arrays的内部类ArrayList，这个类没有重写add、remove等方法，调用这些方法会抛出异常
        List<String> list = new ArrayList<>(Arrays.asList(values));
        return deserialize2(list);
    }

    private TreeNode deserialize2(List<String> list) {
        if (list.isEmpty()) {
            return null;
        }

        String value = list.remove(0);

        if ("null".equals(value)) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(value));
        root.left = deserialize2(list);
        root.right = deserialize2(list);
        return root;
    }

    /**
     * 前序遍历输出
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
