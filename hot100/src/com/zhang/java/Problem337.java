package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/5/30 15:53
 * @Author zsy
 * @Description 打家劫舍 III 类比Problem486、Problem877 类比Problem198、Problem213、Problem2560 dfs类比Problem124、Problem250、Problem298、Problem543、Problem687、Problem968、Problem979、Problem1245、Problem1372、Problem1373、Problem2246、Problem2378
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
     * dfs
     * arr[0]：当前节点作为根节点，不选当前节点能够盗取的最高金额
     * arr[1]：当前节点作为根节点，选当前节点能够盗取的最高金额
     * 计算当前节点左右子节点作为根节点能够盗取的最高金额数组，max(arr[0],arr[1])即为当前节点作为根节点能够盗取的最高金额，
     * 返回当前节点作为根节点能够盗取的最高金额数组，用于计算当前节点父节点作为根节点能够盗取的最高金额数组
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int[] arr = dfs(root);

        //选或不选根节点能够盗取的最高金额中的较大值，即为能够盗取的最高金额
        return Math.max(arr[0], arr[1]);
    }

    /**
     * 返回当前节点作为根节点能够盗取的最高金额数组
     * arr[0]：当前节点作为根节点，不选当前节点能够盗取的最高金额
     * arr[1]：当前节点作为根节点，选当前节点能够盗取的最高金额
     *
     * @param root
     * @return
     */
    private int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }

        //当前节点左子节点作为根节点能够盗取的最高金额数组
        int[] leftArr = dfs(root.left);
        //当前节点右子节点作为根节点能够盗取的最高金额数组
        int[] rightArr = dfs(root.right);

        //不选当前节点，则子节点可选可不选
        int notSelected = Math.max(leftArr[0], leftArr[1]) + Math.max(rightArr[0], rightArr[1]);
        //选当前节点，则子节点不能选
        int selected = root.val + leftArr[0] + rightArr[0];

        //返回当前节点作为根节点能够盗取的最高金额数组
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
