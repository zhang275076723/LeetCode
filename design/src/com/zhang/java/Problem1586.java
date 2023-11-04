package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/11/9 08:56
 * @Author zsy
 * @Description 二叉搜索树迭代器 II 迭代器类比Problem173、Problem251、Problem281、Problem284、Problem341
 * 实现二叉搜索树（BST）的中序遍历迭代器 BSTIterator 类：
 * BSTIterator(TreeNode root) 初始化 BSTIterator 类的实例。二叉搜索树的根节点 root 作为构造函数的参数传入。
 * 内部指针使用一个不存在于树中且小于树中任意值的数值来初始化。
 * boolean hasNext() 如果当前指针在中序遍历序列中，存在右侧数值，返回 true ，否则返回 false 。
 * int next() 将指针在中序遍历序列中向右移动，然后返回移动后指针所指数值。
 * boolean hasPrev() 如果当前指针在中序遍历序列中，存在左侧数值，返回 true ，否则返回 false 。
 * int prev() 将指针在中序遍历序列中向左移动，然后返回移动后指针所指数值。
 * 注意，虽然我们使用树中不存在的最小值来初始化内部指针，第一次调用 next() 需要返回二叉搜索树中最小的元素。
 * 你可以假设 next() 和 prev() 的调用总是有效的。即，当 next()/prev() 被调用的时候，在中序遍历序列中一定存在下一个/上一个元素。
 * 进阶：你可以不提前遍历树中的值来解决问题吗？
 * <p>
 * 输入
 * ["BSTIterator", "next", "next", "prev", "next", "hasNext", "next", "next", "next", "hasNext", "hasPrev", "prev", "prev"]
 * [[[7, 3, 15, null, null, 9, 20]], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null]]
 * 输出
 * [null, 3, 7, 3, 7, true, 9, 15, 20, false, true, 15, 9]
 * 解释
 * // 划线的元素表示指针当前的位置。
 * BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]); // 当前状态为 <u> </u> [3, 7, 9, 15, 20]
 * bSTIterator.next(); // 状态变为 [<u>3</u>, 7, 9, 15, 20], 返回 3
 * bSTIterator.next(); // 状态变为 [3, <u>7</u>, 9, 15, 20], 返回 7
 * bSTIterator.prev(); // 状态变为 [<u>3</u>, 7, 9, 15, 20], 返回 3
 * bSTIterator.next(); // 状态变为 [3, <u>7</u>, 9, 15, 20], 返回 7
 * bSTIterator.hasNext(); // 返回 true
 * bSTIterator.next(); // 状态变为 [3, 7, <u>9</u>, 15, 20], 返回 9
 * bSTIterator.next(); // 状态变为 [3, 7, 9, <u>15</u>, 20], 返回 15
 * bSTIterator.next(); // 状态变为 [3, 7, 9, 15, <u>20</u>], 返回 20
 * bSTIterator.hasNext(); // 返回 false
 * bSTIterator.hasPrev(); // 返回 true
 * bSTIterator.prev(); // 状态变为 [3, 7, 9, <u>15</u>, 20], 返回 15
 * bSTIterator.prev(); // 状态变为 [3, 7, <u>9</u>, 15, 20], 返回 9
 * <p>
 * 树中节点个数的范围是 [1, 10^5] 。
 * 0 <= Node.val <= 10^6
 * 最多调用 10^5 次 hasNext、 next、 hasPrev 和 prev 。
 */
public class Problem1586 {
    public static void main(String[] args) {
        Problem1586 problem1586 = new Problem1586();
        String[] data = {"7", "3", "15", "null", "null", "9", "20"};
        TreeNode root = problem1586.buildTree(data);

//        BSTIterator bSTIterator = new BSTIterator(root);
        BSTIterator2 bSTIterator = new BSTIterator2(root);
        // 返回 3
        System.out.println(bSTIterator.next());
        // 返回 7
        System.out.println(bSTIterator.next());
        // 返回 3
        System.out.println(bSTIterator.prev());
        // 返回 7
        System.out.println(bSTIterator.next());
        // 返回 true
        System.out.println(bSTIterator.hasNext());
        // 返回 9
        System.out.println(bSTIterator.next());
        // 返回 15
        System.out.println(bSTIterator.next());
        // 返回 20
        System.out.println(bSTIterator.next());
        // 返回 false
        System.out.println(bSTIterator.hasNext());
        // 返回 true
        System.out.println(bSTIterator.hasPrev());
        // 返回 15
        System.out.println(bSTIterator.prev());
        // 返回 9
        System.out.println(bSTIterator.prev());
    }

    /**
     * 将树的中序遍历元素保存在集合中，使用下标索引指向集合中的元素即为中序遍历的下一个元素，
     * 下标索引指向集合中的前一个元素即为中序遍历的当前元素，当前元素的前一个元素即为中序遍历的前一个元素
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

        public boolean hasNext() {
            return index < list.size();
        }

        public int next() {
            int value = list.get(index);
            index++;
            return value;
        }

        public boolean hasPrev() {
            //index指向
            return index >= 2;
        }

        public int prev() {
            index--;
            return list.get(index - 1);
        }
    }

    /**
     * 非递归中序遍历思想
     * 不需要将中序遍历元素保存到集合中，而是在需要得到中序遍历下一个元素时，通过非递归中序遍历栈，动态的得到中序遍历下一个元素，
     * 并将当前中序遍历得到的元素保存在集合中，使用下标索引指向集合中的元素即为中序遍历的下一个元素，
     * 下标索引指向集合中的前一个元素即为中序遍历的当前元素，当前元素的前一个元素即为中序遍历的前一个元素
     * 时间复杂度O(n)，平均空间复杂度O(h)，最差空间复杂度O(n) (h：树的高度)
     */
    static class BSTIterator2 {
        //非递归中序遍历栈
        private final Stack<TreeNode> stack;
        //非递归中序遍历到的当前节点
        private TreeNode node;
        //存放当前非递归中序遍历到的元素的集合
        private final List<Integer> list;
        //指向list集合中元素的下标索引，即中序遍历的下一个元素
        private int index;

        public BSTIterator2(TreeNode root) {
            stack = new Stack<>();
            node = root;
            list = new ArrayList<>();
            index = 0;
        }

        public boolean hasNext() {
            return !stack.isEmpty() || node != null;
        }

        public int next() {
            //中序遍历的下一个元素之前已经遍历过，直接从list中返回
            if (index < list.size()) {
                int value = list.get(index);
                index++;
                return value;
            }

            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            int value = node.val;
            list.add(value);
            index++;
            node = node.right;

            return value;
        }

        public boolean hasPrev() {
            return index >= 2;
        }

        public int prev() {
            index--;
            return list.get(index - 1);
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
