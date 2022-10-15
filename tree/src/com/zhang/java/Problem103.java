package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/3/15 11:58
 * @Author zsy
 * @Description 二叉树的锯齿形层序遍历 字节面试题 类比Problem102、Problem107、Offer32、Offer32_2 同Offer32_3
 * 给你二叉树的根节点 root ，返回其节点值的锯齿形层序遍历 。
 * （即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 * <p>
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[3],[20,9],[15,7]]
 * <p>
 * 输入：root = [1]
 * 输出：[[1]]
 * <p>
 * 输入：root = []
 * 输出：[]
 * <p>
 * 树中节点数目在范围 [0, 2000] 内
 * -100 <= Node.val <= 100
 */
public class Problem103 {
    public static void main(String[] args) {
        Problem103 problem103 = new Problem103();
        String[] data = {"3", "9", "20", "null", "null", "15", "7"};
        TreeNode root = problem103.buildTree(data);
        System.out.println(problem103.zigzagLevelOrder(root));
        System.out.println(problem103.zigzagLevelOrder2(root));
        System.out.println(problem103.zigzagLevelOrder3(root));
    }

    /**
     * bfs，使用size统计树每行元素的个数
     * 存储标志确定存储正序或反序
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        //反转标志，1-正序，-1-逆序
        int flag = 1;

        while (!queue.isEmpty()) {
            LinkedList<Integer> list = new LinkedList<>();
            int size = queue.size();

            while (size > 0) {
                TreeNode node = queue.poll();
                size--;

                if (flag == 1) {
                    list.addLast(node.val);
                } else {
                    list.addFirst(node.val);
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
     * bfs，使用两个LinkedList队列
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
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
            LinkedList<Integer> list = new LinkedList<>();

            if (!queue1.isEmpty()) {
                while (!queue1.isEmpty()) {
                    TreeNode node = queue1.poll();
                    //queue1从左到右，尾添加
                    list.addLast(node.val);

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
                    list.addFirst(node.val);

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
    public List<List<Integer>> zigzagLevelOrder3(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();

        dfs(root, 0, result);

        return result;
    }

    private void dfs(TreeNode root, int level, List<List<Integer>> result) {
        if (root == null) {
            return;
        }

        //每行第一次访问，添加list集合
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

