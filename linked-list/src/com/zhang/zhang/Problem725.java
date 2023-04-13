package com.zhang.zhang;

/**
 * @Date 2023/4/13 08:11
 * @Author zsy
 * @Description 分隔链表 分割类比Problem659
 * 给你一个头结点为 head 的单链表和一个整数 k ，请你设计一个算法将链表分隔为 k 个连续的部分。
 * 每部分的长度应该尽可能的相等：任意两部分的长度差距不能超过 1 。这可能会导致有些部分为 null 。
 * 这 k 个部分应该按照在链表中出现的顺序排列，并且排在前面的部分的长度应该大于或等于排在后面的长度。
 * 返回一个由上述 k 部分组成的数组。
 * <p>
 * 输入：head = [1,2,3], k = 5
 * 输出：[[1],[2],[3],[],[]]
 * 解释：
 * 第一个元素 output[0] 为 output[0].val = 1 ，output[0].next = null 。
 * 最后一个元素 output[4] 为 null ，但它作为 ListNode 的字符串表示是 [] 。
 * <p>
 * 输入：head = [1,2,3,4,5,6,7,8,9,10], k = 3
 * 输出：[[1,2,3,4],[5,6,7],[8,9,10]]
 * 解释：
 * 输入被分成了几个连续的部分，并且每部分的长度相差不超过 1 。前面部分的长度大于等于后面部分的长度。
 * <p>
 * 链表中节点的数目在范围 [0, 1000]
 * 0 <= Node.val <= 1000
 * 1 <= k <= 50
 */
public class Problem725 {
    public static void main(String[] args) {
        Problem725 problem725 = new Problem725();
//        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        int k = 3;
        int[] data = {1, 2, 3};
        int k = 5;
        ListNode head = problem725.buildList(data);
        ListNode[] result = problem725.splitListToParts(head, k);
    }

    /**
     * 先获取链表长度len，len/k即为每个子链表的最小长度，len%k即为从前往后每个子链表的长度需要额外加1的个数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode[] splitListToParts(ListNode head, int k) {
        //链表为空，直接返回长度为k的ListNode数组
        if (head == null) {
            return new ListNode[k];
        }

        int len = 0;
        ListNode node = head;

        //获取链表长度
        while (node != null) {
            len++;
            node = node.next;
        }

        //每个子链表的最小长度
        int minLen = len / k;
        //从前往后每个子链表的长度需要额外加1的个数
        int count = len % k;

        ListNode[] result = new ListNode[k];
        node = head;

        //前count个子链表的长度为minLen+1
        for (int i = 0; i < count; i++) {
            result[i] = node;
            //找下一个子链表的头结点
            for (int j = 0; j < minLen; j++) {
                node = node.next;
            }
            //断开链表
            ListNode temp = node.next;
            node.next = null;
            node = temp;
        }

        //k比较大，剩下的k-count个子链表均为null，直接返回
        if (node == null) {
            return result;
        }

        //剩下的k-count个子链表的长度为minLen
        for (int i = count; i < k; i++) {
            result[i] = node;
            //找下一个子链表的头结点
            for (int j = 0; j < minLen - 1; j++) {
                node = node.next;
            }
            //断开链表
            ListNode temp = node.next;
            node.next = null;
            node = temp;
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
