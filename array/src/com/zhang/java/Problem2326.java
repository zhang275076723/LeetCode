package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/1/22 08:53
 * @Author zsy
 * @Description 螺旋矩阵 IV 类比Problem48、Problem54、Problem59、Problem498、Problem885、Offer29
 * 给你两个整数：m 和 n ，表示矩阵的维数。
 * 另给你一个整数链表的头节点 head 。
 * 请你生成一个大小为 m x n 的螺旋矩阵，矩阵包含链表中的所有整数。
 * 链表中的整数从矩阵 左上角 开始、顺时针 按 螺旋 顺序填充。
 * 如果还存在剩余的空格，则用 -1 填充。
 * 返回生成的矩阵。
 * <p>
 * 输入：m = 3, n = 5, head = [3,0,2,6,8,1,7,9,4,2,5,5,0]
 * 输出：[[3,0,2,6,8],[5,0,-1,-1,1],[5,2,4,9,7]]
 * 解释：上图展示了链表中的整数在矩阵中是如何排布的。
 * 注意，矩阵中剩下的空格用 -1 填充。
 * <p>
 * 输入：m = 1, n = 4, head = [0,1,2]
 * 输出：[[0,1,2,-1]]
 * 解释：上图展示了链表中的整数在矩阵中是如何从左到右排布的。
 * 注意，矩阵中剩下的空格用 -1 填充。
 * <p>
 * 1 <= m, n <= 10^5
 * 1 <= m * n <= 10^5
 * 链表中节点数目在范围 [1, m * n] 内
 * 0 <= Node.val <= 1000
 */
public class Problem2326 {
    public static void main(String[] args) {
        Problem2326 problem2326 = new Problem2326();
        int[] data = {3, 0, 2, 6, 8, 1, 7, 9, 4, 2, 5, 5, 0};
        ListNode head = problem2326.buildList(data);
        int m = 3;
        int n = 5;
        System.out.println(Arrays.deepToString(problem2326.spiralMatrix(m, n, head)));
    }

    /**
     * 模拟
     * 使用四个指针，分别限定矩阵的上下左右边界，每次遍历完一行或一列之后，指针移动
     * 时间复杂度O(mn)，空间复杂度O(1)
     *
     * @param m
     * @param n
     * @param head
     * @return
     */
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] result = new int[m][n];

        //result初始化为-1
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = -1;
            }
        }

        //上下左右四个指针，限定矩阵的上下左右边界
        int left = 0;
        int right = n - 1;
        int top = 0;
        int bottom = m - 1;

        ListNode node = head;

        while (node != null) {
            //先从左往右遍历
            for (int i = left; i <= right; i++) {
                if (node == null) {
                    return result;
                }

                result[top][i] = node.val;
                node = node.next;
            }

            //top指针下移
            top++;

            //再从上往下遍历
            for (int i = top; i <= bottom; i++) {
                if (node == null) {
                    return result;
                }

                result[i][right] = node.val;
                node = node.next;
            }

            //right指针左移
            right--;

            //接着从右往左遍历
            for (int i = right; i >= left; i--) {
                if (node == null) {
                    return result;
                }

                result[bottom][i] = node.val;
                node = node.next;
            }

            //bottom指针上移
            bottom--;

            //最后从下往上遍历
            for (int i = bottom; i >= top; i--) {
                if (node == null) {
                    return result;
                }

                result[i][left] = node.val;
                node = node.next;
            }

            //left指针右移
            left++;
        }

        return result;
    }

    private ListNode buildList(int[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        ListNode head = new ListNode(data[0]);
        ListNode node = head;

        for (int i = 1; i < data.length; i++) {
            node.next = new ListNode(data[i]);
            node = node.next;
        }

        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
