package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/2/3 08:04
 * @Author zsy
 * @Description 在二叉树中分配硬币 dfs类比Problem104、Problem110、Problem111、Problem124、Problem298、Problem337、Problem543、Problem687、Problem968、Problem1373
 * 给你一个有 n 个结点的二叉树的根结点 root ，其中树中每个结点 node 都对应有 node.val 枚硬币。
 * 整棵树上一共有 n 枚硬币。
 * 在一次移动中，我们可以选择两个相邻的结点，然后将一枚硬币从其中一个结点移动到另一个结点。
 * 移动可以是从父结点到子结点，或者从子结点移动到父结点。
 * 返回使每个结点上 只有 一枚硬币所需的 最少 移动次数。
 * <p>
 * 输入：root = [3,0,0]
 * 输出：2
 * 解释：一枚硬币从根结点移动到左子结点，一枚硬币从根结点移动到右子结点。
 * <p>
 * 输入：root = [0,3,0]
 * 输出：3
 * 解释：将两枚硬币从根结点的左子结点移动到根结点（两次移动）。然后，将一枚硬币从根结点移动到右子结点。
 * <p>
 * 树中节点的数目为 n
 * 1 <= n <= 100
 * 0 <= Node.val <= n
 * 所有 Node.val 的值之和是 n
 */
public class Problem979 {
    /**
     * dfs中需要移动的最少硬币次数
     */
    private int result = 0;

    /**
     * dfs2中需要移动的最少硬币次数
     */
    private int result2 = 0;

    public static void main(String[] args) {
        Problem979 problem979 = new Problem979();
        String[] data = {"3", "0", "0"};
        TreeNode root = problem979.buildTree(data);
        System.out.println(problem979.distributeCoins(root));
        System.out.println(problem979.distributeCoins2(root));
    }

    /**
     * dfs
     * 核心思想：计算每一条边需要需要移动的最少硬币次数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int distributeCoins(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);

        return result;
    }

    /**
     * dfs
     * 核心思想：计算每一条边需要需要移动的最少硬币次数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int distributeCoins2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs2(root);

        return result2;
    }

    /**
     * 返回以root为根节点的树中硬币的个数arr[0]，树中节点的个数arr[1]
     * root和父节点的边需要移动的最少硬币次数为abs(arr[0]-arr[1])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    private int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }

        //root左子树数组
        int[] leftArr = dfs(root.left);
        //root右子树数组
        int[] rightArr = dfs(root.right);
        //以root为根节点的树中硬币的个数
        int coin = leftArr[0] + rightArr[0] + root.val;
        //以root为根节点的树中节点的个数
        int count = leftArr[1] + rightArr[1] + 1;

        //root和父节点的边需要移动的最少硬币次数
        result = result + Math.abs(coin - count);

        //返回当前节点为根节点的树中硬币的个数arr[0]，树中节点的个数arr[1]
        return new int[]{coin, count};
    }

    /**
     * 返回以root为根节点的树中硬币的个数减去树中节点的个数
     * root和父节点的边需要移动的最少硬币次数为abs(dfs2(root.left)+dfs2(root.right)+root.val-1)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    private int dfs2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //root左子树中硬币的个数减去树中节点的个数
        int leftCount = dfs2(root.left);
        //root右子树中硬币的个数减去树中节点的个数
        int rightCount = dfs2(root.right);

        //root和父节点的边需要移动的最少硬币次数
        result2 = result2 + Math.abs(leftCount + rightCount + root.val - 1);

        //返回以root为根节点的树中硬币的个数减去树中节点的个数
        return leftCount + rightCount + root.val - 1;
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
