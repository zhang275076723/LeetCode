package com.zhang.zhang;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/4/14 08:24
 * @Author zsy
 * @Description 从链表中删去总和值为零的连续节点 字节面试题
 * 给你一个链表的头节点 head，请你编写代码，反复删去链表中由 总和 值为 0 的连续节点组成的序列，直到不存在这样的序列为止。
 * 删除完毕后，请你返回最终结果链表的头节点。
 * 你可以返回任何满足题目要求的答案。
 * （注意，下面示例中的所有序列，都是对 ListNode 对象序列化的表示。）
 * <p>
 * 输入：head = [1,2,-3,3,1]
 * 输出：[3,1]
 * 提示：答案 [1,2,1] 也是正确的。
 * <p>
 * 输入：head = [1,2,3,-3,4]
 * 输出：[1,2,4]
 * <p>
 * 输入：head = [1,2,3,-3,-2]
 * 输出：[1]
 * <p>
 * 给你的链表中可能有 1 到 1000 个节点。
 * 对于链表中的每个节点，节点的值：-1000 <= node.val <= 1000.
 */
public class Problem1171 {
    public static void main(String[] args) {
        Problem1171 problem1171 = new Problem1171();
        int[] data = {1, 3, 2, -3, -2, 5, 5, -5, 1};
        ListNode head = problem1171.buildList(data);
        head = problem1171.removeZeroSumSublists(head);
    }

    /**
     * 前缀和+哈希表
     * 计算从头结点到每个节点的前缀和作为key，当前节点作为value，加入哈希表中，得到前缀和和最后一个节点的映射关系，
     * 再从头遍历链表中每个节点，如果哈希表中存在当前前缀和，则当前节点的下一个节点到哈希表中当前前缀和映射的节点的和为0，
     * 更新当前节点的next指针指向哈希表中当前前缀和映射的节点的next节点
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public ListNode removeZeroSumSublists(ListNode head) {
        if (head == null) {
            return null;
        }

        //设置链表头结点
        ListNode hair = new ListNode();
        hair.next = head;
        //从头结点开始遍历，保证从头结点开始的子链表存在和为0的情况
        ListNode node = hair;
        //前缀和作为key，当前节点作为value
        Map<Integer, ListNode> map = new HashMap<>();
        int sum = 0;

        //从头结点到每个节点的前缀和作为key，当前节点作为value，加入哈希表中
        while (node != null) {
            sum = sum + node.val;
            map.put(sum, node);
            node = node.next;
        }

        node = hair;
        sum = 0;

        while (node != null) {
            sum = sum + node.val;
            //map中存在当前前缀和，则当前节点的下一个节点到哈希表中当前前缀和映射的节点的和为0
            if (map.containsKey(sum)) {
                //更新当前节点的next指针指向哈希表中当前前缀和映射的节点的next节点
                ListNode nextNode = map.get(sum);
                node.next = nextNode.next;
            }
            node = node.next;
        }

        return hair.next;
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
