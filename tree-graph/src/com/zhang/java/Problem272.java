package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/15 09:42
 * @Author zsy
 * @Description 最接近的二叉搜索树值 II 类比Problem658 保存父节点类比Problem113、Problem126、Problem863、Offer34 类比Problem235、Problem236、Problem270、Problem285、Problem450、Problem510、Problem700、Problem701、Offer68、Offer68_2
 * 给定二叉搜索树的根 root 、一个目标值 target 和一个整数 k ，返回BST中最接近目标的 k 个值。
 * 你可以按 任意顺序 返回答案。
 * 题目 保证 该二叉搜索树中只会存在一种 k 个值集合最接近 target。
 * <p>
 * 输入: root = [4,2,5,1,3]，目标值 = 3.714286，且 k = 2
 * 输出: [4,3]
 * <p>
 * 输入: root = [1], target = 0.000000, k = 1
 * 输出: [1]
 * <p>
 * 二叉树的节点总数为 n
 * 1 <= k <= n <= 10^4
 * 0 <= Node.val <= 10^9
 * -10^9 <= target <= 10^9
 */
public class Problem272 {
    public static void main(String[] args) {
        Problem272 problem272 = new Problem272();
        String[] data = {"4", "2", "5", "1", "3"};
        TreeNode root = problem272.buildTree(data);
        double target = 3.714286;
        int k = 2;
        System.out.println(problem272.closestKValues(root, target, k));
        System.out.println(problem272.closestKValues2(root, target, k));
    }

    /**
     * 中序遍历
     * 中序遍历过程中，如果list元素个数小于k，即最接近target的元素个数小于k，则当前节点值加入list末尾；
     * 如果list元素个数等于k，则list中距离target最远的元素为list两端元素，如果list两端元素距离target的距离小于等于当前节点值距离target的距离，
     * 则中序遍历当前节点和之后的所有节点都不是最接近target的k个元素，直接跳出中序遍历返回；
     * 否则list两端元素距离target的较大距离从list中移除，当前节点加入list末尾
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param root
     * @param target
     * @param k
     * @return
     */
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        //LinkedList才能在O(1)首尾添加和移除
        LinkedList<Integer> list = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            //list元素个数小于k，即最接近target的元素个数小于k，则当前节点值加入list末尾
            if (list.size() < k) {
                list.addLast(node.val);
            } else {
                //list元素个数等于k，则list中距离target最远的元素为list两端元素

                //list两端元素距离target的距离小于等于当前节点值距离target的距离，
                //则中序遍历当前节点和之后的所有节点都不是最接近target的k个元素，直接跳出中序遍历返回
                if (Math.max(Math.abs(list.get(0) - target), Math.abs(list.get(list.size() - 1) - target)) <= Math.abs(node.val - target)) {
                    break;
                } else {
                    //list两端元素距离target的距离大于当前节点值距离target的距离，
                    //则list两端元素距离target的较大距离从list中移除，当前节点加入list末尾
                    if (Math.abs(list.get(0) - target) < Math.abs(list.get(list.size() - 1) - target)) {
                        list.removeLast();
                        list.addLast(node.val);
                    } else {
                        list.removeFirst();
                        list.addLast(node.val);
                    }
                }
            }

            node = node.right;
        }

        return list;
    }

    /**
     * 利用二叉搜索树性质
     * 1、同270题，找最接近target的节点，如果当前节点小于target，则往右子树找最接近target的节点；否则，往左子树找最接近target的节点
     * 2、同510题，找当前节点的中序遍历的前一个节点和后一个节点
     * 注意：找最接近target的节点和找当前节点的中序遍历的前一个节点和后一个节点时，通过map记录当前节点的父节点
     * 时间复杂度O(kh)，空间复杂度O(1) (h=logn，即树的高度)
     *
     * @param root
     * @param target
     * @param k
     * @return
     */
    public List<Integer> closestKValues2(TreeNode root, double target, int k) {
        //最接近target的k个节点不要求顺序，则使用ArrayList也可以
        List<Integer> list = new ArrayList<>();
        //通过map记录当前节点的父节点
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        //根节点的父节点为null
        parentMap.put(root, null);

        //最接近target的节点
        TreeNode node = getClosestNode(root, target, parentMap);
        //当前节点的中序遍历的前一个节点
        TreeNode inorderPreNode = getInorderPreNode(node, parentMap);
        //当前节点的中序遍历的后一个节点
        TreeNode inorderNextNode = getInorderNextNode(node, parentMap);

        //最接近target的节点先加入list
        list.add(node.val);

        //判断inorderPreNode和inorderNextNode谁距离target近，谁就是最接近target的k个节点
        for (int i = 1; i < k; i++) {
            //inorderPreNode为空，则inorderNextNode为最接近target的k个节点，加入list，同时更新inorderNextNode
            if (inorderPreNode == null) {
                list.add(inorderNextNode.val);
                inorderNextNode = getInorderNextNode(inorderNextNode, parentMap);
            } else if (inorderNextNode == null) {
                //inorderNextNode为空，则inorderPreNode为最接近target的k个节点，加入list，同时更新inorderPreNode
                list.add(inorderPreNode.val);
                inorderPreNode = getInorderPreNode(inorderPreNode, parentMap);
            } else {
                //inorderPreNode距离target的距离小于inorderNextNode距离target的距离，
                //则inorderPreNode为最接近target的k个节点，加入list，同时更新inorderPreNode
                if (Math.abs(inorderPreNode.val - target) < Math.abs(inorderNextNode.val) - target) {
                    list.add(inorderPreNode.val);
                    inorderPreNode = getInorderPreNode(inorderPreNode, parentMap);
                } else {
                    //inorderPreNode距离target的距离大于等于inorderNextNode距离target的距离，
                    //则inorderNextNode为最接近target的k个节点，加入list，同时更新inorderNextNode
                    list.add(inorderNextNode.val);
                    inorderNextNode = getInorderNextNode(inorderNextNode, parentMap);
                }
            }
        }

        return list;
    }

    /**
     * 返回树中最接近target的节点，同时记录当前节点的父节点
     * 如果当前节点小于target，则往右子树找最接近target的节点；否则，往左子树找最接近target的节点
     * 时间复杂度O(h)，空间复杂度O(1) (h=logn，即树的高度)
     *
     * @param root
     * @param target
     * @param parentMap
     * @return
     */
    private TreeNode getClosestNode(TreeNode root, double target, Map<TreeNode, TreeNode> parentMap) {
        TreeNode closestNode = root;
        TreeNode node = root;

        while (node != null) {
            if (Math.abs(node.val - target) < Math.abs(closestNode.val - target)) {
                closestNode = node;
            }

            if (node.val < target) {
                //在遍历过程中更新parentMap
                parentMap.put(node.right, node);
                node = node.right;
            } else {
                //在遍历过程中更新parentMap
                parentMap.put(node.left, node);
                node = node.left;
            }
        }

        return closestNode;
    }

    /**
     * 返回当前节点中序遍历的前一个节点，同时记录当前节点的父节点
     * 1、当前节点存在左子树，则左子树的最右下节点为当前节点中序遍历的前一个节点
     * 2、当前节点不存在左子树，则当前节点沿着父节点往上找，直至temp节点父节点的右子节点等于temp节点时，temp节点的父节点为当前节点中序遍历的前一个节点
     * 时间复杂度O(h)，空间复杂度O(1) (h=logn，即树的高度)
     *
     * @param node
     * @param parentMap
     * @return
     */
    private TreeNode getInorderPreNode(TreeNode node, Map<TreeNode, TreeNode> parentMap) {
        //情况1：当前节点存在左子树，则左子树的最右下节点为当前节点中序遍历的前一个节点
        if (node.left != null) {
            //在遍历过程中更新parentMap
            parentMap.put(node.left, node);
            //node左子树的最右下节点
            TreeNode mostRightNode = node.left;

            while (mostRightNode.right != null) {
                //在遍历过程中更新parentMap
                parentMap.put(mostRightNode.right, mostRightNode);
                mostRightNode = mostRightNode.right;
            }

            return mostRightNode;
        } else {
            //情况2：当前节点不存在左子树，则当前节点沿着父节点往上找，直至temp节点父节点的右子节点等于temp节点时，temp节点的父节点为当前节点中序遍历的前一个节点

            TreeNode temp = node;

            while (parentMap.get(temp) != null && parentMap.get(temp).right != temp) {
                temp = parentMap.get(temp);
            }

            return parentMap.get(temp);
        }
    }

    /**
     * 返回当前节点中序遍历的下一个节点，同时记录当前节点的父节点
     * 1、当前节点存在右子树，则右子树的最左下节点为当前节点中序遍历的下一个节点
     * 2、当前节点不存在右子树，则当前节点沿着父节点往上找，直至temp节点父节点的左子节点等于temp节点时，temp节点的父节点为当前节点中序遍历的下一个节点
     * 时间复杂度O(h)，空间复杂度O(1) (h=logn，即树的高度)
     *
     * @param node
     * @param parentMap
     * @return
     */
    private TreeNode getInorderNextNode(TreeNode node, Map<TreeNode, TreeNode> parentMap) {
        //情况1：当前节点存在右子树，则右子树的最左下节点为当前节点中序遍历的下一个节点
        if (node.right != null) {
            //在遍历过程中更新parentMap
            parentMap.put(node.right, node);
            //node右子树的最左下节点
            TreeNode mostLeftNode = node.right;

            while (mostLeftNode.left != null) {
                //在遍历过程中更新parentMap
                parentMap.put(mostLeftNode.left, mostLeftNode);
                mostLeftNode = mostLeftNode.left;
            }

            return mostLeftNode;
        } else {
            //情况2：当前节点不存在右子树，则当前节点沿着父节点往上找，直至temp节点父节点的左子节点等于temp节点时，temp节点的父节点为当前节点中序遍历的下一个节点

            TreeNode temp = node;

            while (parentMap.get(temp) != null && parentMap.get(temp).left != temp) {
                temp = parentMap.get(temp);
            }

            return parentMap.get(temp);
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
