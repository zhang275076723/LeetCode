package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/21 17:34
 * @Author zsy
 * @Description 从上到下打印二叉树 III 字节面试题 类比Problem102、Problem107、Offer32、Offer32_2 同Problem103
 * 请实现一个函数按照之字形顺序打印二叉树，
 * 即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
 * <p>
 * 给定二叉树: [3, 9, 20, null, null, 15, 7]
 * 返回其层次遍历结果：[[3], [20,9], [15,7]]
 * <p>
 * 节点总数 <= 1000
 */
public class Offer32_3 {
    public static void main(String[] args) {
        Offer32_3 offer32_3 = new Offer32_3();
        String[] data = {"3", "9", "20", "null", "null", "15", "7"};
        TreeNode root = offer32_3.buildTree(data);
        System.out.println(offer32_3.levelOrder(root));
        System.out.println(offer32_3.levelOrder2(root));
        System.out.println(offer32_3.levelOrder3(root));
    }

    /**
     * 使用size统计树每行元素的个数
     * 存储标志确定存储正序或反序
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        //反转标志，1-正序，-1-逆序
        int flag = 1;

        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int size = queue.size();

            while (size > 0) {
                TreeNode node = queue.poll();
                size--;

                if (flag == 1) {
                    //尾添加
                    list.add(node.val);
                } else {
                    //首添加
                    list.add(0, node.val);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            flag = -flag;
            result.add(list);
        }

        return result;
    }

    /**
     * 使用两个LinkedList队列
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        //从左到右，尾添加
        Queue<TreeNode> queue1 = new LinkedList<>();
        //从右到左，首添加
        Queue<TreeNode> queue2 = new LinkedList<>();
        queue1.offer(root);

        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            List<Integer> list = new ArrayList<>();

            if (!queue1.isEmpty()) {
                while (!queue1.isEmpty()) {
                    TreeNode node = queue1.poll();
                    //queue1从左到右，尾添加
                    list.add(node.val);

                    if (node.left != null) {
                        queue2.offer(node.left);
                    }
                    if (node.right != null) {
                        queue2.offer(node.right);
                    }
                }
            } else {
                while (!queue2.isEmpty()) {
                    TreeNode node = queue2.poll();
                    //queue2从右到左，首添加
                    list.add(0, node.val);

                    if (node.left != null) {
                        queue1.offer(node.left);
                    }
                    if (node.right != null) {
                        queue1.offer(node.right);
                    }
                }
            }

            result.add(list);
        }

        return result;
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder3(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();

        dfs(root, 0, result);

        return result;
    }

    /**
     * @param root   当前根节点
     * @param level  树的层数
     * @param result 结果集合
     */
    public void dfs(TreeNode root, int level, List<List<Integer>> result) {
        if (root == null) {
            return;
        }

        if (result.size() <= level) {
            result.add(new LinkedList<>());
        }

        LinkedList<Integer> list = (LinkedList<Integer>) result.get(level);

        //如果是偶数层，则尾添加
        if (level % 2 == 0) {
            list.addLast(root.val);
        } else {
            //如果是奇数层，则首添加
            list.addFirst(root.val);
        }

        dfs(root.left, level + 1, result);
        dfs(root.right, level + 1, result);
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
