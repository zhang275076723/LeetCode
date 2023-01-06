package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/1/6 12:49
 * @Author zsy
 * @Description 链表随机节点 搜狗面试题 类比Problem398
 * 给你一个单链表，随机选择链表的一个节点，并返回相应的节点值。每个节点 被选中的概率一样 。
 * 实现 Solution 类：
 * Solution(ListNode head) 使用整数数组初始化对象。
 * int getRandom() 从链表中随机选择一个节点并返回该节点的值。链表中所有节点被选中的概率相等。
 * <p>
 * 输入
 * ["Solution", "getRandom", "getRandom", "getRandom", "getRandom", "getRandom"]
 * [[[1, 2, 3]], [], [], [], [], []]
 * 输出
 * [null, 1, 3, 2, 2, 3]
 * 解释
 * Solution solution = new Solution([1, 2, 3]);
 * solution.getRandom(); // 返回 1
 * solution.getRandom(); // 返回 3
 * solution.getRandom(); // 返回 2
 * solution.getRandom(); // 返回 2
 * solution.getRandom(); // 返回 3
 * // getRandom() 方法应随机返回 1、2、3中的一个，每个元素被返回的概率相等。
 * <p>
 * 链表中的节点数在范围 [1, 10^4] 内
 * -10^4 <= Node.val <= 10^4
 * 至多调用 getRandom 方法 10^4 次
 */
public class Problem382 {
    public static void main(String[] args) {
        Problem382 problem382 = new Problem382();
        int[] nums = {1, 2, 3};
        ListNode head = problem382.buildList(nums);
//        Solution solution = new Solution(head);
        Solution2 solution = new Solution2(head);
        System.out.println(solution.getRandom());
        System.out.println(solution.getRandom());
        System.out.println(solution.getRandom());
        System.out.println(solution.getRandom());
        System.out.println(solution.getRandom());
    }

    /**
     * list集合
     * 空间换时间
     */
    static class Solution {
        //存储链表中节点
        private final List<ListNode> list;

        //获取随机值
        private final Random random;

        public Solution(ListNode head) {
            list = new ArrayList<>();
            random = new Random();

            if (head == null) {
                return;
            }

            ListNode node = head;

            while (node != null) {
                list.add(node);
                node = node.next;
            }
        }

        public int getRandom() {
            return list.get(random.nextInt(list.size())).val;
        }
    }

    /**
     * 蓄水池抽样，从n个元素中随机等概率的抽取k个元素，n未知
     * 时间换空间，适用于nums数组很大，无法将nums元素下标索引全部保存到内存中的情况
     */
    static class Solution2 {
        //链表头结点
        private final ListNode head;

        //获取随机值
        private final Random random;

        public Solution2(ListNode head) {
            this.head = head;
            random = new Random();
        }

        /**
         * 遍历到的当前节点，已经遍历的节点个数为count，链表一共有k个节点，
         * 选择当前节点的概率为1/k = (1/count)*(count/(count+1))*((count+1)/(count+2))*...*(k-1/k)
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @return
         */
        public int getRandom() {
            ListNode node = head;
            //当前遍历到的链表中节点个数
            int count = 0;
            int value = -1;

            while (node != null) {
                count++;

                //选择当前节点值作为结果
                if (random.nextInt(count) == 0) {
                    value = node.val;
                }

                node = node.next;
            }

            return value;
        }
    }

    private ListNode buildList(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        ListNode head = new ListNode(nums[0]);
        ListNode node = head;

        for (int i = 1; i < nums.length; i++) {
            node.next = new ListNode(nums[i]);
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
