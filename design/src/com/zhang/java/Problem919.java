package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/4/7 09:38
 * @Author zsy
 * @Description 完全二叉树插入器 完全二叉树类比Problem222、Problem958
 * 完全二叉树 是每一层（除最后一层外）都是完全填充（即，节点数达到最大）的，并且所有的节点都尽可能地集中在左侧。
 * 设计一种算法，将一个新节点插入到一个完整的二叉树中，并在插入后保持其完整。
 * 实现 CBTInserter 类:
 * CBTInserter(TreeNode root) 使用头节点为 root 的给定树初始化该数据结构；
 * CBTInserter.insert(int v) 向树中插入一个值为 Node.val == val的新节点 TreeNode。
 * 使树保持完全二叉树的状态，并返回插入节点 TreeNode 的父节点的值；
 * CBTInserter.get_root() 将返回树的头节点。
 * <p>
 * 输入
 * ["CBTInserter", "insert", "insert", "get_root"]
 * [[[1, 2]], [3], [4], []]
 * 输出
 * [null, 1, 2, [1, 2, 3, 4]]
 * 解释
 * CBTInserter cBTInserter = new CBTInserter([1, 2]);
 * cBTInserter.insert(3);  // 返回 1
 * cBTInserter.insert(4);  // 返回 2
 * cBTInserter.get_root(); // 返回 [1, 2, 3, 4]
 * <p>
 * 树中节点数量范围为 [1, 1000]
 * 0 <= Node.val <= 5000
 * root 是完全二叉树
 * 0 <= val <= 5000
 * 每个测试用例最多调用 insert 和 get_root 操作 10^4 次
 */
public class Problem919 {
    public static void main(String[] args) {
        Problem919 problem919 = new Problem919();
        String[] data = {"1", "2"};
        TreeNode root = problem919.buildTree(data);
        CBTInserter cBTInserter = new CBTInserter(root);
//        CBTInserter2 cBTInserter = new CBTInserter2(root);
        // 返回 1
        cBTInserter.insert(3);
        // 返回 2
        cBTInserter.insert(4);
        // 返回 [1, 2, 3, 4]
        TreeNode node = cBTInserter.get_root();
    }

    /**
     * 队列存储完全二叉树中可以插入子节点的节点
     */
    static class CBTInserter {
        //完全二叉树根节点
        private final TreeNode root;
        //完全二叉树中可以插入子节点的队列，并且插入之后保持完全二叉树性质
        private final Queue<TreeNode> candidateQueue;

        /**
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param root
         */
        public CBTInserter(TreeNode root) {
            this.root = root;
            candidateQueue = new LinkedList<>();

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();

                //当前节点左右子树只要有一个为空，则当前节点可以是完全二叉树下一个插入节点的父节点，放入candidateQueue
                if (node.left == null || node.right == null) {
                    candidateQueue.offer(node);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        /**
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @param val
         * @return
         */
        public int insert(int val) {
            //当前要插入节点的父节点
            TreeNode parentNode = candidateQueue.peek();
            //当前要插入的节点
            TreeNode node = new TreeNode(val);

            if (parentNode.left == null) {
                parentNode.left = node;
                candidateQueue.offer(node);
            } else if (parentNode.right == null) {
                parentNode.right = node;
                candidateQueue.offer(node);
                //当前父节点左右子树都有节点，则从candidateQueue出队
                candidateQueue.poll();
            }

            return parentNode.val;
        }

        public TreeNode get_root() {
            return root;
        }
    }

    /**
     * 根据完全二叉树中父子节点的关系，从根节点找到当前节点应该插入的位置
     * 父节点下标索引为1(从1开始)，则左子节点下标索引为2，右子节点下标索引为3
     * 例如：一个节点下标索引为10，二进制表示为1010，
     * 第一位为1，则表示根节点下标索引；
     * 第二位为0，则表示往左子树找；
     * 第三位为1，则表示往右子树找，则找到当前节点的父节点；
     * 第四位为0，则表示插入父节点的左子树中
     */
    static class CBTInserter2 {
        //完全二叉树根节点
        private final TreeNode root;
        //完全二叉树中节点的个数，统计个数可以使用Problem222中O((logn)^2)的方法
        private int count;

        /**
         * 统计完全二叉树中节点个数，统计个数可以使用Problem222中O((logn)^2)的方法
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param root
         */
        public CBTInserter2(TreeNode root) {
            this.root = root;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                count++;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        /**
         * 时间复杂度O(logn)，空间复杂度O(1)
         *
         * @param val
         * @return
         */
        public int insert(int val) {
            //树中节点个数加1
            count++;
            //当前节点下标索引(从1开始)
            int index = count;
            //当前节点下标索引(从1开始)，二进制表示的长度
            int bitLength = 0;

            while (index != 0) {
                bitLength++;
                index = index >>> 1;
            }

            index = count;
            //当前要插入节点的父节点
            TreeNode parentNode = root;
            //当前要插入的节点
            TreeNode node = new TreeNode(val);

            //从当前节点二进制表示的最高位的下一位往低位遍历(最高位表示根节点，最低位表示插入当前节点的左子树还是右子树)，
            //如果当前位为0，则表示往左子树找，为1，则表示往右子树找
            for (int i = bitLength - 2; i > 0; i--) {
                if ((index & (1 << i)) == 0) {
                    parentNode = parentNode.left;
                } else {
                    parentNode = parentNode.right;
                }
            }

            //当前节点二进制表示的最低位为0，则node插入到parentNode的左子树中
            if ((index & 1) == 0) {
                parentNode.left = node;
            } else {
                //当前节点二进制表示的最低位为1，则node插入到parentNode的右子树中
                parentNode.right = node;
            }

            return parentNode.val;
        }

        public TreeNode get_root() {
            return root;
        }
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
