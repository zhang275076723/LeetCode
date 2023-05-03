package com.zhang.java;

/**
 * @Date 2022/11/11 11:14
 * @Author zsy
 * @Description 将有序数组转换为二叉搜索树 分治法类比Problem95、Problem105、Problem106、Problem109、Problem255、Problem449、Problem889、Problem1008、Offer7、Offer33
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 二叉搜索树。
 * 高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。
 * <p>
 * 输入：nums = [-10,-3,0,5,9]
 * 输出：[0,-3,9,-10,null,5]
 * 解释：[0,-10,5,null,-3,null,9] 也将被视为正确答案：
 * <p>
 * 输入：nums = [1,3]
 * 输出：[3,1]
 * 解释：[1,null,3] 和 [3,1] 都是高度平衡二叉搜索树。
 * <p>
 * 1 <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums 按 严格递增 顺序排列
 */
public class Problem108 {
    public static void main(String[] args) {
        Problem108 problem108 = new Problem108();
        int[] nums = {-10, -3, 0, 5, 9};
        TreeNode root = problem108.sortedArrayToBST(nums);
    }

    /**
     * 分治法
     * 高度平衡二叉搜索树，即需要每次从数组中间节点进行划分
     * 数组中间节点即为根节点，将数组分为左子数组和右子数组，递归对左子数组和右子数组建立二叉搜索树
     * 时间复杂度O(n)，空间复杂度O(logn)
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        return buildTree(nums, 0, nums.length - 1);
    }

    private TreeNode buildTree(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        if (left == right) {
            return new TreeNode(nums[left]);
        }

        int mid = left + ((right - left) >> 1);
        //数组中间节点作为根节点，保证构建的树是高度平衡二叉搜索树
        TreeNode root = new TreeNode(nums[mid]);

        root.left = buildTree(nums, left, mid - 1);
        root.right = buildTree(nums, mid + 1, right);

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
