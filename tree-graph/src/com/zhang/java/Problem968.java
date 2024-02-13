package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/2/4 08:29
 * @Author zsy
 * @Description 监控二叉树 dfs类比Problem104、Problem110、Problem111、Problem124、Problem298、Problem337、Problem543、Problem687、Problem979、Problem1373
 * 给定一个二叉树，我们在树的节点上安装摄像头。
 * 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
 * 计算监控树的所有节点所需的最小摄像头数量。
 * <p>
 * 输入：[0,0,null,0,0]
 * 输出：1
 * 解释：如图所示，一台摄像头足以监控所有节点。
 * <p>
 * 输入：[0,0,null,0,null,0,null,null,0]
 * 输出：2
 * 解释：需要至少两个摄像头来监视树的所有节点。 上图显示了摄像头放置的有效位置之一。
 * <p>
 * 给定树的节点数的范围是 [1, 1000]。
 * 每个节点的值都是 0。
 */
public class Problem968 {
    public static void main(String[] args) {
        Problem968 problem968 = new Problem968();
        String[] data = {"0", "0", "null", "0", "null", "0", "null", "null", "0"};
        TreeNode root = problem968.buildTree(data);
        System.out.println(problem968.minCameraCover(root));
    }

    /**
     * dfs
     * 得到根节点所需的最小摄像头数量数组，根节点没有父节点，即arr[0]和arr[2]中的较大值，即为所需的最小摄像头数量
     * arr[0]：当前节点放置摄像头，当前节点为根节点的树中所需的最小摄像头数量
     * arr[1]：当前节点不放置摄像头，并且当前节点父节点放置摄像头，当前节点为根节点的树中所需的最小摄像头数量
     * arr[2]：当前节点不放置摄像头，并且当前节点子节点放置摄像头，当前节点为根节点的树中所需的最小摄像头数量
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public int minCameraCover(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int[] arr = dfs(root);

        //根节点没有父节点，即arr[0]和arr[2]中的较大值，即为所需的最小摄像头数量
        return Math.min(arr[0], arr[2]);
    }

    /**
     * 返回当前节点所需的最小摄像头数量数组
     * arr[0]：当前节点放置摄像头，当前节点为根节点的树中所需的最小摄像头数量
     * arr[1]：当前节点不放置摄像头，并且当前节点父节点放置摄像头，当前节点为根节点的树中所需的最小摄像头数量
     * arr[2]：当前节点不放置摄像头，并且当前节点子节点放置摄像头，当前节点为根节点的树中所需的最小摄像头数量
     *
     * @param root
     * @return
     */
    private int[] dfs(TreeNode root) {
        //空节点不能放置摄像头，则arr[0]=Integer.MAX_VALUE/2，空节点父节点和子节点放置摄像头，则arr[1]=arr[2]=0
        //最大值使用Integer.MAX_VALUE/2，避免相加int溢出
        if (root == null) {
            return new int[]{Integer.MAX_VALUE / 2, 0, 0};
        }

        //左子节点作为根节点所需的最小摄像头数量数组
        int[] leftArr = dfs(root.left);
        //右子节点作为根节点所需的最小摄像头数量数组
        int[] rightArr = dfs(root.right);

        //当前节点放置摄像头，当前节点为根节点的树中所需的最小摄像头数量
        int curChoose = Math.min(leftArr[0], Math.min(leftArr[1], leftArr[2])) +
                Math.min(rightArr[0], Math.min(rightArr[1], rightArr[2])) + 1;
        //当前节点不放置摄像头，并且当前节点父节点放置摄像头，当前节点为根节点的树中所需的最小摄像头数量
        int parentChoose = Math.min(leftArr[0], leftArr[2]) + Math.min(rightArr[0], rightArr[2]);
        //当前节点不放置摄像头，并且当前节点子节点放置摄像头，当前节点为根节点的树中所需的最小摄像头数量
        int childChoose = Math.min(leftArr[0] + rightArr[0],
                Math.min(leftArr[0] + rightArr[2], leftArr[2] + rightArr[0]));

        //返回当前节点所需的最小摄像头数量数组
        return new int[]{curChoose, parentChoose, childChoose};
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
