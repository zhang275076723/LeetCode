package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/7 8:19
 * @Author zsy
 * @Description 路径总和 III 类比Problem112、Problem113、Problem257、Problem560
 * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
 * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 * <p>
 * 输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
 * 输出：3
 * 解释：和等于 8 的路径有 3 条，如图所示。[5,3] [5,2,1] [-3,11]
 * <p>
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * 输出：3
 * <p>
 * 二叉树的节点个数的范围是 [0,1000]
 * -10^9 <= Node.val <= 10^9
 * -1000 <= targetSum <= 1000
 */
public class Problem437 {
    /**
     * 和为targetSum的路径数量
     */
    private int count = 0;

    /**
     * 路径前缀和，和为targetSum的路径数量
     */
    private int count2 = 0;

    public static void main(String[] args) {
        Problem437 problem437 = new Problem437();
        String[] data = {"10", "5", "-3", "3", "2", "null", "11", "3", "-2", "null", "1"};
        TreeNode root = problem437.buildTree(data);
        System.out.println(problem437.pathSum(root, 8));
        System.out.println(problem437.pathSum2(root, 8));
    }

    /**
     * 双重递归，遍历每个节点，对每个节点进行dfs，因为节点有负数，所以dfs不能剪枝
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }

        dfs(root, targetSum, 0);
        pathSum(root.left, targetSum);
        pathSum(root.right, targetSum);

        return count;
    }

    /**
     * 路径前缀和
     * 遍历每个节点，在哈希表中查找前缀和为targetSum-curSum的节点个数
     * 因为节点存在负数，所以不能使用滑动窗口
     * 哈希表：key，根节点到当前节点路径上，除当前节点之外，所有节点的前缀和；value，满足路径前缀和的个数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum2(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();
        //用于只有一个节点满足要求的情况
        map.put(0, 1);

        dfs2(root, targetSum, 0, map);

        return count2;
    }

    /**
     * @param root      当前根节点
     * @param targetSum 要求的路径之和
     * @param curSum    当前路径之和
     */
    private void dfs(TreeNode root, int targetSum, int curSum) {
        if (root == null) {
            return;
        }

        curSum = curSum + root.val;

        if (curSum == targetSum) {
            count++;
        }

        //因为节点存在负数，所以不能剪枝，需要继续进行遍历
        dfs(root.left, targetSum, curSum);
        dfs(root.right, targetSum, curSum);
    }

    /**
     * @param root      当前根节点
     * @param targetSum 要求的路径之和
     * @param curSum    当前路径之和
     * @param map       根节点到当前节点路径上，除当前节点之外，所有节点的前缀和哈希表
     */
    private void dfs2(TreeNode root, int targetSum, int curSum, Map<Integer, Integer> map) {
        if (root == null) {
            return;
        }

        //根节点到当前节点的路径和
        curSum = curSum + root.val;

        //路径和为curSum-targetSum的数量，即为路径和为targetSum的数量
        if (map.containsKey(curSum - targetSum)) {
            count2 = count2 + map.get(curSum - targetSum);
        }

        //根节点到当前节点的路径和放入哈希表中
        map.put(curSum, map.getOrDefault(curSum, 0) + 1);

        dfs2(root.left, targetSum, curSum, map);
        dfs2(root.right, targetSum, curSum, map);

        //根节点到当前节点的路径和从哈希表中删除，因为当前分叉已经遍历结束，要遍历另一分叉，所以当前分叉的前缀和已经不适用
        map.put(curSum, map.get(curSum) - 1);
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
