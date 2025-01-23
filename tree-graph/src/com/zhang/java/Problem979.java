package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/2/3 08:04
 * @Author zsy
 * @Description 在二叉树中分配硬币 dfs类比Problem124、Problem250、Problem298、Problem337、Problem543、Problem687、Problem968、Problem1245、Problem1372、Problem1373、Problem2246、Problem2378、Problem2925
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
     * 树种每个节点只有1个硬币需要的最少移动次数
     */
    private int result = 0;

    public static void main(String[] args) {
        Problem979 problem979 = new Problem979();
        String[] data = {"3", "0", "0"};
        TreeNode root = problem979.buildTree(data);
        System.out.println(problem979.distributeCoins(root));
    }

    /**
     * dfs
     * arr[0]：当前节点作为根节点的树中硬币的个数
     * arr[1]：当前节点作为根节点的树中节点的个数
     * 当前节点作为根节点的树中硬币个数和节点个数的差值，即为当前节点和父节点的边需要的最少移动次数
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
     * 返回当前节点作为根节点的树中硬币的个数和节点的个数数组
     * arr[0]：当前节点作为根节点的树中硬币的个数
     * arr[1]：当前节点作为根节点的树中节点的个数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    private int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }

        //当前节点左子节点作为根节点的树中硬币的个数和节点的个数数组
        int[] leftArr = dfs(root.left);
        //当前节点右子节点作为根节点的树中硬币的个数和节点的个数数组
        int[] rightArr = dfs(root.right);

        //当前节点作为根节点的树中硬币的个数
        int coinCount = leftArr[0] + rightArr[0] + root.val;
        //当前节点作为根节点的树中节点的个数
        int nodeCount = leftArr[1] + rightArr[1] + 1;
        //root和父节点的边需要的最少移动次数
        result = result + Math.abs(coinCount - nodeCount);

        //返回当前节点作为根节点的树中硬币的个数和节点的个数数组
        return new int[]{coinCount, nodeCount};
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
