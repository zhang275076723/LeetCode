package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/2/1 08:36
 * @Author zsy
 * @Description 二叉树的垂序遍历
 * 给你二叉树的根结点 root ，请你设计算法计算二叉树的 垂序遍历 序列。
 * 对位于 (row, col) 的每个结点而言，其左右子结点分别位于 (row + 1, col - 1) 和 (row + 1, col + 1) 。
 * 树的根结点位于 (0, 0) 。
 * 二叉树的 垂序遍历 从最左边的列开始直到最右边的列结束，按列索引每一列上的所有结点，形成一个按出现位置从上到下排序的有序列表。
 * 如果同行同列上有多个结点，则按结点的值从小到大进行排序。
 * 返回二叉树的 垂序遍历 序列。
 * <p>
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[9],[3,15],[20],[7]]
 * 解释：
 * 列 -1 ：只有结点 9 在此列中。
 * 列  0 ：只有结点 3 和 15 在此列中，按从上到下顺序。
 * 列  1 ：只有结点 20 在此列中。
 * 列  2 ：只有结点 7 在此列中。
 * <p>
 * 输入：root = [1,2,3,4,5,6,7]
 * 输出：[[4],[2],[1,5,6],[3],[7]]
 * 解释：
 * 列 -2 ：只有结点 4 在此列中。
 * 列 -1 ：只有结点 2 在此列中。
 * 列  0 ：结点 1 、5 和 6 都在此列中。
 * 1 在上面，所以它出现在前面。
 * 5 和 6 位置都是 (2, 0) ，所以按值从小到大排序，5 在 6 的前面。
 * 列  1 ：只有结点 3 在此列中。
 * 列  2 ：只有结点 7 在此列中。
 * <p>
 * 输入：root = [1,2,3,4,6,5,7]
 * 输出：[[4],[2],[1,5,6],[3],[7]]
 * 解释：
 * 这个示例实际上与示例 2 完全相同，只是结点 5 和 6 在树中的位置发生了交换。
 * 因为 5 和 6 的位置仍然相同，所以答案保持不变，仍然按值从小到大排序。
 * <p>
 * 树中结点数目总数在范围 [1, 1000] 内
 * 0 <= Node.val <= 1000
 */
public class Problem987 {
    public static void main(String[] args) {
        Problem987 problem987 = new Problem987();
        String[] data = {"1", "2", "3", "4", "5", "6", "7"};
        TreeNode root = problem987.buildTree(data);
        System.out.println(problem987.verticalTraversal(root));
        System.out.println(problem987.verticalTraversal2(root));
    }

    /**
     * dfs模拟
     * dfs遍历root，记录节点坐标(x,y)，先按照纵坐标由小到大排序，纵坐标相等，则按照横坐标由小到大排序，横坐标相等，再按照节点值由小到大排序
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        //arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点值
        List<int[]> list = new ArrayList<>();

        //根节点坐标为(0,0)
        dfs(root, 0, 0, list);

        //先按照纵坐标由小到大排序，再按照横坐标由小到大排序，最后按照节点值由小到大排序
        list.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                if (arr1[1] != arr2[1]) {
                    return arr1[1] - arr2[1];
                } else {
                    if (arr1[0] != arr2[0]) {
                        return arr1[0] - arr2[0];
                    } else {
                        return arr1[2] - arr2[2];
                    }
                }
            }
        });

        List<List<Integer>> result = new ArrayList<>();
        int i = 0;

        while (i < list.size()) {
            List<Integer> tempList = new ArrayList<>();
            int j = i;

            //纵坐标相同的节点存放到同一个列表中
            while (j < list.size() && list.get(i)[1] == list.get(j)[1]) {
                tempList.add(list.get(j)[2]);
                j++;
            }

            result.add(tempList);
            i = j;
        }

        return result;
    }

    /**
     * bfs模拟
     * bfs遍历root，记录节点坐标(x,y)，先按照纵坐标由小到大排序，纵坐标相等，则按照横坐标由小到大排序，横坐标相等，再按照节点值由小到大排序
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> verticalTraversal2(TreeNode root) {
        //arr[0]：节点的横坐标，arr[1]：节点的纵坐标，arr[2]：节点值
        List<int[]> list = bfs(root);

        //先按照纵坐标由小到大排序，再按照横坐标由小到大排序，最后按照节点值由小到大排序
        list.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                if (arr1[1] != arr2[1]) {
                    return arr1[1] - arr2[1];
                } else {
                    if (arr1[0] != arr2[0]) {
                        return arr1[0] - arr2[0];
                    } else {
                        return arr1[2] - arr2[2];
                    }
                }
            }
        });

        List<List<Integer>> result = new ArrayList<>();
        int i = 0;

        while (i < list.size()) {
            List<Integer> tempList = new ArrayList<>();
            int j = i;

            //纵坐标相同的节点存放到同一个列表中
            while (j < list.size() && list.get(i)[1] == list.get(j)[1]) {
                tempList.add(list.get(j)[2]);
                j++;
            }

            result.add(tempList);
            i = j;
        }

        return result;
    }

    private void dfs(TreeNode node, int i, int j, List<int[]> list) {
        if (node == null) {
            return;
        }

        list.add(new int[]{i, j, node.val});

        dfs(node.left, i + 1, j - 1, list);
        dfs(node.right, i + 1, j + 1, list);
    }

    private List<int[]> bfs(TreeNode root) {
        List<int[]> list = new ArrayList<>();
        Queue<Pos> queue = new LinkedList<>();
        //根节点坐标为(0,0)
        queue.offer(new Pos(root, 0, 0));

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            list.add(new int[]{pos.i, pos.j, pos.node.val});

            if (pos.node.left != null) {
                queue.offer(new Pos(pos.node.left, pos.i + 1, pos.j - 1));
            }
            if (pos.node.right != null) {
                queue.offer(new Pos(pos.node.right, pos.i + 1, pos.j + 1));
            }
        }

        return list;
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

    /**
     * bfs节点
     */
    private static class Pos {
        private TreeNode node;
        private int i;
        private int j;

        public Pos(TreeNode node, int i, int j) {
            this.node = node;
            this.i = i;
            this.j = j;
        }
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
