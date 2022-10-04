package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/16 8:45
 * @Author zsy
 * @Description 二叉搜索树中的众数 类比problem98、Problem99、Problem538
 * 给你一个含重复值的二叉搜索树（BST）的根节点 root ，找出并返回 BST 中的所有 众数（即，出现频率最高的元素）。
 * 如果树中有不止一个众数，可以按 任意顺序 返回。
 * 假定 BST 满足如下定义：
 * 结点左子树中所含节点的值 小于等于 当前节点的值
 * 结点右子树中所含节点的值 大于等于 当前节点的值
 * 左子树和右子树都是二叉搜索树
 * <p>
 * 输入：root = [1,null,2,2]
 * 输出：[2]
 * <p>
 * 输入：root = [0]
 * 输出：[0]
 * <p>
 * 树中节点的数目在范围 [1, 10^4] 内
 * -10^5 <= Node.val <= 10^5
 */
public class Problem501 {
    /**
     * 递归中序遍历中当前节点的前驱节点
     */
    private TreeNode pre = null;

    /**
     * 递归中序遍历中当前节点出现的频率次数
     */
    private int curFrequency = 0;

    /**
     * 递归中序遍历中当前出现频率最大的次数
     */
    private int maxFrequency = 0;

    public static void main(String[] args) {
        Problem501 problem501 = new Problem501();
        String[] data = {"1", "null", "2", "2"};
        TreeNode root = problem501.buildTree(data);
        System.out.println(Arrays.toString(problem501.findMode(root)));
        System.out.println(Arrays.toString(problem501.findMode2(root)));
    }

    /**
     * 递归中序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int[] findMode(TreeNode root) {
        if (root == null) {
            return null;
        }

        List<Integer> list = new ArrayList<>();
        inorder(root, list);

        int[] result = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * 非递归中序遍历
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int[] findMode2(TreeNode root) {
        if (root == null) {
            return null;
        }

        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        TreeNode pre = null;
        int curFrequency = 0;
        int maxFrequency = 0;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            if (pre != null) {
                //当前节点和前驱节点相同
                if (pre.val == node.val) {
                    curFrequency++;

                    //当前节点的频率等于最大频率，当前节点加入list
                    if (curFrequency == maxFrequency) {
                        list.add(node.val);
                    } else if (curFrequency > maxFrequency) {
                        //当前节点的频率大于最大频率，将list集合清空，更新最大频率
                        list.clear();
                        maxFrequency = curFrequency;
                        list.add(node.val);
                    }
                } else {
                    //当前节点和前驱节点不相同
                    curFrequency = 1;

                    //当前节点的频率等于最大频率，当前节点加入list
                    if (curFrequency == maxFrequency) {
                        list.add(node.val);
                    }
                }
            } else {
                //当前节点是中序遍历中的第一个节点
                curFrequency = 1;
                maxFrequency = 1;
                list.add(node.val);
            }

            pre = node;
            node = node.right;
        }

        int[] result = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    private void inorder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        inorder(root.left, list);

        if (pre != null) {
            //当前节点和前驱节点相同
            if (pre.val == root.val) {
                curFrequency++;

                //当前节点的频率等于最大频率，当前节点加入list
                if (curFrequency == maxFrequency) {
                    list.add(root.val);
                } else if (curFrequency > maxFrequency) {
                    //当前节点的频率大于最大频率，将list集合清空，更新最大频率
                    list.clear();
                    maxFrequency = curFrequency;
                    list.add(root.val);
                }
            } else {
                //当前节点和前驱节点不相同
                curFrequency = 1;

                //当前节点的频率等于最大频率，当前节点加入list
                if (curFrequency == maxFrequency) {
                    list.add(root.val);
                }
            }
        } else {
            //当前节点是中序遍历中的第一个节点
            curFrequency = 1;
            maxFrequency = 1;
            list.add(root.val);
        }

        //更新前驱节点
        pre = root;

        inorder(root.right, list);
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
