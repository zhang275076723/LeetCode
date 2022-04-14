package com.zhang.java;

/**
 * @Date 2022/3/19 19:44
 * @Author zsy
 * @Description 输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
 * B是A的子结构，即A中有出现和B相同的结构和节点值。
 * <p>
 * 输入：A = [1,2,3], B = [3,1]
 * 输出：false
 * <p>
 * 输入：A = [3,4,5,1,2], B = [4,1]
 * 输出：true
 */
public class Offer26 {
    public static void main(String[] args) {
        Offer26 offer26 = new Offer26();
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(4);
        TreeNode node3 = new TreeNode(5);
        TreeNode node4 = new TreeNode(1);
        TreeNode node5 = new TreeNode(2);
        TreeNode node6 = new TreeNode(4);
        TreeNode node7 = new TreeNode(1);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node6.left = node7;
        System.out.println(offer26.isSubStructure(node1, node6));
    }

    /**
     * 以B为起点，判断以B为根节点的树是否是以A为根节点的树的子结构
     *
     * @param A
     * @param B
     * @return
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        //空树不是任意一个树的子结构
        if (A == null || B == null) {
            return false;
        }

        //如果以A、B为起点，B为根节点的树是A为根节点的树的子结构，或树B是A的左子树的子结构，或树B是A的右子树的子结构，则返回ture
        return contain(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    /**
     * 以A、B为起点判断B为根节点的树是否是A为根节点的树的子结构
     *
     * @param A
     * @param B
     * @return
     */
    public boolean contain(TreeNode A, TreeNode B) {
        //如果B为空，说明B树之前的节点都包含在A树中
        if (B == null) {
            return true;
        }
        //如果A为空，说明B树中还有节点没有匹配；如果A的值不等于B的值，说明B树不是A树的子结构
        if (A == null || A.val != B.val) {
            return false;
        }

        //递归判断B的左子树是否是A的左子树的子结构，B的右子树是否是A的右子树的子结构
        return contain(A.left, B.left) && contain(A.right, B.right);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
