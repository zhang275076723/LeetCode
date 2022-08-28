package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/30 15:53
 * @Author zsy
 * @Description 打家劫舍 III 类比Problem198、Problem213、Problem543
 * 小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 root 。
 * 除了 root 之外，每栋房子有且只有一个“父“房子与之相连。
 * 一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。
 * 如果 两个直接相连的房子在同一天晚上被打劫 ，房屋将自动报警。
 * 给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。
 * <p>
 * 输入: root = [3,2,3,null,3,null,1]
 * 输出: 7
 * 解释: 小偷一晚能够盗取的最高金额 3 + 3 + 1 = 7
 * <p>
 * 输入: root = [3,4,5,1,3,null,1]
 * 输出: 9
 * 解释: 小偷一晚能够盗取的最高金额 4 + 5 = 9
 * <p>
 * 树的节点数在 [1, 10^4] 范围内
 * 0 <= Node.val <= 10^4
 */
public class Problem337 {
    public static void main(String[] args) {
        Problem337 problem337 = new Problem337();
        String[] data = {"3", "2", "3", "null", "3", "null", "1"};
        TreeNode root = problem337.buildTree(data);
        System.out.println(problem337.rob(root));
    }

    /**
     * 动态规划
     * 从根节点开始能够盗取的最高金额为，
     * 选根节点(不能选根节点的左右子节点) 和 不选根节点(可选可不选根节点的左右子节点)，两种情况的最大值
     * result[0]：不选根节点的最大值
     * result[1]；选节点的最大值
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int[] result = dfs(root);

        return Math.max(result[0], result[1]);
    }

    private int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[2];
        }

        //以根节点左节点为根的最大值
        int[] leftMax = dfs(root.left);
        //以根节点右节点为根的最大值
        int[] rightMax = dfs(root.right);

        //不选根节点的最大值
        int notSelected = Math.max(leftMax[0], leftMax[1]) + Math.max(rightMax[0], rightMax[1]);
        //选根节点的最大值
        int selected = root.val + leftMax[0] + rightMax[0];

        return new int[]{notSelected, selected};
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
