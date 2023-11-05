package com.zhang.java;


import java.util.*;

/**
 * @Date 2023/1/4 08:51
 * @Author zsy
 * @Description 二叉搜索树迭代器 迭代器类比Problem251、Problem281、Problem284、Problem341、Problem604、Problem900、Problem1286、Problem1586
 * 实现一个二叉搜索树迭代器类BSTIterator ，表示一个按中序遍历二叉搜索树（BST）的迭代器：
 * BSTIterator(TreeNode root) 初始化 BSTIterator 类的一个对象。BST 的根节点 root 会作为构造函数的一部分给出。
 * 指针应初始化为一个不存在于 BST 中的数字，且该数字小于 BST 中的任何元素。
 * boolean hasNext() 如果向指针右侧遍历存在数字，则返回 true ；否则返回 false 。
 * int next()将指针向右移动，然后返回指针处的数字。
 * 注意，指针初始化为一个不存在于 BST 中的数字，所以对 next() 的首次调用将返回 BST 中的最小元素。
 * 你可以假设 next() 调用总是有效的，也就是说，当调用 next() 时，BST 的中序遍历中至少存在一个下一个数字。
 * <p>
 * 输入
 * ["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
 * [[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
 * 输出
 * [null, 3, 7, true, 9, true, 15, true, 20, false]
 * 解释
 * BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
 * bSTIterator.next();    // 返回 3
 * bSTIterator.next();    // 返回 7
 * bSTIterator.hasNext(); // 返回 True
 * bSTIterator.next();    // 返回 9
 * bSTIterator.hasNext(); // 返回 True
 * bSTIterator.next();    // 返回 15
 * bSTIterator.hasNext(); // 返回 True
 * bSTIterator.next();    // 返回 20
 * bSTIterator.hasNext(); // 返回 False
 * <p>
 * 树中节点的数目在范围 [1, 10^5] 内
 * 0 <= Node.val <= 10^6
 * 最多调用 10^5 次 hasNext 和 next 操作
 */
public class Problem173 {
    public static void main(String[] args) {
        Problem173 problem173 = new Problem173();
        String[] data = {"7", "3", "15", "null", "null", "9", "20"};
        TreeNode root = problem173.buildTree(data);

//        BSTIterator bstIterator = new BSTIterator(root);
        BSTIterator2 bstIterator = new BSTIterator2(root);
        // 返回 3
        System.out.println(bstIterator.next());
        // 返回 7
        System.out.println(bstIterator.next());
        // 返回 True
        System.out.println(bstIterator.hasNext());
        // 返回 9
        System.out.println(bstIterator.next());
        // 返回 True
        System.out.println(bstIterator.hasNext());
        // 返回 15
        System.out.println(bstIterator.next());
        // 返回 True
        System.out.println(bstIterator.hasNext());
        // 返回 20
        System.out.println(bstIterator.next());
        // 返回 False
        System.out.println(bstIterator.hasNext());
    }

    /**
     * 将树的中序遍历元素保存在集合中，使用下标索引指向集合中的元素即为中序遍历的下一个元素
     * 时间复杂度O(n)，空间复杂度O(n)
     */
    static class BSTIterator {
        //存放中序遍历元素的集合
        private final List<Integer> list;
        //指向list集合中元素的下标索引，即中序遍历的下一个元素
        private int index;

        public BSTIterator(TreeNode root) {
            list = new ArrayList<>();
            index = 0;

            //非递归中序遍历
            Stack<TreeNode> stack = new Stack<>();
            TreeNode node = root;

            while (!stack.isEmpty() || node != null) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }

                node = stack.pop();
                list.add(node.val);
                node = node.right;
            }
        }

        public int next() {
            int value = list.get(index);
            index++;
            return value;
        }

        public boolean hasNext() {
            return index < list.size();
        }
    }

    /**
     * 非递归中序遍历思想
     * 不需要将中序遍历元素保存到集合中，而是在需要得到中序遍历下一个元素时，通过非递归中序遍历栈，动态的得到中序遍历下一个元素
     * 时间复杂度O(n)，平均空间复杂度O(h)，最差空间复杂度O(n) (h：树的高度)
     */
    static class BSTIterator2 {
        //非递归中序遍历栈
        private final Stack<TreeNode> stack;
        //非递归中序遍历到的当前节点
        private TreeNode node;

        public BSTIterator2(TreeNode root) {
            stack = new Stack<>();
            node = root;
        }

        public int next() {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            int value = node.val;
            node = node.right;

            return value;
        }

        public boolean hasNext() {
            return !stack.isEmpty() || node != null;
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
