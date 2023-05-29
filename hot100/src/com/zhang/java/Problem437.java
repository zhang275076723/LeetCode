package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/6/7 8:19
 * @Author zsy
 * @Description 路径总和 III 类比Problem112、Problem113、Problem257 前缀和类比Problem209、Problem325、Problem327、Problem525、Problem560、Problem862、Problem1871、Offer57_2
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
    public static void main(String[] args) {
        Problem437 problem437 = new Problem437();
        String[] data = {"10", "5", "-3", "3", "2", "null", "11", "3", "-2", "null", "1"};
        int targetSum = 8;
//        String[] data = {"1000000000", "1000000000", "null", "294967296", "null", "1000000000",
//                "null", "1000000000", "null", "1000000000"};
//        int targetSum = 0;
        TreeNode root = problem437.buildTree(data);
        System.out.println(problem437.pathSum(root, targetSum));
        System.out.println(problem437.pathSum2(root, targetSum));
    }

    /**
     * 双重dfs
     * 遍历每个节点，每个节点作为根节点dfs，节点存在负值，所以dfs不能剪枝
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

        int count = 0;

        //当前节点dfs
        count = count + dfs(root, 0, targetSum);

        //左右子树dfs
        count = count + pathSum(root.left, targetSum);
        count = count + pathSum(root.right, targetSum);

        return count;
    }

    /**
     * 前缀和 (注意：滑动窗口不适合有负数元素的情况，前缀和适合有负数元素的情况)
     * 看到连续子数组，就要想到滑动窗口和前缀和
     * 遍历每个节点，在哈希表中查找key为curSum-targetSum的节点个数
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

        //前缀和哈希表：key，根节点到当前节点路径和，使用long避免相加时int溢出；value，当前路径前缀和key的个数
        Map<Long, Integer> map = new HashMap<>();
        //用于只有一个节点，即根节点值满足为targetSum的情况
        map.put(0L, 1);

        int count = 0;

        count = count + dfs2(root, targetSum, 0, map);

        return count;
    }

    /**
     * @param root      当前根节点
     * @param sum       当前路径之和
     * @param targetSum 要求的路径之和
     * @return
     */
    private int dfs(TreeNode root, long sum, int targetSum) {
        if (root == null) {
            return 0;
        }

        int count = 0;
        //使用long避免相加时int溢出
        sum = sum + root.val;

        if (sum == targetSum) {
            //节点存在负值，所以不能剪枝，继续dfs
            count++;
        }

        count = count + dfs(root.left, sum, targetSum);
        count = count + dfs(root.right, sum, targetSum);

        return count;
    }

    /**
     * @param root      当前根节点
     * @param sum       当前路径之和
     * @param targetSum 要求的路径之和
     * @param map       根节点到当前节点，除去当前节点的路径之和map
     * @return
     */
    private int dfs2(TreeNode root, long sum, int targetSum, Map<Long, Integer> map) {
        if (root == null) {
            return 0;
        }

        int count = 0;
        //更新前缀和，根节点到当前节点的路径和
        sum = sum + root.val;

        //从前缀和哈希表中找路径和为curSum-targetSum的数量，即为路径和为targetSum的数量
        if (map.containsKey(sum - targetSum)) {
            //节点存在负值，所以不能剪枝，继续dfs
            count = count + map.get(sum - targetSum);
        }

        //当前前缀和放入前缀和哈希表中
        map.put(sum, map.getOrDefault(sum, 0) + 1);

        count = count + dfs2(root.left, sum, targetSum, map);
        count = count + dfs2(root.right, sum, targetSum, map);

        //当前路径和从哈希表中删除，因为当前分叉已经遍历结束，要遍历另一分叉，所以当前分叉的路径和已经不能使用
        map.put(sum, map.get(sum) - 1);

        return count;
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
